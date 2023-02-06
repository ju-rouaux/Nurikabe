/**
 * Classe implémentant le menu de séléction de niveau
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

public class MenuSelectionNiveau extends Application{
    /**
     * Le boutton pour revenir au menu précédant
     */
    BouttonRetour retour;


    /**
     * Constructeur du Menue de selection de niveau
     */
    MenuSelectionNiveau(int nb_niveaux) {
        // retour = new Boutton_retour();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../../FXML/menu_selection_niv.fxml"));

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
