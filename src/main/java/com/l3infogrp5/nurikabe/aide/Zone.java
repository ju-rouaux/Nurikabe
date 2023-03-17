package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

/**
 * Classe permettant de trouver un chemin existant à partir d'une position
 * On parcourt la matrice à la recherche de tous les voisins possédant la même
 * référence que la case
 * à la position donnée.
 * 
 * @author Elias Okat, Killian Rattier
 */
public class Zone {

    Matrice matrice;
    List<Position> zone;

    /**
     * Crée une zone vide dans une matrice donnée.
     * 
     * @param matrice la matrice d'entiers que l'on veut tester.
     */
    public Zone(Matrice matrice) {
        this.matrice = matrice;
        this.zone = new ArrayList<Position>();
    }

    /**
     * Méthode permettant de trouver un chemin existant à partir d'une position.
     * 
     * @param pos la position à tester.
     * 
     */
    public void findZone(Position pos) {

        List<Etat> etatInit = new ArrayList<>();
        etatInit.add(Etat.fromInt(matrice.get(pos)));

        if (etatInit.get(0) == Etat.NUMERIQUE)
            etatInit.add(Etat.POINT);

        List<Position> checkVois = pos.getVoisins();

        for (int i = 0; i < checkVois.size(); i++) {

            // On vérifie que le voisin à liste si la position est dans la matrice.
            if (matrice.posValide(checkVois.get(i))) {

                // On vérifie que le voisin est bien du même type de case.
                // if (Etat.fromInt(matrice.get(checkVois.get(i))).equals(etatInit)) {

                if (etatInit.indexOf(Etat.fromInt(matrice.get(checkVois.get(i)))) > -1) {
                    // On vérifie la position n'a pas déja été ajouté a la zone
                    if (zone.indexOf(checkVois.get(i)) == -1) {
                        zone.add(checkVois.get(i));
                        // Appel récusif pour récupérer les voisins similaires à la zone
                        findZone(checkVois.get(i));
                    }
                }
            }

            // System.out.println(i + " : " + checkVois.get(i));
        }
    }

    /**
     * Decremente la valeur d'une case en fonction de la taille de la zone.
     * 
     * @param matrice la matrice d'entiers dans laquelle on veut décrémenter la
     *                valeur.
     */
    public void Decremente(Matrice matrice) {
        List<Position> num_pos = new ArrayList<>();
        List<Position> all_zone = new ArrayList<>();

        for (int i = 0; i < matrice.getNbColonne(); i++) {
            for (int j = 0; j < matrice.getNbLigne(); j++) {
                Position pos = new Position(j, i);

                if (matrice.posValide(pos)) {
                    if (Etat.fromInt(matrice.get(pos)) == Etat.NUMERIQUE) {
                        num_pos.add(pos);
                    }
                }
            }
        }

        for (Position position : num_pos) {
            Zone laZone = new Zone(matrice);
            laZone.findZone(position);

            System.out.println("Val pos : " + matrice.get(position) + "\nTaille zone : " + laZone.zone.size()
                    + "\nValeur dec :" + (matrice.get(position) - laZone.zone.size()));

            int valeur_decremente = matrice.get(position) - laZone.zone.size();

            for (Position cell_in_zone : laZone.zone) {
                matrice.set(cell_in_zone, valeur_decremente);
                all_zone.add(cell_in_zone);
            }

            all_zone.add(position);
            matrice.set(position, valeur_decremente);

        }

        for (int i = 0; i < matrice.getNbColonne(); i++) {
            for (int j = 0; j < matrice.getNbLigne(); j++) {
                Position pos = new Position(j, i);

                if (matrice.posValide(pos)) {
                    if (all_zone.indexOf(pos) == -1) {
                        if (Etat.fromInt(matrice.get(pos)) == Etat.NOIR) {
                            matrice.set(pos, -999);
                        }

                        if (Etat.fromInt(matrice.get(pos)) == Etat.BLANC) {
                            matrice.set(pos, 999);
                        }
                    }
                }
            }
        }

        System.out.println("--------");
        System.out.println(matrice);
    }

    public static void main(String[] args) {
        Matrice grille = new Matrice(new int[][] {
                { -1, 0, 3, 0, 0, 0, -2 },
                { -1, 0, -2, -2, 0, 0, 0 },
                { -1, 0, -2, -2, 0, 0, 0 },
                { -1, 0, 0, 0, -2, 0, 0 },
                { -1, -1, 0, 0, 0, 0, 0 },
                { 0, -1, 0, 0, 0,  0, 0 },
                { 0, 0, -1, 0, 0, -2, 3 },
                { 0, 0, 0, 0, 0,  -2, 0 },
                { 0, 0, 0, 0, 0,  10, 0 },
                { 0, -2, 5, -2, 0, 0, 0 },
                { 0, 0, 0, 0, 0,   0, 0 },
                { 0, 0, 0, 0, 0,   0, 0 },
        });

        System.out.println("nombre_ligne : " + grille.getNbLigne());

        System.out.println("nombre_colonne : " + grille.getNbColonne());

        System.out.println("Grille avant affichage des zone :\n");
        System.out.println(grille);
        System.out.println("\n");
        Position pos = new Position(0, 0);

        Zone laZone = new Zone(grille);
        laZone.findZone(pos);
        // System.out.println(laZone.zone);
        // System.out.println("Taille : "+laZone.zone.size());

        System.out.println("Grille après affichage des zone :\n");
        laZone.Decremente(grille);
    }
}
