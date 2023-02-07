package com.l3infogrp5.nurikabe;

import com.l3infogrp5.nurikabe.Profil.*;

/**
 * Jeu du Nurikabe.
 * Point d'entrée de l'application.
 * Projet étudiant de L3 Informatique.
 *
 * @author Julien Rouaux
 */
public class Main {


    public static void main(String[] args) {
        Sauvegarder sauv = new Sauvegarder();
        sauv.sauvegarderProfil("Juuuliennnnnnnnnnng");

        Charger c = new Charger();
        c.RechercherSauvegarde("Juuuliennnnnnnnnnng");

    }
}
