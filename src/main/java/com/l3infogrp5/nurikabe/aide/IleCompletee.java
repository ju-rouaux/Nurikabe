package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Cette classe implémente la technique "Entourer une île complétée".
 * Cette technique consiste à ajouter des murs horizontaux et verticaux autour
 * d'une île complétée,
 * c'est-à-dire une île dont toutes les cases adjacentes sont noires.
 *
 * @author Elias OKAT, Killian Rattier
 */
public class IleCompletee implements Algorithme {
    BorderPane affichage;
    
    /**
     * Constructeur de l'algorithme
     */
    public IleCompletee (){
        affichage = new BorderPane();
        // Image
        ImageView img = new ImageView("/img/aide/ileComplete.png");
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
                        "Si une île est complétée, toutes les cases adjacentes\nà l'île doivent être noires."));
    }

    /**
     * Implémentation de la méthode resoudre(Matrice m) de l'interface Algorithme.
     * Cette méthode prend une matrice en entrée, parcourt la matrice pour trouver
     * les îles complétées et ajoute des murs horizontaux et verticaux autour de ces
     * îles.
     * 
     * @param m La matrice à résoudre.
     * @return Un objet Resultat contenant le résultat de l'algorithme.
     */
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

            // On enlève les doublons de la zone de la case numérique
            ArrayList<Position> zone_case_num_distinct = (ArrayList<Position>) zone_case_num.stream().distinct()
                    .collect(Collectors.toList());

            // On vérifie si la zone de la case numérique est complétée
            if (m.get(case_num) - zone_case_num_distinct.size() == 0) {

                // On parcourt la liste des cases de la zone de la case numérique
                for (Position pos : zone_case_num_distinct) {
                    ArrayList<Position> voisins = (ArrayList<Position>) pos.getVoisins();

                    // On vérifie si les cases adjacentes de la zone de la case numérique sont
                    // noires
                    for (Position voisin : voisins) {
                        if ((m.posValide(voisin)) && ((m.get(voisin) == 0) || (m.get(voisin) == -2))) {

                            // On vérifie si la case adjacente n'est pas dans la zone de la case numérique
                            if (!zone_case_num.contains(voisin)) {

                                // si la case adjacente n'est pas une case noire, ou une case numérique, on
                                // l'ajoute à la liste des cases à modifier
                                if ((m.get(voisin) < 1) && (m.get(voisin) != -1)) {
                                    ile_non_complete.add(voisin);
                                }

                            }

                        }
                    }
                }

                // si la liste des cases à modifier n'est pas vide, on retourne un résultat vrai
                // avec la liste des cases à modifier
                if (!ile_non_complete.isEmpty()) {
                    return new Resultat(true, ile_non_complete,affichage);
                }

            }
        }

        // si la liste des cases à modifier est vide, on retourne un résultat faux
        return new Resultat(false, ile_non_complete,
                new BorderPane(new Label("")));

    }

}