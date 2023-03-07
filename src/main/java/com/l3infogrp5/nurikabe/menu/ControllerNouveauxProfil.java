package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Contrôleur du menu d'affichage des profils, et sa scène.
 * 
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerNouveauxProfil {

    private FXMLLoader loader;
    private Scene scene;

    private ControllerMenuProfils profils;

    @FXML
    private TextField pseudo;

    /**
     * Initialise le menu de sélection d'affichage des règles et son contrôleur.
     * 
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerNouveauxProfil(ControllerMenuProfils profils) throws IOException {
        this.profils = profils;
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/nouveaux_profil.fxml"));
        loader.setController(this);
        scene = loader.load();
    }

    /**
     * Retourne la scène gérée par le contrôleur.
     * 
     * @return la scène gérée par le contrôleur.
     */
    public Scene getScene() {
        return scene;
    }

    @FXML
    private void getPseudo() {
        profils.joueur = pseudo.getText();
    }

    @FXML
    private void close() {
        if(pseudo.getLength() == 0){
            pseudo.setPromptText("Saisir un pseudo");
        }
        else{
            ((Stage) this.scene.getWindow()).close();
        }
        
    }

}

// TODO : appyer sur entre pour cree nouveaux profile