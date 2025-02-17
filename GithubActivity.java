import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class GithubActivity {

    public static List<String> events = new ArrayList<>();

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
                getEvents(json);
                //for(String event : events){
                    int actorStart = events.get(0).indexOf("{", events.get(0).indexOf("\"actor\""));
                    //if(actorStart == -1 ) break;

                    int actorEnd = findMatchingBracket(json, actorStart);
                    //if (actorEnd == -1) break;

                    // Extraction de l'objet actor (sous forme de chaîne JSON)
                    String actorJson = json.substring(actorStart, actorEnd + 1);
                //}

                createActorInfo(actorJson);
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

    private static void createActorInfo(String json){
        // Extraire les données en manipulant la chaîne JSON
        String id = extractJsonValue(json, "id");
        String login = extractJsonValue(json, "login");
        String displayLogin = extractJsonValue(json, "display_login");
        String gravatarId = extractJsonValue(json, "gravatar_id");
        String url1 = extractJsonValue(json, "url");
        String avatarUrl = extractJsonValue(json, "avatar_url");
        // Afficher les informations
        System.out.println(new Actor(Integer.parseInt(id), login, displayLogin, gravatarId, url1, avatarUrl));
        //return ;
    }

    private static int findMatchingBracket(String json, int startIndex){
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

    private static void getEvents(String json){
        int index = 0;
        while(index < json.length()){
            int eventStart = json.indexOf("{",index);
            if(eventStart == -1) break;

            int eventEnd = findMatchingBracket(json, eventStart);
            if(eventEnd == -1 )break;

            String eventJson = json.substring(eventStart, eventEnd+1);
            events.add(eventJson);
            index = eventEnd +1;
        }

        // Affichage du nombre et des objets actor extraits
        System.out.println("Nombre d'events trouves : " + events.size());
    }

}
