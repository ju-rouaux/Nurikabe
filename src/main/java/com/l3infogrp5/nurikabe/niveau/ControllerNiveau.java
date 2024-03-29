package com.l3infogrp5.nurikabe.niveau;

import com.l3infogrp5.nurikabe.aide.Aide;
import com.l3infogrp5.nurikabe.aide.Resultat;
import com.l3infogrp5.nurikabe.menu.ControllerMenuModeJeu;
import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.score.ScoreEndless;
import com.l3infogrp5.nurikabe.niveau.score.ScoreInterface;
import com.l3infogrp5.nurikabe.sauvegarde.Profil;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Path;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Contrôleur d'affichage d'un niveau
 *
 * @author Julien Rouaux
 */
public class ControllerNiveau {

    private final FXMLLoader loader;
    private final Scene root;
    private final Pane main;

    private final BooleanProperty aide_affichee; // Vrai si l'aide est affichée sur l'écran.
    private final List<Integer> file_niveaux; // Liste des niveaux à jouer successivement
    private Grille grille;
    private ScoreInterface score;
    private boolean niveau_en_cours; // Vrai si le niveau est en cours de jeu.
    private List<Position> pos_aide; // Liste des positions de l'aide, peut être null.

    private boolean retour;
    private int index_file; // Indice du niveau lancé dans la liste

    private Profil.DonneesNiveau donnees_niveau; // Données du niveau en cours de jeu.

    @FXML
    private Button btn_aide;
    @FXML
    private Button btn_check;
    @FXML
    private Button btn_redo;
    @FXML
    private Button btn_reset;
    @FXML
    private Button btn_retour;
    @FXML
    private Button btn_undo;
    @FXML
    private ToggleButton toggle_aide;
    @FXML
    private BorderPane panneau_principal;
    @FXML
    private BorderPane panneau_score;
    @FXML
    private StackPane panneau_central;
    @FXML
    private BorderPane panneau_aide;
    @FXML
    private HBox barre;
    @FXML
    private Button btn_pos_aide;

    /**
     * Initialise la vue du niveau.
     *
     * @param root   la scene racine de l'application.
     * @param niveaux la liste de niveaux à jouer successivement.
     * @throws IOException lancé lorsque le fichier FXML correspondant n'a pas pû
     *                     être lu.
     */
    public ControllerNiveau(Scene root, List<Integer> niveaux) throws IOException {
        this.root = root;
        this.aide_affichee = new SimpleBooleanProperty();

        this.niveau_en_cours = true;
        this.retour = false;

        this.file_niveaux = niveaux;
        this.index_file = 0;

        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/niveau.fxml"));
        loader.setController(this);

        main = loader.load();
    }

    /**
     * Initialise les éléments de l'interface après le préchargement du FXMLLoader.
     */
    @FXML
    private void initialize() throws Exception {

        // Adapter la largeur de la barre à l'écran
        this.barre.prefWidthProperty().bind(this.panneau_principal.widthProperty().subtract(40));

        // Ne pas faire de rendu en dehors du panneau central (pour ne pas masquer la
        // barre d'outils)
        Rectangle zone_clip = new Rectangle();
        zone_clip.widthProperty().bind(this.panneau_central.widthProperty());
        zone_clip.heightProperty().bind(this.panneau_central.heightProperty());
        this.panneau_central.setClip(zone_clip);

        // Afficher l'aide lorsque la Property aide_affichee est active.
        this.toggle_aide.selectedProperty().bindBidirectional(this.aide_affichee);
        this.toggle_aide.selectedProperty().addListener(new ChangeListener<Boolean>() {
            final TranslateTransition transition = new TranslateTransition(Duration.millis(150),
                    ControllerNiveau.this.panneau_aide);

            public void changed(ObservableValue<? extends Boolean> obj, Boolean ancien, Boolean nouveau) {
                // De combien déplacer la fenêtre d'aide, vers le bas si nouveau == true, vers
                // le haut sinon
                transition.setByY((panneau_aide.getHeight() - btn_aide.getHeight() - btn_pos_aide.getHeight())
                        * (nouveau ? 1 : -1));
                transition.play();
            }
        });

        // Charger le premier niveau
        this.loadNiveauSuivant();

        // Sauvegader à la fermeture du programme
        root.getWindow().setOnCloseRequest((e) -> this.retourClique());
    }

    /**
     * Charge la grille suivante dans la liste des niveaux à jouer.
     *
     * @throws Exception lancée lorsque la grille n'a pas pû être chargée.
     */
    public void loadNiveauSuivant() throws Exception {
        int id_niveau = file_niveaux.get(this.index_file++);

        donnees_niveau = Profil.getInstance().chargerDonneesNiveau(id_niveau, true);

        this.grille = new Grille(donnees_niveau.getMatriceNiveau(), donnees_niveau.getMatriceSolution(),
                donnees_niveau.getHistorique());

        // Evenement lancé lorsque la grille est terminée
        this.grille.addOnVictoire(() -> {

            System.out.println("gagné !");
            // Arrêter le comptage du score
            this.score.stop();

            // Afficher une popup de victoire
            Alert popup = new Alert(AlertType.NONE);
            popup.setTitle("Grille terminée !");
            DialogPane alertPane = popup.getDialogPane();
            alertPane.getStylesheets().add(getClass().getResource("/css/niveau.css").toExternalForm());
            alertPane.getStyleClass().add("alert");
            ButtonType btn_quitter;

            // Le joueur poursuit sa partie
            if (index_file < file_niveaux.size()) {

                String texte_affiche = "Nombre de grilles terminées : " + (((ScoreEndless) score).getNbGrilles() + 1)
                        + ". ";

                // Le joueur ne peut plus poursuivre sa partie
                if (this.score.getScore() <= 0) {
                    btn_quitter = new ButtonType("Quitter");
                    texte_affiche += "Le temps s'est écoulé durant votre partie. Vous ne pouvez pas continuer";
                    niveau_en_cours = false;

                } else { // Le joueur gagne un bonus de temps et continue

                    this.score.grilleComplete();
                    donnees_niveau.setChronoTemp((int) this.score.getScore());
                    donnees_niveau.setNbGrilles(((ScoreEndless) score).getNbGrilles());

                    try {
                        Profil.setScore(((ScoreEndless) this.score).getNbGrilles(), niveau_en_cours, retour);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    texte_affiche += "Cliquez pour poursuivre.";
                    ButtonType btn_suivant = new ButtonType("Grille suivante");
                    btn_quitter = new ButtonType("Abandonner");
                    popup.getButtonTypes().add(btn_suivant);

                }

                popup.setContentText(texte_affiche);

            } else { // Le joueur a terminé la partie
                this.score.grilleComplete();
                popup.setContentText("Partie terminée ! Score : " + score.getScoreFormate(false) + ".");
                btn_quitter = new ButtonType("Quitter");
                niveau_en_cours = false;

            }

            popup.getButtonTypes().add(btn_quitter);
            Optional<ButtonType> result = popup.showAndWait();

            if (result.get() == btn_quitter) {
                niveau_en_cours = false;
                retour = true;
                this.retourClique();
            } else {
                try {
                    retour = false;
                    this.loadNiveauSuivant();
                } catch (Exception e) {
                    niveau_en_cours = false;
                    retour = true;
                    this.retourClique();
                }
            }
        });

        // Vider le panneau central
        this.panneau_central.getChildren().clear();

        // Mettre la grille au centre (et ajouter une marge)
        Pane panneau_grille = grille.getPanneau();
        StackPane.setMargin(panneau_grille, new Insets(30, 30, 30, 30));
        this.panneau_central.getChildren().add(panneau_grille);

        // Recharger la fenêtre d'aide pour l'afficher par dessus la grille
        this.panneau_central.getChildren().add(panneau_aide);

        // Données de score
        this.score = donnees_niveau.getScore();
        this.panneau_score.setCenter(this.score.get_Pane());

        // Lier les boutons Undo et Redo à l'historique
        this.btn_undo.disableProperty().bind(donnees_niveau.getHistorique().peutAnnulerProperty().not());
        this.btn_redo.disableProperty().bind(donnees_niveau.getHistorique().peutRetablirProperty().not());

        this.score.start();
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
    private void retourClique() {
        retour = true;
        try {
            Profil.setScore(score.getScore(), niveau_en_cours, retour);
            Profil.getInstance().sauvegarderNiveau(grille);
            this.grille.capturerGrille(Path.repertoire_lvl.toString() + "/" + Profil.getJoueur() + "/"
                    + Profil.getModeDeJeu() + "/" + "capture_niveau_" + Profil.getIdNiveau() + ".png");

            root.setRoot(new ControllerMenuModeJeu(root).getPane());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Annule le dernier mouvement de la grille.
     */
    @FXML
    private void undoClique() {
        grille.undo();
    }

    /**
     * Rétabli le dernier mouvement de la grille.
     */
    @FXML
    private void redoClique() {
        grille.redo();
    }

    /**
     * Invoque l'aide pour résoudre la grille.
     * 
     * @see Aide
     */
    @FXML
    private void aideClique() {
        Resultat r = Aide.calculer(new Matrice(this.grille.getMatrice()));
        this.pos_aide = r.getPosition();
        if (r.getSucces()) {
            this.score.aideUtilise();
            this.toggle_aide.setSelected(true);
            if (r.getAffichage() != null) {
                Pane p = r.getAffichage();
                p.setMouseTransparent(true);
                ((BorderPane) this.panneau_aide.getCenter()).setCenter(p);
            }
        }
    }

    /**
     * Vérifie si la grille est correcte.
     * Undo jusqu'à la dernière grille correcte si le joueur le demande.
     */
    @FXML
    void checkClique() {
        int nb_erreurs = this.grille.nbErreurs();

        // Appliquer un malus si l'utilisateur a utilisé l'aide
        this.score.checkUtilise();

        // Si aucune erreur, on affiche un message d'information
        if (nb_erreurs == 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Vérification des erreurs");
            alert.setHeaderText(
                    "Aucune erreur détectée. Un malus vous a quand même été appliqué pour l'utilisation de l'aide.");

            DialogPane alertPane = alert.getDialogPane();
            alertPane.getStylesheets().add(getClass().getResource("/css/niveau.css").toExternalForm());
            alertPane.getStyleClass().add("alert");
            alert.initOwner((Stage) root.getWindow());
            alert.showAndWait();
            return;
        }

        // Proposer de rétablir la grille au dernier état correct
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Vérification des erreurs");
        alert.setHeaderText("Vous avez fait " + nb_erreurs
                + " erreur(s). Voulez-vous rétablir la grille au dernier état correct ? Aucun malus supplémentaire ne vous sera appliqué.");

        DialogPane alertPane = alert.getDialogPane();
        alertPane.getStylesheets().add(getClass().getResource("/css/niveau.css").toExternalForm());
        alertPane.getStyleClass().add("alert");
        alert.initOwner((Stage) root.getWindow());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            while (this.grille.nbErreurs() > 0)
                this.grille.undo();
        }
    }

    /**
     * Réinitialise la grille et le score.
     */
    @FXML
    void resetClique() {
        // afficher une fenêtre de confirmation
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Réinitialiser la grille");
        alert.setHeaderText("Voulez-vous vraiment réinitialiser la grille ?");

        DialogPane alertPane = alert.getDialogPane();
        alertPane.getStylesheets().add(getClass().getResource("/css/niveau.css").toExternalForm());
        alertPane.getStyleClass().add("alert");
        alert.initOwner((Stage) root.getWindow());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.score.restart();
            this.grille.reset();
        }
    }

    /**
     * Mets en surbrillance pendant 2 secondes les cases de la grille qui sont
     * sugérées par l'aide.
     * 
     * @see Aide
     */
    @FXML
    private void afficherPosAide() {
        if (this.pos_aide != null) {
            // Remonter le volet pour faciliter la lecture par l'utilisateur
            this.toggle_aide.setSelected(false);

            for (Position p : this.pos_aide)
                this.grille.surbrillance(p, true);

            // Désactiver la surbrillance après 2 secondes
            new AnimationTimer() {
                private long temps_debut;
                private List <Position> l = pos_aide;

                @Override
                public void start() {
                    super.start();
                    temps_debut = System.currentTimeMillis();
                }

                @Override
                public void handle(long now) {
                    if ((System.currentTimeMillis() - temps_debut) > 2000) {
                        for (Position p : l)
                            grille.surbrillance(p, false);
                        this.stop();
                    }
                }
            }.start();
        }
    }

}
