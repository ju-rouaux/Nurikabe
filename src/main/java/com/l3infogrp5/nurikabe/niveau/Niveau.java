package com.l3infogrp5.nurikabe.niveau;

import java.io.Serializable;

import com.l3infogrp5.nurikabe.Main;
import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.Charger;
import com.l3infogrp5.nurikabe.sauvegarde.StockageNiveau;

/**
 * Sert de gestionnaire de grille + score + mode de jeu
 */
public class Niveau implements Serializable {

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
        int[][] matrice = StockageNiveau.chargerGrille(index, Main.mode_De_Jeu);
        // Historique de démonstration

        System.out.println("Affichage de la matrice chargée depuis le fichier txt:");
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();
        }

        /*
         * Chargement de l'historique des mouvements du joueur
         */
        this.histo = Charger.chargerHistorique(Main.joueur, Main.mode_De_Jeu, Main.id_Niveau);
        if (this.histo == null) {
            System.out.println("Aucune sauvegarde de l'historique des mouvements du joueur trouvée");
            System.out.println("Chargement de l'historique des mouvements du joueur par défaut...");
            this.histo = new Historique();
        } else {
            System.out.println("Sauvegarde de l'historique des mouvements du joueur trouvée");
            System.out.println("Chargement de l'historique des mouvements du joueur sauvegardé...");
            this.histo.initTransientBoolean();
            this.histo.actualiserEtat();
        }

        /*
         * Chargement de la grille du niveau
         */
        if (Charger.chargerMatrice(Main.joueur, Main.mode_De_Jeu, Main.id_Niveau) == null) {
            System.out.println("Aucune sauvegarde de la grille du niveau trouvée");
            System.out.println("Chargement de la grille du niveau par défaut...");
            this.grille = new Grille(StockageNiveau.chargerGrille(Main.id_Niveau, Main.mode_De_Jeu), this.histo);
        } else {
            this.grille = new Grille(Charger.chargerMatrice(Main.joueur, Main.mode_De_Jeu, Main.id_Niveau), this.histo);
            System.out.println("Sauvegarde de la grille du niveau trouvée");
            System.out.println("Chargement de la grille du niveau sauvegardée...");
        }
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
     * Définit la grille du niveau.
     *
     * @param grille la grille du niveau.
     * @return la grille du niveau.
     */
    public Grille setGrille(Grille grille) {
        this.grille = grille;
        return this.grille;
    }

    /**
     * Définit l'historique du niveau.
     *
     * @param histo l'historique du niveau.
     * @return l'historique du niveau.
     */
    public Historique setHistorique(Historique historique) {
        this.histo = historique;

        return this.histo;
    }
}
