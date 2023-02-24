package com.l3infogrp5.nurikabe.sauvegarde;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.l3infogrp5.nurikabe.niveau.grille.Grille;
import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.utils.Path;

public class Charger {

    public Charger() {
    }

    /**
     * Charge un niveau à partir d'un fichier.
     */

    public static Historique chargerHistorique(String joueur, String mode_De_Jeu, int id_Niveau) {
        File mouvements = new File(
                Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu + "/Mouvements_" + id_Niveau);
        if (mouvements.exists() && mouvements.length() > 0) {
            System.out.println("Chargement des mouvements du niveau du joueur ...");
            return deserialisationHistorique(mouvements);
        } else {
            System.out.println("Erreur de chargement du fichier des mouvements du niveau du joueur !");
        }
        System.out.println("Mouvements.existe() : " + mouvements.exists());
        System.out.println("Mouvements.length() : " + mouvements.length());
        return null;
    }

    private static Historique deserialisationHistorique(File fichier) {
        Historique historique_temp = null;
        try {
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            historique_temp = (Historique) in.readObject();
            historique_temp.initTransientBoolean();
            historique_temp.actualiserEtat();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return historique_temp;
    }

    public static Grille chargerGrille(String joueur, String mode_De_Jeu, int id_Niveau) {
        File grille_repert = new File(Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu);
        File grille_fichier = new File(grille_repert.toString() + "/Grille_" + id_Niveau);
        if (grille_fichier.exists() && grille_fichier.length() > 0) {
            System.out.println("Chargement de la grille du niveau du joueur ...");
            StockageNiveau.chargerGrille(id_Niveau, mode_De_Jeu);
            return deserialisationGrille(grille_fichier);
        } else {
            System.out.println("Erreur de chargement du fichier de la grille du niveau du joueur !");
        }
        System.out.println("Niveau.existe() : " + grille_fichier.exists());
        System.out.println("Niveau.length() : " + grille_fichier.length());
        return null;
    }

    private static Grille deserialisationGrille(File fichier) {
        Grille grille = null;
        int[][] matrice = null;
        try {
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            grille = (Grille) in.readObject();
            matrice = (int[][]) in.readObject();
            in.close();
            fileIn.close();
            grille.initTransientGrille(matrice);

            System.out.println("Grille chargée");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return grille;
    }

    // public static Niveau chargerNiveau(String joueur, String mode_De_Jeu, int
    // id_Niveau) {
    // File niveau = new File(
    // Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu +
    // "/Niveau_" + id_Niveau);
    // if (niveau.exists() && niveau.length() > 0) {
    // System.out.println("La sauvegarde du niveau du joueur existe !");
    // System.out.println("Chargement du niveau du joueur...");
    // return deserialisationNiveau(niveau);
    // } else {
    // System.out.println("La sauvegarde du niveau du joueur n'existe pas !");
    // }
    // return null;
    // }

    // private static Niveau deserialisationNiveau(File fichier){
    // try (ObjectInputStream ois = new ObjectInputStream(new
    // FileInputStream(fichier))) {
    // Niveau niveau = (Niveau) ois.readObject();
    // Grille grille = (Grille) ois.readObject();
    // Historique historique = (Historique) ois.readObject();
    // niveau.setGrille(grille);
    // niveau.setHistorique(historique);

    // System.out.println("Grille :");
    // int matrice [][] = niveau.getGrille().getMatrice();
    // for (int i = 0; i < matrice.length; i++) {
    // for (int j = 0; j < matrice[i].length; j++) {
    // System.out.print(matrice[i][j] + " ");
    // }
    // System.out.println();
    // }
    // return niveau;
    // } catch (IOException | ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

    // private static Niveau deserialisationNiveau(File fichier) {
    // try (ObjectInputStream ois = new ObjectInputStream(new
    // FileInputStream(fichier))) {
    // Niveau niveau = (Niveau) ois.readObject();
    // Grille grille = (Grille) ois.readObject();
    // Historique historique = (Historique) ois.readObject();
    // niveau.setGrille(grille);
    // niveau.setHistorique(historique);
    // System.out.println("Chargement du niveau du joueur...");
    // System.out.println("niveau :" + niveau);
    // if (niveau != null) {
    // System.out.println("niveau not null");
    // if (niveau.getGrille() != null) {
    // System.out.println("grille not null");
    // System.out.println("Grille :" + niveau.getGrille().toString());
    // int [][] matrice = niveau.getGrille().getMatrice();
    // System.out.println("Matrice :");
    // for(int i = 0; i < matrice.length; i++) {
    // for(int j = 0; j < matrice[i].length; j++) {
    // System.out.print(matrice[i][j] + " ");
    // }
    // System.out.println();
    // }
    // // niveau.setGrille(niveau.getGrille());
    // }
    // if (niveau.getHistorique() != null) {
    // System.out.println("historique not null");
    // System.out.println("Historique :" + niveau.getHistorique().toString());
    // // niveau.setHistorique(niveau.getHistorique());
    // }

    // }else{
    // System.out.println("niveau null");
    // }
    // System.out.println("Chargement du niveau du joueur terminé !");
    // return niveau;
    // } catch (IOException | ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // return null;
    // }

}
