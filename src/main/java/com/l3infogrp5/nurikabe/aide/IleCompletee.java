package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import com.l3infogrp5.nurikabe.aide.Zone;

public class IleCompletee implements Algorithme {

    @Override
    public Resultat resoudre(Matrice m) {

        // On crée une liste de positions des cases numériques
        List<Position> cases_num = new ArrayList<>();

        // On crée une liste de positions des cases à modifier
        List<Position> ile_non_complete = new ArrayList<>();

        // On parcourt la matrice pour trouver les cases numériques avec la valeur 2
        for (int i = 0; i < m.getNbLignes(); i++) {
            for (int j = 0; j < m.getNbColonnes(); j++) {
                if (m.get(new Position(i, j)) > 0) {
                    cases_num.add(new Position(i, j));
                }
            }
        }

        // On parcourt la liste des cases numériques
        for (Position case_num : cases_num) {
            // On trouve la zone de la case numérique
            Zone zone = new Zone(m);
            ArrayList<Position> zone_case_num = (ArrayList<Position>) zone.findZone(case_num);

            ArrayList<Position> zone_case_num_distinct = (ArrayList<Position>) zone_case_num.stream().distinct()
                    .collect(Collectors.toList());

            System.out.println("\ncase num : " + m.get(case_num));
            System.out.println("Case dans la zone : " + zone_case_num_distinct);
            // System.out.println("taille de la zone : " + zone_case_num_distinct.size() + "
            // valeur de la case : " + m.get(case_num) + " position de la case : " +
            // case_num + "");
            if (m.get(case_num) - zone_case_num_distinct.size() == 0) {
                System.out.println("Ile completee a la position " + case_num + " valeur de la case : " + m.get(case_num)
                        + " taille de la zone : " + zone_case_num_distinct.size());

                for (Position pos : zone_case_num_distinct) {
                    ArrayList<Position> voisins = (ArrayList<Position>) pos.getVoisins();

                    for (Position voisin : voisins) {
                        if ((m.posValide(voisin)) && ((m.get(voisin) == 0) || (m.get(voisin) == -2))) {

                            if (!zone_case_num.contains(voisin)) {

                                if (m.get(voisin) == -1) {
                                    ile_non_complete.add(voisin);
                                }

                                // ile_non_complete.add(voisin);
                            }

                        }
                    }
                }



                // Sinon, on retourne un résultat vrai avec la liste des cases à modifier
                return new Resultat(true, ile_non_complete,
                        new BorderPane(new Label(
                                "Si une ile est completee, toutes les cases adjacente de l'ile doivent etre noires\n"
                                        + " sur la case " + case_num + " la valeur de la case est : " + m.get(case_num)
                                        + " \net il y a " + zone_case_num_distinct.size() + " cases dans la zone")));
            }
        }

        // // si la liste des cases à modifier est vide, on retourne un résultat faux
        // if (ile_non_complete.isEmpty()) {
        // return new Resultat(false, ile_non_complete,
        // new BorderPane(new Label("")));
        // }

        return new Resultat(false, ile_non_complete,
                new BorderPane(new Label("")));

    }

}