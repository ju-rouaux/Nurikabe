import java.util.Timer;

/**
* Implémentatio, du calcul du score selon le chronomètre
* @author Antoine Couapel, Killian Rattier
* @version 1.0
*/
abstract public class ScoreChrono implements ScoreInterface{

    Timer tps;

    public ScoreChrono(Timer tps) {
        this.tps = tps;
    }

    public void calcul(){}

    @Override
    public void aideUtilise() {}
    
}
