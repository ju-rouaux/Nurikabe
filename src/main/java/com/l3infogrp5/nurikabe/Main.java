package com.l3infogrp5.nurikabe;

import com.l3infogrp5.nurikabe.Profil.*;

public class Main {


    /**
     * Main appelant les méthodes sur la gestion des profils
     * @param args
     */
    public static void main(String[] args) {
        Sauvegarder sauv = new Sauvegarder();
        sauv.sauvegarderProfil("Juuuliennnnnnnnnnng");

        Charger c = new Charger();
        c.RechercherSauvegarde("Juuuliennnnnnnnnnng");

    }
}
