/**
 * Classe implémentant un niveau.
 * Un niveau à une grille, un score et un numéro qui lui est affecté
 * @author Killian Rattier
 * @version 1.0
 */
public class Niveau {

    Grille niveau;
    ScoreInterface score;
    private int numero = 0;

    public Niveau(Grille niveau) {
        this.niveau = niveau;
        this.score = new ScoreZen();
        this.numero = numero++;
    }

}