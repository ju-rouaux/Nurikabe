package com.l3infogrp5.nurikabe;

import com.l3infogrp5.nurikabe.Niveaux.Score.ScoreCLM;
import com.l3infogrp5.nurikabe.Niveaux.Score.ScoreChrono;
import com.l3infogrp5.nurikabe.Niveaux.Score.ScoreEndless;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Nurikabe extends Application {

    @Override
    public void start(Stage primaryStage) {

        final Text text = new Text("00:00");
        text.setLayoutX(100);
        text.setLayoutY(100);
        text.setFill(Color.BLUE);
        final Pane root = new Pane();
        root.getChildren().setAll(text);
        final Scene scene = new Scene(root, 350, 300);

        ScoreChrono score = new ScoreCLM(10, 0, text);
        score.calcul();

        System.out.println("test");
        score.aideUtilise();

        /*
         * ScoreChrono score = new ScoreEndless(40,0, text);
         * score.calcul();
         * 
         * System.out.println("test");
         * //score.aideUtilise(35);
         * score.grilleComplete();
         */

        primaryStage.setTitle("Test sur l'opacité");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}