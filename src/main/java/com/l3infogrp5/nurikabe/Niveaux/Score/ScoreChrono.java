import java.util.Timer;

abstract public class ScoreChrono implements ScoreInterface{

    Timer tps;

    public ScoreChrono(Timer tps) {
        this.tps = tps;
    }

    public void calcul(){}

    @Override
    public void aideUtilise() {}
    
}
