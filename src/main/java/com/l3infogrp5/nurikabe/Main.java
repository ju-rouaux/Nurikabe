package com.l3infogrp5.nurikabe;

import javafx.application.Application;

/**
 * Point d'entrée de l'application.
 * Projet étudiant de L3 Informatique.
 *
 * @author Julien Rouaux
 */
public class Main {
    private Main() {
    }

    /**
     * Lancement de l'application.
     *
     * @param args arguments de ligne de commande.
     */
    public static void main(String[] args) {
        Application.launch(Nurikabe.class, args);
    }
}