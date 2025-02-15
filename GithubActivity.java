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
                System.out.println(events.get(0).indexOf("\"id\""));


            } else {
                System.out.println("Erreur : " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void parseObject(String json, int i) {
        int index = json.indexOf("\"id\"");
        System.out.println(index);

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
