/**
 * Classe permetant de stocker un mouvement en stockant l'état initial d'une case et son état modifié
 */
public class MouvementImpl {
    
    /**
     * L'ancien etat de la case modifiée
     */
    CaseVideImpl old_case;

    /**
     * Le nouvel etat de la case
     */
    CaseVideImpl new_case;

    /**
     * @param old_case ancien etat
     * @param new_case nouvel etat
     */
    public MouvementImpl(CaseVideImpl old_case, CaseVideImpl new_case) {
        this.old_case = old_case;
        this.new_case = new_case;
    }

}