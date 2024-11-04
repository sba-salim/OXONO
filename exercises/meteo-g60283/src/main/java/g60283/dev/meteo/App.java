package g60283.dev.meteo;

import g60283.dev.meteo.controller.WeatherController;
import g60283.dev.meteo.model.WeatherFacade;
import g60283.dev.meteo.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // Initialisation du modèle et de la vue
        WeatherFacade model = new WeatherFacade();
        MainView mainView = new MainView();

        // Initialisation du contrôleur avec le modèle et la vue
        WeatherController controller = new WeatherController(model, mainView);

        // Liaison du contrôleur à la vue
        mainView.setController(controller);

        // Configuration de la scène et de la fenêtre principale
        Scene scene = new Scene(mainView, 700, 350);
        stage.setScene(scene);
        stage.setTitle("Météo App");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
