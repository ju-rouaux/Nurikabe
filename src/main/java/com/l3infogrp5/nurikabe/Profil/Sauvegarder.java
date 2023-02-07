package com.l3infogrp5.nurikabe.Profil;

import java.io.*;
import java.nio.file.*;
import java.util.*;

// import com.google.gson.Gson;

/**
 *
 * Classe pour sauvegarder le profil d'un joueur
 */
public class Sauvegarder {
    // public class Sauvegarder implements Path {

    // Gson gson = new Gson();


    // // Constructeur pour initialiser le répertoire
    public Sauvegarder() {
        System.out.println("repertoire = " + Path.repertoire_Courant.toString());
        // repertoire = repertoire_Courant;
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

    public void sauvegarderScore(String joueur, String mode_De_Jeu) {
        File endless = new File(Path.repertoire_Score.toString() + "/endless.json");
        File detente = new File(Path.repertoire_Score.toString() + "/détente.json");

        if (dossierExistants(endless.getParentFile())) {
            System.out.println("Json endless deja existant");

        } else {
            try {
                Files.createFile(Paths.get(endless.toString()));
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier" + endless.toString());
                e.printStackTrace();
            }
        }

        if (dossierExistants(detente.getParentFile())) {
            System.out.println("Json détente deja existant");
        } else {
            try {
                Files.createFile(Paths.get(detente.toString()));
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier" + detente.toString());
                e.printStackTrace();
            }
        }

    }

}
