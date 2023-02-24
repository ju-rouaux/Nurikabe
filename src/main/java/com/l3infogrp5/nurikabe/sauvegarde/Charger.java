package com.l3infogrp5.nurikabe.sauvegarde;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.utils.Path;

public class Charger {

    /**
     * Charge l'historique des mouvements du joueur
     * @param joueur le nom du joueur
     * @param mode_De_Jeu le mode de jeu
     * @param id_Niveau l'id du niveau
     * @return l'historique des mouvements
     */
    public static Historique chargerHistorique(String joueur, String mode_De_Jeu, int id_Niveau) {
        File mouvements = new File(
                Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu + "/Mouvements_" + id_Niveau);
        if (mouvements.exists() && mouvements.length() > 0) {
            System.out.println("Chargement des mouvements du niveau du joueur ...");
            return deserialisationHistorique(mouvements);
        } else {
            System.out.println("Erreur de chargement du fichier des mouvements du niveau du joueur !");
        }
        System.out.println("Mouvements.existe() : " + mouvements.exists());
        System.out.println("Mouvements.length() : " + mouvements.length());
        return null;
    }

    /**
     * Deserialise l'historique des mouvements a partir d'un fichier.
     *
     * @param fichier le fichier serialisé des mouvements
     * @return l'historique des mouvements
     */
    private static Historique deserialisationHistorique(File fichier) {
        Historique historique = null;
        try {
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            historique = (Historique) in.readObject();
            // historique.initTransientBoolean();
            // historique.actualiserEtat();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return historique;
    }

    /**
     * Charge la grille à partir du fichier.
     *
     * @param joueur      le nom du joueur
     * @param mode_De_Jeu le mode de jeu
     * @param id_Niveau   l'id du niveau
     * @return la grille
     */
    public static int [][] chargerMatrice(String joueur, String mode_De_Jeu, int id_Niveau) {
        File grille_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu);
        File grille_fichier = new File(grille_repert.toString() + "/Matrice_" + id_Niveau);
        if (grille_fichier.exists() && grille_fichier.length() > 0) {
            System.out.println("Chargement de la grille du niveau du joueur ...");
            StockageNiveau.chargerGrille(id_Niveau, mode_De_Jeu);
            return deserialisationMatrice(grille_fichier);
        } else {
            System.out.println("Erreur de chargement du fichier de la grille du niveau du joueur !");
        }
        System.out.println("Niveau.existe() : " + grille_fichier.exists());
        System.out.println("Niveau.length() : " + grille_fichier.length());
        return null;
    }

    /**
     * Deserialise la grille à partir du fichier.
     *
     * @param fichier le fichier serialisé de la grille
     * @return la grille
     */
    private static int [][] deserialisationMatrice(File fichier) {
        int[][] matrice = null;
        try {
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            matrice = (int[][]) in.readObject();
            in.close();
            fileIn.close();

            System.out.println("Grille chargée");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return matrice;
    }

}
