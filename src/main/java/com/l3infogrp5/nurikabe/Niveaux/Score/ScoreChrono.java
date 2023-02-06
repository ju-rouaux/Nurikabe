import java.util.Timer;

abstract public class ScoreChronoImpl implements ScoreInterface{

    Timer tps;

    public ScoreChronoImpl(Timer tps) {
        this.tps = tps;
    }

    public void calcul(){}

    @Override
    public void aideUtilise() {}
    
}
