package Controlador;

import javax.sound.sampled.*;
import java.net.URL;

public class AudioManager {
    private Clip eatClip, deathClip;

    public AudioManager() {
        eatClip = loadClip("/sounds/eat.wav");
        deathClip = loadClip("/sounds/death.wav");
    }

    private Clip loadClip(String resourcePath) {
        try {
            URL url = getClass().getResource(resourcePath);
            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            return clip;
        } catch (Exception e) {
            
            return null;
        }
    }

    public void playEat()   { if (eatClip != null)   restart(eatClip); }
    public void playDeath() { if (deathClip != null) restart(deathClip); }

    private void restart(Clip clip) {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}