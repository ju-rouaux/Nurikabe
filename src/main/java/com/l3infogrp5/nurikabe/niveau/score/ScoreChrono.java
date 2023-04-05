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
    protected double sec;

    /**minutes*/ 
    protected double min;

    /**nombre total de secondes écoulées*/
    protected double totalSec;

    /**Texte du chronomètre*/
    protected Text text;

    /**Zone d'affichage du chronomètre*/
    protected Pane affichage;

    /**Calculateur du temps qui passe*/
    protected Timeline timeline;


    /**
     * Constructeur du chronomètre
     * 
     * @param totalSec nombre total de secondes
     * @param text texte du chronomètre
     */
    public ScoreChrono(double totalSec) {
        this.totalSec = totalSec;
        this.text = new Text();

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
    @Override
    public void grilleComplete() {
    }

    /**
     * Méthode qui retourne le nombre de secondes écoulées durant la partie
     * 
     * @return totalSec
     */
    @Override
    public double getScore() {

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