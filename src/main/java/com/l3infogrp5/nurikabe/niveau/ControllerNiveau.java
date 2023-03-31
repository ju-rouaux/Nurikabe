package com.l3infogrp5.nurikabe.niveau;

import com.l3infogrp5.nurikabe.menu.ControllerMenuModeJeu;
import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.profil.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.Path;

import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Contrôleur d'affichage d'un niveau
 *
 * @author Julien Rouaux
 */
public class ControllerNiveau {

    private final FXMLLoader loader;
    private final Stage stage;
    private final Scene scene;

    private Grille grille;
    private BooleanProperty aide_affichee; // Vrai si l'aide est affichée sur l'écran.
    private Profil joueur;

    @FXML
    private Button btn_aide;
    @FXML
    private Button btn_check;
    @FXML
    private Button btn_redo;
    @FXML
    private Button btn_reset;
    @FXML
    private Button btn_retour;
    @FXML
    private Button btn_undo;
    @FXML
    private ToggleButton toggle_aide;
    @FXML
    private BorderPane panneau_principal;
    @FXML
    private BorderPane panneau_score;
    @FXML
    private StackPane panneau_central;
    @FXML
    private BorderPane panneau_aide;

    @FXML
    private HBox barre;

    private List<Integer> file_niveaux; // Liste des niveaux à jouer successivement
    private int index_file; // Indice du niveau lancé dans la liste

    /**
     * Initialise la vue du niveau.
     *
     * @param stage   la fenêtre contenant la scène.
     * @param niveaux la liste de niveaux à jouer successivement.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerNiveau(Stage stage, List<Integer> niveaux) throws IOException {
        this.stage = stage;
        this.aide_affichee = new SimpleBooleanProperty();
        this.joueur = Profil.getInstance();

        this.file_niveaux = niveaux;
        this.index_file = 0;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/niveau.fxml"));
        loader.setController(this);

        scene = loader.load();
    }

    /**
     * Initialise les éléments de l'interface après le préchargement du FXMLLoader.
     */
    @FXML
    private void initialize() throws Exception {

        // Adapter la largeur de la barre à l'écran
        this.barre.prefWidthProperty().bind(this.panneau_principal.widthProperty().subtract(15));

        // Ne pas faire de rendu en dehors du panneau central (pour ne pas masquer la
        // barre d'outils)
        Rectangle zone_clip = new Rectangle();
        zone_clip.widthProperty().bind(this.panneau_central.widthProperty());
        zone_clip.heightProperty().bind(this.panneau_central.heightProperty());
        this.panneau_central.setClip(zone_clip);

        // Afficher l'aide lorsque la Property aide_affichee est active.
        this.toggle_aide.selectedProperty().bindBidirectional(this.aide_affichee);
        this.toggle_aide.selectedProperty().addListener(new ChangeListener<Boolean>() {
            TranslateTransition transition = new TranslateTransition(Duration.millis(150),
                    ControllerNiveau.this.panneau_aide);

            public void changed(ObservableValue<? extends Boolean> obj, Boolean ancien, Boolean nouveau) {
                // De combien déplacer la fenêtre d'aide, vers le bas si nouveau == true, vers
                // le haut sinon
                transition.setByY((panneau_aide.getHeight() - btn_aide.getHeight()) * (nouveau == true ? 1 : -1));
                transition.play();
            }
        });

        try {
            this.loadNiveauSuivant();
        } catch(Exception e) {
            System.out.println(e);
            this.retourClique();
        }
    }

    /**
     * 
     * @return
     */
    public void loadNiveauSuivant() throws Exception {
        int id_niveau = file_niveaux.get(index_file++);
        Profil.DonneesNiveau donnees = joueur.chargerGrille(id_niveau);
        this.grille = new Grille(donnees.matrice_niveau, donnees.matrice_solution, joueur.chargerHistorique());

        // Mettre la grille au centre (et ajouter une marge)
        Pane panneau_grille = grille.getPanneau();
        StackPane.setMargin(panneau_grille, new Insets(30, 30, 30, 30));
        this.panneau_central.getChildren().add(panneau_grille);
        
        // Recharger la fenêtre d'aide pour l'afficher par dessus la grille
        this.panneau_central.getChildren().remove(panneau_aide);
        this.panneau_central.getChildren().add(panneau_aide);

        // TODO charger les données de score

        // Lier les boutons Undo et Redo à l'historique
        this.btn_undo.disableProperty().bind(this.joueur.getHistorique().peutAnnulerProperty().not());
        this.btn_redo.disableProperty().bind(this.joueur.getHistorique().peutRetablirProperty().not());
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
    private void retourClique() throws Exception {
        // TODO : capturer écran + sauvegarder
        joueur.sauvegarderNiveau(grille);
        // TODO : remplacer null avec le getScore du niveau
        Sauvegarder.sauvegarderScore(Profil.getJoueur(), Profil.getMode_de_jeu(), Profil.getIdNiveau(), null);
        this.grille.capturerGrille(Path.repertoire_lvl.toString() + "/" + Profil.getJoueur() + "/"
                + Profil.getMode_de_jeu() + "/" + "capture_niveau_" + Profil.getIdNiveau() + ".png");
        // stage.setScene(new ControllerMenuNiveau(stage).getScene());
        stage.setScene(new ControllerMenuModeJeu(stage).getScene()); // temporaire
    }

    /**
     * Annule le dernier mouvement de la grille.
     */
    @FXML
    private void undoClique() {
        grille.undo();
    }

    /**
     * Rétabli le dernier mouvement de la grille.
     */
    @FXML
    private void redoClique() {
        grille.redo();
    }
}
