package jogo_cobrinha.model;

import java.util.LinkedList;

public class Cobra {
    private LinkedList<Bloco> comprimentoCobra = new LinkedList<>();
    private Bloco cabecaCobra;
    private static Cobra cobra;

    private Cobra() {}
    private Cobra(Bloco posicaoInicial) {
        this.cabecaCobra = posicaoInicial;
        comprimentoCobra.add(this.cabecaCobra);
        cabecaCobra.setTipo(TipoBloco.COBRA);
    }
    //SINGLETON
    public static Cobra getCobra(Bloco posicaoInicial) {
        if (cobra == null) {
            cobra = new Cobra(posicaoInicial);
        }
        return cobra;
    }

    public void mover(Bloco proximoBloco) {
        Bloco rabo = comprimentoCobra.removeLast();
        rabo.setTipo(TipoBloco.VAZIO);

        cabecaCobra = proximoBloco;
        cabecaCobra.setTipo(TipoBloco.COBRA);
        comprimentoCobra.addFirst(cabecaCobra);
    }

    public void crescer() {
        comprimentoCobra.add(cabecaCobra);
    }

    public boolean impacto(Bloco proximoBloco) {
        for (Bloco bloco : comprimentoCobra) {
            if (bloco == proximoBloco) {
                return true;
            }
        }
        return false;
    }
    public Bloco getCabecaCobra() {
        return cabecaCobra;
    }

    public void resetCobra() {
        while (comprimentoCobra.size() != 1) {
            comprimentoCobra.removeLast();
        }
    }
}
