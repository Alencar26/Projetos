import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class JavaRGBGenerator extends JFrame {
    

    private JLabel RGBLabel;
    private JPanel panel;
    private JButton RButton, GButton, BButton;
    private int RValue, GValue, BValue;

    public enum Clicado { R_CLICADO, G_CLICADO, B_CLICADO, NADA }
    Clicado clicado;

    public JavaRGBGenerator() {
        setJanela();
        initComponents();
    }

    private void setJanela() {
        this.setTitle("RGB Generator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(null);
    }

    private void initComponents() {
        //Initialize components
        RValue = 0;
        GValue = 0;
        BValue = 0;
        clicado = Clicado.NADA;

        //config panel
        panel = new JPanel();
        panel.setBounds(0, 0, 600, 400);
        panel.setLayout(null);
        this.add(panel);

        //label
        RGBLabel = new JLabel("Cor (R, G, B)");
        RGBLabel.setFont(new Font("Arial", Font.BOLD, 24));
        RGBLabel.setBounds(100, 50, 400, 50);
        RGBLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(RGBLabel);

        //buttons
        RButton = new JButton("R");
        RButton.setBounds(100, 200, 100, 100);
        panel.add(RButton);

        RButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clicado = Clicado.R_CLICADO;
            }
            
        });

        GButton = new JButton("G");
        GButton.setBounds(250, 200, 100, 100);
        panel.add(GButton);

        GButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clicado = Clicado.G_CLICADO;
            }
            
        });

        BButton = new JButton("B");
        BButton.setBounds(400, 200, 100, 100);
        panel.add(BButton);

        BButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clicado = Clicado.B_CLICADO;
            }
            
        });

        ScrollMouseAction();
    }

    private void ScrollMouseAction() {
        this.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (clicado != Clicado.NADA) {
                    if (clicado == clicado.R_CLICADO) {
                        //almentar ou diminuir valod de R
                        RValue += e.getWheelRotation();
                        if (RValue < 0 || RValue > 255) {
                            RValue -= e.getWheelRotation();
                        }
                    } else if (clicado == Clicado.G_CLICADO) {
                        //almentar ou diminuir valod de G
                        GValue += e.getWheelRotation();
                        if (GValue < 0 || GValue > 255) {
                            GValue -= e.getWheelRotation();
                        }
                    } else {
                        //almentar ou diminuir valod de B
                        BValue += e.getWheelRotation();
                        if (BValue < 0 || BValue > 255) {
                            BValue -= e.getWheelRotation();
                        }
                    }
                }
                RGBLabel.setText("RGB: " + RValue + " " + GValue + " " + BValue);
                panel.setBackground(new Color(RValue, GValue, BValue));
            }
            
        });
    }

    public static void main(String[] args) {
        new JavaRGBGenerator().setVisible(true);
    }
}
