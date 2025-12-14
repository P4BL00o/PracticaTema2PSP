package Modelo;

import java.awt.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Comida {
    public static final int VALOR = 1;
    private final int x, y;
    private static final int RADIO = 6;
    private Image imagen;

    public Comida(int x, int y) {
        this.x = x;
        this.y = y;
        // Cargar imagen de la comida
        imagen = new ImageIcon(getClass().getResource("/comida.png")).getImage();
    }

    public static Comida aleatoriaDentro(int ancho, int alto) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int rx = rnd.nextInt(20, ancho - 20);
        int ry = rnd.nextInt(20, alto - 20);
        return new Comida(rx, ry);
    }

    public Rectangle limites() {
        return new Rectangle(x - RADIO, y - RADIO, RADIO * 2, RADIO * 2);
    }

    public void dibujar(Graphics g) {
        int r = RADIO * 2;
        g.drawImage(imagen, x - RADIO, y - RADIO, r, r, null);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}