package com.l3infogrp5.nurikabe.niveau.score;

import javafx.scene.layout.Pane;

/**
 * Implémentation du score pour le mode zen
 * On calcule via un système d'étoiles allant de 1 à 5
 * 
 * @author Antoine Couapel
 * @version 1.0
 */
public class ScoreZen implements ScoreInterface {

    float etoiles;

    public ScoreZen(float etoiles) {
        this.etoiles = etoiles;
    }

    /**
     * Réduction du nombre d'étoiles si une aide est utilisée
     * 
     * @param aide valeur de la pénalité appliquer à l'utilisation d'une aide
     */
    @Override
    public void aideUtilise() {

        if (etoiles > 1)
            this.etoiles--;

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

    @Override
    public Pane get_Pane() {
        return null;
    }

    @Override
    public int getScore() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getScore'");
    }

}