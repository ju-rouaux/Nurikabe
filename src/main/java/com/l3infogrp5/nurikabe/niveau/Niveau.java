package com.l3infogrp5.nurikabe.niveau;

import com.l3infogrp5.nurikabe.niveau.grille.Grille;

/**
 * Sert de gestionnaire de grille + score + mode de jeu
 */
public class Niveau {

    private Grille grille;

    /**
     * TODO
     * 
     * @param index -
     */
    public Niveau(int index /* Score mode, Profil profil */) {
        // TODO charger le fichier du niveau

        // TODO vérifier s'il existe une sauvegarde

        // Matrice de démonstration (TODO : à supprimer)
        int[][] matrice = new int[][] { { 0, 0, 3 }, { 0, 0, 0 }, { 0, 0, 0 } };

        this.grille = new Grille(matrice);
    }

    /**
     * Retourne la grille du niveau.
     * 
     * @return la grille du niveau.
     */
    public Grille getGrille() {
        return this.grille;
    }
}
