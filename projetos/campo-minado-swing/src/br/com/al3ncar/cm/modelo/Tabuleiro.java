package br.com.al3ncar.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador {

    private final int linhas;
    private final int colunas;
    private final int minas;

    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    // percorre cada elemento recebendo campo e fazendo algo com ele.
    public void paraCadaCampo(Consumer<Campo> funcao) {
        campos.forEach(funcao);
    }


    public void registrarObservador(Consumer<ResultadoEvento> observador) {
        observadores.add(observador);
    }

    private void notificarObservador(boolean resultado) {
        observadores.forEach(observador -> observador.accept(new ResultadoEvento(resultado)));
    }

    private void gerarCampos() {
        for(int linha = 1; linha <= linhas; linha++) {
            for (int coluna = 1; coluna <= colunas; coluna++) {
                Campo campo = new Campo(linha, coluna);
                campo.registrarObservador(this);
                campos.add(campo);
            }
        }
    }

    private void associarVizinhos() {
        for(Campo campo : campos) {
            for(Campo candidatoAVizinho : campos) {
                campo.adicionarVizinho(candidatoAVizinho);
            }
        }
    }

    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado = Campo::isMinado;

        do {
            int campoAleatorio = (int) (Math.random() * campos.size());
            campos.get(campoAleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while (minasArmadas < minas);
    }

    public void abrir(int linha, int coluna) {
            campos.parallelStream()
                    .filter(campo -> campo.getLinha() == linha)
                    .filter(campo -> campo.getColuna() == coluna)
                    .findFirst()
                    .ifPresent(Campo::abrir);
    }

    public void alternarMarcacao(int linha, int coluna) {
        campos.parallelStream()
                .filter(campo -> campo.getLinha() == linha)
                .filter(campo -> campo.getColuna() == coluna)
                .findFirst()
                .ifPresent(Campo::alternarMarcacao);
    }

    //método que diz se jogador ganhou.
    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(Campo::objetivoAlcancado);
    }

    //reiniciar jogo
    public void reniciar() {
        campos.forEach(Campo::reiniciar);
        sortearMinas();
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        if (evento == CampoEvento.EXPLODIR) {
            mostrarMinas();
            notificarObservador(false);
        } else if (objetivoAlcancado()) {
            notificarObservador(true);
        }
    }

    private void mostrarMinas() {
        campos.stream()
                .filter(Campo::isMinado)
                .filter(c -> !c.isMarcado())
                .forEach(campo -> campo.setAberto(true));
    }
}
