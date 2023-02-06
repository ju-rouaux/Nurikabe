/**
 * Classe abstraite implémentant une case d'une grille
 */
abstract public class Case {

    /**
     * La position en x,y d'une case sous forme de vecteur
     */
    Position position;

    /**
     * @param position la position de la case
     */
    public Case(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

}