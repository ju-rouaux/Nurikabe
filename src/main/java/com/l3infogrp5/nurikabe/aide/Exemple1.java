package com.l3infogrp5.nurikabe.aide;

import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Classe représentant un exemple d'algorithme d'aide à la résolution.
 * //TODO : supprimer cette classe
 */
class Exemple1 implements Algorithme {
    BorderPane affichage;

    public Exemple1() {
        affichage = new BorderPane();
        affichage.setCenter(new Label("Bonne chance !"));
    }

    @Override
    public Resultat resoudre(Matrice m) {
        System.out.println("Exécution de l'algorithme Exemple1");
        return new Resultat(true, new Position(0,0), this.affichage);
    }

}
