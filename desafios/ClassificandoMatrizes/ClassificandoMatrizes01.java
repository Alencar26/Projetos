import java.util.Scanner;

public class ClassificandoMatrizes01 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int i;
        int N = input.nextInt();
        int [] nums = new int[N];
        for (i = 0; i < N; i++)
        {
            nums[i] = input.nextInt();
        }

        ordenar(nums);
        for(int num : nums) {
            System.out.println(num);
        }
    }

    public static void ordenar(int[] vetor) {

        int tamanho = vetor.length;
        int temporario;
        for (int i = 0; i < tamanho - 1; i++) {
            for (int j = 0; j < tamanho -1; j++) {
                if (vetor[j] % 2 != 0) {
                    temporario = vetor[j + 1];
                    vetor[j + 1] = vetor[j];
                    vetor[j] = temporario;
                }
            }
        }
    }
}
