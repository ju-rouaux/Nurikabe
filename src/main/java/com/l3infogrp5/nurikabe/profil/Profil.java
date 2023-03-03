package com.l3infogrp5.nurikabe.profil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.l3infogrp5.nurikabe.utils.Path;
import com.l3infogrp5.nurikabe.niveau.Niveau;
import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;

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
            this.grille = new Grille(Sauvegarder.chargerGrille(this.id_niveau, this.mode_de_jeu, false),
                    Sauvegarder.chargerGrille(this.id_niveau, this.mode_de_jeu, true),
                    this.historique);
        } else {
            this.grille = new Grille(chargerMatrice(this.joueur, this.mode_de_jeu, this.id_niveau),
                    Sauvegarder.chargerGrille(this.id_niveau, this.mode_de_jeu, true),
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
        Sauvegarder.sauvegardeMatrice(this.joueur, this.mode_de_jeu, this.id_niveau, grille.getMatrice());
        Sauvegarder.sauvegarderHistorique(this.joueur, this.mode_de_jeu, this.id_niveau, grille.getHistorique());
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
            Sauvegarder.chargerGrille(id_niveau, mode_de_jeu, false);
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
