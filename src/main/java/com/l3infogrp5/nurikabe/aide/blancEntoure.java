package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Une case BLANCHE ou Point entouré de voisins NOIR doit devenir NOIR
 *
 * @param m la matrice à traiter
 * @return la liste des positions des cases à modifier
 */
public class blancEntoure implements Algorithme {

    @Override
    public Resultat resoudre(Matrice m) {

        List<Position> PositionList = new ArrayList<>();

        // Pour chaque case de la matrice
        for (int x = 0; x < m.getNbLignes(); x++) {

            for (int y = 0; y < m.getNbColonnes(); y++) {
                Position pos = new Position(x, y);

                // Si c'est une case blanche ou un point
                if (Etat.fromInt(m.get(pos)) == Etat.BLANC || Etat.fromInt(m.get(pos)) == Etat.POINT) {
                    List<Position> position_voisins = pos.getVoisins();
                    List<Position> position_voisins_valides = new ArrayList<Position>();

                    position_voisins.forEach(voisin -> {
                        if (m.posValide(voisin)) {
                            if (Etat.fromInt(m.get(voisin)) == Etat.NOIR) {
                                position_voisins_valides.add(pos);
                            }
                        }
                    });

                    // Si il y a au moins 2 voisins noirs autour de la case blanche ou du point
                    // alors on ajoute la case à la liste des résultats
                    if (position_voisins_valides.size() >= 2) {
                        // On lui assigne la position actuelle
                        // On lui assigne la valeur NOIR
                        // On ajoute la case à notre liste de résultats
                        PositionList.add(pos);

                    }
                }
            }
        }

        if (PositionList.isEmpty()) {
            return new Resultat(false, PositionList,
                    new BorderPane(new Label("Aucune case BLANCHE ou POINT entouré de voisins NOIR")));
        }

        // On retourne le résultat
        return new Resultat(true, PositionList,
                new BorderPane(new Label("Une case BLANCHE ou POINT entouré de voisins NOIR doit devenir NOIR")));
    }

}
