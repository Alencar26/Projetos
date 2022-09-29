package pi;

import pi.utils.PI;
import pi.utils.PIDeliveryService;
import pi.utils.Validacao;

import java.io.IOException;

public class PalindromoPrimo {

    public static void main(String[] args) throws IOException, InterruptedException {

        //final String PATH_PI = System.getProperty("user.dir") + "/src/main/resources/pi-billion.txt";
        //PI.buscaLinearComArquivo(PATH_PI, 21);

//        thread(1540561727L, 1000, 21).start();
//        thread(2051255283L, 1000, 21).start();
//        thread(3049544668L, 1000, 21).start();
//        thread(4051666165L, 1000, 21).start();
//        thread(5049454776L, 1000, 21).start();
//        thread(6025413616L, 1000, 21).start();

        final String PATH = System.getProperty("user.dir") + "/src/main/resources/recuperacao.txt";
        try {
            PI.buscaLinear(PI.lerUltimaPosicao(PATH), 1000, 21);
        } catch (Exception e) {
            System.out.println("\nHouve um proble: " + e.getMessage());
            Thread.sleep(10000);
            PI.buscaLinear(PI.lerUltimaPosicao(PATH), 1000, 21);
        }
    }

    public static Thread thread(long posicao, int quantidade, int intervalo) {
        return new Thread(() -> {
            try {
                PI.buscaLinear(posicao,quantidade,intervalo);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
