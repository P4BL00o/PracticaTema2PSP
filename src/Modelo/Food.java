package Modelo;

import java.awt.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Food {
    public static final int VALUE = 1;
    private final int x, y;
    private static final int RADIUS = 6;
    private Image sprite;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
        // Cargar imagen de la comida
        sprite = new ImageIcon(getClass().getResource("/comida.png")).getImage();
    }

    public static Food randomWithin(int width, int height) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int rx = rnd.nextInt(20, width - 20);
        int ry = rnd.nextInt(20, height - 20);
        return new Food(rx, ry);
    }

    public Rectangle bounds() {
        return new Rectangle(x - RADIUS, y - RADIUS, RADIUS * 2, RADIUS * 2);
    }

    public void draw(Graphics g) {
        int r = RADIUS * 2;
        g.drawImage(sprite, x - RADIUS, y - RADIUS, r, r, null);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}