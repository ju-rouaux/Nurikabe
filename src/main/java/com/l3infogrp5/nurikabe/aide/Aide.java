package com.l3infogrp5.nurikabe.aide;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

/**
 * Classe permettant de lancer les algorithmes d'aide à la résolution.
 * Classe non instanciable.
 * Toujours vérifier que le résultat rénvoyé est un succès avant d'utiliser les
 * autres
 * méthodes.
 * 
 * @author Julien Rouaux
 */
public class Aide {

    /**
     * Constructeur privé pour empêcher l'instanciation de la classe.
     */
    private Aide() {
    }

    /**
     * Liste des algorithmes ne nécessitant pas de prétraitement réalisé
     * par Zone.
     */
    private static List<Algorithme> algos_simple = List.of(
            new Num1(),
            new BlancEntoure(),
            new Agregat3CasesNoires(),
            new CasesInatteignables(),
            new IleCompletee(),
            new Num2(),
            new ExpansionIle(),
            new ExpansionFleuve()
    );

    /**
     * Liste des algorithmes nécessitant un prétraitement réalisé par Zone.
     */
    private static List<Algorithme> algos_preprocessed = List.of(
        new NumVoisins(),
        new NumDiagonales()
    );

    /**
     * Exécute les algorithmes d'aide à la résolution et retourne le résultat.
     * 
     * @param m la matrice sur laquelle l'aide est demandée.
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
        return new Resultat(false, null, new BorderPane(new Label("test")));
    }

    /**
     * Vérifie si une case est numérique.
     * 
     * @param m la matrice dans laquelle la case est.
     * @param pos la position de la case.
     * @return true si la case est numérique, false sinon.
     */
    public static boolean isNum(Matrice m, Position pos) {
        return (Etat.fromInt(m.get(pos)) == Etat.NUMERIQUE);
    }

    /**
     * Vérifie si une case est numérique et après prétraitée.
     * 
     * @param m la matrice dans laquelle la case est.
     * @param pos la position de la case.
     * @return true si la case est numérique et non prétraitée, false sinon.
     */
    public static boolean isNumPreproc(Matrice m, Position pos) {
        return (m.get(pos) != 999 && m.get(pos) != -999);
    }
}
