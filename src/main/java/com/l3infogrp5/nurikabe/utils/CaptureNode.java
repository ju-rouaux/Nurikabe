package com.l3infogrp5.nurikabe.utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
/**
 * Classe permettant d'enregsiter l'image du nivea
 * @author Julien Rouaux
 */
public class CaptureNode {
    /**
     * Constructeur
     */
    private CaptureNode() {}

    /**
     * Enregsitre sous forme d'une image la grille
     * @param noeud composant a capturer (la grille)
     * @param joueur le nom du joueur
     * @param mode_de_jeu le mode de jeu
     * @param id_niveau l'identifiant du niveau
     * @return vrai si bien passé, faux sinon
     */
    public static boolean capturer(Node noeud, String joueur, String mode_de_jeu, int id_niveau) {
        //TODO : utiliser la classe Path pour localiser l'emplacement de stockage
        File file = new File(Path.repertoire_lvl.toString() + "/" + joueur + "/" + mode_de_jeu + "/" + "capture_niveau_" + id_niveau+".png");

        ImageView img = new ImageView(noeud.snapshot(null, null));

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img.getImage(), null), "png", file);
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
