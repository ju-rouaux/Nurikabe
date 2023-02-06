/**
 * Classe implémentant le menu d'aafichage des règles du Nurikabe
 * 
 * @author 
 */

package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerMenuRegles {

    @FXML
    private Button retour;


    private FXMLLoader loader;
    private Scene scene;
    private Stage stage;


    public ControllerMenuRegles(Stage stage) throws Exception {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_regles.fxml"));
        

        loader.setController(this);

        scene = loader.load();
    }

    public Scene getScene() { return scene; }

    

    public void chargerRetour() throws Exception {
        stage.setScene(new ControllerMenuPrincipal(stage).getScene());
    }

}
