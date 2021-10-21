package S1.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is an inventory management system application. It includes a JavaFX GUI. It manages the inventory of parts and products, as well as any parts associated with products.
 *
 * FUTURE ENHANCEMENT:
 * When creating a part, adding a checkbox to see if it should be allowed to be associated with a product multiple times. Right now, you can associate a part with a product multiple times. This could possibly lead to unintended effects.
 */

public class Main extends Application {

    /**
     * Loads the stage and sets the initial scene.
     * @param primaryStage Creates the primary stage to load scenes onto
     * @throws Exception Catches error if resource loaded is null
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../view/IMS.fxml"));
        primaryStage.setTitle("IMS");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * The javadocs are located at /src/S1/javadocs
     *
     * Open JavaFX and launch program.
     * @param args Any command line arguments
     */

    public static void main(String[] args) {
        launch(args);
    }
}
