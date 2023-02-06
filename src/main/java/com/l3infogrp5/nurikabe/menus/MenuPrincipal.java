/**
 * Classe implémentant le menu principal du Nurikabe
 * 
 * @author 
 */

package com.l3infogrp5.nurikabe.menus;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MenuPrincipal extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../FXML/menu_principal.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Modes de Jeux");
        stage.setScene(scene);
        stage.show();
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
