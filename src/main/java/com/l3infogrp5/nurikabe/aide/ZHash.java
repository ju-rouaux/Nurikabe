package com.l3infogrp5.nurikabe.aide;

import com.l3infogrp5.nurikabe.utils.Matrice;

/**
 * Classe permettant de calculer le hash d'une grille de jeu
 * 
 * @author Elias Okat
 */
public class ZHash {
  private static final int PRIME = 3; // 3 est un nombre premier choisi arbitrairement

  /**
   * Constructeur de la classe ZHash
   */
  public ZHash() {
  }

  /**
   * Calcule le hash d'une grille de jeu
   * 
   * @param grille        la grille de jeu
   * @param tailleLigne   la taille de la grille en ligne
   * @param tailleColonne la taille de la grille en colonne
   * @return le hash de la grille
   */
  public static int hash(int[][] grille, int tailleLigne, int tailleColonne) {
    int hash = 0;
    int puissance = 1;
    for (int i = 0; i < tailleLigne; i++) {
      for (int j = 0; j < tailleColonne; j++) {
        hash = (hash + grille[i][j] * puissance) % Integer.MAX_VALUE;
        puissance = (puissance * PRIME) % Integer.MAX_VALUE;
      }
    }
    return hash;
  }

  /**
   * Calcule le hash d'une grille de jeu
   * 
   * @param grille        la grille de jeu
   * @param tailleLigne   la taille de la grille en ligne
   * @param tailleColonne la taille de la grille en colonne
   * @return le hash de la grille
   */
  public static int hash(Matrice grille, int tailleLigne, int tailleColonne) {
    int hash = 0;
    int puissance = 1;
    for (int i = 0; i < tailleLigne; i++) {
      for (int j = 0; j < tailleColonne; j++) {
        hash = (hash + grille.getElement(i, j) * puissance) % Integer.MAX_VALUE;
        puissance = (puissance * PRIME) % Integer.MAX_VALUE;
      }
    }
    return hash;
  }

}
