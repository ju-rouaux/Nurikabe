package com.l3infogrp5.nurikabe.Niveaux.Score;

/**
* Implémentatio, du calcul du score selon le chronomètre
* @author Antoine Couapel, Killian Rattier
* @version 1.0
*/

abstract public class ScoreChrono implements ScoreInterface{

    public int sec;
    public int min;

    public ScoreChrono(int sec, int min) {
        this.sec = sec;
        this.min = min;
    }

    public void calcul() throws InterruptedException{

    }

    @Override
    public void aideUtilise() {}
} 



