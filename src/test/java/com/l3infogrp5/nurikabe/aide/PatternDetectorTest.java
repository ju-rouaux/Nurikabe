package com.l3infogrp5.nurikabe.Aide;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.l3infogrp5.nurikabe.aide.PatternDetector;
import com.l3infogrp5.nurikabe.utils.Position;

@TestInstance(Lifecycle.PER_CLASS)
public class PatternDetectorTest {

        @BeforeAll
        void setup(TestInfo testInfo) {
                System.out.println("\u001B[31m" + "\nTest " + testInfo.getDisplayName() + "\n \u001B[0m");
        }

        @AfterEach
        void afficheOK(TestInfo testInfo) {
                System.out.println("Test OK " + testInfo.getDisplayName());
        }

        @Test
        void test_detect_num() {
                int[][] grille = {
                                { 8, 0, 0 },
                                { 0, -1, 0 },
                                { 0, 0, 9 }
                };

                int[][] pattern = {
                                { 666, 0, 0 },
                                { 0, -1, 0 },
                                { 0, 0, 666 }
                };

                PatternDetector patternDetector = new PatternDetector(pattern);
                ArrayList<Position> patternPositions = patternDetector.detectNum(grille);

                assertEquals(9, patternPositions.size());
                assertTrue(patternPositions.contains(new Position(0, 0)));
                assertTrue(patternPositions.contains(new Position(1, 1)));
                assertTrue(patternPositions.contains(new Position(2, 2)));
        }

        @Test
        void test_detect_num_large_grid() {
                int[][] grille = {
                                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 8, 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0, -1, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0, 0, 9, 0, 0, 0 },
                                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 8, 0, 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, -1, 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0, 9, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
                };

                int[][] pattern = {
                                { 666, 0, 0 },
                                { 0, -1, 0 },
                                { 0, 0, 666 }
                };

                PatternDetector patternDetector = new PatternDetector(pattern);
                ArrayList<Position> patternPositions = patternDetector.detectNum(grille);

                assertEquals(9, patternPositions.size());

                assertTrue(patternPositions.contains(new Position(1, 4)));
                assertTrue(patternPositions.contains(new Position(1, 5)));
                assertTrue(patternPositions.contains(new Position(1, 6)));
                assertTrue(patternPositions.contains(new Position(2, 4)));
                assertTrue(patternPositions.contains(new Position(2, 5)));
        }

        @Test
        void test_small_pattern() {
                int[][] pattern1 = {
                                { 1, 0 },
                                { 0, 1 }
                };

                int[][] grid1 = {
                                { 1, 0, 0 },
                                { 0, 1, 1 },
                                { 0, 1, 1 }
                };

                PatternDetector detector1 = new PatternDetector(pattern1);
                ArrayList<Position> locations1 = detector1.detectInGrid(grid1);
                new Position(0, 0).equals(locations1.get(0));
        }

        @Test
        void test_medium_pattern() {
                int[][] pattern2 = {
                                { 1, 0, 1 },
                                { 1, 0, 1 },
                                { 1, 1, 1 }
                };

                int[][] grid2 = {
                                { 0, 0, 0, 0, 0 },
                                { 0, 1, 0, 1, 0 },
                                { 0, 1, 0, 1, 0 },
                                { 0, 1, 1, 1, 1 },
                                { 0, 0, 0, 0, 0 }
                };

                PatternDetector detector2 = new PatternDetector(pattern2);
                ArrayList<Position> locations2 = detector2.detectInGrid(grid2);
                new Position(1, 1).equals(locations2.get(0));
        }

        @Test
        void test_large_pattern() {
                int[][] pattern3 = {
                                { 1, 1, 1, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 0, 0, 1 },
                                { 1, 1, 1, 1 }
                };

                int[][] grid3 = {
                                { 0, 0, 0, 0, 0, 0, 0 },
                                { 0, 1, 1, 1, 1, 0, 0 },
                                { 0, 1, 0, 0, 1, 0, 0 },
                                { 0, 1, 0, 0, 1, 0, 0 },
                                { 0, 1, 1, 1, 1, 1, 0 },
                                { 0, 0, 0, 0, 0, 0, 0 }
                };

                PatternDetector detector3 = new PatternDetector(pattern3);
                ArrayList<Position> locations3 = detector3.detectInGrid(grid3);
                new Position(1, 1).equals(locations3.get(0));
        }

        @Test
        void test_multiple_pattern() {
                int[][] pattern = {
                                { 1, 0, 1 },
                                { 1, 0, 1 },
                                { 1, 1, 1 }
                };

                int[][] grid = {
                                { 1, 0, 1, 0, 1 },
                                { 1, 0, 1, 0, 1 },
                                { 1, 1, 1, 1, 1 },
                                { 0, 0, 0, 0, 0 },
                                { 0, 1, 0, 1, 0 },
                                { 0, 1, 0, 1, 0 },
                                { 0, 1, 1, 1, 1 },
                                { 0, 0, 0, 0, 0 }
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);

                new Position(1, 1).equals(locations.get(0));
                new Position(0, 2).equals(locations.get(1));
                new Position(4, 1).equals(locations.get(2));
        }

        @Test
        void test_no_occurence() {
                int[][] pattern = {
                                { 1, 1 },
                                { 1, 0 },
                };

                int[][] grid = {
                                { 0, 0, 0 },
                                { 0, 1, 1 },
                                { 0, 1, 1 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                assertEquals(0, locations.size());
        }

        @Test
        void test_single_occurence() {
                int[][] pattern = {
                                { 1, 1 },
                                { 1, 0 },
                };

                int[][] grid = {
                                { 0, 0, 0 },
                                { 0, 1, 1 },
                                { 0, 1, 0 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                new Position(1, 1).equals(locations.get(0));
        }

        @Test
        void test_multiple_occurences_linked() {
                int[][] pattern = {
                                { 1, 1 },
                                { 1, 0 },
                };

                int[][] grid = {
                                { 0, 0, 0, 0 },
                                { 0, 1, 1, 0 },
                                { 0, 1, 0, 0 },
                                { 0, 1, 1, 0 },
                                { 0, 0, 0, 0 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                new Position(1, 1).equals(locations.get(0));
                new Position(2, 1).equals(locations.get(1));
        }

        @Test
        void test_pattern_not_square() {
                int[][] pattern = {
                                { 1, 1 },
                                { 1, 0 },
                                { 0, 0 },
                };

                int[][] grid = {
                                { 0, 0, 0 },
                                { 0, 1, 1 },
                                { 0, 1, 0 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                assertEquals(0, locations.size());
        }

        @Test
        void test_pattern_larger_than_grid() {
                int[][] pattern = {
                                { 1, 1, 1, 1 },
                                { 1, 0, 0, 0 },
                                { 1, 0, 0, 1 },
                };

                int[][] grid = {
                                { 0, 0, 0 },
                                { 0, 1, 1 },
                                { 0, 1, 0 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                assertEquals(0, locations.size());
        }

        @Test
        void test_pattern_detection_identical_dimensions() {
                int[][] pattern = {
                                { 1, 1, 1 },
                                { 1, 0, 0 },
                                { 1, 0, 0 },
                };

                int[][] grid = {
                                { 0, 0, 0 },
                                { 0, 1, 1 },
                                { 0, 1, 0 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                assertEquals(0, locations.size());
        }

        @Test
        void test_pattern_detection_different_content() {
                int[][] pattern = {
                                { 1, 1, 1 },
                                { 1, 0, 0 },
                                { 0, 1, 0 },
                };

                int[][] grid = {
                                { 0, 0, 0, 0 },
                                { 0, 1, 1, 0 },
                                { 0, 1, 0, 0 },
                                { 0, 0, 0, 1 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                assertEquals(0, locations.size());
        }

        @Test
        void test_pattern_detection_smaller_grid() {
                int[][] pattern = {
                                { 1, 1, 1 },
                                { 1, 0, 0 },
                                { 0, 1, 0 },
                };

                int[][] grid = {
                                { 0, 0, 0 },
                                { 0, 1, 1 },
                                { 0, 1, 0 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                assertEquals(0, locations.size());
        }

        @Test
        void test_pattern_detection_larger_grid() {
                int[][] pattern = {
                                { 1, 1, 1 },
                                { 1, 0, 0 },
                                { 0, 1, 0 },
                };

                int[][] grid = {
                                { 0, 0, 0, 0, 0 },
                                { 0, 1, 1, 1, 0 },
                                { 0, 1, 0, 0, 0 },
                                { 0, 0, 1, 0, 1 },
                                { 0, 0, 0, 1, 1 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                new Position(1, 1).equals(locations.get(0));
        }

        @Test
        void test_pattern_detection_negative_or_empty_cells() {
                int[][] pattern = {
                                { 1, 1, 1 },
                                { 1, 0, 0 },
                                { 0, 1, 0 },
                };

                int[][] grid = {
                                { 0, 0, 0 },
                                { 0, 1, -1 },
                                { 0, 0, 0 },
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                assertEquals(0, locations.size());
        }

        @Test
        void test_pattern_detection_1x1() {
                int[][] pattern = {
                                { 1 }
                };

                int[][] grid = {
                                { 1 }
                };

                PatternDetector detector = new PatternDetector(pattern);
                ArrayList<Position> locations = detector.detectInGrid(grid);
                new Position(0, 0).equals(locations.get(0));
        }

        // @Test
        // void testDetectInGrid() {

        // int[][] pattern3 = { { 0, 0 }, { 0, 0 } };
        // int[][] grid3 = { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } };
        // PatternDetector detector3 = new PatternDetector(pattern3);
        // ArrayList<int[]> locations3 = detector3.detectInGrid(grid3);
        // assertEquals(4, locations3.size());
        // assertArrayEquals(new int[] { 0, 0 }, locations3.get(0));
        // assertArrayEquals(new int[] { 0, 2 }, locations3.get(1));
        // assertArrayEquals(new int[] { 2, 0 }, locations3.get(2));
        // assertArrayEquals(new int[] { 2, 2 }, locations3.get(3));
        // }
}