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
    void start();

    /**
     * Méthode d'arrêt du chrono
     */
    void stop();

    /**
     * Méthode abstraite à lancer quand une aide est utilisée
     */
    void aideUtilise();

    /**
     * Méthode abstraite à lancer quand une grille est recommencée
     */
    void restart();

    /**
     * Méthode abstraite à lancer quand une grille a été complétée
     */
    void grilleComplete();

    /**
     * Méthode abstraite à lancer quand un check est utilisée
     */
    void checkUtilise();

    /**
     * Méthode qui retourne l'affichage du score
     *
     * @return Pane
     */
    Pane get_Pane();

    /**
     * Méthode qui retourne le nombre de grilles complétées durant la partie
     *
     * @return nbGrilles
     */
    double getScore();

    /**
     * Retourne le score formaté pour son affichage.
     * Le parametre est uniquement pour le mode detente
     *
     * @param b si vrai retourne le temps total, sinon retourne le nombre de grilles
     * @return le score formaté
     */
    String getScoreFormate(boolean b);

    /**
     * Méthode qui modifie le score selon le boolean
     * Si b est faux, modifie le nombre de grilles
     * Si b est vrai, modifie le temps total
     *
     * @param score le nouveau score
     */
    void setScore(double score);


}
