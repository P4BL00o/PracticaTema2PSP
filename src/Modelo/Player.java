package Modelo;

import java.awt.*;
import javax.swing.*;

public class Player {
    private int x, y;
    private int size;
    private int speed = 5;
    private Image sprite;

    public Player(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;

        // Cargar imagen del jugador
        sprite = new ImageIcon(getClass().getResource("/tiburon.png")).getImage();
    }

    public void move(int dx, int dy, int maxWidth, int maxHeight) {
        x += dx;
        y += dy;
        x = Math.max(0, Math.min(x, maxWidth));
        y = Math.max(0, Math.min(y, maxHeight));
    }

    public void grow(int delta) {
        size += delta;
    }

    public int getSize() {
        return size;
    }

    public int getSpeed() {
        return speed;
    }

    public Rectangle bounds() {
        int r = sizeToPixels();
        return new Rectangle(x - r / 2, y - r / 2, r, r);
    }

    private int sizeToPixels() {
        return Math.max(16, size * 4);
    }

    public void draw(Graphics g) {
        int r = sizeToPixels();
        g.drawImage(sprite, x - r / 2, y - r / 2, r, r, null);
    }
    

	public void update() {
		// TODO Auto-generated method stub
		
	}
}