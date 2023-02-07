package com.l3infogrp5.nurikabe;

import javafx.application.Application;
import javafx.stage.Stage;

import com.l3infogrp5.nurikabe.menu.ControllerMenuPrincipal;

/**
 * Jeu du Nurikabe.
 * Point d'entrée de l'application.
 * Projet étudiant de L3 Informatique.
 * 
 * @author Julien Rouaux
 */
public class Main extends Application {

    /**
     * Nouvelle instance de l'application.
     */
    public Main() {}

    /**
     * Initialisation de la fenêtre de l'application.
     * 
     * @throws Exception {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        ControllerMenuPrincipal menu = new ControllerMenuPrincipal(stage);

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
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
