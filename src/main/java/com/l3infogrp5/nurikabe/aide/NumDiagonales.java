package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Classe implémentant l'algorithme d'aide à la résolution lorsque l'on a 2
 * cases NUM en diagonale
 * 
 * @author Killian Rattier
 */
public class NumDiagonales implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public NumDiagonales() {
        affichage = new BorderPane();
        affichage.setCenter(new Label(
                "Si deux cases numériques sont adjacentes en diagonale, les deux cases blanches de la diagonale opposée doivent être noircies."));
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
        // Pour chaque case de la matrice
        for (int x = 0; x < m.getNbLignes(); x++) {
            for (int y = 0; y < m.getNbColonnes(); y++) {
                Position pos = new Position(x, y);
                // Si pos est une case NUM
                if (Aide.isNumPreproc(m, pos)) {
                    // On vérifie si une de ses diagonales est aussi une case NUM
                    List<Position> diagonales = pos.getDiagonales();
                    for (Position diag : diagonales) {
                        if (m.posValide(diag) && Aide.isNumPreproc(m, diag)) {
                            // On regarde les voisins à cette diagonale
                            List<Position> voisinsDiag = diag.getVoisins();
                            // Pour chaque voisin
                            for (Position v : voisinsDiag) {
                                // Si il a au moins 2 voisins NUM
                                List<Position> voisins2deg = v.getVoisins();
                                int count = 0;
                                // Parcours des voisins au 2eme degré
                                for (Position v2 : voisins2deg) {
                                    if (m.posValide(v2) && Aide.isNumPreproc(m, v2)) {
                                        count++;
                                    }
                                    if (count == 2 && m.get(v) != -999 && !Aide.isNumPreproc(m,v)) {
                                        resList.add(v);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!resList.isEmpty()) return new Resultat(true, resList, affichage);
            }
        }
        return new Resultat(false, null, new BorderPane(new Label("Aucune aide disponible")));
    }
}