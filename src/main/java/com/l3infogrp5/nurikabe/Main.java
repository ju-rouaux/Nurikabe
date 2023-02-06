package com.l3infogrp5.nurikabe;

import com.l3infogrp5.nurikabe.Profil.Sauvegarder;

public class Main {

    public static void main(String[] args) {
        Sauvegarder sauv = new Sauvegarder();
        sauv.afficherFichiers();
        sauv.creerDossiers();
        // sauv.sauvegarderProfil("Dakire");
    }
}
