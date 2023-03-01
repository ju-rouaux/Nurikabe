package com.l3infogrp5.nurikabe.menu;

import java.io.IOException;
import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * Génère une un panneau pour la selection de niveau.
 * Chaque modification faite sur la matrice sera répercutée automatiquement sur
 * l'interface, et vice-versa.
 * 
 * @author Cyprien Pennachi
 */


public class Grille_niveau {

    private GridPane pane;
    private Stage stage;



    public Grille_niveau(Stage stage,int indice) throws IOException{
        /* TODO: méthode pour connaitre le nombre de niveaux */
        this.stage=stage;
        this.pane=new GridPane();
        /* méthode pour que ce soit redimensionnable selon la tzaille de la fenetre */
        int nb_colonnes=4;
        int nb_lignes=4;
        for (int i = 0; i < nb_lignes; i++) {
            for (int j = 0; j < nb_colonnes; j++) {
                ControllerCaseNiveau c=new ControllerCaseNiveau(stage,indice);
                //Case_niveau c = new Case_niveau(stage);
                this.pane.add(c.getBorderPane(),i,j);
                // (indice*16)+(i*nb_lignes)+j
                }
            }
        }

    /**
     * Retourne l'affichage de la grille.
     * 
     * @return l'affichage de la grille.
     */
    public GridPane getGridPane(){
        return this.pane;
    }

    private void computePane(){
        this.pane.setPrefSize(this.pane.USE_COMPUTED_SIZE, this.pane.USE_COMPUTED_SIZE);
        this.pane.setMaxSize(this.pane.USE_COMPUTED_SIZE, this.pane.USE_COMPUTED_SIZE);
    }
}