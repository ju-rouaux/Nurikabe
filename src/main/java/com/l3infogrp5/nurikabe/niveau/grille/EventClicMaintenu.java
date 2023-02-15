package com.l3infogrp5.nurikabe.niveau.grille;

/**
 * Interface qui permet de définir le code à exécuter lorsqu'un clic est
 * maintenu sur une case. Cette interface permet de récupérer les informations
 * liées à la case maintenue.
 * 
 * @author Julien Rouaux
 */
public interface EventClicMaintenu {
    /**
     * Durée en ms pour que le clic soit considéré "maintenu"
     */
    public final int DUREE_CLIC_MS = 500;

    /**
     * Exécuté lorsqu'un clic est maintenu sur une case.
     * Ne fait rien par défaut.
     * 
     * @param c la case cliquée.
     */
    default public void maintenu(Case c) {
    }

    /**
     * Exécuté lorsqu'un clic maintenu est relâché.
     * Ne fait rien par défaut.
     * 
     * @param c la case relâchée.
     */
    default public void relache(Case c) {
    }
}
