package com.l3infogrp5.nurikabe.niveau;

import com.l3infogrp5.nurikabe.menu.ControllerMenuModeJeu;
import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.score.ScoreInterface;
import com.l3infogrp5.nurikabe.profil.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.ModeDeJeu;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import java.util.Optional;

/**
 * Contrôleur d'affichage d'un niveau
 *
 * @author Julien Rouaux
 */
public class ControllerNiveau {

    private final FXMLLoader loader;
    private final Stage stage;
    private final Scene scene;
    private final BooleanProperty aide_affichee; // Vrai si l'aide est affichée sur l'écran.
    private final Profil joueur;
    private final List<Integer> file_niveaux; // Liste des niveaux à jouer successivement
    private Grille grille;
    private ScoreInterface score;
    private boolean niveau_en_cours; // Vrai si le niveau est en cours de jeu.
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

        this.niveau_en_cours = true;

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
        this.barre.prefWidthProperty().bind(this.panneau_principal.widthProperty().subtract(40));

        // Ne pas faire de rendu en dehors du panneau central (pour ne pas masquer la
        // barre d'outils)
        Rectangle zone_clip = new Rectangle();
        zone_clip.widthProperty().bind(this.panneau_central.widthProperty());
        zone_clip.heightProperty().bind(this.panneau_central.heightProperty());
        this.panneau_central.setClip(zone_clip);

        // Afficher l'aide lorsque la Property aide_affichee est active.
        this.toggle_aide.selectedProperty().bindBidirectional(this.aide_affichee);
        this.toggle_aide.selectedProperty().addListener(new ChangeListener<Boolean>() {
            final TranslateTransition transition = new TranslateTransition(Duration.millis(150),
                ControllerNiveau.this.panneau_aide);

            public void changed(ObservableValue<? extends Boolean> obj, Boolean ancien, Boolean nouveau) {
                // De combien déplacer la fenêtre d'aide, vers le bas si nouveau == true, vers
                // le haut sinon
                transition.setByY((panneau_aide.getHeight() - btn_aide.getHeight()) * (nouveau ? 1 : -1));
                transition.play();
            }
        });

        try {
            this.loadNiveauSuivant();
        } catch (Exception e) {
            System.out.println(e);
            this.retourClique();
        }
        // Charger le premier niveau
        this.loadNiveauSuivant();
    }

    /**
     * Charge la grille suivante dans la liste des niveaux à jouer.
     *
     * @throws Exception lancée lorsque la grille n'a pas pû être chargée.
     */
    public void loadNiveauSuivant() throws Exception {
//    TODO a corriger
//        int id_niveau = file_niveaux.get(index_file++);

        int id_niveau = 0;

        Profil.DonneesNiveau donnees = joueur.getDonneesNiveau(id_niveau, niveau_en_cours);
        this.grille = new Grille(donnees.matrice_niveau, donnees.matrice_solution, joueur.getDonneesNiveau(id_niveau,niveau_en_cours).getHistorique());
        this.grille.addOnVictoire(() -> niveau_en_cours = false);

        // Evenement lancé lorsque la grille est terminée
        this.grille.addOnVictoire(() -> {
            System.out.println("gagné !");
            // Arrêter le comptage du score
            this.score.stop();

            // Afficher une popup de victoire
            Alert popup = new Alert(AlertType.NONE);
            popup.setTitle("Grille terminée !");
            ButtonType btn_quitter;

            // Le joueur poursuit sa partie
            if (index_file < file_niveaux.size()) {
                popup.setContentText("Nombre de grilles terminées : " + score.getScore() + ". Cliquez pour poursuivre.");
                ButtonType btn_suivant = new ButtonType("Grille suivante");
                btn_quitter = new ButtonType("Abandonner");
                popup.getButtonTypes().add(btn_suivant);
            } else { // Le joueur a terminé la partie
                popup.setContentText("Partie terminée ! Score : " + score.getScore() + ".");
                btn_quitter = new ButtonType("Quitter");
            }

            popup.getButtonTypes().add(btn_quitter);
            Optional<ButtonType> result = popup.showAndWait();

            if (result.get() == btn_quitter)
                this.retourClique();
            else {
                try {
                    this.loadNiveauSuivant();
                } catch (Exception e) {
                    this.retourClique();
                }
            }
        });

        // Mettre la grille au centre (et ajouter une marge)
        Pane panneau_grille = grille.getPanneau();
        StackPane.setMargin(panneau_grille, new Insets(30, 30, 30, 30));
        this.panneau_central.getChildren().add(panneau_grille);

        // Recharger la fenêtre d'aide pour l'afficher par dessus la grille
        this.panneau_central.getChildren().remove(panneau_aide);
        this.panneau_central.getChildren().add(panneau_aide);

        // TODO charger les données de score depuis le profil
        this.score = Profil.getInstance().getDonneesNiveau(id_niveau,niveau_en_cours).score;
        this.panneau_score.setCenter(this.score.get_Pane());

        // Lier les boutons Undo et Redo à l'historique
        this.btn_undo.disableProperty().bind(this.joueur.getDonneesNiveau(id_niveau,niveau_en_cours).getHistorique().peutAnnulerProperty().not());
        this.btn_redo.disableProperty().bind(this.joueur.getDonneesNiveau(id_niveau,niveau_en_cours).getHistorique().peutRetablirProperty().not());

        this.score.start();
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
    private void retourClique() {
        try {
            Profil.setScore(score.getScore(), niveau_en_cours);
            if (Profil.getMode_de_jeu() != ModeDeJeu.SANSFIN)
                Profil.getInstance().sauvegarderNiveau(grille);
            this.grille.capturerGrille(Path.repertoire_lvl.toString() + "/" + Profil.getJoueur() + "/"
                + Profil.getMode_de_jeu() + "/" + "capture_niveau_" + Profil.getIdNiveau() + ".png");
            stage.setScene(new ControllerMenuModeJeu(stage).getScene()); //TODO temporaire
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    //TODO
    @FXML
    void aideClique() {
        this.score.aideUtilise();
    }

    //TODO
    @FXML
    void checkClique() {
        this.score.checkUtilise();
    }

    //TODO
    @FXML
    void resetClique() {
        //afficher une fenêtre de confirmation
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Réinitialiser la grille");
        alert.setHeaderText("Voulez-vous vraiment réinitialiser la grille ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.score.restart();
            this.grille.reset();
        }
    }

}
