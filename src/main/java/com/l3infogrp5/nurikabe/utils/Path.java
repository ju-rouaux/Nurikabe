package com.l3infogrp5.nurikabe.utils;

import java.io.*;
import java.net.*;

public final class Path {
    // Répertoire du fichier .jar
    public static File repertoire_Jar;
    // Répertoire courant
    public static final File repertoire_Courant;
    // Répertoire des sauvegardes
    public static final File repertoire_Save;
    // Répertoire des niveaux
    public static final File repertoire_Lvl;
    // Répertoire des scores
    public static final File repertoire_Score;
    // Répertoire des grilles
    public static final File repertoire_Grilles;

    private static final String DOSSIER_NURIKABE = "/nurikabe_data";
    private static final String DOSSIER_SAVE = "/save";
    private static final String DOSSIER_NIVEAUX = "/lvl";
    private static final String DOSSIER_SCORE = "/score";
    private static final String DOSSIER_GRILLES = "/grilles";

    static {
        try {
            // Récupère le répertoire du .jar
            repertoire_Jar = new File(
                    Path.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            // Affichage d'une erreur si impossible de récupérer le répertoire du .jar
            System.out.println("Erreur indexation fichiers : Impossible de récupérer le répertoire du .jar");
            e.printStackTrace();
        }
        System.out.println(repertoire_Jar.toString());
        if (repertoire_Jar != null && repertoire_Jar.toString().length() > 0) {
            int dernier_Indice;

            // Récupère l'index du dernier séparateur de fichier
            dernier_Indice = repertoire_Jar.toString().lastIndexOf(System.getProperty("file.separator"));
            if (dernier_Indice > 0) {
                // Définit le répertoire courant
                repertoire_Courant = new File(repertoire_Jar.toString().substring(0, dernier_Indice) + DOSSIER_NURIKABE);
                // Définit le répertoire des sauvegardes
                repertoire_Save = new File(repertoire_Courant.toString() + DOSSIER_SAVE);
                // Définit le répertoire des niveaux
                repertoire_Lvl = new File(repertoire_Save.toString() + DOSSIER_NIVEAUX);
                // Définit le répertoire des scores
                repertoire_Score = new File(repertoire_Save.toString() + DOSSIER_SCORE);
                // Définit le répertoire des grilles
                repertoire_Grilles = new File(repertoire_Courant.toString() + DOSSIER_GRILLES);

            } else {
                System.out.println("lastIndex < 0");
                // Répertoire courant non défini
                repertoire_Courant = null;
                // Répertoire des sauvegardes non défini
                repertoire_Save = null;
                // Répertoire des niveaux non défini
                repertoire_Lvl = null;
                // Répertoire des scores non défini
                repertoire_Score = null;
                // Répertoire des grilles non défini
                repertoire_Grilles = null;
            }
        } else {
            System.out.println("<Erreur> : Probleme d'indexation de fichiers");
            System.out.println("Path.enclosing_method()");
            // Répertoire courant non défini
            repertoire_Courant = null;
            // Répertoire des sauvegardes non défini
            repertoire_Save = null;
            // Répertoire des niveaux non défini
            repertoire_Lvl = null;
            // Répertoire des scores non défini
            repertoire_Score = null;
            // Répertoire des grilles non défini
            repertoire_Grilles = null;
        }
    }
}
