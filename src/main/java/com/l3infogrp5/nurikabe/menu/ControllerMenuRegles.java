package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.Profil;

/**
 * Contrôleur du menu d'affichage des règles, et sa scène.
 *
 * @author Nicolas
 */
public class ControllerMenuRegles {

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;
    Profil joueur;

    @FXML
    private Button retour;
    @FXML
    private BorderPane preview_ile;
    @FXML
    private BorderPane preview_jeu;

    /**
     * Initialise le menu de sélection d'affichage des règles et son contrôleur.
     *
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuRegles(Stage stage) throws IOException {
        this.joueur = Profil.getInstance();

        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_regles.fxml"));
        loader.setController(this);

        scene = loader.load();
    }

    @FXML
    private void initialize() throws IOException {
        this.preview_ile.setCenter(new Grille(new int[][] {{-2,-1}}, new int[][] {{50,50}}, new Historique()).getPanneau());
        this.preview_jeu.setCenter(new Grille(
            new int[][] {{-2, 2, -1, -1, -1}, {-1, -1, -1, -2, -1}, {-1, 1, -1, -2, -1}, {-1, -1, 4, -2, -1}, {-2, 2, -1, -1, -1}},
            new int[][] {{-2, 2, -1, -1, -1}, {-1, -1, -1, -2, -1}, {-1, 1, -1, -2, -1}, {-1, -1, 4, -2, -1}, {-2, 2, -1, -1, -1}},
        new Historique()).getPanneau());
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
        stage.setScene(new ControllerMenuPrincipal(stage).getScene());
    }

}
