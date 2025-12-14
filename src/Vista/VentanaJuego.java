package Vista;

import javax.swing.*;

import Modelo.MundoJuego;
import Modelo.Jugador;
import Controlador.Controlador;

public class VentanaJuego extends JFrame {
    public VentanaJuego() {
        setTitle("A Comer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Crear el modelo (jugador y mundo)
        Jugador jugador = new Jugador(Ventana.ANCHO / 2, Ventana.ALTO - 50, 5);
        MundoJuego mundo = new MundoJuego(Ventana.ANCHO, Ventana.ALTO, jugador);

        // Crear el controlador (se inicializa después de tener el panel)
        Controlador controlador;

        // Crear la vista (panel) con controlador y mundo
        Ventana panel = new Ventana(null, mundo);
        controlador = new Controlador(mundo, panel);

        // Reasignar el controlador al panel
        panel = new Ventana(controlador, mundo);

        // Configurar ventana
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VentanaJuego();
            }
        });
    }
}