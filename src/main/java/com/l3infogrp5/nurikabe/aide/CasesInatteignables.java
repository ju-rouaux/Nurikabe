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
 * Classe implémentant l'algorithme d'aide à la résolution indiquant les cases
 * ne pouvant être atteintes par aucune case NUM
 * 
 * @author Killian Rattier
 */
public class CasesInatteignables implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public CasesInatteignables() {
        affichage = new BorderPane();
        affichage.setCenter(new Label(
                "Si une case blanche ne peut appartenir à aucun chemin de case numérique, elle doit être noircie."));
    }

    public List<Position> atteignables(Matrice m){
        //List<Position> inatteignables = new ArrayList<>();
        List<Position> atteignables = new ArrayList<>();
        List<Position> cases = new ArrayList<>();

        // Pour chaque case de la matrice
        for (int x = 0; x < m.getNbLignes(); x++) {
            for (int y = 0; y < m.getNbColonnes(); y++) {
                Position pos = new Position(x, y);
                cases.add(pos);
                // Si on a une case NUM
                if (Aide.isNum(m, pos)) {
                    // On ajoute la case à la pile
                    Stack<Position> pile = new Stack<>();
                    pile.push(pos);
                    atteignables.add(pos);

                    int distanceMax = m.getElement(pos.getX(), pos.getY()) - 1;

                    // Ajouter les cases atteignables pour chaque distance
                    for (int dist = 1; dist <= distanceMax; dist++) {
                        List<Position> casesATraiter = new ArrayList<>(atteignables);
                        for (Position caseCourante : casesATraiter) {
                            List<Position> voisins = caseCourante.getVoisins();
                            for (Position voisin : voisins) {
                                if (m.posValide(voisin)) {
                                    int distance = Math.abs(voisin.getX() - pos.getX())
                                            + Math.abs(voisin.getY() - pos.getY());
                                    if (distance <= dist && !atteignables.contains(voisin)) {
                                        atteignables.add(voisin);
                                        pile.push(voisin);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return atteignables;
    }

    /**
     * Resouds l'algorithme d'aide dans une matrice donnée.
     * 
     * @param m la matrice à tester
     * @return la liste des cases trouvées par l'algorithme
     */
    @Override
    public Resultat resoudre(Matrice m) {
        List<Position> inatteignables = new ArrayList<>();
        List<Position> atteignables = atteignables(m);
        
        // Parcourir la matrice pour ajouter les cases innaccessibles à la liste
        for (int i = 0; i < m.getNbLignes(); i++) {
            for (int j = 0; j < m.getNbColonnes(); j++) {
                Position pos = new Position(i, j);
                if (!atteignables.contains(pos) && Etat.fromInt(m.get(pos)) == Etat.BLANC) {
                    inatteignables.add(pos);
                }
            }
        }
        if (!inatteignables.isEmpty())
            return new Resultat(true, inatteignables, affichage);
        return new Resultat(false, null, new BorderPane(new Label("Aucune aide disponible")));
    }
}
