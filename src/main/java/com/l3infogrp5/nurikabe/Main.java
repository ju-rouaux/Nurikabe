package com.l3infogrp5.nurikabe;

import java.io.*;

import com.l3infogrp5.nurikabe.menu.ControllerMenuPrincipal;
import com.l3infogrp5.nurikabe.sauvegarde.StockageNiveau;
import com.l3infogrp5.nurikabe.utils.Path;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Jeu du Nurikabe.
 * Point d'entrée de l'application.
 * Projet étudiant de L3 Informatique.
 *
 * @author Julien Rouaux
 */
public class Main extends Application {

    // public static Profil joueur;

    /*
     * Pas encore utilisé mais l'idée est là
     */
    public enum mode_De_Jeu {
        DETENTE("detente"),
        CONTRE_LA_MONTRE("Contre la montre"),
        SANS_FIN("Sans fin");

        private String stringValue;

        mode_De_Jeu(String stringValue) {
            this.stringValue = stringValue;
        }

        public String getStringValue() {
            return stringValue;
        }
    }


    /**
     * Nouvelle instance de l'application.
     *
     * @throws IOException
     */
    public Main() throws IOException {
        StockageNiveau.creationNiveauDetente();


    }

    /**
     * Initialisation de la fenêtre de l'application.
     *
     * @throws Exception {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        ControllerMenuPrincipal menu = new ControllerMenuPrincipal(stage);

        stage.setMinHeight(480);
        stage.setMinWidth(640);
        stage.setScene(menu.getScene());
        stage.show();
    }

    /**
     * Fonction appelée avant la fermeture du programme.
     * Utiliser Platform.exit() au lieu de System.exit() pour s'assurer de
     * l'exécution de cette méthode.
     *
     * @throws Exception {@inheritDoc}
     */
    @Override
    public void stop() throws Exception {
        // TODO : ajouter une fermeture du programme propre qui s'assure de la
        // sauvegarde de la partie.
    }

    /**
     * Lancement de l'application.
     *
     * @param args arguments de ligne de commande.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        Application.launch(args);

        boolean debug = false;
        if (debug) {
            File jarPath = Path.repertoire_Jar;
            File currentPath = Path.repertoire_Courant;
            File savePath = Path.repertoire_Save;
            File lvlPath = Path.repertoire_Lvl;
            File scorePath = Path.repertoire_Score;

            System.out.println("Jar Path: " + (jarPath != null ? jarPath.toString() : "null"));
            System.out.println("Current Path: " + (currentPath != null ? currentPath.toString() : "null"));
            System.out.println("Save Path: " + (savePath != null ? savePath.toString() : "null"));
            System.out.println("Lvl Path: " + (lvlPath != null ? lvlPath.toString() : "null"));
            System.out.println("Score Path: " + (scorePath != null ? scorePath.toString() : "null"));
        }

        // Sauvegarder sauv = new Sauvegarder();
        // sauv.sauvegarderProfil(joueur);
        // sauv.sauvegarderScore(joueur, mode_De_Jeu);
        // sauv.sauvegarderNiveau(joueur, mode_De_Jeu, id_Niveau);
    }
}
