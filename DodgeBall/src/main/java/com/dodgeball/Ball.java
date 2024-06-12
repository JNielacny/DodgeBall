package com.dodgeball;

import java.awt.*;
import java.util.Random;

// Klasa Ball implementująca interfejs Runnable
public class Ball implements Runnable {
    int x, y; // Współrzędne piłki
    int speedX, speedY; // Prędkość w osi X i Y
    int size = 20; // Rozmiar piłki
    final Color color; // Kolor piłki
    private boolean running = true; // Flaga określająca czy piłka powinna się poruszać
    private final Component parent; // Komponent rodzica, do którego należy piłka

    // Konstruktor klasy Ball
    public Ball(Component parent, int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.speedX = dx;
        this.speedY = dy;
        this.parent = parent;
        this.color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random()); // Losowy kolor piłki
    }

    // Metoda zwracająca współrzędną X piłki
    public int getX() {
        return x;
    }

    // Metoda zwracająca współrzędną Y piłki
    public int getY() {
        return y;
    }

    // Metoda rysująca piłkę
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x - size / 2, y - size / 2, size, size);
    }

    // Metoda poruszająca piłkę
    public void move() {
        x += speedX;
        y += speedY;
        if (x <= size / 2 + 20 || x >= parent.getWidth() - size / 2 - 20) speedX = -speedX; // Odbicie od ściany w osi X
        if (y <= size / 2 || y >= parent.getHeight() - size / 2) speedY = -speedY; // Odbicie od ściany w osi Y
    }

    // Metoda ustawiająca nową prędkość piłki
    public void setSpeed(int newSpeed) {
        Random rand = new Random();
        speedX = (rand.nextBoolean() ? 1 : -1) * newSpeed; // Losowy kierunek w osi X
        speedY = (rand.nextBoolean() ? 1 : -1) * newSpeed; // Losowy kierunek w osi Y
    }

    // Metoda sprawdzająca kolizję z inną piłką
    public boolean collidesWith(Ball other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        double distance = Math.sqrt(dx * dx + dy * dy); // Odległość między piłkami
        return distance < (this.size / 2 + other.size / 2); // Sprawdzenie czy odległość jest mniejsza niż suma promieni piłek
    }

    // Metoda rozwiązująca kolizję z inną piłką
    public void resolveCollision(Ball other) {
        int dx = other.x - this.x;
        int dy = other.y - this.y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double overlap = 0.5 * (distance - this.size / 2 - other.size / 2);

        // Przesunięcie obecnej piłki
        this.x -= overlap * (this.x - other.x) / distance;
        this.y -= overlap * (this.y - other.y) / distance;

        // Przesunięcie innej piłki
        other.x += overlap * (this.x - other.x) / distance;
        other.y += overlap * (this.y - other.y) / distance;

        // Obliczenie nowych prędkości
        int newSpeedX1 = (this.speedX * (this.size - other.size) + (2 * other.size * other.speedX)) / (this.size + other.size);
        int newSpeedY1 = (this.speedY * (this.size - other.size) + (2 * other.size * other.speedY)) / (this.size + other.size);
        int newSpeedX2 = (other.speedX * (other.size - this.size) + (2 * this.size * this.speedX)) / (this.size + other.size);
        int newSpeedY2 = (other.speedY * (other.size - this.size) + (2 * this.size * this.speedY)) / (this.size + other.size);

        this.speedX = newSpeedX1;
        this.speedY = newSpeedY1;
        other.speedX = newSpeedX2;
        other.speedY = newSpeedY2;
    }

    // Metoda run dla wątku
    @Override
    public void run() {
        while (running) {
            try {
                move(); // Porusz piłkę
                Thread.sleep(10); // Uśpienie wątku na 10 milisekund
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Ustawienie flagi przerwania wątku
                running = false; // Zatrzymanie pętli
            }
        }
    }
}
