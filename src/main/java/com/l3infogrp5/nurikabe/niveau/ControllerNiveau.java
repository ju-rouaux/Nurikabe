package com.l3infogrp5.nurikabe.niveau;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import com.l3infogrp5.nurikabe.Main;
import com.l3infogrp5.nurikabe.menu.ControllerMenuModeJeu;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;

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
    private BorderPane panneau_central;

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
    private void initialize() {

        // Récupérer taille matrice
        int[][] matrice = niveau.getGrille().getMatrice(); // TODO TEMPORAIRE
        int hauteur = matrice.length;
        int largeur = matrice[0].length;

        GridPane panneau_grille = new GridPane();

        // Adapter la taille du panneau de la grille au panneau central
        NumberBinding ratio = Bindings.min(
                this.panneau_central.widthProperty().divide(largeur),
                this.panneau_central.heightProperty().divide(hauteur));
        panneau_grille.maxWidthProperty().bind(ratio.multiply(largeur));
        panneau_grille.maxHeightProperty().bind(ratio.multiply(hauteur));

        // Définir l'écart entre les cases
        panneau_grille.setVgap(5);
        panneau_grille.setHgap(5);

        // Remplir le panneau grille de la grille du niveau
        this.niveau.getGrille().remplirPanneau(panneau_grille);

        // Mettre la grille dans le panneau central
        this.panneau_central.setCenter(panneau_grille);

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

    /**
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique() throws Exception {
        // TODO : capturer écran + sauvegarder
        // stage.setScene(new ControllerMenuNiveau(stage).getScene());
        stage.setScene(new ControllerMenuModeJeu(stage).getScene()); // temporaire
        Sauvegarder.sauvegarderMouvement(Main.joueur, Main.mode_De_Jeu, Main.id_Niveau, niveau.getHistorique());
        System.out.println("Sauvegarde des mouvements en cours");

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
