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

               parseObject(json,0);


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

    public static void parseObject(String json, int indent) {
        json = json.trim();
        // Si l'objet commence et se termine par des accolades, on les enlève
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
        }

        int index = 0;
        while (index < json.length()) {
            // Passer les espaces ou retours à la ligne
            while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
                index++;
            }
            if (index >= json.length()) break;

            // Attendre une clé (commençant par ")
            if (json.charAt(index) == '"') {
                int keyStart = index + 1;
                int keyEnd = json.indexOf('"', keyStart);
                String key = json.substring(keyStart, keyEnd);
                index = keyEnd + 1;

                // Passer jusqu'au deux-points ':'
                while (index < json.length() && json.charAt(index) != ':') {
                    index++;
                }
                index++; // Passer le deux-points
                while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
                    index++;
                }
                if (index >= json.length()) break;

                char c = json.charAt(index);
                if (c == '{') {
                    // Valeur est un objet
                    int objEnd = findMatchingBracket(json, index);
                    String value = json.substring(index, objEnd + 1);
                    printIndent(indent);
                    System.out.println(key + " : ");
                    parseObject(value, indent + 2);
                    index = objEnd + 1;
                } else if (c == '[') {
                    // Valeur est un tableau
                    int arrEnd = findMatchingSquareBracket(json, index);
                    String value = json.substring(index, arrEnd + 1);
                    printIndent(indent);
                    System.out.println(key + " : " + value);
                    index = arrEnd + 1;
                } else if (c == '"') {
                    // Valeur est une chaîne de caractères
                    int valStart = index + 1;
                    int valEnd = json.indexOf('"', valStart);
                    String value = json.substring(valStart, valEnd);
                    printIndent(indent);
                    System.out.println(key + " : " + value);
                    index = valEnd + 1;
                } else {
                    // Valeur est un nombre, un booléen ou null
                    int valStart = index;
                    while (index < json.length() && json.charAt(index) != ',' && json.charAt(index) != '}') {
                        index++;
                    }
                    String value = json.substring(valStart, index).trim();
                    printIndent(indent);
                    System.out.println(key + " : " + value);
                }
                // Passer la virgule qui sépare les paires, le cas échéant
                while (index < json.length() && (json.charAt(index) == ',' || Character.isWhitespace(json.charAt(index)))) {
                    index++;
                }
            } else {
                index++;
            }
        }
    }

    // Affiche des espaces pour l'indentation
    private static void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }
    }

    // Retourne l'indice de la parenthèse fermante ']' correspondant à l'ouverture à startIndex
    private static int findMatchingSquareBracket(String json, int startIndex) {
        int count = 0;
        for (int i = startIndex; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '[') {
                count++;
            } else if (c == ']') {
                count--;
                if (count == 0) {
                    return i;
                }
            }
        }
        return -1;
    }
}
