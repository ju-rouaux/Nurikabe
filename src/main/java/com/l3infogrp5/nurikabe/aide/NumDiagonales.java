package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;

import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Classe implémentant l'algorithme d'aide à la résolution lorsque l'on a 2
 * cases NUM en diagonale
 * 
 * @author Elias Okat, Killian Rattier
 */
public class NumDiagonales implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public NumDiagonales() {
        affichage = new BorderPane();
        // Image
        ImageView img = new ImageView("/img/aide/numDiag.png");
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
                "Si deux cases numériques sont adjacentes en diagonale,\nles deux cases blanches de la diagonale opposée doivent être noircies."));
    }

    /**
     * Resouds l'algorithme d'aide dans une matrice donnée.
     * 
     * @param m la matrice à tester
     * @return la liste des cases trouvées par l'algorithme
     */
    @Override
    public Resultat resoudre(Matrice m) {

        int[][] patternA = {
                { 0, 666 },
                { 666, 0 }
        };

        int[][] patternB = {
                { -1, 666 },
                { 666, 0 }
        };

        ArrayList<Matrice> patternList = new ArrayList<Matrice>();
        patternList.add(new Matrice(patternA));
        patternList.add(new Matrice(patternB));

        ArrayList<Position> case_a_modifier = new ArrayList<Position>();

        for (Matrice pattern : patternList) {
            Matrice m2 = new Matrice(m.getElements());
            // On crée un PatternDetector avec le pattern
            PatternDetector patternDetector = new PatternDetector(pattern.getElements());

            // On détecte le pattern dans la matrice
            ArrayList<Position> positions = patternDetector.detectNumPreproc(m2.getElements());

            // On parcourt la liste des positions trouvées
            for (Position position : positions) {
                // On vérifie que la position est valide
                if (m2.posValide(position)) {
                    // On ajoute les cases blanches dans la liste des cases à modifier
                    if (m2.get(position) == 999) {
                        case_a_modifier.add(position);
                    }
                }
            }

            // si la liste des cases à modifier n'est pas vide, on retourne un résultat vrai
            // avec la liste des cases à modifier
            if (!case_a_modifier.isEmpty()) {
                return new Resultat(true, case_a_modifier,
                        new BorderPane(new Label(
                                "Si deux cases numériques sont adjacentes en diagonale,\nles deux cases blanches de la diagonale opposée doivent être noircies.")));
            }

        }

        // si la liste des cases à modifier est vide, on retourne un résultat faux
        return new Resultat(false, case_a_modifier,
                new BorderPane(new Label("")));

    }

}