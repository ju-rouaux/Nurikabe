/**
 * Classe héritant d'une case implémentant une case numérique
 */
public class CaseNum extends Case {

    /**
     * Le nombre affecté à notre case numérique
     */
    int valeur;

    /**
     * @param position la position dans la grille de notre case 
     * @param valeur la valeur numérique de notre case
     */
    public CaseNum(Position position, int valeur) {
        super(position);
        this.valeur = valeur;
    }

    public int getValeur() {
        return valeur;
    }

}