import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuadradosPerfeitos {

    public static void main(String[] args) {

        int n = Integer.parseInt(new Scanner(System.in).nextLine());

        List<Integer> quadradosPerfeitos = separaQuadradosPerfeitos(n);
        int resultado = quantidadeNecessariaParaChegarEm(n, quadradosPerfeitos);
        System.out.println(resultado);
    }

    public static List<Integer> separaQuadradosPerfeitos(int n) {
        List<Integer> quadradosPerfeitos = new ArrayList<>();
        for (int i=1; i < n; i++) {
            double raiz = Math.sqrt(i);
            if ((int) raiz == raiz) {
                quadradosPerfeitos.add(0,i);
            }
        }
        return quadradosPerfeitos;
    }

    public static int quantidadeNecessariaParaChegarEm(int valor, List<Integer> lista) {
       int contador = 0;
        for(int i = 0; i < lista.size(); i++) {
           int aux = valor;
           int contadorAux = 0;
           for(int j = i; j < lista.size(); j++) {
               while(aux - lista.get(j) >= 0) {
                   aux -= lista.get(j);
                   contadorAux++;
               }
           }
           if(aux == 0) {
               if (contadorAux < contador || contador == 0) {
                   contador = contadorAux;
               }
           }
       }
        return contador;
    }
}
