import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //FORMA QUE EU DESENVOLVI
        Scanner scanner = new Scanner(System.in);
        int resultado = fatorialDesejado(scanner.nextInt());
        System.out.println(resultado);
    }

    static int[] preCalcularFatorial(int x) {
        int[] v;
        int f = x, c = 1, i = 0;
        if(x % 2 != 0) v = new int[(x / 2)+1];
        else v = new int[x / 2];
        while (x > 1) {
            if (c > 4) c = 1;
            if (c == 1) {f=x; f*= (x-1);}
            if (c == 2) {v[i]= f / (x-1); i++;}
            if (c == 4) {v[i] = x; i++;}
            x--;
            c++;
        }
        if (c - 1 < 3) v[i]=f;
        else v[i]=x;
        return v;
    }

    static int calcularFatorial(int[] vetor) {
        for (int i = 0; i < vetor.length - 1; i++) {
            if (i % 2 == 0){
                vetor[i+1] = vetor[i] + vetor[i+1];
            } else {
                vetor[i+1] = vetor[i] - vetor[i+1];
            }
            vetor[i] = 0;
        }
        return vetor[vetor.length -1];
    }

    static int fatorialDesejado(int num) {
        int[] vetor = preCalcularFatorial(num);
        return calcularFatorial(vetor);
    }
}