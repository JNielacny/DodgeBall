package com.dodgeball;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

// Klasa testowa dla klasy BouncingBallsGame
public class BouncingBallsGameTest {
    private BouncingBallsGame game; // Deklaracja zmiennej game

    // Metoda setUp, która uruchamia się przed każdym testem
    @Before
    public void setUp() {
        game = new BouncingBallsGame(); // Inicjalizacja BouncingBallsGame przed każdym testem
    }

    // Test sprawdzający właściwości ramki okna
    @Test
    public void testFrameProperties() {
        // Sprawdzenie, czy tytuł ramki to 'Bouncing Balls Game'
        assertEquals("Frame title should be 'Bouncing Balls Game'", "Bouncing Balls Game", game.getTitle());
        // Sprawdzenie, czy ramka nie jest możliwa do zmiany rozmiaru
        assertFalse("Frame should not be resizable", game.isResizable());
        // Sprawdzenie, czy domyślna operacja zamknięcia to EXIT_ON_CLOSE
        assertEquals("Frame default close operation should be EXIT_ON_CLOSE", JFrame.EXIT_ON_CLOSE, game.getDefaultCloseOperation());
    }

    // Test sprawdzający konfigurację menu
    @Test
    public void testMenuSetup() {
        // Pobranie pierwszego menu z paska menu
        JMenu difficultyMenu = game.getJMenuBar().getMenu(0);
        // Sprawdzenie, czy menu z poziomem trudności zostało utworzone
        assertNotNull("Difficulty menu should be created", difficultyMenu);
        // Sprawdzenie, czy menu z poziomem trudności zawiera 3 pozycje
        assertEquals("Difficulty menu should have 3 items", 3, difficultyMenu.getItemCount());
    }

    // Test sprawdzający ustawienie poziomu trudności
    @Test
    public void testDifficultySetting() {
        // Pobranie drugiej pozycji menu (zakładamy, że 'Medium' jest drugą pozycją)
        JMenuItem medium = game.getJMenuBar().getMenu(0).getItem(1); // Zakładamy, że 'Medium' jest drugą pozycją
        medium.doClick(); // Symulacja kliknięcia w opcję 'Medium'
        // Sprawdzenie, czy rozmiar trudności został ustawiony na 'Medium'
        assertEquals("Difficulty should be set to Medium", 15, game.ballPanel.difficultySize);
        // Sprawdzenie, czy prędkość trudności została ustawiona na 'Medium'
        assertEquals("Speed should be set to Medium speed", 5, game.ballPanel.difficultySpeed);
    }
}
