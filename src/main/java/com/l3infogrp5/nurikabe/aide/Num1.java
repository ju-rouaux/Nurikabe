package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

/**
 * Classe implémentant l'algorithme d'aide à la résolution lorsque qu'une case NUM est à 1
 * 
 * @author Killian Rattier, Elias Okat
 */
public class Num1 implements Algorithme {
    BorderPane affichage;
    
    /**
     * Constructeur de l'algorithme.
     */
    public Num1(){
        affichage = new BorderPane();
        // Image
        ImageView img = new ImageView("/img/aide/ile1.png");
        // Taille de l'image
        img.setFitWidth(100);
        img.setFitHeight(100);
        // Ajouter l'image à gauche et la centrer verticalement
        affichage.setLeft(img);
        BorderPane.setAlignment(img, Pos.CENTER);
        // Aouter une marge autour de l'image
        BorderPane.setMargin(img, new Insets(10));
        //Ajouter le texte
        Label texte = new Label("Si une case numérique vaut 1, vous pouvez entourer les 4 cases autour d'elle avec des cases noires.");
        texte.setWrapText(true);
        affichage.setCenter(texte);
    }

    /**
     * Resouds l'algorithme d'aide dans une matrice donnée.
     * @param m la matrice à tester
     * @return la liste des cases trouvées par l'algorithme
     */
    @Override
    public Resultat resoudre(Matrice m) {

        List<Position> resList = new ArrayList<>();

        // Pour chaque case de la matrice
        for (int x = 0; x < m.getNbLignes(); x++) {
            for (int y = 0; y < m.getNbColonnes(); y++) {

                Position pos_courante = new Position(x, y);

                // Si c'est une case numérique valant 1
                if (m.get(pos_courante) == 1) {
                    // On recupere les voisins au nord, sud, est et ouest
                    List<Position> pos = pos_courante.getVoisins();
                    for (Position p : pos) {
                        if (m.posValide(p)) {
                            if (m.get(p) == Etat.BLANC.toInt() || m.get(p) == Etat.POINT.toInt()) {
                                resList.add(p);
                            }
                        }
                    }
                }
                // On renvoie la liste des cases concernées
                if(!resList.isEmpty()) return new Resultat(true, resList, affichage);
            }
        }
        return new Resultat(false, null, new BorderPane(new Label("Aucune aide disponible")));
    }
}
