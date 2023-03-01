package com.l3infogrp5.nurikabe.sauvegarde;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.utils.Path;

/**
 *
 * Classe pour sauvegarder le profil d'un joueur
 *
 * @author Guillaume Richard
 */
public class Sauvegarder {

    // Constructeur pour initialiser le répertoire
    public Sauvegarder() {

    }

    /**
     *
     * Recherche si la sauvegarde pour le joueur existe déjà
     *
     * @param player nom du joueur
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
     * @return une liste de noms de fichiers/répertoires
     */
    private static ArrayList<String> ajoutFichiers(File repertoire) {
        ArrayList<String> fichiers = new ArrayList<String>();
        if (dossierExistants(repertoire)) {
            for (File fichier : repertoire.listFiles()) {
                fichiers.add(fichier.getName());
            }
        }
        return fichiers;
    }

    public static void creerDossierJoueur(String nom_joueur) throws IOException {

        // Vérifier si le dossier "lvl" existe déjà dans "save"
        boolean niveau_existe = ajoutFichiers(Path.repertoire_courant).contains("lvl");
        if (!niveau_existe) {
            // Créer le dossier "lvl" si il n'existe pas
            Files.createDirectories(Paths.get(Path.repertoire_lvl.toString()));
        }
        Files.createDirectories(Paths.get(Path.repertoire_lvl.toString() + "/" + nom_joueur));
    }

    /**
     * Création des dossiers necessaires a la sauvegarde
     *
     * @param nom_joueur le nom du joueur/profil
     */
    public static void creerArborescence() {

        ArrayList<String> fichiers = ajoutFichiers(Path.repertoire_courant);

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
     * @throws IOException si erreur lors de la création du fichier
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
     * Créer un fichier et un dossier
     *
     * @param joueur      le nom du joueur/profil
     * @param mode_de_jeu le mode de jeu
     **/
    public void sauvegarderScore(String joueur, String mode_de_jeu) {
        File sans_fin = new File(Path.repertoire_score.toString() + "/endless.save");
        File detente = new File(Path.repertoire_score.toString() + "/détente.save");

        if (creerDossierFichier(Path.repertoire_score, detente))
            System.out.println("[Sauvegarde] Fichier détente créé");
        else
            System.out.println("[Sauvegarde] Erreur creation fichier detente pour les scores");

        if (creerDossierFichier(Path.repertoire_score, sans_fin))
            System.out.println("[Sauvegarde] Fichier endless créé");
        else
            System.out.println("[Sauvegarde] Erreur creation fichier endless pour les scores");

    }

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
