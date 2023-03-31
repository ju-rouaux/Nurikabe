/**
 * Classe implémentant un niveau.
 * Un niveau à une grille, un score et un numéro qui lui est affecté
 */
public class NiveauImpl {

    GrilleImpl niveau;
    ScoreInterface score;
    private int numero = 0;

    public NiveauImpl(GrilleImpl niveau) {
        this.niveau = niveau;
        this.score = new ScoreZenImpl();
        this.numero = numero++;
    }

}