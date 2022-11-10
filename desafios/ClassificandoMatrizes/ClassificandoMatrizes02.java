import java.util.Scanner;

public class ClassificandoMatrizes02 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int i;
        int N = input.nextInt();
        int [] nums = new int[N];
        for (i = 0; i < N; i++)
        {
            nums[i] = input.nextInt();
        }

        ordenar(nums, N);
        for(int num : nums) {
            System.out.println(num);
        }
    }

    public static void ordenar(int[] vetor, int tamanho) {

        int temporario, posicaoAtual = 0;
        for (int i = 0; i < tamanho; i++) {
            if (vetor[i] % 2 == 0) {
                temporario = vetor[posicaoAtual];
                vetor[posicaoAtual] = vetor[i];
                vetor[i] = temporario;
                posicaoAtual++;
            }
        }
    }
}