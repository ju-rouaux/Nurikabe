package com.l3infogrp5.nurikabe.aide;

import java.util.ArrayList;
import java.util.List;

import com.l3infogrp5.nurikabe.niveau.grille.Etat;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

/**
 * Classe permettant de détecter un pattern dans une Matrice
 * 
 * @author Elias Okat
 */
public class PatternDetector {
  private Matrice pattern; // le pattern à détecter
  private ArrayList<Integer> patternHash; // la liste des hash des rotations du pattern
  private int patternRowSize; // la taille d'une ligne du pattern
  private int patternColSize; // la taille d'une colone du pattern

  private ArrayList<Integer> subGridHashList; // la liste des hash des sous-grilles de la grille

  /**
   * Constructeur de la classe PatternDetector
   * 
   * @param pattern le pattern à détecter
   */
  public PatternDetector(int[][] pattern) {
    this.pattern = new Matrice(pattern);
    patternRowSize = pattern.length;
    patternColSize = pattern[0].length;
    patternHash = new ArrayList<Integer>(4);
    subGridHashList = new ArrayList<Integer>();

    // Ajouter les hash des rotations du pattern
    for (Matrice rotatedPattern : this.pattern.rotations()) {
      patternHash.add(ZHash.hash(rotatedPattern, rotatedPattern.getNbLignes(), rotatedPattern.getNbColonnes()));
    }

  }

  /**
   * Détecte le pattern dans la grille
   * 
   * @param grid la grille dans laquelle le pattern doit être détecté
   * @return une liste des positions de la première coccurence du pattern dans la
   *         grille
   */

  public ArrayList<Position> detectInGrid(int[][] grid) {
    // ArrayList <Position> patternPositions = new ArrayList <Position>();
    // int patternHashCount = 0;

    int gridRowSize = grid.length; // taille d'une ligne de la grille
    int gridColSize = grid[0].length; // taille d'une colone de la grille

    // Pour chaque sous-grille de la taille du pattern dans la grille, nous
    // vérifions si leur hash correspond à celui du pattern
    for (int i = 0; i <= gridRowSize - patternRowSize; i++) {
      for (int j = 0; j <= gridColSize - patternColSize; j++) {
        int[][] subGrid = new int[patternRowSize][patternColSize];
        // Copier les valeurs dans la sous-grille
        for (int k = 0; k < patternRowSize; k++) {
          for (int l = 0; l < patternColSize; l++) {
            subGrid[k][l] = grid[i + k][j + l];
          }
        }

        // Vérifier si le hash de la sous-grille correspond à celui du pattern
        Integer subGridHash = ZHash.hash(subGrid, patternRowSize, patternColSize);

        int index = patternHash.indexOf(subGridHash);
        subGridHashList.add(subGridHash);
        if (index > -1) {
          // Si oui, ajouter la position (i, j) à la liste des positions du pattern dans
          // la grille
          // parcour la sous grille
          ArrayList<Position> subGrid_pos_array = new ArrayList<Position>();

          for (int k = 0; k < patternRowSize; k++) {
            for (int l = 0; l < patternColSize; l++) {
              subGrid_pos_array.add(new Position(i + k, j + l));
            }
          }

          return subGrid_pos_array;
        }
      }
    }

    return new ArrayList<Position>();
  }

  /**
   * 
   * Cette méthode prend en paramètre une grille de nombres entiers et renvoie une
   * liste de positions où un certain motif spécifié par la variable "pattern" est
   * détecté dans la grille.
   * 
   * Important : pour que cette méthode fonctionne il est nécessaire de replacer
   * les case numérique par la valeur 666 dans le pattern.
   * 
   * exemple : int[][] pattern = { { 666, 0, 0 }, { 0, -1, 0 }, { 0, 0, 666 } };
   * 
   * 
   * Elle parcourt la grille à l'aide de boucles for pour remplacer tous les
   * éléments qui ne sont pas égaux à 0, -1 ou -2 par la valeur 666.
   * Elle affiche la grille modifiée ainsi que le motif recherché.
   * Enfin, elle renvoie les positions où le motif est détecté dans la grille en
   * appelant la méthode "detectInGrid".
   * 
   * @param grid une grille de nombres entiers dans laquelle chercher le motif
   * @return une liste de positions de la première occurrence où le motif est
   *         détecté dans la grille
   */
  public ArrayList<Position> detectNum(int[][] grid) {

    // si le pattern ne contient aucune valeur 666, on ne peut pas le détecter dans
    // la grille
    if (!(pattern.valeurPresente(666))) {
      // retourne une exeption
      throw new IllegalArgumentException(
          "Le pattern ne contient pas de valeur 666, aucune valeur numérique ne peut être détectée");
    }

    Matrice matriceGrid = new Matrice(grid);
    // fait un parcourir la grille avec des boucles for
    for (int i = 0; i < matriceGrid.getNbLignes(); i++) {
      for (int j = 0; j < matriceGrid.getNbColonnes(); j++) {

        if (Etat.fromInt(matriceGrid.getElement(i, j)) == Etat.NUMERIQUE) {
          matriceGrid.setElement(i, j, 666);
        }

      }
    }

    return detectInGrid(matriceGrid.getElements());
  }


  /**
   * 
   * Cette méthode prend en paramètre une grille de nombres entiers et renvoie une
   * liste de positions où un certain motif spécifié par la variable "pattern" est
   * détecté dans la grille.
   * 
   * Important : pour que cette méthode fonctionne il est nécessaire de replacer
   * les case numérique par la valeur 666 dans le pattern.
   * 
   * exemple : int[][] pattern = { { 666, 0, 0 }, { 0, -1, 0 }, { 0, 0, 666 } };
   * 
   * 
   * Elle parcourt la grille à l'aide de boucles for pour remplacer tous les
   * éléments qui ne sont pas égaux à 0, -1 ou -2 par la valeur 666.
   * Elle affiche la grille modifiée ainsi que le motif recherché.
   * Enfin, elle renvoie les positions où le motif est détecté dans la grille en
   * appelant la méthode "detectInGrid".
   * 
   * cette méthode est la version préprocesseur de la méthode "detectNum"
   * 
   * @param grid une grille de nombres entiers dans laquelle chercher le motif
   * @return une liste de positions de la première occurrence où le motif est
   *         détecté dans la grille
   */
  public ArrayList<Position> detectNumPreproc(int[][] grid) {

    // si le pattern ne contient aucune valeur 666, on ne peut pas le détecter dans
    if (!(pattern.valeurPresente(666))) {
      // retourne une exeption
      throw new IllegalArgumentException(
          "Le pattern ne contient pas de valeur 666, aucune valeur numérique ne peut être détectée");
    }

    Matrice matriceGrid = new Matrice(grid);

    matriceGrid.remplacerValeurs(999, 0);
    matriceGrid.remplacerValeurs(-999, -1);

    // fait un parcourir la grille avec des boucles for et remplace les valeurs numériques par 666
    for (int i = 0; i < matriceGrid.getNbLignes(); i++) {
      for (int j = 0; j < matriceGrid.getNbColonnes(); j++) {

        if (Etat.fromInt(matriceGrid.getElement(i, j)) == Etat.NUMERIQUE) {
          matriceGrid.setElement(i, j, 666);
        }

      }
    }

    return detectInGrid(matriceGrid.getElements());
  }

}
