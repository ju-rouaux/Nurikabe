package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Contrôleur du menu de sélection de niveau, et sa scène.
 * 
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerMenuNiveau {

    private FXMLLoader loader;
    public Stage stage;
    private Scene scene;

    private ArrayList<Grille_niveau> grille;

    private static int index=0;


    @FXML
    private Button btn_retour;
    @FXML
    private Button btn_page_suiv;
    @FXML
    private Button btn_page_prec;
    @FXML
    private Label num_page;
    @FXML
    private BorderPane panneau;
    @FXML
    private HBox box;
    /**
     * Initialise le menu de sélection de niveau et son contrôleur.
     * 
     * @param stage la fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuNiveau(Stage stage) throws IOException {
        this.stage = stage;
        this.grille=new ArrayList<Grille_niveau>();
        this.grille.add(new Grille_niveau(stage,index));
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_niveau.fxml"));
        loader.setController(this);

        scene = loader.load();
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
     * Initialise les éléments de l'interface après le préchargement du FXMLLoader.
     */
    @FXML
    public void initialize() {
        panneau.setCenter(this.grille.get(0).getGridPane());   //TODO charger la grille ici
                                                        //TODO charger les données de score
    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        stage.setScene(new ControllerMenuModeJeu(stage).getScene());
    }

     /*
     * Retourne à la page précédente.
     */
    @FXML
    private void page_precedente(ActionEvent event) throws Exception {
        if(this.index!=0){
            index-=1;
            num_page.setText(""+index);
            panneau.setCenter(this.grille.get(index).getGridPane());
            
        }
    }

    /*
     * Retourne à la page suivante.
     */
    @FXML
    private void page_suivante(ActionEvent event) throws Exception {
        if(this.grille.size()>this.index){
            index+=1;
            num_page.setText(""+index);
            this.grille.add(new Grille_niveau(index));
            panneau.setCenter(this.grille.get(index).getGridPane()); 

        }
    }
}
