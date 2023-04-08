package com.l3infogrp5.nurikabe.niveau.score;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Implémentation du calcul d'un score pour le mode Contre La Montre
 *
 * @author Antoine Couapel
 * @version 1.0
 */

public class ScoreCLM extends ScoreChrono {

    /**
     * Constructeur du Score Contre La Montre
     * @param totalSec nombre total de secondes
     */
    public ScoreCLM(double totalSec) {
        super(totalSec);

        /**calcul pour l'incrémentation du chrono */
        KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {

            totalSec++;

            afficheChrono();

        });

        this.timeline = new Timeline(kf);
    }

    /**
     * Méthode qui démarre le chrono
     */
    @Override
    public void start() {
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
     * Méthode qui ajoute du temps quand une aide est utilisée
     */
    @Override
    public void aideUtilise() {

        int malus = 40;
        totalSec += malus;

    }

    /**
     * Méthode qui ajoute du temps quand un check est utilisée
     */
    @Override
    public void checkUtilise() {

        int malus = 40;
        totalSec += malus;

    }

    @Override
    public void setScore(double score) {
        this.totalSec = score;
    }

    /**
     * Méthode qui remet le temps à zéro quand la grille est réinitialisée
     */
    @Override
    public void restart() {
        totalSec = 0;
    }

    /**
     * {@inheritDoc}}
     * 
     */
    @Override
    public Pane get_Pane() {
        super.get_Pane();
    }
}
