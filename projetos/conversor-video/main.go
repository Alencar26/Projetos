package main

import (
	"context"
	"fmt"
	"math/rand"
	"sync"
	"time"

	"github.com/Alencar26/Projetos/pkg/workerpool"
)

type NumeroJob struct {
	Numero int
}

type ResultadoNumero struct {
	Valor     int
	WorkerID  int
	Timestamp time.Time
}

// SIMULANDO uma função de processamento. Poderia ser uma funcção que processa qualquer coisa.
func processarNumero(ctx context.Context, job workerpool.Job) workerpool.Result {
	numero := job.(NumeroJob).Numero //efetuando o casting em job já que ele é uma interface vazia
	workerID := numero % 3           //simulando ID falso

	//simulando a duração do processamento
	sleepTime := time.Duration(800+rand.Intn(400)) * time.Millisecond
	time.Sleep(sleepTime)

	//retornando um result fake
	return ResultadoNumero{
		Valor:     numero,
		WorkerID:  workerID,
		Timestamp: time.Now(),
	}
}

func main() {
	valorMaximo := 20
	bufferSize := 10

	pool := workerpool.New(processarNumero, workerpool.Config{
		WorkerCount: 3,
	})

	inputCh := make(chan workerpool.Job, bufferSize) //criando canal de job com tamanho de 10
	ctx := context.Background()                      //contexto vazio, não faz nada.

	resultCh, err := pool.Start(ctx, inputCh)
	if err != nil {
		panic(err)
	}

	var wg sync.WaitGroup
	wg.Add(valorMaximo) //iniciando wg com valor de 20

	fmt.Println("Iniciando o pool de workers com contagem de", valorMaximo, "números.")

	//função anônima para rodar em background (thread)
	//vai jogando informações no canal de input
	go func() {
		for i := range valorMaximo {
			inputCh <- NumeroJob{Numero: i}
		}
		close(inputCh)
	}()

	//func em background que fica consultando o canal de resultado e processando o que vai caindo no canal
	go func() {
		for result := range resultCh {
			r := result.(ResultadoNumero) //casting para conseguir manipular o result.
			fmt.Printf("Número: %d, Worker_ID: %d, Timestamp: %s\n", r.Valor, r.WorkerID, r.Timestamp)
			wg.Done() //vai dando baixa no wg (-1) para que ele chegue a zero.
		}
	}()

	wg.Wait() //espera o wg chegar a zero e finaliza
	fmt.Println("Pool de workers finalizado.")
}
