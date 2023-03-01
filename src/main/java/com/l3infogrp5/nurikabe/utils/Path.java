package com.l3infogrp5.nurikabe.utils;

import java.io.*;
import java.net.*;

/**
 * Classe constante qui definit tout les chemins des différents dossiers
 *
 * @author Guillaume Richard
 */
public final class Path {

    /**
     * Répertoire du fichier .jar
     */
    public static File repertoire_jar;
    /** Répertoire courant */
    public static final File repertoire_courant;
    /** Répertoire des sauvegardes */
    public static final File repertoire_save;
    /** Répertoire des niveaux */
    public static final File repertoire_lvl;
    /** Répertoire des scores */
    public static final File repertoire_score;
    /** Répertoire des grilles */
    public static final File repertoire_grilles;

    /** Le nom du dossier a la racine des dossiers et fichiers de sauvegarde */
    private static final String DOSSIER_NURIKABE = "/nurikabe_data";
    /** Le nom du dossier des sauvegardes */
    private static final String DOSSIER_SAVE = "/save";
    /** Le nom du dossier pour la sauvegarde des niveaux */
    private static final String DOSSIER_NIVEAUX = "/lvl";
    /** Le nom de dossier pour la sauvegarde des score */
    private static final String DOSSIER_SCORE = "/score";
    /** Le nom de dosser pour la sauvegarde des grilles */
    private static final String DOSSIER_GRILLES = "/grilles";

    /**
     * Constructeur
     */
    Path() {

    }

    static {
        try {
            // Récupère le répertoire du .jar
            repertoire_jar = new File(
                    Path.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            // Affichage d'une erreur si impossible de récupérer le répertoire du .jar
            System.out.println("[Path] Erreur indexation fichiers : Impossible de récupérer le répertoire du .jar");
            e.printStackTrace();
        }
        System.out.println(repertoire_jar.toString());
        if (repertoire_jar != null && repertoire_jar.toString().length() > 0) {
            int dernier_indice;

            // Récupère l'index du dernier séparateur de fichier
            dernier_indice = repertoire_jar.toString().lastIndexOf(System.getProperty("file.separator"));
            if (dernier_indice > 0) {
                // Définit le répertoire courant
                repertoire_courant = new File(
                        repertoire_jar.toString().substring(0, dernier_indice) + DOSSIER_NURIKABE);
                // Définit le répertoire des sauvegardes
                repertoire_save = new File(repertoire_courant.toString() + DOSSIER_SAVE);
                // Définit le répertoire des niveaux
                repertoire_lvl = new File(repertoire_save.toString() + DOSSIER_NIVEAUX);
                // Définit le répertoire des scores
                repertoire_score = new File(repertoire_save.toString() + DOSSIER_SCORE);
                // Définit le répertoire des grilles
                repertoire_grilles = new File(repertoire_jar + DOSSIER_GRILLES);

            } else {
                System.out.println("[Path] erreur index < 0");
                // Répertoire courant non défini
                repertoire_courant = null;
                // Répertoire des sauvegardes non défini
                repertoire_save = null;
                // Répertoire des niveaux non défini
                repertoire_lvl = null;
                // Répertoire des scores non défini
                repertoire_score = null;
                // Répertoire des grilles non défini
                repertoire_grilles = null;
            }
        } else {
            System.out.println("[Path] Probleme d'indexation de fichiers");
            // Répertoire courant non défini
            repertoire_courant = null;
            // Répertoire des sauvegardes non défini
            repertoire_save = null;
            // Répertoire des niveaux non défini
            repertoire_lvl = null;
            // Répertoire des scores non défini
            repertoire_score = null;
            // Répertoire des grilles non défini
            repertoire_grilles = null;
        }
    }
}
