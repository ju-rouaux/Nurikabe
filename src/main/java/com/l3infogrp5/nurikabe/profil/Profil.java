package com.l3infogrp5.nurikabe.profil;

import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
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
    private static int id_niveau;

    /**
     * Création d'un profil.
     * {@link #chargerProfil}
     *
     * @throws IOException si le fichier de sauvegarde n'existe pas
     */
    private Profil() throws IOException {
        joueur = "default";
        mode_de_jeu = "detente";
        id_niveau = 0;

        if (Sauvegarder.RechercherSauvegarde(joueur)) {
            System.out.println("[Profil] Profil deja existant");
        } else {
            Sauvegarder.creerDossierJoueur(joueur);
        }
    }

    /**
     * Retourne l'instance du profil.
     *
     * @return l'instance unique de la classe Profil
     * @throws IOException si le fichier de sauvegarde n'existe pas
     */
    public static Profil getInstance() throws IOException {
        if (instance == null) {
            instance = new Profil();
        }
        return instance;
    }


    /**
     * //TODO a utiliser
     * retourne une liste de chaines de caractères de l'emplacement des images, s'il
     * elle existe, sinon on charge celle par défaut
     *
     * @return la liste des emplacements des images
     * @throws FileNotFoundException si le fichier n'existe pas
     */
    public static List<String> chargerImageNiveau() throws IOException {
        List<String> url_images = new ArrayList<>();
        String mdj = getMode_de_jeu();
        String joueur = getJoueur();
        File image_grille = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mdj + "/" +
            "capture_niveau_" + getIdNiveau() + ".png");
        File placeholder_default = new File(Path.repertoire_jar.toString() + "/img/placeholder.png");

        List<String> liste_fichiers = Sauvegarder.listeFichiers(image_grille.getParentFile());

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
     * Getter pour le nom du joueur du profil
     *
     * @return le nom du joueur
     */
    public static String getJoueur() {
        return joueur;
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
        mode_de_jeu = mdj;
        //TODO temporaire : mode de jeu detente
        mode_de_jeu = "detente";
    }

    /**
     * Getter pour l'identifiant du niveau
     *
     * @return l'historique des mouvements
     */
    public static int getIdNiveau() {
        return id_niveau;
    }

    /**
     * Methode pour charger un profil
     * Attention, il faut ensuite initialiser le mode de jeu avec le setter {@link #setMode_de_jeu(String)}.
     * Et l'id du niveau en paramètre de la méthode chargerGrille {@link #chargerGrille(int)}.
     *
     * @param joueur le nom du joueur
     * @throws IOException {@link IOException} exception levée si une erreur survient lors du chargement du profil
     */
    public void chargerProfil(String joueur) throws IOException {
        donneesNiveau = new DonneesNiveau();
        Profil.joueur = joueur;
        // Valeurs par défaut
        mode_de_jeu = "detente";
        id_niveau = 0;

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
        Sauvegarder.sauvegarderMatrice(joueur, mode_de_jeu, id_niveau, niveau.getMatrice());
        Sauvegarder.sauvegarderHistorique(joueur, mode_de_jeu, id_niveau, niveau.getHistorique());
    }

    /**
     * Charge l'historique des mouvements du joueur
     * S'il n'y en a pas, création d'un historique vierge
     *
     * @return l'historique des mouvements du joueur
     */
    public Historique chargerHistorique() {
        Historique hist;
        System.out.println("Mode de jeu : " + mode_de_jeu);
        File fichier_mouvements = Paths.get(Path.repertoire_lvl.toString(), joueur, mode_de_jeu, "Mouvements_" + id_niveau + ".hist").toFile();

        if (fichier_mouvements.exists() && fichier_mouvements.length() > 0) {
            System.out.println(
                "[Profil] Sauvegarde de l'historique des mouvements du joueur trouvée - Chargement de l'historique des mouvements du joueur sauvegardé...");
            hist = Sauvegarder.deserialiserHistorique(fichier_mouvements);
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
     * @throws FileNotFoundException si le fichier n'existe pas
     */
    public DonneesNiveau chargerGrille(int niv) throws FileNotFoundException {
        id_niveau = niv;
        File grille_repertoire = new File(Path.repertoire_lvl + "/" + joueur + "/" + mode_de_jeu);
        File grille_fichier = new File(grille_repertoire + "/Matrice_" + id_niveau + ".mat");
        if (grille_fichier.exists() && grille_fichier.length() > 0) {
            System.out.println(
                "[Profil] Sauvegarde de la grille du niveau trouvée - Chargement de la grille du niveau sauvegardée...");
            //            TODO : Temporaire -> lit les grilles dans le fichier detente

            Sauvegarder.chargerGrilleFichier(id_niveau, "detente", false);
//            Sauvegarder.chargerGrilleFichier(this.id_niveau, mode_de_jeu, false);
            donneesNiveau.matrice_niveau = Sauvegarder.deserialiserMatrice(grille_fichier);
        } else {
//            TODO : Temporaire -> lit les grilles dans le fichier detente
//            donneesNiveau.matrice_niveau = Sauvegarder.chargerGrilleFichier(this.id_niveau, this.mode_de_jeu, false);
            donneesNiveau.matrice_niveau = Sauvegarder.chargerGrilleFichier(id_niveau, "detente", false);
        }
        //            TODO : Temporaire -> lit les grilles dans le fichier detente

//        donneesNiveau.matrice_solution = Sauvegarder.chargerGrilleFichier(this.id_niveau, this.mode_de_jeu, true);
        donneesNiveau.matrice_solution = Sauvegarder.chargerGrilleFichier(id_niveau, "detente", true);

        return donneesNiveau;
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
