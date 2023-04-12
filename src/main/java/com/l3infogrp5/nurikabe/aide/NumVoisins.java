package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Classe implémentant l'algorithme d'aide à la résolution lorsque 2 cases NUM snt séparées d'une case
 * 
 * @author Killian Rattier, Guillaume Richard
 */
public class NumVoisins implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public NumVoisins() {
        affichage = new BorderPane();
        affichage.setCenter(new Label(
                "Si deux cases numériques sont séparées par une case blanche dans la même ligne ou colonne, la case blanche doit être noircie."));
    }

    /**
     * Resouds l'algorithme d'aide dans une matrice donnée.
     * 
     * @param m la matrice à tester
     * @return la liste des cases trouvées par l'algorithme
     */
    @Override
    public Resultat resoudre(Matrice m) {
        List<Position> resList = new ArrayList<>();
        for (int x = 0; x < m.getNbLignes(); x++) {
            for (int y = 0; y < m.getNbColonnes(); y++) {
                Position pos = new Position(x, y);
                if (m.get(pos) == 999) {
                    if (((m.posValide(pos.getN()) && Aide.isNumPreproc(m, pos.getN())))
                            && (m.posValide(pos.getS()) && Aide.isNumPreproc(m, pos.getS()))
                            || (m.posValide(pos.getE()) && Aide.isNumPreproc(m, pos.getE()))
                                    && (m.posValide(pos.getO()) && Aide.isNumPreproc(m, pos.getO()))) {
                        resList.add(pos);
                    }
                }
            }
        }
        if (!resList.isEmpty()) return new Resultat(true, resList, affichage);
        return new Resultat(false, null, new BorderPane(new Label("Aucune aide disponible")));
    }
}