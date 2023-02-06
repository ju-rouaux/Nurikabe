/**
 * Classe implémentant le menu de séléction de niveau
 * 
 * @author 
 */

package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ControllerMenuSelectionNiveau  {
    /**
     * Le boutton pour revenir au menu précédant
     */
    @FXML
    private Button retour;

    private FXMLLoader loader;
    private Scene scene;
    private Stage stage;


    /**
     * Constructeur du Menue de selection de niveau
     */
    public ControllerMenuSelectionNiveau(Stage stage) throws Exception {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_selection_niv.fxml"));
        

        loader.setController(this);

        scene = loader.load();

        // retour = new Boutton_retour();
    }

    public Scene getScene() { return scene; }


    /**
     * Méthode qui permet de revenir a l'ancienne affichage
     * @throws Exception
     * 
     * 
     */
    public void chargerRetour() throws Exception {
        stage.setScene(new ControllerMenuModeJeu(stage).getScene());
    }

    /**
     * Méthode qui permet d'avoir une preview d'un niveau
     * 
     * 
     */
    public void niveauGrille() {

    }

    /**
     * Méthode qui charge le niveau a jouer
     * 
     * 
     */
    public void chargeNiveau() {

    }

    /**
     * Méthode quirevoie le menu des scores
     * 
     * 
     */
    public void chargeMenuScore() {

    }
}
