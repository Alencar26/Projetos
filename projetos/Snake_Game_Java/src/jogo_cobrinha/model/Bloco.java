package jogo_cobrinha.model;

public class Bloco {
    private int linha, coluna;
    private TipoBloco tipo;

    public Bloco(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public TipoBloco getTipo() {
        return tipo;
    }

    public void setTipo(TipoBloco tipo) {
        this.tipo = tipo;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    @Override
    public String toString() {
        if (this.tipo == TipoBloco.FRUTA) {
            return "*";
        } else if (this.tipo == TipoBloco.COBRA) {
            return "@";
        } else {
            return " ";
        }
    }
}
