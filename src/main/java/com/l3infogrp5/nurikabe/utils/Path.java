package com.l3infogrp5.nurikabe.utils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Classe constante qui définit tous les chemins des différents dossiers
 *
 * @author Guillaume Richard
 */
public final class Path {

    /**
     * Le nom du dossier a la racine des dossiers et fichiers de sauvegarde
     */
    private static final String DOSSIER_NURIKABE = "/nurikabe_data";
    /**
     * Le nom du dossier des sauvegardes
     */
    private static final String DOSSIER_SAVE = "/save";
    /**
     * Le nom du dossier pour la sauvegarde des niveaux
     */
    private static final String DOSSIER_NIVEAUX = "/lvl";
    /**
     * Le nom de dossier pour la sauvegarde des scores
     */
    private static final String DOSSIER_SCORE = "/score";
    /**
     * Le nom de dossier pour la sauvegarde des grilles
     */
    private static final String DOSSIER_GRILLES = "/grilles";
    /**
     * Le nom de dossier pour la sauvegarde des profils
     */
    private static final String DOSSIER_PROFILS = "/profils";
    /**
     * Répertoire du fichier .jar
     */
    public static File repertoire_jar;
    /**
     * Répertoire courant
     */
    public static File repertoire_courant = null;
    /**
     * Répertoire des sauvegardes
     */
    public static File repertoire_save = null;
    /**
     * Répertoire des niveaux
     */
    public static File repertoire_lvl = null;
    /**
     * Répertoire des scores
     */
    public static File repertoire_score = null;
    /**
     * Répertoire des grilles
     */
    public static File repertoire_grilles = null;

    /**
     * Repertoire des profils
     */
    public static File repertoire_profils = null;

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
        if (repertoire_jar != null && repertoire_jar.toString().length() > 0) {
            String baseDir = repertoire_jar.toString().replaceAll("\\\\", "/");
            int lastSeparatorIndex = baseDir.lastIndexOf("/");
            if (lastSeparatorIndex > 0) {
                String currentDir = baseDir.substring(0, lastSeparatorIndex) + "/" + DOSSIER_NURIKABE;
                repertoire_courant = new File(currentDir);
                repertoire_save = new File(currentDir + "/" + DOSSIER_SAVE);
                repertoire_lvl = new File(repertoire_save + "/" + DOSSIER_NIVEAUX);
                repertoire_score = new File(repertoire_save + "/" + DOSSIER_SCORE);
                repertoire_grilles = new File(baseDir + "/" + DOSSIER_GRILLES);
                repertoire_profils = new File(repertoire_courant + "/" + DOSSIER_PROFILS);
            } else {
                System.out.println("[Path] erreur index < 0");
            }
        } else {
            System.out.println("[Path] Problème d'indexation de fichiers");
        }
    }

    /**
     * Constructeur
     */
    private Path() {

    }
}
