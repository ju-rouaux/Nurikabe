/**
 * Classe permetant de stocker un mouvement en stockant l'état initial d'une case et son état modifié
 * @author Killian Rattier
 * @version 1.0
 */
public class Mouvement {
    
    /**
     * L'ancien etat de la case modifiée
     */
    CaseVide old_case;

    /**
     * Le nouvel etat de la case
     */
    CaseVideImpl new_case;

    /**
     * @param old_case ancien etat
     * @param new_case nouvel etat
     */
    public Mouvement(CaseVide old_case, CaseVide new_case) {
        this.old_case = old_case;
        this.new_case = new_case;
    }

}