package com.l3infogrp5.nurikabe.sauvegarde;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.l3infogrp5.nurikabe.utils.Path;

public class StockageNiveau {

    /**
     * Ecrit la matrice dans un fichier texte.
     *
     * @param writer  le writer du fichier
     * @param matrice la matrice a ecrire
     * @throws IOException si une erreur se produit lors de l'écriture dans le
     *                     fichier.
     */
    private static void grilleVersFichier(FileWriter writer, int[][] matrice) throws IOException {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                writer.write(matrice[i][j] + " ");
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
            FileWriter writer = new FileWriter(Path.repertoire_grilles.toString() + "/grilles_detente.txt");
            writer.write("Grille 0 (" + niveau_0.length + ";" + niveau_0[0].length + ") :\n");
            grilleVersFichier(writer, niveau_0);
            writer.write("\nGrille 1 (" + niveau_0.length + ";" + niveau_0[0].length + ") :\n");
            grilleVersFichier(writer, niveau_1);
            writer.write("\nGrille 2 (" + niveau_0.length + ";" + niveau_0[0].length + ") :\n");
            grilleVersFichier(writer, niveau_2);
            writer.close();
            System.out.println("[StockageNiveau] Toutes les grilles ont été sauvegardées dans le fichier grilles_detente.txt");
        } catch (IOException e) {
            System.out.println("[StockageNiveau] Une erreur s'est produite" + e.getMessage());
        }
    }

    /**
     * Crée les grilles pour le mode contre la montre et les stocke dans un fichier
     * texte.
     */
    public static void creationNiveauCLM() {
        // TODO
    }

    /**
     * Crée les grilles pour le mode sans fin et les stocke dans un fichier texte.
     */
    public static void creationNiveauSansFin() {
        // TODO : a voir, peut etre niveau aleatoire des modes detente et CLM
    }

    /**
     * Charge une grille depuis un fichier texte selon le numero de la grille et le
     * mode de jeu
     *
     * @param id_niveau   le numero de la grille
     * @param mode_de_jeu le mode de jeu
     * @return la matrice du niveau chargé
     */
    public static int[][] chargerGrille(int id_niveau, String mode_de_jeu) {
        try {
            Scanner scanner = new Scanner(
                    new File(Path.repertoire_grilles.toString() + "/grilles_" + mode_de_jeu + ".txt"));
            int[][] grille = null;
            int lignes = 0;
            int colonnes = 0;
            boolean grille_courante = false;
            int index = 0; // add row index variable
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Grille " + id_niveau)) {
                    // Si une grille a été trouvée, on récupère ses dimensions
                    String[] dimensions = line.split("\\(")[1].split("\\)")[0].split(";");
                    lignes = Integer.parseInt(dimensions[0].trim());
                    colonnes = Integer.parseInt(dimensions[1].trim());
                    grille = new int[lignes][colonnes];
                    grille_courante = true;
                } else if (line.startsWith("#")) {
                    // Ignore les lignes commentées
                    grille_courante = false;
                } else if (line.startsWith("Grille") && grille_courante) {
                    grille_courante = false;
                    break;

                } else if (grille_courante) {
                    String[] values = line.split(" ");
                    for (int i = 0; i < values.length; i++) {
                        if (!values[i].equals("")) {
                            grille[index][i] = Integer.parseInt(values[i]);
                        }
                    }
                    index++; // increment row index
                }
            }
            scanner.close();
            return grille;
        } catch (FileNotFoundException e) {
            System.out.println("[StockageNiveau] Le fichier 'grilles_detente.txt' est introuvable : " + e.getMessage());
        } catch (Exception e) {
            System.out.println(
                    "[StockageNiveau] Une erreur s'est produite lors de la lecture de la grille dans le fichier 'grilles_detente.txt'"
                            + e.getMessage());
        }
        return null;
    }

}
