package com.l3infogrp5.nurikabe.aide;

import java.util.List;
import java.util.ArrayList;

import com.l3infogrp5.nurikabe.aide.Zone;
import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Classe implémentant l'algorithme d'aide à la résolution lorsque 2 cases NUM
 * snt séparées d'une case
 * 
 * @author Killian Rattier, Guillaume Richard
 */
public class IleCompletee implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public IleCompletee() {
        affichage = new BorderPane();
        affichage.setCenter(new Label(
                "Si 3 cases noires remplissent une zone de 4x4, la case restante appartient forcément à une île."));
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
                if (m.get(pos) == 1) {
                    Zone zone = new Zone(new Matrice(m.getElements()));
                    List<Position> listePositions = zone.findZone(pos);
                    System.out.println(listePositions);
                    for (Position p : listePositions) {
                        p.getVoisins().forEach(voisin -> {
                            if (m.posValide(voisin)) {
                                if (Etat.fromInt(m.get(voisin)) == Etat.BLANC) {
                                    if (!resList.contains(voisin))
                                        resList.add(voisin);
                                }
                            }
                        });
                    }
                    if (!resList.isEmpty()) return new Resultat(true, resList, affichage);
                }
            }
        }
        return new Resultat(false, null, new BorderPane(new Label("Aucune aide disponible")));
    }
}
