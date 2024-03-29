package com.l3infogrp5.nurikabe.niveau.grille;

import com.l3infogrp5.nurikabe.aide.CasesInatteignables;
import com.l3infogrp5.nurikabe.aide.Zone;
import com.l3infogrp5.nurikabe.niveau.grille.Historique.Mouvement;
import com.l3infogrp5.nurikabe.sauvegarde.Profil;
import com.l3infogrp5.nurikabe.sauvegarde.ModeDeJeu;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Génère une grille de {@link Case} à partir de la matrice donnée.
 * Chaque modification faite sur la matrice sera répercutée automatiquement sur
 * l'affichage des cases, et vice-versa.
 * Pour afficher la grille, utiliser {@link #getPanneau()}.
 * Le panneau renvoyé contient la grille. Il est conseillé de ne pas modifier ce
 * panneau.
 * Il est possible d'ajouter du code qui sera exécuté lorsque la grille est
 * complétée, grâce à la méthode {@link #addOnVictoire(Runnable)}.
 *
 * @author Julien Rouaux
 */
public class Grille {

    /**
     * Vrai si le suivi des mouvements doit être activé.
     */
    private boolean suivre_mouvement;

    private final int nb_lignes;
    private final int nb_colonnes;

    /**
     * Grille interne composée des Properties des cases.
     * Pour récupérer la case liée à la Property, utiliser
     * {@link #getCase(IntegerProperty)}
     */
    private final IntegerProperty[][] grille;
    private final int[][] solution;

    private final ArrayList<Runnable> onVictoire;

    private final BorderPane panneau; // Panneau contenant l'affichage de la grille

    private final Historique histo;

    private final GridPane panneau_grille; // Grille en elle même pour pouvoir la photographier

    /**
     * Créer une grille.
     * Utiliser {@link #getPanneau()} pour récupérer l'affichage de la grille
     *
     * @param matrice    initialisation de la grille.
     * @param solution   l'état final attendu de la grille qui marquera la victoire.
     * @param historique historique des mouvements réalisés sur cette grille.
     */
    public Grille(int[][] matrice, int[][] solution, Historique historique) {

        Case case_courante;

        // Variables d'instance
        this.suivre_mouvement = false;
        this.nb_lignes = matrice.length;
        this.nb_colonnes = matrice[0].length;
        this.solution = solution;
        this.histo = historique;
        this.onVictoire = new ArrayList<>();

        // Créer une GridPane qui contient les cases
        this.panneau_grille = new GridPane();

        // Adapter la taille de la GridPane à son panneau parent.
        // Cela permet de conserver le ratio hauteur-largeur en occupant un maximum
        // d'espace au sein du parent.
        this.panneau = new BorderPane();
        NumberBinding ratio = Bindings.min(
            this.panneau.widthProperty().divide(nb_colonnes),
            this.panneau.heightProperty().divide(nb_lignes));
        panneau_grille.maxWidthProperty().bind(ratio.multiply(nb_colonnes));
        panneau_grille.maxHeightProperty().bind(ratio.multiply(nb_lignes));

        // Définir l'écart entre les cases
        panneau_grille.setVgap(5);
        panneau_grille.setHgap(5);

        // Mettre la grille dans le panneau, les cases sont ajoutées un peu plus loin
        this.panneau.setCenter(panneau_grille);

        // Charger la matrice interne et ses cases
        this.grille = new SimpleIntegerProperty[nb_lignes][nb_colonnes];

        for (int i = 0; i < nb_lignes; i++) {
            for (int j = 0; j < nb_colonnes; j++) {

                // Case numérique
                if (matrice[i][j] > 0) {
                    case_courante = new CaseNumerique(new Position(i, j));

                    case_courante.setEventMaintienGauche(new EventClicMaintenu() {
                        List<Position> positions;

                        public void maintenu(Case c) {
                            positions = CasesInatteignables.atteignablesParCase(new Matrice(getMatrice()), c.getPosition());

                            for (Position p : positions)
                                getCase(grille[p.getX()][p.getY()]).surbrillance(true);
                        }

                        public void relache(Case c) {
                            if (positions != null) {
                                for (Position p : positions)
                                    getCase(grille[p.getX()][p.getY()]).surbrillance(false);
                                positions = null;
                            }
                        }
                    });
                }

                // Case interactive
                else {
                    case_courante = new CaseInteractive(new Position(i, j));

                    case_courante.setEventMaintienGauche(new EventClicMaintenu() {
                        List<Position> positions;

                        public void maintenu(Case c) {
                            positions = new Zone(new Matrice(getMatrice())).findZone(c.getPosition());

                            for (Position p : positions)
                                getCase(grille[p.getX()][p.getY()]).surbrillance(true);
                        }

                        public void relache(Case c) {
                            if (positions != null) {
                                for (Position p : positions)
                                    getCase(grille[p.getX()][p.getY()]).surbrillance(false);
                                positions = null;
                            }
                        }
                    });

                }

                // Ajouter la Property de la case à la matrice des Properties
                this.grille[i][j] = case_courante.etatProperty();

                // Assigner la valeur de la case
                this.grille[i][j].set(matrice[i][j]);

                // Commencer le suivi des mouvements
                this.suivre_mouvement = true;

                // A chaque modification effectuée sur la grille
                this.grille[i][j].addListener((property, ancienEtat, nouvelEtat) -> {
                    // Ajouter la modification à l'historique si cela est autorisé.
                    if (this.suivre_mouvement)
                        this.histo.ajoutMouvement(new Mouvement(this.getCase((IntegerProperty) property).getPosition(),
                            Etat.fromInt(ancienEtat.intValue()), Etat.fromInt(nouvelEtat.intValue())));

                    // Vérifier si le mouvement ne mène pas à la victoire
                    boolean victoire = true;

                    for (int a = 0; a < this.solution.length; a++)
                        for (int b = 0; b < this.solution[a].length; b++) {
                            int v = this.grille[a][b].get();
                            if (v == Etat.POINT.toInt())
                                v = Etat.BLANC.toInt();
                            if (this.solution[a][b] != v) {
                                victoire = false;
                                break;
                            }
                        }
                    if (victoire) {
                        System.out.println("Grille complétée.");
                        for (Runnable r : onVictoire)
                            r.run();
                    }
                });

                // Ajouter la case au panneau grille
                case_courante.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                panneau_grille.add(case_courante, j, i);
                GridPane.setHgrow(case_courante, Priority.ALWAYS);
                GridPane.setVgrow(case_courante, Priority.ALWAYS);
            }
        }
    }

    /**
     * Réinitialiser la grille.
     */
    public void reset() {
        this.suivre_mouvement = false;
        for (int i = 0; i < nb_lignes; i++)
            for (int j = 0; j < nb_colonnes; j++)
                if (this.grille[i][j].get() < 0)
                    this.grille[i][j].set(0);
        this.suivre_mouvement = true;
    }

    /**
     * Retourne la case liée à la Property.
     *
     * @param i la Property de la case à obtenir.
     * @return la case liée à la Property.
     */
    private Case getCase(IntegerProperty i) {
        return ((Case) i.getBean());
    }

    /**
     * Changer l'état d'une case.
     *
     * @param x    coordonnées en x de la case à changer.
     * @param y    coordonnées en y de la case à changer.
     * @param etat nouvel état de la case.
     * @throws IllegalArgumentException lancé lorsque la case ciblée est une case
     *                                  numérique.
     */
    public void set(int x, int y, Etat etat) throws IllegalArgumentException {
        if (this.grille[x][y].get() > 0)
            throw new IllegalArgumentException(
                "Impossible de modifier l'état de la cellule à la position (" + x + ", " + y + ").");

        this.grille[x][y].set(etat.toInt());
    }

    /**
     * Retourne l'état ou la valeur de la case ciblée.
     *
     * @param x coordonnées en x de la case à cibler.
     * @param y coordonnées en y de la case à cibler.
     * @return l'état ou la valeur de la case donnée.
     */
    public int get(int x, int y) {
        return this.grille[x][y].get();
    }

    /**
     * Retourne une copie de la matrice.
     * Aucune modification ne sera répercutée sur la vraie matrice.
     * Pour modifier la vraie matrice, utiliser la méthode set().
     *
     * @return une copie de la matrice.
     * @see Grille#set(int, int, Etat)
     */
    public int[][] getMatrice() {
        int[][] copie = new int[this.grille.length][this.grille[0].length];

        for (int i = 0; i < this.grille.length; i++)
            for (int j = 0; j < this.grille[i].length; j++)
                copie[i][j] = this.grille[i][j].get();

        return copie;
    }

    /**
     * Retourne le panneau contenant la grille.
     * Il est conseillé de ne pas modifier ce panneau.
     *
     * @return le panneau contenant la grille.
     */
    public Pane getPanneau() {
        return this.panneau;
    }

    /**
     * Annule le dernier mouvement du joueur.
     * Cette méthode désactive le suivi des mouvements pour ne pas enregistrer des
     * mouvements réalisés par cette dernière.
     */
    public void undo() {
        if (histo.peutAnnuler()) {
            this.suivre_mouvement = false; // Désactiver le suivi des mouvements
            Mouvement m = histo.annuler();
            this.grille[m.getPosition().getX()][m.getPosition().getY()].set(m.getAncienEtat().toInt());
            this.suivre_mouvement = true; // Réactiver le suivi des mouvements
        }
    }

    /**
     * Rétabli le dernier mouvement du joueur.
     * Cette méthode désactive le suivi des mouvements pour ne pas enregistrer des
     * mouvements réalisés par cette dernière.
     */
    public void redo() {
        if (histo.peutRetablir()) {
            this.suivre_mouvement = false; // Désactiver le suivi des mouvements
            Mouvement m = histo.retablir();
            this.grille[m.getPosition().getX()][m.getPosition().getY()].set(m.getNouvelEtat().toInt());
            this.suivre_mouvement = true; // Réactiver le suivi des mouvements
        }
    }

    /**
     * Active ou désactive l’interaction du joueur avec les cases.
     *
     * @param b vrai si les cases doivent être activées, faux si désactivées.
     */
    public void setActivation(boolean b) {
        for (int i = 0; i < this.grille.length; i++)
            for (int j = 0; j < this.grille[i].length; j++)
                this.getCase(this.grille[i][j]).setDisable(!b);
    }

    /**
     * Ajouter un événement à exécuter lorsque la grille est complétée.
     *
     * @param r événement à exécuter lorsque la grille est complétée.
     */
    public void addOnVictoire(Runnable r) {
        this.onVictoire.add(r);
    }

    /**
     * Retirer un événement exécuté lorsque la grille est complétée.
     *
     * @param r l'événement à retirer.
     */
    public void removeOnVictoire(Runnable r) {
        this.onVictoire.remove(r);
    }

    /**
     * Retourne l'historique du niveau.
     *
     * @return l'historique du niveau.
     */
    public Historique getHistorique() {
        return this.histo;
    }

    /**
     * Retourne la solution du niveau.
     *
     * @return la solution du niveau.
     */
    public int[][] getSolution() {
        return this.solution;
    }

    /**
     * Réalise une capture d'écran de la grille et la sauvegarde à l'emplacement
     * donné.
     *
     * @param emplacement l'emplacement de sauvegarde de la capture.
     * @return vrai la capture a été sauvegardée, faux sinon.
     */
    public boolean capturerGrille(String emplacement) {
        if (Profil.getModeDeJeu() != ModeDeJeu.SANSFIN) {
            double old_Vgap = panneau_grille.getVgap();
            double old_Hgap = panneau_grille.getHgap();

            File file = new File(emplacement);

            this.panneau_grille.setGridLinesVisible(true);
            panneau_grille.setVgap(0);
            panneau_grille.setHgap(0);
            ImageView img = new ImageView(this.panneau_grille.snapshot(null, null));
            this.panneau_grille.setGridLinesVisible(false);
            panneau_grille.setVgap(old_Vgap);
            panneau_grille.setHgap(old_Hgap);

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(img.getImage(), null), "png", file);
            } catch (Exception e) {
                return false;
            }
        }

        return true;
    }

    /**
     * Retourne le nombre d'erreurs dans la grille.
     * 
     * @return le nombre d'erreurs dans la grille.
     */
    public int nbErreurs() {
        int nb = 0;

        for (int i = 0; i < this.solution.length; i++)
            for (int j = 0; j < this.solution[j].length; j++) {
                int v = this.grille[i][j].get();
                if (v == Etat.BLANC.toInt()) // Ignorer les cases qui n'ont pas été coloriées
                    continue;

                if (v == Etat.POINT.toInt())
                    v = Etat.BLANC.toInt();
                if (this.solution[i][j] != v)
                    nb++;
            }

        return nb;
    }

    /**
     * Met en surbrillance la case à la position donnée.
     * 
     * @param p la position de la case à mettre en surbrillance.
     * @param b vrai si la case doit être mise en surbrillance, faux sinon.
     */
    public void surbrillance(Position p, boolean b) {
        if (p != null && p.getX() >= 0 && p.getX() < this.nb_lignes && p.getY() >= 0 && p.getY() < this.nb_colonnes)
            this.getCase(this.grille[p.getX()][p.getY()]).surbrillance(b);
    }
}
