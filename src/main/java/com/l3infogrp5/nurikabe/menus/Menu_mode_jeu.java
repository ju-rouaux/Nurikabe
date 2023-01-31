/**
 * Classe implémentant le menu de séléction des modes de jeu
 * 
 * @author 
 * @version 1.0
 */

package com.nurikabe.menus;

public class Menu_mode_jeu{

    /**
     * Le boutton pour revenir au menu précédant
     */
    Boutton_retour retour;

    /**
     * Les bouttons pour acceder au niveau d'un mode de jeu
     */
    Button detente, sans_fin, chrono;

    /**
     * Le boutton pour acceder au menu des score pour le mode de jeu sans fin
     */
    Button menu_score_sf;

    /**
     * Constructeur du menu des mode de jeu
     */
    Menu_mode_jeu() {
        // retour = new Boutton_retour();
        detente = new Button("Détente");
        sans_fin = new Button("Contre la montre");
        chrono = new Button("Sans fin");
        ImageView iw = new ImageView(new Image("trophee.png"));
        menu_score_sf = new Button("", iw);
    }

    /**
     * Méthode qui permet de revenir a l'ancienne affichage
     * 
     * 
     * @return void
     */
    public void charge_retour() {
        retour.charge_retour();
    }

    /**
     * Méthode qui charge la grille de niveau selon le mode de jeu selectionner
     * 
     * 
     * @return void
     */
    public void charge_select_niveau() {

    }

    /**
     * Méthode qui revoie le menu des scores
     * 
     * 
     * @return void
     */
    public void charge_menu_score() {

    }

    /**
     * Méthode qui demarre un parti en mode sans fin
     * 
     * 
     * @return void
     */
    public void charge_sans_fin() {

    }

    /**
     * Méthode qui charge la page de séléction des modes de jeu
     * 
     * 
     * @return void
     */
    public void charge_mode_jeu() {

    }

    /**
     * Méthode pour creer la page de séléction des mode de jeu 
     * 
     * 
     * @return void
     */
    public void creation_mode_jeu(){

    }

}
