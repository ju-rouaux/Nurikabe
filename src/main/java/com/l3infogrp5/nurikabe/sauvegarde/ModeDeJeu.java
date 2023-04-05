package com.l3infogrp5.nurikabe.sauvegarde;

/**
 * Enumération des différents modes de jeu.
 *
 * @author Guillaume Richard
 */
public enum ModeDeJeu {

    /**
     * Mode détente.
     */
    DETENTE,
    /**
     * Mode contre la montre.
     */
    CONTRELAMONTRE,
    /**
     * Mode sans fin.
     */
    SANSFIN;


    /**
     * Convertit l'état en string.
     * @return le string correspondant à l'état.
     */
    public String toString() {
        return switch (this) {
            case DETENTE -> "detente";
            case CONTRELAMONTRE -> "clm";
            case SANSFIN -> "endless";
            default ->
                throw new IllegalArgumentException("Impossible de convertir l'état " + this + " vers un string.");
        };
    }

}
