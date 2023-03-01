package com.l3infogrp5.nurikabe.niveau;

import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;

/**
 * Sert de gestionnaire de grille + score + mode de jeu
 */
public class Niveau {

    private Grille grille;
    private Historique histo;

    /**
     * TODO
     *
     * @param index -
     */
    public Niveau(int index /* Score mode, Profil profil */) {
        // TODO charger le fichier du niveau

        // TODO vérifier s'il existe une sauvegarde

        /**
         * TODO Modifier code pour supprimer matrice et solution supprimer
         */

        // Matrice de démonstration (TODO : à supprimer)
        int[][] matrice = new int[][] { { 0, 0, 17, 0, 3, 0, 0 }, { 0, 0, 0, 0, -1, 0, 0 }, { 0, -2, 0, 0, 0, 0, 0 } };
        int[][] solution = new int[][] { { -1, 0, 17, 0, 3, 0, 0 }, { 0, 0, 0, 0, -1, 0, 0 },
                { 0, -2, 0, 0, 0, 0, 0 } };
        // Historique de démonstration
        this.histo = new Historique();

        this.grille = new Grille(matrice, solution, this.histo);
    }

    /**
     * Retourne la grille du niveau.
     *
     * @return la grille du niveau.
     */
    public Grille getGrille() {
        return this.grille;
    }

    /**
     * Retourne l'historique du niveau.
     *
     * @return l'historique du niveau.
     */
    public Historique getHistorique() {
        return this.histo;
    }

    /**
     * Setter pour l'historique du niveau
     *
     * @param historique l'historique du niveau a charger
     */
    public void setHistorique(Historique historique) {
        this.histo = historique;
    }

    /**
     * Définit la grille du niveau.
     *
     * @param grille la grille du niveau.
     * @return la grille du niveau.
     */
    public Grille setGrille(Grille grille) {
        this.grille = grille;
        return this.grille;
    }

}
