package com.l3infogrp5.nurikabe.menu;

import com.l3infogrp5.nurikabe.profil.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.Path;

import java.io.BufferedWriter;
import java.io.File;
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
    Profil joueur;

    @FXML
    private Button btn_retour;

    @FXML
    private GridPane pseudo_grid;

    private int profil_actif;

    public String nom_joueur;
    public String[] profils_attributs = new String[7];

    /**
     * Initialise le menu de sélection d'affichage des règles et son contrôleur.
     *
     * @param stage la fenêtre contenant la scène.
     * @throws IOException
     */
    public ControllerMenuProfils(Stage stage, Profil joueur) throws IOException {
        this.joueur = joueur;

        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_profils.fxml"));
        loader.setController(this);
        scene = loader.load();

        profil_actif = 0;

        Arrays.fill(profils_attributs, null);

        // new Profil(((Label) ((VBox)
        // pseudo_grid.getChildren().get(0)).getChildren().get(1)).getText());
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
        stage.setScene(new ControllerMenuPrincipal(stage, joueur).getScene());
    }

    /**
     * Modfie l'affichage du menue profile pour afficher le profile du nouveaux
     * joueur
     * 
     * @param i l'indice de la grille a modifier
     * 
     */
    private void afficherNouveauxProfil(int i) {
        // affiche le nom du nouveaux profil
        ((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(1)).setText(nom_joueur);

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
    private void nouveauxProfil(int i) throws IOException {
        // creation de la popup pour cree un nouveaux profil
        Stage popup = new Stage();

        popup.setScene(new ControllerNouveauxProfil(this).getScene());
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();

        new Profil(nom_joueur);

        // modification de l'affichage
        afficherNouveauxProfil(i);

        ajoutProfils(nom_joueur);
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
                nom_joueur = (((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren()
                        .get(1)).getText());

                if(Sauvegarder.listeFichiers(new File(Path.repertoire_lvl.toString())).contains(nom_joueur))
                    nouveauxProfil(i);
                }

                joueur = new Profil(nom_joueur);
                setActiveProfil(i);
                sauvegarderProfils();
            }
        }
    }

    /**
     * Methode qui modifi le style pour le profil actif et sauvegarde l'indice et le
     * nom de ce dernier pour le recharger plus tard
     * 
     * @param i l'indice du profil actif
     * @throws IOException
     */
    private void setActiveProfil(int i) throws IOException {
        ((Button) ((VBox) pseudo_grid.getChildren().get(profil_actif)).getChildren().get(0)).getStyleClass()
                .remove("actif");
        ((Button) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0)).getStyleClass().add("actif");

        profil_actif = i;

        creerDossierProfils();

        BufferedWriter writer = new BufferedWriter(
                new FileWriter(Path.repertoire_courant.toString() + "/Profil_interface" + "/profil_actif"));

        writer.write(String.valueOf(profil_actif));
        writer.newLine();
        writer.write(nom_joueur);
        writer.close();

    }

    /**
     * Methode pour recuperer les joeur creer
     * 
     * @throws IOException
     * @throws NumberFormatException
     */
    public void chargerTableau() throws NumberFormatException, IOException {
        File file = new File(Path.repertoire_courant.toString() + "/Profil_interface" + "/liste_profils");
        if (file.exists()) {
            Scanner reader = new Scanner(file);
            for (int i = 0; reader.hasNextLine(); i++) {
                nom_joueur = reader.nextLine();
                profils_attributs[i] = nom_joueur;
                afficherNouveauxProfil(i + 1);
            }

            reader.close();
        }

        file = new File(Path.repertoire_courant.toString() + "/Profil_interface" + "/profil_actif");
        if (file.exists()) {
            Scanner reader = new Scanner(file);
            int actif = Integer.parseInt(reader.nextLine());
            String nom_actif = reader.nextLine();
            Profil joueur_actif = new Profil(nom_actif);

            if (joueur != joueur_actif) {
                joueur = joueur_actif;
            }

            setActiveProfil(actif);

            reader.close();
        }
    }

    /**
     * Methode pour ajouter un joueur au tableau des joueur
     * 
     * @param nom_joueur le nom du joueur a ajouter
     */
    private void ajoutProfils(String nom_joueur) {
        for (int i = 0; i < profils_attributs.length; i++) {
            if (profils_attributs[i] == null) {
                profils_attributs[i] = nom_joueur;
                return;
            }
        }
    }

    /**
     * Methode pour creer si il n'existe pas déjà le dossier qui stocke les
     * information relative au coter graphique du profil
     * 
     * @throws IOException
     */
    private void creerDossierProfils() throws IOException {
        if (!Sauvegarder.dossierExistants(new File(Path.repertoire_courant.toString() + "/Profil_interface"))) {
            Files.createDirectories(Paths.get(Path.repertoire_courant.toString() + "/Profil_interface"));
            System.out.println("Repertoire interface profil creer");
        } else {
            System.out.println("Repertoire interface profil deja existant");
        }
    }

    /**
     * Methode pour sauvegarder l'affichage des profils creer
     * 
     * @throws IOException
     */
    private void sauvegarderProfils() throws IOException {
        creerDossierProfils();

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

    /**
     * Methode qui au passage de la souris sur un profil existant affiche l'icone
     * d'une corbeille permetant sa suppression
     * 
     * @param event les action de la souris
     */
    @FXML
    private void viewDel(MouseEvent event) {
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            if ((event.getSource()) == (((VBox) pseudo_grid.getChildren().get(i)))) {
                if (((Button) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0)).getText() == "")
                    ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(2).setVisible(true);
            }
        }
    }

    /**
     * Methode qui s'assure que l'icone de corbeille n'est pas afficher si la souri
     * n'est pas sur le profils
     * 
     * @param event les action de la souris
     */
    @FXML
    private void hideDel(MouseEvent event) {
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(2).setVisible(false);
        }
    }

    /**
     * Methode qui supprime un profil de la liste des profil et qui reorganise
     * l'affichage
     * 
     * @param event le profil cliquer et a supprimer
     * @throws IOException
     */
    @FXML
    private void suprProfil(ActionEvent event) throws IOException {
        // on parcour la grid des profil pour savoir lequel est appuiyer
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            // recuperation du boutton appuiyer
            if ((event.getSource()) == (((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(2))) {
                // recuperation du pseudo
                nom_joueur = (((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren()
                        .get(1)).getText());
            }
        }

        // suppression du nom dans le tableau et rearangement
        for (int i = 0; i < profils_attributs.length; i++) {
            if (profils_attributs[i] == nom_joueur) {
                for (int j = i; j < profils_attributs.length - 1; j++) {
                    profils_attributs[j] = profils_attributs[j + 1];
                }
            }
        }

        sauvegarderProfils();

        // TODO : ajout method suppresion sauvegarde

        // rechargement de la scene
        ControllerMenuProfils reload = new ControllerMenuProfils(stage, joueur);
        stage.setScene(reload.getScene());
        reload.chargerTableau();
    }
}

// IDEA : different profil meme icon fond de couleur different
// IDEA : potentiellement laisser joueur modifier pseudo et couleur de fond
// IDEA : permettrre d'appuiyer dur + pour creer nouveau profil