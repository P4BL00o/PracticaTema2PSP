package Controlador;

import Modelo.*;
import Vista.GamePanel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class GameController {
    private final GameWorld world;
    private final GamePanel panel;
    private final AudioManager audio;
    private final List<EnemyThread> enemyThreads;

    public GameController(GameWorld world, GamePanel panel) {
        this.world = world;
        this.panel = panel;
        this.audio = new AudioManager();
        this.enemyThreads = new java.util.ArrayList<>();

        world.initFoods(5);
        world.initEnemies(5);

        for (Fish f : world.getEnemies()) {
            EnemyThread et = new EnemyThread(f, this);
            enemyThreads.add(et);
            et.start();
        }
    }

    public GameWorld getWorld() { return world; }

    public void onKeyPressed(KeyEvent e) {
        if (!world.isRunning()) return;
        Player p = world.getPlayer();
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP)    p.move(0, -p.getSpeed(), world.getWidth(), world.getHeight());
        if (code == KeyEvent.VK_DOWN)  p.move(0,  p.getSpeed(), world.getWidth(), world.getHeight());
        if (code == KeyEvent.VK_LEFT)  p.move(-p.getSpeed(), 0, world.getWidth(), world.getHeight());
        if (code == KeyEvent.VK_RIGHT) p.move( p.getSpeed(), 0, world.getWidth(), world.getHeight());
        panel.repaint();
    }

    public void onEnemyUpdated(Fish f) {
        world.updateEnemy(f);
        panel.repaint();

        // 🔹 Detectar fin de juego desde el controlador
        if (!world.isRunning()) {
            if (world.isVictory()) {
                endGameVictory();
            } else {
                endGame();
            }
        }
    }

    private void endGame() {
        stopThreads();
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(panel,
                    "Has sido comido. Tamaño final: " + world.getPlayer().getSize());
            System.exit(0);
        });
    }

    private void endGameVictory() {
        stopThreads();
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(panel,
                    "¡Has ganado! Te has comido a todos los peces. Tamaño final: " + world.getPlayer().getSize());
            System.exit(0);
        });
    }

    private void stopThreads() {
        for (EnemyThread et : enemyThreads) et.terminate();
        enemyThreads.clear();
    }
}