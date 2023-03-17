package com.l3infogrp5.nurikabe.niveau.score;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Implémentation du calcul du score pour le mode sans fin
 * 
 * @author Antoine Couapel
 * @version 1.0
 */
public class ScoreEndless extends ScoreChrono {

    public Timeline timeline;

    public ScoreEndless(int sec, int min, Text text) {
        super(sec, min, text);
    }

    /**
     * Méthode de calcul pour la décrémentation du chrono
     */
    public void calcul() {
        KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {

            sec--;

            if (sec < 0) {
                min--;
                sec = 59;

            }

            if (min <= 0 && sec <= 0) {
                calcStop();
            }

            afficheChrono();

        });

        // System.out.println(min+":"+sec);
        text.setText(min + ":" + sec);
        timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void calcStop() {
        timeline.stop();
    }

    /**
     * Méthode qui enlève du temps en fonction de l'malus utilisée
     * 
     * @param malus valeur de la pénalité appliquée à l'utilisation d'une malus
     */

    @Override
    public void aideUtilise() {

        int malus = 40;

        if (sec - malus < 0) {
            min--;
            sec -= malus - 60;
        } else if (min == 0 && sec - malus < 0) {
            calcStop();
        } else {
            sec -= malus;
        }

    }

    /**
     * Ajout de temps pour le joueur lors de la complétion d'une grille
     */
    public void grilleComplete() {

        int bonus = 30;

        if (sec + bonus > 60) {
            min++;
            sec += (bonus - 60);
        } else
            sec += bonus;

    }

    @Override
    public void checkUtilise() {
        
        int malus = 60;

        if (sec - malus < 0) {
            min--;
            sec -= malus - 60;
        } else if (min == 0 && sec - malus < 0) {
            calcStop();
        } else {
            sec -= malus;
        }

    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }

    @Override
    public void nouveauLance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nouveauLance'");
    }

    @Override
    public void restart() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restart'");
    }

    @Override
    public Pane get_Pane() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get_Pane'");
    }

}