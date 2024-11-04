package g60283.dev.meteo.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * The WeatherAPI class provides methods to fetch weather data based on city names and dates.
 * It uses external APIs to convert city names to coordinates and retrieve weather information.
 */
public class WeatherAPI {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Fetches weather data for a specified city and date.
     * First, it retrieves the geographical coordinates of the city.
     * Then, it uses these coordinates to query the weather API.
     *
     * @param city the name of the city to fetch weather data for
     * @param date the date for which to retrieve weather data (formatted as YYYY-MM-DD)
     * @return a WeatherObject containing the weather information for the specified city and date
     * @throws WeatherException if there is an error during the API call or data processing
     */
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

            // Parse response to extract latitude and longitude
            var jsonArray = objectMapper.readTree(response.body());
            if (jsonArray.isArray() && !jsonArray.isEmpty()) {
                var firstResult = jsonArray.get(0);
                String lat = firstResult.get("lat").asText();
                String lon = firstResult.get("lon").asText();
                return fetchWeatherData(lat, lon, date, city);
            } else {
                throw new WeatherException("No results found", null);
            }
        } catch (IOException | InterruptedException e) {
            throw new WeatherException("Error during API call", e);
        }
    }

    /**
     * Fetches weather data for specific coordinates and date from the weather API.
     *
     * @param lat the latitude of the location
     * @param lon the longitude of the location
     * @param date the date for which to retrieve weather data (formatted as YYYY-MM-DD)
     * @param city the name of the city, used as metadata for the WeatherObject
     * @return a WeatherObject containing the weather data for the given coordinates and date
     * @throws WeatherException if there is an error during the API call or data processing
     */
    private static WeatherObject fetchWeatherData(String lat, String lon, String date, String city) {
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

            return new WeatherObject(tempMax, tempMin, weatherCode, city);
        } catch (IOException | InterruptedException e) {
            throw new WeatherException("Error during weather data retrieval", e);
        }
    }
}
