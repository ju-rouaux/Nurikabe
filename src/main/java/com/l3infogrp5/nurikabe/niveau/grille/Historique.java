package com.l3infogrp5.nurikabe.niveau.grille;

import java.io.IOException;
import java.io.Serializable;
import java.util.Stack;

import com.l3infogrp5.nurikabe.utils.Position;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

/**
 * Classe enregistrant l'historique des mouvements du joueur.
 * Il est possible de lier des éléments graphiques aux propriétés
 * {@link #peutAnnuler()} et {@link #peutRetablir()} grâce à la méthode
 *
 * @author Julien Rouaux
 */
public class Historique implements Serializable {

    /**
     * Représente un changement d'état d'une case.
     * On y trouve l'ancien état, le nouvel état, et la position de la case changée.
     *
     * @author Julien Rouaux
     */
    public static class Mouvement implements Serializable {
        /** Position de la case changée */
        private Position pos;
        /** Ancien état */
        private Etat ancien;
        /** Nouvel état */
        private Etat nouveau;

        /**
         * Nouveau mouvement immuable.
         *
         * @param pos     position de la case changée.
         * @param ancien  ancien état de la case.
         * @param nouveau nouvel état de la case.
         */
        public Mouvement(Position pos, Etat ancien, Etat nouveau) {
            this.pos = pos;
            this.ancien = ancien;
            this.nouveau = nouveau;
        }

        /**
         * Retourne la position de la case changée.
         *
         * @return la position de la case changée.
         */
        public Position getPosition() {
            return this.pos;
        }

        /**
         * Retourne l'ancien état de la case.
         *
         * @return l'ancien état de la case.
         */
        public Etat getAncienEtat() {
            return this.ancien;
        }

        /**
         * Retourne le nouvel état de la case.
         *
         * @return le nouvel état de la case.
         */
        public Etat getNouvelEtat() {
            return this.nouveau;
        }
    }

    /** Historique des mouvements */
    private Stack<Mouvement> pile;
    /** Mouvement courant (pour pouvoir redo après undo) */
    private int curseur;

    /** Vrai si l'historique peut annuler un mouvement. */
    private transient ReadOnlyBooleanWrapper peutAnnuler;
    /** Vrai si l'historique peut rétablir un mouvement */
    private transient ReadOnlyBooleanWrapper peutRetablir;

    /**
     * Instancie un nouvel historique vide.
     */
    public Historique() {
        this.pile = new Stack<Mouvement>();
        this.curseur = -1;
        this.peutAnnuler = new ReadOnlyBooleanWrapper(false);
        this.peutRetablir = new ReadOnlyBooleanWrapper(false);
    }

    /**
     * A appeler à chaque changement effectué sur la pile.
     * Actualise les états peutAnnuler et peutRetablir.
     */
    private void actualiserEtat() {
        this.peutAnnuler.set(curseur >= 0);
        this.peutRetablir.set(this.pile.size() - 1 > curseur);
    }

    /**
     * Ajoute le mouvement à l'historique. Si le mouvement est réalisé à la même
     * position que le dernier, le dernier mouvement est écrasé.
     * Tous les mouvements situés après le curseur sont oubliés.
     *
     * @param m le mouvement à enregistrer.
     */
    public void ajoutMouvement(Mouvement m) {
        // Si des éléments sont situés après le curseur, les supprimer.
        // Le curseur pointe alors sur le haut de la pile.
        while (this.pile.size() - 1 > curseur)
            this.pile.pop();

        // Ecraser le nouvel état du haut de la pile,
        // si le mouvement est réalisé à la même position
        if (!this.estVide() && this.pile.peek().pos.equals(m.pos)) {
            this.pile.peek().nouveau = m.nouveau;
            // Retirer le mouvement s'il est revenu à l'état initial
            if (this.pile.peek().ancien.equals(this.pile.peek().nouveau)) {
                this.pile.pop();
                curseur--;
            }
        } else {
            // Insérer le mouvement
            this.pile.push(m);
            curseur++;
        }

        System.out.println("[Historique] Mouvement " + m.ancien + " vers " + m.nouveau + " : " + m.pos);

        this.actualiserEtat();
    }

    /**
     * Retourne un mouvement pouvant être rétabli selon l'historique.
     * Toujours vérifier si un mouvement peut être rétabli avec
     * {@link #peutRetablir()}.
     *
     * @return un mouvement pouvant être rétabli selon l'historique.
     * @throws IndexOutOfBoundsException lancé lorsqu'aucun mouvement n'est à
     *                                   rétablir.
     */
    public Mouvement retablir() throws IndexOutOfBoundsException {
        if (this.peutRetablir()) {
            Mouvement retour = this.pile.get(++curseur);
            this.actualiserEtat();
            return retour;
        }

        throw new IndexOutOfBoundsException("Aucun mouvement n'est à rétablir.");
    }

    /**
     * Retourne un mouvement pouvant être annulé selon l'historique.
     * Toujours vérifier si un mouvement peut être annulé avec
     * {@link #peutAnnuler()}.
     *
     * @return un mouvement pouvant être annulé selon l'historique.
     * @throws IndexOutOfBoundsException lancé lorsqu'aucun mouvement n'est à
     *                                   annuler.
     */
    public Mouvement annuler() throws IndexOutOfBoundsException {
        if (this.peutAnnuler()) {
            Mouvement retour = this.pile.get(curseur--);
            this.actualiserEtat();
            return retour;
        }
        throw new IndexOutOfBoundsException("Aucun mouvement n'est à annuler.");
    }

    /**
     * Retourne vrai si l'historique est vide.
     *
     * @return vrai si l'historique est vide.
     */
    public boolean estVide() {
        return this.pile.empty();
    }

    /**
     * Retourne vrai si un mouvement peut être annulé.
     *
     * @return vrai si un mouvement peut être annulé.
     */
    public boolean peutAnnuler() {
        return this.peutAnnuler.get();
    }

    /**
     * Retourne vrai si un mouvement peut être rétabli.
     *
     * @return vrai si un mouvement peut être rétabli.
     */
    public boolean peutRetablir() {
        return this.peutRetablir.get();
    }

    public ReadOnlyBooleanProperty peutAnnulerProperty() {
        return this.peutAnnuler.getReadOnlyProperty();
    }

    public ReadOnlyBooleanProperty peutRetablirProperty() {
        return this.peutRetablir.getReadOnlyProperty();
    }

    /**
     * Méthode exécutée après la deserialization de l'objet.
     * 
     * @param in paramètre pour invoquer le mécanisme de deserialization par défaut.
     * @throws IOException            -
     * @throws ClassNotFoundException -
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.peutAnnuler = new ReadOnlyBooleanWrapper(false);
        this.peutRetablir = new ReadOnlyBooleanWrapper(false);
        this.actualiserEtat();
    }
}
