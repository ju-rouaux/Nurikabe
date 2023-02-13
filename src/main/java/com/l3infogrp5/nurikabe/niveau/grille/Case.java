package com.l3infogrp5.nurikabe.niveau.grille;

import javafx.scene.control.Button;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.converter.NumberStringConverter;

import com.l3infogrp5.utils.Position;

/**
 * Case abstraite de grille de Nurikabe.
 * Un bouton qui connait sa position dans la grille.
 * Le bouton peut être mis en surbrillance.
 * 
 * @author Julien Rouaux
 */
abstract class Case extends Button {

    private Position position;

    /**
     * Initialise la position de la case et le style du bouton.
     * 
     * @param pos la position de la case dans la grille.
     */
    Case(Position pos) {
        this.position = pos;
        this.getStyleClass().add("case");
    }

    /**
     * Met la case en surbrillance ou non.
     * 
     * @param b           vrai si la case doit être en surbrillance, faux sinon.
     * @param taille_zone nombre de cases mises en surbrillance.
     */
    void surbrillance(boolean b, int taille_zone) {
        if (b)
            this.getStyleClass().add("surbrillance");
        else
            this.getStyleClass().remove("surbrillance");
    }

    /**
     * Retourne la position de la case.
     * 
     * @return la position de la case.
     */
    Position getPosition() {
        return this.position;
    }
}

/**
 * Représente une case numérique.
 * La valeur du bouton est liée à la valeur ayant la même position dans la
 * grille.
 * 
 * TODO : différents événements de clic
 * 
 * @author Julien Rouaux
 */
class CaseNumerique extends Case {

    /**
     * Initialise une case numérique.
     * 
     * @param pos             la position de la case dans la grille.
     * @param cellule_matrice la cellule de la matrice liée à ce bouton.
     */
    CaseNumerique(Position pos, IntegerProperty cellule_matrice) {
        super(pos);
        this.getStyleClass().add("case-numerique");

        // Lier le numéro du bouton au numéro de la matrice
        this.textProperty().bindBidirectional(cellule_matrice, new NumberStringConverter());
    }

    /**
     * Retourne la valeur de la case.
     * 
     * @return la valeur de la case.
     */
    int getValeur() {
        return Integer.valueOf(this.textProperty().get());
    }

    /**
     * {@inheritDoc}
     * Le nombre de case contenues dans la zone est affiché à côté du numéro de la
     * case.
     */
    @Override
    void surbrillance(boolean b, int taille_zone) {
        super.surbrillance(b, taille_zone);
        /*
         * TODO : afficher le nombre de case contenues dans la zone.
         * NE JAMAIS MODIFIER LA VALEUR DE this.textProperty()
         */
    }
}

/**
 * Représente une case pouvant changer d'état à chaque clic.
 * L'état du bouton est lié à l'état donné par la grille à la même position que
 * le bouton.
 * 
 * TODO : différents événements de clic
 * 
 * @author Julien Rouaux
 */
class CaseInteractive extends Case {

    private IntegerProperty etat;

    /**
     * Initialise une case intéractive.
     * 
     * @param pos             position de la case dans la grille.
     * @param cellule_matrice la cellule de la matrice liée à ce bouton.
     */
    CaseInteractive(Position pos, IntegerProperty cellule_matrice) {
        super(pos);
        this.getStyleClass().add("case-interactive");

        // Lier l'état du bouton à l'état de la celulle dans la matrice
        this.etat = new SimpleIntegerProperty();
        this.etat.bindBidirectional(cellule_matrice);

        // Bouton cliqué
        this.setOnAction(e -> {
            // Passer la case à l'état suivant
            this.etat.set(getEtat().etatSuivant().toInt());
        });

        //Appelé lorsque l'état de la cellule est changé (depuis n'importe où)
        etat.addListener((obj, ancien_etat, nouvel_etat) -> {
            changerApparence(Etat.fromInt(ancien_etat.intValue()), Etat.fromInt(nouvel_etat.intValue()));
        });

        //Initialiser l'apparence
        changerApparence(getEtat(), getEtat());
    }

    /**
     * Change l'apparence du bouton à chaque changement d'état.
     * @param ancien ancien état.
     * @param nouveau nouvel état.
     */
    private void changerApparence(Etat ancien, Etat nouveau) {
        // TODO changer l'apparence du bouton ici (ce qui suit a pour valeur d'exemple)

        // Retirer l'apparence de l'ancien état
        switch (ancien) {
            case NOIR:
                this.getStyleClass().remove("case-noire");
                break;
            case POINT:
                this.getStyleClass().remove("case-point");
                break;
            default:
            case BLANC:
                this.getStyleClass().remove("case-blanche");
                break;
        }
        switch (nouveau) {
            case NOIR:
                this.getStyleClass().add("case-noire");
                break;
            case POINT:
                this.getStyleClass().add("case-point");
                break;
            default:
            case BLANC:
                this.getStyleClass().add("case-blanche");
                break;
        }
    }

    /**
     * Retourne l'état de la case.
     * 
     * @return l'état de la case.
     */
    Etat getEtat() {
        return Etat.fromInt(this.etat.get());
    }
}
