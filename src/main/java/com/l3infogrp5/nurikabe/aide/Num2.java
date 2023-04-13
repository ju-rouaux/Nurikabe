package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Classe implémentant l'algorithme de la case NUMÉRIQUE 2 entouré de deux cases
 * 
 * Si un case numérique avec la valeur 2 est entouré dans sa diagonale de deux
 * cases noires,
 * la case diagonale opposée doit devenir noire
 *
 * @author Elias OKAT
 */
class Num2 implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme
     */
    public Num2 (){
        affichage = new BorderPane();
        // Image
        ImageView img = new ImageView("/img/aide/num2.png");
        // Taille de l'image
        img.setFitWidth(100);
        img.setFitHeight(100);
        // Ajouter l'image à gauche et la centrer verticalement
        affichage.setLeft(img);
        BorderPane.setAlignment(img, Pos.CENTER);
        // Aouter une marge autour de l'image
        BorderPane.setMargin(img, new Insets(10));
        //Ajouter le texte
        affichage.setCenter(new Label(
                        "Si un chiffre 2 est entouré dans sa diagonale de deux cases noires,\nla case diagonale opposée doit devenir une case noire."));
    }
    /**
     * Résoud l'algorithme de la case NUMÉRIQUE 2 entouré de deux cases noires
     *
     * @param m la matrice à traiter
     * @return la première valeur de la liste des positions des cases qui doivent
     *         devenir noires
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

            // Si la case 2 est bloquée par deux bordures, sa seule diagonale doit devenir
            // noire
            if (diagonales_valide.size() == 1) {
                Position diag_pos = diagonales_valide.get(0);

                case_noire.add(diag_pos);
            } else {

                // On cherche les cases noires autour de la case 2
                ArrayList<Position> voisin_cases_noires = new ArrayList<>();
                ArrayList<Position> case_voisine_invalide = new ArrayList<>();

                for (Position voisin : case_2.getVoisins()) {
                    if (m.posValide(voisin)) {
                        if (m.get(voisin) == -1) {
                            voisin_cases_noires.add(voisin);
                        }
                    } else {
                        // si la case voisine n'est pas valide, on la supprime de la liste
                        case_voisine_invalide.add(voisin);
                    }
                }

                // si il y a une case noire autour de la case 2 et un mur
                if ((voisin_cases_noires.size() == 1) && (case_voisine_invalide.size() == 1)) {

                    // si la case noire est sur la même diagonale que la case voisine invalide
                    if (voisin_cases_noires.get(0).getDiagonales().contains(case_voisine_invalide.get(0))) {
                        // case_noire.add(case_voisine_invalide.get(0));

                        // on cherche l'opposé de la case voisine invalide
                        Position oposite = new Position(0, 0);

                        ArrayList<Position> diagonales_case_2 = (ArrayList<Position>) case_2.getDiagonales();
                        ArrayList<Position> voisin_case_noire = (ArrayList<Position>) voisin_cases_noires.get(0)
                                .getVoisins();
                        ArrayList<Position> voisin_case_invalide = (ArrayList<Position>) case_voisine_invalide.get(0)
                                .getVoisins();

                        diagonales_case_2.retainAll(voisin_case_noire);
                        diagonales_case_2.retainAll(voisin_case_invalide);

                        oposite = getOposite(diagonales_case_2.get(0), case_2);

                        if (m.posValide(oposite)) {

                            if ((m.get(oposite) < 1) && (m.get(oposite) != -1)) {
                                case_noire.add(oposite);
                            }

                        }

                    }
                }

                // si il y a moins de 2 cases noires autour de la case 2, on ne fait rien
                if ((voisin_cases_noires.size() >= 2)) {

                    // on cherche les cases diagonales de la case 2
                    for (Position diag_pos : case_2.getDiagonales()) {
                        if (m.posValide(diag_pos)) {
                            Position oposite = getOposite(diag_pos, case_2);

                            // si la case diagonale est entourée de 2 cases noire (-1)

                            int count = 0;
                            for (Position voisin_case_diag : diag_pos.getVoisins()) {
                                if (m.posValide(voisin_case_diag)) {
                                    if (m.get(voisin_case_diag) == -1) {
                                        count++;
                                    }
                                }
                            }

                            if ((count >= 2) && (diag_pos.getVoisins().containsAll(voisin_cases_noires))) {
                                if (m.posValide(oposite)) {
                                    if ((m.get(oposite) < 1) && (m.get(oposite) != -1)) {
                                        case_noire.add(oposite);
                                    }
                                }
                            }

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
        return new Resultat(true, case_noire.subList(0, 1),affichage);
    }

    private Position getOposite(Position diagonales, Position case_centrale) {
        Position oposite = new Position(0, 0);

        // on cherche l'opposé de la case diagonale
        if (diagonales.equals(case_centrale.getNE())) {
            // l'opposé de NE est SO
            oposite = case_centrale.getSO();
        } else if (diagonales.equals(case_centrale.getNO())) {
            // l'opposé de NO est SE
            oposite = case_centrale.getSE();
        } else if (diagonales.equals(case_centrale.getSE())) {
            // l'opposé de SE est NO
            oposite = case_centrale.getNO();
        } else if (diagonales.equals(case_centrale.getSO())) {
            // l'opposé de SO est NE
            oposite = case_centrale.getNE();
        }

        return oposite;
    }

}