package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Modelo.*;
import Controlador.Controlador;

public class Ventana extends JPanel implements KeyListener {
    public static final int ANCHO = 800, ALTO = 600;
    private Controlador controlador;
    private MundoJuego mundo;
    private Image fondo;

    public Ventana(Controlador controlador, MundoJuego mundo) {
        this.controlador = controlador;
        this.mundo = mundo;

        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        fondo = new ImageIcon(getClass().getResource("/fondo.png")).getImage();

        Timer temporizador = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mundo.actualizar();
                repaint();
                if (!mundo.estaEnEjecucion()) {
                    ((Timer)e.getSource()).stop(); // solo detiene animación
                }
            }
        });
        temporizador.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null) g.drawImage(fondo, 0, 0, ANCHO, ALTO, null);

        for (Comida comida : mundo.getComidas()) comida.dibujar(g);
        for (Pez enemigo : mundo.getEnemigos()) enemigo.dibujar(g);
        mundo.getJugador().dibujar(g);

        g.setColor(Color.BLACK);
        g.drawString("Tamaño: " + mundo.getJugador().getTamano(), 10, 20);
    }

    @Override 
    public void keyPressed(KeyEvent e) { 
        controlador.alPresionarTecla(e); 
    }

    @Override 
    public void keyReleased(KeyEvent e) {} 

    @Override 
    public void keyTyped(KeyEvent e) {} 
}