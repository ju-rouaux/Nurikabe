package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.application.Platform;

import java.io.IOException;

import com.l3infogrp5.nurikabe.profil.Profil;

/**
 * Contrôleur du menu principal, et sa scène.
 * 
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerMenuPrincipal {

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button btn_jouer;

    @FXML
    private Button btn_profils;

    @FXML
    private Button btn_regles;

    @FXML
    private Button btn_quitter;

    /**
     * Initialise le menu principal et son contrôleur.
     * 
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû être lu.
     */
    public ControllerMenuPrincipal(Stage stage) throws IOException {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_principal.fxml"));
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

    /**
     * Change la scène par le menu de mode de jeu.
     */
    @FXML
    private void jouerClique(ActionEvent event) throws Exception {
        Profil.getInstance().chargerProfil("Julieng"); // TODO charger profil dans menu selection
        stage.setScene(new ControllerMenuModeJeu(stage).getScene());
    }

    /**
     * Change la scène par le menu de sélection de profil.
     * @throws IOException
     */
    @FXML
    private void profilsClique(ActionEvent event) throws IOException {
        stage.setScene(new ControllerMenuProfils(stage).getScene());
    }

    /**
     * Change la scène par le menu des règles.
     */
    @FXML
    private void reglesClique(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuRegles(stage).getScene());
    }

    /**
     * Quitte l'application.
     */
    @FXML
    private void quitterClique(ActionEvent event) {
        Platform.exit();
    }
}
