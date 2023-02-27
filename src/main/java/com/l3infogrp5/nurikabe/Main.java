package com.l3infogrp5.nurikabe;

import java.io.*;

import com.l3infogrp5.nurikabe.menu.ControllerMenuPrincipal;
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
    public enum mode_de_jeu {
        DETENTE("detente"),
        CONTRE_LA_MONTRE("Contre la montre"),
        SANS_FIN("Sans fin");

        private String stringValue;

        mode_de_jeu(String stringValue) {
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
    public Main() {

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
    public static void main(String[] args) {

        Application.launch(args);

        boolean debug = false;
        if (debug) {
            File jar_path = Path.repertoire_jar;
            File current_path = Path.repertoire_courant;
            File save_path = Path.repertoire_save;
            File lvl_path = Path.repertoire_lvl;
            File score_path = Path.repertoire_score;

            System.out.println("Jar Path: " + (jar_path != null ? jar_path.toString() : "null"));
            System.out.println("Current Path: " + (current_path != null ? current_path.toString() : "null"));
            System.out.println("Save Path: " + (save_path != null ? save_path.toString() : "null"));
            System.out.println("Lvl Path: " + (lvl_path != null ? lvl_path.toString() : "null"));
            System.out.println("Score Path: " + (score_path != null ? score_path.toString() : "null"));
        }
    }
}
