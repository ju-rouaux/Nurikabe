package com.l3infogrp5.nurikabe.Niveaux.Score;
import java.lang.Thread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Implémentation du calcul d'un score pour le mode Contre La Montre
 * @author Antoine Couapel, Killian Rattier
 * @version 1.0
 */

public class ScoreCLM extends ScoreChrono {


    public ScoreCLM(int sec, int min) {
        super(sec, min);
    }

    public void calcul(){


        KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {
            //Thread.sleep(1000);
            
            sec++;

            if(sec == 60){
                min++;
                sec = 0;

            }
            
            System.out.println(min+":"+sec);
                

        });

        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Override
    public void aideUtilise() {

        sec += 20;
    }  

    public static void main(String[] args) {
        ScoreChrono score = new ScoreCLM(0,0);
        try {
            score.calcul();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        score.aideUtilise();
    }
}


