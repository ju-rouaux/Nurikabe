public class ScoreZenImpl implements ScoreInterface {
    float etoiles;

    public ScoreZen(float etoiles) {
        this.etoiles = etoiles;
    }

    /*
     *Réduction du nombre d'étoiles si une aide est utilisée
     */
    @Override
    public void aideUtilise() {
        
        if(etoiles > 1)
            this.etoiles--;
        
    }

    
}