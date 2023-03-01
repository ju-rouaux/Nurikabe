package com.l3infogrp5.nurikabe.menu;

import com.l3infogrp5.nurikabe.profil.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    ControllerMenuPrincipal main_menu;

    /**
     * Initialise le menu de sélection d'affichage des règles et son contrôleur.
     *
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuProfils(Stage stage, ControllerMenuPrincipal main_menu) throws IOException {
        this.stage = stage;
        this.main_menu = main_menu;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_profils.fxml"));
        loader.setController(this);
        scene = loader.load();

        new Profil(((Label) ((VBox) pseudo_grid.getChildren().get(0)).getChildren().get(1)).getText(), null, 0);
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
        stage.setScene(main_menu.getScene());
    }

    /**
     * Modfie l'affichage du menue profile pour afficher le profile du nouveaux
     * joueur
     * 
     * @param i l'indice de la grille a modifier
     * 
     * @throws Exception
     */
    @FXML
    private void afficherNouveauxProfil(int i) throws Exception {
        // affiche le nom du nouveaux profil
        ((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(1)).setText(joueur);

        // supprime le text du boutton
        ((Button) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0)).setText("");

        // suppression du style d'ajout de profil pour le boutton et ajout du style de
        // profil existant
        ((Button) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0)).getStyleClass()
                .remove("btn-add");
        ((Button) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0)).getStyleClass()
                .add("btn-profile");

        // si on a encore de la place on rend visible l'ajout de profile suivant
        if (i + 1 < pseudo_grid.getChildren().size())
            ((Button) ((VBox) pseudo_grid.getChildren().get(i + 1)).getChildren().get(0)).setVisible(true);
    }

    /**
     * Méthode pour créer un nouveaux profils avec un pseudo
     * 
     * @param i l'indice de la grille a modifier
     *
     * @throws Exception
     */
    @FXML
    private void nouveauxProfil(int i) throws Exception {
        // creation de la popup pour cree un nouveaux profil
        Stage popup = new Stage();

        popup.setScene(new ControllerNouveauxProfil(this).getScene());
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();

        new Profil(joueur, null, 0);

        // modification de l'affichage
        afficherNouveauxProfil(i);
    }

    /**
     * Méthode pour charger un profils
     *
     * @throws Exception
     */
    @FXML
    private void chargerProfil(ActionEvent event) throws Exception {

        File f = new File("target/classes/FXML/menu_profils.fxml"); // en construction pour sauvegarder les modif (phase
                                                                    // de tests)

        // on parcour la grid des profil pour savoir lequel est appuiyer
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            // recuperation du boutton appuiyer
            if ((event.getSource()) == (((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0))) {
                // recuperation du pseudo
                joueur = (((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren()
                        .get(1)).getText());

                if (Sauvegarder.RechercherSauvegarde(joueur)) {
                    System.out.println("Profil " + joueur + " charger");
                } else {
                    System.out.println("Creation du profils " + joueur);
                    nouveauxProfil(i);
                }
            }
        }
    }
}

// TODO : creer un profile par default (Default)
// TODO : modifier fxml pour sauvagarder modif visuel des nouveaux profil
// TODO : indiquer visuellement le profile en cour d'utilisation
// TODO : Permetre de supprimer un profile

// IDEA : different profil meme icon fond de couleur different
// IDEA : potentiellement laisser joueur modifier pseudo et couleur de fond