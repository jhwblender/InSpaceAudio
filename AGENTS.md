# AGENTS.md

## Project Snapshot
- Java 21 + Maven desktop app using Processing (`org.processing:core`) for wave-field visualization.
- Entry point is `src/main/java/com/lucenforge/inspaceaudio/Main.java`; graphics runtime is `src/main/java/com/lucenforge/inspaceaudio/Engine.java`.

## Architecture
- `Engine` extends `PApplet` and drives the Processing loop.
- `Simulation` owns a `Room`, `Speaker`s, `AudioZone`s, `Microphone`s, and global `time`.
- `Room` holds `Speaker` instances and can compute combined amplitude at any point.
- `Speaker` computes time-delayed amplitude based on distance and `SPEED_OF_SOUND`.
- `AudioSource` is an abstract wave generator; `SineWave` and `WavAudioSource` are concrete implementations.
- `AudioZone` couples a 3D position with an `AudioSource`, representing a target "sound pocket."
- `Room` supports two modes:
  - **Direct mode:** each speaker emits its own attached sources (legacy).
  - **Synthesize mode (`synthesizeMode`):** speakers emit a superposition of all zone sources, advanced by propagation delay from speaker to zone, creating constructive interference hotspots.
- `Microphone` can sample the room field at 44.1 kHz and export mono 16-bit PCM WAV files.
- `Vector2` and `Vector3` are lightweight math primitives.

## Controls
- `Space` — toggle overlay (speakers, zones, microphones).
- `R` — record all microphones for 2 seconds and export WAVs to project root.

## Build / Run
- `mvn compile`
- `mvn package`
- In IntelliJ: import as Maven project and run `com.lucenforge.inspaceaudio.Main`.

## Known Bugs Inherited from Original Sketch
- `Vector2(float x, float y, float z)` constructor discards `z` and incorrectly assigns `this.x = y`. Preserve for now or refactor carefully if downstream depends on the current buggy behavior.
