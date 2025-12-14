package Controlador;

import Modelo.Pez;

public class HiloEnemigo extends Thread {
    private final Pez pez;
    private final Controlador controlador;
    private volatile boolean enEjecucion = true;

    public HiloEnemigo(Pez pez, Controlador controlador) {
        this.pez = pez;
        this.controlador = controlador;
    }

    @Override
    public void run() {
        while (enEjecucion) {
            pez.actualizar(controlador.getMundo().getAncho());
            controlador.ActualizarEnemigo(pez); // ✅ ahora avisa al controlador
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                // Se ignora la excepción para continuar el bucle
            }
        }
    }

    public void terminar() {
        enEjecucion = false;
    }
}