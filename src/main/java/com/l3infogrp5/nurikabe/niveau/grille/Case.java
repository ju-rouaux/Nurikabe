package com.l3infogrp5.nurikabe.niveau.grille;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import com.l3infogrp5.nurikabe.utils.Position;

import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Case abstraite de grille de Nurikabe.
 * Il s'agit d'un JavaFX Button qui contient une donnée de Position et une
 * {@link #etatProperty} (IntegerProperty).
 * L'etatProperty de la case peut être associée à une autre property pour
 * répercuter automatiquement les changements entre elles.
 * Le bouton reserve la modification de ses événements "clic simple" aux classes
 * filles, mais offre la possibilité aux utilisateurs d'exécuter leur méthode
 * pour les clics "maintenus" (ex.
 * {@link #setEventMaintienDroit(EventClicMaintenu)}).
 * 
 * @author Julien Rouaux
 * @see com.l3infogrp5.nurikabe.niveau.grille.EventClicMaintenu
 */
public abstract class Case extends Button {

    /**
     * Classe qui démarre un chronomètre et exécute une action de clic maintenu à la
     * fin du temps, puis une action de relâchement.
     * On peut interrompre le chronomètre pour éviter d'exécuter les actions avec
     * {@link #stop()}
     * Il est possible de savoir si l'action de maintien a été exécutée au travers
     * de la méthode {@link #aEteMaintenu()}.
     * La durée d'un clic maintenu est définie dans l'interface
     * {@link EventClicMaintenu}.
     * 
     * @author Julien Rouaux
     */
    private class ChronoMaintien extends AnimationTimer {

        private long temps_debut; // Temps qu'il était au lancement du chrono
        private boolean maintenu; // Vrai si le bouton a été maintenu, faux sinon
        private EventClicMaintenu event_maintien; // Méthode à exécuter lorsque la case est maintenue

        /**
         * Créer un nouveau chronomètre exécutant l'{@link EventClicMaintenu} à la fin
         * du temps imparti.
         * 
         * @param e
         */
        ChronoMaintien(EventClicMaintenu e) {
            this.event_maintien = e;
            this.maintenu = false;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void handle(long t) {
            if (!this.maintenu && (System.currentTimeMillis() - temps_debut) > EventClicMaintenu.DUREE_CLIC_MS) {
                this.maintenu = true;
                event_maintien.maintenu(Case.this);
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void start() {
            super.start();
            this.maintenu = false;
            temps_debut = System.currentTimeMillis();
        }

        /**
         * {@inheritDoc}
         * Exécute la méthode de relâchement si la case a été maintenue suffisamment
         * longtemps.
         */
        @Override
        public void stop() {
            super.stop();
            if (this.maintenu)
                event_maintien.relache(Case.this);
        }

        /**
         * Retourne vrai si la case a été maintenue jusqu'au bout du temps.
         */
        public boolean aEteMaintenu() {
            return maintenu;
        }
    }

    private Position position; // Position de la case dans la grille.
    private IntegerProperty etat; // Etat stocké sous forme numérique.

    private ChronoMaintien chronoClicGauche; // Clic gauche maintenu
    private ChronoMaintien chronoClicDroit; // Clic droit maintenu
    private Runnable clicGauche; // Clic gauche rapide
    private Runnable clicDroit; // Clic droit rapide

    /**
     * Initialise la position de la case et le style du bouton.
     * Un clic maintenu sur une case ne fait rien par défaut, définir ce
     * comportement avec les méthodes
     * {@link #setEventMaintienDroit(EventClicMaintenu)}
     * et {@link #setEventMaintienGauche(EventClicMaintenu)}.
     * Le clic simple est réservé aux classes filles de cette classe.
     * 
     * @param pos la position de la case dans la grille.
     */
    public Case(Position pos) {
        this.position = pos;
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.etat = new SimpleIntegerProperty();

        // Style
        this.getStylesheets().add("/css/niveau.css");
        this.getStyleClass().add("case");

        // Par défaut : ne rien faire lorsqu'un clic est réalisé.
        chronoClicGauche = new ChronoMaintien(new EventClicMaintenu() {
        });
        chronoClicDroit = new ChronoMaintien(new EventClicMaintenu() {
        });
        clicGauche = () -> {
        };
        clicDroit = () -> {
        };

        // Ajouter les événements de clics
        this.addEventFilter(MouseEvent.ANY, (e) -> {
            if (e.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
                if (e.getButton().equals(MouseButton.PRIMARY))
                    this.chronoClicGauche.start();
                if (e.getButton().equals(MouseButton.SECONDARY))
                    this.chronoClicDroit.start();
            } else if (e.getEventType().equals(MouseEvent.MOUSE_RELEASED)) {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    this.chronoClicGauche.stop();
                    if (!this.chronoClicGauche.aEteMaintenu())
                        this.clicGauche.run();
                }
                if (e.getButton().equals(MouseButton.SECONDARY)) {
                    chronoClicDroit.stop();
                    if (!this.chronoClicDroit.aEteMaintenu())
                        this.clicDroit.run();
                }
            }
        });
    }

    /**
     * Retourne l'état de la case.
     * 
     * @return l'état de la case.
     */
    public Etat getEtat() {
        return Etat.fromInt(etat.get());
    }

    /**
     * Retourne l'état de la case sous forme numérique.
     * 
     * @return l'état de la case sous forme numérique.
     */
    public int getEtatInt() {
        return this.etat.get();
    }

    /**
     * Retourne la Property de l'état de la case.
     * Permet de lier dynamiquement son état à une grille.
     * 
     * @return la Property de l'état de la case.
     */
    public IntegerProperty etatProperty() {
        return this.etat;
    }

    /**
     * Retourne la position de la case.
     * 
     * @return la position de la case.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Met la case en surbrillance ou non.
     * 
     * @param b           vrai si la case doit être en surbrillance, faux sinon.
     * @param taille_zone nombre de cases mises en surbrillance.
     */
    public void surbrillance(boolean b, int taille_zone) {
        if (b)
            this.getStyleClass().add("surbrillance");
        else
            this.getStyleClass().remove("surbrillance");
    }

    /**
     * Définit le code à exécuter lorsque le clic gauche est maintenu sur la case.
     * 
     * @param e le code à exécuter lorsque le clic gauche est maintenu sur la case.
     */
    public void setEventMaintienGauche(EventClicMaintenu e) {
        chronoClicGauche = new ChronoMaintien(e);
    }

    /**
     * Définit le code à exécuter lorsque le clic droit est maintenu sur la case.
     * 
     * @param e le code à exécuter lorsque le clic droit est maintenu sur la case.
     */
    public void setEventMaintienDroit(EventClicMaintenu e) {
        chronoClicDroit = new ChronoMaintien(e);
    }

    /**
     * Définit le code à exécuter lorsqu'un simple clic gauche est réalisé.
     * 
     * @param e le code à exécuter lorsqu'un simple clic gauche est réalisé.
     */
    protected void setEventClicGauche(Runnable e) {
        this.clicGauche = e;
    }

    /**
     * Définit le code à exécuter lorsqu'un simple clic droit est réalisé.
     * 
     * @param e le code à exécuter lorsqu'un simple clic droit est réalisé.
     */
    protected void setEventClicDroit(Runnable e) {
        this.clicDroit = e;
    }
}