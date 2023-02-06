// Java program to create a Circle
// and add InnerShadow effect to it
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.effect.*;
import java.io.*;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Group;

public class Inner_shadow_1 extends Application {

    // launch the application
    public void start(Stage stage) throws Exception
    {

        // set title for the stage
        stage.setTitle("Inner_shadow example");

        // create a circle
        Circle circle = new Circle(50.0f, 50.0f, 50.0f);

        // set fill for circle
        circle.setFill(Color.BLUE);

        // translate to a position
        circle.setTranslateX(50.0f);
        circle.setTranslateY(50.0f);

        // create a sepia_tone effect
        InnerShadow sepia_tone = new InnerShadow(10, Color.RED);

        // set effect
        circle.setEffect(sepia_tone);

        // create a Group
        Group group = new Group(circle);

        // create a scene
        Scene scene = new Scene(group, 200, 200);

        // set the scene
        stage.setScene(scene);

        stage.show();
    }

    // Main Method
    public static void main(String args[])
    {

        // launch the application
        launch(args);
    }
}
