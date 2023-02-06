/**
 * Classe implémentant le menu de séléction des modes de jeu
 * 
 * @author 
 */

package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerMenuModeJeu {

    /**
     * Le boutton pour revenir au menu précédant
     */
    @FXML
    private Button retour;

    private FXMLLoader loader;
    private Scene scene;
    private Stage stage;

    /**
     * Constructeur du menu des mode de jeu
     */
    public ControllerMenuModeJeu(Stage stage) throws Exception {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_mode.fxml"));

        loader.setController(this);

        scene = loader.load();

    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Méthode qui permet de revenir a l'ancienne affichage
     * @throws Exception
     * 
     * 
     */
    public void chargerRetour() throws Exception {
        stage.setScene(new ControllerMenuPrincipal(stage).getScene());
    }

    /**
     * Méthode qui charge la grille de niveau selon le mode de jeu selectionner
     * @throws Exception
     * 
     * 
     */
    public void chargeSelectNiveau() throws Exception {
        stage.setScene(new ControllerMenuSelectionNiveau(stage).getScene());
    }

    /**
     * Méthode qui revoie le menu des scores
     * 
     * 
     */
    public void chargeMenuScore() {

    }

    /**
     * Méthode qui demarre un parti en mode sans fin
     * 
     * 
     */
    public void chargeSansFin() {

    }

}
