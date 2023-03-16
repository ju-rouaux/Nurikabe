package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contrôleur du menu d'affichage des profils, et sa scène.
 * 
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerNouveauxProfil {

    private FXMLLoader loader;
    private Scene scene;

    private ControllerMenuProfils profils;

    boolean pseudo_correct ;

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
        this.pseudo_correct = false;

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
    private void isEnter(KeyEvent event) {
        // si on appuie sur entrer
        if (event.getCode() == KeyCode.ENTER) {
            close();
        }
    }

    private boolean pseudoExist(){
        for(String str : profils.profils_attributs){
            if(str != null && str.equals(pseudo.getText())){
                return true;
            }
        }
        return false;
    }

    @FXML
    private void getPseudo() {
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{1,10}$");
        Matcher m = p.matcher(pseudo.getText());
        
        if(m.matches()){
            profils.nom_joueur = pseudo.getText();
            pseudo_correct = true;
        }
        else {
            pseudo_correct = false;
        }

        if(pseudoExist()){
            pseudo_correct = false;
        }

    }

    @FXML
    private void close() {
        if (pseudo.getLength() == 0 || !pseudo_correct) {
            pseudo.setPromptText("Saisir un pseudo !");
        } else {
            ((Stage) this.scene.getWindow()).close();
        }

    }

}