package com.l3infogrp5.nurikabe.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.l3infogrp5.nurikabe.pattern_matching.*;
import com.l3infogrp5.nurikabe.utils.Matrice;

/**
 * Unit test for simple App.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class MatriceTest {

    Matrice matrice;

    @BeforeEach
    public void setUp() {
        int[][] elements = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        matrice = new Matrice(elements);
    }

    @AfterEach
    void afficheOK(TestInfo testInfo) {
        System.out.println("Test OK " + testInfo.getDisplayName());
    }

    @Test
    public void testEstValide() {
        assertTrue(matrice.estValide());
        Matrice matriceInvalide = new Matrice(new int[][] { {}, {} });
        assertFalse(matriceInvalide.estValide());
    }

    @Test
    public void testEstCarree() {
        assertTrue(matrice.estCarree());
        int[][] elements2 = { { 1, 2, 3 }, { 4, 5, 6 } };
        Matrice matrice2 = new Matrice(elements2);
        assertFalse(matrice2.estCarree());
    }

    @Test
    public void testGetMatrice() {
        int[][] expectedElements = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        Matrice expectedMatrice = new Matrice(expectedElements);
        assertEquals(expectedMatrice, matrice.getMatrice());
    }

    @Test
    public void testGetLignes() {
        assertEquals(3, matrice.getNbLignes());
    }

    @Test
    public void testGetColonnes() {
        assertEquals(3, matrice.getNbColonnes());
    }

    @Test
    public void testGetElement() {
        assertEquals(1, matrice.getElement(0, 0));
        assertEquals(2, matrice.getElement(0, 1));
        assertEquals(4, matrice.getElement(1, 0));
        assertEquals(5, matrice.getElement(1, 1));
    }

    @Test
    public void testSetElement() {
        matrice.setElement(0, 0, 5);
        assertEquals(5, matrice.getElement(0, 0));
    }

    @Test
    public void testValeurPresente() {
        assertTrue(matrice.valeurPresente(5));
        assertFalse(matrice.valeurPresente(10));
    }

    @Test
    public void testMin() {
        assertEquals(1, matrice.min());
    }

    @Test
    public void testMax() {
        assertEquals(9, matrice.max());
    }

    @Test
    public void testIndex1d() {
        assertEquals(0, matrice.index1d(1));
        assertEquals(1, matrice.index1d(2));
        assertEquals(8, matrice.index1d(9));
        assertEquals(-1, matrice.index1d(10));
    }

    @Test
    public void testTo1D() {
        int index = matrice.to1D(1, 2);
        Assertions.assertEquals(5, index);
    }

    @Test
    public void testIndex2d() {
        assertArrayEquals(new int[] { 0, 0 }, matrice.index2d(1));
        assertArrayEquals(new int[] { 2, 1 }, matrice.index2d(8));
        assertArrayEquals(new int[] { 2, 2 }, matrice.index2d(9));
        assertNull(matrice.index2d(10));
    }

    @Test
    public void testAddition() {
        int[][] elements2 = { { 5, 9, 7 }, { 8, 6, 4 }, { 3, 2, 5 } };
        Matrice matrice2 = new Matrice(elements2);
        Matrice resultat = matrice.additionner(matrice2);
        assertEquals(6, resultat.getElement(0, 0));
        assertEquals(11, resultat.getElement(0, 1));
        assertEquals(12, resultat.getElement(1, 0));
        assertEquals(11, resultat.getElement(1, 1));
    }

    @Test
    public void testMultiplication() {
        int[][] elements2 = { { 22, 58, 7 }, { 4, 5, 9 }, { 12, 48, 1 } };
        Matrice matrice2 = new Matrice(elements2);
        Matrice resultat = matrice.multiplier(matrice2);
        assertEquals(66, resultat.getElement(0, 0));
        assertEquals(212, resultat.getElement(0, 1));
        assertEquals(180, resultat.getElement(1, 0));
        assertEquals(545, resultat.getElement(1, 1));
    }

    @Test
    public void testMemesTailleTrue() {
        Matrice m1 = new Matrice(new int[][] { { 1, 2 }, { 3, 4 } });
        Matrice m2 = new Matrice(new int[][] { { 5, 6 }, { 7, 8 } });
        assertTrue(Matrice.memesTaille(m1, m2));
    }

    @Test
    public void testMemesTailleFalse() {
        Matrice m1 = new Matrice(new int[][] { { 1, 2, 3 }, { 4, 5, 6 } });
        Matrice m2 = new Matrice(new int[][] { { 7, 8 }, { 9, 10 } });
        assertFalse(Matrice.memesTaille(m1, m2));
    }

    @Test
    public void testMultipliableTrue() {
        Matrice m1 = new Matrice(new int[][] { { 1, 2, 3 }, { 4, 5, 6 } });
        Matrice m2 = new Matrice(new int[][] { { 7, 8 }, { 9, 10 }, { 11, 12 } });
        assertTrue(Matrice.multipliable(m1, m2));
    }

    @Test
    public void testMultipliableFalse() {
        Matrice m1 = new Matrice(new int[][] { { 1, 2 }, { 3, 4 } });
        Matrice m2 = new Matrice(new int[][] { { 5, 6 }, { 7, 8 }, { 11, 12 } });
        assertFalse(Matrice.multipliable(m1, m2));
    }

    @Test
    public void testRotation90() {
        int[][] expectedElements = { { 7, 4, 1 }, { 8, 5, 2 }, { 9, 6, 3 } };
        Matrice expectedMatrice = new Matrice(expectedElements);
        assertEquals(expectedMatrice, matrice.rotation90());
    }

    @Test
    public void testRotations() {
        ArrayList<Matrice> rotations = matrice.rotations();

        int[][] expectedElements1 = { { 7, 4, 1 }, { 8, 5, 2 }, { 9, 6, 3 } };
        int[][] expectedElements2 = { { 9, 8, 7 }, { 6, 5, 4 }, { 3, 2, 1 } };
        int[][] expectedElements3 = { { 3, 6, 9 }, { 2, 5, 8 }, { 1, 4, 7 } };

        assertEquals(new Matrice(expectedElements1), rotations.get(1).getMatrice());
        assertEquals(new Matrice(expectedElements2), rotations.get(2).getMatrice());
        assertEquals(new Matrice(expectedElements3), rotations.get(3).getMatrice());
    }

    @Test
    public void testRemplir() {
        matrice.remplir(10);
        int[][] expectedElements = { { 10, 10, 10 }, { 10, 10, 10 }, { 10, 10, 10 } };
        Matrice expectedMatrice = new Matrice(expectedElements);
        assertEquals(expectedMatrice, matrice.getMatrice());
    }

    @Test
    public void testRemplacerValeurs() {
        matrice.remplacerValeurs(2, 10);
        int[][] expectedElements = { { 1, 10, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        Matrice expectedMatrice = new Matrice(expectedElements);
        assertEquals(expectedMatrice, matrice.getMatrice());
    }

    @Test
    public void testAjouterLigne() {
        matrice.ajouterLigne(10);
        int[][] expectedElements = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 10, 10 } };
        Matrice expectedMatrice = new Matrice(expectedElements);
        assertEquals(expectedMatrice, matrice.getMatrice());
    }

    @Test
    public void testAjouterColonne() {
        matrice.ajouterColonne(10);
        int[][] expectedElements = { { 1, 2, 3, 10 }, { 4, 5, 6, 10 }, { 7, 8, 9, 10 } };
        Matrice expectedMatrice = new Matrice(expectedElements);
        assertEquals(expectedMatrice, matrice.getMatrice());
    }

    @Test
    public void testEnleverLigne() {
        matrice.enleverLigne(1);
        int[][] expectedElements = { { 1, 2, 3 }, { 7, 8, 9 } };
        Matrice expectedMatrice = new Matrice(expectedElements);
        assertEquals(expectedMatrice, matrice.getMatrice());
    }

    @Test
    public void testEnleverColonne() {
        matrice.enleverColonne(1);
        int[][] expectedElements = { { 1, 3 }, { 4, 6 }, { 7, 9 } };
        Matrice expectedMatrice = new Matrice(expectedElements);
        assertEquals(expectedMatrice, matrice.getMatrice());
    }

    @Test
    public void testToString() {
        String resultatAttendu = "1 2 3 \n4 5 6 \n7 8 9 \n";
        assertEquals(resultatAttendu, matrice.toString());
    }

    @Test
    public void testEquals() {
        int[][] elements2 = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        Matrice matrice2 = new Matrice(elements2);
        assertTrue(matrice.equals(matrice2));
    }

    @Test
    public void testClone() {
        Matrice matriceClone = matrice.clone();
        assertTrue(matrice.equals(matriceClone));
    }

}
