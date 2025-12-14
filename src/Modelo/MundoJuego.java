package Modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class MundoJuego {
    private final int ancho, alto;
    private final Jugador jugador;
    private final List<Pez> enemigos = new ArrayList<>();
    private final List<Comida> comidas = new ArrayList<>();
    private volatile boolean enEjecucion = true;
    private boolean victoria = false;

    public MundoJuego(int ancho, int alto, Jugador jugador) {
        this.ancho = ancho;
        this.alto = alto;
        this.jugador = jugador;
    }

    public synchronized void inicializarComidas(int n) {
        for (int i = 0; i < n; i++) {
            comidas.add(Comida.aleatoriaDentro(ancho, alto));
        }
    }

    public synchronized void inicializarEnemigos(int n) {
        for (int i = 0; i < n; i++) {
            int y = 100 + i * 80;                  // posición vertical distinta
            int direccion = (i % 2 == 0) ? +1 : -1; // alterna dirección
            int x = (direccion == +1) ? 0 : ancho;  // inicio según dirección
            int tamano = 5 + i;                     // tamaño creciente
            double[] velocidades = {2.0, 2.0, 2.0, 2.0, 2.0};
            enemigos.add(new Pez(x, y, tamano, direccion, velocidades[i]));
        }
    }

    public synchronized void actualizarEnemigo(Pez pez) {
        pez.actualizar(ancho);
        comprobarColisiones();
    }

    public synchronized void comprobarColisiones() {
        Iterator<Comida> itComida = comidas.iterator();
        while (itComida.hasNext()) {
            Comida comida = itComida.next();
            if (jugador.limites().intersects(comida.limites())) {
                jugador.crecer(Comida.VALOR);
                itComida.remove();
            }
        }

        Iterator<Pez> itEnemigo = enemigos.iterator();
        while (itEnemigo.hasNext()) {
            Pez enemigo = itEnemigo.next();
            if (jugador.limites().intersects(enemigo.limites())) {
                if (enemigo.getTamano() < jugador.getTamano()) {
                    jugador.crecer(enemigo.getTamano());
                    itEnemigo.remove();
                    if (enemigos.isEmpty()) {
                        enEjecucion = false;
                        victoria = true; // marca estado de victoria
                    }
                    break;
                } else {
                    enEjecucion = false;
                    victoria = false; // marca estado de derrota
                    break;
                }
            }
        }
    }

    public synchronized boolean estaEnEjecucion() { 
        return enEjecucion; 
    }

    public synchronized boolean hayVictoria() { 
        return victoria; 
    }

    public synchronized Jugador getJugador() { 
        return jugador; 
    }

    public synchronized List<Pez> getEnemigos() { 
        return enemigos; 
    }

    public synchronized List<Comida> getComidas() { 
        return comidas; 
    }

    public int getAncho() { 
        return ancho; 
    }

    public int getAlto() { 
        return alto; 
    }
    

    public synchronized void actualizar() {
        if (!enEjecucion) return;
        for (Pez enemigo : enemigos) enemigo.mover();
        jugador.actualizar();
        comprobarColisiones();
    }
}