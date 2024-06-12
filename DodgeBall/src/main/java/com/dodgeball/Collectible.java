package com.dodgeball;

import java.awt.*;

// Klasa Collectible reprezentuje przedmiot do zebrania w grze
public class Collectible {
    int x, y; // Współrzędne przedmiotu
    int size = 10; // Rozmiar przedmiotu
    Color color = Color.YELLOW; // Kolor przedmiotu

    // Konstruktor klasy Collectible
    public Collectible(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Metoda zwracająca współrzędną X przedmiotu
    public int getX() {
        return x;
    }

    // Metoda zwracająca współrzędną Y przedmiotu
    public int getY() {
        return y;
    }

    // Metoda zwracająca rozmiar przedmiotu
    public int getSize() {
        return size;
    }

    // Metoda zwracająca kolor przedmiotu
    public Color getColor() {
        return color;
    }

    // Metoda rysująca przedmiot na ekranie
    public void draw(Graphics g) {
        g.setColor(color); // Ustaw kolor rysowania
        g.fillOval(x - size / 2, y - size / 2, size, size); // Rysuj owal (okrąg) reprezentujący przedmiot
    }
}
