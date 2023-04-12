package com.l3infogrp5.nurikabe.aide;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Classe implémentant l'algorithme d'aide à la résolution indiquant comment
 * connecter deux îles
 * 
 * @author Killian Rattier
 */
public class ExpansionIle implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public ExpansionIle() {
        affichage = new BorderPane();
        affichage.setCenter(new Label(
                "Les cases noires doivent être reliées en un seul chemin continu. Si une case noire ne peut se connecter qu'à un seul chemin, elle doit être prolongée pour se connecter aux autres."));
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

        for (int i = 0; i < m.getNbLignes(); i++) {
            for (int j = 0; j < m.getNbColonnes(); j++) {
                Position pos = new Position(i, j);

                // Si la case est blanche et est bloquée entre un MUR (ou une case non blanche) et une autre case non
                // blanche, ajouter la position à la liste
                if (m.get(pos) == 999) {
                    boolean caseVoisin = false;
                    boolean autreCaseVoisin = false;

                    for (Position voisin : pos.getVoisins()) {
                        if (!m.posValide(voisin) || m.get(voisin) != -999) {
                            caseVoisin = true;
                        } else if (m.get(voisin) != -999) {
                            autreCaseVoisin = true;
                        }
                    }

                    if (caseVoisin && autreCaseVoisin) {
                        resList.add(pos);
                    }
                }
            }
        }

        if (!resList.isEmpty()) {
            return new Resultat(true, resList, affichage);
        }
        return new Resultat(false, null, new BorderPane(new Label("Aucune aide disponible")));
    }

}
