package com.l3infogrp5.nurikabe.niveau;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * Contrôleur d'affichage d'un niveau
 * 
 * @author Julien Rouaux
 */
public class ControllerNiveau {

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;

    private Niveau niveau;

    @FXML
    private BorderPane panneau;

    /**
     * Initialise la vue du niveau.
     * 
     * @param stage la fenêtre contenant la scène.
     * @param niveau le niveau à lancer.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerNiveau(Stage stage, Niveau niveau) throws IOException {
        this.stage = stage;
        this.niveau = niveau;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/niveau.fxml"));
        loader.setController(this);

        scene = loader.load();
    }

    /**
     * Initialise les éléments de l'interface après le préchargement du FXMLLoader.
     */
    @FXML
    public void initialize() {
        panneau.setCenter(this.niveau.getGrille().getGridPane());   //TODO charger la grille ici
                                                                    //TODO charger les données de score
    }

    /**
     * Retourne la scène gérée par le contrôleur.
     * 
     * @return la scène gérée par le contrôleur.
     */
    public Scene getScene() {
        return scene;
    }
}
