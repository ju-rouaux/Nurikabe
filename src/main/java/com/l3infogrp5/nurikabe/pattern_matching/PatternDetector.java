package com.l3infogrp5.nurikabe.pattern_matching;

import java.util.ArrayList;

import com.l3infogrp5.nurikabe.utils.Matrice;

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
      patternHash.add(ZHash.hash(rotatedPattern, rotatedPattern.getNbLignes(),  rotatedPattern.getNbColonnes()));
    }

  }

  /**
   * Détecte le pattern dans la grille
   * 
   * @param grid la grille dans laquelle le pattern doit être détecté
   * @return la liste des positions du pattern dans la grille
   */
  public ArrayList<int[]> detectInGrid(int[][] grid) {
    ArrayList<int[]> patternLocations = new ArrayList<>();
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
          patternLocations.add(new int[] { i, j });
        }
      }
    }

    return patternLocations;
  }

}
