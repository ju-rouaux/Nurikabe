package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;

/**
 * Contrôleur du menu de sélection de niveau, et sa scène.
 * Lorsqu'un niveau est sélectionné, ce dernier est immédiatement lancé, seul.
 *
 * @author Julien Rouaux
 */
public class ControllerMenuNiveau {

    /**
     * 
     */
    private class ControllerSelection {
        private int id_niveau;
        private BorderPane panneau;

        ControllerSelection(int id_niveau) throws IOException {
            this.id_niveau = id_niveau;
            FXMLLoader loader_selection = new FXMLLoader();
            loader_selection.setLocation(getClass().getResource("/FXML/selection.fxml"));
            loader_selection.setController(this);

            this.panneau = loader_selection.load();
        }

        @FXML
        private void initialize() {
            // TODO set l'image ici
            //this.image.setImage(null);
            this.texte.setText("Niveau " + id_niveau);
        }

        Pane getPanneau() {
            return this.panneau;
        }

        @FXML
        private ImageView image;

        @FXML
        private Label texte;

        @FXML
        private void scoreClique() {
            // TODO charger score ici
            System.out.println("Lancement score " + id_niveau);
        }

        @FXML
        private void selectionClique() {
            // TODO lancer niveau ici
            System.out.println("Lancement niveau " + id_niveau);
        }
    }

    // Quantité de niveaux affichés sur le menu par page
    private final int NOMBRE_ENTREE = 6;
    // Nombre de lignes de la grille affichée
    private final int NOMBRE_LIGNES = 2;
    
    private IntegerProperty page_chargee;

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;

    // Nombre de niveaux totaux
    private int nb_grilles;

    @FXML
    private Button btn_retour;
    @FXML
    private GridPane grille;
    @FXML
    private Button btn_precedent;
    @FXML
    private Button btn_suivant;

    /**
     * Initialise le menu de sélection de niveau et son contrôleur.
     *
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuNiveau(Stage stage) throws IOException {
        this.stage = stage;
        this.page_chargee = new SimpleIntegerProperty(1);
        this.nb_grilles = Sauvegarder.nbGrilles("detente"); // TODO pas ouf d'écrire en dur le mode

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_selection.fxml"));
        loader.setController(this);

        scene = loader.load();
    }

    /**
     * Initialise les éléments de l'interface après le préchargement du FXMLLoader.
     * 
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException {

        // Remplir la grille de la première page
        this.chargerPage(page_chargee.get());

        // Désactiver les boutons précédent/suivant si l'action n'est plus possible
        this.btn_precedent.disableProperty().bind(this.page_chargee.lessThanOrEqualTo(1));
        this.btn_suivant.disableProperty()
                .bind(this.page_chargee.greaterThanOrEqualTo(this.nb_grilles / NOMBRE_ENTREE));

        this.page_chargee.addListener((obj, o, n) -> {
            try {
                chargerPage(page_chargee.get());
            } catch (IOException e) {
            }
        });
    }

    /**
     * Charge la page donnée.
     */
    private void chargerPage(int p) throws IOException {
        this.grille.getChildren().clear(); //Vider la grille
        for (int i = 0; i < NOMBRE_LIGNES; i++)
            for (int j = 0; j < NOMBRE_ENTREE / NOMBRE_LIGNES; j++) {
                // id = [1..6] + quantité_de_niveau_par_page*(numero_de_page-1)
                int id_niveau = (i*NOMBRE_ENTREE/NOMBRE_LIGNES + j + 1) + (this.page_chargee.get()-1)*NOMBRE_ENTREE; 

                this.grille.add(new ControllerSelection(id_niveau).getPanneau(), j, i);
            }
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
     * Incrémente la valeur page_chargee pour passer à la page précédente.
     */
    @FXML
    private void suivantClique() {
        if (this.page_chargee.get() < (this.nb_grilles / NOMBRE_ENTREE))
            this.page_chargee.set(this.page_chargee.get() + 1);
    }

    
    /**
     * Décrémente la valeur page_chargee pour passer à la page précédente.
     */
    @FXML
    private void precedentClique() {
        if (this.page_chargee.get() >= 1)
            this.page_chargee.set(this.page_chargee.get() - 1);
    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique() throws Exception {
        stage.setScene(new ControllerMenuModeJeu(stage).getScene());
    }
}
