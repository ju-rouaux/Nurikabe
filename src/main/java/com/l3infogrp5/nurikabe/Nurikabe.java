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

        testSauvegardeScoreProfil();

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

    private void testSauvegardeScoreProfil(){
        /*
         * TEMP
         */
        HashMap<String, HashMap<String, String>> mapdetente = new HashMap<>();
        HashMap<String, HashMap<String, String>> mapendless = new HashMap<>();

        int id_niveau = 1;
        String nom_joueur = "khkqsdqsds";

        try {
            Sauvegarder.sauvegarderScore("Julieqzeqsddng", "endless", 1,2017);
            Sauvegarder.sauvegarderScore("sdqez", "endless", 1,39);
            Sauvegarder.sauvegarderScore("khkqsdqsds", "endless", 12,"3:30");
            Sauvegarder.sauvegarderScore("sdqfgez", "endless", 13,"test");
            Sauvegarder.sauvegarderScore("khkqsdqsds", "detente", 1,"test");
            Sauvegarder.sauvegarderScore("khaedqdsks", "detente", 1,"test");
            Sauvegarder.sauvegarderScore("test", "detente", 2,"test");
            Sauvegarder.sauvegarderScore("jhkjs", "detente", 2,"test");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            System.out.println("Charger score detente niveau 1");
            mapdetente = Sauvegarder.chargerScore("detente", id_niveau);
            System.out.println("\nCharger score endless");
            mapendless = Sauvegarder.chargerScore("endless", -1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("hashmap detente : " + mapdetente);
        // Exemple recuperer informations mode detente / controle la montre
        // Recuperer infomrtaions d'un id_niveau et d'un nom_joueur
        System.out.println("Informations d'un id_niveau <" + id_niveau + "> et d'un nom_joueur <" + nom_joueur + ">: "
            + mapdetente.get(nom_joueur));
        System.out.println("Score = " + mapdetente.get(nom_joueur).get("score"));
        System.out.println("Date = " + mapdetente.get(nom_joueur).get("date"));

        System.out.println("\nListe joueurs / score-date" + mapendless);
        System.out.println(
            "Infos joueur en mode endless ayant pour pseudo : " + nom_joueur + " = " + mapendless.get(nom_joueur));
        System.out.println("Score = " + mapendless.get(nom_joueur).get("score"));
        System.out.println("Date = " + mapendless.get(nom_joueur).get("date"));

        System.out.println("Nb grilles = " + Sauvegarder.nbGrilles("detente"));
    }
}

// Test affichage score détente
// @Override
// public void start(Stage primaryStage) {
//     final Pane root = new Pane();
//     final Scene scene = new Scene(root, 350, 300);

//     ScoreZen score = new ScoreZen(5);
//     score.aideUtilise();
//     Pane scorePane = score.get_Pane();
//     score.aideUtilise();
//     score.aideUtilise();
//     score.aideUtilise();
//     score.aideUtilise();
    
//     root.getChildren().add(scorePane);
// }

// Test affichage scores CLM
// @Override
// public void start(Stage primaryStage) {

//     final Text text = new Text("00:00");
//     text.setLayoutX(100);
//     text.setLayoutY(100);
//     text.setFill(Color.BLUE);


//     ScoreChrono score = new ScoreCLM(75, text);
//     final Scene scene = new Scene(score.get_Pane(), 350, 300);
//     score.start();

//     System.out.println("test");
//     score.aideUtilise();

//     /* 
//      * ScoreChrono score = new ScoreEndless(40,0, text);
//      * score.calcul();
//      * 
//      * System.out.println("test");
//      * //score.aideUtilise(35);
//      * score.grilleComplete();
//      */

//     primaryStage.setTitle("Test sur l'opacité");
//     primaryStage.setScene(scene);
//     primaryStage.show();
// }
