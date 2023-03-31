package com.l3infogrp5.nurikabe.menu;

import javafx.scene.control.cell.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Date;

/**
 * Contrôleur du Leaderboard
 *
 * @author Cyprien-Pennachi
 */

public class ControllerLeaderboard {
private Stage stage;
private FXMLLoader loader;
private Scene scene;
private int id_niveau;

@FXML
private BorderPane borderPane;

@FXML
private Button btn_retour;

@FXML
private TableColumn<Scoring, Date> date;

@FXML
private TableColumn<Scoring, Date> date_moi;

@FXML
private TableColumn<Scoring, String> nom;

@FXML
private TableColumn<Scoring, Double> score;

@FXML
private TableColumn<Scoring, Double> score_moi;

@FXML
private TableView<Scoring> tableau;

@FXML
private TableView<Scoring> tableau_moi;


   /**
     * Initialise le menu de sélection de mode de jeu et son contrôleur.
     *
     * @param stage la fenêtre contenant la scène.
     * @param id_niveau le numéro du niveau.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
public ControllerLeaderboard(Stage stage,int id_niveau) throws IOException {
    this.stage = stage;
    this.id_niveau=id_niveau;

    loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/FXML/leaderboard.fxml"));
    loader.setController(this);

    scene = loader.load();
}


    /**
     * Charge l'image et le nom du niveau sur la carte.
    */
    public void initialize() { 


        this.date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        this.nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        this.score.setCellValueFactory(new PropertyValueFactory<>("score"));

        // ajout des valeurs
        ObservableList<Scoring> items = FXCollections.observableArrayList(
            new Scoring("é",22.1, Date.valueOf("2022-02-01")),
            new Scoring("ze",13.0, Date.valueOf("2022-03-01"))
        );

        this.tableau.setItems(items);

        // rendre les colonnes triables
        this.tableau.getSortOrder().addAll(date, nom, score);
        



        this.date_moi.setCellValueFactory(new PropertyValueFactory<>("Date"));
        this.score_moi.setCellValueFactory(new PropertyValueFactory<>("score"));

        ObservableList<Scoring> items_moi = FXCollections.observableArrayList(
            new Scoring("é",22.1, Date.valueOf("2022-02-01")),
            new Scoring("ze",13.0, Date.valueOf("2022-03-01"))
        );

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
    /**
     * Classe interne au leaderboard 
     * 
     * @author Cyprien PENNACHI
     */

    public class Scoring{

        private String nom;
        private double score;
        private Date date;
        /**
         * Initialise le menu de sélection de mode de jeu et son contrôleur.
         *
         * @param nom le nom de la personne.
         * @param score le score de la personne.
         * @param date la date du score fait. 
         */
        public Scoring(String nom, double score, Date date){
            this.nom=nom;
            this.score=score;
            this.date=date;
        }
        /**
         * Retourne le nom du profil.
         * 
         * @return le nom du profil.
         */
        public String getNom(){
            return this.nom;
        }
                /**
         * Retourne le nom du profil.
         * 
         * @return le score du profil.
         */
        public double getScore(){
            return this.score;
        }
        /**
         * Retourne la date du score rélisé.
         * 
         * @return le date.
         */
        public Date getDate(){
            return this.date;
        }
        /**
         * Change le nom du profil.
         * @param nom le nom du profil.
         */
        public void setNom(String nom){
            this.nom=nom;
        }
        /**
         * change le score d'un profil.
         * @param score le score du profil.
         */
        public void setScore(double score){
            this.score=score;
        }
        /**
         * Change la date du score réalisé.
         * @param date la date du score réalisé.
         */
        public void setDate(Date date){
            this.date=date;
        }
    }
}


