package workerpool

import (
	"context"
	"fmt"
	"log/slog"
	"sync"
)

type Job interface{}    //any
type Result interface{} //any

type ProcessFunc func(ctx context.Context, job Job) Result

// o que caracteriza um workerpool
type WorkerPool interface {
	Start(ctx context.Context, inputCn <-chan Job) (<-chan Result, error)
	Stop() error
	IsRunning() bool
}

type State int

// enum
const (
	StateIdle State = iota
	StateRunning
	StateStopping
)

type Config struct {
	WorkerCount int
	Logger      *slog.Logger
}

func Defaultconfig() Config {
	return Config{
		WorkerCount: 1,
		Logger:      slog.Default(),
	}
}

type workerPool struct {
	workerCount int
	processFunc ProcessFunc
	logger      *slog.Logger
	state       State
	stateMutex  sync.Mutex //impede condição de corrida entre dois workers
	stopCh      chan struct{}
	stopWg      sync.WaitGroup //aguuarda todas as tarefas a serem executadas (sincronizar threads)
}

//Mutex: trava um recorso enquanto vc está usando ele para que ninguém consiga usa-lo enquanto vc usa.

func New(processFunc ProcessFunc, config Config) *workerPool {
	if config.WorkerCount <= 0 {
		config.WorkerCount = 1
	}
	if config.Logger == nil {
		config.Logger = slog.Default()
	}

	return &workerPool{
		processFunc: processFunc,
		workerCount: config.WorkerCount,
		stopCh:      make(chan struct{}),
		state:       StateIdle,
		logger:      config.Logger,
	}
}

func (wp *workerPool) Start(ctx context.Context, inputCh <-chan Job) (<-chan Result, error) {
	wp.stateMutex.Lock()         //travando recurso
	defer wp.stateMutex.Unlock() //no final da função eu libero o recurso

	//valida se o estado dele está diferente de parado.
	if wp.state != StateIdle {
		return nil, fmt.Errorf("Worker pool is not idle.")
	}

	resultCh := make(chan Result) //criando canal de Result
	wp.state = StateRunning       //alterar estado para "rodando"
	wp.stopCh = make(chan struct{})

	/*
		sincronismo de threads - Agarda as tarefas a serem executadas
		recebe um valor int como parâmetro, que serão os "créditos"
		a cada finalização esse "crédito" é descontado (-1).
	*/
	wp.stopWg.Add(wp.workerCount) //adicionando os "créditos"

	//inicializando cada worker
	for w := range wp.workerCount {
		go wp.worker(ctx, w, inputCh, resultCh)
	}

	//goroutine = vai ser uma thread anônima
	go func() {
		wp.stopWg.Wait() //aguardando até que os "créditos" cheguem a zero
		close(resultCh)  //fechando canal. Pois já terminou o que tinha que envia para ele.

		wp.stateMutex.Lock()   //travando vando meu workerpool para manipular ele.
		wp.state = StateIdle   //manipualando estado dele
		wp.stateMutex.Unlock() //liberando o recuros após alterar.
	}()

	return resultCh, nil
}

func (wp *workerPool) Stop() error {
	wp.stateMutex.Lock()
	defer wp.stateMutex.Unlock()

	if wp.state != StateRunning {
		return fmt.Errorf("Worker pool is not running")
	}

	wp.state = StateStopping
	close(wp.stopCh)

	/*
		Como não tenho certeza que o worker já concluiu seus procesamentos,
		vamos assegurar isso pedindo para ele esperar o processamento dos workers
	*/
	wp.stopWg.Wait()

	wp.state = StateIdle //só após ele concluir tudo, será alterado o estado para "parado"
	return nil           //termina função;
}

func (wp *workerPool) IsRunning() bool {
	wp.stateMutex.Lock()
	defer wp.stateMutex.Unlock()
	return wp.state == StateRunning
}

func (wp *workerPool) worker(ctx context.Context, id int, inputCh <-chan Job, resultCh chan<- Result) {
	wp.logger.Info("Worker started", "worker_id", id)

	for {
		//select é quase um Switch Case, porém voltado para concorrência.
		//quem receber primeiro executa
		//se alguma condição for satisfeita primeiro, vai executar.
		select {
		case <-wp.stopCh:
			wp.logger.Info("Worker interrompido", "worker_id", id)
			return
		case <-ctx.Done():
			wp.logger.Info("Contexto cancelado, interrompendo worker", "worker_id", id)
			return
		case job, ok := <-inputCh:
			if !ok {
				wp.logger.Info("Canal de entrada fechado, interrompendo worker", "worker_id", id)
				return
			}

			result := wp.processFunc(ctx, job)
			select {
			case resultCh <- result:
			case <-wp.stopCh:
				wp.logger.Info("Worker interrompido, não enviando resultado", "worker_id", id)
				return
			case <-ctx.Done():
				wp.logger.Info("Contexto cancelado, interrompendo worker", "worker_id", id)
				return
			}
		}
	}
}
