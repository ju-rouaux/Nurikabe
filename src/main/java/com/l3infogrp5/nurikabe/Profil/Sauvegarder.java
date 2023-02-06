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
        if (player == null)
            return false;

        File repertoire_Temp = new File(repertoire.toString() + "/save/lvl/");

        // Récupère tous les fichiers dans le répertoire
        File[] fichiers = repertoire_Temp.listFiles();
        if (!dossierExistants(repertoire_Temp) || fichiers.length == 0) {
            System.out.println("Il n'y pas de fichiers ou dossiers");
            return false;
        }
        // Parcourt tous les fichiers pour voir s'il y a une sauvegarde pour le joueur
        for (File fichier : fichiers) {
            if (fichier.isDirectory() && fichier.getName().equals(player)) {
                System.out.println("La sauvegarde du joueur existe");
                return true;
            } else
                System.out.println("Erreur, ce pseudo n'est pas associé à une sauvegarde");
        }
        return false;
    }

    // Affiche tous les fichiers dans le répertoire
    public void afficherFichiers() {
        File[] fichiers = repertoire.listFiles();
        if (dossierExistants(repertoire)) {
            for (File fichier : fichiers) {
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
            if (!fichiers.contains("save")) {
                // Créer le dossier "save" si il n'existe pas
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/save"));
                System.out.println("Dossier save créé");

                repertoire = new File(repertoire.toString() + "/save/");

                Files.createDirectories(Paths.get(this.repertoire.toString() + "/lvl"));
                System.out.println("Dossier lvl créé");

                Files.createDirectories(Paths.get(this.repertoire.toString() + "/score"));
                System.out.println("Dossier score créé");

                Files.createDirectories(Paths.get(this.repertoire.toString() + "/lvl/" + joueur));
                System.out.println("Dossier du joueur créé");
            } else {
                // Se déplacer dans le nouveau dossier "save"
                repertoire = new File(repertoire.toString() + "/save");

                // Vider la liste de fichiers
                fichiers.clear();
                fichiers = ajoutFichiers(this.repertoire);

                // Vérifier si le dossier "lvl" existe déjà dans "save"
                if (!fichiers.contains("lvl")) {
                    // Créer le dossier "lvl" si il n'existe pas
                    Files.createDirectories(Paths.get(this.repertoire.toString() + "/lvl"));
                    Files.createDirectories(Paths.get(this.repertoire.toString() + "/lvl/" + joueur));
                    System.out.println("Dossier lvl et joueur créés");
                }
                // Vérifier si le dossier "score" existe déjà dans "save"
                if (!fichiers.contains("score")) {
                    // Créer le dossier "score" si il n'existe pas
                    Files.createDirectories(Paths.get(this.repertoire.toString() + "/score"));
                    System.out.println("Dossier score créé");
                }

                // Se déplacer dans le nouveau dossier "save"
                repertoire = new File(repertoire.toString() + "/lvl");

                // Vider la liste de fichiers
                fichiers.clear();
                fichiers = ajoutFichiers(this.repertoire);
                // Vérifier si le dossier du joueur existe déjà dans "lvl"
                if (!fichiers.contains(joueur)) {
                    // Créer le dossier du joueur si il n'existe pas
                    Files.createDirectories(Paths.get(this.repertoire.toString() + "/" + joueur));
                    System.out.println("Dossier lvl et joueur créés");
                }

                System.out.println("Repertoires essentiels créés");
            }

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
