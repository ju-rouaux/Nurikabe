package com.l3infogrp5.nurikabe.Niveaux.Score;

import javafx.scene.text.Text;

/**
 * Implémentation du calcul du score selon le chronomètre
 * 
 * @author Antoine Couapel, Killian Rattier
 * @version 1.0
 */

abstract public class ScoreChrono implements ScoreInterface {

    public int sec;
    public int min;
    public Text text;

    public ScoreChrono(int sec, int min, Text text) {
        this.sec = sec;
        this.min = min;
        this.text = text;
    }

    /**
     * Méthode d'affichage du chrono
     */
    public void afficheChrono() {

        if (sec > 10)
            text.setText(min + ":" + sec);
        else
            text.setText(min + ":0" + sec);

    }

    /**
     * Méthode abstraite
     */
    public void calcul() {
    }

    @Override
    /**
     * Méthode abstraite à lancer quand une aide est utilisée
     * 
     * @param aide valeur de la pénalité appliquée à l'utilisation d'une aide
     */
    public void aideUtilise() {
    }

    @Override
    /**
     * Méthode abstraite à lancer quand une grille a été complétée
     */
    public void grilleComplete() {
    }
}
