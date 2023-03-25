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

    private Matrice matrice;
    private List<Position> zone;

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
     * Méthode permettant de supprimer une position de la zone.
     * 
     * @return la liste des positions formant la zone.
     */
    public List<Position> getZone() {
        return this.zone;
    }

    /**
     * Méthode permettant de savoir si une position est dans la zone.
     * 
     * @param pos la position à tester.
     * @return true si la position est dans la zone, false sinon.
     */
    public boolean isInZone(Position pos) {
        return this.zone.indexOf(pos) > -1;
    }

    /**
     * Méthode permettant de savoir si la zone est vide.
     * 
     * @return true si la zone est vide, false sinon.
     */
    public boolean isEmpty() {
        return this.zone.isEmpty();
    }

    /**
     * Méthode permettant de supprimer une position de la zone.
     * 
     * @param pos la position à supprimer.
     */
    public void removeFromZone(Position pos) {
        this.zone.remove(pos);
    }

    /**
     * Méthode permettant de trouver un chemin existant à partir d'une position.
     * 
     * @param pos la position à tester.
     * @return La liste des positions formant la zone.
     */
    public List<Position> findZone(Position pos) {
        this.zone = new ArrayList<Position>();
        findZoneRecursive(pos);
        this.zone.add(pos);
        return this.zone;
    }

    /**
     * Méthode permettant de trouver un chemin existant à partir d'une position.
     * 
     * @param pos la position à tester.
     * 
     */
    private List<Position> findZoneRecursive(Position pos) {
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
                    // On vérifie la position n'a pas deja été ajouté a la zone
                    if (zone.indexOf(checkVois.get(i)) == -1) {
                        zone.add(checkVois.get(i));
                        // Appel récursif pour récupérer les voisins similaires à la zone
                        findZoneRecursive(checkVois.get(i));
                    }
                }
            }

            // System.out.println(i + " : " + checkVois.get(i));
        }

        return this.zone;
    }

    /**
     * Méthode qui décrémente la valeur d'une case en fonction de la taille de la
     * zone.
     * 
     * @param matrice la matrice d'entiers dans laquelle on veut décrémenter la
     *                valeur.
     */
    public void decremente(Matrice matrice) {
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

            int valeur_decremente = matrice.get(position) - laZone.zone.size() + 1;
            List<Position> zone = laZone.getZone();
            // for (int i = 0; i < zone.size()-1; i++) {
            // matrice.set(zone.get(i) , valeur_decremente);
            // all_zone.add(zone.get(i));
            // }
            for (Position cell_in_zone : laZone.zone) {
                matrice.set(cell_in_zone, valeur_decremente);
                all_zone.add(cell_in_zone);
                this.removeFromZone(cell_in_zone);
            }

            // all_zone.add(position);
            // matrice.set(position, valeur_decremente);

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

}