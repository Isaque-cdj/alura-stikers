import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    private static JsonParser jsonParser;

    public static void main(String[] args) throws Exception {

        // Fazzer uma conexão HTTP buscar os tops 250 filmes
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dadod que interesssam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println("\u001b[37m" + "Título: " + "\u001b[1m" + filme.get("title") + "\u001b[1m");
            System.out.println(filme.get("image"));
            System.out.println("\u001b[37m \u001b[45m" + "Classificação: " + filme.get("imDbRating" ) + " \u001b[m" +);
            System.out.println();

        }

    }

}
