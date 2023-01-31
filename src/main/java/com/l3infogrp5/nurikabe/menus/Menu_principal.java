/**
 * Classe implémentant le menu principal du Nurikabe
 * 
 * @author 
 * @version 1.0
 */

package com.nurikabe.menus;

public class Menu_principal {
    /**
     * Le nom du jeu
     */
    static Label nurikabe = new Label("NURIKABE");

    /**
     * Les boutton menant au divers menu du jeu
     */
    Button joueur, profils, regles, quitter;

    /**
     * Constructeur du menu principal
     */
    Menu_principal() {
        joueur = new Button("Jouer");
        profils = new Button("Profils");
        regles = new Button("Règles");
        quitter = new Button("Quitter");
    }

    /**
     * Méthode qui creer le menu principal
     * 
     * 
     * @return void
     */
    public void creation_menu_principal() {

    }

    /**
     * Méthode qui charge le menu principal
     * 
     * 
     * @return void
     */
    public void charge_menu_principal() {

    }

    /**
     * Méthode renvoie au menu des profils
     * 
     * 
     * @return void
     */
    public void charge_profils() {

    }

    /**
     * Méthode qui renvoie au menu des mode de jeu
     * 
     * 
     * @return void
     */
    public void charge_mode_jeu() {

    }

    /**
     * Méthode qui renvoie au menu des regles du jeu
     * 
     * 
     * @return void
     */
    public void charge_regles() {

    }

    /**
     * Méthode qui ferme le jeu
     * 
     * 
     * @return void
     */
    public void quitter() {

    }

}
