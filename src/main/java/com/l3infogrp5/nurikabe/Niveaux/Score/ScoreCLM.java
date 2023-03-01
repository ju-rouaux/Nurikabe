package com.l3infogrp5.nurikabe.Niveaux.Score;

import java.lang.Thread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Implémentation du calcul d'un score pour le mode Contre La Montre
 * 
 * @author Antoine Couapel, Killian Rattier
 * @version 1.0
 */

public class ScoreCLM extends ScoreChrono {

    public Timeline timeline;

    public ScoreCLM(int sec, int min, Text text) {
        super(sec, min, text);
    }

    /**
     * Méthode de calcul pour l'incrémentation du chrono
     */
    public void calcul() {
        KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {

            sec++;

            if (sec == 60) {
                min++;
                sec = 0;

            }

            afficheChrono();

        });

        // System.out.println(min+":"+sec);
        text.setText(min + ":" + sec);
        Timeline timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void calcStop() {
        timeline.stop();
    }

    /**
     * Méthode qui ajoute du temps en fonction de l'aide utilisée
     * 
     * @param aide valeur de la pénalité appliquée à l'utilisation d'une aide
     */
    @Override
    public void aideUtilise() {

        int aide = 40;

        if (sec + aide > 60) {
            min++;
            sec += (aide - 60);
        } else
            sec += aide;

    }

    @Override
    public void checkUtilise() {

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
    public void get_Pane() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get_Pane'");
    }
}
