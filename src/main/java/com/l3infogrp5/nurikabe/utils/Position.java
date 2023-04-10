package com.l3infogrp5.nurikabe.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
     * @param x position en x.
     * @param y position en y.
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
     * Retourne la position située au Nord de la position courante.
     * 
     * @return la position située au Nord de la position courante.
     */
    public Position getN() {
        return new Position(this.x - 1, this.y);
    }

    /**
     * Retourne la position située au Nord Est de la position courante.
     * 
     * @return la position située au Nord Est de la position courante.
     */
    public Position getNE() {
        return new Position(this.x - 1, this.y + 1);
    }

    /**
     * Retourne la position située à l'Est de la position courante.
     * 
     * @return la position située à l'Est de la position courante.
     */
    public Position getE() {
        return new Position(this.x, this.y + 1);
    }

    /**
     * Retourne la position située au Sud Est de la position courante.
     * 
     * @return la position située au Sud Est de la position courante.
     */
    public Position getSE() {
        return new Position(this.x + 1, this.y + 1);
    }

    /**
     * Retourne la position située au Sud de la position courante.
     * 
     * @return la position située au Sud de la position courante.
     */
    public Position getS() {
        return new Position(this.x + 1, this.y);
    }

    /**
     * Retourne la position située au Sud Ouest de la position courante.
     * 
     * @return la position située au Sud Ouest de la position courante.
     */
    public Position getSO() {
        return new Position(this.x + 1, this.y - 1);
    }

    /**
     * Retourne la position située à l'Ouest de la position courante.
     * 
     * @return la position située à l'Ouest de la position courante.
     */
    public Position getO() {
        return new Position(this.x, this.y - 1);
    }

    /**
     * Retourne la position située au Nord Ouest de la position courante.
     * 
     * @return la position située au Nord Ouest de la position courante.
     */
    public Position getNO() {
        return new Position(this.x - 1, this.y - 1);
    }

    /**
     * Retourne les positions au Nord, Est, Sud, et Ouest de la position courantes.
     * 
     * @return les positions au Nord, Est, Sud, et Ouest de la position courantes.
     */
    public List<Position> getVoisins() {
        return new ArrayList<>(List.of(this.getN(), this.getE(), this.getS(), this.getO()));
    }

    /**
     * Retourne les positions au Nord Ouest, Nord Est, Sud Est, et Sud Ouest de la
     * position courantes.
     * 
     * @return les positions au Nord Ouest, Nord Est, Sud Est, et Sud Ouest de la
     *         position courantes.
     */
    public List<Position> getDiagonales() {
        return new ArrayList<>(List.of(this.getNO(), this.getNE(), this.getSE(), this.getSO()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[Position (" + this.x + ", " + this.y + ")]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object p) {
        if (!(p instanceof Position))
            return false;
        Position p2 = (Position) p;
        return this.x == p2.x && this.y == p2.y;
    }
}
