package com.l3infogrp5.nurikabe.niveau.score;

import javafx.scene.layout.Pane;

/**
 * Classe interface répertoriant les méthodes à invoquer pour calculer un score
 *
 * @author Antoine Couapel
 * @version 1.0
 */
public interface ScoreInterface {

    /**
     * Méthode abstraite de lancement de chrono
     */
    public void start();

    /**
     * Méthode d'arrêt du chrono
     */
    public void stop();

    /**
     * Méthode abstraite à lancer quand une aide est utilisée
     */
    public void aideUtilise();

    /**
     * Méthode abstraite à lancer quand une grille est recommencée
     */
    public void restart();

    /**
     * Méthode abstraite à lancer quand une grille a été complétée
     */
    public void grilleComplete();

    /**
     * Méthode abstraite à lancer quand un check est utilisée
     */
    public void checkUtilise();

    /**
     * Méthode qui retourne l'affichage du score
     * @return Pane
     */
    public Pane get_Pane();

    /**
     * Méthode qui retourne le score
     * @return double
     */
    public double getScore();

    /**
     * Méthode qui modifie le score
     * @param score le nouveau score
     */
    public void setScore(double score);

}
