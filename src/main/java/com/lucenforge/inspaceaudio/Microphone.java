package com.lucenforge.inspaceaudio;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class Microphone {
    private Vector3 loc;
    private float[] recordedBuffer;
    private float sampleRate = 44100f;

    public Microphone(Vector3 loc) {
        this.loc = loc;
    }

    public Vector3 getLoc() {
        return loc;
    }

    public void setLoc(Vector3 loc) {
        this.loc = loc;
    }

    public void record(Room room, float startTime, float duration) {
        int numSamples = (int) (duration * sampleRate);
        recordedBuffer = new float[numSamples];
        for (int i = 0; i < numSamples; i++) {
            float t = startTime + i / sampleRate;
            recordedBuffer[i] = room.getPointAmplitude(loc, t);
        }
    }

    public void exportWav(String filePath) throws IOException {
        if (recordedBuffer == null) return;
        int numSamples = recordedBuffer.length;
        byte[] bytes = new byte[numSamples * 2];
        for (int i = 0; i < numSamples; i++) {
            float sample = Math.max(-1f, Math.min(1f, recordedBuffer[i]));
            short s = (short) (sample * 32767);
            bytes[i * 2] = (byte) (s & 0xFF);
            bytes[i * 2 + 1] = (byte) ((s >> 8) & 0xFF);
        }
        AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        AudioInputStream ais = new AudioInputStream(bais, format, numSamples);
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filePath));
        ais.close();
    }

    public float[] getRecordedBuffer() {
        return recordedBuffer;
    }
}
