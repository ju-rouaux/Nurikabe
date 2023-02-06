package com.l3infogrp5.nurikabe.Niveaux.Score;
import java.lang.Thread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
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

    
    /* 
    *Méthode de calcul du chrono pour le mode Contre la montre, ce chrono s'incrémente. 
     */
    public void calcul(){
        KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {
            
            sec++;

            if(sec == 60){
                min++;
                sec = 0;

            }
            
            System.out.println(min+":"+sec);
                

        });

        System.out.println(min+":"+sec);
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /*
     * @{inheritedDoc}
     */
    @Override
    public void aideUtilise(int aide) {

        if(sec+aide > 60){
            min++;
            sec += (aide-60);
        }

    }  
}


