package Modelo;

import java.awt.*;
import javax.swing.*;

public class Jugador {
    private int x, y;
    private int tamano;
    private int velocidad = 5;
    private Image imagen;

    public Jugador(int x, int y, int tamano) {
        this.x = x;
        this.y = y;
        this.tamano = tamano;

        // Cargar imagen del jugador
        imagen = new ImageIcon(getClass().getResource("/tiburon.png")).getImage();
    }

    public void mover(int dx, int dy, int anchoMax, int altoMax) {
        x += dx;
        y += dy;
        x = Math.max(0, Math.min(x, anchoMax));
        y = Math.max(0, Math.min(y, altoMax));
    }

    public void crecer(int incremento) {
        tamano += incremento;
    }

    public int getTamano() {
        return tamano;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public Rectangle limites() {
        int r = tamanoAPixeles();
        return new Rectangle(x - r / 2, y - r / 2, r, r);
    }

    private int tamanoAPixeles() {
        return Math.max(16, tamano * 4);
    }

    public void dibujar(Graphics g) {
        int r = tamanoAPixeles();
        g.drawImage(imagen, x - r / 2, y - r / 2, r, r, null);
    }

    public void actualizar() {
        // TODO: implementar lógica de actualización si es necesario
    }
}