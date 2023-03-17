package com.l3infogrp5.nurikabe.utils;

import java.util.ArrayList;

/**
 * Classe fournissant différentes méthodes utiles à la manipulation de matrices.
 * Les matrices ne peuvent pas changer de taille dynamiquement.
 * 
 * @author Elias Okat, Julien Rouaux
 */
public class Matrice implements Cloneable {
    private int[][] donnees;
    private int nombre_ligne;
    private int nombre_colonne;

    /**
     * Créer une matrice d'une taille donnée, remplie de zéros.
     * 
     * @param col nombre de colonnes.
     * @param row nombre de lignes.
     */
    public Matrice(int col, int row) {
        nombre_colonne = col;
        nombre_ligne = row;
        donnees = new int[nombre_colonne][nombre_ligne];
    }

    /**
     * Créer une nouvelle matrice à partir d'un tableau à deux dimensions.
     * La matrice crée est du même nombre de ligne que le tableau, et du même nombre
     * de colonnes que la première ligne du tableau.
     * Si une ligne du tableau contient moins de valeurs qu'une ligne de la matrice,
     * la matrice est complétée par des 0.
     * 
     * @param matrice matrice d'entiers.
     */
    public Matrice(int[][] matrice) {
        nombre_ligne = matrice.length;
        nombre_colonne = matrice[0].length;

        donnees = new int[nombre_ligne][nombre_colonne];
        for (int i = 0; i < nombre_ligne; i++)
            for (int j = 0; j < nombre_colonne; j++)
                if (j < matrice[i].length)
                    donnees[i][j] = matrice[i][j];
    }

    /**
     * Obtenir la valeur d'une cellule de la matrice.
     * 
     * @param l ligne.
     * @param c colonne.
     * @return la valeur de la cellule aux positions données.
     * @throws IndexOutOfBoundsException -
     */
    public int get(int l, int c) throws IndexOutOfBoundsException {
        return donnees[l][c];
    }

    /**
     * Obtenir la valeur d'une cellule de la matrice.
     * 
     * @param p position de la cellule.
     * @return la valeur de la cellule à la position donnée.
     */
    public int get(Position p) throws IndexOutOfBoundsException {
        return donnees[p.getX()][p.getY()];
    }

    /**
     * Changer la valeur d'une cellule de la matrice.
     * 
     * @param l      ligne.
     * @param c      colonne.
     * @param valeur valeur à définir.
     * @throws IndexOutOfBoundsException -
     */
    public void set(int l, int c, int valeur) throws IndexOutOfBoundsException {
        donnees[l][c] = valeur;
    }

    /**
     * Changer la valeur d'une cellule de la matrice.
     * 
     * @param p      position de la cellule dans la matrice.
     * @param valeur valeur à définir.
     * @throws IndexOutOfBoundsException -
     */
    public void set(Position p, int valeur) throws IndexOutOfBoundsException {
        donnees[p.getX()][p.getY()] = valeur;
    }

    /**
     * Retourne le nombre de lignes dans la matrice.
     * 
     * @return le nombre de lignes dans la matrice.
     */
    public int getNbLigne() {
        return nombre_ligne;
    }

    /**
     * Retourne le nombre de colonnes dans la matrice.
     * 
     * @return le nombre de colonnes dans la matrice.
     */
    public int getNbColonne() {
        return nombre_colonne;
    }

    /**
     * Retourne vrai si la position est contenue dans la matrice.
     * 
     * @param p la position à valider.
     * @return vrai si la position est contenue dans la matrice.
     */
    public boolean posValide(Position p) {
        return (p.getX() < this.nombre_ligne) && (p.getX() >= 0) && (p.getY() < this.nombre_colonne) && (p.getY() >= 0);
    }

    /**
     * 
     * Ré-implémentation de la méthode clone() de la classe Object.
     * TODO vérifier son fonctionnement
     * 
     * @throws CloneNotSupportedException si la classe n'est pas clonable.
     * 
     * 
     */
    @Override
    public Matrice clone() throws CloneNotSupportedException {
        Matrice matrice_clone = (Matrice) super.clone();
        matrice_clone.donnees = donnees.clone();
        for (int i = 0; i < donnees.length; i++) {
            matrice_clone.donnees[i] = donnees[i].clone();
        }
        return matrice_clone;
    }

    /**
     * Retourne une copie de la matrice.
     * 
     * @return retourne une copie de la matrice
     */
    public Matrice copy() {
        try {
            Matrice cloned = this.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (Matrice) null;
    }

    /**
     * Getter de la matrice
     * 
     * @return la matrice
     */
    public Matrice getMatrice() {
        return this;
    }

    /**
     * Remplie la matrice avec une valeur
     * 
     * @param valeur valeur avec laquelle remplir la matrice
     */
    public void fill(int valeur) {
        for (int i = 0; i < nombre_ligne; i++) {
            for (int j = 0; j < nombre_colonne; j++) {
                this.donnees[i][j] = valeur;
            }
        }
    }

    /**
     * Retourne la valeur maximale dans la matrice
     * 
     * @return max_valeur : La valeur maximale dans la matrice
     */
    public int max() {
        int max_valeur = Integer.MIN_VALUE;

        for (int i = 0; i < nombre_ligne; i++) {
            for (int j = 0; j < nombre_colonne; j++) {
                if (donnees[i][j] > max_valeur)
                    max_valeur = donnees[i][j];
            }
        }

        return max_valeur;
    }

    /**
     * Retourne la valeur minimale dans la matrice
     * 
     * @return min_valeur : La valeur minimale dans la matrice
     */
    public int min() {
        int min_valeur = Integer.MAX_VALUE;

        for (int i = 0; i < nombre_ligne; i++) {
            for (int j = 0; j < nombre_colonne; j++) {
                if (donnees[i][j] < min_valeur)
                    min_valeur = donnees[i][j];
            }
        }

        return min_valeur;
    }

    /**
     * Cette méthode retourne une matrice qui est la rotation de la matrice
     * originale.
     * 
     * La rotation est obtenue en transposant d'abord la matrice originale, puis en
     * effectuant une symétrie sur la matrice transposée par rapport à la diagonale
     * principale.
     * 
     * @return la matrice après rotation de 90°.
     */
    public Matrice rotate() {
        Matrice matrice_transpose = this.transpose();

        int i = 0;
        int k = nombre_ligne - 1;

        while (i < k) {
            for (int j = 0; j < nombre_colonne; j++) {
                int temp = matrice_transpose.get(j, i);
                matrice_transpose.set(j, i, matrice_transpose.get(j, k));
                matrice_transpose.set(j, k, temp);
            }

            i++;
            k--;
        }

        return matrice_transpose;
    }

    /**
     * Méthode qui retourne une liste de Matrice contenant les 4 rotation possible
     * de la matrice appelant la méthode
     * 
     * @return Une ArrayList de matrices
     */
    public ArrayList<Matrice> AllRotation() {

        ArrayList<Matrice> liste_de_rotations = new ArrayList<Matrice>();

        liste_de_rotations.add(this.copy());

        for (int i = 0; i < 3; i++) {
            Matrice buffer = liste_de_rotations.get(liste_de_rotations.size() - 1).rotate();
            liste_de_rotations.add(buffer);
        }

        return liste_de_rotations;
    }

    /**
     * Méthode qui retourne l'index de la valeur dans la matrice, comme si la
     * matrice était
     * un tableau à une dimension (ligne 1, ligne 2, ligne 3, etc...)
     * Exemple :
     * 1 2 3
     * 4 5 6
     * 7 8 9
     * 
     * 1 2 3 4 5 6 7 8 9
     * 
     * 
     * @param valeur la valeur a chercher dans la matrice
     * @return la position de la valeur dans la matrice comme si elle était un
     *         tableau à une dimension
     * 
     */
    public int getIndex1D(int valeur) {
        int index = -1;
        for (int i = 0; i < nombre_ligne; i++) {
            for (int j = 0; j < nombre_colonne; j++) {
                if (this.donnees[i][j] == valeur)
                    index = to1D(i, j);
            }
        }
        return index;
    }

    /**
     * Méthode qui retourne les coordonnées de la valeur dans la matrice
     * 
     * @param valeur la valeur a chercher dans la matrice
     * 
     * @return coords : retourne un tableau d'entier avec les coordonnée dans la
     *         matrice de la valeur recherché
     */
    public int[] getIndex2D(int valeur) {
        int[] coords = new int[2];
        coords[0] = -1;
        coords[1] = -1;

        for (int i = 0; i < nombre_ligne; i++) {
            for (int j = 0; j < nombre_colonne; j++) {
                if (this.donnees[i][j] == valeur) {
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }
        return coords;
    }

    /**
     * Convertit les coordonnées d'une matrice en un index en un seul dimension.
     * 
     * @param index_ligne   la ligne de la matrice
     * @param index_colonne la colonne de la matrice
     * @return l'index en un seul dimension correspondant aux coordonnées de la
     *         matrice
     */
    public int to1D(int index_ligne, int index_colonne) {
        return index_ligne * this.nombre_colonne + index_colonne;
    }

    /**
     * Calcule la transposée d'une matrice.
     * 
     * @return une nouvelle matrice qui est la transposée de la matrice actuelle
     */
    public Matrice transpose() {
        Matrice matrice_transpose = new Matrice(nombre_colonne, nombre_ligne);

        for (int i = 0; i < nombre_ligne; i++) {
            for (int j = 0; j < nombre_colonne; j++) {
                matrice_transpose.donnees[j][i] = donnees[i][j];
            }
        }

        return matrice_transpose;
    }

    /**
     * Calcule la moyenne de toutes les valeurs dans la matrice.
     * 
     * @return la moyenne de toutes les valeurs dans la matrice
     */
    public double mean() {
        double somme = 0.0;
        int compteur = 0;
        for (int i = 0; i < nombre_ligne; i++) {
            for (int j = 0; j < nombre_colonne; j++) {
                somme += donnees[i][j];
                compteur++;
            }
        }
        return somme / compteur;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object m) {
        if (!(m instanceof Matrice))
            return false;

        Matrice m2 = (Matrice) m;
        if (m2.nombre_colonne != this.nombre_colonne || m2.nombre_ligne != this.nombre_ligne)
            return false;

        for (int i = 0; i < nombre_ligne; i++)
            for (int j = 0; j < nombre_colonne; j++)
                if (this.donnees[i][j] != m2.donnees[i][j])
                    return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < nombre_ligne; i++) {
            for (int j = 0; j < nombre_colonne; j++)
                str.append(this.donnees[i][j] + " ");
            str.append("\n");
        }

        return str.toString();
    }

    // TODO : à corriger
    /**
     * Méthode qui remplace toutes les occurences d'une valeur dans la matrice par
     * une nouvelle valeur
     * 
     * @param old_value la valeur à remplacer
     * @param new_value la nouvelle valeur
     */
    public void replaceAll(int old_value, int new_value) {
        for (int i = 0; i < nombre_colonne; i++) {
            for (int j = 0; j < nombre_ligne; j++) {
                if (this.get(i, j) == old_value)
                    this.set(i, j, new_value);
            }
        }
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
