package com.l3infogrp5.nurikabe.niveau.grille;

import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.image.ImageView;

/**
 * Représente une case pouvant changer d'état à chaque clic simple.
 * L'état du bouton est lié à l'état donné par la grille à la même position que
 * le bouton.
 * 
 * @author Julien Rouaux
 * @see com.l3infogrp5.nurikabe.niveau.grille.Case
 */
public class CaseInteractive extends Case {

    private ImageView imagePoint;

    /**
     * Initialise une case intéractive.
     * 
     * @param pos position de la case dans la grille.
     */
    public CaseInteractive(Position pos) {
        super(pos);

        // Exécuté lors d'un simple clic
        this.setEventClicGauche(() -> {
            this.etatProperty().set(getEtat().etatSuivant().toInt());
        });


        // Appelé lorsque l'état de la cellule est changé (depuis n'importe où)
        this.etatProperty().addListener((obj, ancien_etat, nouvel_etat) -> {
            changerApparence(Etat.fromInt(ancien_etat.intValue()),
                    Etat.fromInt(nouvel_etat.intValue()));
        });

        // Importer l'image du "point"
        imagePoint = new ImageView("/img/point.png");
        imagePoint.fitWidthProperty().bind(this.widthProperty().divide(3));
        imagePoint.setPreserveRatio(true);
        imagePoint.xProperty().bind(this.widthProperty().divide(2).subtract(imagePoint.fitWidthProperty().divide(2)));
        imagePoint.yProperty().bind(this.heightProperty().divide(2).subtract(imagePoint.fitHeightProperty().divide(2)));
    }

    /**
     * Change l'apparence du bouton à chaque changement d'état.
     * 
     * @param ancien  ancien état.
     * @param nouveau nouvel état.
     */
    private void changerApparence(Etat ancien, Etat nouveau) {

        // Retirer l'apparence de l'ancien état
        switch (ancien) {
            case NOIR:
                this.getStyleClass().remove("case-noire");
                break;
            case POINT:
                this.getStyleClass().remove("case-point");
                this.setGraphic(null);
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
                this.setGraphic(imagePoint);
                break;
            default:
            case BLANC:
                this.getStyleClass().add("case-blanche");
                break;
        }
    }
}
