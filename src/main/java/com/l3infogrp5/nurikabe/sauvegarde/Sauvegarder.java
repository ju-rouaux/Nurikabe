package com.l3infogrp5.nurikabe.sauvegarde;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.utils.Path;

/**
 *
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
     *
     * Recherche si la sauvegarde pour le joueur existe déjà
     *
     * @param joueur nom du joueur
     *
     * @return vrai si la sauvegarde existe, faux sinon
     */
    public static boolean RechercherSauvegarde(String joueur) {
        // Vérifier si le nom du joueur est nul
        if (joueur == null)
            return false;

        // Récupère tous les fichiers dans le répertoire
        // Vérifier si le répertoire existe et s'il contient des fichiers
        if (!dossierExistants(Path.repertoire_lvl) ||
                Path.repertoire_lvl.listFiles().length == 0) {
            // System.out.println("Il n'y pas de fichiers ou dossiers");
            return false;
        }

        // Parcourt tous les fichiers pour voir s'il y a une sauvegarde pour le joueur
        for (File fichier : Path.repertoire_lvl.listFiles()) {
            if (fichier.isDirectory() && fichier.getName().equals(joueur)) {
                // System.out.println("La sauvegarde du joueur existe");
                return true;
            }
        }
        return false;
    }

    /**
     * Affiche tous les fichiers dans le répertoire
     *
     * @param repertoire le répertoire à scanner
     */
    public void afficherFichiers(File repertoire) {
        if (dossierExistants(repertoire)) {
            for (File fichier : repertoire.listFiles()) {
                if (fichier.isFile())
                    System.out.println("[Sauvegarde] Fichier : " + fichier.getName());
                else if (fichier.isDirectory())
                    System.out.println("[Sauvegarde] Repertoire : " + fichier.getName());
            }
        }
    }

    /**
     *
     * Scanne les fichiers et répertoires et les ajoutes dans une liste
     *
     * @param repertoire le répertoire à scanner
     * @return une liste de noms de fichiers/répertoires du repertoire donné en
     *         parametre
     */
    private static List<String> listeFichiers(File repertoire) {
        List<String> fichiers = new ArrayList<String>();
        if (dossierExistants(repertoire)) {
            for (File fichier : repertoire.listFiles()) {
                fichiers.add(fichier.getName());
            }
        }
        return fichiers;
    }

    /**
     * Creer le dossier pour le joueur
     *
     * @param nom_joueur le nom du joueur
     * @throws IOException {@link IOException}
     */
    public static void creerDossierJoueur(String nom_joueur) throws IOException {

        // Vérifier si le dossier "lvl" existe déjà dans "save"
        boolean niveau_existe = listeFichiers(Path.repertoire_courant).contains("lvl");
        if (!niveau_existe) {
            // Créer le dossier "lvl" si il n'existe pas
            Files.createDirectories(Paths.get(Path.repertoire_lvl.toString()));
        }
        Files.createDirectories(Paths.get(Path.repertoire_lvl.toString() + "/" + nom_joueur));
    }

    /**
     * Création des dossiers necessaires a la sauvegarde
     */
    public static void creerArborescence() {

        List<String> fichiers = listeFichiers(Path.repertoire_courant);

        try {
            // Vérifier si le dossier "save" existe déjà
            boolean sauvegarde_existe = fichiers.contains("save");
            if (!sauvegarde_existe) {
                // Créer le dossier "save" si il n'existe pas
                Files.createDirectories(Paths.get(Path.repertoire_save.toString()));
                // System.out.println("Dossier save créé");
            }

            // Vérifier si le dossier "score" existe déjà dans "save"
            boolean score_existe = fichiers.contains("score");
            if (!score_existe) {
                // Créer le dossier "score" si il n'existe pas
                Files.createDirectories(Paths.get(Path.repertoire_score.toString()));
                // System.out.println("Dossier score créé");
            }

            boolean placeholder_images_existe = fichiers.contains("placeholder_images");
            if (!placeholder_images_existe) {
                // Créer le dossier "placeholder_images" si il n'existe pas
                Files.createDirectories(Paths.get(Path.repertoire_images.toString()));
                // System.out.println("Dossier placeholder_images créé");

            }


            // Verification des fichiers "endless" et "detente"
            fichiers = listeFichiers(Path.repertoire_score);
            boolean endless = fichiers.contains("endless.save");
            if (!endless) {
                if (!creerDossierFichier(Path.repertoire_score,
                        new File(Path.repertoire_score.toString() + "/endless.save")))
                    System.out.println("[Sauvegarder] Erreur lors de la création du fichier endless.save");
            }
            boolean clm = fichiers.contains("clm.save");
            if (!clm) {
                if (!creerDossierFichier(Path.repertoire_score,
                        new File(Path.repertoire_score.toString() + "/clm.save")))
                    System.out.println("[Sauvegarder] Erreur lors de la création du fichier endless.save");
            }
            boolean detente = fichiers.contains("detente.save");
            if (!detente) {
                if (!creerDossierFichier(Path.repertoire_score,
                        new File(Path.repertoire_score.toString() + "/detente.save")))
                    System.out.println("[Sauvegarder] Erreur lors de la création du fichier endless.save");
            }

        } catch (IOException e) {
            System.err.println("[Sauvegarde] Erreur lors de la création des répertoires nécessaires au jeu");
            e.printStackTrace();

        }
    }

    /**
     * Créer un fichier et un/ou plusieurs dossier(s) selon le chemin
     *
     * @param dossier les dossier à créer (selon le chemin)
     * @param fichier le fichier à créer
     * @return true si creation(s) bien effectuée, false sinon
     */
    public static boolean creerDossierFichier(File dossier, File fichier) {
        boolean statut = false;
        if (!dossier.exists()) {
            boolean resultat = dossier.mkdirs();
            if (resultat) {
                System.out.println("[Sauvegarde] Directory " + dossier.getName() + " créé avec succès.");
                statut = true;
            } else {
                System.out.println("[Sauvegarde] Directory" + dossier.getName() + " n'a pas pu être créé.");
                return false;
            }
        } else {
            statut = true;
        }

        try {
            if (fichier.createNewFile() || fichier.exists()) {
                System.out.println("[Sauvegarde] Fichier " + fichier.getName() + " créé avec succès ou deja existant.");
                statut = true;
            } else {
                System.out.println("[Sauvegarde] Fichier " + fichier.getName() + " n'a pas pu être créé.");
                return false;
            }
        } catch (IOException e) {
            System.err.println("[Sauvegarde] Erreur lors de la création du fichier" + fichier.toString());
            e.printStackTrace();
        }
        return statut;
    }

    /**
     * Verifie s'il y a des dossiers dans le répertoire
     *
     * @param repertoire le repertoire
     * @return boolean, true s'il y a des dossiers dans le répertoire sinon false
     */
    public static boolean dossierExistants(File repertoire) {
        File[] fichiers = repertoire.listFiles();
        if (fichiers == null)
            return false;
        else
            return true;
    }

    /**
     * Sauvegarde la matrice de la grille
     *
     * @param joueur      le nom du joueur/profil
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau   l'id du niveau
     * @param matrice     la grille
     */
    public static void sauvegarderMatrice(String joueur, String mode_de_jeu, int id_niveau, int[][] matrice) {
        File matrice_repertoire = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu);
        File matrice_fichier = new File(matrice_repertoire.toString() + "/Matrice_" + id_niveau);

        if (creerDossierFichier(matrice_repertoire, matrice_fichier)) {
            serialisationMatrice(matrice_fichier, matrice);
        } else {
            System.out.println("[Sauvegarde] Erreur lors de la création de fichier et/ou de dossier");
        }
    }

    /**
     * Append le score dans le fichier adequat
     *
     * @param joueur      le nom du joueur/profil
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau   le numero du niveau, -1 si en mode de jeu sans fin
     * @throws IOException {@link IOException}
     **/
    public static void sauvegarderScore(String joueur, String mode_de_jeu, int id_niveau) throws IOException {

        // Récupérer la date courante
        Date date = new Date();

        // Créer un objet SimpleDateFormat avec le format souhaité
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

        // Formater la date en tant que chaîne de caractères en utilisant l'objet
        // SimpleDateFormat
        String date_formate = format.format(date);

        FileWriter writer = new FileWriter(new File(Path.repertoire_score + "/" + mode_de_jeu + ".save"), true);

        if (mode_de_jeu.equals("endless"))
            writer.write(joueur + " % " + getScore() + " % " + date_formate + "\n");
        else
            writer.write(joueur + " % " + getScore() + " % " + id_niveau + " % " + date_formate + "\n");

        writer.close();

    }

    /**
     * Methode de test en attendant le getscore du controller score
     *
     * @return un score bidon
     */
    private static String getScore() {
        return "1:30";
    }

    /**
     * Charge les scores d'un mode de jeu donné et selon l'indice du niveau
     *
     * @param mode_de_jeu le mode de jeu pour lequel les scores doivent être
     *                    chargés.
     * @param id_niveau   l'indice du niveau, negatif si en mode endless
     * @return un hashmap : [Nom du joueur[ date - le score]]], contenant tout les
     *         scores du fichier
     * @throws IOException {@link IOException}
     */
    public static HashMap<String, HashMap<String, String>> chargerScore(String mode_de_jeu, int id_niveau)
            throws IOException {
        HashMap<String, HashMap<String, String>> scores = new HashMap<>();
        InputStream inputStream = new FileInputStream(new File(Path.repertoire_score + "/" + mode_de_jeu + ".save"));
        Scanner scanner = new Scanner(inputStream);
        boolean niveaux = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("%");
            String nom_joueur = parts[0].trim();
            String score = parts[1].trim();

            if (id_niveau >= 0 && parts[2].trim().equals(Integer.toString(id_niveau))) {
                niveaux = true;
                String date = parts[3].trim();
                HashMap<String, String> infos_niveau = new HashMap<>();
                infos_niveau.put("score", score);
                infos_niveau.put("date", date);
                scores.put(nom_joueur, infos_niveau);
            } else if (!niveaux) {
                String date = parts[2].trim();
                HashMap<String, String> infos_niveau = new HashMap<>();
                infos_niveau.put("score", score);
                infos_niveau.put("date", date);
                scores.put(nom_joueur, infos_niveau);
            }
        }
        scanner.close();
        return scores;
    }

    /**
     * Compte le nombre de niveaux implémentés
     * @param mode_de_jeu le mode de jeu
     * @return le nombre de niveaux
     */
    public static int nbGrilles(String mode_de_jeu) {
        int nb_grilles = 0;
        InputStream inputStream;
        inputStream = Sauvegarder.class
                .getResourceAsStream("/grilles/grilles_" + mode_de_jeu + ".txt");

        if (inputStream == null) {
            System.out.println("[StockageNiveau] : Fichier inexistant");
            return -1;
        }

        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Grille ")) {
                nb_grilles++;
            }
        }
        scanner.close();
        return nb_grilles;
    }

    /**
     * Charge une grille depuis un fichier texte selon le numero de la grille et le
     * mode de jeu
     *
     * @param id_niveau   le numero de la grille
     * @param mode_de_jeu le mode de jeu
     * @param solution    boolean, vrai s'il faut charger la solution du niveau
     *                    selon l'id
     * @return la matrice du niveau chargé
     */
    public static int[][] chargerGrille(int id_niveau, String mode_de_jeu, Boolean solution) {

        InputStream inputStream;
        if (!solution) {
            inputStream = // Creating a new instance of the class "Person" and assigning it to the
                          // variable
                    // "person".
                    // Creating a new instance of the class "Person" and assigning it to the
                    // variable
                    // "person".
                    Sauvegarder.class
                            .getResourceAsStream("/grilles/grilles_" + mode_de_jeu + ".txt");
        } else {
            inputStream = Sauvegarder.class
                    .getResourceAsStream("/grilles/grilles_" + mode_de_jeu + "_solutions.txt");
        }

        if (inputStream == null) {
            System.out.println("[StockageNiveau] : Fichier inexistant");
            return null;
        }

        Scanner scanner = new Scanner(inputStream);
        int[][] grille = null;
        int lignes = -1;
        int colonnes = 0;
        boolean grille_courante = false;
        int index = 0;
        while (scanner.hasNextLine() && index != lignes) {
            String line = scanner.nextLine();
            if (line.startsWith("Grille " + id_niveau)) {
                // Si une grille a été trouvée, on récupère ses dimensions
                String[] dimensions = line.split("\\(")[1].split("\\)")[0].split(";");
                lignes = Integer.parseInt(dimensions[0].trim());
                colonnes = Integer.parseInt(dimensions[1].trim());
                grille = new int[lignes][colonnes];
                grille_courante = true;
            } else if (line.startsWith("#")) {
                // Ignore les lignes commentées
                grille_courante = false;
            } else if (line.startsWith("Grille") && grille_courante) {
                grille_courante = false;
                break;
            } else if (grille_courante) {
                String[] values = line.split(" ");
                for (int i = 0; i < colonnes; i++) {
                    if (!values[i].equals("")) {
                        grille[index][i] = Integer.parseInt(values[i]);
                    }
                }
                index++; // incremente indice de la ligne
            }
        }
        scanner.close();
        return grille;
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
     */
    public static void sauvegarderHistorique(String joueur, String mode_de_jeu, int id_niveau,
            Historique historique) {
        File mouvements_repertoire = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu);
        File mouvements_fichier = new File(mouvements_repertoire.toString() + "/Mouvements_" + id_niveau);

        if (creerDossierFichier(mouvements_repertoire, mouvements_fichier)) {
            System.out
                    .println("[Sauvegarde] Fichier de sauvegarde de l'historique des mouvements créé / deja existant");
            serialisationHistorique(mouvements_fichier, historique);
        } else {
            System.out.println("[Sauvegarde] Erreur lors de la création de fichier et/ou de dossier");
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

            System.out.println(
                    "[Sauvegarde] Historique des mouvements serialisé et sauvegardé dans Mouvements_<id_niveau>");
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
    public static void sauvegardeMatrice(String joueur, String mode_de_jeu, int id_niveau, int[][] matrice) {
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
     * Serialisation de la matrice de la grille
     *
     * @param repertoire le répertoire
     * @param matrice    la matrice de la grille
     */
    private static void serialisationMatrice(File repertoire, int[][] matrice) {
        try {
            FileOutputStream fichier_sortie = new FileOutputStream(repertoire, false);
            ObjectOutputStream sortie = new ObjectOutputStream(fichier_sortie);
            sortie.writeObject(matrice);
            sortie.close();
            fichier_sortie.close();

            System.out.println("[Sauvegarde] Matrice serialisé et sauvegardé dans Matrice_<id_niveau>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
