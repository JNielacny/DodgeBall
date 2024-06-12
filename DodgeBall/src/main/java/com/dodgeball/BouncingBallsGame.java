package com.dodgeball;

import javax.swing.*;
import java.awt.*;

// Klasa BouncingBallsGame rozszerza JFrame
public class BouncingBallsGame extends JFrame {
    final BallPanel ballPanel = new BallPanel(); // Inicjalizacja panelu z piłkami

    // Konstruktor klasy BouncingBallsGame
    public BouncingBallsGame() {
        super("Bouncing Balls Game"); // Ustawienie tytułu okna
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Zamknięcie aplikacji przy zamknięciu okna
        this.setSize(500, 500); // Ustawienie rozmiaru okna
        this.setResizable(false); // Wyłączenie możliwości zmiany rozmiaru okna
        this.setMaximumSize(new Dimension(500, 500)); // Ustawienie maksymalnego rozmiaru okna
        this.add(ballPanel, BorderLayout.CENTER); // Dodanie panelu z piłkami do środka okna
        initializeMenu(); // Inicjalizacja menu
        this.setVisible(true); // Ustawienie okna jako widoczne

        // Uruchomienie timera, który odświeża panel co 10 milisekund
        new Timer(10, e -> ballPanel.repaint()).start();
    }

    // Metoda inicjalizująca menu
    private void initializeMenu() {
        JMenuBar menuBar = new JMenuBar(); // Pasek menu
        JMenu difficultyMenu = new JMenu("Poziom trudności"); // Menu z poziomami trudności
        JMenuItem easy = new JMenuItem("Łatwy"); // Opcja łatwego poziomu
        JMenuItem medium = new JMenuItem("Średni"); // Opcja średniego poziomu
        JMenuItem hard = new JMenuItem("Trudny"); // Opcja trudnego poziomu

        // Dodanie akcji dla opcji łatwego poziomu
        easy.addActionListener(e -> ballPanel.setDifficulty(18, 2));
        // Dodanie akcji dla opcji średniego poziomu
        medium.addActionListener(e -> ballPanel.setDifficulty(15, 5));
        // Dodanie akcji dla opcji trudnego poziomu
        hard.addActionListener(e -> ballPanel.setDifficulty(10, 8));

        // Dodanie opcji do menu poziomów trudności
        difficultyMenu.add(easy);
        difficultyMenu.add(medium);
        difficultyMenu.add(hard);

        // Dodanie menu poziomów trudności do paska menu
        menuBar.add(difficultyMenu);
        setJMenuBar(menuBar); // Ustawienie paska menu dla okna
    }

    // Metoda główna uruchamiająca grę
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BouncingBallsGame::new); // Uruchomienie gry w wątku do obsługi zdarzeń Swing
    }
}
