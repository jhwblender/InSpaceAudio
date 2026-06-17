package com.lucenforge.inspaceaudio;

import java.util.ArrayList;

public class Room {
    private Vector3 roomSize;
    private ArrayList<Speaker> speakers;
    private ArrayList<AudioZone> zones;
    private boolean synthesizeMode = false;

    public Room(Vector3 roomSize) {
        this.roomSize = roomSize;
        this.speakers = new ArrayList<>();
        this.zones = new ArrayList<>();
    }

    public int addSpeaker(Vector3 loc) {
        speakers.add(new Speaker(loc));
        return speakers.size() - 1;
    }

    public Speaker getSpeaker(int speakerNum) {
        return speakers.get(speakerNum);
    }

    public void addRandomSpeakers(int count) {
        for (int i = 0; i < count; i++) {
            float x = (float) (Math.random() * roomSize.x);
            float y = (float) (Math.random() * roomSize.y);
            float z = (float) (Math.random() * roomSize.z);
            addSpeaker(new Vector3(x, y, z));
        }
    }

    public Vector3 getSize() {
        return roomSize;
    }

    public void addZone(AudioZone zone) {
        zones.add(zone);
    }

    public ArrayList<AudioZone> getZones() {
        return zones;
    }

    public void setSynthesizeMode(boolean on) {
        synthesizeMode = on;
    }

    public boolean isSynthesizeMode() {
        return synthesizeMode;
    }

    public float getPointAmplitude(Vector3 loc, float time) {
        if (synthesizeMode && !zones.isEmpty()) {
            if (speakers.isEmpty()) return 0;
            float total = 0;
            for (Speaker s : speakers) {
                float distToPoint = s.getLoc().dist(loc);
                float pointDelay = distToPoint / Simulation.SPEED_OF_SOUND;
                float emitTime = time - pointDelay;
                float emission = 0;
                for (AudioZone zone : zones) {
                    float distToZone = s.getLoc().dist(zone.getPosition());
                    float zoneDelay = distToZone / Simulation.SPEED_OF_SOUND;
                    emission += zone.getSource().amplitude(emitTime + zoneDelay);
                }
                total += emission;
            }
            return total / speakers.size();
        } else {
            if (speakers.isEmpty()) return 0;
            float sum = 0;
            for (Speaker s : speakers) {
                sum += s.getAmplitude(loc, time);
            }
            return sum / speakers.size();
        }
    }
}
