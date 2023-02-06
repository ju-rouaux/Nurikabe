/**
 * Classe implémentant le menu de séléction de niveau
 * 
 * @author 
 */

package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class ControllerMenuSelectionNiveau  {
    /**
     * Le boutton pour revenir au menu précédant
     */
    BouttonRetour retour;

    private FXMLLoader loader;
    private Scene scene;


    /**
     * Constructeur du Menue de selection de niveau
     */
    public ControllerMenuSelectionNiveau() throws Exception {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_selection_niv.fxml"));
        

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
     * Méthode qui permet d'avoir une preview d'un niveau
     * 
     * 
     */
    public void niveauGrille() {

    }

    /**
     * Méthode qui charge le niveau a jouer
     * 
     * 
     */
    public void chargeNiveau() {

    }

    /**
     * Méthode quirevoie le menu des scores
     * 
     * 
     */
    public void chargeMenuScore() {

    }

    /**
     * Méthode qui charge la page des sélections de niveaux
     * 
     * 
     */
    public void chargeSelectMenu() {

    }

    /**
     * Méthode qui creer la page des sélections de niveaux
     * 
     * 
     */
    public void creationSelectMenu() {

    }
}
