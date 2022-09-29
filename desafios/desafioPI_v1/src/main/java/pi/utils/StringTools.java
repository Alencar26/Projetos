package pi.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StringTools {

    public static String[] toStringArray(String string) {
        return string.replace(".", "").split("");
    }
    public static String inversao(String fragmento) {
        String fragmentoInvertido = "";
        for(int i = fragmento.length() -1; i >= 0; i--) {
            fragmentoInvertido += fragmento.charAt(i);
        }
        return fragmentoInvertido;
    }

    public static boolean isPalindromo(String original, String invertido) {
        return original.equals(invertido);
    }

    public static boolean sucesso(String original, String invertido) {
        return isPalindromo(original, invertido) && MathTools.isPrimo(invertido);
    }

    public static String fragmentoString(int n, String[] pi, int intervaloFragmento) {
        String fragmentoComNove = "";
        String[] arrayFragmento = new String[intervaloFragmento];
        int contextoN = n;

        for (int i = 0; i < intervaloFragmento; i++) {
            arrayFragmento[i] = pi[contextoN];
            contextoN++;
        }

        for(String item : arrayFragmento) {
            fragmentoComNove += item;
        }
        return fragmentoComNove;
    }

    public static void buscaLinear(String path, int intervalo) throws IOException {

        String[] pi = leitor(path);
        int n = 0;
        String fragmento = "", fragmentoInvertigo = "";

        while (n != pi.length) {
            fragmento = fragmentoString(n, pi, intervalo);

            System.out.print("posição - "+ n + " | " + fragmento);
            fragmentoInvertigo = StringTools.inversao(fragmento);
            System.out.print(" - " + fragmentoInvertigo);

            if (sucesso(fragmento, fragmentoInvertigo)) {
                System.out.println(" - Sucesso! Aqui está um palindrômo que é primo e com 9 dígitos: " + fragmento);
                break;
            }

            fragmento = "";
            n++;
            System.out.print("\n");
        }
    }

    public static String[] leitor(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha="";
        linha = buffRead.readLine();
        return toStringArray(linha);
        }
}
