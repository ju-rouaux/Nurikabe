package com.l3infogrp5.nurikabe.aide;

import com.l3infogrp5.nurikabe.utils.Matrice;

/**
 * Interface représentant un algorithme d'aide à la résolution.
 * 
 * @author Julien Rouaux
 */
interface Algorithme {

    /**
     * Exécute l'algorithme et retourne le résultat.
     * @return le résultat de l'algorithme.
     */
    public Resultat resoudre(Matrice m);
}
