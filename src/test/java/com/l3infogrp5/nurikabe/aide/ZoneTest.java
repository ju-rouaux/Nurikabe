package com.l3infogrp5.nurikabe.Aide;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.l3infogrp5.nurikabe.aide.Zone;
import com.l3infogrp5.nurikabe.utils.Matrice;
import com.l3infogrp5.nurikabe.utils.Position;

@TestInstance(Lifecycle.PER_CLASS)
class ZoneTest {

    private Matrice matrice;
    private Zone zone;

    @BeforeAll
    void setup(TestInfo testInfo) {
        System.out.println("\u001B[31m" + "\nTest " + testInfo.getDisplayName() + "\n \u001B[0m");
    }

    @BeforeEach
    void setUp() throws Exception {
        int[][] grille = {
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 3, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, -2, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, -2, -2, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 3, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, -2, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, -2, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        };
        matrice = new Matrice(grille);
        zone = new Zone(matrice);
    }

    @AfterEach
    void afficheOK(TestInfo testInfo) {
        System.out.println("Test OK " + testInfo.getDisplayName());
    }

    @Test
    void testFindZone() {
        Position pos = new Position(1, 1);
        List<Position> listePositions = zone.findZone(pos);

        System.out.println(listePositions);

        assertNotNull(listePositions);
        assertTrue(listePositions.contains(new Position(1, 1)));
        assertTrue(listePositions.contains(new Position(2, 1)));
        assertTrue(listePositions.contains(new Position(3, 1)));
        assertTrue(listePositions.contains(new Position(3, 2)));
    }

    @Test
    void testDecrement() {
        Matrice expectedMatrice = new Matrice(new int[][] {
                { 999, 999, 999, 999, 999, 999, 999, 999, 999, 999 },
                { 999, 0, 999, 999, 999, 999, 999, 999, 999, 999 },
                { 999, 0, 999, 999, 999, 999, 999, 999, 999, 999 },
                { 999, 0, 0, 999, 999, 999, 999, 999, 999, 999 },
                { 999, 999, 999, 999, 999, 999, 999, 999, 999, 999 },
                { 999, 999, 999, 999, 999, 999, 999, 999, 999, 999 },
                { 999, 999, 999, 999, 999, 1, 999, 999, 999, 999 },
                { 999, 999, 999, 999, 999, 1, 999, 999, 999, 999 },
                { 999, 999, 999, 999, 999, 1, 999, 999, 999, 999 },
                { 999, 999, 999, 999, 999, 999, 999, 999, 999, 999 }
        });

        zone.decremente(matrice);

        assertEquals(expectedMatrice, matrice);
    }
}
