package com.l3infogrp5.nurikabe.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Classe fournissant différentes méthodes utiles à la manipulation de matrices.
 * Les matrices ne peuvent pas changer de taille dynamiquement.
 * 
 * @author Elias Okat, Julien Rouaux
 */
public class Matrice {
    private int lignes; // nombre de lignes de la matrice
    private int colonnes; // nombre de colonnes de la matrice
    private int[][] elements; // tableau contenant les éléments de la matrice

    /**
     * Crée une nouvelle matrice de la taille spécifiée.
     * Tous les éléments sont initialisés à 0.
     * Si les dimensions sont négatives, une exception est levée.
     * 
     * @param lignes   nombre de lignes de la matrice
     * @param colonnes nombre de colonnes de la matrice
     */
    public Matrice(int lignes, int colonnes) {
        if (lignes < 0 || colonnes < 0) {
            throw new IllegalArgumentException("Les dimensions de la matrice doivent être positives");
        }
        this.lignes = lignes;
        this.colonnes = colonnes;
        this.elements = new int[lignes][colonnes];
    }

    /**
     * Crée une nouvelle matrice à partir d'un tableau d'entiers à deux dimensions.
     * Si le tableau est vide, une exception est levée.
     * Si le tableau ne contient pas le même nombre de colonnes pour chaque ligne,
     * une exception est levée.
     *
     * @param elements tableau d'entiers à deux dimensions
     */
    public Matrice(int[][] elements) {
        int lignes = elements.length;
        if (lignes == 0) {
            throw new IllegalArgumentException("Le tableau ne contient aucune ligne");
        }
        int colonnes = elements[0].length;
        for (int i = 1; i < lignes; i++) {
            if (elements[i].length != colonnes) {
                throw new IllegalArgumentException(
                        "Le tableau ne contient pas le même nombre de colonnes pour chaque ligne");
            }
        }
        this.elements = new int[lignes][colonnes];
        for (int i = 0; i < lignes; i++) {
            System.arraycopy(elements[i], 0, this.elements[i], 0, colonnes);
        }
        this.lignes = lignes;
        this.colonnes = colonnes;
    }

    /**
     * Vérifie si la matrice est valide, c'est-à-dire si elle a des éléments non
     * nuls et une dimension supérieure à 0.
     * 
     * @return true si la matrice est valide, false sinon
     */
    public boolean estValide() {
        return this.elements != null && this.lignes > 0 && this.colonnes > 0;
    }

    /**
     * Méthode qui retourne vrai si la matrice est carrée (c'est-à-dire si le nombre
     * de lignes est égal au nombre de colonnes)
     * 
     * @return vrai si la matrice est carrée, faux sinon
     */
    public boolean estCarree() {
        return this.lignes == this.colonnes;
    }

    /**
     * Obtenir la valeur d'une cellule de la matrice.
     * 
     * @param p position de la cellule.
     * @return la valeur de la cellule à la position donnée.
     */
    public int get(Position p) throws IndexOutOfBoundsException {
        return elements[p.getX()][p.getY()];
    }

    /**
     * Changer la valeur d'une cellule de la matrice.
     * 
     * @param p      position de la cellule dans la matrice.
     * @param valeur valeur à définir.
     * @throws IndexOutOfBoundsException -
     */
    public void set(Position p, int valeur) throws IndexOutOfBoundsException {
        elements[p.getX()][p.getY()] = valeur;
    }

    /**
     * Retourne vrai si la position est contenue dans la matrice.
     * 
     * @param p la position à valider.
     * @return vrai si la position est contenue dans la matrice.
     */
    public boolean posValide(Position p) {
        return (p.getX() < this.getNbLignes()) && (p.getX() >= 0) && (p.getY() < this.getNbColonnes())
                && (p.getY() >= 0);
    }

    /**
     * Retourne une copie de la matrice courante.
     * 
     * @return une copie de la matrice courante
     */
    public Matrice getMatrice() {
        return this;
    }

    /**
     * Retourne le nombre de lignes de la matrice.
     * 
     * @return le nombre de lignes de la matrice.
     */
    public int getNbLignes() {
        return lignes;
    }

    /**
     * Retourne le nombre de colonnes de la matrice.
     * 
     * @return le nombre de colonnes de la matrice.
     */
    public int getNbColonnes() {
        return colonnes;
    }

    /**
     * Retourne la valeur de l'élément à la position (i, j) dans la matrice.
     * 
     * @param i la position de la ligne de l'élément.
     * @param j la position de la colonne de l'élément.
     * @return la valeur de l'élément à la position (i, j) dans la matrice.
     */
    public int getElement(int i, int j) {
        return elements[i][j];
    }

    /**
     * Modifie la valeur de l'élément à la position (i, j) dans la matrice.
     * 
     * @param i      la position de la ligne de l'élément.
     * @param j      la position de la colonne de l'élément.
     * @param valeur la nouvelle valeur de l'élément.
     */
    public void setElement(int i, int j, int valeur) {
        elements[i][j] = valeur;
    }

    /**
     * Méthode permettant de récupérer une colonne de la matrice sous forme de liste
     * en fournissant son index.
     * 
     * @param numColonne numéro de la colonne à récupérer (commence à 0)
     * @return la colonne de la matrice
     */
    public ArrayList<Integer> getColonne(int numColonne) {
        ArrayList<Integer> colonne = new ArrayList<Integer>();
        for (int i = 0; i < lignes; i++) {
            colonne.add(getElement(i, numColonne));
        }
        return colonne;
    }

    /**
     * Méthode permettant de récupérer une ligne de la matrice sous forme de liste
     * en fournissant son index.
     * 
     * @param numLigne numéro de la ligne à récupérer (commence à 0)
     * @return la ligne de la matrice
     */
    public ArrayList<Integer> getLigne(int numLigne) {
        ArrayList<Integer> ligne = new ArrayList<Integer>();
        for (int j = 0; j < colonnes; j++) {
            ligne.add(getElement(numLigne, j));
        }
        return ligne;
    }

    /**
     * Méthode permettant de récupérer les lignes de la matrice qui sont égales à
     * une
     * valeur donnée.
     * 
     * @param valeur la valeur à rechercher dans la matrice.
     * @return la liste des lignes de la matrice qui sont égales à la valeur donnée.
     */
    public ArrayList<Integer> getLignesEqualto(int valeur) {
        ArrayList<Integer> lignesCorrespondante = new ArrayList<Integer>();

        for (int i = 0; i < getNbLignes(); i++) {
            if (getLigne(i).equals(new ArrayList<Integer>(Collections.nCopies(getNbColonnes(), valeur)))) {
                lignesCorrespondante.add(i);
            }
        }

        return lignesCorrespondante;
    }

    /**
     * Méthode permettant de récupérer les colonnes de la matrice qui sont égales à
     * une valeur donnée.
     * 
     * @param valeur la valeur à rechercher dans la matrice.
     * @return la liste des colonnes de la matrice qui sont égales à la valeur
     *         donnée.
     */
    public ArrayList<Integer> getColonesEqualto(int valeur) {
        ArrayList<Integer> colonesCorrespondante = new ArrayList<Integer>();

        for (int i = 0; i < getNbColonnes(); i++) {
            if (getColonne(i).equals(new ArrayList<Integer>(Collections.nCopies(getNbLignes(), valeur)))) {
                colonesCorrespondante.add(i);
            }
        }

        return colonesCorrespondante;
    }

    /**
     * Vérifie si une valeur est présente dans la matrice.
     * 
     * @param valeur la valeur à rechercher dans la matrice.
     * @return true si la valeur est présente dans la matrice, false sinon.
     */
    public boolean valeurPresente(int valeur) {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                if (elements[i][j] == valeur) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * Renvoie la valeur minimale dans la matrice.
     * 
     * @return la valeur minimale dans la matrice
     */
    public int min() {
        int min = this.elements[0][0];
        for (int i = 0; i < this.lignes; i++) {
            for (int j = 0; j < this.colonnes; j++) {
                if (this.elements[i][j] < min) {
                    min = this.elements[i][j];
                }
            }
        }
        return min;
    }

    /**
     * Renvoie la valeur maximale dans la matrice.
     * 
     * @return la valeur maximale dans la matrice
     */
    public int max() {
        int max = this.elements[0][0];
        for (int i = 0; i < this.lignes; i++) {
            for (int j = 0; j < this.colonnes; j++) {
                if (this.elements[i][j] > max) {
                    max = this.elements[i][j];
                }
            }
        }
        return max;
    }

    /**
     * Convertit une valeur de la matrice en un index à une dimension.
     * 
     * @param valeur la valeur à convertir en index
     * @return l'index correspondant à la valeur donnée, ou -1 si la valeur n'est
     *         pas trouvée
     */
    public int index1d(int valeur) {
        int index = -1;
        for (int i = 0; i < this.lignes; i++) {
            for (int j = 0; j < this.colonnes; j++) {
                if (this.elements[i][j] == valeur) {
                    index = i * this.colonnes + j;
                    break;
                }
            }
            if (index != -1) {
                break;
            }
        }
        return index;
    }

    /**
     * Convertit des coordonnées en deux dimensions en un index à une dimension.
     * 
     * @param row la ligne de la matrice
     * @param col la colonne de la matrice
     * @return l'index correspondant aux coordonnées données
     * @throws IndexOutOfBoundsException si les coordonnées données sont invalides
     */
    public int to1D(int row, int col) {
        if (row < 0 || col < 0 || row >= lignes || col >= colonnes) {
            throw new IndexOutOfBoundsException("Coordonnées invalides");
        }
        return row * colonnes + col;
    }

    /**
     * Recherche les coordonnées d'une valeur dans la matrice.
     * 
     * @param valeur la valeur à rechercher
     * @return un tableau d'entiers de deux éléments contenant les coordonnées de la
     *         valeur dans la matrice, ou null si la valeur n'est pas trouvée
     */
    public int[] index2d(int valeur) {
        int[] coordonnees = new int[2];
        for (int i = 0; i < this.lignes; i++) {
            for (int j = 0; j < this.colonnes; j++) {
                if (this.elements[i][j] == valeur) {
                    coordonnees[0] = i;
                    coordonnees[1] = j;
                    return coordonnees;
                }
            }
        }
        return null;
    }

    /**
     * Calcule l'addition de cette matrice avec une autre matrice de même
     * dimensions.
     * 
     * @param autre la matrice à additionner à cette matrice
     * @return la matrice résultant de l'addition de cette matrice avec l'autre
     *         matrice
     * @throws IllegalArgumentException si les matrices n'ont pas les mêmes
     *                                  dimensions
     */
    public Matrice additionner(Matrice autre) {
        if (this.lignes != autre.lignes || this.colonnes != autre.colonnes) {
            throw new IllegalArgumentException("Les matrices doivent avoir les mêmes dimensions");
        }

        Matrice resultat = new Matrice(lignes, colonnes);
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                resultat.setElement(i, j, this.getElement(i, j) + autre.getElement(i, j));
            }
        }

        return resultat;
    }

    /**
     * 
     * Calcule la multiplication de cette matrice avec une autre matrice compatible.
     * 
     * @param autre la matrice à multiplier avec cette matrice
     * @return la matrice résultant de la multiplication de cette matrice avec
     *         l'autre matrice
     * @throws IllegalArgumentException si le nombre de colonnes de cette matrice
     *                                  n'est pas égal au nombre de lignes de
     *                                  l'autre matrice
     */
    public Matrice multiplier(Matrice autre) {
        if (this.colonnes != autre.lignes) {
            throw new IllegalArgumentException(
                    "Le nombre de colonnes de la première matrice doit correspondre au nombre de lignes de la seconde matrice");
        }

        Matrice resultat = new Matrice(this.lignes, autre.colonnes);
        for (int i = 0; i < this.lignes; i++) {
            for (int j = 0; j < autre.colonnes; j++) {
                int somme = 0;
                for (int k = 0; k < this.colonnes; k++) {
                    somme += this.getElement(i, k) * autre.getElement(k, j);
                }
                resultat.setElement(i, j, somme);
            }
        }

        return resultat;
    }

    /**
     * Vérifie si deux matrices ont des tailles égales.
     *
     * @param matrice1 la première matrice
     * @param matrice2 la deuxième matrice
     * @return true si les matrices ont des tailles égales, false sinon
     */
    public static boolean memesTaille(Matrice matrice1, Matrice matrice2) {
        return (matrice1 != null && matrice2 != null &&
                matrice1.getNbLignes() == matrice2.getNbLignes() &&
                matrice1.getNbColonnes() == matrice2.getNbColonnes());
    }

    /**
     * Vérifie si deux matrices peuvent être multipliées.
     *
     * @param matrice1 la première matrice
     * @param matrice2 la deuxième matrice
     * @return true si les matrices peuvent être multipliées, false sinon
     */
    public static boolean multipliable(Matrice matrice1, Matrice matrice2) {
        return (matrice1 != null && matrice2 != null &&
                matrice1.getNbColonnes() == matrice2.getNbLignes());
    }

    /**
     * Effectue une rotation de 90 degrés de cette matrice dans le sens des
     * aiguilles d'une montre.
     * 
     * @return la matrice résultant de la rotation de 90 degrés de cette matrice
     * @throws IllegalArgumentException si la matrice appelant la fonction n'est pas
     *                                  carrée
     */
    public Matrice rotation90() {
        Matrice mat_cpy = new Matrice(this.getNbColonnes(), this.getNbLignes());

        for (int i = 0; i < this.getNbLignes(); i++) {
            for (int j = 0; j < this.getNbColonnes(); j++) {
                mat_cpy.setElement(j, this.getNbLignes() - i - 1, this.getElement(i, j));
            }
        }

        return mat_cpy;
    }

    /**
     * Calcule les 4 rotations possibles de cette matrice dans le sens des aiguilles
     * d'une montre.
     * 
     * @return une liste contenant les 4 matrices résultant de chaque rotation de 90
     *         degrés de cette matrice
     */
    public ArrayList<Matrice> rotations() {
        ArrayList<Matrice> rotations = new ArrayList<>();

        Matrice matriceActuelle = this;
        rotations.add(matriceActuelle);

        for (int i = 0; i < 3; i++) {
            Matrice rotation = matriceActuelle.rotation90();
            rotations.add(rotation);
            matriceActuelle = rotation;
        }

        return rotations;
    }

    /**
     * Remplit tous les éléments de la matrice avec la même valeur donnée en
     * paramètre.
     * 
     * @param valeur la valeur à utiliser pour remplir la matrice
     */
    public void remplir(int valeur) {
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                elements[i][j] = valeur;
            }
        }
    }

    /**
     * Remplace toutes les occurrences de la valeur donnée en paramètre par une
     * autre valeur.
     * 
     * @param valeur      la valeur à remplacer
     * @param nouvelleVal la nouvelle valeur à utiliser pour le remplacement
     */
    public void remplacerValeurs(int valeur, int nouvelleVal) {
        for (int i = 0; i < this.getNbLignes(); i++) {
            for (int j = 0; j < this.getNbColonnes(); j++) {
                if (this.getElement(i, j) == valeur) {
                    this.setElement(i, j, nouvelleVal);
                }
            }
        }
    }

    /**
     * Ajoute une ligne remplie de la valeur donnée en paramètre à la matrice.
     * 
     * @param valeur la valeur à utiliser pour remplir la nouvelle ligne
     */
    public void ajouterLigne(int valeur) {
        int[] nouvelleLigne = new int[this.colonnes];
        Arrays.fill(nouvelleLigne, valeur);
        int[][] nouveauxElements = Arrays.copyOf(this.elements, this.lignes + 1);
        nouveauxElements[this.lignes] = nouvelleLigne;
        this.elements = nouveauxElements;
        this.lignes++;
    }

    /**
     * Ajoute une colonne remplie de la valeur donnée en paramètre à la matrice.
     * 
     * @param valeur la valeur à utiliser pour remplir la nouvelle colonne
     */
    public void ajouterColonne(int valeur) {
        int[][] nouveauxElements = new int[this.lignes][this.colonnes + 1];
        for (int i = 0; i < this.lignes; i++) {
            System.arraycopy(this.elements[i], 0, nouveauxElements[i], 0, this.colonnes);
            nouveauxElements[i][this.colonnes] = valeur;
        }
        this.elements = nouveauxElements;
        this.colonnes++;
    }

    /**
     * 
     * Enlève la ligne spécifiée de la matrice.
     * 
     * @param index L'index de la ligne à enlever.
     * @throws IllegalArgumentException si l'index spécifié est invalide.
     */
    public void enleverLigne(int index) {
        if (index < 0 || index >= lignes) {
            throw new IllegalArgumentException("L'index de la ligne à supprimer est invalide");
        }
        int[][] nouvelleMatrice = new int[lignes - 1][colonnes];
        System.arraycopy(elements, 0, nouvelleMatrice, 0, index);
        System.arraycopy(elements, index + 1, nouvelleMatrice, index, lignes - index - 1);
        elements = nouvelleMatrice;
        lignes--;
    }

    /**
     * Enlève la colonne spécifiée de la matrice.
     * 
     * @param index L'index de la colonne à enlever.
     * @throws IllegalArgumentException si l'index spécifié est invalide.
     */
    public void enleverColonne(int index) {
        if (index < 0 || index >= colonnes) {
            throw new IllegalArgumentException("L'index de la colonne à supprimer est invalide");
        }
        int[][] nouvelleMatrice = new int[lignes][colonnes - 1];
        for (int i = 0; i < lignes; i++) {
            System.arraycopy(elements[i], 0, nouvelleMatrice[i], 0, index);
            System.arraycopy(elements[i], index + 1, nouvelleMatrice[i], index, colonnes - index - 1);
        }
        elements = nouvelleMatrice;
        colonnes--;
    }

    /**
     * Méthode qui retourne une chaîne de caractères représentant la matrice
     * 
     * @return une chaîne de caractères représentant la matrice
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.lignes; i++) {
            for (int j = 0; j < this.colonnes; j++) {
                sb.append(this.elements[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Méthode qui vérifie si une autre matrice est égale à cette matrice
     * 
     * @param m la matrice à comparer avec cette matrice
     * @return true si les matrices sont égales, false sinon
     */
    @Override
    public boolean equals(Object m) {
        if (!(m instanceof Matrice))
            return false;

        Matrice m2 = (Matrice) m;
        if (m2.getNbColonnes() != this.getNbColonnes() || m2.getNbLignes() != this.getNbLignes())
            return false;

        for (int i = 0; i < getNbLignes(); i++)
            for (int j = 0; j < getNbColonnes(); j++)
                if (this.elements[i][j] != m2.elements[i][j])
                    return false;

        return true;
    }

    /**
     * Méthode qui crée une copie de cette matrice
     * 
     * @return une copie de cette matrice
     */
    @Override
    public Matrice clone() {
        int[][] newElements = new int[lignes][colonnes];
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                newElements[i][j] = elements[i][j];
            }
        }
        return new Matrice(newElements);
    }

    /**
     * Méthode qui retourne la distance entre deux points (x1, y1) et (x2, y2)
     * 
     * @param x1 coordonnée x du premier point
     * @param y1 coordonnée y du premier point
     * @param x2 coordonnée x du deuxième point
     * @param y2 coordonnée y du deuxième point
     * 
     * @return la distance entre les deux points (x1, y1) et (x2, y2)
     */
    public int distance(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
    }

}
