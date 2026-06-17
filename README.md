# InSpaceAudio

A real-time acoustic wave-field simulator inspired by **pCell (Personal Cell)** technology. Instead of fighting wave interference, it exploits it — using distributed speakers to synthesize localized "sound pockets" anywhere in a 3D space.

---

## What Is This?

Imagine placing speakers around a room. Each speaker is too quiet to hear on its own, but by carefully delaying and combining their signals, you can make them constructively interfere at a specific point in space. That point becomes a **private audio bubble** — a localized hotspot where a chosen sound is perfectly audible, while everywhere else the waves cancel or remain faint.

**InSpaceAudio** simulates exactly that. It visualizes the interference field in real-time and lets you place virtual microphones to record what the synthesized sound field actually sounds like at any location.

This is an acoustic analogue to [Artemis Networks' pCell](https://www.artemisnetworks.com/pcell) — instead of cellular radio, we're doing it with sound.

---

## Features

- **Real-time wave visualization** — Watch sound waves propagate, reflect, and interfere.
- **Live wave mode** — See the instantaneous oscillating field (black & white).
- **Time-averaged heat map** — Toggle an accumulating heat map that reveals stable constructive interference hotspots (green → yellow → red).
- **pCell-style synthesis mode** — Define `AudioZone`s (sound pockets). Speakers emit a phase-compensated superposition so all waves converge at the target location.
- **Virtual microphones** — Place microphones anywhere in the room and record/export what they "hear" as 44.1 kHz mono WAV files.
- **WAV file sources** — Load real audio files as zone sources, not just sine waves.
- **Cross-section slicing** — Visualize any Z-height slice through the 3D room.

---

## Architecture

- `Engine` — Processing-based renderer and event loop.
- `Simulation` — Owns the `Room`, speakers, zones, microphones, and global time.
- `Room` — Holds speakers and zones; computes combined amplitude at any point.
- `Speaker` — Computes time-delayed amplitude based on distance and speed of sound.
- `AudioZone` — Couples a 3D position with an `AudioSource`; defines where a sound pocket should exist.
- `AudioSource` / `SineWave` / `WavAudioSource` — Procedural and file-based audio generators.
- `Microphone` — Samples the room field and exports to mono 16-bit PCM WAV.
- `Vector2` / `Vector3` — Lightweight math primitives.

### Two Modes

1. **Direct mode (default)** — Each speaker emits its own attached sources. Classic multi-source wave interference.
2. **Synthesize mode** — Speakers emit a superposition of all `AudioZone` sources, pre-advanced by propagation delay. This creates deliberate constructive interference at each zone position.

---

## Build & Run

Requires **Java 21** and **Maven**.

```bash
mvn compile
mvn package
```

In IntelliJ IDEA: Import as a Maven project and run `com.lucenforge.inspaceaudio.Main`.

---

## Controls

| Key | Action |
|-----|--------|
| `Space` | Toggle overlay (speakers, zones, microphones) |
| `H` | Toggle **time-averaged heat map** vs. live wave view |
| `C` | Clear heat accumulation |
| `R` | Record all virtual microphones for 2 seconds and export WAVs to project root |

---

## Screenshots

*(Placeholder — add screenshots of live wave mode and heat map mode here)*

Live wave mode shows beautiful traveling ripple interference patterns. Heat map mode, after accumulating for a few seconds, reveals stable bright red hotspots exactly at the `AudioZone` positions.

---

## Prior Art

This project draws from several well-known domains:

- **Wave Field Synthesis (WFS)** — Spatial audio reconstruction via speaker arrays.
- **Sound Zone / Personal Audio research** — Using arrays to create isolated listening regions.
- **Acoustic Beamforming** — Phased arrays for directional sound.

The novelty here is framing it through the lens of **pCell interference synthesis** — turning what is normally a wireless networking concept into an acoustic simulation testbed.

---

## License

*(Add your license here)*

---

Built with ☕ and 🌊 by [jhwblender](https://github.com/jhwblender).
