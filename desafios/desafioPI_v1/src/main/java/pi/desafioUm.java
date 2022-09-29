package pi;

import pi.utils.StringTools;
import java.io.IOException;


public class desafioUm {

    public static void main(String[] args) throws IOException {

        final String PATH_PI = System.getProperty("user.dir") + "/src/main/resources/pi_1M_casas_decimais.txt";
        StringTools.buscaLinear(PATH_PI, 9);
    }
}
