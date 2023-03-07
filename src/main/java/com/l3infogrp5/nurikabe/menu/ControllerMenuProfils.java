package com.l3infogrp5.nurikabe.menu;

import com.l3infogrp5.nurikabe.profil.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.Path;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Contrôleur du menu d'affichage des profils, et sa scène.
 *
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerMenuProfils {

    private FXMLLoader loader;
    private Stage stage;
    private Scene scene;

    @FXML
    private Button btn_retour;

    @FXML
    private GridPane pseudo_grid;

    private int profil_actif;

    public String joueur;
    private String[] profils_attributs = new String[7];

    /**
     * Initialise le menu de sélection d'affichage des règles et son contrôleur.
     *
     * @param stage la fenêtre contenant la scène.
     * @throws IOException
     */
    public ControllerMenuProfils(Stage stage) throws IOException {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_profils.fxml"));
        loader.setController(this);
        scene = loader.load();

        profil_actif = 0;

        Arrays.fill(profils_attributs, null);

        new Profil(((Label) ((VBox) pseudo_grid.getChildren().get(0)).getChildren().get(1)).getText());
    }

    /**
     * Retourne la scène gérée par le contrôleur.
     *
     * @return la scène gérée par le contrôleur.
     */
    public Scene getScene() {
        return scene;
    }

    /*
     * Retourne au menu précédent, le menu principal.
     */
    @FXML
    private void retourClique(ActionEvent event) throws IOException {
        stage.setScene(new ControllerMenuPrincipal(stage).getScene());
    }

    /**
     * Modfie l'affichage du menue profile pour afficher le profile du nouveaux
     * joueur
     * 
     * @param i l'indice de la grille a modifier
     * 
     */
    @FXML
    private void afficherNouveauxProfil(int i) {
        // affiche le nom du nouveaux profil
        ((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(1)).setText(joueur);

        // supprime le text du boutton
        ((Button) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0)).setText("");

        // suppression du style d'ajout de profil pour le boutton et ajout du style de
        // profil existant
        ((Button) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0)).getStyleClass()
                .remove("btn-add");
        ((Button) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0)).getStyleClass()
                .add("btn-profile");

        // si on a encore de la place on rend visible l'ajout de profile suivant
        if (i + 1 < pseudo_grid.getChildren().size())
            ((Button) ((VBox) pseudo_grid.getChildren().get(i + 1)).getChildren().get(0)).setVisible(true);
    }

    /**
     * Méthode pour créer un nouveaux profils avec un pseudo
     * 
     * @param i l'indice de la grille a modifier
     * @throws IOException
     *
     */
    @FXML
    private void nouveauxProfil(int i) throws IOException {
        // creation de la popup pour cree un nouveaux profil
        Stage popup = new Stage();

        popup.setScene(new ControllerNouveauxProfil(this).getScene());
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();

        new Profil(joueur);

        // modification de l'affichage
        afficherNouveauxProfil(i);

        ajoutProfils(joueur);
        sauvegarderProfils();
    }

    /**
     * Méthode pour charger un profils
     * 
     * @throws IOException
     *
     */
    @FXML
    private void chargerProfil(ActionEvent event) throws IOException {
        // on parcour la grid des profil pour savoir lequel est appuiyer
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            // recuperation du boutton appuiyer
            if ((event.getSource()) == (((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0))) {
                // recuperation du pseudo
                joueur = (((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren()
                        .get(1)).getText());

                if (Sauvegarder.RechercherSauvegarde(joueur)) {
                    System.out.println("Profil " + joueur + " charger");
                } else {
                    System.out.println("Creation du profils " + joueur);
                    nouveauxProfil(i);
                }

                setActiveProfil(i);
            }
        }
    }

    private void setActiveProfil(int i) {
        ((VBox) pseudo_grid.getChildren().get(profil_actif)).getStyleClass().remove("actif");
        ((VBox) pseudo_grid.getChildren().get(i)).getStyleClass().add("actif");

        profil_actif = i;
    }

    /**
     * Methode pour recuperer les joeur creer
     * 
     * @throws FileNotFoundException
     */
    public void chargerTableau() throws FileNotFoundException {
        File file = new File(Path.repertoire_courant.toString() + "/Profil_interface" + "/liste_profils");
        if (file.exists()) {
            Scanner reader = new Scanner(file);
            for (int i = 0; reader.hasNextLine(); i++) {
                joueur = reader.nextLine();
                profils_attributs[i] = joueur;
                afficherNouveauxProfil(i + 1);
            }

            reader.close();
        }
    }

    /**
     * Methode pour ajouter un joueur au tableau des joueur
     * 
     * @param joueur le nom du joueur a ajouter
     */
    private void ajoutProfils(String joueur) {
        for (int i = 0; i < profils_attributs.length; i++) {
            if (profils_attributs[i] == null) {
                profils_attributs[i] = joueur;
                return;
            }
        }
    }

    /**
     * Methode pour sauvegarder l'affichage des profils creer
     * 
     * @throws IOException
     */
    private void sauvegarderProfils() throws IOException {
        if (!Sauvegarder.dossierExistants(new File(Path.repertoire_courant.toString() + "/Profil_interface"))) {
            Files.createDirectories(Paths.get(Path.repertoire_courant.toString() + "/Profil_interface"));
            System.out.println("Repertoire interface profil creer");
        } else {
            System.out.println("Repertoire interface profil deja existant");
        }

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(Path.repertoire_courant.toString() + "/Profil_interface" + "/liste_profils"));

        // recopie du tableau dans un fichier
        for (String str : profils_attributs) {
            if (str != null) {
                writer.write(str);
                writer.newLine();
            }
        }

        writer.close();
    }

    // TODO: empecher affichage corbeille sur profile non creer
    @FXML
    private void viewDel(MouseEvent event) {
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            if ((event.getSource()) == (((VBox) pseudo_grid.getChildren().get(i)))) {
                if (((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0).isVisible())
                    ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(2).setVisible(true);
            }
        }
    }

    @FXML
    private void hideDel(MouseEvent event) {
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(2).setVisible(false);
        }
    }
}

// TODO : Permetre de supprimer un profile
// TODO : Essayer sans tableaux directement sauvegarde et chargement

// IDEA : different profil meme icon fond de couleur different
// IDEA : potentiellement laisser joueur modifier pseudo et couleur de fond