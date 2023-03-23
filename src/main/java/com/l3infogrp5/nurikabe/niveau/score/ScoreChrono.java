package com.l3infogrp5.nurikabe.niveau.score;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Implémentation du calcul du score selon le chronomètre
 * 
 * @author Antoine Couapel, Killian Rattier
 * @version 1.0
 */

abstract public class ScoreChrono implements ScoreInterface {

    public int sec;
    public float min;
    public int totalSec;
    public Text text;
    public Pane affichage; 

    public ScoreChrono(int totalSec, Text text) {
        this.totalSec = totalSec;
        this.text = text;

        this.affichage = new Pane();
        this.affichage.getChildren().setAll(text);
    }

    /**
     * Méthode d'affichage du chrono
     */
    public void afficheChrono() {

        // endless
        /*
         * if (sec < 0) {
         * min--;
         * sec = 59;
         * 
         * }
         * 
         * //CLM
         * if (sec == 60) {
         * min++;
         * sec = 0;
         * 
         * }
         */
        min = totalSec / 60;
        sec = totalSec % 60;

        if (sec >= 10)
            text.setText((int) min + ":" + (int) sec);
        else
            text.setText((int) min + ":0" + (int) sec);

    }

    /**
     * Méthode abstraite de lancement de chrono
     */
    public void start() {
    }

    @Override
    /**
     * Méthode abstraite à lancer quand une aide est utilisée
     */
    public void aideUtilise() {
    }

    @Override
    /**
     * Méthode abstraite à lancer quand un check est utilisée
     */
    public void checkUtilise() {
    }

    @Override
    /**
     * Méthode abstraite à lancer quand une grille a été complétée
     */
    public void grilleComplete() {
    }

    /**
     * Méthode qui retourne le score
     * 
     * @return totalSec
     */
    @Override
    public int getScore() {

        return totalSec;
    }

    /**
     * Méthode abstraite à lancer quand une grille est recommencée
     */
    @Override
    public void restart() {
    }

    /**
     * Méthode qui retourne l'affichage du score
     * 
     * @return affichage
     */
    @Override
    public Pane get_Pane() {
        return this.affichage;
    }
}
