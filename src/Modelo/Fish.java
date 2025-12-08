package Modelo;

import java.awt.*; 
import javax.swing.*;

public class Fish {
    private double x, y;       // 🔹 ahora son double
    private int size;
    private int dir;           // +1 derecha, -1 izquierda
    private double speed;
    private Image sprite;

    public Fish(int x, int y, int size, int dir, double speed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.dir = dir;
        this.speed = speed;

        // Cargar imagen del pez enemigo
        sprite = new ImageIcon(getClass().getResource("/pez.png")).getImage();
    }

    public void update(int worldWidth) {
        x += dir * speed;

        if (x < 0) {
            x = 0;
            dir = +1;
        }
        if (x > worldWidth) {
            x = worldWidth;
            dir = -1;
        }
    }

    public int getSize() {
        return size;
    }

    public Rectangle bounds() {
        int r = sizeToPixels();
        return new Rectangle((int)x - r / 2, (int)y - r / 2, r, r); // 🔹 conversión a int
    }

    private int sizeToPixels() {
        return Math.max(14, size * 3);
    }

    public void draw(Graphics g) {
        int r = sizeToPixels();
        g.drawImage(sprite, (int)x - r / 2, (int)y - r / 2, r, r, null); // 🔹 conversión a int
    }

    public void move() {
        update(800);  // 🔹 ancho del mundo, puedes pasarlo dinámico si quieres
    }
}