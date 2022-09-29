package pi.utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PIDeliveryService {

    public static String getPI(long posicao, int fim) throws IOException, InterruptedException {


        final String URL_PI1T = "https://api.pi.delivery/v1/pi?start="+ posicao +"&numberOfDigits="+ fim +"";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(URL_PI1T))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body().replace("{\"content\":\"", "").replace("\"}", "");
    }
}
