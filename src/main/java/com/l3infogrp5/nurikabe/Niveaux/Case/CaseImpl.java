/**
 * Classe abstraite implémentant une case d'une grille
 */
abstract public class CaseImpl {

    /**
     * La position en x,y d'une case sous forme de vecteur
     */
    Position position;

    /**
     * @param position la position de la case
     */
    public CaseImpl(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

}