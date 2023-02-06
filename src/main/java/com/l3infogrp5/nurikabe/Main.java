package com.l3infogrp5.nurikabe;

import java.util.concurrent.ExecutionException;

import com.l3infogrp5.nurikabe.menus.ControllerMenuPrincipal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ControllerMenuPrincipal menu = new ControllerMenuPrincipal();
        
        stage.setScene(menu.getScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
