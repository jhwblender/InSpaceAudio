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
        room.addRandomSpeakers(20);

        // pCell-style sound pockets — two zones with different tones
        room.addZone(new AudioZone(new Vector3(3, 3, 1.5f), new SineWave(440)));
        room.addZone(new AudioZone(new Vector3(5, 2, 1.5f), new SineWave(660)));
        room.setSynthesizeMode(true);

        microphones = new ArrayList<>();

        // Virtual mic at first zone to sample the hotspot
        addMicrophone(new Vector3(3, 3, 1.5f));
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
