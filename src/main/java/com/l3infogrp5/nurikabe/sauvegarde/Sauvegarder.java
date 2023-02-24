package com.l3infogrp5.nurikabe.sauvegarde;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.l3infogrp5.nurikabe.niveau.grille.Grille;
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
    public static void creerDossiers(String nom_Joueur) {

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

            boolean gridExists = fichiers.contains("grilles");
            if(!gridExists) {
                Files.createDirectories(Paths.get(Path.repertoire_Grilles.toString()));
                System.out.println("Dossier grilles créé");
            }

            System.out.println("Repertoires essentiels créés");
        } catch (IOException e) {
            System.err.println("Erreur lors de la création des répertoires nécessaires au jeu");
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
            System.err.println("Erreur lors de la création du fichier" + fichier.toString());
            e.printStackTrace();
        }
        return statut;
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

    // /**
    //  * Sauvegarde le profil
    //  *
    //  * @param joueur le nom du joueur/profil
    //  */
    // public void sauvegarderProfil(String joueur) {
    //     if (RechercherSauvegarde(joueur) != true) {
    //         creerDossiers(joueur);
    //     }
    // }

    /**
     * Créer un fichier et un dossier
     *
     * @param repert  le répertoire
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

    // /**
    // * Créer un fichier et un dossier
    // *
    // * @param repert le répertoire
    // * @param fichier le fichier
    // * @return boolean, true si le fichier et le dossier ont été créés sinon false
    // */
    // private static void sauvegarderGrille(String joueur, String mode_De_Jeu, int
    // id_Niveau, Grille grille) {

    // File niveau_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur +
    // "/" + mode_De_Jeu);
    // File niveau_fichier = new File(niveau_repert.toString() + "/Niveau_" +
    // id_Niveau);

    // if (creerDossierFichier(niveau_repert, niveau_fichier)) {
    // // TODO : Serialisation du niveau
    // serialisationGrille(niveau_fichier, grille);

    // } else {
    // System.out.println("Erreur lors de la création de fichier et/ou de dossier");
    // return;
    // }
    // }

    // private static void serialisationGrille(File repert, Grille grille) {
    // try {
    // FileOutputStream fichier = new FileOutputStream(repert);
    // ObjectOutputStream oos = new ObjectOutputStream(fichier);
    // oos.writeObject(grille);
    // oos.flush();
    // oos.close();
    // } catch (java.io.IOException e) {
    // e.printStackTrace();
    // }
    // }

    // /**
    // * Sauvegarde les mouvements
    // *
    // * @param joueur le nom du joueur/profil
    // * @param mode_De_Jeu le mode de jeu
    // * @param id_Niveau l'id du niveau
    // * @param historique l'historique des mouvements
    // */
    // private static void sauvegarderMouvement(String joueur, String mode_De_Jeu,
    // int id_Niveau, Historique historique) {
    // File mouvements_repert = new File(Path.repertoire_Lvl.toString() + "/" +
    // joueur + "/" + mode_De_Jeu);
    // File mouvements_fichier = new File(mouvements_repert.toString() +
    // "/Mouvements_" + id_Niveau);

    // if (creerDossierFichier(mouvements_repert, mouvements_fichier)) {
    // serialisationMouvement(mouvements_fichier, historique);
    // } else {
    // System.out.println("Erreur lors de la création de fichier et/ou de dossier");
    // return;
    // }

    // }

    // /**
    // * Serialisation de l'historique des mouvements
    // *
    // * @param repert
    // * @param historique
    // */
    // private static void serialisationMouvement(File repert, Historique
    // historique) {
    // try {
    // FileOutputStream fileOut = new FileOutputStream(repert);
    // ObjectOutputStream out = new ObjectOutputStream(fileOut);
    // out.writeObject(historique);
    // out.close();
    // fileOut.close();
    // System.out.println("Historique des mouvements serialisé et sauvegardé dans
    // Mouvements_<id_niveau>");
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    /**
     * Sauvegarde le niveau
     */

    // public static void sauvegarderNiveau(String joueur, String mode_De_Jeu, int
    // id_Niveau, Niveau niveau) {

    // // File mouvements_repert = new File(Path.repertoire_Lvl.toString() + "/" +
    // // joueur + "/" + mode_De_Jeu);
    // // File mouvements_fichier = new File(mouvements_repert.toString() +
    // // "/Mouvements_" + id_Niveau);

    // File niveau_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur +
    // "/" + mode_De_Jeu);
    // File niveau_fichier = new File(niveau_repert.toString() + "/Niveau_" +
    // id_Niveau);

    // // sauvegarderGrille(joueur, mode_De_Jeu, id_Niveau, grille);
    // // sauvegarderMouvement(joueur, mode_De_Jeu, id_Niveau, historique);

    // if (creerDossierFichier(niveau_repert, niveau_fichier)) {
    // serialiserNiveau(joueur, mode_De_Jeu, id_Niveau, niveau_fichier, niveau);
    // } else {
    // System.out.println("Erreur lors de la création de fichier et/ou de dossier");
    // return;
    // }
    // }

    // private static void serialiserNiveau(String joueur, String mode_De_Jeu, int
    // id_Niveau, File fichier,
    // Niveau niveau) {
    // try (ObjectOutputStream oos = new ObjectOutputStream(new
    // FileOutputStream(fichier))) {
    // oos.writeObject(niveau);
    // oos.writeObject(niveau.getGrille());
    // oos.writeObject(niveau.getHistorique());

    // System.out.println("Grille :");
    // int matrice [][] = niveau.getGrille().getMatrice();
    // for (int i = 0; i < matrice.length; i++) {
    // for (int j = 0; j < matrice[i].length; j++) {
    // System.out.print(matrice[i][j] + " ");
    // }
    // System.out.println();
    // }

    // System.out.println("Niveau serialisé et sauvegardé dans Niveau_<id_niveau>");
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }

    public static Historique sauvegarderHistorique(String joueur, String mode_De_Jeu, int id_Niveau,
            Historique historique) {
        File mouvements_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu);
        File mouvements_fichier = new File(mouvements_repert.toString() + "/Mouvements_" + id_Niveau);

        if (creerDossierFichier(mouvements_repert, mouvements_fichier)) {
            serialisationHistorique(mouvements_fichier, historique);
        } else {
            System.out.println("Erreur lors de la création de fichier et/ou de dossier");
            return null;
        }
        return historique;
    }

    /**
     * Serialisation de l'historique des mouvements
     *
     * @param repert
     * @param historique
     */
    private static void serialisationHistorique(File repert, Historique historique) {
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

    public static Grille sauvegarderGrille(String joueur, String mode_De_Jeu, int id_Niveau, Grille grille) {
        File grille_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu);
        File grille_fichier = new File(grille_repert.toString() + "/Grille_" + id_Niveau);

        if (creerDossierFichier(grille_repert, grille_fichier)) {
            serialisationGrille(grille_fichier, grille);
        } else {
            System.out.println("Erreur lors de la création de fichier et/ou de dossier");
            return null;
        }
        return grille;
    }

    /**
     * Serialisation de la grille
     *
     * @param repert
     * @param grille
     */
    private static void serialisationGrille(File repert, Grille grille) {
        try {
            FileOutputStream fileOut = new FileOutputStream(repert);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(grille);
            out.writeObject(grille.getMatrice());
            out.close();
            fileOut.close();
            System.out.println("Grille serialisé et sauvegardé dans Grille_<id_niveau>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
