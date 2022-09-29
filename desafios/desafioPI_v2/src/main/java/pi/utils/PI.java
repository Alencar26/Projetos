package pi.utils;

import pi.PalindromoPrimo;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PI {

    public static String inversao(String fragmento) {
        String fragmentoInvertido = "";
        for(int i = fragmento.length() -1; i >= 0; i--) {
            fragmentoInvertido += fragmento.charAt(i);
        }
        return fragmentoInvertido;
    }

    public static String fragmentoString(int n, String pi, int intervaloFragmento) {
        String fragmentoComNove = "";
        String[] arrayFragmento = new String[intervaloFragmento];
        int contextoN = n;

        for (int i = 0; i < intervaloFragmento; i++) {
            arrayFragmento[i] = pi.charAt(contextoN)+"";
            contextoN++;
        }

        if(contextoN == pi.length()) contextoN = 0;

        for(String item : arrayFragmento) {
            fragmentoComNove += item;
        }
        return fragmentoComNove;
    }

    public static void buscaLinear(long posicaoInical,int quantidade, int intervalo) throws IOException, InterruptedException {

        int n = 0;
        long inicio = posicaoInical, contador = posicaoInical;
        String fragmento = "", fragmentoInvertigo = "";
        boolean continua = true;

        String pi = "";
        try {
            pi = PIDeliveryService.getPI(inicio, quantidade);
        } catch (Exception e) {
            gravarPosicao(""+contador);
            System.out.println("\nHouve um proble: " + e.getMessage());
            Thread.sleep(10000);
            buscaLinear(contador, quantidade, intervalo);
        }

        while (continua) {

            try {
                fragmento = fragmentoString(n, pi, intervalo);
            } catch (Exception e) {
                gravarPosicao(""+contador);
                System.out.println("\nHouve um proble: " + e.getMessage());
                Thread.sleep(10000);
                buscaLinear(contador, quantidade, intervalo);
            }

            System.out.print("posição - "+ contador + " | " + fragmento);
            fragmentoInvertigo = inversao(fragmento);
            System.out.print(" - " + fragmentoInvertigo);

            if (Validacao.sucesso(fragmento, fragmentoInvertigo)) {
                System.out.println(" - Sucesso! Aqui está um palindrômo que é primo e com 9 dígitos: " + fragmento);
                continua = false;
                gravarPosicao(contador+" - VOCÊ ACHOU");
                break;
            }

            if (n == (quantidade - intervalo)) {
                inicio += (quantidade - intervalo) + 1;
                try {
                    pi = PIDeliveryService.getPI(inicio, quantidade);
                } catch (Exception e) {
                    gravarPosicao(""+contador);
                    System.out.println("\nHouve um proble: " + e.getMessage());
                    Thread.sleep(10000);
                    buscaLinear(contador, quantidade, intervalo);
                }
                n = -1;
            }

            fragmento = "";
            n++;
            contador++;
            System.out.print("\n");
        }
    }

    public static void buscaLinearComArquivo(String path, int intervalo) throws IOException {

        String pi = leitor(path);
        int n = 0;
        String fragmento = "", fragmentoInvertigo = "";

        while (n != pi.length()) {
            fragmento = fragmentoString(n, pi, intervalo);

            System.out.print("posição - "+ n + " | " + fragmento);
            fragmentoInvertigo = inversao(fragmento);
            System.out.print(" - " + fragmentoInvertigo);

            if (Validacao.sucesso(fragmento, fragmentoInvertigo)) {
                System.out.println(" - Sucesso! Aqui está um palindrômo que é primo e com 21 dígitos: " + fragmento);
                break;
            }

            fragmento = "";
            n++;
            System.out.print("\n");
        }
    }

    public static String leitor(String path) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha="";
        linha = buffRead.readLine();
        return linha;
    }

    public static Long lerUltimaPosicao(String path) throws IOException {
        List<String> linhasArquivo = Files.readAllLines(new File(path).toPath());
        return Long.parseLong(linhasArquivo.get(linhasArquivo.size() - 1));
    }

    public static void gravarPosicao(String str) throws IOException {
        final String PATH = System.getProperty("user.dir") + "/src/main/resources/recuperacao.txt";
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(PATH, true));
        buffWrite.append(str).append("\n");
        buffWrite.close();
    }
}
