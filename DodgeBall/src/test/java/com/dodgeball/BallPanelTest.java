package com.dodgeball;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

// Klasa testowa dla klasy BallPanel
public class BallPanelTest {
    private BallPanel panel; // Deklaracja zmiennej panel

    // Metoda setUp, która uruchamia się przed każdym testem
    @Before
    public void setUp() {
        panel = new BallPanel(); // Inicjalizacja BallPanel przed każdym testem
    }

    // Test sprawdzający tworzenie piłek
    @Test
    public void testCreateBalls() {
        panel.createBalls(5); // Tworzenie 5 dodatkowych piłek
        assertEquals("Number of balls created should be 12", 12, panel.balls.size()); // Sprawdzenie, czy liczba piłek wynosi 12 (7 z konstruktora + 5 z testu)
    }

    // Test sprawdzający tworzenie przedmiotów do zebrania
    @Test
    public void testCreateCollectibles() {
        panel.createCollectibles(3); // Tworzenie 3 dodatkowych przedmiotów do zebrania
        assertEquals("Number of collectibles created should be 7", 7, panel.collectibles.size()); // Sprawdzenie, czy liczba przedmiotów wynosi 7 (4 z konstruktora + 3 z testu)
    }

    // Test sprawdzający ustawianie poziomu trudności
    @Test
    public void testSetDifficulty() {
        panel.setDifficulty(20, 3); // Ustawienie trudności na rozmiar 20 i prędkość 3
        assertEquals("Difficulty size should be set to 20", 20, panel.difficultySize); // Sprawdzenie, czy rozmiar trudności wynosi 20
        assertEquals("Difficulty speed should be set to 3", 3, panel.difficultySpeed); // Sprawdzenie, czy prędkość trudności wynosi 3
    }
}
