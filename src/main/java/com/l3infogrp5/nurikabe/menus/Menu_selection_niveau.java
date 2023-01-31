/**
 * Classe implémentant le menu de séléction de niveau
 * 
 * @author 
 * @version 1.0
 */

package com.nurikabe.menus;

public class Menu_selection_niveau {
    /**
     * Le boutton pour revenir au menu précédant
     */
    Boutton_retour retour;

    /**
     * La liste des boutton contentant les niveaux
     */
    Button grille_bouttons[];

    /**
     * Constructeur du Menue de selection de niveau
     */
    Menu_selection_niveau() {
        grille_button = new Button[nb_niveaux];
        for (int i = 0; i < nb_niveaux; i++) {
            grille_bouttons[i] = new Button("Niveau" + i);
        }
    }

    /**
     * Méthode qui permet de revenir a l'ancienne affichage
     * 
     * 
     * @return void
     */
    public void Charge_Retour() {
        retour.charge_retour();
    }

    /**
     * Méthode qui permet d'avoir une preview d'un niveau
     * 
     * 
     * @return void
     */
    public void Niveau_grille() {

    }

    /**
     * Méthode qui charge le niveau a jouer
     * 
     * 
     * @return void
     */
    public void Charge_niveau() {

    }

    /**
     * Méthode quirevoie le menu des scores
     * 
     * 
     * @return void
     */
    public void Charge_menu_score() {

    }

    /**
     * Méthode qui charge la page des sélections de niveaux
     * 
     * 
     * @return void
     */
    public void Charge_Select_menu() {

    }

    /**
     * Méthode qui creer la page des sélections de niveaux
     * 
     * 
     * @return void
     */
    public void Création_Select_menu() {

    }
}
