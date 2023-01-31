/**
 * Classe implémentant le menu d'aafichage des règles du Nurikabe
 * 
 * @author 
 * @version 1.0
 */

package com.nurikabe.menus;

public class Menu_regles {
    /**
     * Le boutton pour revenir au menu précédant
     */
    Boutton_retour retour;

    /**
     * Les regles
     */
    Label text;

    /**
     * Constructeur des relges
     */
    Menu_regles() {

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

}
