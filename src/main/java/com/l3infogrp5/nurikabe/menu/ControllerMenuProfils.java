package com.l3infogrp5.nurikabe.menu;

import com.l3infogrp5.nurikabe.profil.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import com.l3infogrp5.nurikabe.utils.Path;

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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Contrôleur du menu d'affichage des profils, et sa scène.
 *
 * @author Julien Rouaux - Nicolas Gouget
 */
public class ControllerMenuProfils {

    private final FXMLLoader loader;
    private final Stage stage;
    private final Scene scene;
    public String nom_joueur;
    private Profil joueur;
    private List<String> profils_attributs;
    @FXML
    private Button btn_retour;
    @FXML
    private GridPane pseudo_grid;
    private int profil_actif;

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
        profils_attributs = Sauvegarder.listeFichiers(new File(Path.repertoire_lvl.toString()));
        System.out.println("joueur : " + profils_attributs);

    }

    /**
     * Retourne la scène gérée par le contrôleur.
     *
     * @return la scène gérée par le contrôleur.
     */
    public Scene getScene() {
        return scene;
    }

    /**
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
     */
    private void afficherNouveauxProfil(int i) {
        VBox vbox = (VBox) pseudo_grid.getChildren().get(i);
        Label label = (Label) vbox.getChildren().get(1);
        Button bouton = (Button) vbox.getChildren().get(0);

        bouton.setText("");

        label.setText(nom_joueur);
        bouton.getStyleClass().remove("btn-add");
        bouton.getStyleClass().add("btn-profile");

        //Si on a encore de la place on rend visible le bouton pour ajouter un profil.
        // OK
        if (i + 1 < pseudo_grid.getChildren().size()) {
            VBox prochain_vbox = (VBox) pseudo_grid.getChildren().get(i + 1);
            Button prochain_bouton = (Button) prochain_vbox.getChildren().get(0);
            prochain_bouton.setVisible(true);
        }
    }

    /**
     * Méthode pour créer un nouveaux profils avec un pseudo
     *
     * @param i l'indice de la grille a modifier
     * @throws IOException
     */
    private void nouveauxProfil(int i) throws IOException {
        System.out.println("test Nouveau profil");
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
     */
    @FXML
    private void chargerProfil(ActionEvent event) throws IOException {
        // on parcour la grid des profil pour savoir lequel est appuiyer
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            VBox vbox = (VBox) pseudo_grid.getChildren().get(i);
            Button bouton = (Button) vbox.getChildren().get(0);
            Label label = (Label) vbox.getChildren().get(1);
            if (event.getSource() == bouton) {
                // acces au pseudo
                nom_joueur = label.getText();

                if (!Sauvegarder.listeFichiers(new File(Path.repertoire_lvl.toString())).contains(nom_joueur)) {
                    nouveauxProfil(i);
                    System.out.println("Nouveau profil");
                }

                joueur = new Profil(nom_joueur);
                setActiveProfil(i);
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
        if (profil_actif != -1) {
            VBox vbox = (VBox) pseudo_grid.getChildren().get(profil_actif);
            vbox.getChildren().get(0).getStyleClass().remove("actif");
        }

        VBox vbox = (VBox) pseudo_grid.getChildren().get(i);
        vbox.getChildren().get(0).getStyleClass().add("actif");
        profil_actif = i;

        BufferedWriter writer = new BufferedWriter(
            new FileWriter(Path.repertoire_profils.toString() + "/profil_actif"));

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

        /// Récupération des profils existants
        System.out.println("Reload tableau profils :" + Sauvegarder.listeFichiers(Path.repertoire_lvl));
        profils_attributs = Sauvegarder.listeFichiers(Path.repertoire_lvl);
        for (int i = 0; i < profils_attributs.size(); i++) {
            nom_joueur = profils_attributs.get(i);
            afficherNouveauxProfil(i);
        }

        // Récupération du profil actif
        File file = new File(Path.repertoire_profils, "actif");
        if (file.exists()) {
            try (Scanner reader = new Scanner(file)) {
                int actif = reader.nextInt();
                String nom_actif = reader.nextLine().trim();
                Profil joueur_actif = new Profil(nom_actif);
                if (!joueur.equals(joueur_actif)) {
                    joueur = joueur_actif;
                }
                setActiveProfil(actif);
            }
        }
    }

    /**
     * Methode pour ajouter un joueur au tableau des joueur
     *
     * @param nom_joueur le nom du joueur a ajouter
     */
    private void ajoutProfils(String nom_joueur) {
        for (int i = 0; i < profils_attributs.size(); i++) {
            if (profils_attributs.get(i).isEmpty()) {
                profils_attributs.set(i, nom_joueur);
                return;
            }
        }
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
            if (!((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(1)).getText().equals("default") && (event.getSource()) == pseudo_grid.getChildren().get(i)) {
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

        profils_attributs.remove(nom_joueur);
        System.out.println(profils_attributs);


        Sauvegarder.supprimerProfil(nom_joueur);

        // rechargement de la scene
        ControllerMenuProfils reload = new ControllerMenuProfils(stage, joueur);
        stage.setScene(reload.getScene());
        reload.chargerTableau();
    }


    /**
     * Getter
     */
    public List<String> getProfilsAttributs() {
        return profils_attributs;
    }
}

// TODO : PANIQUE A BORD REPOSIBILITER DAVOIR DEUX PROFILS MEME NOM (POTENTIEL SOLUTION MAJ LIST)
// TODO : REVOIR JAVADOC
// TODO : FORCER PROFILS DEFAULT EN PREMIERE POSITION

// IDEA : different profil meme icon fond de couleur different
// IDEA : potentiellement laisser joueur modifier pseudo et couleur de fond
// IDEA : permettrre d'appuiyer dur + pour creer nouveau profil
