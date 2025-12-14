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
       
        double[] tamanos = {4.5, 4.5, 5.5, 6.5, 9.5};
        
        double[] velocidades = {4.0, 3.5, 3.0, 2.5, 2.0};

        for (int i = 0; i < n; i++) {
            int y = (int)(Math.random() * alto);   

            int direccion;
            if (i % 2 == 0) {
                direccion = +1; 
            } else {
                direccion = -1; 
            }

            int x;
            if (direccion == +1) {
                x = 0;
            } else {
                x = ancho;
            }

  
            enemigos.add(new Pez(x, y, tamanos[i], direccion, velocidades[i]) );
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
                        victoria = true;
                    }
                    break;
                } else {
                    enEjecucion = false;
                    victoria = false; 
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