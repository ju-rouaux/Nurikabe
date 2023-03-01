package com.l3infogrp5.nurikabe.profil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.l3infogrp5.nurikabe.utils.Path;
import com.l3infogrp5.nurikabe.niveau.Niveau;
import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.sauvegarde.StockageNiveau;

/**
 * Classe représentant un profil de joueur.
 *
 * @author Guillaume Richard
 */
public class Profil {

    /* Le nom du joueur */
    private String joueur;
    /* Le mode de jeu */
    private String mode_de_jeu;
    /* L'identifiant du niveau représenté par un numero */
    private int id_niveau;
    /* L'historique des mouvements */
    private Historique historique;
    /* La grille */
    private Grille grille;

    /**
     * Création d'un profil.
     *
     * @param joueur      le nom du joueur
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau   l'id du niveau
     * @throws IOException {@link IOException}
     */
    public Profil(String joueur, String mode_de_jeu, int id_niveau) throws IOException {
        this.joueur = joueur;
        this.mode_de_jeu = mode_de_jeu;
        this.id_niveau = id_niveau;

        if (Sauvegarder.RechercherSauvegarde(joueur)) {
            System.out.println("[Profil] Profil deja existant");
        } else {
            Sauvegarder.creerDossierJoueur(joueur);
        }

    }

    /**
     * Chargement d'un niveau, si une sauvegarde existe alors on la charge sinon on
     * charge le niveau par défaut.
     *
     * @param id_niveau l'id du niveau
     * @return le niveau chargé
     */
    public Niveau chargerNiveau(int id_niveau) {

        Niveau niveau_temp = null;
        niveau_temp = new Niveau(this.id_niveau);

        /*
         * Chargement de l'historique des mouvements du joueur
         */
        this.historique = chargerHistorique(this.joueur, this.mode_de_jeu, this.id_niveau);
        if (this.historique == null) {
            System.out.println(
                    "[Profil] Aucune sauvegarde de l'historique des mouvements du joueur trouvée - Création d'un historique vide");
            this.historique = new Historique();
        } else {
            System.out.println(
                    "[Profil] Sauvegarde de l'historique des mouvements du joueur trouvée - Chargement de l'historique des mouvements du joueur sauvegardé...");
            this.historique.initTransientBoolean();
            this.historique.actualiserEtat();
        }

        niveau_temp.setHistorique(this.historique);

        /*
         * Chargement de la grille du niveau
         */
        if (chargerMatrice(this.joueur, this.mode_de_jeu, this.id_niveau) == null) {
            System.out.println(
                    "[Profil] Aucune sauvegarde de la grille du niveau trouvée - Chargement de la grille par défaut");
            this.grille = new Grille(StockageNiveau.chargerGrille(this.id_niveau, this.mode_de_jeu, false),
                    StockageNiveau.chargerGrille(this.id_niveau, this.mode_de_jeu, true),
                    this.historique);
        } else {
            this.grille = new Grille(chargerMatrice(this.joueur, this.mode_de_jeu, this.id_niveau),
                    StockageNiveau.chargerGrille(this.id_niveau, this.mode_de_jeu, true),
                    this.historique);
            System.out.println(
                    "[Profil] Sauvegarde de la grille du niveau trouvée - Chargement de la grille du niveau sauvegardée...");
        }
        niveau_temp.setGrille(this.grille);

        return niveau_temp;

    }

    /**
     * Sauvegarde le niveau deja commencé
     *
     * @param matrice    la matrice du niveau
     * @param historique l'historique des mouvements
     */
    public void sauvegarderNiveau(int[][] matrice, Historique historique) {
        // sauvegarder le niveau correspondant au profil
        sauvegardeMatrice(this.joueur, this.mode_de_jeu, this.id_niveau, grille.getMatrice());
        sauvegarderHistorique(this.joueur, this.mode_de_jeu, this.id_niveau, grille.getHistorique());
    }

    /**
     * Charge l'historique des mouvements du joueur
     *
     * @param joueur      le nom du joueur
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau   l'id du niveau
     * @return l'historique des mouvements chargé
     */
    public static Historique chargerHistorique(String joueur, String mode_de_jeu, int id_niveau) {
        File fichier_mouvements = new File(
                Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu + "/Mouvements_" + id_niveau);
        if (fichier_mouvements.exists() && fichier_mouvements.length() > 0) {
            return deserialisationHistorique(fichier_mouvements);
        } else {
            System.out.println("[Profil] Aucun mouvement du niveau du joueur à charger !");
        }
        return null;
    }

    /**
     * Deserialise l'historique des mouvements a partir d'un fichier.
     *
     * @param fichier le fichier serialisé des mouvements
     * @return l'historique des mouvements deserialisé
     */
    private static Historique deserialisationHistorique(File fichier) {
        Historique historique = null;
        try {
            FileInputStream fichier_entree = new FileInputStream(fichier);
            ObjectInputStream entree = new ObjectInputStream(fichier_entree);
            historique = (Historique) entree.readObject();
            entree.close();
            fichier_entree.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return historique;
    }

    /**
     * Charge la grille à partir du fichier.
     *
     * @param joueur      le nom du joueur
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau   l'id du niveau
     * @return la matrice du niveau chargé
     */
    private static int[][] chargerMatrice(String joueur, String mode_de_jeu, int id_niveau) {
        File grille_repertoire = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu);
        File grille_fichier = new File(grille_repertoire.toString() + "/Matrice_" + id_niveau);
        if (grille_fichier.exists() && grille_fichier.length() > 0) {
            StockageNiveau.chargerGrille(id_niveau, mode_de_jeu, false);
            return deserialisationMatrice(grille_fichier);
        } else {
            System.out.println("[Profil] Aucune grille du niveau du joueur à charger !");
        }
        return null;
    }

    /**
     * Deserialise la grille à partir du fichier.
     *
     * @param fichier le fichier serialisé de la grille
     * @return la matrice du niveau deserialisé
     */
    private static int[][] deserialisationMatrice(File fichier) {
        int[][] matrice = null;
        try {
            FileInputStream fichier_entree = new FileInputStream(fichier);
            ObjectInputStream entree = new ObjectInputStream(fichier_entree);
            matrice = (int[][]) entree.readObject();
            entree.close();
            fichier_entree.close();
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
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau   l'id du niveau
     * @param historique  l'historique des mouvements
     * @return l'historique des mouvements
     */
    private static void sauvegarderHistorique(String joueur, String mode_de_jeu, int id_niveau,
            Historique historique) {
        File mouvements_repertoire = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu);
        File mouvements_fichier = new File(mouvements_repertoire.toString() + "/Mouvements_" + id_niveau);

        if (Sauvegarder.creerDossierFichier(mouvements_repertoire, mouvements_fichier)) {
            System.out.println("[Profil] Fichier de sauvegarde de l'historique des mouvements créé / deja existant");
            serialisationHistorique(mouvements_fichier, historique);
        } else {
            System.out.println("[Profil] Erreur lors de la création de fichier et/ou de dossier");
        }
    }

    /**
     * Serialisation de l'historique des mouvements
     *
     * @param repertoire le répertoire
     * @param historique l'historique des mouvements
     */
    private static void serialisationHistorique(File repertoire, Historique historique) {
        try {
            FileOutputStream fichier_sortie = new FileOutputStream(repertoire, false); // false ecrase le fichier
            ObjectOutputStream sortie = new ObjectOutputStream(fichier_sortie);
            sortie.writeObject(historique);
            sortie.close();
            fichier_sortie.close();

            System.out
                    .println("[Profil] Historique des mouvements serialisé et sauvegardé dans Mouvements_<id_niveau>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sauvegarde la grille
     *
     * @param joueur      le nom du joueur/profil associé a la grille
     * @param mode_de_jeu le mode de jeu associé a la grille
     * @param id_niveau   l'id du niveau associé a la grille
     * @param matrice     la matrice du niveau a sauvegarder
     */
    private static void sauvegardeMatrice(String joueur, String mode_de_jeu, int id_niveau, int[][] matrice) {
        File matrice_repertoire = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu);
        File matrice_fichier = new File(matrice_repertoire.toString() + "/Matrice_" + id_niveau);

        if (Sauvegarder.creerDossierFichier(matrice_repertoire, matrice_fichier)) {
            serialisationMatrice(matrice_fichier, matrice);
        } else {
            System.out.println("[Profil] Erreur lors de la création de fichier et/ou de dossier");
        }
        // Affichage de la matrice
        boolean debug = false;
        if (debug) {
            System.out.println("{");
            for (int i = 0; i < matrice.length; i++) {
                System.out.print(" { ");
                for (int j = 0; j < matrice[i].length; j++) {
                    System.out.print(matrice[i][j] + ", ");
                }
                System.out.println("},");
            }
            System.out.println("};");
        }
    }

    /**
     * Serialisation de la grille
     *
     * @param repertoire le répertoire de la grille
     * @param matrice    la matrice du niveau
     */
    private static void serialisationMatrice(File repertoire, int[][] matrice) {
        try {
            FileOutputStream fichier_sortie = new FileOutputStream(repertoire, false);
            ObjectOutputStream sortie = new ObjectOutputStream(fichier_sortie);
            sortie.writeObject(matrice);
            sortie.close();
            fichier_sortie.close();
            System.out.println("[Profil] Matrice serialisé et sauvegardé dans Matrice_<id_niveau>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Getters
     */
    /**
     * getter pour le nom du joueur du profil
     *
     * @return le nom du joueur
     */
    public String getJoueur() {
        return joueur;
    }

    /**
     * getter pour l'identifiant du niveau
     *
     * @return l'historique des mouvements
     */
    public int getId_niveau() {
        return id_niveau;
    }

    /**
     * getter pour l'id du niveau
     *
     * @return l'id du niveau
     */
    public String getMode_de_jeu() {
        return mode_de_jeu;
    }

}
