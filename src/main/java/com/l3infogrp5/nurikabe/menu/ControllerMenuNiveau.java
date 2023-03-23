package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

import com.l3infogrp5.nurikabe.profil.Profil;

/**
 * Contrôleur du menu de sélection de niveau, et sa scène.
 *
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerMenuNiveau {

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;
    Profil joueur;

    @FXML
    private Button btn_retour;

    /**
     * Initialise le menu de sélection de niveau et son contrôleur.
     *
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuNiveau(Stage stage) throws IOException {
        this.joueur = Profil.getInstance();

        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_niveau.fxml"));
        loader.setController(this);

        scene = loader.load();
    }

    /**
     * Retourne la scène gérée par le contrôleur.
     *
     * @return la scène gérée par le contrôleur.
     */
    public Scene getScene() {
        return scene;
    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuModeJeu(stage).getScene());
    }
}
