package com.dodgeball;

import java.awt.*;
import java.awt.event.KeyEvent;

// Klasa PlayerBall implementuje Runnable do obsługi ruchu piłki gracza w osobnym wątku
public class PlayerBall implements Runnable {
    int x, y; // Współrzędne piłki gracza
    int size; // Rozmiar piłki
    volatile int dx = 0, dy = 0; // Kierunki ruchu w osi X i Y
    Color color = Color.WHITE; // Kolor piłki
    private final int speed = 5; // Prędkość piłki
    private boolean running = true; // Flaga określająca czy wątek powinien działać
    private boolean visible = true; // Flaga określająca widoczność piłki
    private final Component parent; // Komponent rodzica, do którego należy piłka

    // Konstruktor klasy PlayerBall
    public PlayerBall(Component parent, int size) {
        this.size = size;
        this.parent = parent;
        resetPosition(); // Ustaw początkową pozycję piłki
    }

    // Metoda rysująca piłkę
    public void draw(Graphics g) {
        if (visible) {
            g.setColor(color);
            g.fillOval(x - size / 2, y - size / 2, size, size);
        }
    }

    // Metoda ustawiająca kierunek ruchu na podstawie wciśniętego klawisza
    public void setDirection(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                dy = -speed;
                dx = 0;
                break;
            case KeyEvent.VK_DOWN:
                dy = speed;
                dx = 0;
                break;
            case KeyEvent.VK_LEFT:
                dx = -speed;
                dy = 0;
                break;
            case KeyEvent.VK_RIGHT:
                dx = speed;
                dy = 0;
                break;
        }
    }

    // Metoda zatrzymująca ruch na podstawie zwolnionego klawisza
    public void stopDirection(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                dy = 0;
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                dx = 0;
                break;
        }
    }

    // Metoda zmieniająca rozmiar piłki
    public void resize(int newSize) {
        size = newSize;
    }

    // Metoda resetująca pozycję piłki
    public void resetPosition() {
        x = 1 + size / 2;
        y = 219 - size / 2;
        dx = 0; // Resetowanie poziomego ruchu
        dy = 0; // Resetowanie pionowego ruchu
    }

    // Metoda ustawiająca widoczność piłki
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    // Metoda run dla wątku
    @Override
    public void run() {
        while (running) {
            try {
                if (dx != 0 || dy != 0) {
                    x += dx;
                    y += dy;
                    // Zapewnij, że piłka nie wychodzi poza lewe i prawe granice
                    x = Math.max(x, size / 2);
                    x = Math.min(x, parent.getWidth() - 20);
                    // Zapewnij, że piłka nie wychodzi poza górną granicę
                    y = Math.max(y, size / 2);
                    // Zapewnij, że piłka nie wychodzi poza dolną granicę
                    y = Math.min(y, parent.getHeight() - size / 2);
                    Thread.sleep(10); // Uśpienie wątku na 10 milisekund
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false; // Zatrzymanie pętli
            }
        }
    }

    // Metoda sprawdzająca kolizję z inną piłką
    public boolean collidesWith(Ball ball) {
        int dx = this.x - ball.x;
        int dy = this.y - ball.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.size / 2 + ball.size / 2);
    }

    // Metoda sprawdzająca kolizję z przedmiotem do zebrania
    public boolean collidesWith(Collectible collectible) {
        int dx = this.x - collectible.x;
        int dy = this.y - collectible.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.size / 2 + collectible.size / 2);
    }

    // Metody ruchu piłki w różnych kierunkach
    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    // Gettery i settery dla współrzędnych X i Y
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
