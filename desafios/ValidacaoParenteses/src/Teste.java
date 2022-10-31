import java.util.Scanner;

public class Teste {

    public static void main(String[] args) {

        Scanner scanner= new Scanner(System.in);
        boolean caracter = ehValido(scanner.nextLine());

        System.out.println(caracter);
    }

    public static boolean ehValido(String s) {
        switch(s.charAt(0)) {
            case '{':
                return s.charAt(s.length() - 1) == '}';
            case '[':
                return s.charAt(s.length() - 1) == ']';
            case '(':
                return s.charAt(s.length() - 1) == ')';
            default:
                return false;
        }
    }
}
