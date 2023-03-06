package com.l3infogrp5.nurikabe.profil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.utils.Path;
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
     * // * Chargement d'un niveau, si une sauvegarde existe alors on la charge
     * sinon on
     * // * charge le niveau par défaut.
     * // *
     * // * @param id_niveau l'id du niveau
     * // * @return le niveau chargé
     * //
     */
    // public Niveau chargerNiveau(int id_niveau) {

    // Niveau niveau_temp = null;
    // niveau_temp = new Niveau(this.id_niveau);

    // /*
    // * Chargement de la grille du niveau
    // */
    // if (chargerMatrice(this.joueur, this.mode_de_jeu, this.id_niveau) == null) {
    // } else {
    // this.grille = new Grille(chargerMatrice(this.joueur, this.mode_de_jeu,
    // this.id_niveau),
    // Sauvegarder.chargerGrille(this.id_niveau, this.mode_de_jeu, true),
    // this.historique);
    // }
    // niveau_temp.setGrille(this.grille);

    // return niveau_temp;

    // }

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
     * S'il en a pas, creation d'un historique vierge
     */
    public void chargerHistorique() {
        Historique histo = null;
        File fichier_mouvements = new File(
                Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu + "/Mouvements_" + id_niveau);
        if (fichier_mouvements.exists() && fichier_mouvements.length() > 0) {
            System.out.println(
                    "[Profil] Sauvegarde de l'historique des mouvements du joueur trouvée - Chargement de l'historique des mouvements du joueur sauvegardé...");

            histo = deserialisationHistorique(fichier_mouvements);
            histo.initTransientBoolean();
            histo.actualiserEtat();
        } else if (histo == null) {
            System.out.println(
                    "[Profil] Aucune sauvegarde de l'historique des mouvements du joueur trouvée - Création d'un historique vide");
            histo = new Historique();
        }
        this.historique = histo;
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
     * S'il en a pas, chargement du niveau par défaut
     */
    public void chargerGrille() {
        Grille g = null;
        File grille_repertoire = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu);
        File grille_fichier = new File(grille_repertoire.toString() + "/Matrice_" + id_niveau);
        if (grille_fichier.exists() && grille_fichier.length() > 0) {
            System.out.println(
                    "[Profil] Sauvegarde de la grille du niveau trouvée - Chargement de la grille du niveau sauvegardée...");
            Sauvegarder.chargerGrilleFichier(id_niveau, mode_de_jeu, false);
            System.out
                    .println("[Debug] deserialisationMatrice(grille_fichier)" + deserialisationMatrice(grille_fichier));
            g = new Grille(deserialisationMatrice(grille_fichier),
                    Sauvegarder.chargerGrilleFichier(id_niveau, mode_de_jeu, true), this.historique);
        } else {
            System.out.println(
                    "[Profil] Aucune sauvegarde de la grille du niveau trouvée - Chargement de la grille par défaut");
            g = new Grille(Sauvegarder.chargerGrilleFichier(id_niveau, mode_de_jeu, false),
                    Sauvegarder.chargerGrilleFichier(id_niveau, mode_de_jeu, true), this.historique);
        }
        this.grille = g;
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

    /**
     * retourne une liste de chaines de caracteres de l'emplacement des images, s'il
     * elle existe, sinon on charge celle par défaut
     *
     * @param joueur      le nom du joueur
     * @param mode_de_jeu le nom du mode de jeu
     * @return la liste des emplacements des images
     *
     */
    public static List<String> chargerImageNiveau(String joueur, String mode_de_jeu) {
        List<String> url_images = new ArrayList<String>();
        File mouvements_fichier = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu + "/"
                + "capture_niveau_");
        File placeholder_default = new File(Path.repertoire_jar.toString() + "/Nurikabe_grille.png");

        List<String> liste_fichiers = Sauvegarder.listeFichiers(mouvements_fichier.getParentFile());

        for (int i = 0; i < Sauvegarder.nbGrilles(mode_de_jeu); i++) {
            if (liste_fichiers.contains("capture_niveau_" + i + ".png"))
                url_images.add(new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu + "/"
                        + "capture_niveau_" + i + ".png").toString());
            else
                url_images.add(placeholder_default.toString());

        }

        return url_images;
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

    /**
     * getter pour l'historique des mouvements
     *
     * @return l'historique des mouvements
     */
    public Historique getHistorique() {
        return historique;
    }

    /**
     * Getter pour la grille du niveau
     * @return la grille du niveau
     */
    public Grille getGrille() {
        return grille;
    }


}
