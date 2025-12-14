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

        
        Jugador jugador = new Jugador(Ventana.ANCHO / 2, Ventana.ALTO - 50, 5);
        MundoJuego mundo = new MundoJuego(Ventana.ANCHO, Ventana.ALTO, jugador);

       
        Controlador controlador;

        
        Ventana panel = new Ventana(null, mundo);
        controlador = new Controlador(mundo, panel);

        
        panel = new Ventana(controlador, mundo);

        
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