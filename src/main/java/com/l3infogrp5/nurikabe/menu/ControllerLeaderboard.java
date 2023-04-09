package com.l3infogrp5.nurikabe.menu;

import com.l3infogrp5.nurikabe.sauvegarde.ModeDeJeu;
import com.l3infogrp5.nurikabe.sauvegarde.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
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
     *
     * @throws IOException lancé lorsque le chargement du score n'a pas pû être effectué.
     */
    @FXML
    public void initialize() throws IOException {

        // Définit les propriétés des colonnes pour le tableau de score
        this.nom.setCellValueFactory(new PropertyValueFactory<>("joueur"));
        this.score.setCellValueFactory(new PropertyValueFactory<>("score"));
        this.date.setCellValueFactory(new PropertyValueFactory<>("date"));
        // Récupère la liste des pseudos
        List<String> pseudos = Sauvegarder.joueursScore(Profil.getModeDeJeu(), id_niveau);

        // Ajoute les scores de chaque joueur
        ObservableList<Sauvegarder.DonneesScore> items = FXCollections.observableArrayList();

        // Récupère les données pour le profil sélectionné
        ObservableList<Sauvegarder.DonneesScore> items_moi = FXCollections.observableArrayList();

        if (!pseudos.isEmpty()) {
            for (String pseudo : pseudos) {
                List<Sauvegarder.DonneesScore> scores = Sauvegarder.chargerScore(pseudo, Profil.getModeDeJeu(), id_niveau, false);
                for(Sauvegarder.DonneesScore score : scores){
                    System.out.println(score);
                    items.add(score);
                }
            }
            for (String pseudo : pseudos) {
                if (pseudo.equals(Profil.getJoueur())) {
                    List<Sauvegarder.DonneesScore> scores = Sauvegarder.chargerScore(pseudo, Profil.getModeDeJeu(), id_niveau, false);
                    if (!scores.isEmpty()) {
                        items_moi.addAll(scores);
                    }
                    break;
                }
            }
        }

        this.tableau.setItems(items);
        this.tableau.getSortOrder().addAll(date, nom, score);

        // Définit les propriétés des colonnes pour le tableau de score personnel
        this.date_moi.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.score_moi.setCellValueFactory(new PropertyValueFactory<>("score"));

        this.tableau_moi.setItems(items_moi);
    }


    /*
     * Retourne au menu précédent, le menu de selection.
     */
    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        if (Profil.getModeDeJeu() != ModeDeJeu.SANSFIN)
            stage.setScene(new ControllerMenuSelection(stage).getScene());
        else
            stage.setScene(new ControllerMenuModeJeu(stage).getScene());
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


