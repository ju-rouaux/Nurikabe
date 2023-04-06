package com.l3infogrp5.nurikabe.niveau.score;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Implémentation du calcul du score pour le mode sans fin
 *
 * @author Antoine Couapel
 * @version 1.0
 */
public class ScoreEndless extends ScoreChrono {

    /**Nombres de grilles complétées lors de la partie*/
    public double nbGrilles = 0;


    /**
     * Constructeur du ScoreEndless
     * @param totalSec nombre total de secondes
     */
    public ScoreEndless(double totalSec) {
        super(totalSec);
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
     * {@inheritDoc}}
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
     * Méthode qui retourne le nombre de grilles complétées durant la partie
     * @return nbGrilles
     */
    @Override
    public double getScore() {
        return nbGrilles;
    }



}
