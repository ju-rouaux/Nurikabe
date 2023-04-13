package com.l3infogrp5.nurikabe.aide;

import java.util.List;
import java.util.ArrayList;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * 
 * Classe implémentant l'algorithme d'aide à la résolution correspondant à la technique d'expansion de fleuve.
 * 
 * Si une île n'est pas complète et qu'il y a une seule case blanche adjacente à l'île, il faut la prolonger sur cette case blanche.
 * 
 * @author Killian Rattier, Elias OKAT
 */
public class ExpansionFleuve implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public ExpansionFleuve() {
        affichage = new BorderPane();
        // Image
        ImageView img = new ImageView("/img/aide/expNoir.png");
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
                "Les cases noires doivent être reliées en un seul chemin continu. \nSi une case noire ne peut se connecter qu'à un seul chemin,\n elle doit être prolongée pour se connecter aux autres."));
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

        // On crée une liste de positions des cases noires
        List<Position> cases_noire = new ArrayList<>();

        // On parcourt la matrice pour trouver les cases noires
        for (int i = 0; i < m.getNbLignes(); i++) {
            for (int j = 0; j < m.getNbColonnes(); j++) {
                if (m.get(new Position(i, j)) == Etat.NOIR.toInt()) {
                    cases_noire.add(new Position(i, j));
                }
            }
        }

        // On parcourt la liste des cases noires
        for (Position pos : cases_noire) {
            // On trouve la zone de la case noire
            Zone zone = new Zone(m);
            List<Position> case_noire_zone = zone.findZone(pos);

            // On fait appel à la classe SousAlgo pour trouver les cases blanches adjacentes à la zone
            List<Position> case_blanche_adj = SousAlgo.case_blanche_adjacentes(m, case_noire_zone);


            // On parcourt la liste des cases blanches adjacentes
            if (case_blanche_adj.size() == 1) {
                // Si la liste contient une seule case blanche adjacente, on l'ajoute à la liste des cases à ajouter
                resList.add(case_blanche_adj.get(0));
            }
        }

        if (!resList.isEmpty()) {
            return new Resultat(true, resList.subList(0, 1), affichage);
        }

        return new Resultat(false, null, new BorderPane(new Label("")));
    }

}
