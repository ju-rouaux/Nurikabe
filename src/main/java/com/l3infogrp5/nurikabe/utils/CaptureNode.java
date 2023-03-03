package com.l3infogrp5.nurikabe.utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
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
     * @return vrai si bien passé, faux sinon
     */
    public static boolean capturer(Node noeud) {
        //TODO : utiliser la classe Path pour localiser l'emplacement de stockage
        File file = new FileChooser().showSaveDialog(null);

        ImageView img = new ImageView(noeud.snapshot(null, null));

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img.getImage(), null), "png", file);
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
