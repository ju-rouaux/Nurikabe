package com.l3infogrp5.nurikabe.niveau.grille;

import com.l3infogrp5.nurikabe.utils.Position;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.GridPane;

/**
 * Génère une grille de {@link Case} à partir de la matrice donnée.
 * Il est possible de remplir une {@link GridPannel} de ces cases grâce à la
 * méthode {@link #remplirPanneau(GridPane)}.
 * Chaque modification faite sur la matrice sera répercutée automatiquement sur
 * l'affichage des cases, et vice-versa.
 * 
 * @author Julien Rouaux
 */
public class Grille {

    private Case[][] cases; // Cases affichées

    private IntegerProperty[][] matrice; // Grille interne

    /**
     * Créer une grille.
     * Utiliser {@link #remplirPanneau(GridPane)} pour afficher la grille dans le
     * panneau donné.
     * 
     * @param matrice initialisation de la grille.
     */
    public Grille(int[][] matrice) {

        this.cases = new Case[matrice.length][matrice[0].length];

        Case case_courante;
        int nb_lignes = matrice.length;
        int nb_colonnes = matrice[0].length;

        // Charger la matrice  interne et ses cases
        this.matrice = new SimpleIntegerProperty[nb_lignes][nb_colonnes];

        for (int i = 0; i < nb_lignes; i++) {
            for (int j = 0; j < nb_colonnes; j++) {

                // Créer une IntegerProperty pour la case
                this.matrice[i][j] = new SimpleIntegerProperty(matrice[i][j]);

                // Case numérique
                if (matrice[i][j] > 0) {
                    case_courante = new CaseNumerique(new Position(i, j, i * nb_lignes + j));

                    // Définition des événements de maintien
                    // TODO ceci est une démo
                    case_courante.setEventMaintienGauche(new EventClicMaintenu() {
                        public void maintenu(Case c) {
                            System.out.println("Maintien de : " + c.getPosition());
                            for (Case[] ligne : cases)
                                for (Case c_demo : ligne)
                                    c_demo.surbrillance(true, 0);
                        }

                        public void relache(Case c) {
                            System.out.println("Relachement de : " + c.getPosition());
                            c.surbrillance(false, 0);
                            for (Case[] ligne : cases)
                                for (Case c_demo : ligne)
                                    c_demo.surbrillance(false, 0);
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

                // Lier l'état de la case à celui de la matrice
                case_courante.etatProperty().bindBidirectional(this.matrice[i][j]);

                // Ajouter la case à la liste des cases
                cases[i][j] = case_courante;
            }
        }
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
        if (this.matrice[x][y].get() > 0)
            throw new IllegalArgumentException(
                    "Impossible de modifier l'état de la celulle à la position (" + x + ", " + y + ").");

        this.matrice[x][y].set(etat.toInt());
    }

    /**
     * Retourne l'état ou la valeur de la case ciblée.
     * 
     * @param x coordonnées en x de la case à cibler.
     * @param y coordonnées en y de la case à cibler.
     * @return l'état ou la valeur de la case donnée.
     */
    public int get(int x, int y) {
        return this.matrice[x][y].get();
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
        int[][] copie = new int[this.matrice.length][this.matrice[0].length];

        for (int i = 0; i < this.matrice.length; i++)
            for (int j = 0; j < this.matrice[i].length; j++)
                copie[i][j] = this.matrice[i][j].get();

        return copie;
    }

    /**
     * Remplir le panneau donné des cases générées par la grille, afin d'afficher
     * cette dernière.
     * 
     * @param panneau le panneau qui accueille les cases.
     */
    public void remplirPanneau(GridPane panneau) {
        for (int i = 0; i < this.matrice.length; i++)
            for (int j = 0; j < this.matrice[i].length; j++)
                panneau.add(this.cases[i][j], j, i);
    }
}
