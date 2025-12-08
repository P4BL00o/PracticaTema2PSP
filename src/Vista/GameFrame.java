package Vista;

import javax.swing.*;

import Modelo.GameWorld;
import Modelo.Player;
import Controlador.GameController;

public class GameFrame extends JFrame {
    public GameFrame() {
        setTitle("A Comer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Crear el modelo (jugador y mundo)
        Player player = new Player(GamePanel.WIDTH / 2, GamePanel.HEIGHT - 50, 5);
        GameWorld world = new GameWorld(GamePanel.WIDTH, GamePanel.HEIGHT, player);

        // Crear el controlador (se inicializa después de tener el panel)
        GameController controlador;

        // Crear la vista (panel) con controlador y mundo
        GamePanel panel = new GamePanel(null, world);
        controlador = new GameController(world, panel);

        // Reasignar el controlador al panel
        panel = new GamePanel(controlador, world);

        // Configurar ventana
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameFrame::new);
    }
}