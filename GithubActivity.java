import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GithubActivity {

    public static void main(String[] args) {
        String username = "loft972"; // Change pour un autre utilisateur GitHub si besoin
        String url = "https://api.github.com/users/" + username;

        // Créer un client HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Construire la requête GET
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github.v3+json")
                .GET()
                .build();

        try {
            // Envoyer la requête et récupérer la réponse
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Vérifier si la requête est réussie
            if (response.statusCode() == 200) {
                String json = response.body();

                // Extraire les données en manipulant la chaîne JSON
                String name = extractJsonValue(json, "name");
                String bio = extractJsonValue(json, "bio");
                String publicRepos = extractJsonValue(json, "public_repos");
                String followers = extractJsonValue(json, "followers");
                String profileUrl = extractJsonValue(json, "html_url");

                // Afficher les informations
                System.out.println("Nom : " + name);
                System.out.println("Bio : " + bio);
                System.out.println("Public Repos : " + publicRepos);
                System.out.println("Followers : " + followers);
                System.out.println("GitHub Profile : " + profileUrl);

            } else {
                System.out.println("Erreur : " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Fonction pour extraire une valeur d'une chaîne JSON
    private static String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) return "N/A";

        startIndex += searchKey.length();

        // Trouver la fin de la valeur
        int endIndex;
        if (json.charAt(startIndex) == '"') { // C'est une chaîne
            startIndex++;
            endIndex = json.indexOf('"', startIndex);
        } else { // C'est un nombre
            endIndex = json.indexOf(',', startIndex);
            if (endIndex == -1) endIndex = json.indexOf('}', startIndex); // Dernière valeur du JSON
        }

        return json.substring(startIndex, endIndex).trim();
    }
}
