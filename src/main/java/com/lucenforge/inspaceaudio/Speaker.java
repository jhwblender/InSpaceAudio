package com.lucenforge.inspaceaudio;

import java.util.ArrayList;

public class Speaker {
    private ArrayList<AudioSource> sources;
    private Vector3 loc;
    private float volume = 1;

    public Speaker(Vector3 loc) {
        this.loc = loc;
        sources = new ArrayList<>();
    }

    public int addSource(AudioSource source) {
        sources.add(source);
        return sources.size() - 1;
    }

    public float getAmplitude(Vector3 point, float time) {
        if (sources.isEmpty()) return 0;
        float sum = 0;
        float distDelay = loc.dist(point) / Simulation.SPEED_OF_SOUND;
        for (int i = 0; i < sources.size(); i++) {
            sum += sources.get(i).amplitude(time - distDelay);
        }
        return sum / sources.size();
    }

    public Vector3 getLoc() {
        return loc;
    }

    public ArrayList<AudioSource> getSources() {
        return sources;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }
}
