package com.l3infogrp5.nurikabe.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.sauvegarde.Profil;

/**
 * Contrôleur du menu d'affichage des règles, et sa scène.
 *
 * @author Nicolas
 */
public class ControllerMenuRegles {

    private FXMLLoader loader;
    private Scene root;
    private Pane main;
    Profil joueur;

    @FXML
    private Button retour;
    @FXML
    private BorderPane preview_ile;
    @FXML
    private BorderPane preview_jeu;

    /**
     * Initialise le menu de sélection d'affichage des règles et son contrôleur.
     *
     * @param root la scene racine de l'application.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuRegles(Scene root) throws IOException {
        this.joueur = Profil.getInstance();

        this.root = root;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_regles.fxml"));
        loader.setController(this);

        main = loader.load();
    }

    @FXML
    private void initialize() throws IOException {
        this.preview_ile.setCenter(new Grille(new int[][] {{-2,-1}}, new int[][] {{50,50}}, new Historique()).getPanneau());
        this.preview_jeu.setCenter(new Grille(
            new int[][] {{-2, 2, -1, -1, -1}, {-1, -1, -1, -2, -1}, {-1, 1, -1, -2, -1}, {-1, -1, 4, -2, -1}, {-2, 2, -1, -1, -1}},
            new int[][] {{-2, 2, -1, -1, -1}, {-1, -1, -1, -2, -1}, {-1, 1, -1, -2, -1}, {-1, -1, 4, -2, -1}, {-2, 2, -1, -1, -1}},
        new Historique()).getPanneau());
    }

    /**
     * Retourne l'affichage géré par le contrôleur.
     *
     * @return l'affichage géré par le contrôleur.
     */
    public Pane getPane() {
        return main;
    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique(ActionEvent event) throws Exception {
        root.setRoot(new ControllerMenuPrincipal(root).getPane());
    }

}
