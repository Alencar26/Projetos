package br.com.al3ncar.calc.view;

import javax.swing.*;
import java.awt.*;

public class Botao extends JButton {

    public Botao(String texto, Color cor) {
        setText(texto);
        setOpaque(true); // pra realmente mudar a cor
        setBackground(cor);
        setFont(new Font("courier", Font.PLAIN, 20));
        setForeground(Color.WHITE);
        //setBorderPainted(false); //tirar a borda dos botões
        setBorder(BorderFactory.createLineBorder(new Color(33,37,43)));
    }
}
