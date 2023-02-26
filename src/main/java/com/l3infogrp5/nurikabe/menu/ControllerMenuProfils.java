package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.Path;
import com.l3infogrp5.nurikabe.profil.Profil;

/**
 * Contrôleur du menu d'affichage des profils, et sa scène.
 * 
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerMenuProfils {

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button btn_retour;

    @FXML
    private GridPane pseudo_grid;

    public String joueur;

    /**
     * Initialise le menu de sélection d'affichage des règles et son contrôleur.
     * 
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuProfils(Stage stage) throws IOException {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_profils.fxml"));
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

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuPrincipal(stage).getScene());
    }

    /**
     * Méthode pour créer un nouveaux profils avec un pseudo
     * 
     * @throws Exception
     */
    @FXML
    private void nouveauxProfil() throws Exception {
        // TODO : Ecrire une methode pour creer un profil avec un pseudo
        // (et potentiellement un avatar)

        Stage popup = new Stage();

        popup.setScene(new ControllerNouveauxProfil(this).getScene());
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();

        System.out.println(joueur);

        Profil nouveaux_profil = new Profil(joueur, null, 0);

    }

    /**
     * Méthode pour charger un profils
     * 
     * @throws Exception
     */
    @FXML
    private void chargerProfil(ActionEvent event) throws Exception {
        // TODO : ecrire une methode pour charger un profil et reprendre le cour de sa
        // partie
        String joueur;
        File dossier_joueur;

        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            // recuperation du boutton appuiyer
            if ((event.getSource()) == (((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0))) {
                // recuperation du pseudo
                joueur = (((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(1)).getText());
                dossier_joueur = Path.repertoire_Save;
                System.out.println(joueur);
                if (joueur != " " && Sauvegarder.dossierExistants(dossier_joueur)) {
                    System.out.println("Profil " + joueur + " charger");
                } else {
                    System.out.println("Creation du profils " + joueur);
                    nouveauxProfil();
                }

            }

        }
    }

}
