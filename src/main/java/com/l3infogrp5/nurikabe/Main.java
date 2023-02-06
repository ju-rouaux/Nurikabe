package com.l3infogrp5.nurikabe;

import com.l3infogrp5.nurikabe.menu.ControllerMenuPrincipal;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    

    @Override
    public void start(Stage stage) throws Exception {
        ControllerMenuPrincipal menu = new ControllerMenuPrincipal(stage);


        stage.setScene(menu.getScene());
        stage.show();
    }


    /*
     * UTILISEZ Platform.exit() AU LIEU DE System.exit() POUR QUE CETTE METHODE SOIT TOUJOURS APPELÉE
     */
    @Override
    public void stop() throws Exception {
        //TODO : ajouter une fermeture du programme propre qui s'assure de la sauvegarde de la partie.
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
