package jogo_cobrinha.controller;

import jogo_cobrinha.exception.PosicaoException;
import jogo_cobrinha.model.Bloco;
import jogo_cobrinha.model.Cobra;
import jogo_cobrinha.model.Tabuleiro;
import jogo_cobrinha.model.TipoBloco;

import java.util.Scanner;

public class Gameplay {

    private static final int DIREITA = 1, ESQUERDA = -1, CIMA = 2, BAIXO = -2;
    private Cobra cobra;
    private Tabuleiro tabuleiro;
    private int direcao;
    private boolean fimDeJogo;
    private Scanner sc = new Scanner(System.in);

    public Gameplay(Cobra cobra, Tabuleiro tabuleiro) {
        this.cobra = cobra;
        this.tabuleiro = tabuleiro;
    }

    public void executarJogo() {
        tabuleiro.sortearFruta();
        tabuleiro.posicaoInicalCobra();


        while (!fimDeJogo) {
            System.out.println(tabuleiro);
            System.out.println("w/a/s/d");
            System.out.print("Direção: ");
            switch (sc.nextLine()) {
                case "sair" -> fimDeJogo = true;
                case "w" -> direcao = CIMA;
                case "s" -> direcao = BAIXO;
                case "a" -> direcao = ESQUERDA;
                case "d" -> direcao = DIREITA;
            }
            try {
                Bloco proxBloco = proximoBloco(cobra.getCabecaCobra());

                if (cobra.impacto(proxBloco))
                    throw new PosicaoException();

                if (proxBloco.getTipo() == TipoBloco.FRUTA) {
                    cobra.crescer();
                    cobra.mover(proxBloco);
                    tabuleiro.sortearFruta();
                } else {
                    cobra.mover(proxBloco);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Você bateu na parede. Fim de Jogo");
                tabuleiro.posicaoInicalCobra();
                cobra.resetCobra();
                fimDeJogo = true;
            } catch (PosicaoException e) {
                System.out.println("Você bateu no próprio corpo. Fim de Jogo");
                tabuleiro.posicaoInicalCobra();
                cobra.resetCobra();
                fimDeJogo = true;
            }
        }
    }

    public Bloco proximoBloco(Bloco blocoAtual) {
        int linha = blocoAtual.getLinha();
        int coluna = blocoAtual.getColuna();

        switch (direcao) {
            case DIREITA -> coluna++;
            case ESQUERDA -> coluna--;
            case CIMA -> linha--;
            case BAIXO -> linha++;
        }
        return tabuleiro.getTabuleiro()[linha][coluna];
    }
}
