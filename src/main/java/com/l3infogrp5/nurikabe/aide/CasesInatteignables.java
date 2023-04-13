package com.l3infogrp5.nurikabe.aide;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Classe implémentant l'algorithme d'aide à la résolution indiquant les cases
 * ne pouvant être atteintes par aucune case NUM
 * 
 * @author Killian Rattier, Elias Okat
 */
public class CasesInatteignables implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public CasesInatteignables() {
        affichage = new BorderPane();
        // Image
        ImageView img = new ImageView("/img/aide/inatteignables.png");
        // Taille de l'image
        img.setFitWidth(100);
        img.setFitHeight(100);
        // Ajouter l'image à gauche et la centrer verticalement
        affichage.setLeft(img);
        BorderPane.setAlignment(img, Pos.CENTER);
        // Aouter une marge autour de l'image
        BorderPane.setMargin(img, new Insets(10));
        // Ajouter le texte
        Label texte = new Label("Si une case blanche ne peut être atteinte par aucune case numérique, elle doit être noircie.");
        texte.setWrapText(true);
        affichage.setCenter(texte);
    }

    /**
     * Retourne les cases atteignables depuis une case numérique donnée.
     * 
     * @param m la matrice
     * @param pos la position de la case à tester
     */
    public static List<Position> atteignablesParCase(Matrice m, Position pos) {
        List<Position> atteignables = new ArrayList<>();
        
        // Si on a une case NUM
        if (Aide.isNum(m, pos)) {
            // On ajoute la case à la pile
            Stack<Position> pile = new Stack<>();
            pile.push(pos);
            atteignables.add(pos);

            int distanceMax = m.getElement(pos.getX(), pos.getY()) - 1;
            // Ajouter les cases atteignables pour chaque distance
            for (int dist = 1; dist <= distanceMax; dist++) {
                List<Position> casesATraiter = new ArrayList<>(pile);
                for (Position caseCourante : casesATraiter) {
                    List<Position> voisins = caseCourante.getVoisins();
                    for (Position voisin : voisins) {
                        if (m.posValide(voisin)) {
                            int distance = Math.abs(voisin.getX() - pos.getX()) + Math.abs(voisin.getY() - pos.getY());
                            if (distance <= dist && ((Etat.fromInt(m.get(voisin)) != Etat.NOIR) && (!Aide.isNum(m, voisin)))) {
                                atteignables.add(voisin);
                                pile.push(voisin);

                            }
                        }
                    }
                }
            }
        }

        return atteignables;
    }


    /**
     * Méthode parcourant la matrice et renvoyant les cases que l'on peut atteindre
     * 
     * @param m la matrice à tester
     * @return la liste des cases atteignables
     */
    public List<Position> atteignables(Matrice m) {
        List<Position> atteignables = new ArrayList<>();

        // Pour chaque case de la matrice
        for (int x = 0; x < m.getNbLignes(); x++) {
            for (int y = 0; y < m.getNbColonnes(); y++) {
                Position pos = new Position(x, y);
                atteignables.addAll(atteignablesParCase(m, pos));
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
        List<Position> cases = new ArrayList<>();

        // Parcourir la matrice pour ajouter les cases innaccessibles à la liste
        for (int i = 0; i < m.getNbLignes(); i++) {
            for (int j = 0; j < m.getNbColonnes(); j++) {
                Position pos = new Position(i, j);
                cases.add(pos);
                if (!atteignables.contains(pos) && (!(Etat.fromInt(m.get(pos)) == Etat.NOIR))) {
                    inatteignables.add(pos);
                }
            }
        }

        if (!inatteignables.isEmpty())
            return new Resultat(true, inatteignables, affichage);
        return new Resultat(false, null, new BorderPane(new Label("Aucune aide disponible")));
    }
}