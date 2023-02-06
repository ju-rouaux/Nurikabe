import java.util.*;

/**
 * Classe implémentant une grille contenant un ensemble de cases et stockant les mouvements
 * effectués sur celle-ci
 */
public class Grille {


    /**
     * La liste des mouvements effectués sur la grille sous forme de liste
     */
    LinkedList<Mouvement> mouvements = new LinkedList<Mouvement>();

    /**
     * Le plateau contenant toutes les cases de la grille
     */
    Case[][] plateau;

    /**
     * Tailles en x et y de la matrice
     */
    int tx, ty;

    public Grille() {
       this.plateau = fillGrid(this.plateau, "fic");
    }

    /**
     * Méthode remplissant un plateau à l'aide d'un fichier.
     */
    private Case[][] fillGrid(Case[][] plateau, String fichier) {
        int nb = 0;
        int temp = 10;
        for(int i = 0; i < tx; i++){
            for(int j = 0; j < ty; j++){
                Position pos = new Position(i,j);
                if(nb == 0){
                    plateau[i][j] = new CaseNum(pos,temp);
                } else if(nb == 1){
                    plateau[i][j] = new CaseVide(pos,Etat.VIDE);
                } else if(nb == 2){
                    plateau[i][j] = new CaseVide(pos,Etat.POINT);
                } else if(nb == 3){
                    plateau[i][j] = new CaseVide(pos,Etat.PLEIN);
                }
            }
        }
        return plateau;
    }

    /**
     * Méthode de convertion de la matrice actuelle en matrice d'entiers
     * #0 : numérique
     * #1 : vide
     * #2 : point
     * #3 : plein
     * @param plateau la grille de jeu
     * @return la matrice d'entier comportant les informations converties
     */
    public int[][] toIntMat(Case[][] plateau){

        int[][] mat = new int[tx][ty];
        
        for(int i = 0; i < tx; i++){
            for(int j = 0; j < ty; j++){
                if(plateau[i][j] instanceof CaseNum){
                    mat[i][j] = 0;
                } else if(plateau[i][j] instanceof CaseVide){
                    CaseVide temp = (CaseVide) plateau[i][j];
                    if(temp.etat_case.equals(Etat.VIDE)){
                        mat[i][j] = 1;
                    } else if(temp.etat_case.equals(Etat.POINT)){
                        mat[i][j] = 2;
                    } else if(temp.etat_case.equals(Etat.PLEIN)){
                        mat[i][j] = 3;
                    }
                }
            }
        }

        return mat;
    }
}