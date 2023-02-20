package com.l3infogrp5.nurikabe.niveau.grille;

import com.l3infogrp5.nurikabe.utils.Position;

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
    }

    /**
     * Retourne la valeur de la case.
     * 
     * @return la valeur de la case.
     */
    public int getValeur() {
        return Integer.valueOf(this.textProperty().get());
    }

    /**
     * {@inheritDoc}
     * Le nombre de case contenues dans la zone est affiché à côté du numéro de la
     * case.
     */
    @Override
    public void surbrillance(boolean b, int taille_zone) {
        super.surbrillance(b, taille_zone);
        /*
         * TODO : afficher le nombre de case contenues dans la zone.
         * NE JAMAIS MODIFIER LA VALEUR DE this.textProperty()
         */
    }
}
