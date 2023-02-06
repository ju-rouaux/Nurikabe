/**
 * Classe implémentant le menu d'aafichage des règles du Nurikabe
 * 
 * @author 
 */

package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class ControllerMenuRegles {
    /**
     * Le boutton pour revenir au menu précédant
     */
    BouttonRetour retour;

    private FXMLLoader loader;
    private Scene scene;


    /**
     * Constructeur des relges
     */
    public ControllerMenuRegles() throws Exception {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_regles.fxml"));
        

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
    public void ChargeRetour() {
        retour.chargeRetour();
    }

}
