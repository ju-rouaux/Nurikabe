package com.l3infogrp5.nurikabe.sauvegarde;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.l3infogrp5.nurikabe.niveau.grille.Historique;
import com.l3infogrp5.nurikabe.utils.Path;

public class Charger {

    public Charger() {
    }

    public static Historique chargerMouvements(String joueur, String mode_De_Jeu, int id_Niveau) {
        File mouvements = new File(
                Path.repertoire_Lvl.toString() + "/" + joueur + "/" + mode_De_Jeu + "/Mouvements_" + id_Niveau);
        if (mouvements.exists() && mouvements.length() > 0) {
            System.out.println("Fichier des mouvements du niveau du joueur vide");
            return chargerSerialisation(mouvements);
        } else
            return null;

    }

    private static Historique chargerSerialisation(File fichier) {
        Historique historique_temp = null;
        try {
            FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            historique_temp = (Historique) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return historique_temp;
    }

}
