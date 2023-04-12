package com.l3infogrp5.nurikabe.niveau.grille;

import com.l3infogrp5.nurikabe.utils.Position;

import javafx.beans.binding.Bindings;
import javafx.util.converter.NumberStringConverter;

/**
 * Représente une case numérique.
 * La valeur du bouton est liée à la valeur ayant la même position dans la
 * grille.
 * 
 * @author Julien Rouaux
 * @see com.l3infogrp5.nurikabe.niveau.grille.Case
 */
public class CaseNumerique extends Case {

    /**
     * Initialise une case numérique.
     * 
     * @param pos la position de la case dans la grille.
     */
    public CaseNumerique(Position pos) {
        super(pos);

        //Style
        this.getStyleClass().add("case-numerique");

        // Lier l'affichage du bouton à l'état de la case.
        this.textProperty().bindBidirectional(this.etatProperty(), new NumberStringConverter());

        // Lier la taille du nombre à la taille de la case
        this.styleProperty().bind(Bindings.concat("-fx-font-size: ", this.heightProperty().divide(2)));
    }

    /**
     * Retourne la valeur de la case.
     * 
     * @return la valeur de la case.
     */
    public int getValeur() {
        return Integer.valueOf(this.textProperty().get());
    }
}
