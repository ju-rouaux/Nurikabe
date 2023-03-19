package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.event.ActionEvent;

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
    Profil joueur;

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
     * @throws IOException
     */
    public ControllerMenuPrincipal(Stage stage, Profil joueur) throws IOException {
        this.joueur = joueur;

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
        stage.setScene(new ControllerMenuModeJeu(stage, joueur).getScene());
    }

    /**
     * Change la scène par le menu de sélection de profil.
     * 
     * @throws IOException
     */
    @FXML
    private void profilsClique(ActionEvent event) throws IOException {
        ControllerMenuProfils controller_profil = new ControllerMenuProfils(stage, joueur);
        stage.setScene(controller_profil.getScene());
        controller_profil.chargerTableau();
    }

    /**
     * Change la scène par le menu des règles.
     */
    @FXML
    private void reglesClique(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuRegles(stage, joueur).getScene());
    }

    /**
     * Quitte l'application.
     */
    @FXML
    private void quitterClique(ActionEvent event) {
        Platform.exit();
    }
}
