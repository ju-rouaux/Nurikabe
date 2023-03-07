package com.l3infogrp5.nurikabe.niveau;

import com.l3infogrp5.nurikabe.menu.ControllerMenuModeJeu;
import com.l3infogrp5.nurikabe.profil.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.CaptureNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Contrôleur d'affichage d'un niveau
 *
 * @author Julien Rouaux
 */
public class ControllerNiveau {

    Profil joueur;
    private final FXMLLoader loader;
    private final Stage stage;
    private final Scene scene;
    private Pane panneau_grille;
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

    /**
     * Initialise la vue du niveau.
     *
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerNiveau(Stage stage) throws IOException {
        this.stage = stage;
        //TODO charger profil dans le menu de selection des profils
        joueur = new Profil("Julieng");
        joueur.setId_niveau(0);
        joueur.setMode_de_jeu("detente");

        this.joueur.chargerHistorique();
        this.joueur.chargerGrille();


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
        this.panneau_grille = this.joueur.getGrille().getPanneau();
        BorderPane.setMargin(panneau_grille, new Insets(30, 30, 30, 30));
        this.panneau_principal.setCenter(panneau_grille);

        // TODO charger les données de score

        // Lier les boutons Undo et Redo à l'historique
        this.btn_undo.disableProperty().bind(this.joueur.getHistorique().peutAnnulerProperty().not());
        this.btn_redo.disableProperty().bind(this.joueur.getHistorique().peutRetablirProperty().not());
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
        joueur.sauvegarderNiveau();
        //TODO : remplacer null avec le getScore du niveau
        Sauvegarder.sauvegarderScore(joueur.getJoueur(), joueur.getMode_de_jeu(), joueur.getId_niveau(), null);
        CaptureNode.capturer(panneau_grille, joueur.getJoueur(), joueur.getMode_de_jeu(), joueur.getId_niveau());
        // stage.setScene(new ControllerMenuNiveau(stage).getScene());
        stage.setScene(new ControllerMenuModeJeu(stage).getScene()); // temporaire
    }

    /**
     * Annule le dernier mouvement de la grille.
     */
    @FXML
    private void undoClique() {
        this.joueur.getGrille().undo();
    }

    /**
     * Rétabli le dernier mouvement de la grille.
     */
    @FXML
    private void redoClique() {
        this.joueur.getGrille().redo();
    }
}
