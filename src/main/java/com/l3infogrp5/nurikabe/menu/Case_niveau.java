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
            this.image=new ImageView(new Image("/img/Nurikabe_grille"+(indice)+".png"));
            this.pane.setCenter(this.image);
        }
        this.box.getChildren().add(new Label("Niveau "+indice));
        ImageView img_trophee= new ImageView(new Image("/img/trophee.png"));
        /* rendre les méthodes plus belles  */
        img_trophee.setFitWidth(50);
        img_trophee.setFitHeight(37.5);
        //
        this.box.getChildren().add(new Button("",img_trophee));
        this.pane.setBottom(box);
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

    private void computePane(){
        this.pane.setPrefSize(this.pane..setCenter().USE_COMPUTED_SIZE, this.pane.setCenter().USE_COMPUTED_SIZE);
        this.pane.setMaxSize(this.pane.setCenter().USE_COMPUTED_SIZE, this.pane.setCenter().USE_COMPUTED_SIZE);
    }
}
