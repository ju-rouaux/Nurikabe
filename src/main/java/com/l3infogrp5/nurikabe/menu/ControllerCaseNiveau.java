package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import java.lang.annotation.Target;
import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.ArrayList;

import com.l3infogrp5.nurikabe.niveau.*;


/**
 * Contrôleur du menu de sélection de niveau, et sa scène.
 * 
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerCaseNiveau {

    private FXMLLoader loader;
    public Stage stage;
    private int indice;

    @FXML
    private BorderPane pane;
    @FXML
    private ImageView image;
    @FXML
    private HBox box;
    @FXML 
    private Label label;
    @FXML
    private Button bouton;
    @FXML
    private ImageView img_trophee;

    /**
     * Initialise le menu de sélection de niveau et son contrôleur.
     * 
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerCaseNiveau(Stage stage,int indice) throws IOException {
        this.stage = stage;
        this.indice=indice;
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/CaseNiveau.fxml"));
        loader.setController(this);
        this.pane = loader.load();
    }


    /**
     * Initialise les éléments de l'interface après le préchargement du FXMLLoader.
     */
    @FXML
    public void initialize() { 
        
        if(true){ /* refaire les conditions */
            this.image=new ImageView(new Image("/img/Nurikabe_grille.png")); // TODO: mettre ça -> /img/Nurikabe_grille"+(indice+i*nb_lignes +j)+".png
            this.pane.setCenter(this.image);
        }
        else if( this.image.getImage().getUrl().equals("/img/Nurikabe_grille"+(indice)+".png")){ // TODO: mettre l'emplacement précis 
            this.image=new ImageView(new Image("/img/Nurikabe_grille"+(indice)+".png"));
            this.pane.setCenter(this.image);
        }
        this.label.setText("Niveau "+indice);
        this.img_trophee.setFitWidth(50);
        this.img_trophee.setFitHeight(37.5);
    }


        /**
     * Retourne l'affichage de la grille.
     * 
     * @return l'affichage de la grille.
     */
    public BorderPane getBorderPane(){
        return this.pane;
    }

    /**
     * Retourne une image.
     * 
     * @return l'affichage de la grille.
     */
    private ImageView getImage(){
        return this.image;
    }

    /**
     * Rend modulable la case.
     *
     */
    private void computePane(){
       /* 
        this.pane.setPrefSize(this.pane.setCenter().USE_COMPUTED_SIZE, this.pane.setCenter().USE_COMPUTED_SIZE);
        this.pane.setMaxSize(this.pane.setCenter().USE_COMPUTED_SIZE, this.pane.setCenter().USE_COMPUTED_SIZE);
        */
    }

    /**
     * Lance le niveau sur lequel on a cliqué.
     *
     */
    @FXML
    private void CliqueNiveau() throws Exception {  
       stage.setScene(new ControllerNiveau(this.stage,(new Niveau(this.indice))).getScene());
    }

    @FXML
    private void CliqueLeaderBoard() throws Exception {
        stage.setScene(new ControllerLeaderBoard(this.stage,this.indice).getScene());

    }
}
