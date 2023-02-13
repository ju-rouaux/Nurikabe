package com.l3infogrp5.nurikabe.niveau.grille;

/**
 * Les différents états possibles d'une case interactive.
 * 
 * @author Julien Rouaux
 */
public enum Etat {
    /** Case noire, représente un fleuve. */
    NOIR,
    /** Case blanche, représente une case non traitée ou une fraction d'île. */
    BLANC,
    /** Case point, représente une fraction d'île */
    POINT;

    /**
     * Donne la représentation numérique de l'état.
     * Cette représentation est utilisée pour traiter plus facilement les grilles de
     * cases.
     * 
     * @return la représentation numérique de l'état.
     */
    public int toInt() {
        switch (this) {
            case POINT:
                return -2;
            case NOIR:
                return -1;
            case BLANC:
                return 0;
            default:
                return 0; // Ne devrait pas être atteint
        }
    }

    /**
     * Retourne l'état correspondant à la représentation numérique donnée.
     * 
     * @param i représentation numérique d'un état.
     * @return l'état correspondant à la représentation numérique donnée.
     * @throws IllegalArgumentException lancé lorsqu'aucun état n'est associé au
     *                                  numéro.
     */
    public static Etat fromInt(int i) throws IllegalArgumentException {
        switch (i) {
            case -2:
                return Etat.POINT;
            case -1:
                return Etat.NOIR;
            case 0:
                return Etat.BLANC;
            default:
                throw new IllegalArgumentException("Aucun état de case n'est associé au numéro " + i + ".");
        }
    }

    /**
     * Retourne l'état suivant.
     * 
     * @return l'état suivant.
     */
    public Etat etatSuivant() {
        switch (this) {
            case BLANC:
                return Etat.NOIR;
            case NOIR:
                return Etat.POINT;
            case POINT:
                return Etat.BLANC;
            default:
                return Etat.BLANC; // Ne devrait pas être atteint
        }
    }
};