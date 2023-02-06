// Java Program to create a button and add it to the stage
package com.l3infogrp5.nurikabe.menus;


import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ButtonInnerShadow extends Application {
    /*
    // launch the application
    public void start(Stage s) {
        // set title for the stage
        s.setTitle("creating buttons");
        InnerShadow is= new InnerShadow();
        //is.setRadius(50);

        // create a button
        Button b = new Button("Jouer");

        b.setMaxSize(400,100);
        b.setStyle("-fx-border-color: #000000; -fx-border-radius: 50px; -fx-background-color:#ffffff; -fx-font-size:40px; -fx-font-weight:bold;");
        b.setEffect(is);
        // create a stack pane
        StackPane r = new StackPane();

        // add button
        r.getChildren().add(b);

        // create a scene
        Scene sc = new Scene(r, 1280, 720);

        // set the scene
        s.setScene(sc);

        s.show();
    }*/

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../FXML/menu_regles.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Modes de Jeux");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String args[]) {
        // launch the application
        launch(args);
    }
}
