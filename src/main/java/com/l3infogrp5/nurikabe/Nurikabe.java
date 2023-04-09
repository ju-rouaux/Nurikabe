package com.l3infogrp5.nurikabe;

import com.l3infogrp5.nurikabe.menu.ControllerMenuPrincipal;
import com.l3infogrp5.nurikabe.sauvegarde.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.Sauvegarder;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Jeu du Nurikabe.
 * Projet étudiant de L3 Informatique.
 *
 * @author Julien Rouaux
 */
public class Nurikabe extends Application {

    /**
     * Constructeur.
     *
     * @throws FileNotFoundException Si le fichier de sauvegarde n'a pas été trouvé.
     */
    public Nurikabe() throws IOException {
        super();

        Sauvegarder.creerArborescence();
    }

    /**
     * Initialisation de la fenêtre de l'application.
     *
     * @throws Exception {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws Exception {
        Profil.getInstance().chargerProfil(Profil.getInstance().getJoueur());
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
