package com.l3infogrp5.nurikabe.aide;

import java.util.List;
import java.util.ArrayList;

import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Classe implémentant l'algorithme d'aide à la résolution indiquant comment
 * connecter deux fleuves
 * 
 * @author Killian Rattier
 */
public class ExpansionFleuve implements Algorithme {
    BorderPane affichage;

    /**
     * Constructeur de l'algorithme.
     */
    public ExpansionFleuve() {
        affichage = new BorderPane();
        affichage.setCenter(new Label(
                "Les cases noires doivent être reliées en un seul chemin continu. Si une case noire ne peut se connecter qu'à un seul chemin, elle doit être prolongée pour se connecter aux autres."));
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

        for (int i = 0; i < m.getNbLignes(); i++) {
            for (int j = 0; j < m.getNbColonnes(); j++) {
                Position pos = new Position(i, j);
                if (m.get(pos) == 1) { // si la case est un mur
                    if (!estConnecte(m, pos)) { // si le mur n'est pas connecté
                        List<Position> casesAdjacentes = pos.getVoisins(); // on récupère les cases adjacentes
                        List<Position> casesAdjacentesLibres = new ArrayList<>(); // on crée une liste pour stocker les
                                                                                  // cases adjacentes libres

                        // on parcourt les cases adjacentes pour trouver celles qui sont libres
                        for (Position caseAdjacente : casesAdjacentes) {
                            if (m.posValide(caseAdjacente) && m.get(caseAdjacente) == 0) {
                                casesAdjacentesLibres.add(caseAdjacente);
                            }
                        }

                        if (casesAdjacentesLibres.size() == 3) { // si on a trouvé 3 cases adjacentes libres, on les
                                                                 // ajoute à la liste des cases à ajouter
                            resList.addAll(casesAdjacentesLibres);
                        }
                    }
                }
            }
        }

        if (!resList.isEmpty()) {
            return new Resultat(true, resList, affichage);
        }
        return new Resultat(false, null, new BorderPane(new Label("Aucune aide disponible")));
    }

    /**
     * Vérifie si le mur à la position donnée est connecté à d'autres murs
     * 
     * @param matrice la matrice du jeu Nurikabe
     * @param pos     la position du mur
     * @return vrai si le mur est connecté à d'autres murs, faux sinon
     */
    private static boolean estConnecte(Matrice matrice, Position pos) {
        List<Position> casesAdjacentes = pos.getVoisins();

        // on parcourt les cases adjacentes pour trouver un autre mur connecté
        for (Position caseAdjacente : casesAdjacentes) {
            if (matrice.posValide(caseAdjacente) && matrice.get(caseAdjacente) == 1 && estConnecteRec(matrice, caseAdjacente, new boolean[matrice.getNbLignes()][matrice.getNbColonnes()])) {
                return true;
            }
        }

        return false;
    }

    /**
     * Méthode récursive pour vérifier si une case est connectée à une autre case
     * 
     * @param m             la matrice
     * @param pos           la position de la case en cours de vérification
     * @param casesVisitees une liste des cases déjà visitées
     * @return vrai si la case est connectée à une autre case, faux sinon
     */
    private static boolean estConnecteRec(Matrice matrice, Position position, boolean[][] visite) {
        int ligne = position.getX();
        int colonne = position.getY();
    
        if (!matrice.posValide(position) || visite[ligne][colonne] || matrice.get(position) == 999) {
            return false;
        }
    
        visite[ligne][colonne] = true;
    
        // Vérifier si la case courante est un mur
        if (matrice.get(position) == 0) {
            boolean voisinMur = false;
    
            // Vérifier si la case courante a un mur comme voisin
            for (Position voisin : position.getVoisins()) {
                if (matrice.posValide(voisin) && matrice.get(voisin) == 0) {
                    voisinMur = true;
                    break;
                }
            }
    
            if (!voisinMur) {
                return false;
            }
        }
    
        // Explorer les voisins récursivement
        boolean connecte = false;
        for (Position voisin : position.getVoisins()) {
            connecte |= estConnecteRec(matrice, voisin, visite);
        }
    
        return connecte;
    }
    

}
