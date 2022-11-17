package jogo_cobrinha.view;

import jogo_cobrinha.controller.Gameplay;
import jogo_cobrinha.model.Cobra;
import jogo_cobrinha.model.Tabuleiro;

import java.util.Scanner;

public class GameView {

    private static boolean continua = true;
    private static Scanner sc = new Scanner(System.in);

    //FACADE
    public static void novoJogo() {
        while (continua) {
            System.out.println("=========JOGO DA COBRINHA========");
            System.out.println("(1) - Novo Jogo");
            System.out.println("(2) - Sair");
            System.out.print("R: ");
            String resposta = sc.nextLine();

            switch (resposta) {
                case "1" -> {
                    Tabuleiro tb = new Tabuleiro(10,20);
                    Cobra cobra = Cobra.getCobra(tb.posicaoInicalCobra());
                    new Gameplay(cobra, tb).executarJogo();
                }
                case "2" -> continua = false;
            }

        }
    }
}
