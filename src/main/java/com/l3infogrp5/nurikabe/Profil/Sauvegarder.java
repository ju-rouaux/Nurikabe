package com.l3infogrp5.nurikabe.Profil;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Sauvegarder {

    private File repertoire;

    public Sauvegarder() {
        String nom_chemin = Sauvegarder.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int index = nom_chemin.lastIndexOf("/");
        repertoire = new File(nom_chemin.substring(0, index));
    }

    private boolean RechercherSauvegarde(String player) {
        if (player == null)
            return false;

        File[] fichiers = repertoire.listFiles();
        if (fichiers.length == 0) {
            System.out.println("Il n'y pas de fichiers");
            return false;
        }
        for (File fichier : fichiers) {
            if (fichier.isDirectory() && fichier.getName().equals(player)) {
                System.out.println("La sauvegarde du joueur existe");
                return true;
            } else
                System.out.println("Erreur, ce pseudo n'est pas associé à une sauvegarde");
        }
        return false;
    }

    public void afficherFichiers() {
        File[] fichiers = repertoire.listFiles();

        for (File fichier : fichiers) {
            if (fichier.isFile())
                System.out.println("Fichier : " + fichier.getName());
            else if (fichier.isDirectory())
                System.out.println("Repertoire : " + fichier.getName());
        }
    }

    /**
     * Scanne les fichiers et repertoire et les ajoutes dans une liste
     *
     * @param fichiers
     */
    private void ajoutFichiers(ArrayList<String> fichiers, File repertoire) {
        for (File fichier : repertoire.listFiles()) {
            fichiers.add(fichier.getName());
        }
        System.out.println(fichiers.toString());
    }

    public void creerDossiers() {

        ArrayList<String> fichiers = new ArrayList<>();
        ajoutFichiers(fichiers, this.repertoire);

        try {
            if (!fichiers.contains("save")) {
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/save"));
                repertoire = new File(repertoire.toString() + "/save");
                System.out.println("repertoire = " + repertoire.toString());
                fichiers.clear();
                ajoutFichiers(fichiers, repertoire);

            } else if (!fichiers.contains("lvl"))
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/save/lvl"));
            else if (!fichiers.contains("score"))
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/save/score"));
            System.out.println("Repertoires essentiels créés");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création des repertoires necessaires au jeux");
        }
    }

    public int sauvegarderProfil(String joueur) {
        if (RechercherSauvegarde(joueur) != true) {
            // Creation du dossier
            try {
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/" + joueur));
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/" + joueur + "/CLM"));
                Files.createDirectories(Paths.get(this.repertoire.toString() + "/" + joueur + "/Détente"));
                System.out.println("Repertoires du joueur créés");
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du repertoire pour le profil");
            }
        }

        return 0;
    }

}
