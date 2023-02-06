package com.l3infogrp5.nurikabe.Niveaux.Score;
import java.lang.Thread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;

/**
 * Implémentation du calcul du score pour le mode sans fin
 * @author Antoine Couapel
 * @version 1.0
 */
public class ScoreEndless extends ScoreChrono{ 

    public Timeline timeline;
    public ScoreEndless(int sec, int min) {
        super(sec,min); 
    }

    public void calcul()
    {
        KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {
            
            sec--;

            if(sec < 0){
                min--;
                sec = 59;

            }
            
            if(min <= 0 && sec <= 0){
                calcStop();
            }


            System.out.println(min+":"+sec);
                

        });

        System.out.println(min+":"+sec);
        timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void calcStop() {
        timeline.stop();
    }

    @Override
    public void aideUtilise(int aide) {

        if(sec-aide < 0){
            min --;
            sec -= aide-60;
        }
        else if(min == 0 && sec-aide <0){
            calcStop();
        }
        else{
            sec -= aide;
        }
        


    }

    public void grilleComplete(){

        sec +=30;

    }

}