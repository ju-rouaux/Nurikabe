package com.l3infogrp5.nurikabe.niveau.score;

import org.controlsfx.control.Rating;

/**
 * Implémentation du score pour le mode zen
 * On calcule via un système d'étoiles allant de 0 à 5
 * 
 * @author Antoine Couapel
 * @version 1.0
 */
public class ScoreZen implements ScoreInterface {

    double etoiles;
    final Rating rating = new Rating();
    
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

        if (etoiles >= 0)
            this.etoiles -= 0.5;

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
    public void nouveauLance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nouveauLance'");
    }

    @Override
    public void restart() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'restart'");
    }

    @Override
    public void get_Pane() {
        if(!rating.isPartialRating())
            rating.setPartialRating(true);
        if(rating.getRating() >= 0.0)
            rating.setRating(this.etoiles);     
    }

}