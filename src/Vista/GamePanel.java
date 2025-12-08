package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Modelo.*;
import Controlador.GameController;

public class GamePanel extends JPanel implements KeyListener {
    public static final int WIDTH = 800, HEIGHT = 600;
    private GameController controlador;
    private GameWorld world;
    private Image background;

    public GamePanel(GameController controlador, GameWorld world) {
        this.controlador = controlador;
        this.world = world;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        background = new ImageIcon(getClass().getResource("/fondo.png")).getImage();

        Timer timer = new Timer(30, e -> {
            world.update();
            repaint();
            if (!world.isRunning()) {
                ((Timer)e.getSource()).stop(); // solo detiene animación
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);

        for (Food f : world.getFoods()) f.draw(g);
        for (Fish en : world.getEnemies()) en.draw(g);
        world.getPlayer().draw(g);

        g.setColor(Color.BLACK);
        g.drawString("Tamaño: " + world.getPlayer().getSize(), 10, 20);
    }

    @Override public void keyPressed(KeyEvent e) { controlador.onKeyPressed(e); }
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}