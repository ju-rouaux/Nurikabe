package com.l3infogrp5.nurikabe;
import com.l3infogrp5.nurikabe.Niveaux.Score.ScoreCLM;
import com.l3infogrp5.nurikabe.Niveaux.Score.ScoreChrono;
import com.l3infogrp5.nurikabe.Niveaux.Score.ScoreEndless;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{

    @Override
    public
    void start(Stage primaryStage){
        ScoreChrono score = new ScoreCLM(10,0);
        score.calcul();

        System.out.println("test");
        score.aideUtilise(105);
    }
/*
    @Override
    public
    void start(Stage primaryStage){
        ScoreChrono score = new ScoreEndless(40,0);
        score.calcul();

        System.out.println("test");
        score.aideUtilise(35);
        //score.grilleComplete();
    }
*/
}