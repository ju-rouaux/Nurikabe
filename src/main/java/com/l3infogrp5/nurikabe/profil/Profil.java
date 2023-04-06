package com.l3infogrp5.nurikabe.profil;

import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.ModeDeJeu;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private static final Profil instance;
    /* Les données du niveau */
    private static DonneesNiveau donneesNiveau;
    /* Le nom du joueur */
    private static String joueur;
    /* Le mode de jeu */
    private static ModeDeJeu mode_de_jeu;
    /* L'identifiant du niveau représenté par un numéro */
    private static int id_niveau;
    private static List<Sauvegarder.DonneesScore> score;

    static {
        try {
            instance = new Profil();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Création d'un profil.
     * {@link #chargerProfil}
     *
     * @throws IOException si le fichier de sauvegarde n'existe pas
     */
    private Profil() throws IOException {
        joueur = "default";
        mode_de_jeu = ModeDeJeu.DETENTE;
        id_niveau = 0;
        score = null;

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
     */
    public static Profil getInstance() {
        return instance;
    }


    /**
     * retourne une liste de chaines de caractères de l'emplacement des images, s'il
     * elle existe, sinon on charge celle par défaut
     *
     * @return la liste des emplacements des images
     * @throws IOException si le fichier de sauvegarde n'existe pas
     */
    public static List<String> chargerImageNiveau() throws IOException {
        List<String> url_images = new ArrayList<>();
        ModeDeJeu mdj = getMode_de_jeu();
        String joueur = getJoueur();
        File image_grille = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mdj + "/" +
            "capture_niveau_" + getIdNiveau() + ".png");

        InputStream placeholder_default = Profil.class.getClassLoader().getResourceAsStream("img/placeholder.png");
        if (placeholder_default == null) {
            throw new FileNotFoundException("[Profil] placeholder_default.png n'a pas été trouvé");
        }

        // Sauvegarde placeholder_default.png dans un fichier temporaire
        //  Supprimé quand le programme se termine
        File placeholder_temp = File.createTempFile("placeholder_default", ".png");
        placeholder_temp.deleteOnExit();
        Files.copy(placeholder_default, placeholder_temp.toPath(), StandardCopyOption.REPLACE_EXISTING);

        List<String> liste_fichiers = Sauvegarder.listeFichiers(image_grille.getParentFile());

        for (int i = 0; i < Sauvegarder.nbGrilles(); i++) {
            if (liste_fichiers.contains("capture_niveau_" + i + ".png"))
                url_images.add(new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mdj + "/" +
                    "capture_niveau_" + i + ".png").toString());
            else
                url_images.add(placeholder_temp.toString());
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
    public static ModeDeJeu getMode_de_jeu() {
        return mode_de_jeu;
    }

    /**
     * Setter pour le mode de jeu
     *
     * @param mdj le mode de jeu courant
     */
    public void setMode_de_jeu(ModeDeJeu mdj) {
        mode_de_jeu = mdj;
    }

    /**
     * Setter pour le score du joueur
     *
     * @param score le score du joueur
     * @param enCours si le niveau est en cours ou non
     * @throws IOException si le fichier de sauvegarde n'existe pas
     */
    public static void setScore(double score, boolean enCours) throws IOException {
        Sauvegarder.sauvegarderScore(joueur, mode_de_jeu, id_niveau, score, enCours);
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
     * Getter pour le score du niveau
     *
     * @return le score du niveau
     * @throws IOException si le fichier de sauvegarde n'existe pas
     */
    public static Sauvegarder.DonneesScore getDonneesScore() throws IOException {
//        Sauvegarder.DonneesScore score_temp = new Sauvegarder.DonneesScore();
        if (Sauvegarder.RechercherSauvegardeNiveau(joueur,mode_de_jeu, id_niveau)) {
            System.out.println("Sauvegarde existante ? : " + Sauvegarder.RechercherSauvegardeNiveau(joueur,mode_de_jeu, id_niveau));
            score = Sauvegarder.chargerScore(mode_de_jeu, id_niveau);
            if (score.size() > 0) {
                for (Sauvegarder.DonneesScore s : score) {
                    if (s.getNiveauEnCours()) {
                        donneesNiveau.donneesScore = s;
                    }
                }
                return donneesNiveau.donneesScore;
            }

        }
        if (mode_de_jeu.equals(ModeDeJeu.DETENTE)) {
            donneesNiveau.donneesScore.score = "5";
            return donneesNiveau.donneesScore;
        }
        donneesNiveau.donneesScore.score = "0";
        return donneesNiveau.donneesScore;
    }

    /**
     * Methode pour charger un profil
     * Attention, il faut ensuite initialiser le mode de jeu avec le setter {@link #setMode_de_jeu(ModeDeJeu)}.
     * Et l'id du niveau en paramètre de la méthode chargerGrille {@link #chargerGrille(int)}.
     *
     * @param joueur le nom du joueur
     * @throws IOException {@link IOException} exception levée si une erreur survient lors du chargement du profil
     */
    public void chargerProfil(String joueur) throws IOException {
        donneesNiveau = new DonneesNiveau();
        donneesNiveau.donneesScore = new Sauvegarder.DonneesScore();
        Profil.joueur = joueur;
        // Valeurs par défaut
        mode_de_jeu = ModeDeJeu.DETENTE;
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
        try {
            Sauvegarder.supprimerScores(mode_de_jeu, id_niveau);
        } catch (IOException ignored) {
        }
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
        File fichier_mouvements = Paths.get(Path.repertoire_lvl.toString(), joueur, mode_de_jeu.toString(), "Mouvements_" + id_niveau + ".hist").toFile();

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
            Sauvegarder.chargerGrilleFichier(id_niveau, false);
            donneesNiveau.matrice_niveau = Sauvegarder.deserialiserMatrice(grille_fichier);
        } else
            donneesNiveau.matrice_niveau = Sauvegarder.chargerGrilleFichier(id_niveau, false);

        donneesNiveau.matrice_solution = Sauvegarder.chargerGrilleFichier(id_niveau, true);

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
     * Charger le score du niveau
     * @param id_niveau l'id du niveau
     * @return le score du niveau
     * @throws IOException {@link IOException} exception levée si une erreur survient lors du chargement du score
     */
    public Sauvegarder.DonneesScore chargerScore(int id_niveau) throws IOException {
        if (Sauvegarder.RechercherSauvegardeNiveau(joueur,mode_de_jeu, id_niveau)){
            System.out.println("[Profil] Sauvegarde du score du niveau trouvée - Chargement du score du niveau sauvegardé...");
            Sauvegarder.supprimerScores(mode_de_jeu, id_niveau);
            return Sauvegarder.chargerScore(mode_de_jeu, id_niveau).get(0);
        }
        else {
            System.out.println("[Profil] Aucune sauvegarde du score du niveau trouvée - Création d'un score vide");
            return getDonneesScore();
        }
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

        /** Les données de score */
        public Sauvegarder.DonneesScore donneesScore;

        /**
         * Constructeur privé.
         */
        private DonneesNiveau() {
        }
    }

}
