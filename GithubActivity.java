import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GithubActivity {

    public static void main(String[] args) {

        String url = "https://api.github.com/users/loft972/events";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/vnd.github.v3+json")
                .GET()
                .build();

        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            if (response.statusCode() == 200) {
                String json = response.body();
                List<String> events = new ArrayList<>();

                int index = 0;
                while(index < json.length()){
                    int eventStart = json.indexOf("{",index);
                    if(eventStart == -1) break;

                    int eventEnd = findMathcingBracket(json, eventStart);
                    if(eventEnd == -1 )break;

                    String eventJson = json.substring(eventStart, eventEnd+1);
                    events.add(eventJson);
                    index = eventEnd +1;
                }

                // Affichage du nombre et des objets actor extraits
                System.out.println("Nombre d'objets actor trouvés : " + events.size());
                for (int i = 0; i < events.size(); i++) {
                    System.out.println("events " + (i + 1) + " : " + events.get(i));
                }

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

    private static int findMathcingBracket(String json, int startIndex){
        int count = 0;
        for(int i= startIndex; i<json.length();i++){
            if(json.charAt(i) == '{'){
                count++;
            } else if(json.charAt(i) == '}'){
                count--;
                if(count == 0){
                    return i;
                }
            }
        }
        return -1;
    }
}
