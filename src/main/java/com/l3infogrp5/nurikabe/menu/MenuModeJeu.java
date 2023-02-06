/**
 * Classe implémentant le menu de séléction des modes de jeu
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


public class MenuModeJeu extends Application{

    /**
     * Le boutton pour revenir au menu précédant
     */
    BouttonRetour retour;


    /**
     * Constructeur du menu des mode de jeu
     */
    /* MenuModeJeu() {
        // retour = new Boutton_retour();
    }*/

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../FXML/menu_mode.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Modes de Jeux");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Méthode qui permet de revenir a l'ancienne affichage
     * 
     * 
     */
    public void chargeRetour() {
        retour.chargeRetour();
    }

    /**
     * Méthode qui charge la grille de niveau selon le mode de jeu selectionner
     * 
     * 
     */
    public void chargeSelectNiveau() {

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

    /**
     * Méthode qui charge la page de séléction des modes de jeu
     * 
     * 
     */
    public void chargeModeJeu() {

    }

    /**
     * Méthode pour creer la page de séléction des mode de jeu 
     * 
     * 
     */
    public void creationModeJeu(){

    }

}
