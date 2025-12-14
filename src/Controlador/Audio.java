package Controlador;

import javax.sound.sampled.*;
import java.net.URL;

public class Audio {
    private Clip clipComer, clipMorir;

    public Audio() {
        clipComer = cargarClip("/sounds/eat.wav");
        clipMorir = cargarClip("/sounds/death.wav");
    }

    private Clip cargarClip(String rutaRecurso) {
        try {
            URL url = getClass().getResource(rutaRecurso);
            AudioInputStream flujoAudio = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(flujoAudio);
            return clip;
        } catch (Exception e) {
            return null;
        }
    }

    public void reproducirComer() {
        if (clipComer != null) reiniciar(clipComer);
    }

    public void reproducirMorir() {
        if (clipMorir != null) reiniciar(clipMorir);
    }

    private void reiniciar(Clip clip) {
        clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}