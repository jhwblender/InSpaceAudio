package com.lucenforge.inspaceaudio;

import processing.core.PApplet;
import java.util.ArrayList;

public class Engine extends PApplet {
    private Simulation simulation;
    private boolean showOverlay = true;
    private boolean heatMapMode = false;

    // Time-averaging accumulators for the heat map
    private float[][] heatAccum;
    private long heatFrames = 0;
    private int lastW = -1;
    private int lastH = -1;

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        surface.setResizable(true);
        surface.setLocation(displayWidth / 4, displayHeight / 4);
        simulation = new Simulation();
    }

    @Override
    public void draw() {
        background(0);
        float time = simulation.getTime();

        Room room = simulation.getRoom();
        int w = width;
        int h = height;
        float scaleX = w / room.getSize().x;
        float scaleY = h / room.getSize().y;
        float scale = Math.min(scaleX, scaleY);

        // Reset accumulators if the window size changed
        if (w != lastW || h != lastH) {
            heatAccum = new float[h][w];
            heatFrames = 0;
            lastW = w;
            lastH = h;
        }

        loadPixels();
        for (int py = 0; py < h; py++) {
            for (int px = 0; px < w; px++) {
                Vector3 pointLoc = new Vector3(px / scale, py / scale, simulation.getSliceZ());
                float amp = room.getPointAmplitude(pointLoc, time);

                heatAccum[py][px] += Math.abs(amp);

                if (heatMapMode) {
                    float avg = heatAccum[py][px] / (heatFrames + 1);
                    pixels[py * w + px] = heatMapColor(avg);
                } else {
                    int gray = (int) Math.max(0, Math.min(255, 128 * amp + 128));
                    pixels[py * w + px] = color(gray);
                }
            }
        }
        updatePixels();
        heatFrames++;

        if (showOverlay) {
            drawOverlay(room, scale);
        }

        simulation.update();
    }

    private void drawOverlay(Room room, float scale) {
        strokeWeight(2);
        for (Speaker s : room.getSpeakers()) {
            Vector3 sl = s.getLoc();
            float sx = sl.x * scale;
            float sy = sl.y * scale;
            fill(255, 0, 0);
            stroke(255, 0, 0);
            rect(sx - 5, sy - 5, 10, 10);
        }

        noFill();
        stroke(0, 255, 0);
        for (AudioZone z : room.getZones()) {
            Vector3 zl = z.getPosition();
            ellipse(zl.x * scale, zl.y * scale, 20, 20);
        }

        fill(0, 100, 255);
        stroke(0, 100, 255);
        for (Microphone m : simulation.getMicrophones()) {
            Vector3 ml = m.getLoc();
            triangle(ml.x * scale, ml.y * scale - 6,
                     ml.x * scale - 5, ml.y * scale + 4,
                     ml.x * scale + 5, ml.y * scale + 4);
        }
    }

    @Override
    public void keyPressed() {
        if (key == ' ') {
            showOverlay = !showOverlay;
        }
        if (key == 'h' || key == 'H') {
            heatMapMode = !heatMapMode;
            println("Heat map mode: " + heatMapMode);
        }
        if (key == 'c' || key == 'C') {
            // Clear heat accumulation
            if (heatAccum != null) {
                for (int py = 0; py < heatAccum.length; py++) {
                    java.util.Arrays.fill(heatAccum[py], 0f);
                }
            }
            heatFrames = 0;
            println("Heat accumulation cleared.");
        }
        if (key == 'r' || key == 'R') {
            println("Recording microphones...");
            ArrayList<Microphone> mics = simulation.getMicrophones();
            for (int i = 0; i < mics.size(); i++) {
                Microphone m = mics.get(i);
                m.record(simulation.getRoom(), simulation.getTime(), 2.0f);
                try {
                    m.exportWav("mic_" + i + ".wav");
                    println("Saved mic_" + i + ".wav");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int heatMapColor(float normalizedAmp) {
        float t = Math.max(0f, Math.min(1f, normalizedAmp));
        int r, g, b;
        if (t < 0.5f) {
            float local = t * 2f;
            r = (int) (local * 255);
            g = 255;
            b = 0;
        } else {
            float local = (t - 0.5f) * 2f;
            r = 255;
            g = (int) ((1f - local) * 255);
            b = 0;
        }
        return color(r, g, b);
    }
}
