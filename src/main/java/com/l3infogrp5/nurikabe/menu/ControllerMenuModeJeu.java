/**
 * Classe implémentant le menu de séléction des modes de jeu
 * 
 * @author 
 */

package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;


public class ControllerMenuModeJeu  {

    /**
     * Le boutton pour revenir au menu précédant
     */
    BouttonRetour retour;

    private FXMLLoader loader;
    private Scene scene;


    /**
     * Constructeur du menu des mode de jeu
     */
    public ControllerMenuModeJeu() throws Exception {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_mode.fxml"));
        

        loader.setController(this);

        scene = loader.load(); 
        
        // retour = new Boutton_retour();
    }

    public Scene getScene() { return scene; }


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
