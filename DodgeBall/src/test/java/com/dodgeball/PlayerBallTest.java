package com.dodgeball;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

// Klasa testowa dla klasy PlayerBall
public class PlayerBallTest {
    private PlayerBall playerBall; // Deklaracja zmiennej playerBall
    private int initialX; // Deklaracja początkowej współrzędnej X
    private int initialY; // Deklaracja początkowej współrzędnej Y
    private int speed; // Zakładamy, że speed jest polem w PlayerBall, odzwierciedlającym, jak bardzo zmienia się pozycja.

    // Metoda setUp, która uruchamia się przed każdym testem
    @Before
    public void setUp() {
        speed = 5; // Zakładamy, że prędkość to 5 jednostek na ruch.
        initialX = 250; // Środek obszaru 500x500
        initialY = 250;
        playerBall = new PlayerBall(new Panel(), 20); // Komponent nadrzędny, średnica piłki
        playerBall.setX(initialX); // Ustawienie początkowej współrzędnej X piłki
        playerBall.setY(initialY); // Ustawienie początkowej współrzędnej Y piłki
    }

    // Test sprawdzający ruch w lewo
    @Test
    public void testMoveLeft() {
        playerBall.moveLeft(); // Wykonanie ruchu w lewo
        // Sprawdzenie, czy współrzędna X została zmniejszona o wartość speed
        assertEquals("Player should move left by speed units", initialX - speed, playerBall.getX());
    }

    // Test sprawdzający ruch w prawo
    @Test
    public void testMoveRight() {
        playerBall.moveRight(); // Wykonanie ruchu w prawo
        // Sprawdzenie, czy współrzędna X została zwiększona o wartość speed
        assertEquals("Player should move right by speed units", initialX + speed, playerBall.getX());
    }

    // Test sprawdzający ruch w górę
    @Test
    public void testMoveUp() {
        playerBall.moveUp(); // Wykonanie ruchu w górę
        // Sprawdzenie, czy współrzędna Y została zmniejszona o wartość speed
        assertEquals("Player should move up by speed units", initialY - speed, playerBall.getY());
    }

    // Test sprawdzający ruch w dół
    @Test
    public void testMoveDown() {
        playerBall.moveDown(); // Wykonanie ruchu w dół
        // Sprawdzenie, czy współrzędna Y została zwiększona o wartość speed
        assertEquals("Player should move down by speed units", initialY + speed, playerBall.getY());
    }
}
