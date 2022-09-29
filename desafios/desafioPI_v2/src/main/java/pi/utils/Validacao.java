package pi.utils;

public class Validacao {

    public static boolean isPalindromo(String original, String invertido) {
        return original.equals(invertido);
    }

    public static boolean sucesso(String original, String invertido) {
        return isPalindromo(original, invertido) && Validacao.isPrimo(invertido);
    }

    public static boolean isPrimo(String palindromo) {
        //boolean isPrimo = false;
        long v = Long.parseLong(palindromo);
        for (int i = 2; i < v; i++) {
            if (v % i == 0) {
                return false;
            }
        }
        return true;
    }
}
