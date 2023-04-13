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
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Contrôleur du Leaderboard
 *
 * @author Cyprien-Pennachi
 */

public class ControllerLeaderboard {
    private final Scene root;
    private final FXMLLoader loader;
    private final Pane main;
    private final int id_niveau;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button btn_retour;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, Date> date;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, Date> date_joueur_courant;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, String> nom;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, Double> score;

    @FXML
    private TableColumn<Sauvegarder.DonneesScore, Double> score_joueur_courant;

    @FXML
    private TableView<Sauvegarder.DonneesScore> leaderboard_general;

    @FXML
    private TableView<Sauvegarder.DonneesScore> leaderboard_joueur_courant;


    /**
     * Initialise le menu de sélection de mode de jeu et son contrôleur.
     *
     * @param root     la scene racine de l'application.
     * @param id_niveau le numéro du niveau.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerLeaderboard(Scene root, int id_niveau) throws IOException {
        this.root = root;
        this.id_niveau = id_niveau;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/leaderboard.fxml"));
        loader.setController(this);

        main = loader.load();
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
        this.score.setSortType(TableColumn.SortType.DESCENDING);
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
                for (Sauvegarder.DonneesScore score : scores) {
                    if(Profil.getModeDeJeu().equals(ModeDeJeu.CLM)){
                        score.getScore()
                    }
                    System.out.println(score);
                    items.add(score);
                }
            }
            for (String pseudo : pseudos) {
                if (pseudo.equals(Profil.getJoueur())) {
                    List<Sauvegarder.DonneesScore> scores = Sauvegarder.chargerScore(pseudo, Profil.getModeDeJeu(), id_niveau, false);
                    if(Profil.getModeDeJeu().equals(ModeDeJeu.CLM)){
                        score.getScore()
                    }
                    if (!scores.isEmpty()) {
                        items_moi.addAll(scores);
                    }
                    break;
                }
            }
        }

        this.leaderboard_general.setItems(items);
        this.leaderboard_general.getSortOrder().add(score);

        // Définit les propriétés des colonnes pour le tableau de score personnel
        this.date_joueur_courant.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.score_joueur_courant.setCellValueFactory(new PropertyValueFactory<>("score"));
        this.score_joueur_courant.setSortType(TableColumn.SortType.DESCENDING);

        this.leaderboard_joueur_courant.setItems(items_moi);
        this.leaderboard_joueur_courant.getSortOrder().add(score_joueur_courant);

    }


    /*
     * Retourne au menu précédent, le menu de selection.
     */
    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        if (Profil.getModeDeJeu() != ModeDeJeu.SANSFIN)
            root.setRoot(new ControllerMenuSelection(root).getPane());
        else
            root.setRoot(new ControllerMenuModeJeu(root).getPane());
    }

    /**
     * Retourne l'affichage géré par le contrôleur.
     *
     * @return l'affichage géré par le contrôleur.
     */
    public Pane getPane() {
        return main;
    }
}


