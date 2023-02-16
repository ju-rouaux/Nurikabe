package com.l3infogrp5.nurikabe.niveau;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import com.l3infogrp5.nurikabe.menu.ControllerMenuModeJeu;

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
    private Button btn_aide;

    @FXML
    private Button btn_check;

    @FXML
    private Button btn_redo;

    @FXML
    private Button btn_reset;

    @FXML
    private Button btn_retour;

    @FXML
    private Button btn_undo;

    @FXML
    private BorderPane panneau_principal;

    @FXML
    private GridPane panneau_grille;

    /**
     * Initialise la vue du niveau.
     * 
     * @param stage  la fenêtre contenant la scène.
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
        // Lier la taille de la grille à la taille de la fenêtre
        panneau_grille.prefWidthProperty()
                .bind(Bindings.min(this.stage.widthProperty(), this.stage.heightProperty()).subtract(200));
        panneau_grille.prefHeightProperty()
                .bind(panneau_grille.prefWidthProperty());

        panneau_grille.getStylesheets().add("/css/niveau.css");
        this.niveau.getGrille().remplirPanneau(panneau_grille);
    
        panneau_principal.setCenter(panneau_grille);

        // TODO charger les données de score
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
        // TODO : capturer écran + sauvegarder
        // stage.setScene(new ControllerMenuNiveau(stage).getScene());
        stage.setScene(new ControllerMenuModeJeu(stage).getScene()); // temporaire
    }
}
