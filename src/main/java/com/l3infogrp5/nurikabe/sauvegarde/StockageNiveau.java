package com.l3infogrp5.nurikabe.sauvegarde;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.l3infogrp5.nurikabe.utils.Path;

public class StockageNiveau {

    private static void writeGridToFile(FileWriter writer, int[][] grille) throws IOException {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[0].length; j++) {
                writer.write(grille[i][j] + " ");
            }
            writer.write("\n");
        }
    }

    /**
     * Crée les grilles pour le mode détente et les stocke dans un fichier texte.
     * Les grilles sont stockées dans le répertoire "repertoire_Grilles" avec le nom
     * "grilles_detente.txt".
     *
     * @throws IOException si une erreur se produit lors de la création ou de
     *                     l'écriture dans le fichier.
     */
    public static void creationNiveauDetente() throws IOException {
        int niveau_0[][] = new int[][] {
                { 0, 0, 17, 0, 3, 0, 0 },
                { 0, 0, 0, 0, -1, 0, 0 },
                { 0, -2, 0, 0, 0, 0, 0 } };

        int niveau_1[][] = new int[][] {
                { 0, 5, 0, 0, 0, 0, 0 },
                { -1, 0, 0, 0, 0, -1, 0 },
                { 0, 0, 0, -1, 0, 0, 0 },
        };

        int niveau_2[][] = new int[][] {
                { 0, 0, 0, -1, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, -1 },
                { 0, 0, 0, 0, 5, 0, 0 },
        };

        try {
            FileWriter writer = new FileWriter(Path.repertoire_Grilles.toString() + "/grilles_detente.txt");
            writer.write("Grille 0 (" + niveau_0.length + ";" + niveau_0[0].length + ") :\n");
            writeGridToFile(writer, niveau_0);
            writer.write("\nGrille 1 (" + niveau_0.length + ";" + niveau_0[0].length + ") :\n");
            writeGridToFile(writer, niveau_1);
            writer.write("\nGrille 2 (" + niveau_0.length + ";" + niveau_0[0].length + ") :\n");
            writeGridToFile(writer, niveau_2);
            writer.close();
            System.out.println("Toutes les grilles ont été sauvegardées dans le fichier grilles_detente.txt");
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite" + e.getMessage());
        }
    }

    public static void creationSolutionDetente() throws IOException{
        int niveau_0[][] = new int [][]{

        };
    }

    /**
     * Crée les grilles pour le mode contre la montre et les stocke dans un fichier texte.
     */
    public static void creationNiveauCLM(){
        //TODO
    }

    /**
     * Crée les grilles pour le mode sans fin et les stocke dans un fichier texte.
     */
    public static void creationNiveauSansFin(){
        //TODO : a voir, peut etre niveau aleatoire des modes detente et CLM
    }





    public static int[][] chargerGrille(int numeroGrille, String modeDeJeu) {
        try {
            Scanner scanner = new Scanner(
                    new File(Path.repertoire_Grilles.toString() + "/grilles_" + modeDeJeu + ".txt"));
            int[][] grille = null;
            int lignes = 0;
            int colonnes = 0;
            boolean isCurrentGrid = false;
            int rowIndex = 0; // add row index variable
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Grille " + numeroGrille)) {
                    // Found a new grid, initialize a new 2D array
                    String[] dimensions = line.split("\\(")[1].split("\\)")[0].split(";");
                    lignes = Integer.parseInt(dimensions[0].trim());
                    colonnes = Integer.parseInt(dimensions[1].trim());
                    // System.out.println("Grille de " + lignes + " lignes et " + colonnes + " colonnes");
                    grille = new int[lignes][colonnes];
                    isCurrentGrid = true;
                } else if (line.startsWith("#")) {
                    // Skip separator line
                    isCurrentGrid = false;
                } else if (line.startsWith("Grille") && isCurrentGrid) {
                    isCurrentGrid = false;
                    break;

                } else if (isCurrentGrid) {
                    // Load data into the current grid
                    String[] values = line.split(" ");
                    for (int i = 0; i < values.length; i++) {
                        if (!values[i].equals("")) {
                            grille[rowIndex][i] = Integer.parseInt(values[i]);
                        }
                    }
                    rowIndex++; // increment row index
                }
            }
            scanner.close();
            return grille;
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier 'grilles_detente.txt' est introuvable : " + e.getMessage());
        } catch (Exception e) {
            System.out.println(
                    "Une erreur s'est produite lors de la lecture de la grille dans le fichier 'grilles_detente.txt'"
                            + e.getMessage());
        }
        return null;
    }

}
