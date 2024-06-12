package com.dodgeball;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Klasa testowa dla klasy Ball
public class BallTest {
    private Ball ball; // Deklaracja zmiennej ball

    // Metoda setUp, która uruchamia się przed każdym testem
    @Before
    public void setUp() {
        // Inicjalizacja obiektu Ball przed każdym testem
        // Zakładając, że konstruktor Ball(panel, x, y, vx, vy)
        ball = new Ball(null, 50, 50, 5, 5);
    }

    // Test sprawdzający początkową pozycję piłki
    @Test
    public void testInitialPosition() {
        // Sprawdzenie, czy początkowa współrzędna X piłki wynosi 50
        assertEquals("Initial X should be 50", 50, ball.getX());
        // Sprawdzenie, czy początkowa współrzędna Y piłki wynosi 50
        assertEquals("Initial Y should be 50", 50, ball.getY());
    }
}
