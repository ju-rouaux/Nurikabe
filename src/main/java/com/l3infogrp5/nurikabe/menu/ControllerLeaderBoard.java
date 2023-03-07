package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ControllerLeaderBoard {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button btn_retour;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> score;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TableView<?> tableau;



    public ControllerLeaderBoard(Stage stage,int indice) throws IOException {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/leaderboard.fxml"));
        loader.setController(this);

        scene = loader.load();
    }

    public void initialize() { 
        
        // ajout des valeurs
        this.nom.getColumns().add; 
        this.score.setText("12");
        this.date.setText("12.12.2023");

        // rendre les colonnes triables
    }


    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuNiveau(stage).getScene());
    }
}


