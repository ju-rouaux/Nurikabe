package com.l3infogrp5.nurikabe.menu;

import java.util.ArrayList;

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



    public Grille_niveau(int indice){
        /* TODO: méthode pour connaitre le nombre de niveaux */

        this.pane=new GridPane();
        /* méthode pour que ce soit redimensionnable selon la tzaille de la fenetre */
        int nb_colonnes=4;
        int nb_lignes=4;

        for (int i = 0; i < nb_lignes; i++) {
            for (int j = 0; j < nb_colonnes; j++) {
                Case_niveau c=new Case_niveau(indice+(i*nb_lignes)+j);
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
