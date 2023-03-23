package com.l3infogrp5.nurikabe.niveau.score;

import org.controlsfx.control.Rating;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;

/**
 * Implémentation du score pour le mode zen
 * On calcule via un système d'étoiles allant de 0 à 5
 * 
 * @author Antoine Couapel
 * @version 1.0
 */
public class ScoreZen implements ScoreInterface {

    double etoiles;
    public final Rating rating = new Rating();
    
    public ScoreZen(double etoiles) {
        this.etoiles = etoiles;
    }

    /**
     * Réduction du nombre d'étoiles si une aide est utilisée
     * 
     * @param aide valeur de la pénalité appliquer à l'utilisation d'une aide
     */
    @Override
    public void aideUtilise() {

        if (etoiles >= 0) {
            this.etoiles -= 0.5;
            rating.setRating(etoiles);
        }
    }

    @Override
    public void grilleComplete() {

    }

    @Override
    public void checkUtilise() {

    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stop'");
    }
    @Override
    public void restart() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restart'");
    }

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
    
    public int getScore() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getScore'");
    }
}