package com.l3infogrp5.nurikabe.niveau.score;

import org.controlsfx.control.Rating;

//import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente une implémentation concrète de l'interface ScoreInterface, qui calcule le score pour le mode zen en utilisant un système d'étoiles allant de 0 à 5.
 * Elle contient une variable double représentant le nombre d'étoiles, ainsi qu'un objet Rating de la librairie ControlsFX utilisé pour l'affichage graphique du score.
 * @author Antoine Couapel
 * @version 1.0
 */

public class ScoreZen implements ScoreInterface {

    /** Nombre d'étoiles pour le score */
    private double etoiles;
    /** Rating utilisé pour l'affichage graphique du score */
    public final Rating rating = new Rating();
    
    /**
     * Constructeur de la classe ScoreZen.
     * @param etoiles le nombre d'étoiles initial du score.
     */

    public ScoreZen(double etoiles) {
        this.etoiles = etoiles;
    }

    /**
     * Applique une pénalité de 0.5 étoile si une aide est utilisée.
     * Si l'évaluation actuelle des étoiles est inférieure à 0, aucune pénalité n'est appliquée.
     * Met à jour l'affichage de l'évaluation du score.
     */
    @Override
    public void aideUtilise() {

        if (etoiles >= 0) {
            this.etoiles -= 0.5;
            rating.setRating(etoiles);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void grilleComplete() {}

    /**
     * Applique une pénalité de une étoile si le check est utilisé.
     * Si l'évaluation actuelle des étoiles est inférieure à 0, aucune pénalité n'est appliquée.
     * Met à jour l'affichage de l'évaluation du score.
     */
    @Override
    public void checkUtilise() {
        if (etoiles >= 0) {
            this.etoiles -= 1;
            rating.setRating(etoiles);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void restart() {}

    /**
     * Cette méthode permet de créer un objet Pane contenant un Rating, et d'initialiser les propriétés de celui-ci selon les valeurs définies dans l'objet ScoreZen.
     * @return l'objet Pane contenant le Rating initialisé
     */

    @Override
    public Pane get_Pane() {
        rating.setUpdateOnHover(false); // On désactive la mise à jour du Rating au survol de la souris
        rating.setDisable(true);        // On désactive la possibilité de cliquer sur le Rating
        rating.setPartialRating(true);  // On active l'affichage des demi-étoiles
        rating.setRating(etoiles);             // On définit la valeur du Rating en fonction du nombre d'étoiles

        /* Augmente la saturation mais ça colore le fond en rose
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(1);
        rating.setEffect(colorAdjust);*/

        Pane ratingPane = new Pane();           // On crée un nouvel objet Pane pour contenir le Rating
        ratingPane.getChildren().add(rating);   // On ajoute le Rating à l'objet Pane
        return ratingPane;                      // On retourne l'objet Pane contenant le Rating initialisé
    }
    

    /**
     * Méthodes qui retourne le nombre d'étoiles restante à la fin de la partie
     * @return etoiles
     */
    @Override
    public Object getScore() {
        return etoiles;
    }
}