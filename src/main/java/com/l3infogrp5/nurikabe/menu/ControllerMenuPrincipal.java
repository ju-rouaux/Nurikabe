package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Contrôleur du menu principal, et sa scène.
 * 
 * @author Julien Rouaux - Nicolas
 */
public class ControllerMenuPrincipal {

    private final double MIN_POLICE_TAILLE = 80;
    private final double MAX_POLICE_TAILLE = 500;

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
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuPrincipal(Stage stage) throws IOException {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_principal.fxml"));
        loader.setController(this);

        scene = loader.load();

        // Redimensionnement responsive du titre principal 
        Label titreLabel = (Label) loader.getNamespace().get("titre_nurikabe");

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double taillePolice = newVal.doubleValue() / 12;

            if (taillePolice < MIN_POLICE_TAILLE) taillePolice = MIN_POLICE_TAILLE;
            else if (taillePolice > MAX_POLICE_TAILLE) taillePolice = MAX_POLICE_TAILLE;
            
            titreLabel.setStyle("-fx-font-size: " + taillePolice + ";" + "-fx-font-weight:bold;");
        });
        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double taillePolice = newVal.doubleValue() / 6;

            if (taillePolice < MIN_POLICE_TAILLE) taillePolice = MIN_POLICE_TAILLE;
            else if (taillePolice > MAX_POLICE_TAILLE) taillePolice = MAX_POLICE_TAILLE;
            
            titreLabel.setStyle("-fx-font-size: " + taillePolice + ";" + "-fx-font-weight:bold;");
        });

        ControllerMenuProfils controller_profil = new ControllerMenuProfils(stage);
        controller_profil.getActif();
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
        stage.setScene(new ControllerMenuModeJeu(stage).getScene());
    }

    /**
     * Change la scène par le menu de sélection de profil.
     * 
     * @throws IOException
     */
    @FXML
    private void profilsClique(ActionEvent event) throws IOException {
        ControllerMenuProfils controller_profil = new ControllerMenuProfils(stage);
        stage.setScene(controller_profil.getScene());
        controller_profil.chargerTableau();
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
