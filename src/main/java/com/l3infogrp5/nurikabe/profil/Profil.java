package com.l3infogrp5.nurikabe.profil;

import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.Path;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un profil de joueur.
 * Elle permet de charger et sauvegarder des grilles de jeu, ainsi que l'historique des mouvements effectués sur la grille.
 * Elle permet également de récupérer des informations sur le joueur et le niveau en cours.
 *
 * @author Guillaume Richard
 */
public class Profil {

    /* L'instance du profil */
    private static Profil instance = null;

    /* Les données du niveau */
    private static DonneesNiveau donneesNiveau;
    /* Le nom du joueur */
    private static String joueur;
    /* Le mode de jeu */
    private static String mode_de_jeu;
    /* L'identifiant du niveau représenté par un numéro */
    private int id_niveau;

    /**
     * Création d'un profil.
     */
    private Profil(){
    }

    /**
     * Retourne l'instance du profil.
     *
     * @return l'instance unique de la classe Profil
     */
    public static Profil getInstance() {
        if (instance == null) {
            instance = new Profil();
        }
        return instance;
    }

    /**
     * Désérialise l'historique des mouvements à partir d'un fichier.
     *
     * @param fichier le fichier sérialisé des mouvements
     * @return l'historique des mouvements désérialisé
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
     * Désérialise la grille à partir du fichier.
     *
     * @param fichier le fichier sérialisé de la grille
     * @return la matrice du niveau désérialisé
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
     * //TODO a utiliser
     * retourne une liste de chaines de caractères de l'emplacement des images, s'il
     * elle existe, sinon on charge celle par défaut
     *
     * @return la liste des emplacements des images
     */
    public static List<String> chargerImageNiveau() {
        List<String> url_images = new ArrayList<>();
        String mdj = getMode_de_jeu();
        String joueur = getJoueur();
        File mouvements_fichier = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mdj + "/" +
            "capture_niveau_");
        File placeholder_default = new File(Path.repertoire_jar.toString() + "/Nurikabe_grille.png");

        List<String> liste_fichiers = Sauvegarder.listeFichiers(mouvements_fichier.getParentFile());

        for (int i = 0; i < Sauvegarder.nbGrilles(mdj); i++) {
            if (liste_fichiers.contains("capture_niveau_" + i + ".png"))
                url_images.add(new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mdj + "/" +
                    "capture_niveau_" + i + ".png").toString());
            else
                url_images.add(placeholder_default.toString());

        }

        return url_images;
    }

    /**
     * Methode pour charger un profil
     * Attention, il faut ensuite initialiser le mode de jeu avec le setter {@link #setMode_de_jeu(String)}.
     * Et l'id du niveau en paramètre de la méthode chargerGrille {@link #chargerGrille(int)}.
     * @param joueur le nom du joueur
     * @throws IOException {@link IOException} exception levée si une erreur survient lors du chargement du profil
     */
    public void chargerProfil(String joueur) throws IOException {
        donneesNiveau = new DonneesNiveau();
        this.joueur = joueur;
        // Valeurs par défaut
        this.mode_de_jeu = "detente";
        this.id_niveau = 0;

        if (Sauvegarder.RechercherSauvegarde(joueur)) {
            System.out.println("[Profil] Profil deja existant");
        } else {
            Sauvegarder.creerDossierJoueur(joueur);
        }
    }

    /**
     * Sauvegarde le niveau deja commencé
     *
     * @param niveau la grille du niveau à sauvegarder
     */
    public void sauvegarderNiveau(Grille niveau) {
        // sauvegarder le niveau correspondant au profil
        Sauvegarder.sauvegardeMatrice(this.joueur, this.mode_de_jeu, this.id_niveau, niveau.getMatrice());
        Sauvegarder.sauvegarderHistorique(this.joueur, this.mode_de_jeu, this.id_niveau, niveau.getHistorique());
    }

    /**
     * Charge l'historique des mouvements du joueur
     * S'il n'y en a pas, création d'un historique vierge
     *
     * @return l'historique des mouvements du joueur
     */
    public Historique chargerHistorique() {
        Historique hist;
        File fichier_mouvements = new File(
            Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu + "/Mouvements_" + this.id_niveau);
        if (fichier_mouvements.exists() && fichier_mouvements.length() > 0) {
            System.out.println(
                "[Profil] Sauvegarde de l'historique des mouvements du joueur trouvée - Chargement de l'historique des mouvements du joueur sauvegardé...");

            hist = deserialisationHistorique(fichier_mouvements);
        } else {
            System.out.println(
                "[Profil] Aucune sauvegarde de l'historique des mouvements du joueur trouvée - Création d'un historique vide");
            hist = new Historique();
        }
        donneesNiveau.historique = hist;
        return hist;
    }

    /**
     * Charge la grille à partir du fichier.
     * S'il n'y en a pas, chargement du niveau par défaut
     *
     * @param niv l'indice du niveau à charger
     * @return les données du niveau
     */
    public DonneesNiveau chargerGrille(int niv) {
        this.id_niveau = niv;
        File grille_repertoire = new File(Path.repertoire_lvl + "/" + this.joueur + "/" + this.mode_de_jeu);
        File grille_fichier = new File(grille_repertoire + "/Matrice_" + this.id_niveau);
        if (grille_fichier.exists() && grille_fichier.length() > 0) {
            System.out.println(
                "[Profil] Sauvegarde de la grille du niveau trouvée - Chargement de la grille du niveau sauvegardée...");
            Sauvegarder.chargerGrilleFichier(this.id_niveau, this.mode_de_jeu, false);
            donneesNiveau.matrice_niveau = deserialisationMatrice(grille_fichier);
        } else {
            donneesNiveau.matrice_niveau = Sauvegarder.chargerGrilleFichier(this.id_niveau, this.mode_de_jeu, false);
        }
        donneesNiveau.matrice_solution = Sauvegarder.chargerGrilleFichier(this.id_niveau, this.mode_de_jeu, true);

        return donneesNiveau;
    }

    /**
     * Getter pour le nom du joueur du profil
     *
     * @return le nom du joueur
     */
    public static String getJoueur() {
        return joueur;
    }

    /**
     * Getter pour l'identifiant du niveau
     *
     * @return l'historique des mouvements
     */
    public int getId_niveau() {
        return id_niveau;
    }

    /**
     * Getter pour le nom du mode de jeu
     *
     * @return l'id du niveau
     */
    public static String getMode_de_jeu() {
        return mode_de_jeu;
    }

    /**
     * Setter pour le mode de jeu
     *
     * @param mdj le mode de jeu courant
     */
    public void setMode_de_jeu(String mdj) {
        this.mode_de_jeu = mdj;
    }

    /**
     * Getter pour l'historique des mouvements
     *
     * @return l'historique des mouvements
     */
    public Historique getHistorique() {
        return donneesNiveau.historique;
    }

    /**
     * Classe interne permettant de stocker les données d'un niveau.
     */
    public static class DonneesNiveau {
        /**
         * L'historique du niveau.
         */
        public Historique historique;
        /**
         * La grille du niveau.
         */
        public int[][] matrice_niveau;
        /**
         * La grille de solution du niveau.
         */
        public int[][] matrice_solution;

        /**
         * Constructeur privé.
         */
        private DonneesNiveau() {
        }
    }

}
