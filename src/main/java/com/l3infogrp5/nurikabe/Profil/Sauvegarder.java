package com.l3infogrp5.nurikabe.Profil;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 *
 * Classe pour sauvegarder le profil d'un joueur
 */
public class Sauvegarder {

    // Répertoire principal
    private File repertoire;

    // Constructeur pour initialiser le répertoire
    public Sauvegarder() {
        String nomChemin = Sauvegarder.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int index = nomChemin.lastIndexOf("/");
        repertoire = new File(nomChemin.substring(0, index));

        // System.out.println("repertoire = " + repertoire.toString());
    }

    /**
     *
     * Recherche si la sauvegarde pour le joueur existe déjà
     *
     * @param player nom du joueur
     *
     * @return vrai si la sauvegarde existe, faux sinon
     */
    private boolean RechercherSauvegarde(String player) {
        // Vérifier si le nom du joueur est nul
        if (player == null)
            return false;

        File repertoire_Temp = new File(repertoire.toString() + "/save/lvl/");

        // Récupère tous les fichiers dans le répertoire
        // Vérifier si le répertoire existe et s'il contient des fichiers
        if (!dossierExistants(repertoire_Temp) || repertoire_Temp.listFiles().length == 0) {
            System.out.println("Il n'y pas de fichiers ou dossiers");
            return false;
        }

        // Parcourt tous les fichiers pour voir s'il y a une sauvegarde pour le joueur
        for (File fichier : repertoire_Temp.listFiles()) {
            if (fichier.isDirectory() && fichier.getName().equals(player)) {
                System.out.println("La sauvegarde du joueur existe");
                return true;
            }
        }

        System.out.println("Erreur, ce pseudo n'est pas associé à une sauvegarde");
        return false;
    }

    // Affiche tous les fichiers dans le répertoire
    public void afficherFichiers() {

        if (dossierExistants(repertoire)) {
            for (File fichier : repertoire.listFiles()) {
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
    private ArrayList<String> ajoutFichiers(File repertoire) {
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
     */
    private void creerDossiers(String joueur) {

        ArrayList<String> fichiers = ajoutFichiers(this.repertoire);

        try {
            // Vérifier si le dossier "save" existe déjà
            boolean saveExists = fichiers.contains("save");
            if (!saveExists) {
                // Créer le dossier "save" si il n'existe pas
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/save"));
                System.out.println("Dossier save créé");

                repertoire = new File(repertoire.toString() + "/save/");
            } else {
                // Se déplacer dans le nouveau dossier "save"
                repertoire = new File(repertoire.toString() + "/save");
            }

            // Vérifier si le dossier "lvl" existe déjà dans "save"
            boolean lvlExists = fichiers.contains("lvl");
            if (!lvlExists) {
                // Créer le dossier "lvl" si il n'existe pas
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/lvl"));
            }
            Files.createDirectories(Paths.get(this.repertoire.toString() + "/lvl/" + joueur));

            // Vérifier si le dossier "score" existe déjà dans "save"
            boolean scoreExists = fichiers.contains("score");
            if (!scoreExists) {
                // Créer le dossier "score" si il n'existe pas
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/score"));
                System.out.println("Dossier score créé");
            }

            System.out.println("Repertoires essentiels créés");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création des répertoires nécessaires au jeu");
        }
    }

    private boolean dossierExistants(File repert) {
        File[] fichiers = repert.listFiles();
        if (fichiers == null)
            return false;
        else
            return true;
    }

    public int sauvegarderProfil(String joueur) {
        if (RechercherSauvegarde(joueur) != true) {
            creerDossiers(joueur);
        }

        return 0;
    }

}
