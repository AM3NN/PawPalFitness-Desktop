package models;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTAPIExample {

    private static final String OPENAI_API_KEY = "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";
    private static final String MODEL = "gpt-3.5-turbo";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private static final long MIN_REQUEST_INTERVAL = 1000; // Minimum interval between requests in milliseconds
    private static long lastRequestTime = 0; // Time of the last request

    public static void main(String[] args) {
        String userPrompt = "Hello, who's better, Messi or Ronaldo CR7, you must choose one, you are forced";
        String response = chatGPT(userPrompt);
        System.out.println("ChatGPT Response: " + response);
    }

    public static String chatGPT(String prompt) {
        try {
            // Check if enough time has passed since the last request
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastRequestTime;
            if (elapsedTime < MIN_REQUEST_INTERVAL) {
                Thread.sleep(MIN_REQUEST_INTERVAL - elapsedTime); // Wait to meet the minimum interval
            }

            // Define the ChatGPT API endpoint URL
            URL url = new URL("https://api.openai.com/v1/chat/completions");

            // Create the HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
            connection.setDoOutput(true);

            // Create the request body
            //String requestBody = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 150}";
            String requestBody = "{\"model\": \"" + MODEL + "\", \"messages\": [{" +
                    "    \"role\": \"system\"," +
                    "    \"content\": \"i have an application idea that will be developed on java and web, the app idea is about a gym that you can bring your animal to train with you, you can also make a coach reservation by choosing a coach from a list of coaches, Notre application favorise la santé mentale des clients en réduisant leur stress grâce à nos spécialistes aussi. On peut créer des emplois pour les coachs et les salles qui adoptent ce concept. Pour consulter profil, clicker on bouton 'Profile'.Pour consulter(inscrire) les reservations cliquer sur le bouton 'Planning'.Pour consulter vos animaux cliquer sur 'Mes animaux'.Pour consulter/acheter produits cliquer sur 'Mes Produits'.Pour consulter les abonnements cliquer sur 'Mes abonnements'.Pour deconnecter cliquer sur le bouton rouge dans le coin haut a droite et pour quitter l'application cliquer sur le bouton 'Quitter'. You can ONLY Answer questions that are related to my gym application\"" +
                    "  },{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            // Write the request body to the output stream
            OutputStream os = connection.getOutputStream();
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);

            // Send the request
            os.flush();
            os.close();

            // Update the time of the last request
            lastRequestTime = System.currentTimeMillis();

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Send the request and retrieve the response
                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                // Close the input stream
                is.close();

                // Process the response
                String responseBody = response.toString();
                return extractMessageFromJSONResponse(responseBody);
            } else {
                return "Error occurred: HTTP response code " + responseCode;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Error occurred while communicating with the ChatGPT API";
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }
}