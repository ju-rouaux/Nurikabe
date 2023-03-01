package com.l3infogrp5.nurikabe.niveau;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;

import com.l3infogrp5.nurikabe.profil.Profil;
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
    private BorderPane panneau_score;

    @FXML
    private HBox barre;

    /*
     * temp
     */

    Profil joueur;

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
        joueur = new Profil("Julieng", "detente", 1);

        this.niveau = joueur.chargerNiveau(joueur.getId_niveau());

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/niveau.fxml"));
        loader.setController(this);

        scene = loader.load();
    }

    /**
     * Initialise les éléments de l'interface après le préchargement du FXMLLoader.
     */
    @FXML
    private void initialize() {

        // Adapter la largeur de la barre à l'écran
        this.barre.prefWidthProperty().bind(this.panneau_principal.widthProperty().subtract(15));

        // Mettre la grille au centre (et ajouter une marge)
        Pane grille = this.niveau.getGrille().getPanneau();
        BorderPane.setMargin(grille, new Insets(30, 30, 30, 30));
        this.panneau_principal.setCenter(grille);

        // TODO charger les données de score

        // Lier les boutons Undo et Redo à l'historique
        this.btn_undo.disableProperty().bind(this.niveau.getHistorique().peutAnnulerProperty().not());
        this.btn_redo.disableProperty().bind(this.niveau.getHistorique().peutRetablirProperty().not());
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
    private void retourClique() throws Exception {
        // TODO : capturer écran + sauvegarder
        // stage.setScene(new ControllerMenuNiveau(stage).getScene());
        stage.setScene(new ControllerMenuModeJeu(stage).getScene()); // temporaire
        joueur.sauvegarderNiveau(this.niveau.getGrille().getMatrice(), this.niveau.getHistorique());
    }

    /**
     * Annule le dernier mouvement de la grille.
     */
    @FXML
    private void undoClique() {
        this.niveau.getGrille().undo();
    }

    /**
     * Rétabli le dernier mouvement de la grille.
     */
    @FXML
    private void redoClique() {
        this.niveau.getGrille().redo();
    }
}
