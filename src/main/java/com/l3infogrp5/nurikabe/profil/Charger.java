package com.l3infogrp5.nurikabe.profil;

import java.io.File;

import com.l3infogrp5.nurikabe.utils.Path;

public class Charger {

    public Charger() {
        System.out.println("Repertoire " + Path.repertoire_Courant.toString());
    }

    /**
     * Met a jour le repertoire courant au repertoire du profil enregistré
     *
     * @param joueur
     * @return
     */
    public boolean RechercherSauvegarde(String joueur) {

        // Vérifier si le nom du joueur est nul
        if (joueur == null)
            return false;

        // Récupère tous les fichiers dans le répertoire
        // Vérifier si le répertoire existe et s'il contient des fichiers
        if (!Sauvegarder.dossierExistants(Path.repertoire_Lvl) || Path.repertoire_Lvl.listFiles().length == 0) {
            System.out.println("Il n'y pas de fichiers ou dossiers");
            return false;
        }

        // Parcourt tous les fichiers pour voir s'il y a une sauvegarde pour le joueur
        for (File fichier : Path.repertoire_Lvl.listFiles()) {
            if (fichier.isDirectory() && fichier.getName().equals(joueur)) {
                System.out.println("La sauvegarde du joueur existe");
                // repertoire = new File(repertoire.toString() + "/" + joueur);
                return true;
            }
        }

        System.out.println("Erreur, ce pseudo n'est pas associé à une sauvegarde");
        return false;

    }

}
