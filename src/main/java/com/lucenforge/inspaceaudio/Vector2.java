package com.lucenforge.inspaceaudio;

public class Vector2 {
    public float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(float x, float y, float z) {
        this.x = x;
        this.y = y;
    }

    public Vector2 mult(float value) {
        return new Vector2(x * value, y * value);
    }

    public Vector2 add(Vector2 value) {
        return new Vector2(x + value.x, y + value.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
