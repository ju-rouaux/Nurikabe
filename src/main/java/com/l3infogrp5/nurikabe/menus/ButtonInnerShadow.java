// Java Program to create a button and add it to the stage
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.geometry.Pos;

public class ButtonInnerShadow extends Application {

    // launch the application
    public void start(Stage s) {
        // set title for the stage
        s.setTitle("creating buttons");

        // create a button
        Button b = new Button("Jouer");

        Label label = new Label("Nurikabe");

        b.setMaxSize(400,100);

        b.setStyle("-fx-font-size:40px; -fx-font-weight:bold;-fx-background-color: #ffffff;-fx-background-radius: 50px;-fx-border-radius: 50px;-fx-border-width:4px;-fx-border-color: black;-fx-effect: innershadow( gaussian, rgba(0, 0, 0, 1), 60, 0, 0, 0);");

        label.setStyle("-fx-font-size:100px; -fx-font-weight:bold;");
        // create a stack pane
        StackPane r = new StackPane();


        r.setStyle("-fx-background-color: #bebebe;");
        // add button
        r.getChildren().add(b);
        r.getChildren().add(label);

        StackPane.setAlignment(label, Pos.TOP_CENTER);
        // create a scene
        Scene sc = new Scene(r, 1280, 720);

        // set the scene
        s.setScene(sc);

        s.show();
    }

    public static void main(String args[]) {
        // launch the application
        launch(args);
    }
}
