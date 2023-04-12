package com.l3infogrp5.nurikabe.aide;


import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

/**
 * Classe implémentant une méthode qui retourne la liste des voisins blancs adjacents à une zone
 * 
 * Cette méthode est dans une classe à part car elle est utilisée dans plusieurs classes de l'algorithme d'aide à la résolution
 * 
 * @author Elias Okat
 */
public class SousAlgo {
    /**
     * Constructeur de la classe SousAlgo
     */
    public  SousAlgo() {
    }

    /**
     * Retourne une liste de positions des voisins blancs adjacents a une zone passée en paramètre
     * 
     * @param matrice la matrice dans laquelle on cherche les voisins blancs
     * @param zone la zone dont on cherche les voisins blancs
     * @return une liste des positions des voisins blancs adjacents à la zone
     */
    public static ArrayList<Position> case_blanche_adjacentes(Matrice matrice, List<Position> zone) {
        ArrayList<Position> positions_voisins_blanc = new ArrayList<Position>();

        // Pour chaque position de la zone
        for (Position pos : zone) {
            // Pour chaque voisin de la position
            for (Position pos_voisin : pos.getVoisins()) {
                // Si la position du voisin est valide et que la case est blanche
                if (matrice.posValide(pos_voisin)) {
                    if (matrice.get(pos_voisin) == Etat.BLANC.toInt()) {
                        // Ajouter la position du voisin à la liste des voisins blancs
                        positions_voisins_blanc.add(pos_voisin);
                    }
                }
            }
        }
        
        return positions_voisins_blanc;
    }

}
