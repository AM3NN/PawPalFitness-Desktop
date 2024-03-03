package Services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DogImageService {

    private static final String API_URL = "https://dog.ceo/api/breeds/image/random";

    public static String getRandomDogImage() {
        try {
            // Create URL object
            URL url = new URL(API_URL);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Get response code
            int responseCode = connection.getResponseCode();

            // If response code is 200 (OK), read response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Get input stream from connection
                InputStream inputStream = connection.getInputStream();

                // Use Scanner to read response as string
                Scanner scanner = new Scanner(inputStream);
                StringBuilder response = new StringBuilder();
                while (scanner.hasNextLine()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();

                // Parse JSON response
                JSONParser parser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) parser.parse(response.toString());

                // Extract dog image URL
                return jsonResponse.get("message").toString();
            } else {
                System.out.println("Failed to fetch dog image. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
