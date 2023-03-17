package com.l3infogrp5.nurikabe;

import com.l3infogrp5.nurikabe.niveau.score.ScoreZen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Nurikabe extends Application {

    @Override
    public void start(Stage primaryStage) {
        final Pane root = new Pane();
        final Scene scene = new Scene(root, 350, 300);
    
        ScoreZen score = new ScoreZen(5);
        score.aideUtilise();
        Pane scorePane = score.get_Pane();
        score.aideUtilise();
        score.aideUtilise();
        score.aideUtilise();
        score.aideUtilise();
        
        root.getChildren().add(scorePane);

        primaryStage.setTitle("Test sur l'opacité");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}