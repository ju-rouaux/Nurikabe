package com.l3infogrp5.nurikabe.menu;

import com.l3infogrp5.nurikabe.sauvegarde.ModeDeJeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.ControllerNiveau;
import com.l3infogrp5.nurikabe.sauvegarde.Profil;

/**
 * Contrôleur du menu de sélection de mode de jeu, et sa scène.
 *
 * @author Julien Rouaux - Nicolas
 */
public class ControllerMenuModeJeu {

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button btn_detente;

    @FXML
    private Button btn_contre_montre;

    @FXML
    private Button btn_sans_fin;

    @FXML
    private Button btn_score;

    @FXML
    private Button btn_retour;

    /**
     * Initialise le menu de sélection de mode de jeu et son contrôleur.
     *
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuModeJeu(Stage stage) throws IOException {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_mode.fxml"));
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
     * Change la scène par le menu de sélection de niveau du mode détente.
     */
    @FXML
    private void detenteClique() throws Exception {
        Profil.getInstance().setModeDeJeu(ModeDeJeu.DETENTE);
        stage.setScene(new ControllerMenuSelection(stage).getScene());
    }

    /**
     * Change la scène par le menu de sélection de niveau du mode contre la montre.
     */
    @FXML
    private void contreMontreClique() throws Exception {
        Profil.getInstance().setModeDeJeu(ModeDeJeu.CONTRELAMONTRE);
        stage.setScene(new ControllerMenuSelection(stage).getScene());
    }

    /**
     * Change la scène par une partie du mode sans fin.
     */
    @FXML
    private void sansFinClique() throws Exception {
        Profil.getInstance().setModeDeJeu(ModeDeJeu.SANSFIN);
        stage.setScene(new ControllerNiveau(stage, List.of(
            0, 1//, 3, 4, 5, 6, 7, 8, 9, 10 //TODO selectionner les niveaux à jouer
        )).getScene());
    }

    /**
     * Change la scène par le menu de consultation des scores du mode sans fin.
     */
    @FXML
    private void scoreClique() throws IOException {
        Profil.getInstance().setModeDeJeu(ModeDeJeu.SANSFIN);
        stage.setScene(new ControllerLeaderboard(stage, 0).getScene());
    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique() throws Exception {
        stage.setScene(new ControllerMenuPrincipal(stage).getScene());
    }
}
