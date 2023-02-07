package com.l3infogrp5.nurikabe.Profil;

import java.io.File;

public class Charger {

    private File repertoire;

    public Charger() {
        String repertoire_Jar = Sauvegarder.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int index = repertoire_Jar.lastIndexOf("/");
        repertoire = new File(repertoire_Jar.substring(0, index));
        System.out.println("Repertoire " + repertoire);
    }

    // /**
    //  * Met a jour le repertoire courant au repertoire du profil enregistré
    //  * @param joueur
    //  * @return
    //  */
    // public boolean RechercherSauvegarde(String joueur) {

    //     // Vérifier si le nom du joueur est nul
    //     if (joueur == null)
    //         return false;

    //     File repertoire_Temp = new File(repertoire.toString() + "/save/lvl/");

    //     // Récupère tous les fichiers dans le répertoire
    //     // Vérifier si le répertoire existe et s'il contient des fichiers
    //     if (!Sauvegarder.dossierExistants(repertoire_Temp) || repertoire_Temp.listFiles().length == 0) {
    //         System.out.println("Il n'y pas de fichiers ou dossiers");
    //         return false;
    //     }

    //     // Parcourt tous les fichiers pour voir s'il y a une sauvegarde pour le joueur
    //     for (File fichier : repertoire_Temp.listFiles()) {
    //         if (fichier.isDirectory() && fichier.getName().equals(joueur)) {
    //             System.out.println("La sauvegarde du joueur existe");
    //             repertoire = new File(repertoire.toString() + "/" + joueur);
    //             return true;
    //         }
    //     }

    //     System.out.println("Erreur, ce pseudo n'est pas associé à une sauvegarde");
    //     return false;

    // }



}
