package com.l3infogrp5.nurikabe.Profil;

import java.io.*;
import java.net.*;

public final class Path {
    public static File repertoire_Jar;
    public static final File repertoire_Courant;
    public static final File repertoire_Save;
    public static final File repertoire_Lvl;
    public static final File repertoire_Score;

    static {
        try {
            repertoire_Jar = new File(
                Path.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Erreur indexation fichiers : Impossible de recuperer le repertoire du .jar");
        }
        if (repertoire_Jar != null && repertoire_Jar.toString().length() > 0) {
            int lastIndex = repertoire_Jar.toString().lastIndexOf("/");
            if (lastIndex > 0) {
                repertoire_Courant = new File(repertoire_Jar.toString().substring(0, lastIndex));
                repertoire_Save = new File(repertoire_Courant.toString() + "/save");
                repertoire_Lvl = new File(repertoire_Save.toString() + "/lvl");
                repertoire_Score = new File(repertoire_Save.toString() + "/score");
            } else {
                repertoire_Courant = null;
                repertoire_Save = null;
                repertoire_Lvl = null;
                repertoire_Score = null;
            }
        } else {
            System.out.println("<Erreur> : Probleme d'indexation de fichiers");
            System.out.println("Path.enclosing_method()");
            repertoire_Courant = null;
            repertoire_Save = null;
            repertoire_Lvl = null;
            repertoire_Score = null;
        }
    }
}
