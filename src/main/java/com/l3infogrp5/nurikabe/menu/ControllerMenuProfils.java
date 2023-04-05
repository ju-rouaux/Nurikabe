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
import javafx.stage.WindowEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.Scanner;

/**
 * Contrôleur du menu d'affichage des profils, et sa scène.
 *
 * @author Nicolas
 */
public class ControllerMenuProfils {

    private final FXMLLoader loader;
    private final Stage stage;
    private final Scene scene;

    /**
     * Le nom du joueur actif
     */
    public static String nom_joueur;

    private int profil_actif;
    private List<String> profils_attributs;

    @FXML
    private Button btn_retour;
    @FXML
    private GridPane pseudo_grid;

    /**
     * Initialise le menu de sélection d'affichages des profils et son contrôleur.
     *
     * @param stage La fenêtre contenant la scène.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerMenuProfils(Stage stage) throws IOException {
        this.stage = stage;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/menu_profils.fxml"));
        loader.setController(this);
        scene = loader.load();

        profil_actif = 0;
        profils_attributs = Sauvegarder.listeFichiers(new File(Path.repertoire_lvl.toString()));
    }

    /**
     * Retourne la scène gérée par le contrôleur.
     *
     * @return La scène gérée par le contrôleur.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Retourne au menu précédent, le menu principal.
     * 
     * @throws IOException {@inheritDoc}
     */
    @FXML
    private void retourClique(ActionEvent event) throws IOException {
        stage.setScene(new ControllerMenuPrincipal(stage).getScene());
    }

    /**
     * Modifie l'affichage du menu profil pour afficher le profil du nouveau joueur.
     *
     * @param i L'indice de la grille a modifié.
     */
    private void afficherNouveauxProfil(int i) {
        // Récupération de la boite du label et du bouton dont l'affichage doit être
        // modifié
        VBox vbox = (VBox) pseudo_grid.getChildren().get(i);
        Label label = (Label) vbox.getChildren().get(1);
        Button bouton = (Button) vbox.getChildren().get(0);

        bouton.setText("");

        label.setText(nom_joueur);

        // Changement du style du bouton
        bouton.getStyleClass().remove("btn-add");
        bouton.getStyleClass().add("btn-profile");

        // Si on a encore de la place, on rend visible le bouton pour ajouter un profil.
        if (i + 1 < pseudo_grid.getChildren().size()) {
            VBox prochain_vbox = (VBox) pseudo_grid.getChildren().get(i + 1);
            Button prochain_bouton = (Button) prochain_vbox.getChildren().get(0);
            prochain_bouton.setVisible(true);
        }
    }

    /**
     * Méthode pour créer un nouveau profil avec un pseudo
     *
     * @param i L'indice de la grille a modifié.
     * @throws IOException {@inheritDoc}
     */
    private void nouveauxProfil(int i) throws IOException {
        // Création du pop-up pour créer un nouveau profil
        Stage popup = new Stage();

        popup.setScene(new ControllerNouveauxProfil(this).getScene());
        popup.setResizable(false);

        // empêche l'utilisation de la croix pour fermer la fenêtre
        popup.setOnCloseRequest((WindowEvent event) -> {
            event.consume();
        });
        
        // On ne peux pas agir sur la fenêtre actuelle tant que la pop-up n'est pas
        // fermé
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.showAndWait();

        // Modification de l'affichage
        afficherNouveauxProfil(i);

        Profil.getInstance().chargerProfil(nom_joueur);

        // On ajoute le profil créé aux profils existant et on met à jour le tableau des
        // noms.
        ajoutProfils(nom_joueur);
        chargerTableau();
    }

    /**
     * Méthode pour charger un profil
     *
     * @throws IOException {@inheritDoc}
     */
    @FXML
    private void chargerProfil(ActionEvent event) throws IOException {
        // On parcourt la grille des profils pour savoir lequel est appuyer.
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            VBox vbox = (VBox) pseudo_grid.getChildren().get(i);
            Button bouton = (Button) vbox.getChildren().get(0);
            Label label = (Label) vbox.getChildren().get(1);
            if (event.getSource() == bouton) {
                // Accès au pseudo
                nom_joueur = label.getText();

                if (!Sauvegarder.listeFichiers(new File(Path.repertoire_lvl.toString())).contains(nom_joueur))
                    nouveauxProfil(i);
                else
                    Profil.getInstance().chargerProfil(nom_joueur);

                setActiveProfil(i);
                writeActif(nom_joueur);
                return;
            }
        }
    }

    /**
     * Méthode qui modifie le style pour le profil actif et sauvegarde l'indice et
     * le nom de ce dernier pour le recharger plus tard
     *
     * @param i L'indice du profil actif
     * @throws IOException {@inheritDoc}
     */
    private void setActiveProfil(int i) throws IOException {
        if (profil_actif != -1) {
            VBox vbox = (VBox) pseudo_grid.getChildren().get(profil_actif);
            vbox.getChildren().get(0).getStyleClass().remove("actif");
        }

        VBox vbox = (VBox) pseudo_grid.getChildren().get(i);
        vbox.getChildren().get(0).getStyleClass().add("actif");
        profil_actif = i;
    }

    private void writeActif(String nom_actif) throws IOException {
        // Sauvegarde du profil en cour d'utilisation
        BufferedWriter writer = new BufferedWriter(
                new FileWriter(Path.repertoire_profils.toString() + "/profil_actif"));

        writer.write(String.valueOf(profil_actif));
        writer.newLine();
        writer.write(nom_actif);
        writer.close();
    }

    /**
     * Méthode pour récupérer les joueurs créés
     *
     * @throws IOException lancé lorsque le fichier correspondant n'a pas pû être
     *                     lu.
     */
    public void chargerTableau() throws IOException {
        // Récupération des profils existants
        profils_attributs = Sauvegarder.listeFichiers(Path.repertoire_lvl);
        for (int i = 0; i < profils_attributs.size(); i++) {
            nom_joueur = profils_attributs.get(i);
            afficherNouveauxProfil(i);
        }

        // Récupération du profil actif
        getActif();
    }

    /**
     * Méthode pour récupérer le joueur actif
     * 
     * @throws IOException lancé lorsque le fichier correspondant n'a pas pû être
     *                     lu.
     */
    public void getActif() throws IOException {
        File file = new File(Path.repertoire_profils, "profil_actif");
        if (file.exists()) {
            Scanner reader = new Scanner(file);

            int actif = reader.nextInt();
            setActiveProfil(actif);

            String nom_actif = reader.next();

            // si la ligne récupérer n'est pas égal au profil en cour on change
            if (!nom_actif.isEmpty() && !nom_actif.equals(Profil.getJoueur())) {
                writeActif(nom_actif);
                Profil.getInstance().chargerProfil(nom_actif);
            }

            reader.close();
        }
    }

    /**
     * Méthode pour ajouter un joueur au tableau des joueurs
     *
     * @param nom_joueur Le nom du joueur a ajouté.
     */
    private void ajoutProfils(String nom_joueur) {
        profils_attributs.add(nom_joueur);
    }

    /**
     * Méthode qui au passage de la souris sur un profil existant affiche l'icône
     * d'une corbeille permettant sa suppression
     *
     * @param event Les actions de la souris
     */
    @FXML
    private void viewDel(MouseEvent event) {
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++) {
            if (!((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(1)).getText().equals("default")
                    && (event.getSource()) == pseudo_grid.getChildren().get(i)) {
                if (((Button) ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(0)).getText() == "")
                    ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(2).setVisible(true);
            }
        }
    }

    /**
     * Méthode qui s'assure que l'icône de corbeille n'est pas affichée quand la
     * souri n'est pas sur le profil
     *
     * @param event Les actions de la souris
     */
    @FXML
    private void hideDel(MouseEvent event) {
        for (int i = 0; i < pseudo_grid.getChildren().size(); i++)
            ((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(2).setVisible(false);
    }

    /**
     * Méthode qui supprime un profil de la liste des profils et qui réorganise
     * l'affichage
     *
     * @param event Le profil cliqué et à supprimer
     * @throws IOException {@inheritDoc}
     */
    @FXML
    private void suprProfil(ActionEvent event) throws IOException {
        int i ;

        // On parcourt la grille des profils pour savoir lequel est appuyer.
        for (i = 0; i < pseudo_grid.getChildren().size(); i++) {
            // Récupération du bouton appuyé
            if ((event.getSource()) == (((VBox) pseudo_grid.getChildren().get(i)).getChildren().get(2))) {
                // Récupération du pseudo
                nom_joueur = (((Label) ((VBox) pseudo_grid.getChildren().get(i)).getChildren()
                        .get(1)).getText());
            }
        }

        profils_attributs.remove(nom_joueur);

        Sauvegarder.supprimerProfil(nom_joueur);   
        
        if(i == profil_actif){
            setActiveProfil(0);
            writeActif("default");
        }
        else {
            getActif();
            profil_actif = profil_actif - 1;
            setActiveProfil(profil_actif);
            writeActif(Profil.getJoueur());
        }

        // Rechargement de la scène
        ControllerMenuProfils reload = new ControllerMenuProfils(stage);
        stage.setScene(reload.getScene());
        reload.chargerTableau();
        Profil.getInstance().chargerProfil(nom_joueur);
    }

    /**
     * Getter
     * 
     * @return La liste des nom de tout les profils
     */
    public List<String> getProfilsAttributs() {
        return profils_attributs;
    }
}

// IDEA : different profil meme icon fond de couleur different
// IDEA : potentiellement laisser joueur modifier pseudo et couleur de fond
// IDEA : permettre d’appuyer dur + pour créer nouveau profil
