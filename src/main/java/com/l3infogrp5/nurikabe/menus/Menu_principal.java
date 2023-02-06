/**
 * Classe implémentant le menu principal du Nurikabe
 * 
 * @author 
 * @version 1.0
 */

package com.l3infogrp5.nurikabe.menus;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Menu_principal extends Application{

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
     * @return void
     */
    public static void creation_menu_principal() {

    }

    /**
     * Méthode qui charge le menu principal
     * 
     * 
     * @return void
     */
    public void charge_menu_principal() {

    }

    /**
     * Méthode renvoie au menu des profils
     * 
     * 
     * @return void
     */
    public void charge_profils() {

    }

    /**
     * Méthode qui renvoie au menu des mode de jeu
     * 
     * 
     * @return void
     */
    public void charge_mode_jeu() {

    }

    /**
     * Méthode qui renvoie au menu des regles du jeu
     * 
     * 
     * @return void
     */
    public void charge_regles() {

    }

    /**
     * Méthode qui ferme le jeu
     * 
     * 
     * @return void
     */
    public void quitter() {

    }

}
