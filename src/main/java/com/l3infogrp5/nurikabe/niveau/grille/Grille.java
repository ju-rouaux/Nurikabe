package com.l3infogrp5.nurikabe.niveau.grille;

import java.io.Serializable;

import com.l3infogrp5.nurikabe.niveau.grille.Historique.Mouvement;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * Génère une grille de {@link Case} à partir de la matrice donnée.
 * Il est possible de remplir une {@link GridPane} de ces cases grâce à la
 * méthode {@link #remplirPanneau(GridPane)}.
 * Chaque modification faite sur la matrice sera répercutée automatiquement sur
 * l'affichage des cases, et vice-versa.
 *
 * @author Julien Rouaux
 */
public class Grille implements Serializable {

    /** Vrai si le suivi des mouvements doit être activé. */
    private boolean suivre_mouvement;

    private int nb_lignes;
    private int nb_colonnes;

    /**
     * Grille interne composée des Properties des cases.
     * Pour récupérer la case liée à la Property, utiliser
     * {@link #getCase(IntegerProperty)}
     */
    private transient IntegerProperty[][] grille;

    private Historique histo;

    /**
     * Créer une grille.
     * Utiliser {@link #remplirPanneau(GridPane)} pour afficher la grille dans le
     * panneau donné.
     *
     * @param matrice    initialisation de la grille.
     * @param historique historique des mouvements réalisés sur cette grille.
     */
    public Grille(int[][] matrice, Historique historique) {

        Case case_courante;

        this.suivre_mouvement = false;

        this.nb_lignes = matrice.length;
        this.nb_colonnes = matrice[0].length;

        this.histo = historique;

        // Charger la matrice interne et ses cases
        this.grille = new SimpleIntegerProperty[nb_lignes][nb_colonnes];

        for (int i = 0; i < nb_lignes; i++) {
            for (int j = 0; j < nb_colonnes; j++) {

                // Case numérique
                if (matrice[i][j] > 0) {
                    case_courante = new CaseNumerique(new Position(i, j, i * nb_lignes + j));

                    // Définition des événements de maintien
                    // TODO ceci est une démo
                    case_courante.setEventMaintienGauche(new EventClicMaintenu() {
                        public void maintenu(Case c) {
                            System.out.println("Maintien de : " + c.getPosition());
                            c.surbrillance(true, 0);
                            for (IntegerProperty[] ligne : grille)
                                for (IntegerProperty c_demo : ligne)
                                    getCase(c_demo).surbrillance(true, 0);
                        }

                        public void relache(Case c) {
                            System.out.println("Relachement de : " + c.getPosition());
                            c.surbrillance(false, 0);
                            for (IntegerProperty[] ligne : grille)
                                for (IntegerProperty c_demo : ligne)
                                    getCase(c_demo).surbrillance(false, 0);
                        }
                    });
                }

                // Case intéractive
                else {
                    case_courante = new CaseInteractive(new Position(i, j, i * nb_lignes + j));

                    // Définition des événements de maintien
                    // TODO ceci est une démo
                    case_courante.setEventMaintienGauche(new EventClicMaintenu() {
                        public void maintenu(Case c) {
                            System.out.println("Maintien de : " + c.getPosition());
                            c.surbrillance(true, 0);
                        }

                        public void relache(Case c) {
                            System.out.println("Relachement de : " + c.getPosition());
                            c.surbrillance(false, 0);
                        }
                    });

                }

                // Ajouter la Property de la case à la matrice des Properties
                this.grille[i][j] = case_courante.etatProperty();

                // Assigner la valeur de la case
                this.grille[i][j].set(matrice[i][j]);

                // Commencer le suivi des mouvements
                this.suivre_mouvement = true;

                // Suivre chaque changement de la grille lorsque suivre_mouvement est vrai
                this.grille[i][j].addListener((property, ancienEtat, nouvelEtat) -> {
                    if (this.suivre_mouvement)
                        this.histo.ajoutMouvement(new Mouvement(this.getCase((IntegerProperty) property).getPosition(),
                                Etat.fromInt(ancienEtat.intValue()), Etat.fromInt(nouvelEtat.intValue())));
                });
            }
        }
    }

    /**
     * Retourne la case liée à la Property.
     *
     * @param i la Property de la case à obtenir.
     * @return la case liée à la Property.
     */
    private Case getCase(IntegerProperty i) {
        return ((Case) i.getBean());
    }

    /**
     * Changer l'état d'une case.
     *
     * @param x    coordonnées en x de la case à changer.
     * @param y    coordonnées en y de la case à changer.
     * @param etat nouvel état de la case.
     * @throws IllegalArgumentException lancé lorsque la case ciblée est une case
     *                                  numérique.
     */
    public void set(int x, int y, Etat etat) throws IllegalArgumentException {
        if (this.grille[x][y].get() > 0)
            throw new IllegalArgumentException(
                    "Impossible de modifier l'état de la celulle à la position (" + x + ", " + y + ").");

        this.grille[x][y].set(etat.toInt());
    }

    /**
     * Retourne l'état ou la valeur de la case ciblée.
     *
     * @param x coordonnées en x de la case à cibler.
     * @param y coordonnées en y de la case à cibler.
     * @return l'état ou la valeur de la case donnée.
     */
    public int get(int x, int y) {
        return this.grille[x][y].get();
    }

    /**
     * Retourne une copie de la matrice.
     * Aucune modification ne sera répercutée sur la vraie matrice.
     * Pour modifier la vraie matrice, utiliser la méthode set().
     *
     * @return une copie de la matrice.
     * @see Grille#set(int, int, Etat)
     */
    public int[][] getMatrice() {
        int[][] copie = new int[this.grille.length][this.grille[0].length];

        for (int i = 0; i < this.grille.length; i++)
            for (int j = 0; j < this.grille[i].length; j++)
                copie[i][j] = this.grille[i][j].get();

        return copie;
    }

    public void setMatrice(int mat [][]){
        for (int i = 0; i < this.grille.length; i++)
            for (int j = 0; j < this.grille[i].length; j++)
                this.grille[i][j].set(mat[i][j]);
    }

    /**
     * Remplir le panneau donné des cases générées par la grille, afin d'afficher
     * cette dernière.
     *
     * @param panneau le panneau qui accueille les cases.
     */
    public void remplirPanneau(GridPane panneau) {
        for (int i = 0; i < this.grille.length; i++)
            for (int j = 0; j < this.grille[i].length; j++) {
                // Prendre l'espace de tout le conteneur

                getCase(this.grille[i][j]).setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                // Ajouter
                panneau.add(getCase(this.grille[i][j]), j, i);
                GridPane.setHgrow(getCase(this.grille[i][j]), Priority.ALWAYS);
                GridPane.setVgrow(getCase(this.grille[i][j]), Priority.ALWAYS);
            }
    }

    /**
     * Annule le dernier mouvement du joueur.
     * Cette méthode désactive le suivi des mouvements pour ne pas enregistrer des
     * mouvements réalisés par cette dernière.
     */
    public void undo() {
        if (histo.peutAnnuler()) {
            this.suivre_mouvement = false; // Désactiver le suivi des mouvements
            Mouvement m = histo.annuler();
            this.grille[m.getPosition().getX()][m.getPosition().getY()].set(m.getAncienEtat().toInt());
            this.suivre_mouvement = true; // Réactiver le suivi des mouvements
        }
    }

    /**
     * Rétabli le dernier mouvement du joueur.
     * Cette méthode désactive le suivi des mouvements pour ne pas enregistrer des
     * mouvements réalisés par cette dernière.
     */
    public void redo() {
        if (histo.peutRetablir()) {
            this.suivre_mouvement = false; // Désactiver le suivi des mouvements
            Mouvement m = histo.retablir();
            this.grille[m.getPosition().getX()][m.getPosition().getY()].set(m.getNouvelEtat().toInt());
            this.suivre_mouvement = true; // Réactiver le suivi des mouvements
        }
    }

    /**
     * Initialise la variable IntegerProperty[][] grille apres la deserialisation.
     * @param matrice la matrice de la grille.
     */
    public void initTransientGrille(int [][] matrice) {
        grille = new SimpleIntegerProperty[nb_lignes][nb_colonnes];
        Case case_courante;
        for (int i = 0; i < nb_lignes; i++) {
            for (int j = 0; j < nb_colonnes; j++) {

                // Case numérique
                if (matrice[i][j] > 0) {
                    case_courante = new CaseNumerique(new Position(i, j, i * nb_lignes + j));

                    // Définition des événements de maintien
                    // TODO ceci est une démo
                    case_courante.setEventMaintienGauche(new EventClicMaintenu() {
                        public void maintenu(Case c) {
                            System.out.println("Maintien de : " + c.getPosition());
                            c.surbrillance(true, 0);
                            for (IntegerProperty[] ligne : grille)
                                for (IntegerProperty c_demo : ligne)
                                    getCase(c_demo).surbrillance(true, 0);
                        }

                        public void relache(Case c) {
                            System.out.println("Relachement de : " + c.getPosition());
                            c.surbrillance(false, 0);
                            for (IntegerProperty[] ligne : grille)
                                for (IntegerProperty c_demo : ligne)
                                    getCase(c_demo).surbrillance(false, 0);
                        }
                    });
                }

                // Case intéractive
                else {
                    case_courante = new CaseInteractive(new Position(i, j, i * nb_lignes + j));

                    // Définition des événements de maintien
                    // TODO ceci est une démo
                    case_courante.setEventMaintienGauche(new EventClicMaintenu() {
                        public void maintenu(Case c) {
                            System.out.println("Maintien de : " + c.getPosition());
                            c.surbrillance(true, 0);
                        }

                        public void relache(Case c) {
                            System.out.println("Relachement de : " + c.getPosition());
                            c.surbrillance(false, 0);
                        }
                    });

                }

                // Ajouter la Property de la case à la matrice des Properties
                this.grille[i][j] = case_courante.etatProperty();

                // Assigner la valeur de la case
                this.grille[i][j].set(matrice[i][j]);

                // Commencer le suivi des mouvements
                this.suivre_mouvement = true;

                // Suivre chaque changement de la grille lorsque suivre_mouvement est vrai
                this.grille[i][j].addListener((property, ancienEtat, nouvelEtat) -> {
                    if (this.suivre_mouvement)
                        this.histo.ajoutMouvement(new Mouvement(this.getCase((IntegerProperty) property).getPosition(),
                                Etat.fromInt(ancienEtat.intValue()), Etat.fromInt(nouvelEtat.intValue())));
                });
            }
        }
    }

}
