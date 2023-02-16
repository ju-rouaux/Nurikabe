package com.l3infogrp5.nurikabe.menu;

import java.util.ArrayList;

import javafx.scene.image.*;

import javafx.scene.layout.*;
import javafx.event.ActionEvent;

import javafx.scene.control.*;



public class Case_niveau{
    
/**
 * Génère une un panneau pour la selection de niveau.
 * Chaque modification faite sur la matrice sera répercutée automatiquement sur
 * l'interface, et vice-versa.
 * 
 * @author Cyprien Pennachi
 */

    private BorderPane pane;
    private ImageView image;
    private HBox box;



    public Case_niveau(int indice){
        

        this.pane=new BorderPane();
        /* méthode pour que ce soit redimensionnable selon la tzaille de la fenetre */
        this.image= new ImageView();
        this.box=new HBox();

        if(true){ /* refaire les conditions */
            this.image=new ImageView(new Image("/img/Nurikabe_grille.png")); // TODO: mettre ça -> /img/Nurikabe_grille"+(indice+i*nb_lignes +j)+".png
            this.pane.setCenter(this.image);
        }
        else if( this.image.getImage().getUrl().equals("/img/Nurikabe_grille"+(indice)+".png")){ // TODO: mettre l'emplacement précis 
            this.box.getChildren().add(new Label("Niveau "+indice));
            this.box.getChildren().add(new Button("2X",new ImageView(new Image("/img/trophee.png"))));
            this.pane.setBottom(box);
            this.image=new ImageView(new Image("/img/Nurikabe_grille"+(indice)+".png"));
            this.pane.setCenter(this.image);
            
        }
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
     * Retourne une image selon son indice.
     * 
     * @return l'affichage de la grille.
     */
    private ImageView getImage(){
        return this.image;
    }
}
