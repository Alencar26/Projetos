import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class RGBGenerator extends JFrame {
  
  // private JButton button;
  private JPanel panel;
  private JLabel RGBlabel, R, G, B;
  private Color panelColor = Color.lightGray;
  private JTextField RValue, GValue, BValue;

  public RGBGenerator() {
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

  private void setPanel() {
    this.panel = new JPanel();
    this.panel.setLayout(null);
    this.panel.setBounds(0, 0,  600, 400);
    this.panel.setBackground(panelColor);
    this.getContentPane().add(panel);
  }

  private void setRGBLabel(int r, int g, int b) {
    this.RGBlabel = new JLabel("RGB: " + r + " " + g + " " + b);
    this.RGBlabel.setFont(new Font("Arial", Font.BOLD, 24));
    this.RGBlabel.setBounds(170, 50, 1000, 25);
    this.panel.add(this.RGBlabel);
  }

  private void setRValue() {
    this.RValue = new JTextField();
    this.RValue.setFont(new Font("Arial", Font.BOLD, 16));
    this.RValue.setBounds(170, 10, 100, 25);
    this.panel.add(this.RValue);
  }

  private void initComponents() {
    setPanel();
    setRGBLabel(255,255,255);
    setRValue();
  }

  public static void main(String[] args) { 
    new RGBGenerator();
  }
}
