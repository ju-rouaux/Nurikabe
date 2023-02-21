package com.l3infogrp5.nurikabe.niveau.grille;

import com.l3infogrp5.nurikabe.utils.Position;

/**
 * Représente une case pouvant changer d'état à chaque clic simple.
 * L'état du bouton est lié à l'état donné par la grille à la même position que
 * le bouton.
 * 
 * @author Julien Rouaux
 * @see com.l3infogrp5.nurikabe.niveau.grille.Case
 */
class CaseInteractive extends Case {

    /**
     * Initialise une case intéractive.
     * 
     * @param pos position de la case dans la grille.
     */
    CaseInteractive(Position pos) {
        super(pos);

        //Style 
        this.getStyleClass().add("case-interactive");

        //Exécuté lors d'un simple clic
        this.setEventClicGauche(() -> {
            this.etatProperty().set(getEtat().etatSuivant().toInt());
        });

        // Appelé lorsque l'état de la cellule est changé (depuis n'importe où)
        this.etatProperty().addListener((obj, ancien_etat, nouvel_etat) -> {
            changerApparence(Etat.fromInt(ancien_etat.intValue()),
                    Etat.fromInt(nouvel_etat.intValue()));
        });
    }

    /**
     * Change l'apparence du bouton à chaque changement d'état.
     * 
     * @param ancien  ancien état.
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
}
