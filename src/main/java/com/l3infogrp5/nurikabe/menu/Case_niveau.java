package com.l3infogrp5.nurikabe.menu;


import java.beans.EventHandler;
import java.lang.annotation.Target;
import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.control.*;



public class Case_niveau{
/**
 * Génère une un panneau pour la selection de niveau.
 * Chaque modification faite sur la matrice sera répercutée automatiquement sur
 * l'interface, et vice-versa.
 * 
 * @author Cyprien Pennachi
 */

    private int indice; // le numero auquel correspond le nivezu
    private Stage stage;
    private BorderPane pane;
    private ImageView image;
    private HBox box;



    public Case_niveau(Stage stage,int indice){
        

        this.pane=new BorderPane();
        /* méthode pour que ce soit redimensionnable selon la tzaille de la fenetre */
        this.image= new ImageView();
        this.image.setPickOnBounds(true);
        
        this.image.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.equals(MouseEvent.MOUSE_CLICKED)){
                    this.CliqueNiveau();
                }
            }
           });
           
        this.box=new HBox();
        this.indice=indice;

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
    private void CliqueNiveau() {  
       stage.setScene(new ControllerNiveau(this.stage,(new Niveau(this.indice))).getScene());
    }
}
