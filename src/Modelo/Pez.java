package Modelo;

import java.awt.*; 
import javax.swing.*;

public class Pez {
    private double x, y;       
    private double tamano;
    private int direccion;     
    private double velocidad;
    private Image imagen;

    public Pez(int x, int y, double tamano, int direccion, double velocidad) {
        this.x = x;
        this.y = y;
        this.tamano = tamano;
        this.direccion = direccion;
        this.velocidad = velocidad;

        
        imagen = new ImageIcon(getClass().getResource("/pez.png")).getImage();
    }

    public void actualizar(int anchoMundo) {
        x += direccion * velocidad;

        if (x < 0) {
            x = 0;
            direccion = +1;
        }
        if (x > anchoMundo) {
            x = anchoMundo;
            direccion = -1;
        }
    }

    public double getTamano() {
        return tamano;
    }

    public Rectangle limites() {
        int r = tamanoAPixeles();
        return new Rectangle((int)x - r / 2, (int)y - r / 2, r, r); 
    }

    private int tamanoAPixeles() {
        return (int) Math.max(14.0, tamano * 3);
    }

    public void dibujar(Graphics g) {
        int r = tamanoAPixeles();
        g.drawImage(imagen, (int)x - r / 2, (int)y - r / 2, r, r, null);
    }

    public void mover() {
        actualizar(800);  
    }
}