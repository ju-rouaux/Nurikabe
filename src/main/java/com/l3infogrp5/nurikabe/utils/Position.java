package com.l3infogrp5.nurikabe.utils;

import java.io.Serializable;

/**
 * Structure représentant des coordonnées.
 * 
 * @author Julien Rouaux
 */
public class Position implements Serializable {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        return "[Position ("+this.x+", "+this.y+") / Index "+this.index+"]";
    }

    @Override
    public boolean equals(Object p) {
        Position p2 = (Position) p;
        return this.x == p2.x && this.y == p2.y && this.index == p2.index;
    }
}
