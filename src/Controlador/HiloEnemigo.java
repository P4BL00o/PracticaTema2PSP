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
            controlador.ActualizarEnemigo(pez); 
            try {
                Thread.sleep(16); 
            } catch (InterruptedException e) {
                
            }
        }
    }

    public void terminar() {
        enEjecucion = false;
    }
}