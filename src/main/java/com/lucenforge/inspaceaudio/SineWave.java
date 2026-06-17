package com.lucenforge.inspaceaudio;

public class SineWave extends AudioSource {
    private float frequency;
    private float amplitude = 1;

    public SineWave(float frequency) {
        this.frequency = frequency;
    }

    public SineWave(float frequency, float amplitude) {
        this.frequency = frequency;
        this.amplitude = amplitude;
    }

    @Override
    public float amplitude(float time) {
        return (time >= 0) ? amplitude * (float) Math.sin(time * frequency * 2 * Math.PI) : 0;
    }
}
