package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Si un case numérique 2 est entouré dans sa diagonale de deux cases noires, la case diagonale opposée doit devenir noire
 *
 * @author Elias OKAT
 */
class Num2 implements Algorithme {

    /**
     * Résoud l'algorithme de la case NUMÉRIQUE 2 entouré de deux cases noires
     *
     * @param m la matrice à traiter
     * @return la liste des positions des cases à modifier
     */
    @Override
    public Resultat resoudre(Matrice m) {

        // On crée une liste de positions des cases numériques
        List<Position> num2_List = new ArrayList<>();

        // On crée une liste de positions des cases à modifier
        List<Position> case_noire = new ArrayList<>();

        // On parcourt la matrice pour trouver les cases numériques avec la valeur 2
        for (int i = 0; i < m.getNbLignes(); i++) {
            for (int j = 0; j < m.getNbColonnes(); j++) {
                if (m.get(new Position(i, j)) == 2) {
                    num2_List.add(new Position(i, j));
                }
            }
        }

        // On parcourt la liste des cases numériques 
        for (Position case_2 : num2_List) {
            // On trouve les diagonales de la case 2
            ArrayList<Position> diagonales = (ArrayList<Position>) case_2.getDiagonales();
            ArrayList<Position> diagonales_valide = new ArrayList<>();

            // On parcourt les diagonales de la case 2
            for (Position diag : diagonales) {
                if (m.posValide(diag)) {
                    // si la diagonale n'est pas valide, on la supprime de la liste
                    diagonales_valide.add(diag);
                }
            }

            // Si la case 2 est bloquée par deux bordures, sa seule diagonale doit devenir noire
            if (diagonales_valide.size() == 1) {
                Position diag_pos = diagonales_valide.get(0);
                System.out.println("La case " + m.get(diag_pos) + " [" + diag_pos + "] doit devenir NOIR car la case 2 est bloquée par deux bordures");
                
                case_noire.add(diag_pos);
            } else {
                // on cherche les cases diagonales de la case 2
                for (Position diag_pos : case_2.getDiagonales()) {
                    if (m.posValide(diag_pos)) {
                        Position oposite = new Position(0, 0);

                        // on cherche l'opposé de la case diagonale
                        if (diag_pos.equals(case_2.getNE())) {
                            // l'opposé de NE est SO
                            oposite = case_2.getSO();
                        } else if (diag_pos.equals(case_2.getNO())) {
                            // l'opposé de NO est SE
                            oposite = case_2.getSE();
                        } else if (diag_pos.equals(case_2.getSE())) {
                            // l'opposé de SE est NO
                            oposite = case_2.getNO();
                        } else if (diag_pos.equals(case_2.getSO())) {
                            // l'opposé de SO est NE
                            oposite = case_2.getNE();
                        }

                        // si la case diagonale est entourée de 2 cases noire (-1)

                        int count = 0;
                        for (Position voisin_case_diag : diag_pos.getVoisins()) {
                            if (m.posValide(voisin_case_diag)) {
                                if (m.get(voisin_case_diag) == -1) {
                                    count++;
                                }
                            }
                        }

                        // si la case diagonale est entourée de 2 (ou plus dans certains cas particulié) cases noire (-1), l'opposé doit devenir noire
                        if (count >= 2) {
                            System.out.println("La case " + m.get(oposite) + " [" + oposite + "] doit devenir NOIR");
                            case_noire.add(oposite);
                        }

                    }
                }
            }
        }

        // si la liste des cases à modifier est vide, on retourne un résultat faux
        if (case_noire.isEmpty()) {
            return new Resultat(false, case_noire,
                    new BorderPane(new Label("")));
        }

        // Sinon, on retourne un résultat vrai avec la liste des cases à modifier
        return new Resultat(true, case_noire,
                new BorderPane(new Label("Si un chiffre 2 est entouré dans sa diagonale de deux cases noires, la case diagonale opposée doit devenir une case noire")));
    }

}