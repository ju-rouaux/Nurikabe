package com.l3infogrp5.nurikabe;

import com.l3infogrp5.nurikabe.menu.ControllerMenuPrincipal;
import com.l3infogrp5.nurikabe.sauvegarde.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Jeu du Nurikabe.
 * Projet étudiant de L3 Informatique.
 *
 * @author Julien Rouaux
 */
public class Nurikabe extends Application {

    /**
     * Constructeur.
     *
     * @throws FileNotFoundException Si le fichier de sauvegarde n'a pas été trouvé.
     */
    public Nurikabe() throws IOException {
        super();

        Sauvegarder.creerArborescence();
    }

    /**
     * Initialisation de la fenêtre de l'application.
     *
     * @throws Exception {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        Profil.getInstance().chargerProfil(Profil.getJoueur());
        stage.setScene(new Scene(new Group(new Text("test"))));
        stage.getScene().setRoot(new ControllerMenuPrincipal(stage.getScene()).getPane());

        stage.setMinHeight(480);
        stage.setMinWidth(640);
        stage.setTitle("Nurikabe");
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
}