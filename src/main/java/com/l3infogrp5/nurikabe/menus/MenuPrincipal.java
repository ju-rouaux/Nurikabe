/**
 * Classe implémentant le menu principal du Nurikabe
 * 
 * @author 
 */

package com.l3infogrp5.nurikabe.menus;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuPrincipal extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../../../FXML/menu_principal.fxml"));
        

        loader.setController(this);

        Scene scene = loader.load();

        stage.setTitle("Modes de Jeux");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Méthode qui creer le menu principal
     * 
     * 
     */
    public static void creationMenuPrincipal() {

    }

    /**
     * Méthode qui charge le menu principal
     * 
     * 
     */
    public void chargeMenuPrincipal() {

    }

    /**
     * Méthode renvoie au menu des profils
     * 
     * 
     */
    public void chargeProfils() {

    }

    /**
     * Méthode qui renvoie au menu des mode de jeu
     * 
     * 
     */
    public void chargeModeJeu() {

    }

    /**
     * Méthode qui renvoie au menu des regles du jeu
     * 
     * 
     */
    public void chargeRegles() {

    }

    /**
     * Méthode qui ferme le jeu
     * 
     * 
     */
    public void quitter() {

    }

}
