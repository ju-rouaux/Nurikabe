/**
 * Classe implémentant le menu principal du Nurikabe
 * 
 * @author 
 */

package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class ControllerMenuPrincipal {

    private FXMLLoader loader;
    private Scene scene;
    private Stage stage;
    

    @FXML
    private Button jouer;

    @FXML
    private Button profils;

    @FXML
    private Button quitter;

    @FXML
    private Button regles;
    
    public ControllerMenuPrincipal(Stage stage) throws Exception {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_principal.fxml"));
        
        loader.setController(this);

        scene = loader.load();
    }

    public Scene getScene() { return scene; }


    @FXML
    void chargeModeJeu(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuModeJeu(stage).getScene());
    }

    @FXML
    void chargeProfils(ActionEvent event) {

    }

    @FXML
    void chargeRegles(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuRegles(stage).getScene());
    }

    @FXML
    void quitter(ActionEvent event) {
        stage.close();
    }

}
