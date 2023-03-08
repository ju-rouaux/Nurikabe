package com.l3infogrp5.nurikabe.niveau.score;

/**
 * Classe interface répertoriant les méthodes à invoquer pour calculer un score
 * 
 * @author Antoine Couapel
 * @version 1.0
 */
public interface ScoreInterface {

    public void start();

    public void stop();

    public void nouveauLance();

    public void aideUtilise();

    public void restart();

    public void grilleComplete();

    public void checkUtilise();

    public void get_Pane();

}