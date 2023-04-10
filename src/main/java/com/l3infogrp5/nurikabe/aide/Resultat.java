package com.l3infogrp5.nurikabe.aide;

import java.util.List;

import com.l3infogrp5.nurikabe.utils.Position;

import javafx.scene.layout.Pane;

/**
 * Classe représentant le résultat d'un algorithme d'aide à la résolution.
 * 
 * Toujours vérifier que le résultat est un succès avant d'utiliser les autres
 * méthodes.
 * 
 * @author Julien Rouaux
 */
public class Resultat {

    private boolean succes;
    private List<Position> position;
    private Pane affichage;

    /**
     * Constructeur de la classe.
     * 
     * @param succes    vrai si l'algorithme a trouvé une solution.
     * @param position  les positions des cases à modifier pour avancer dans la
     *                  résolution (null si non succes).
     * @param affichage un affichage à placer dans le volet dédié à l'aide (null si
     *                  non succes).
     */
    public Resultat(boolean succes, List<Position> position, Pane affichage) {
        this.succes = succes;
        this.position = position;
        this.affichage = affichage;
    }

    /**
     * Retourne vrai si l'algorithme a trouvé une solution. 
     * 
     * @return vrai si l'algorithme a trouvé une solution.
     */
    public boolean getSucces() {
        return succes;
    }

    /**
     * Retourne les positions des cases à modifier pour avancer dans la résolution.
     * Peut être null si le résultat n'est pas un succès.
     * 
     * @return les positions des cases à modifier pour avancer dans la résolution.
     */
    public List<Position> getPosition() {
        return position;
    }

    /**
     * Retourne un affichage à placer dans le volet dédié à l'aide.
     * Peut être null si le résultat n'est pas un succès.
     * 
     * @return un affichage à placer dans le volet dédié à l'aide.
     */
    public Pane getAffichage() {
        return affichage;
    }
}
