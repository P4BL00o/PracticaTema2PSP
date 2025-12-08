package Controlador;

import Modelo.Fish;

public class EnemyThread extends Thread {
    private final Fish pez;
    private final GameController controlador;
    private volatile boolean ejecutando = true;

    public EnemyThread(Fish pez, GameController controlador) {
        this.pez = pez;
        this.controlador = controlador;
    }

    @Override
    public void run() {
        while (ejecutando) {
            pez.update(controlador.getWorld().getWidth());
            controlador.onEnemyUpdated(pez); // ✅ ahora avisa al controlador
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {}
        }
    }

    public void terminate() {
        ejecutando = false;
    }
}