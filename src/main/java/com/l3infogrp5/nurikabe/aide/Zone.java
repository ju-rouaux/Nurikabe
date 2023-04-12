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
     * Méthode permettant de reccuperer la zone
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
        return this.zone.contains(pos);
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
        
        if (!this.zone.contains(pos))
            this.zone.add(pos);
        
        return this.zone;
    }

    /**
     * Méthode permettant de trouver un chemin existant à partir d'une position.
     * 
     * @param pos la position de départ à tester.
     * @return La liste des positions formant la zone.
     */
    private List<Position> findZoneRecursive(Position pos) {

        // Récupération de l'état de la position de départ
        List<Etat> etatInit = new ArrayList<>();
        etatInit.add(Etat.fromInt(matrice.get(pos)));

        // Si l'état est NUMERIQUE on ajoute un point, si l'état est POINT on ajoute un numérique
        if (etatInit.get(0) == Etat.NUMERIQUE)
            etatInit.add(Etat.POINT);
        else if (etatInit.get(0) == Etat.POINT)
            etatInit.add(Etat.NUMERIQUE);
        
        // Récupération des positions voisines
        List<Position> checkVois = pos.getVoisins();

        // On parcourt les positions voisines pour voir si elles sont similaires à la position de départ
        for (Position vois : checkVois) {
            // On vérifie que le voisin à liste si la position est dans la matrice.
            if (matrice.posValide(vois)) {

                // On vérifie que le voisin est bien du même type de case.
                if (etatInit.contains(Etat.fromInt(matrice.get(vois)))) {
                    // On vérifie la position n'a pas deja été ajouté a la zone
                    if (!zone.contains(vois)) {
                        zone.add(vois);
                        // Appel récursif pour récupérer les voisins similaires à la zone
                        findZoneRecursive(vois);
                    }
                }
            }
        }
        return this.zone;
    }

    /**
     * Méthode qui décrémente la valeur d'une case en fonction de la taille de la
     * zone.
     * 
     * Important : Cette méthode modifie directement la matrice qui lui est passée en paramètre.
     * 
     * @param matrice la matrice d'entiers dans laquelle on veut décrémenter la valeur.
     */
    public void decremente(Matrice matrice) {
        // On initialise deux listes vides pour stocker les positions numériques et toutes les positions des zones trouvées.
        List<Position> num_pos = new ArrayList<>();
        List<Position> all_zone = new ArrayList<>();

        // On parcourt toutes les positions de la matrice.
        for (int i = 0; i < matrice.getNbColonnes(); i++) {
            for (int j = 0; j < matrice.getNbLignes(); j++) {
                Position pos = new Position(j, i);
                // On vérifie que la position est valide.
                if (matrice.posValide(pos)) {
                    // On vérifie que la position est numérique.
                    if (Etat.fromInt(matrice.get(pos)) == Etat.NUMERIQUE) {
                        num_pos.add(pos);
                    }
                }
            }
        }

        // Pour chaque position numérique, on cherche la zone à laquelle elle appartient.
        for (Position position : num_pos) {
            Zone laZone = new Zone(matrice);
            laZone.findZone(position);

            // On décrémente la valeur de chaque cellule dans la zone, en utilisant la taille de la zone plus un.
            int valeur_decremente = matrice.get(position) - laZone.zone.size() + 1;

            for (Position cell_in_zone : laZone.zone) {
                matrice.set(cell_in_zone, valeur_decremente);
                all_zone.add(cell_in_zone);
                this.removeFromZone(cell_in_zone);
            }
        }
        
        // On remplit la matrice avec des valeurs spéciales pour les cellules qui ne sont pas dans une zone.
        for (int i = 0; i < matrice.getNbColonnes(); i++) {
            for (int j = 0; j < matrice.getNbLignes(); j++) {
                Position pos = new Position(j, i);

                if (matrice.posValide(pos)) {
                    if (!all_zone.contains(pos)) {
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
    }

}

