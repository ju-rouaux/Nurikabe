/**
 * Classe implémentant le menu d'aafichage des règles du Nurikabe
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

public class Menu_regles extends Application{
    /**
     * Le boutton pour revenir au menu précédant
     */
    Boutton_retour retour;

   
    /**
     * Constructeur des relges
     */
    Menu_regles() {
        // retour = new Boutton_retour();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../FXML/menu_regles.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Modes de Jeux");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Méthode qui permet de revenir a l'ancienne affichage
     * 
     * 
     * @return void
     */
    public void Charge_Retour() {
        retour.charge_retour();
    }

}
