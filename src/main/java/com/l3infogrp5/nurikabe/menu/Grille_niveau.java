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
    private ArrayList<AnchorPane> panneau;
    private ArrayList<ImageView> image;



    public Grille_niveau(){
        /* TODO: méthode pour connaitre le nombre de niveaux */

        this.pane=new GridPane();
        this.image= new ArrayList<ImageView>();
        this.panneau= new ArrayList<AnchorPane>();
        int nb_colonnes=4;
        int nb_lignes=4;
        // TODO:
        /* voir comment avoir le nombre de lignes ainsi que de colonnes */

        for (int i = 0; i < nb_lignes; i++) {
            for (int j = 0; j < nb_colonnes; j++) {
                if(true){
                    this.image.add(new ImageView(new Image("./Nurikabe_grille.png"))); // TODO: mettre l'emplacement précis 
                    this.pane.add(this.getImage(i*nb_lignes+j),i,j);
                    System.out.println("image ajouté avec succès");
                }
                else if( this.getImage(i*nb_lignes +j).getImage().getUrl().equals("./Nurikabe_grille.png"/*emplacement de l'image d'un niveau commencé */)){ // TODO: mettre l'emplacement précis 
                    this.image.add(new ImageView(new Image("./Nurikabe_grille.png"))/* emplacement de l'image */);
                    this.pane.add(this.getImage(i*nb_lignes+j),i,j);
                }
                
                //this.pane.add(this.getPanneau(i*nb_lignes+j), i, j);
                System.out.println("panneau ajouté avec succès");
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

    /**
     * Retourne une image selon son indice.
     * 
     * @return l'affichage de la grille.
     */
    private ImageView getImage(int i){
        return this.image.get(i);
    }

    private AnchorPane getPanneau(int i){
        return this.panneau.get(i);
    }
}
