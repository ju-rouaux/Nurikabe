package com.l3infogrp5.nurikabe.menu;

import java.io.IOException;

import javafx.stage.Stage;

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
        int nb_lignes=2;
        for (int i = 0; i < nb_colonnes; i++) {
            for (int j = 0; j < nb_lignes; j++) {
                ControllerCaseNiveau c=new ControllerCaseNiveau(stage,(indice*8)+(j*nb_lignes)+i);
                //Case_niveau c = new Case_niveau(stage);
                this.pane.add(c.getBorderPane(),i,j);
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
}
