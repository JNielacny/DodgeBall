package com.dodgeball;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

// Klasa testowa dla klasy Collectible
public class CollectibleTest {
    private Collectible collectible; // Deklaracja zmiennej collectible

    // Metoda setUp, która uruchamia się przed każdym testem
    @Before
    public void setUp() {
        collectible = new Collectible(100, 100); // Inicjalizacja Collectible przed każdym testem
    }

    // Test sprawdzający inicjalizację obiektu Collectible
    @Test
    public void testInitialization() {
        // Sprawdzenie, czy współrzędna X została poprawnie zainicjalizowana
        assertEquals("X should be initialized to 100", 100, collectible.getX());
        // Sprawdzenie, czy współrzędna Y została poprawnie zainicjalizowana
        assertEquals("Y should be initialized to 100", 100, collectible.getY());
        // Sprawdzenie, czy rozmiar został poprawnie zainicjalizowany
        assertEquals("Size should be initialized to default", 10, collectible.getSize());
        // Sprawdzenie, czy kolor został poprawnie zainicjalizowany
        assertEquals("Color should be initialized to yellow", Color.YELLOW, collectible.getColor());
    }
}
