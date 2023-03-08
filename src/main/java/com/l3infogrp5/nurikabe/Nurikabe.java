package com.l3infogrp5.nurikabe;

import java.io.IOException;

import com.l3infogrp5.nurikabe.niveau.score.ScoreZen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

public class Nurikabe extends Application {

    double etoiles = 5.0;

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader;
        Rating score;
        
        Pane root = new Pane();
        
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/score_etoile.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final ScoreZen score = new ScoreZen(etoiles);

        Label label = new Label("Your rating: " + etoiles);
        label.setTextFill(Color.BLACK);

        HBox hBox = new HBox(score, label);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(hBox);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setTitle("Test score etoile");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}