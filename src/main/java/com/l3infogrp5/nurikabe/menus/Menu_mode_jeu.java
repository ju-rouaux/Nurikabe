/**
 * Classe implémentant le menu de séléction des modes de jeu
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


public class Menu_mode_jeu extends Application{

    /**
     * Le boutton pour revenir au menu précédant
     */
    Boutton_retour retour;


    /**
     * Constructeur du menu des mode de jeu
     */
   /* Menu_mode_jeu() {
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
     * @return void
     */
    public void charge_retour() {
        retour.charge_retour();
    }

    /**
     * Méthode qui charge la grille de niveau selon le mode de jeu selectionner
     * 
     * 
     * @return void
     */
    public void charge_select_niveau() {

    }

    /**
     * Méthode qui revoie le menu des scores
     * 
     * 
     * @return void
     */
    public void charge_menu_score() {

    }

    /**
     * Méthode qui demarre un parti en mode sans fin
     * 
     * 
     * @return void
     */
    public void charge_sans_fin() {

    }

    /**
     * Méthode qui charge la page de séléction des modes de jeu
     * 
     * 
     * @return void
     */
    public void charge_mode_jeu() {

    }

    /**
     * Méthode pour creer la page de séléction des mode de jeu 
     * 
     * 
     * @return void
     */
    public void creation_mode_jeu(){

    }

}
