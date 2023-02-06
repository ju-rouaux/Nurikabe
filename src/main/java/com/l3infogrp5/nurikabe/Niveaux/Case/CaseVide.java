/**
 * Classe héritant d'une case implémentant une case vide
 * @author Killian Rattier
 * @version 1.0
 */
public class CaseVide extends Case {
    
    /**
     * Etat actuel de la case
     */
    Etat etat_case;

    /**
     * @param position la position de notre case
     * @param etat_case l'état initialisé de notre case
     */
    public CaseVide(Position position, Etat etat_case) {
        super(position);
        this.etat_case = etat_case;
    }

    public Etat getEtat_case() {
        return etat_case;
    }
    
}