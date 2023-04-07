package com.l3infogrp5.nurikabe.aide;

import java.util.List;

import com.l3infogrp5.nurikabe.utils.Matrice;

public class Aide {
    /**
     * Liste des algorithmes ne nécessitant pas de prétraitement réalisé
     * par Zone.
     */
    private static List<Algorithme> algos_simple = List.of(
        new Exemple1() //, new Exemple2(), new Exemple3()...
    );

    /**
     * Liste des algorithmes nécessitant un prétraitement réalisé par Zone.
     */
    private static List<Algorithme> algos_preprocessed = List.of(
        new Exemple1()
    );
    
    /**
     * Exécute les algorithmes d'aide à la résolution et retourne le résultat.
     * 
     * @return le résultat des algorithmes d'aide à la résolution.
     */
    public static Resultat calculer(Matrice m) {
        Resultat resultat;

        for (Algorithme algo : algos_simple) {
            resultat = algo.resoudre(m.clone());
            if (resultat.getSucces())
                return resultat;
        }

        // Prétraitement des zones.
        Zone z = new Zone(m);
        z.decremente(m);
        
        for (Algorithme algo : algos_preprocessed) {
            resultat = algo.resoudre(m.clone());
            if (resultat.getSucces())
                return resultat;
        }

        // Si aucun algorithme n'a trouvé de solution.
        return new Resultat(false, null, null);
    }
}
