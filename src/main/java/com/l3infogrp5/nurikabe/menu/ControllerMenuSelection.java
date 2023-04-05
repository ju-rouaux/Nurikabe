package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.ControllerNiveau;
import com.l3infogrp5.nurikabe.profil.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;

/**
 * Contrôleur du menu de sélection de niveau, et sa scène.
 * Lorsqu'un niveau est sélectionné, ce dernier est immédiatement lancé, seul.
 *
 * @author Julien Rouaux
 */
public class ControllerMenuSelection {

    /**
     * Petite carte de sélection de niveau qui lance un niveau lorsqu'elle est
     * cliquée.
     *
     * @author Julien Rouaux
     */
    private class ControllerSelection {
        private int id_niveau;
        private BorderPane panneau;

        /**
         * Créer une carte de sélection qui lancera le niveau donné.
         * 
         * @param id_niveau l'id du niveau à lancer.
         */
        ControllerSelection(int id_niveau) throws IOException {
            this.id_niveau = id_niveau;
            FXMLLoader loader_selection = new FXMLLoader();
            loader_selection.setLocation(getClass().getResource("/FXML/selection.fxml"));
            loader_selection.setController(this);

            this.panneau = loader_selection.load();
        }

        /**
         * Charge l'image et le nom du niveau sur la carte.
         */
        @FXML
        private void initialize() {
            File image = new File(ControllerMenuSelection.this.liens_images.get(id_niveau));
            this.image.setImage(new Image(image.toURI().toString()));
            this.texte.setText("Niveau " + (id_niveau + 1));
        }

        /**
         * Retourne le panneau pour afficher la carte.
         * @return le panneau pour afficher la carte.
         */
        Pane getPanneau() {
            return this.panneau;
        }

        @FXML
        private ImageView image;

        @FXML
        private Label texte;

        /**
         * Lance la fenêtre d'affichage des scores.
         */
        @FXML
        private void scoreClique() throws IOException{
            // TODO charger score ici
            System.out.println("Lancement score " + (id_niveau));
            stage.setScene(new ControllerLeaderboard(stage, id_niveau).getScene());
        }

        /**
         * Lance le niveau associé à la carte.
         * @throws IOException lancé lorsque le niveau n'a pas pû être chargé.
         */
        @FXML
        private void selectionClique() throws IOException {
            stage.setScene(new ControllerNiveau(stage, List.of(id_niveau)).getScene());
            System.out.println("Lancement niveau " + id_niveau);
        }
    }

    // Quantité de niveaux affichés sur le menu par page
    private final int NOMBRE_ENTREE = 6;
    // Nombre de lignes de la grille affichée
    private final int NOMBRE_LIGNES = 2;
    // Numéro de la page chargée
    private IntegerProperty page_chargee;
    // Lien des images
    private List<String> liens_images;
    // Nombre de niveaux totaux
    private int nb_grilles;

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;

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
    public ControllerMenuSelection(Stage stage) throws IOException {
        this.stage = stage;
        this.page_chargee = new SimpleIntegerProperty(1);
        this.nb_grilles = Sauvegarder.nbGrilles(Profil.getMode_de_jeu());
        this.liens_images = Profil.chargerImageNiveau();

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

        // Appeler le chargement de page à chaque changement de page
        this.page_chargee.addListener((obj, o, n) -> {
            try {
                chargerPage(page_chargee.get());
            } catch (IOException e) {
            }
        });
    }

    /**
     * Charge la page donnée dans la grille de l'interface.
     *
     * @param p la page à charger (à partir de 1)
     * @throws IOException lancé lorsqu'une carte de niveau n'a pas pû être créée.
     */
    private void chargerPage(int p) throws IOException {
        this.grille.getChildren().clear(); // Vider la grille
        for (int i = 0; i < NOMBRE_LIGNES; i++)
            for (int j = 0; j < NOMBRE_ENTREE / NOMBRE_LIGNES; j++) {
                // id = [1..6] + quantité_de_niveau_par_page*(numéro_de_page-1)
                int id_niveau = (i * NOMBRE_ENTREE / NOMBRE_LIGNES + j) + (this.page_chargee.get() - 1) * NOMBRE_ENTREE;

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
