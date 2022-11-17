package jogo_cobrinha.model;

import jogo_cobrinha.exception.PosicaoException;

public class Tabuleiro {
    final int LINHAS, COLUNAS;
    final private Bloco[][] tabuleiro;

    public Tabuleiro(int LINHAS, int COLUNAS) {
        this.LINHAS = LINHAS;
        this.COLUNAS = COLUNAS;

        tabuleiro = new Bloco[LINHAS][COLUNAS];
        gerarCampos();
    }

    private void gerarCampos() {
        for (int linha = 0; linha < LINHAS; linha++) {
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                tabuleiro[linha][coluna] = new Bloco(linha, coluna);
            }
        }
    }

    public void sortearFruta() {
        int linhaAleatoria = (int) (Math.random() * LINHAS);
        int colunaAleatoria = (int) (Math.random() * COLUNAS);
        if ( tabuleiro[linhaAleatoria][colunaAleatoria].getTipo() != TipoBloco.COBRA) {
            tabuleiro[linhaAleatoria][colunaAleatoria].setTipo(TipoBloco.FRUTA);
        } else {
            throw new PosicaoException();
        }
    }

    public Bloco posicaoInicalCobra() {
        int linha = LINHAS / 2;
        int coluna = COLUNAS / 2;
        return new Bloco(linha,coluna);
    }

    public Bloco[][] getTabuleiro() {
        return tabuleiro;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int c = -1; c <= COLUNAS; c++) {
            sb.append("#");
        }
        sb.append("\n");
        for (int linha = 0; linha < LINHAS; linha++) {
            sb.append("#");
            for (int coluna = 0; coluna < COLUNAS; coluna++) {
                sb.append(tabuleiro[linha][coluna]);
            }
            sb.append("#");
            sb.append("\n");
        }
        for (int c = -1; c <= COLUNAS; c++) {
            sb.append("#");
        }
        return sb.toString();
    }
}
