import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {

        // Fazzer uma conexão HTTP buscar os tops 250 filmes

        // String url = "https://imdb-api.com/en/API/Top250Movies/k_8pw5xuzg";
        String url = "https://api.nasa.gov/planetary/apod?api_key=PemP5z34quUjhXhIPhar2I4zINAn08xBh0w5dzq7&start_date=2022-06-12&end_date=2022-06-14";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dadod que interesssam (título, poster, classificação)

        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        var geradora = new GeradoraDeFigurinhas();
        for (int i = 0; i < 10; i++) {

            Map<String, String> filme = listaDeFilmes.get(i);

            String urlImagem = // filme.get("image")
                    filme.get("url")
                            .replaceAll("(@+)(.*).jpg$", "$1.jpg");

            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "output/" + titulo + ".png";

            geradora.cria(inputStream, nomeArquivo);

            System.out.println(filme.get("title"));
            System.out.println();

        }
    }
}
