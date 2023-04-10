package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

public class untest {

    public List<Position> ile(Matrice m) {
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

            ArrayList<Position> zone_case_num_distinct = (ArrayList<Position>) zone_case_num.stream().distinct().collect(Collectors.toList());

            System.out.println("\ncase num : "+m.get(case_num));
            System.out.println("Case dans la zone : " + zone_case_num_distinct);
            // System.out.println("taille de la zone : " + zone_case_num_distinct.size() + " valeur de la case : " + m.get(case_num) + " position de la case : " + case_num + "");
            if (m.get(case_num) - zone_case_num_distinct.size() == 0) {
                System.out.println("Ile completee a la position " + case_num + " valeur de la case : " + m.get(case_num) + " taille de la zone : " + zone_case_num_distinct.size());
            }

            for (Position pos : zone_case_num_distinct) {
                ArrayList<Position> voisins = (ArrayList<Position>) pos.getVoisins();

                for (Position voisin : voisins) {
                    if ((m.posValide(voisin)) && ((m.get(voisin) == 0) || (m.get(voisin) == -2))) {

                        if (!zone_case_num.contains(voisin)) {
                            ile_non_complete.add(voisin);
                        }

                    }
                }
            }

        }

        return ile_non_complete;
    }

    private void affiche_mat(Matrice m, Position pos, List<Position> list) {
        int maxLen = Integer.MIN_VALUE;
        for (int i = 0; i < m.getNbLignes(); i++) {
            for (int j = 0; j < m.getNbColonnes(); j++) {
                int len = Integer.toString(m.getElement(i, j)).length();
                if (len > maxLen) {
                    maxLen = len;
                }
            }
        }
        String format = "%" + (maxLen + 1) + "s";
        for (int i = 0; i < m.getNbLignes(); i++) {
            for (int j = 0; j < m.getNbColonnes(); j++) {
                String valStr = Integer.toString(m.getElement(i, j));
                String formattedVal = String.format(format, valStr);
                if (pos.equals(new Position(i, j))) {
                    System.out.print("\033[0;31m" + formattedVal + "\033[0m");
                } else if (list.contains(new Position(i, j))) {
                    System.out.print("\033[0;33m" + formattedVal + "\033[0m");
                } else {
                    System.out.print(formattedVal);
                }
            }
            System.out.println();
        }
    }

    public void test(int[][] grille) {
        Matrice m = new Matrice(grille);
        untest test = new untest();
        

        test.affiche_mat(m, new Position(0, 0), test.ile(m));
    }
    

    public static void main(String[] args) {
        int[][] grille1 = new int[][] {
            { 0, 0, 0, 0, 3, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, -2, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, -2, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } 
        };

        int[][] grille2 = new int[][] {
            { 0, 0, 0, 0, 4, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, -2, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, -2, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } 
        };

        untest test = new untest();
        System.out.println("Grille 1");
        test.test(grille1);
        System.out.println(" ");
        System.out.println("Grille 2");
        test.test(grille2);




    }
}