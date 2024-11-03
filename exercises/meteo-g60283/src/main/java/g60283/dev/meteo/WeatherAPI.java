package g60283.dev.meteo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class WeatherAPI {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected static WeatherObject fetch(String city, String date) {
        String cooRequest = "https://nominatim.openstreetmap.org/search.php?q=" + city + "&format=jsonv2";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(cooRequest))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WeatherException("Failed to fetch coordinates", null);
            }

            // Traitement de la réponse pour obtenir latitude et longitude
            var jsonArray = objectMapper.readTree(response.body());
            if (jsonArray.isArray() && !jsonArray.isEmpty()) {
                var firstResult = jsonArray.get(0);
                String lat = firstResult.get("lat").asText();
                String lon = firstResult.get("lon").asText();
                return fetchWeatherData(lat, lon, date);
            } else {
                throw new WeatherException("No results found", null);
            }
        } catch (IOException | InterruptedException e) {
            throw new WeatherException("Error during API call", e);
        }
    }

    private static WeatherObject fetchWeatherData(String lat, String lon, String date) {
        String meteoRequest = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon +
                "&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=Europe%2FBerlin&start_date=" + date + "&end_date=" + date;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(meteoRequest))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new WeatherException("Failed to fetch weather data", null);
            }

            var jsonNode = objectMapper.readTree(response.body());
            int weatherCode = jsonNode.get("daily").get("weather_code").get(0).asInt();
            double tempMin = jsonNode.get("daily").get("temperature_2m_min").get(0).asDouble();
            double tempMax = jsonNode.get("daily").get("temperature_2m_max").get(0).asDouble();

            return new WeatherObject(tempMax, tempMin, weatherCode);
        } catch (IOException | InterruptedException e) {
            throw new WeatherException("Error during weather data retrieval", e);
        }
    }
}
