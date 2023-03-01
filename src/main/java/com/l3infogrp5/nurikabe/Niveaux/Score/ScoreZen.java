package com.l3infogrp5.nurikabe.Niveaux.Score;

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

}