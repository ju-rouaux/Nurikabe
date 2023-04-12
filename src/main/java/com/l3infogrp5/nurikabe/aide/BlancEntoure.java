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
 * @author Elias OKAT
 */
class BlancEntoure implements Algorithme {

    /**
     * Résoud l'algorithme de la case BLANCHE ou POINT entouré de voisins NOIR doit
     * devenir NOIR
     *
     * @param m la matrice à traiter
     * @return la première valeur de la liste des positions des cases à modifier et
     *         le message à afficher en cas de succès
     */
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
                    // Les voisins invalides sont les voisins qui sont hors de la matrice
                    List<Position> position_voisins_invalides = new ArrayList<Position>();

                    // On parcourt les voisins de la case
                    position_voisins.forEach(voisin -> {
                        if (m.posValide(voisin)) {
                            if (Etat.fromInt(m.get(voisin)) == Etat.NOIR) {
                                position_voisins_valides.add(pos);
                            }
                        } else {
                            position_voisins_invalides.add(pos);
                        }
                    });

                    // Si il y a au moins 2 voisins noirs autour de la case blanche ou du point
                    // alors on ajoute la case à la liste des résultats
                    if ((position_voisins_valides.size() >= 2)
                            && (position_voisins_valides.size() + position_voisins_invalides.size() == 4)) {
                        // On ajoute la case à notre liste de résultats
                        PositionList.add(pos);
                    }
                }
            }
        }

        // Si la liste est vide, la valeur de retour est faux et on retourne une liste
        // vide
        if (PositionList.isEmpty()) {
            return new Resultat(false, PositionList,
                    new BorderPane(new Label("")));
        }

        // Sinon la valeur de retour est vrai et on retourne la liste des positions à
        // modifier
        return new Resultat(true, PositionList.subList(0, 1),
                new BorderPane(
                        new Label(
                                "Une case blanche où point entouré de cases voisines noires doit devenir elle aussi noire.")));
    }

}
