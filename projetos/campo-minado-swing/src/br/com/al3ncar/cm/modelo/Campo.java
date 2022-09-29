package br.com.al3ncar.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;

    private boolean aberto = false;
    private boolean minado = false;
    private boolean marcado = false;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void registrarObservador(CampoObservador observador) {
        observadores.add(observador);
    }

    private void notificarObservador(CampoEvento evento) {
        observadores.forEach(observador -> observador.eventoOcorreu(this,evento));
    }

    /*
     * Implementação da lógica para adicionar um vizinho:
     *
     * Para que seja possível adicionar um vizinho ao campo
     * deve-se verificar o delta dos campos ao vizinho se o
     * delta for  igual a 1 significa que ele é um vizinho
     * da linha horizontal ou vertical, já se o delta for 2
     * significa que esse vizinho é da diagonal.
     * Exemplo:
     *
     * Considere a matriz abaixo.
     *
     *          1,1  1,2  1,3
     *          2,1  2,2  2,3
     *          3,1  3,2  3,3
     *
     * Se pegarmos o valor do meio da matriz ou seja (2,2)
     * e subtrairmos os valores da linha e coluna do mesmo
     * e pegarmos o vbalor absoluto, então teremos 1. Agora
     * se pegar os valores na diagora e fazer o mesmo processo
     * de subtração e pegar o valor absoluto, então teremos 2.
     *
     * Essa é a lógica que define se adicionamos um vizinho ou não.
     *
     */
    boolean adicionarVizinho(Campo vizinho) {

        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    public void alternarMarcacao() {
        if (!aberto) {
            marcado = !marcado;
        }

        if (marcado) {
            notificarObservador(CampoEvento.MARCAR);
        } else {
            notificarObservador(CampoEvento.DESMARCAR);
        }
    }

    public boolean abrir() {
        if (!aberto && !marcado) {
            if (minado) {
                notificarObservador(CampoEvento.EXPLODIR);
                return true;
            }

            setAberto(true);

            if (vizinhancaSegura()) {
                vizinhos.forEach(vizinho -> vizinho.abrir());
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(vizinho -> vizinho.minado);
    }

    boolean minar() {
        if(!minado) {
            minado = true;
            return true;
        }
        return false;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isAberto() {
        return aberto;
    }

    public boolean isMinado() {
        return minado;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public boolean isDesmarcado() {
        return !isAberto();
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
        if (aberto) notificarObservador(CampoEvento.ABRIR);
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;

        return desvendado || protegido;
    }

    public long minasNaVizinhanca() {
        return vizinhos.stream().filter(vizinho -> vizinho.minado).count();
    }

    void reiniciar() {
        aberto = false;
        minado = false;
        marcado = false;
        notificarObservador(CampoEvento.REINICIAR);
    }
}
