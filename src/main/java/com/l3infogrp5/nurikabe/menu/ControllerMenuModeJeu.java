package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

//TODO imports pour la démo de lancement de niveau
import com.l3infogrp5.nurikabe.niveau.ControllerNiveau;
//TODO Charger images placeholder selon le profil dans le menu de selection des niveaux
import com.l3infogrp5.nurikabe.profil.Profil;

/**
 * Contrôleur du menu de sélection de mode de jeu, et sa scène.
 *
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerMenuModeJeu {

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;
    Profil joueur;

    @FXML
    private Button btn_detente;

    @FXML
    private Button btn_contre_montre;

    @FXML
    private Button btn_sans_fin;

    @FXML
    private Button btn_score;

    @FXML
    private Button btn_retour;

    /**
     * Initialise le menu de sélection de mode de jeu et son contrôleur.
     *
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuModeJeu(Stage stage, Profil joueur) throws IOException {
        /*
         * Test chargement des images
         */
        // TODO Changer parametres avec <nomProfil>.get()
//        Profil.chargerImageNiveau("Julieng", "detente");

        this.joueur = joueur;

        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_mode.fxml"));
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

    /**
     * Change la scène par le menu de sélection de niveau du mode détente.
     */
    @FXML
    private void detenteClique() throws Exception {
        // stage.setScene(new ControllerMenuNiveau(stage).getScene()); //TODO rétablir
        // le menu
        stage.setScene(new ControllerNiveau(stage, joueur).getScene());
    }

    /**
     * Change la scène par le menu de sélection de niveau du mode contre la montre.
     */
    @FXML
    private void contreMontreClique() throws Exception {
        stage.setScene(new ControllerMenuNiveau(stage, joueur).getScene());
    }

    /**
     * Change la scène par une partie du mode sans fin.
     */
    @FXML
    private void sansFinClique() {

    }

    /**
     * Change la scène par le menu de consultation des scores du mode sans fin.
     */
    @FXML
    private void scoreClique() {

    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique() throws Exception {
        stage.setScene(new ControllerMenuPrincipal(stage, joueur).getScene());
    }
}
