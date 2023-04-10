package com.l3infogrp5.nurikabe.menu;

import com.l3infogrp5.nurikabe.sauvegarde.ModeDeJeu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.ControllerNiveau;
import com.l3infogrp5.nurikabe.sauvegarde.Profil;

/**
 * Contrôleur du menu de sélection de mode de jeu, et sa scène.
 *
 * @author Julien Rouaux - Nicolas
 */
public class ControllerMenuModeJeu {

    private FXMLLoader loader;
    private Scene root;
    private Pane main;

    @FXML
    private Button btn_detente;

    @FXML
    private Button btn_contre_montre;

    @FXML
    private Button btn_sans_fin;

    @FXML
    private Button btn_score;

    @FXML
    private Button btn_retour;

    /**
     * Initialise le menu de sélection de mode de jeu et son contrôleur.
     *
     * @param root la scene racine de l'application.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuModeJeu(Scene root) throws IOException {
        this.root = root;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_mode.fxml"));
        loader.setController(this);

        main = loader.load();

    }

    /**
     * Retourne l'affichage géré par le contrôleur.
     *
     * @return l'affichage géré par le contrôleur.
     */
    public Pane getPane() {
        return main;
    }

    /**
     * Change la scène par le menu de sélection de niveau du mode détente.
     */
    @FXML
    private void detenteClique() throws Exception {
        Profil.getInstance().setModeDeJeu(ModeDeJeu.DETENTE);
        root.setRoot(new ControllerMenuSelection(root).getPane());
    }

    /**
     * Change la scène par le menu de sélection de niveau du mode contre la montre.
     */
    @FXML
    private void contreMontreClique() throws Exception {
        Profil.getInstance().setModeDeJeu(ModeDeJeu.CONTRELAMONTRE);
        root.setRoot(new ControllerMenuSelection(root).getPane());
    }

    /**
     * Change la scène par une partie du mode sans fin.
     */
    @FXML
    private void sansFinClique() throws Exception {
        Profil.getInstance().setModeDeJeu(ModeDeJeu.SANSFIN);
        root.setRoot(new ControllerNiveau(root, List.of(
            0, 1, 3, 4, 5, 6, 7, 8, 9, 10 //TODO selectionner les niveaux à jouer
        )).getPane());
    }

    /**
     * Change la scène par le menu de consultation des scores du mode sans fin.
     */
    @FXML
    private void scoreClique() throws IOException {
        Profil.getInstance().setModeDeJeu(ModeDeJeu.SANSFIN);
        root.setRoot(new ControllerLeaderboard(root, 0).getPane());
    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique() throws Exception {
        root.setRoot(new ControllerMenuPrincipal(root).getPane());
    }
}
