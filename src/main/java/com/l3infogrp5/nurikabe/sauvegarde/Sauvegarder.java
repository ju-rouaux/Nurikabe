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
    private boolean RechercherSauvegarde(String joueur) {
        // Vérifier si le nom du joueur est nul
        if (joueur == null)
            return false;

        // Récupère tous les fichiers dans le répertoire
        // Vérifier si le répertoire existe et s'il contient des fichiers
        if (!dossierExistants(Path.repertoire_Lvl) || Path.repertoire_Lvl.listFiles().length == 0) {
            System.out.println("Il n'y pas de fichiers ou dossiers");
            return false;
        }

        // Parcourt tous les fichiers pour voir s'il y a une sauvegarde pour le joueur
        for (File fichier : Path.repertoire_Lvl.listFiles()) {
            if (fichier.isDirectory() && fichier.getName().equals(joueur)) {
                System.out.println("La sauvegarde du joueur existe");
                return true;
            }
        }

        System.out.println("Erreur, ce pseudo n'est pas associé à une sauvegarde");
        return false;
    }

    /**
     * Affiche tous les fichiers dans le répertoire
     */
    public void afficherFichiers(File repert) {
        if (dossierExistants(repert)) {
            for (File fichier : repert.listFiles()) {
                if (fichier.isFile())
                    System.out.println("Fichier : " + fichier.getName());
                else if (fichier.isDirectory())
                    System.out.println("Repertoire : " + fichier.getName());
            }
        }
    }

    /**
     *
     * Scanne les fichiers et répertoires et les ajoutes dans une liste
     *
     * @param répertoire le répertoire à scanner
     * @return une liste de noms de fichiers/répertoires
     */
    private static ArrayList<String> ajoutFichiers(File repertoire) {
        ArrayList<String> fichiers = new ArrayList<String>();
        if (dossierExistants(repertoire)) {
            for (File fichier : repertoire.listFiles()) {
                fichiers.add(fichier.getName());
            }
        }
        System.out.println(fichiers.toString());

        return fichiers;
    }

    /**
     * Création des dossiers necessaires a la sauvegarde
     *
     * @param nom_Joueur le nom du joueur/profil
     */
    private void creerDossiers(String nom_Joueur) {

        ArrayList<String> fichiers = ajoutFichiers(Path.repertoire_Courant);

        try {
            // Vérifier si le dossier "save" existe déjà
            boolean saveExists = fichiers.contains("save");
            if (!saveExists) {
                // Créer le dossier "save" si il n'existe pas
                Files.createDirectories(Paths.get(Path.repertoire_Save.toString()));
                System.out.println("Dossier save créé");
            } else {
                // Se déplacer dans le nouveau dossier "save"
            }

            // Vérifier si le dossier "lvl" existe déjà dans "save"
            boolean lvlExists = fichiers.contains("lvl");
            if (!lvlExists) {
                // Créer le dossier "lvl" si il n'existe pas
                Files.createDirectories(Paths.get(Path.repertoire_Lvl.toString()));
            }
            Files.createDirectories(Paths.get(Path.repertoire_Lvl.toString() + "/" + nom_Joueur));

            // Vérifier si le dossier "score" existe déjà dans "save"
            boolean scoreExists = fichiers.contains("score");
            if (!scoreExists) {
                // Créer le dossier "score" si il n'existe pas
                Files.createDirectories(Paths.get(Path.repertoire_Score.toString()));
                System.out.println("Dossier score créé");
            }

            System.out.println("Repertoires essentiels créés");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création des répertoires nécessaires au jeu");
            e.printStackTrace();

        }
    }

    /**
     * Verifie s'il y a des dossiers dans le répertoire
     *
     * @param repert le repertoire
     * @return boolean, true s'il y a des dossiers dans le répertoire sinon false
     */
    public static boolean dossierExistants(File repert) {
        File[] fichiers = repert.listFiles();
        if (fichiers == null)
            return false;
        else
            return true;
    }

    /**
     * Sauvegarde le profil
     *
     * @param joueur le nom du joueur/profil
     */
    public void sauvegarderProfil(String joueur) {
        if (RechercherSauvegarde(joueur) != true) {
            creerDossiers(joueur);
        }
    }

    /**
     * Créer un fichier et un dossier
     *
     * @param repert le répertoire
     * @param fichier le fichier
     * @return boolean, true si le fichier et le dossier ont été créés sinon false
     */
    public void sauvegarderScore(String joueur, String mode_De_Jeu) {
        File endless = new File(Path.repertoire_Score.toString() + "/endless.save");
        File detente = new File(Path.repertoire_Score.toString() + "/détente.save");

        if (creerDossierFichier(Path.repertoire_Score, detente))
            System.out.println("Fichier détente créé");
        else
            System.out.println("Erreur creation fichier detente pour les scores");

        if (creerDossierFichier(Path.repertoire_Score, endless))
            System.out.println("Fichier endless créé");
        else
            System.out.println("Erreur creation fichier endless pour les scores");

    }

    /**
     * Créer un fichier et un dossier
     *
     * @param repert le répertoire
     * @param fichier le fichier
     * @return boolean, true si le fichier et le dossier ont été créés sinon false
     */
    public void sauvegarderNiveau(String joueur, String mode_De_Jeu, int id_Niveau) {

        File niveau_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu);
        File niveau_fichier = new File(niveau_repert.toString() + "/Niveau_" + id_Niveau);

        if (creerDossierFichier(niveau_repert, niveau_fichier)) {

        } else {
            System.out.println("Erreur lors de la création de fichier et/ou de dossier");
            return;
        }
    }

    /**
     * Sauvegarde les mouvements
     *
     * @param joueur le nom du joueur/profil
     * @param mode_De_Jeu le mode de jeu
     * @param id_Niveau l'id du niveau
     * @param historique l'historique des mouvements
     */
    public static void sauvegarderMouvement(String joueur, String mode_De_Jeu, int id_Niveau, Historique historique) {
        File mouvements_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu);
        File mouvements_fichier = new File(mouvements_repert.toString() + "/Mouvements_" + id_Niveau);

        if (creerDossierFichier(mouvements_repert, mouvements_fichier)) {
            serialisationMouvement(mouvements_fichier, historique);
        } else {
            System.out.println("Erreur lors de la création de fichier et/ou de dossier");
            return;
        }

    }

    /**
     * Serialisation de l'historique des mouvements
     * @param repert
     * @param historique
     */
    private static void serialisationMouvement(File repert, Historique historique) {
        try {
            FileOutputStream fileOut = new FileOutputStream(repert);
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
     * Créer un fichier et un/ou plusieurs dossier(s) selon le chemin
     * @param dossier les dossier à créer (selon le chemin)
     * @param fichier le fichier à créer
     * @return true si creation(s) bien effectuée, false sinon
     * @throws IOException si erreur lors de la création du fichier
     */
    private static boolean creerDossierFichier(File dossier, File fichier) {
        boolean statut = false;
        if (!dossier.exists()) {
            boolean result = dossier.mkdirs();
            if (result) {
                System.out.println("Directory created successfully.");
                statut = true;
            } else {
                System.out.println("Directory creation failed.");
                return false;
            }
        }

        try {
            if (fichier.createNewFile()) {
                System.out.println("File created successfully.");
                statut = true;
            } else {
                System.out.println("File creation failed.");
                return false;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("Erreur lors de la création du fichier" + fichier.toString());
            e.printStackTrace();
        }
        return statut;
    }

}
