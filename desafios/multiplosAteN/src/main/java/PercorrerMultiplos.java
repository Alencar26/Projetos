
public class PercorrerMultiplos {

    public static void main(String[] args) {

        int[] elementos = {64, 137, -16, 43, 67, 81, -90, 212, 10, 75};

        int num = 76;

        for(int i = 0; i < elementos.length; i++) {
            if(elementos[i] == num) {
                System.out.println("Achei " + num + " na posicao " + i);
                break;
            } else if(i == elementos.length - 1) {
                System.out.println("Numero " + num + " nao encontrado!");
            }
        }
    }
}
