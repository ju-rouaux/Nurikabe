package com.l3infogrp5.nurikabe.niveau.score;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Implémentation du calcul du score selon le chronomètre
 * 
 * @author Antoine Couapel, Killian Rattier
 * @version 1.0
 */
abstract public class ScoreChrono implements ScoreInterface {

    /**secondes*/
    public int sec;

    /**minutes*/ 
    public float min;

    /**nombre total de secondes écoulées*/
    public int totalSec;

    /**Texte du chronomètre*/
    public Text text;

    /**Zone d'affichage du chronomètre*/
    public Pane affichage;

    /**Calculateur du temps qui passe*/
    public Timeline timeline;


    /**
     * Constructeur du chronomètre
     * 
     * @param totalSec nombre total de secondes
     * @param text texte du chronomètre
     */
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

        min = totalSec / 60;
        sec = totalSec % 60;

        if (sec >= 10)
            text.setText((int) min + ":" + (int) sec);
        else
            text.setText((int) min + ":0" + (int) sec);

    }

    /**
     * {@inheritDoc}
     */
    public void start() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void aideUtilise() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void checkUtilise() {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void grilleComplete() {
    }

    /**
     * Méthode qui retourne le nombre de secondes écoulées durant la partie
     * 
     * @return totalSec
     */
    @Override
    public Object getScore() {

        return totalSec;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public void restart() {
    }

    /**
     * {@inheritDoc}}
     * 
     * @return affichage
     */
    @Override
    public Pane get_Pane() {
        return this.affichage;
    }
}