package com.l3infogrp5.nurikabe;

import java.io.IOException;

import com.l3infogrp5.nurikabe.niveau.score.ScoreZen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Nurikabe extends Application {

    FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) {

        
        Pane root = new Pane();
        
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/score_etoile.fxml"));
        loader.setController(this);
        try {
            root = loader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        final Scene scene = new Scene(root, 800, 800);

        double etoiles = 5.0;

        final ScoreZen score = new ScoreZen(etoiles);
        System.out.println(etoiles);
        score.get_Pane();
        score.aideUtilise();
        score.aideUtilise();
        score.aideUtilise();
        score.aideUtilise();
        score.get_Pane();

        primaryStage.setTitle("Test score etoile");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}