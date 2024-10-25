package g60283.dev.meteo;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class App extends Application {
    private Label maxTemp = new Label("max");
    private Label minTemp = new Label("min");
    private ImageView weatherIcon = new ImageView();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: lightblue;");

        // --- Création du GridPane pour le bas ---
        GridPane bottom = new GridPane();
        TextField city = new TextField();
        city.setPromptText("Enter city name");
        DatePicker date = new DatePicker();
        Button actionButton = new Button("Select");

        // Configuration des colonnes pour une disposition responsive
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(66.66);  // Plus de largeur pour la ville
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(22.23);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(11.11);

        bottom.getColumnConstraints().addAll(col1, col2, col3);
        bottom.add(city, 0, 0);
        bottom.add(date, 1, 0);
        bottom.add(actionButton, 2, 0);
        bottom.setHgap(10);
        bottom.setPadding(new Insets(10));
        bottom.setMaxWidth(Double.MAX_VALUE);
        GridPane.setHgrow(city, Priority.ALWAYS);

        // --- Création de la VBox pour afficher les températures au centre ---
        maxTemp.setFont(Font.font("Arial", 20));  // Police plus grande et plus belle
        maxTemp.setTextFill(Color.RED);           // Couleur rouge pour la température maximale
        minTemp.setFont(Font.font("Arial", 20));
        minTemp.setTextFill(Color.BLUE);          // Couleur bleue pour la température minimale

        VBox tempBox = new VBox(10, maxTemp, minTemp); // Espacement de 10px entre max et min
        tempBox.setAlignment(Pos.CENTER);         // Centrer les éléments
        HBox weatherBox = new HBox(tempBox, weatherIcon);
        tempBox.setStyle("-fx-background-color: lightblue;");
        weatherBox.setAlignment(Pos.CENTER);
        root.setCenter(weatherBox);

        // Configuration de l'action du bouton
        actionButton.setOnAction(e -> actionButton(city.getText(), date.getValue()));

        // Ajout du GridPane au bas du BorderPane
        root.setBottom(bottom);

        // Configuration de la scène et affichage de la fenêtre
        Scene scene = new Scene(root, 700, 350);
        stage.setScene(scene);
        stage.setTitle("Météo App");
        stage.show();
    }

    private void actionButton(String text, LocalDate value) {
        String cooRequest = "https://nominatim.openstreetmap.org/search.php?q=" + text + "&format=jsonv2";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(cooRequest))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Vérifier le code de statut et traiter la réponse
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                System.out.println("Réponse reçue : " + responseBody);

                // Utilisation d'ObjectMapper pour parser la réponse JSON
                ObjectMapper objectMapper = new ObjectMapper();

                // Lire la réponse JSON qui est un tableau
                var jsonArray = objectMapper.readTree(responseBody);

                if (jsonArray.isArray() && !jsonArray.isEmpty()) {
                    var firstResult = jsonArray.get(0);
                    var lat = firstResult.get("lat").asText();
                    var lon = firstResult.get("lon").asText();
                    processCoordinates(lat, lon, value);

                }
            } else {
                System.out.println("Erreur dans la requête : Code " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            // Gérer les exceptions
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void processCoordinates(String lat, String lon, LocalDate value) throws URISyntaxException {
        String meteoRequest = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current=temperature_2m&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=Europe%2FBerlin&start_date=" + value + "&end_date=" + value;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(meteoRequest))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                var json = response.body(); // Get body of http response
                var jsonNode = objectMapper.readTree(json);
                var weatherCode = jsonNode.get("daily").get("weather_code").get(0).asInt();
                var tempMin = jsonNode.get("daily")
                        .get("temperature_2m_min").get(0).asDouble();
                var tempMax = jsonNode.get("daily")
                        .get("temperature_2m_max").get(0).asDouble();
                maxTemp.setText(tempMax + "°");
                minTemp.setText(tempMin + "°");
                String iconPath = getImageForWeatherCode(weatherCode);
                System.out.println(weatherCode);
                if (iconPath != null) {
                    Image weatherImage = new Image(iconPath);  // true pour charger l'image de manière asynchrone
                    weatherIcon.setImage(weatherImage);
                    weatherIcon.setFitHeight(50);  // Taille de l'image
                    weatherIcon.setPreserveRatio(true);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getImageForWeatherCode(int weatherCode) {
        switch (weatherCode) {
            case 0, 1:
                return "http://openweathermap.org/img/wn/01d@2x.png";
            case 2:
                return "http://openweathermap.org/img/wn/02d@2x.png";
            case 3:
                return "http://openweathermap.org/img/wn/03d@2x.png";
            case 48:
                return "http://openweathermap.org/img/wn/50d@2x.png";

            case 51, 53, 55:
                return "http://openweathermap.org/img/wn/09d@2x.png";
            case 56, 57:
                return "http://openweathermap.org/img/wn/09d@2x.png";

            case 61, 63, 65:
                return "http://openweathermap.org/img/wn/10d@2x.png";
            case 66, 67:
                return "http://openweathermap.org/img/wn/10d@2x.png";

            case 71, 73, 75, 77:
                return "http://openweathermap.org/img/wn/13d@2x.png";

            case 80, 81, 82:
                return "http://openweathermap.org/img/wn/09d@2x.png";
            case 85, 86:
                return "http://openweathermap.org/img/wn/13d@2x.png";
            case 95:
                return "http://openweathermap.org/img/wn/11d@2x.png";
            case 96, 99:
                return "http://openweathermap.org/img/wn/11d@2x.png";

            default:
                return null;
        }
    }

}
