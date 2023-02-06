package com.l3infogrp5.nurikabe.menus;

import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class Test_MenuPrincipal extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("menu_principal.fxml"));

        Scene scene = new Scene(root);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
