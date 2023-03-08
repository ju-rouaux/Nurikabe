package com.l3infogrp5.nurikabe.menu;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.beans.property.*;
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


    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btn_retour;
    @FXML
    private TableColumn<String, String> date;
    @FXML
    private TableColumn<String, String> nom;
    @FXML
    private TableColumn<String, String> score;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TableView<String> tableau;



    public ControllerLeaderBoard(Stage stage,int indice) throws IOException {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/leaderboard.fxml"));
        loader.setController(this);

        scene = loader.load();
    }

    public void initialize() { 
        // ajout des valeurs
      

        this.nom.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        
        Scoring c= new Scoring(null,null,null){
                private String nom;
                private double score;
                private int date;

                public Scoring(String nom, double score,int date){
                    this.nom=nom;
                    this.score=score;
                    this.date=date;
                }
    
                public String getNom(){
                    return this.nom;
                }
        };

        ObservableList<Scoring> items = FXCollections.observableArrayList(new Scoring("é",22.1,12),new Scoring("ze",13.0,23)); // créer une classe pour chaque type
        this.tableau.setItems(items);
        System.out.println(this.tableau.getItems());

        
        
        // rendre les colonnes triables
    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuNiveau(stage).getScene());
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


