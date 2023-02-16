package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Contrôleur du menu de sélection de niveau, et sa scène.
 * 
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerMenuNiveau {

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;

    private Grille_niveau grille;


    @FXML
    private Button btn_retour;
    @FXML
    private BorderPane panneau;
    /**
     * Initialise le menu de sélection de niveau et son contrôleur.
     * 
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuNiveau(Stage stage) throws IOException {
        this.stage = stage;
        this.grille=new Grille_niveau();

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

    /**
     * Initialise les éléments de l'interface après le préchargement du FXMLLoader.
     */
    @FXML
    public void initialize() {
        panneau.setCenter(this.grille.getGridPane());   //TODO charger la grille ici
                                                        //TODO charger les données de score
    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuModeJeu(stage).getScene());
    }
}
