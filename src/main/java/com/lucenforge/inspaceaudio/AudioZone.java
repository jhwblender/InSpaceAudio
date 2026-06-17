package com.lucenforge.inspaceaudio;

public class AudioZone {
    private Vector3 position;
    private AudioSource source;

    public AudioZone(Vector3 position, AudioSource source) {
        this.position = position;
        this.source = source;
    }

    public Vector3 getPosition() {
        return position;
    }

    public AudioSource getSource() {
        return source;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setSource(AudioSource source) {
        this.source = source;
    }
}
