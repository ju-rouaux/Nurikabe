package com.l3infogrp5.nurikabe;

import java.io.IOException;
import java.util.HashMap;

import com.l3infogrp5.nurikabe.menu.ControllerMenuPrincipal;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Jeu du Nurikabe.
 * Projet étudiant de L3 Informatique.
 *
 * @author Julien Rouaux
 */
public class Nurikabe extends Application {

    /**
     * Constructeur.
     */
    public Nurikabe() {
        super();

        Sauvegarder.creerArborescence();
        /**
         * TEMP
         */
        HashMap<String, HashMap<String, HashMap<String, String>>> mapdetente = new HashMap<>();
        HashMap<String, HashMap<String, HashMap<String, String>>> mapendless = new HashMap<>();

        String id_niveau = "1";
        String nom_joueur = "khkqsdqsds";

        for (int i = 0; i < 3; i++) {
            try {
                Sauvegarder.sauvegarderScore("Julieqzeqsddng", "endless", 1);
                Sauvegarder.sauvegarderScore("sdqez", "endless", 1);
                Sauvegarder.sauvegarderScore("sdqsqdqez", "endless", 12);
                Sauvegarder.sauvegarderScore("sdqfgez", "endless", 13);
                Sauvegarder.sauvegarderScore("khkqsdqsds", "detente", 1);
                Sauvegarder.sauvegarderScore("khaedqdsks", "detente", 1);
                Sauvegarder.sauvegarderScore("khkqsdqsds", "detente", 2);
                Sauvegarder.sauvegarderScore("khaedqdsks", "detente", 2);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                mapdetente = Sauvegarder.chargerScore("detente");
                mapendless = Sauvegarder.chargerScore("endless");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("hashmap detente : " + mapdetente);
        // Exemple recuperer informations mode detente / controle la montre
        System.out.println("Informations niveau id_niveau : " + mapdetente.get(id_niveau));
        // Recuperer infomrtaions d'un id_niveau et d'un nom_joueur
        System.out.println("Informations d'un id_niveau <" + id_niveau + "> et d'un nom_joueur <" + nom_joueur + ">: "
                + mapdetente.get(id_niveau).get(nom_joueur));
        System.out.println("\nhashmap endless : " + mapendless);

    }

    /**
     * Initialisation de la fenêtre de l'application.
     *
     * @throws Exception {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        ControllerMenuPrincipal menu = new ControllerMenuPrincipal(stage);

        stage.setMinHeight(480);
        stage.setMinWidth(640);
        stage.setScene(menu.getScene());
        stage.show();
    }

    /**
     * Fonction appelée avant la fermeture du programme.
     * Utiliser Platform.exit() au lieu de System.exit() pour s'assurer de
     * l'exécution de cette méthode.
     *
     * @throws Exception {@inheritDoc}
     */
    @Override
    public void stop() throws Exception {
        // TODO : ajouter une fermeture du programme propre qui s'assure de la
        // sauvegarde de la partie.
    }
}
