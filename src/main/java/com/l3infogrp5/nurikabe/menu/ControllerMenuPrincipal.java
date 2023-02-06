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

public class ControllerMenuPrincipal {

    private FXMLLoader loader;
    private Scene scene;
    
    public ControllerMenuPrincipal() throws Exception {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_principal.fxml"));
        

        loader.setController(this);

        scene = loader.load();
    }

    public Scene getScene() { return scene; }

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
