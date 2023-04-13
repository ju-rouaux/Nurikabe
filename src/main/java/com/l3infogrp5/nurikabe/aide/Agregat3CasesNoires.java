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
 * Classe implémentant l'algorithme d'aide à la résolution lorsque l'on a un
 * agrégat de 3 cases NOIR
 * 
 * @author Killian Rattier, Elias Okat
 */
public class Agregat3CasesNoires implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public Agregat3CasesNoires() {
        affichage = new BorderPane();
        // Image
        ImageView img = new ImageView("/img/aide/agregatNoir.png");
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
                "Si 3 cases noires remplissent une zone de 4x4,\nla case restante appartient forcément à une île."));
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

        // On déclare le pattern à détecter
        int[][] pattern = new int[][] { { Etat.NOIR.toInt(), Etat.NOIR.toInt() },
                { Etat.NOIR.toInt(), Etat.BLANC.toInt() } };
        PatternDetector pd = new PatternDetector(pattern);

        // On détecte le pattern dans notre matrice
        ArrayList<Position> pos_res = pd.detectInGrid(m.getElements());

        // On compte le nombre de cases noires
        int cases_noir = 0;

        // Pour chaque position dans notre pattern
        for (Position p : pos_res) {
            // Si la position est blanche on l'ajoute aux résultats
            // Sinon on incrémente le nombre de cases noires
            if (Etat.fromInt(m.get(p)) == Etat.NOIR) {
                cases_noir++;
            } else if (Etat.fromInt(m.get(p)) == Etat.BLANC) {
                resList.add(p);
            } 
        }

        // Si la liste n'est pas vide
        if(!resList.isEmpty()){
            // Si on a bien 3 cases noires on renvoie le résultat
            if (cases_noir == 3) {
                return new Resultat(true, resList, this.affichage);
            }
        }



        return new Resultat(false, null, new BorderPane(new Label("Aucune aide disponible")));
    }
}