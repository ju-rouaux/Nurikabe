package com.l3infogrp5.nurikabe.menu;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.cell.*;
import javafx.beans.property.*;
import javafx.beans.binding.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Date;

public class ControllerLeaderBoard {
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



public ControllerLeaderBoard(Stage stage,int id_niveau) throws IOException {
    this.stage = stage;
    this.id_niveau=id_niveau;

    loader = new FXMLLoader();
    loader.setLocation(getClass().getResource("/FXML/leaderboard.fxml"));
    loader.setController(this);

    scene = loader.load();
}

public void initialize() { 



    DoubleProperty tableWidth = tableau.prefWidthProperty();

    //Définir la largeur de chaque colonne en un tiers de la largeur de la tableview
    date.prefWidthProperty().bind(tableau.prefWidthProperty().divide(3));
    nom.prefWidthProperty().bind(tableWidth.divide(3));
    score.prefWidthProperty().bind(tableWidth.divide(3));



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

    this.date.setVisible(false);
    this.date.setVisible(true);
}

/*
 * Retourne au menu précédent, le menu principal.
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

    
public static void autoResizeColumns(TableView<?> table) {
    // Set the right policy
    table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    
    // Calculate the width of each column
    double tableWidth = table.getWidth();
    double columnWidth = tableWidth / table.getColumns().size();
    
    table.getColumns().forEach((column) -> {
        // Set the calculated width for each column
        column.setPrefWidth(columnWidth);
    });
}

public class Scoring{

    private String nom;
    private double score;
    private Date date;

    public Scoring(String nom, double score, Date date){
        this.nom=nom;
        this.score=score;
        this.date=date;
    }

    public String getNom(){
        return this.nom;
    }

    public double getScore(){
        return this.score;
    }

    public Date getDate(){
        return this.date;
    }

    public void setNom(String nom){
        this.nom=nom;
    }

    public void setScore(double score){
        this.score=score;
    }

    public void setDate(Date date){
        this.date=date;
    }
}

}


