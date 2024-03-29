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

    /**
     * Nombres de grilles complétées lors de la partie
     */
    public int nbGrilles;


    /**
     * Constructeur du ScoreEndless
     *
     * @param totalSec  nombre total de secondes
     * @param nbGrilles nombre de grilles complétées
     */
    public ScoreEndless(double totalSec, int nbGrilles) {
        super(totalSec);
        this.nbGrilles = nbGrilles;

        /**calcul pour la décrémentation du chrono */
        KeyFrame kf = new KeyFrame(Duration.millis(1000), e -> {

            this.totalSec--;

            if (this.totalSec <= 0) {
                this.totalSec = 0;
            }

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
     * Méthode qui enlève du temps quand une aide est utilisée
     */
    @Override
    public void aideUtilise() {

        int malus = 30;

        if (totalSec - malus < 0) {
            totalSec = 0;
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

        int malus = 45;

        if (totalSec - malus < 0) {
            stop();
        } else {
            totalSec -= malus;
        }

    }

    /**
     * Méthode qui retourne le temps total restant
     *
     * @return nbGrilles
     */
    @Override
    public double getScore() {
        return totalSec;
    }

    /**
     * Méthode qui modifie le score selon le boolean
     * Si b est faux, modifie le nombre de grilles
     * Si b est vrai, modifie le temps total
     *
     * @param score le nouveau score
     */
    @Override
    public void setScore(double score) {
        totalSec = score;
    }

    /**
     * Retourne le score formaté pour son affichage.
     * Le parametre est uniquement pour le mode detente
     *
     * @param b si vrai retourne le temps total, sinon retourne le nombre de grilles
     * @return le score formaté
     */
    @Override
    public String getScoreFormate(boolean b) {
        if (!b)
            return String.valueOf(nbGrilles);
        else return super.getScoreFormate(b);
    }

    /**
     * Méthode qui retourne le nombre de grilles complétées durant la partie
     * @return nbGrilles
     */
    public int getNbGrilles() {
        return nbGrilles;
    }

}
