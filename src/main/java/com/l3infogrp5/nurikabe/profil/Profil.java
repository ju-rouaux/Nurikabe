package com.l3infogrp5.nurikabe.profil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.l3infogrp5.nurikabe.utils.Path;
import com.l3infogrp5.nurikabe.Main;
import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.sauvegarde.StockageNiveau;

public class Profil {

    public String joueur;
    public String modeDeJeu;
    public int idNiveau;
    public Historique histo;
    public Grille grille;

    public Profil(String joueur, String modeDeJeu, int idNiveau) {
        this.joueur = joueur;
        this.modeDeJeu = modeDeJeu;
        this.idNiveau = idNiveau;
    }

    public void chargerNiveau(int idNiveau) {
        // charger le niveau correspondant au profil

        /*
         * Chargement de l'historique des mouvements du joueur
         */
        this.histo = chargerHistorique(this.joueur, this.modeDeJeu, this.idNiveau);
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
        if (chargerMatrice(this.joueur, this.modeDeJeu, this.idNiveau) == null) {
            System.out.println("Aucune sauvegarde de la grille du niveau trouvée");
            System.out.println("Chargement de la grille du niveau par défaut...");
            this.grille = new Grille(StockageNiveau.chargerGrille(this.joueur, this.modeDeJeu), this.histo);
        } else {
            this.grille = new Grille(chargerMatrice(this.joueur, this.modeDeJeu, this.idNiveau), this.histo);
            System.out.println("Sauvegarde de la grille du niveau trouvée");
            System.out.println("Chargement de la grille du niveau sauvegardée...");
        }

    }

    public void sauvegarderNiveau(int[][] matrice, Historique historique) {
        // sauvegarder le niveau correspondant au profil
        System.out.println("Sauvegarde du niveau en cours");
        Sauvegarder.sauvegardeMatrice(this.joueur, this.modeDeJeu, this.idNiveau, grille.getGrille().getMatrice());
        Sauvegarder.sauvegarderHistorique(this.joueur, this.modeDeJeu, this.idNiveau, grille.getHistorique());
    }

    /**
     * Charge l'historique des mouvements du joueur
     *
     * @param joueur      le nom du joueur
     * @param mode_De_Jeu le mode de jeu
     * @param id_Niveau   l'id du niveau
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
    private static int[][] chargerMatrice(String joueur, String mode_De_Jeu, int id_Niveau) {
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
    private static int[][] deserialisationMatrice(File fichier) {
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

    /*
     * Sauvegarde
     */

    /**
     * Sauvegarde l'historique des mouvements
     *
     * @param joueur      le nom du joueur/profil
     * @param mode_De_Jeu le mode de jeu
     * @param id_Niveau   l'id du niveau
     * @param historique  l'historique des mouvements
     * @return l'historique des mouvements
     */
    private static void sauvegarderHistorique(String joueur, String mode_De_Jeu, int id_Niveau,
            Historique historique) {
        File mouvements_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu);
        File mouvements_fichier = new File(mouvements_repert.toString() + "/Mouvements_" + id_Niveau);

        if (Sauvegarder.creerDossierFichier(mouvements_repert, mouvements_fichier)) {
            System.out.println("Fichier de sauvegarde de l'historique des mouvements créé / deja existant");
            serialisationHistorique(mouvements_fichier, historique);
        } else {
            System.out.println("Erreur lors de la création de fichier et/ou de dossier");
        }
    }

    /**
     * Serialisation de l'historique des mouvements
     *
     * @param repert     le répertoire
     * @param historique l'historique des mouvements
     */
    private static void serialisationHistorique(File repert, Historique historique) {
        try {
            FileOutputStream fileOut = new FileOutputStream(repert, false); // false ecrase le fichier
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(historique);
            out.close();
            fileOut.close();

            System.out.println("Historique des mouvements serialisé et sauvegardé dans Mouvements_<id_niveau>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sauvegarde la grille
     *
     * @param joueur      le nom du joueur/profil
     * @param mode_De_Jeu le mode de jeu
     * @param id_Niveau   l'id du niveau
     * @param grille      la grille
     * @return la grille
     */
    private static void sauvegardeMatrice(String joueur, String mode_De_Jeu, int id_Niveau, int[][] matrice) {
        File matrice_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu);
        File matrice_fichier = new File(matrice_repert.toString() + "/Matrice_" + id_Niveau);

        if (Sauvegarder.creerDossierFichier(matrice_repert, matrice_fichier)) {
            serialisationMatrice(matrice_fichier, matrice);
        } else {
            System.out.println("Erreur lors de la création de fichier et/ou de dossier");
        }
    }

    /**
     * Serialisation de la grille
     *
     * @param repert le répertoire
     * @param grille la grille
     */
    private static void serialisationMatrice(File repert, int[][] matrice) {
        try {
            FileOutputStream fileOut = new FileOutputStream(repert, false);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(matrice);
            out.close();
            fileOut.close();
            System.out.println("Matrice serialisé et sauvegardé dans Matrice_<id_niveau>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getJoueur() {
        return joueur;
    }

}
