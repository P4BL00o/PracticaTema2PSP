package Controlador;

import Modelo.*;
import Vista.Ventana;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class Controlador {
    private final MundoJuego mundo;
    private final Ventana ventana;
    private final Audio audio;
    private final List<HiloEnemigo> hilosEnemigos;

    public Controlador(MundoJuego mundo, Ventana ventana) {
        this.mundo = mundo;
        this.ventana = ventana;
        this.audio = new Audio();
        this.hilosEnemigos = new java.util.ArrayList<>();

        mundo.inicializarComidas(5);
        mundo.inicializarEnemigos(5);

        for (Pez enemigo : mundo.getEnemigos()) {
            HiloEnemigo hilo = new HiloEnemigo(enemigo, this);
            hilosEnemigos.add(hilo);
            hilo.start();
        }
    }

    public MundoJuego getMundo() { 
        return mundo; 
    }

    public void alPresionarTecla(KeyEvent e) {
        if (!mundo.estaEnEjecucion()) return;
        Jugador jugador = mundo.getJugador();
        int codigo = e.getKeyCode();

        if (codigo == KeyEvent.VK_UP)    
            jugador.mover(0, -jugador.getVelocidad(), mundo.getAncho(), mundo.getAlto());
        if (codigo == KeyEvent.VK_DOWN)  
            jugador.mover(0,  jugador.getVelocidad(), mundo.getAncho(), mundo.getAlto());
        if (codigo == KeyEvent.VK_LEFT)  
            jugador.mover(-jugador.getVelocidad(), 0, mundo.getAncho(), mundo.getAlto());
        if (codigo == KeyEvent.VK_RIGHT) 
            jugador.mover( jugador.getVelocidad(), 0, mundo.getAncho(), mundo.getAlto());

        ventana.repaint();
    }

    public void ActualizarEnemigo(Pez enemigo) {
        int tamanoAntes = mundo.getJugador().getTamano();

        mundo.actualizarEnemigo(enemigo);
        ventana.repaint();

        // 🔊 Si el jugador ha crecido, significa que comió algo
        if (mundo.getJugador().getTamano() > tamanoAntes) {
            audio.reproducirComer();
        }

        // 🔹 Detectar fin de juego desde el controlador
        if (!mundo.estaEnEjecucion()) {
            if (mundo.hayVictoria()) {
                finalizarJuegoVictoria();
            } else {
                finalizarJuego();
            }
        }
    }

    private void finalizarJuego() {
        detenerHilos();
        audio.reproducirMorir(); 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(ventana,
                        "Has sido comido. Tamaño final: " + mundo.getJugador().getTamano());
                System.exit(0);
            }
        });
    }

    private void finalizarJuegoVictoria() {
        detenerHilos();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(ventana,
                        "¡Has ganado! Te has comido a todos los peces. Tamaño final: " + mundo.getJugador().getTamano());
                System.exit(0);
            }
        });
    }

    private void detenerHilos() {
        for (HiloEnemigo hilo : hilosEnemigos) hilo.terminar();
        hilosEnemigos.clear();
    }
}