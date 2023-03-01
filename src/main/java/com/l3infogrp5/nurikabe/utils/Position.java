package com.l3infogrp5.nurikabe.utils;

import java.io.Serializable;

/**
 * Structure représentant des coordonnées.
 * 
 * @author Julien Rouaux
 */
public class Position implements Serializable {
    /** Coordonnées x, y */
    private int x, y;

    /**
     * Créer de nouvelles coordonnées
     * 
     * @param x     position en x.
     * @param y     position en y.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retourne la position en X.
     * 
     * @return la position en X.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Retourne la position en Y.
     * 
     * @return la position en Y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[Position (" + this.x + ", " + this.y + ")]";
    }

    @Override
    public boolean equals(Object p) {
        if (!(p instanceof Position))
            return false;
        Position p2 = (Position) p;
        return this.x == p2.x && this.y == p2.y;
    }
}
