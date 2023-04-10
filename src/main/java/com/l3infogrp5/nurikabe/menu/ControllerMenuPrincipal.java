package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * Contrôleur du menu principal, et sa scène.
 * 
 * @author Julien Rouaux - Nicolas
 */
public class ControllerMenuPrincipal {

    private FXMLLoader loader;
    private Scene root;
    private Pane main;

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
     * @param root la scene racine de l'application. 
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuPrincipal(Scene root) throws IOException {
        this.root = root;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_principal.fxml"));
        loader.setController(this);

        main = loader.load();

        ControllerMenuProfils controller_profil = new ControllerMenuProfils(root);
        controller_profil.getActif();
    }

    /**
     * Retourne l'affichage géré par le contrôleur.
     * 
     * @return l'affichage géré par le contrôleur.
     */
    public Pane getPane() {
        return main;
    }

    /**
     * Change la scène par le menu de mode de jeu.
     */
    @FXML
    private void jouerClique(ActionEvent event) throws Exception {
        root.setRoot(new ControllerMenuModeJeu(root).getPane());
    }

    /**
     * Change la scène par le menu de sélection de profil.
     * 
     * @throws IOException
     */
    @FXML
    private void profilsClique(ActionEvent event) throws IOException {
        ControllerMenuProfils controller_profil = new ControllerMenuProfils(root);
        root.setRoot(controller_profil.getPane());
        controller_profil.chargerTableau();
    }

    /**
     * Change la scène par le menu des règles.
     */
    @FXML
    private void reglesClique(ActionEvent event) throws Exception {
        root.setRoot(new ControllerMenuRegles(root).getPane());
    }

    /**
     * Quitte l'application.
     */
    @FXML
    private void quitterClique(ActionEvent event) {
        Platform.exit();
    }
}
