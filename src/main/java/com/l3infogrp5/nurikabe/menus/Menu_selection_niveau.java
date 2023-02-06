/**
 * Classe implémentant le menu de séléction de niveau
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

public class Menu_selection_niveau extends Application{
    /**
     * Le boutton pour revenir au menu précédant
     */
    Boutton_retour retour;


    /**
     * Constructeur du Menue de selection de niveau
     */
    Menu_selection_niveau(int nb_niveaux) {
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
     * @return void
     */
    public void Charge_Retour() {
        retour.charge_retour();
    }

    /**
     * Méthode qui permet d'avoir une preview d'un niveau
     * 
     * 
     * @return void
     */
    public void Niveau_grille() {

    }

    /**
     * Méthode qui charge le niveau a jouer
     * 
     * 
     * @return void
     */
    public void Charge_niveau() {

    }

    /**
     * Méthode quirevoie le menu des scores
     * 
     * 
     * @return void
     */
    public void Charge_menu_score() {

    }

    /**
     * Méthode qui charge la page des sélections de niveaux
     * 
     * 
     * @return void
     */
    public void Charge_Select_menu() {

    }

    /**
     * Méthode qui creer la page des sélections de niveaux
     * 
     * 
     * @return void
     */
    public void Création_Select_menu() {

    }
}
