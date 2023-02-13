package com.l3infogrp5.utils;

/**
 * Structure représentant des coordonnées.
 * 
 * @author Julien Rouaux
 */
public class Position {
    private int x, y;
    private int index;

    /**
     * Créer de nouvelles coordonnées
     * @param x position en x.
     * @param y position en y.
     * @param index index dans la matrice (x*nb_colonne + y).
     */
    public Position(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    /**
     * Retourne la position en X.
     * @return la position en X.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Retourne la position en Y.
     * @return la position en Y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Retourne l'index.
     * @return l'index.
     */
    public int getIndex() {
        return this.index;
    }
}
