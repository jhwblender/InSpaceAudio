package com.lucenforge.inspaceaudio;

import java.util.ArrayList;

public class Simulation {
    public static final float SPEED_OF_SOUND = 343.3f; // m/s

    private Room room;
    private ArrayList<Microphone> microphones;
    private float time = 0;
    private float sliceZ = 1.5f;

    public Simulation() {
        room = new Room(new Vector3(6, 6, 3));
        room.addSpeaker(new Vector3(6, 3, 0));
        room.addSpeaker(new Vector3(0, 3, 0));
        room.addSpeaker(new Vector3(0, 0, 0));

        // Default direct-mode sources so the wavefield is visible out of the box
        room.getSpeaker(0).addSource(new SineWave(300));
        room.getSpeaker(1).addSource(new SineWave(600));
        room.getSpeaker(2).addSource(new SineWave(1000));

        microphones = new ArrayList<>();
    }

    public void update() {
        time += 0.0001f;
    }

    public float getTime() {
        return time;
    }

    public Room getRoom() {
        return room;
    }

    public float getSliceZ() {
        return sliceZ;
    }

    public void setSliceZ(float z) {
        this.sliceZ = z;
    }

    public ArrayList<Microphone> getMicrophones() {
        return microphones;
    }

    public void addMicrophone(Vector3 loc) {
        microphones.add(new Microphone(loc));
    }
}
