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
    public static File repertoire_courant;
    /**
     * Répertoire des sauvegardes
     */
    public static File repertoire_save;
    /**
     * Répertoire des niveaux
     */
    public static File repertoire_lvl;
    /**
     * Répertoire des scores
     */
    public static File repertoire_score;
    /**
     * Répertoire des grilles
     */
    public static File repertoire_grilles;
    /**
     * Répertoire des profils
     */
    public static File repertoire_profils;

    static {
        try {
            repertoire_jar = new File(Path.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            if (repertoire_jar.exists()) {
                repertoire_courant = new File(repertoire_jar.getParent() + DOSSIER_NURIKABE);
                repertoire_save = new File(repertoire_courant + DOSSIER_SAVE);
                repertoire_lvl = new File(repertoire_save + DOSSIER_NIVEAUX);
                repertoire_score = new File(repertoire_save + DOSSIER_SCORE);
                repertoire_grilles = new File(repertoire_jar.getParent() + DOSSIER_GRILLES);
                repertoire_profils = new File(repertoire_courant.getParent() + DOSSIER_PROFILS);
            } else {
                throw new RuntimeException("Jar directory does not exist");
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException("Error getting jar directory", e);
        }
    }

    /**
     * Constructeur
     */
    private Path() {

    }
}
