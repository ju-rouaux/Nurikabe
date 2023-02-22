package com.l3infogrp5.nurikabe.niveau;

import com.l3infogrp5.nurikabe.Main;
import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.Charger;

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

        // Matrice de démonstration (TODO : à supprimer)
        int[][] matrice = new int[][] { { 0, 0, 17, 0, 3, 0, 0 }, { 0, 0, 0, 0, -1, 0, 0 }, { 0, -2, 0, 0, 0, 0, 0 } };
        // Historique de démonstration
        System.out.println("Chargement de la sauvegarde");
        this.histo = Charger.chargerMouvements(Main.joueur, Main.mode_De_Jeu, Main.id_Niveau);
        if (this.histo == null) {
            System.out.println("Sauvegarde vide ou corrompue");
            this.histo = new Historique();
        }

        this.grille = new Grille(matrice, this.histo);
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
}
