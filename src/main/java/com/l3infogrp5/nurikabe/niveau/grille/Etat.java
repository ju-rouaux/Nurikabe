package com.l3infogrp5.nurikabe.niveau.grille;

import java.io.Serializable;

/**
 * Les différents états possibles d'une case.
 * Une case intéractive peut être NOIR, BLANC ou POINT.
 * Une case numérique sera NUMERIQUE.
 * Il n'est pas possible d'obtenir la représentation numérique d'un état
 * NUMERIQUE, car il est impossible de retrouver la vraie valeur de la case.
 * 
 * @author Julien Rouaux
 */
public enum Etat {
    /** Case noire, représente un fleuve. */
    NOIR,
    /** Case blanche, représente une case non traitée ou une fraction d'île. */
    BLANC,
    /** Case point, représente une fraction d'île */
    POINT,
    /** Case numérique */
    NUMERIQUE;

    /**
     * Donne la représentation numérique de l'état.
     * 
     * @return la représentation numérique de l'état.
     * @throws IllegalArgumentException lancé lorsque la case est NUMERIQUE.
     */
    public int toInt() throws IllegalArgumentException {
        switch (this) {
            case POINT:
                return -2;
            case NOIR:
                return -1;
            case BLANC:
                return 0;
            default:
                throw new IllegalArgumentException("Impossible de convertir l'état " + this + " vers un entier.");
        }
    }

    /**
     * Retourne l'état correspondant à la représentation numérique donnée.
     * 
     * @param i représentation numérique d'un état.
     * @return l'état correspondant à la représentation numérique donnée.
     */
    public static Etat fromInt(int i) {
        switch (i) {
            case -2:
                return Etat.POINT;
            case -1:
                return Etat.NOIR;
            case 0:
                return Etat.BLANC;
            default:
                return Etat.NUMERIQUE;
        }
    }

    /**
     * Retourne l'état suivant.
     * 
     * @return l'état suivant.
     * @throws IllegalArgumentException lancé lorsque la case est NUMERIQUE.
     */
    public Etat etatSuivant() throws IllegalArgumentException {
        switch (this) {
            case BLANC:
                return Etat.NOIR;
            case NOIR:
                return Etat.POINT;
            case POINT:
                return Etat.BLANC;
            default:
                throw new IllegalArgumentException("L'état " + this + " n'a pas d'état suivant.");
        }
    }
};