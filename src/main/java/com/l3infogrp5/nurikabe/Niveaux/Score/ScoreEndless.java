package com.l3infogrp5.nurikabe.Niveaux.Score;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

    public int nbGrilles = 0;

    public ScoreEndless(int totalSec, Text text) {
        super(totalSec, text);
    }

    /**
     * Méthode de calcul pour la décrémentation du chrono
     */
    @Override
    public void start() {
        KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {

            totalSec--;

            if (totalSec <= 0) {
                stop();
            }

            afficheChrono();

        });

        timeline = new Timeline(kf);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Méthode d'arrêt du chrono
     */
    @Override
    public void stop() {
        timeline.stop();
    }

    /**
     * Méthode qui enlève du temps quand une aide est utilisée
     */
    @Override
    public void aideUtilise() {

        int malus = 40;

        if (totalSec - malus < 0) {
            stop();
        } else {
            totalSec -= malus;
        }

    }

    /**
     * Ajout de temps pour le joueur lors de la complétion d'une grille
     * Augmente le nombre de grille complété
     */
    @Override
    public void grilleComplete() {

        int bonus = 30;

        nbGrilles++;

        totalSec += bonus;

    }

    /**
     * Méthode qui enlève du temps quand une aide est utilisée
     */
    @Override
    public void checkUtilise() {

        int malus = 60;

        if (totalSec - malus < 0) {
            stop();
        } else {
            totalSec -= malus;
        }

    }

    /**
     * {@inheritDoc}
     */
    public int getScore() {
        return nbGrilles;
    }

}