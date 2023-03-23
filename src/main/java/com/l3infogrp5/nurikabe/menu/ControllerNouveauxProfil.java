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
 * @author Julien Rouaux - Nicolas
 */
public class ControllerNouveauxProfil {

    private FXMLLoader loader;
    private Scene scene;

    private ControllerMenuProfils profils;

    private boolean pseudo_correct;

    @FXML
    private TextField pseudo;

    /**
     * Initialise la fenêtre de création d'un nouveau profil et son contrôleur.
     *
     * @throws IOException {@inheritDoc}
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
     * @return La scène gérée par le contrôleur.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Test si la touche "Enter" est appuyer pour fermer la fenêtre
     *
     * @param event Les touches du clavier appuyé
     */
    @FXML
    private void isEnter(KeyEvent event) {
        // Si on appuie sur entrer
        if (event.getCode() == KeyCode.ENTER) {
            close();
        }
    }

    /**
     * Test si un pseudo qui porte le même nom existe déjà pour empêcher les
     * doublons
     *
     * @return boolean | true si un profil avec ce pseudo existe sinon false
     */
    private boolean pseudoExist() {
        if (profils.getProfilsAttributs().contains(pseudo.getText()))
            return true;
        return false;
    }

    /**
     * Méthode qui re test le pseudo saisi a chaque nouvel input du clavier et le
     * stocke s'il est correct
     */
    @FXML
    private void getPseudo() {
        // Définition d'une regexp qui n'autorise que les lettres et chiffres dans la
        // limite de 10 caractères
        Pattern p = Pattern.compile("^[a-zA-Z0-9]{1,10}$");

        // Vérifie que le pseudo saisi respecte le pattern.
        Matcher m = p.matcher(pseudo.getText());
        if (m.matches()) {
            profils.nom_joueur = pseudo.getText();
            pseudo_correct = true;
        } else {
            pseudo_correct = false;
        }

        // Test si le pseudo saisi existe déjà
        if (pseudoExist()) {
            pseudo_correct = false;
        }
    }

    /**
     * Méthode pour fermer la fenêtre si le pseudo rentré est correct rentré est
     * correcte
     */
    @FXML
    private void close() {
        if (pseudo.getLength() == 0 || !pseudo_correct) {
            pseudo.setPromptText("Saisir un pseudo !");
        } else {
            ((Stage) this.scene.getWindow()).close();
        }
    }
}
