package com.l3infogrp5.nurikabe.sauvegarde;

import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.utils.Path;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Classe pour sauvegarder le profil d'un joueur
 *
 * @author Guillaume Richard
 */
public class Sauvegarder {

    /**
     * Constructeur
     */
    public Sauvegarder() {
    }

    /**
     * Recherche si la sauvegarde pour le joueur existe déjà
     *
     * @param joueur nom du joueur
     * @return vrai si la sauvegarde existe, faux sinon
     */
    public static boolean RechercherSauvegarde(String joueur) {

        if (joueur == null) {
            return true;
        }

        File[] files = Path.repertoire_lvl.listFiles();
        if (files == null) {
            return true;
        }

        for (File file : files) {
            if (file.isDirectory() && file.getName().equals(joueur)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Recherche si la sauvegarde pour le joueur existe déjà
     *
     * @param joueur      nom du joueur
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau   l'id du niveau
     * @return vrai si la sauvegarde existe, faux sinon
     * @throws IOException {@link IOException} si le fichier n'existe pas
     */
    public static boolean RechercherSauvegardeNiveau(String joueur, ModeDeJeu mode_de_jeu, int id_niveau) throws IOException {

        Reader file_reader;
        if (!mode_de_jeu.equals(ModeDeJeu.DETENTE)) {
            try {
                file_reader = new FileReader(Path.repertoire_score + "/" + mode_de_jeu + ".save");
            } catch (FileNotFoundException e) {
                return false;
            }
        } else {
            try {
                file_reader = new FileReader(Path.repertoire_score + "/" + mode_de_jeu + "_" + id_niveau + ".save");
            } catch (FileNotFoundException e) {
                return false;
            }
        }
        System.out.println("[SAUVEGARDER] Chargement du fichier " + mode_de_jeu + "_" + id_niveau + ".save");
        BufferedReader bufferedReader = new BufferedReader(file_reader);

        boolean lastScoreInProgress = false;
        String enCours = "";
        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            String[] parts = line.split("%");
            String nom_joueur = parts[0].trim();
            enCours = parts[3].trim();
            if (nom_joueur.equals(joueur) && enCours.equals("true"))
                lastScoreInProgress = true;
        }
        bufferedReader.close();
        return lastScoreInProgress && enCours.equals("true");
    }


    /**
     * Scanne les fichiers et répertoires et les ajoutés dans une liste
     *
     * @param repertoire le répertoire à scanner
     * @return une liste de noms de fichiers/répertoires du repertoire donné en
     * paramètre
     */
    public static List<String> listeFichiers(File repertoire) {
        List<String> fichiers = new ArrayList<>();
        if (dossierExistants(repertoire)) {
            File[] files = repertoire.listFiles();
            if (files != null) {
                Arrays.sort(files, (f1, f2) -> {
                    try {
                        BasicFileAttributes f1Attr = Files.readAttributes(Paths.get(f1.toURI()), BasicFileAttributes.class);
                        BasicFileAttributes f2Attr = Files.readAttributes(Paths.get(f2.toURI()), BasicFileAttributes.class);
                        return f1Attr.creationTime().compareTo(f2Attr.creationTime());
                    } catch (IOException e) {
                        return 0;
                    }
                });

                for (File fichier : files) {
                    fichiers.add(fichier.getName());
                }
            }
        }
        return fichiers;
    }

    /**
     * Créer le dossier pour le joueur
     *
     * @param nom_joueur le nom du joueur
     * @throws IOException {@link IOException} si le dossier n'a pas pu être créé
     */
    public static void creerDossierJoueur(String nom_joueur) throws IOException {

        // Vérifier si le dossier "lvl" existe déjà dans "save"
        boolean niveau_existe = listeFichiers(Path.repertoire_courant).contains("lvl");
        if (!niveau_existe) {
            // Créer le dossier "lvl" s'il n'existe pas
            Files.createDirectories(Paths.get(Path.repertoire_lvl.toString()));
        }
        Files.createDirectories(Paths.get(Path.repertoire_lvl.toString() + "/" + nom_joueur));
    }

    /**
     * Création des dossiers nécessaires a la sauvegarde
     */
    public static void creerArborescence() {
        List<String> fichiers = listeFichiers(Path.repertoire_courant);

        try {
            // Crée les dossiers "save" et "score" s'ils n'existent pas
            Files.createDirectories(Path.repertoire_save.toPath());
            Files.createDirectories(Path.repertoire_score.toPath());

            // Crée les dossiers "profil" s'il n'existent pas
            boolean profil = fichiers.contains("profil");
            if (!profil) {
                Files.createDirectories(Paths.get(Path.repertoire_profils.toString()));
            }

            // verifie si les fichiers existent
            String[] files = {"endless.save", "clm.save", "detente.save"};
            for (String file : files) {
                boolean fileExists = fichiers.contains(file);
                if (!fileExists) {
                    if (!creerDossierFichier(Path.repertoire_score, new File(Path.repertoire_score + "/" + file))) {
                        System.out.println("[Sauvegarder] Erreur lors de la création du fichier " + file);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[Sauvegarde] Erreur lors de la création des répertoires nécessaires au jeu");
            e.printStackTrace();
        }
    }

    /**
     * Créer un fichier et un/ou plusieurs dossier(s) selon le chemin
     *
     * @param dossier les dossiers à créer (selon le chemin)
     * @param fichier le fichier à créer
     * @return true si creation(s) bien effectuée, false sinon
     */
    public static boolean creerDossierFichier(File dossier, File fichier) {
        boolean dossierCree = dossier.mkdirs();
        if (dossierCree) {
            System.out.println("[Sauvegarde] Directory " + dossier.getName() + " créé avec succès.");
        } else if (!dossier.exists()) {
            System.out.println("[Sauvegarde] Directory" + dossier.getName() + " n'a pas pu être créé.");
            return false;
        }
        try {
            boolean fichierCree = fichier.createNewFile();
            if (fichierCree) {
                System.out.println("[Sauvegarde] Fichier " + fichier.getName() + " créé avec succès.");
            } else if (!fichier.exists()) {
                System.out.println("[Sauvegarde] Fichier " + fichier.getName() + " n'a pas pu être créé.");
                return false;
            }
        } catch (IOException e) {
            System.err.println("[Sauvegarde] Erreur lors de la création du fichier" + fichier);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Vérifie s'il y a des dossiers dans le répertoire
     *
     * @param repertoire le repertoire
     * @return boolean, true s'il y a des dossiers dans le répertoire sinon false
     */
    public static boolean dossierExistants(File repertoire) {
        File[] fichiers = repertoire.listFiles();
        return fichiers != null;
    }

    /**
     * Append le score dans le fichier adéquat
     *
     * @param joueur      le nom du joueur/profil
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau   le numéro du niveau, -1 si en mode de jeu sans fin
     * @param score       le score
     * @param enCours     si le niveau est en cours ou non
     * @throws IOException {@link IOException} si le fichier n'existe pas
     **/
    public static void sauvegarderScore(String joueur, ModeDeJeu mode_de_jeu, int id_niveau, double score, boolean enCours) throws IOException {
        FileWriter writer;

        // Récupérer la date courante
        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

        // Formater la date en tant que chaîne de caractères en utilisant l'objet SimpleDateFormat
        String date_formate = format.format(date);

        if (!mode_de_jeu.equals(ModeDeJeu.DETENTE))
            writer = new FileWriter(Path.repertoire_score + "/" + mode_de_jeu + ".save", true);
        else writer = new FileWriter(Path.repertoire_score + "/" + mode_de_jeu + "_" + id_niveau + ".save", true);
        if (!mode_de_jeu.equals(ModeDeJeu.SANSFIN))
            writer.write(joueur + " % " + score + " % " + date_formate + " % " + enCours + "\n");
        else writer.write(joueur + " % " + score + " % " + date_formate + "\n");
        writer.close();

    }

    /**
     * Charge les scores d'un mode de jeu donné et selon l'indice du niveau
     *
     * @param joueur          le nom du joueur/profil
     * @param mode_de_jeu     le mode de jeu pour lequel les scores doivent être
     *                        chargés.
     * @param id_niveau       l'indice du niveau, négatif si en mode endless
     * @param niveau_en_cours si le niveau est en cours ou non
     * @return une liste de type DonneesScore, contenant tout les
     * scores du joueur
     * @throws IOException {@link IOException} si le fichier n'existe pas
     */
    public static List<DonneesScore> chargerScore(String joueur, ModeDeJeu mode_de_jeu, int id_niveau, boolean niveau_en_cours) throws IOException {
        System.out.println("Chargement des scores pour le joueur " + joueur + " en mode " + mode_de_jeu + "niveau_en_cours" + niveau_en_cours );
        List<DonneesScore> scores = new ArrayList<>();
        DonneesScore donneeScore = new DonneesScore();
        Reader file_reader;
        if (!mode_de_jeu.equals(ModeDeJeu.DETENTE)) {
            file_reader = new FileReader(Path.repertoire_score + "/" + mode_de_jeu + ".save");
        } else {
            file_reader = new FileReader(Path.repertoire_score + "/" + mode_de_jeu + "_" + id_niveau + ".save");
        }
        BufferedReader bufferedReader = new BufferedReader(file_reader);

        while (bufferedReader.ready()) {
            String line = bufferedReader.readLine();
            String[] parts = line.split("%");
            String nom_joueur = parts[0].trim();
            if (!nom_joueur.equals(joueur)) continue;
            String score = parts[1].trim();
            String date = parts[2].trim();
            String enCours = "";
            if (mode_de_jeu != ModeDeJeu.SANSFIN)
                enCours = parts[3].trim();
            else {
                enCours = "false";
            }
            if (!Boolean.parseBoolean(enCours) == niveau_en_cours) continue;
            System.out.println("score : " + score + " date : " + date + " en cours : " + enCours + " mode de jeu : " + mode_de_jeu);
            donneeScore.score = score;
            donneeScore.date = date;
            donneeScore.niveau_en_cours = enCours;
            scores.add(donneeScore);
        }
        bufferedReader.close();
        for (DonneesScore donneesScore : scores) {
            System.out.println("score : " + donneesScore.score + " date : " + donneesScore.date + " en cours : " + donneesScore.niveau_en_cours + " mode de jeu : " + mode_de_jeu);
        }
            return scores;
    }

    /**
     * Compte le nombre de niveaux implémentés
     *
     * @return le nombre de niveaux
     */
    public static int nbGrilles() {

        int nb_grilles;
//        TODO : modifier mode de jeu pour fichier des grilles
        try (InputStream inputStream = Sauvegarder.class.getClassLoader().getResourceAsStream("grilles/grilles_" + ModeDeJeu.DETENTE + ".txt")) {
            assert inputStream != null;
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(bufferedInputStream))) {
                Pattern pattern = Pattern.compile("^Grille\\s");
                nb_grilles = (int) reader.lines().filter(pattern.asPredicate()).count();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return nb_grilles;
    }

    /**
     * Charge une grille depuis un fichier texte selon le numéro de la grille et le
     * mode de jeu
     *
     * @param id_niveau le numéro de la grille
     * @param solution  boolean, vrai s'il faut charger la solution du niveau
     *                  selon l'id
     * @return la matrice du niveau chargé
     * @throws FileNotFoundException {@link FileNotFoundException} si le fichier n'est pas trouvé
     */
    public static int[][] chargerGrilleFichier(int id_niveau, Boolean solution) throws FileNotFoundException {

//    TODO : Modifier si split niveaux en différents fichiers
        String nom_fichier = "grilles_" + ModeDeJeu.DETENTE;
        if (solution) {
            nom_fichier += "_solutions";
        }
        nom_fichier += ".txt";

        InputStream inputStream = Sauvegarder.class.getClassLoader().getResourceAsStream("grilles/" + nom_fichier);
        if (inputStream == null) {
            throw new FileNotFoundException("Fichier pas trouvé: " + nom_fichier);
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        int[][] grille = null;
        int lignes = -1;
        int colonnes = 0;
        boolean grille_courante = false;
        int index = 0;
        int num_niveau = 0;
        try {
            String ligne;
            while ((ligne = bufferedReader.readLine()) != null && index != lignes) {
                if (ligne.startsWith("Grille ") && num_niveau == id_niveau) {
                    // Si une grille a été trouvée, on récupère ses dimensions
                    String[] dimensions = ligne.split("\\(")[1].split("\\)")[0].split(";");
                    lignes = Integer.parseInt(dimensions[0].trim());
                    colonnes = Integer.parseInt(dimensions[1].trim());
                    grille = new int[lignes][colonnes];
                    grille_courante = true;
                } else if (ligne.startsWith("#")) {
                    // Ignore les lignes commentées
                    grille_courante = false;
                } else if (ligne.startsWith("Grille") && grille_courante) {
                    break;
                } else if (grille_courante) {
                    String[] values = ligne.split(" ");
                    for (int i = 0; i < colonnes; i++) {
                        if (!values[i].equals("")) {
                            grille[index][i] = Integer.parseInt(values[i]);
                        }
                    }
                    index++; // incrémente indice de la ligne
                } else if (ligne.startsWith("Grille ") && num_niveau != id_niveau) {
                    num_niveau++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return grille;
    }

    /**
     * Sauvegarde l'historique des mouvements
     *
     * @param joueur      le nom du joueur/profil
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau   l'id du niveau
     * @param historique  l'historique des mouvements
     */
    public static void sauvegarderHistorique(String joueur, ModeDeJeu mode_de_jeu, int id_niveau, Historique historique) {
        File mouvements_repertoire = new File(Path.repertoire_lvl + "/" + joueur + "/" + mode_de_jeu);
        File mouvements_fichier = new File(mouvements_repertoire + "/Mouvements_" + id_niveau + ".hist");

        if (creerDossierFichier(mouvements_repertoire, mouvements_fichier)) {
            System.out.println("[Sauvegarde] Fichier de sauvegarde de l'historique des mouvements créé / deja existant");
            serialisationHistorique(mouvements_fichier, historique);
        } else {
            System.out.println("[Sauvegarde] Erreur lors de la création de fichier et/ou de dossier");
        }

    }


    /*
     * Gestion des fichiers de sauvegarde
     */

    /**
     * Serialisation de l'historique des mouvements
     *
     * @param repertoire le répertoire
     * @param historique l'historique des mouvements
     */
    private static void serialisationHistorique(File repertoire, Historique historique) {
        try {
            FileOutputStream fichier_sortie = new FileOutputStream(repertoire, false); // false écrase le fichier
            ObjectOutputStream sortie = new ObjectOutputStream(fichier_sortie);
            sortie.writeObject(historique);
            sortie.close();
            fichier_sortie.close();

            System.out.println("[Sauvegarde] Historique des mouvements sérialisé et sauvegardé dans Mouvements_<id_niveau>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Désérialise l'historique des mouvements à partir d'un fichier.
     *
     * @param fichier le fichier sérialisé des mouvements
     * @return l'historique des mouvements désérialisé
     */
    public static Historique deserialiserHistorique(File fichier) {
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
     * Sauvegarde la grille
     *
     * @param joueur      le nom du joueur/profil associé à la grille
     * @param mode_de_jeu le mode de jeu associé à la grille
     * @param id_niveau   l'id du niveau associé à la grille
     * @param matrice     la matrice du niveau a sauvegarder
     */
    public static void sauvegarderMatrice(String joueur, ModeDeJeu mode_de_jeu, int id_niveau, int[][] matrice) {
        File matrice_repertoire = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu);
        File matrice_fichier = new File(matrice_repertoire + "/Matrice_" + id_niveau + ".mat");

        if (Sauvegarder.creerDossierFichier(matrice_repertoire, matrice_fichier)) {
            serialiserMatrice(matrice_fichier, matrice);
        } else {
            System.out.println("[Profil] Erreur lors de la création de fichier et/ou de dossier");
        }
    }

    /**
     * Serialisation de la matrice de la grille
     *
     * @param repertoire le répertoire
     * @param matrice    la matrice de la grille
     */
    private static void serialiserMatrice(File repertoire, int[][] matrice) {
        try {
            FileOutputStream fichier_sortie = new FileOutputStream(repertoire, false);
            ObjectOutputStream sortie = new ObjectOutputStream(fichier_sortie);
            sortie.writeObject(matrice);
            sortie.close();
            fichier_sortie.close();

            System.out.println("[Sauvegarde] Matrice sérialisé et sauvegardé dans Matrice_<id_niveau>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Désérialise la grille à partir du fichier.
     *
     * @param fichier le fichier sérialisé de la grille
     * @return la matrice du niveau désérialisé
     */
    public static int[][] deserialiserMatrice(File fichier) {
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
     * Supprime le profil du joueur
     *
     * @param nom_joueur le nom du joueur
     * @throws IOException lancé lorsque le dossier correspondant n'a pas pû être lu.
     */
    public static void supprimerProfil(String nom_joueur) throws IOException {
        File repertoire = new File(Path.repertoire_lvl + "/" + nom_joueur);
        if (repertoire.exists()) {
            FileUtils.deleteDirectory(repertoire);
            System.out.println("[Sauvegarde] Profil supprimé");
        }
    }

    /**
     * Type de données pour le score
     */
    public static class DonneesScore {

        /**
         * Le score
         */
        public String score;
        /**
         * La date
         */
        public String date;
        /**
         * Le niveau en cours
         */
        public String niveau_en_cours;

        /**
         * Constructeur
         */
        public DonneesScore() {
            this.score = "";
            this.date = "";
            this.niveau_en_cours = "";
        }


        /**
         * Construit l'affichage des données du score
         *
         * @return la chaine de caractère à afficher
         */
        public String toString() {
            return score + " " + date + " " + niveau_en_cours;
        }

        /**
         * Récupère le score
         *
         * @return le score
         */
        public String getScore() {
            return score;
        }

        /**
         * Récupère la date
         *
         * @return la date
         */
        public String getDate() {
            return date;
        }

        /**
         * Récupère le boolean pour savoir si le niveau en cours
         *
         * @return le boolean du niveau en cours
         */
        public boolean getNiveauEnCours() {
            return Boolean.getBoolean(niveau_en_cours);
        }


    }
}
