package com.l3infogrp5.nurikabe.menu;

import com.l3infogrp5.nurikabe.profil.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.Path;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Contrôleur du Leaderboard
 *
 * @author Cyprien-Pennachi
 */

public class ControllerLeaderboard {
    private final Stage stage;
    private final FXMLLoader loader;
    private final Scene scene;
    private final int id_niveau;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button btn_retour;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, Date> date;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, Date> date_moi;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, String> nom;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, Double> score;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, Double> score_moi;

    @FXML
    private TableView<Sauvegarder.DonneesScore> tableau;

    @FXML
    private TableView<Sauvegarder.DonneesScore> tableau_moi;


    /**
     * Initialise le menu de sélection de mode de jeu et son contrôleur.
     *
     * @param stage     la fenêtre contenant la scène.
     * @param id_niveau le numéro du niveau.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerLeaderboard(Stage stage, int id_niveau) throws IOException {
        this.stage = stage;
        this.id_niveau = id_niveau;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/leaderboard.fxml"));
        loader.setController(this);

        scene = loader.load();
    }


    /**
     * Charge l'image et le nom du niveau sur la carte.
     */
    @FXML
    public void initialize() {


        this.date.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.nom.setCellValueFactory(new PropertyValueFactory<>("nom_joueur"));
        this.score.setCellValueFactory(new PropertyValueFactory<>("score"));
        List<String> pseudos = Sauvegarder.listeFichiers(new File(Path.repertoire_lvl.toString()));


        // ajout des valeurs
        ObservableList<Sauvegarder.DonneesScore> items = FXCollections.observableArrayList(
            new Sauvegarder.DonneesScore()
        );
        for (String pseudo : pseudos) {
            try {
                List<Sauvegarder.DonneesScore> scores = Sauvegarder.chargerScore(pseudo, Profil.getModeDeJeu(), id_niveau, false);
                if (scores != null) {
                    for(Sauvegarder.DonneesScore score : scores) {
                        System.out.println(score.getNomJoueur() + " " + score.getScore() + " " + score.getDate());
                        items = FXCollections.observableArrayList(scores);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.tableau.setItems(items);

        // rendre les colonnes triables
        this.tableau.getSortOrder().addAll(date, nom, score);


        this.date_moi.setCellValueFactory(new PropertyValueFactory<>("Date"));
        this.score_moi.setCellValueFactory(new PropertyValueFactory<>("Score"));

        ObservableList<Sauvegarder.DonneesScore> items_moi = null;

        for (String pseudo : pseudos) {
            try {
                if (pseudo.equals(Profil.getJoueur())) {
                    List<Sauvegarder.DonneesScore> scores = Sauvegarder.chargerScore(pseudo, Profil.getModeDeJeu(), id_niveau, true);
                    items_moi = FXCollections.observableArrayList(scores);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        this.tableau_moi.setItems(items_moi);
    }

    /*
     * Retourne au menu précédent, le menu de selection  .
     */
    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuSelection(stage).getScene());
    }

    /**
     * Retourne la scène gérée par le contrôleur.
     *
     * @return la scène gérée par le contrôleur.
     */
    public Scene getScene() {
        return scene;
    }
}


