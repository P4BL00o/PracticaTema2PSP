package Modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class GameWorld {
    private final int width, height;
    private final Player player;
    private final List<Fish> enemies = new ArrayList<>();
    private final List<Food> foods = new ArrayList<>();
    private volatile boolean running = true;
    private boolean victory = false;

    public GameWorld(int width, int height, Player player) {
        this.width = width;
        this.height = height;
        this.player = player;
    }

    public synchronized void initFoods(int n) {
        for (int i = 0; i < n; i++) {
            foods.add(Food.randomWithin(width, height));
        }
    }

    public synchronized void initEnemies(int n) {
    	for (int i = 0; i < n; i++) {
    	    int y = 100 + i * 80;
    	    int dir = (i % 2 == 0) ? +1 : -1;
    	    int x = (dir == +1) ? 0 : width;
    	    int size = 5 + i; // ✅ aquí defines size
    	    double[] velocidades = {2.0, 2.0, 2.0, 2.0, 2.0}; // asegúrate de que tenga al menos n elementos
    	    enemies.add(new Fish(x, y, size, dir, velocidades[i]));
    	}
    }

    public synchronized void updateEnemy(Fish f) {
        f.update(width);
        checkCollisions();
    }

    public synchronized void checkCollisions() {
        Iterator<Food> itFood = foods.iterator();
        while (itFood.hasNext()) {
            Food food = itFood.next();
            if (player.bounds().intersects(food.bounds())) {
                player.grow(Food.VALUE);
                itFood.remove();
            }
        }

        Iterator<Fish> itEnemy = enemies.iterator();
        while (itEnemy.hasNext()) {
            Fish en = itEnemy.next();
            if (player.bounds().intersects(en.bounds())) {
                if (en.getSize() < player.getSize()) {
                    player.grow(en.getSize());
                    itEnemy.remove();
                    if (enemies.isEmpty()) {
                        running = false;
                        victory = true; // 🔹 solo marca estado
                    }
                    break;
                } else {
                    running = false;
                    victory = false; // 🔹 solo marca estado
                    break;
                }
            }
        }
    }

    public synchronized boolean isRunning() { return running; }
    public synchronized boolean isVictory() { return victory; }

    public synchronized Player getPlayer() { return player; }
    public synchronized List<Fish> getEnemies() { return enemies; }
    public synchronized List<Food> getFoods() { return foods; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public synchronized void update() {
        if (!running) return;
        for (Fish en : enemies) en.move();
        player.update();
        checkCollisions();
    }
}