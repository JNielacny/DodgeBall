package com.dodgeball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Klasa BallPanel rozszerza JPanel i implementuje KeyListener do obsługi klawiatury
public class BallPanel extends JPanel implements KeyListener {
    final List<Ball> balls = new ArrayList<>(); // Lista piłek
    final List<Collectible> collectibles = new ArrayList<>(); // Lista przedmiotów do zebrania
    final PlayerBall playerBall; // Piłka gracza
    int lives = 3; // Liczba żyć gracza
    private boolean gameOver = false; // Flaga końca gry
    private String difficultyLevel = "Easy"; // Domyślny poziom trudności
    int difficultySize = 18; // Domyślny rozmiar trudności
    int difficultySpeed = 2; // Domyślna prędkość trudności

    private final ExecutorService executorService = Executors.newCachedThreadPool(); // ExecutorService do zarządzania wątkami

    // Konstruktor klasy BallPanel
    public BallPanel() {
        setPreferredSize(new Dimension(500, 500)); // Ustaw preferowany rozmiar panelu
        setBackground(Color.BLACK); // Ustaw tło na czarne
        createBalls(7); // Stwórz 7 piłek
        createCollectibles(4); // Stwórz 4 przedmioty do zebrania
        playerBall = new PlayerBall(this, difficultySize); // Utwórz piłkę gracza
        executorService.submit(playerBall); // Uruchom wątek dla piłki gracza
        setFocusable(true); // Ustaw panel jako fokusowalny
        addKeyListener(this); // Dodaj nasłuchiwacz klawiatury
    }

    // Metoda tworząca piłki
    void createBalls(int number) {
        Random rand = new Random();
        for (int i = 0; i < number; i++) {
            int x = 30 + rand.nextInt(397);
            int y = 30 + rand.nextInt(397);
            int speedX = rand.nextInt(5) + 1;
            int speedY = rand.nextInt(5) + 1;
            Ball ball = new Ball(this, x, y, speedX, speedY);
            balls.add(ball);
            executorService.submit(ball); // Uruchom wątek dla każdej piłki
        }
    }

    // Metoda tworząca przedmioty do zebrania
    void createCollectibles(int number) {
        Random rand = new Random();
        for (int i = 0; i < number; i++) {
            int x, y;
            do {
                x = rand.nextInt(400) + 30;
                y = rand.nextInt(400) + 30;
            } while (y <= 0 || y >= 436);
            collectibles.add(new Collectible(x, y));
        }
    }

    // Metoda ustawiająca poziom trudności
    public void setDifficulty(int size, int speed) {
        difficultySize = size;
        difficultySpeed = speed;
        playerBall.resize(size); // Zmień rozmiar piłki gracza
        for (Ball ball : balls) {
            ball.setSpeed(speed); // Ustaw prędkość dla każdej piłki
        }
        // Ustaw poziom trudności
        if (size == 18) {
            difficultyLevel = "Easy";
        } else if (size == 15) {
            difficultyLevel = "Medium";
        } else if (size == 10) {
            difficultyLevel = "Hard";
        }
    }

    // Metoda rysująca komponenty
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, 20, getHeight()); // Zielona strefa po lewej
        g.setColor(Color.RED);
        g.fillRect(getWidth() - 20, 0, 20, getHeight()); // Czerwona strefa po prawej
        playerBall.draw(g); // Rysuj piłkę gracza
        // Rysuj wszystkie piłki i sprawdzaj kolizje między nimi
        for (int i = 0; i < balls.size(); i++) {
            Ball ball = balls.get(i);
            ball.draw(g);
            for (int j = i + 1; j < balls.size(); j++) {
                Ball other = balls.get(j);
                if (ball.collidesWith(other)) {
                    ball.resolveCollision(other); // Rozwiązanie kolizji
                }
            }
        }
        // Rysuj przedmioty do zebrania
        for (Collectible collectible : collectibles) {
            collectible.draw(g);
        }
        // Jeśli gra nie jest zakończona, sprawdź kolizje
        if (!gameOver) {
            checkCollisions();
        }
        drawLives(g); // Rysuj pozostałe życia
        drawDifficulty(g); // Rysuj aktualny poziom trudności
    }

    // Metoda rysująca aktualny poziom trudności
    private void drawDifficulty(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 12));
        switch (difficultyLevel) {
            case "Easy":
                g.setColor(Color.GREEN);
                break;
            case "Medium":
                g.setColor(Color.YELLOW);
                break;
            case "Hard":
                g.setColor(Color.RED);
                break;
        }
        g.drawString(difficultyLevel, getWidth() - 80, 20);
    }

    // Metoda sprawdzająca kolizje
    void checkCollisions() {
        for (Ball ball : balls) {
            if (playerBall.collidesWith(ball)) {
                lives--;
                playerBall.setVisible(false);
                repaint();
                if (lives <= 0) {
                    gameOver = true;
                    int option = JOptionPane.showOptionDialog(this, "GAME OVER", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Restart", "Quit"}, "Restart");
                    if (option == 0) {
                        restartGame(); // Restart gry
                    } else {
                        System.exit(0); // Zakończ aplikację
                    }
                } else {
                    resetCollectibles(); // Zresetuj przedmioty do zebrania po utracie życia
                    playerBall.resetPosition();
                    playerBall.setVisible(true);
                }
            }
        }

        // Sprawdź kolizje z przedmiotami do zebrania
        for (int i = 0; i < collectibles.size(); i++) {
            if (playerBall.collidesWith(collectibles.get(i))) {
                collectibles.remove(i);
                i--;
            }
        }

        // Sprawdź, czy gracz zebrał wszystkie przedmioty i dotarł do strefy końcowej
        if (collectibles.isEmpty() && playerBall.getX() >= getWidth() - 20 - playerBall.size / 2) {
            gameOver = true;
            playerBall.setVisible(false);
            repaint();
            int option = JOptionPane.showOptionDialog(this, "Congratulations! You reached the goal!", "You Won", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"New Game", "Quit"}, "New Game");
            if (option == 0) {
                restartGame(); // Restart gry
            } else {
                System.exit(0); // Zakończ aplikację
            }
        }
    }

    // Metoda rysująca pozostałe życia
    private void drawLives(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("Lives: " + lives, 10, getHeight() - 10);
    }

    // Metoda restartująca grę
    private void restartGame() {
        lives = 3;
        playerBall.resetPosition();
        playerBall.setVisible(true);
        balls.clear();
        createBalls(7); // Reset piłek przy użyciu aktualnych ustawień trudności
        collectibles.clear();
        createCollectibles(4); // Reset przedmiotów do zebrania
        setDifficulty(difficultySize, difficultySpeed); // Ponowne zastosowanie aktualnej trudności
        gameOver = false;
    }

    // Metoda resetująca przedmioty do zebrania
    private void resetCollectibles() {
        collectibles.clear();
        createCollectibles(4);
    }

    // Metody nasłuchujące klawiaturę
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver) {
            playerBall.setDirection(e.getKeyCode()); // Ustaw kierunek ruchu piłki gracza
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver) {
            playerBall.stopDirection(e.getKeyCode()); // Zatrzymaj ruch piłki gracza
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
