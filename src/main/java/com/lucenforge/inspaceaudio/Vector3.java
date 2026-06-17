package com.lucenforge.inspaceaudio;

public class Vector3 {
    public float x, y, z;

    public Vector3(float x, float y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 mult(float value) {
        return new Vector3(x * value, y * value, z * value);
    }

    public Vector3 div(float value) {
        return new Vector3(x / value, y / value, z / value);
    }

    public float dist(Vector3 other) {
        float dx = x - other.x;
        float dy = y - other.y;
        float dz = z - other.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
