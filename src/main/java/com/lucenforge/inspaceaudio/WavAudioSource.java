package com.lucenforge.inspaceaudio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class WavAudioSource extends AudioSource {
    private final float[] samples;
    private final float sampleRate;

    public WavAudioSource(String filePath) throws IOException, UnsupportedAudioFileException {
        File file = new File(filePath);
        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        AudioFormat fmt = ais.getFormat();

        if (fmt.getEncoding() != AudioFormat.Encoding.PCM_SIGNED &&
            fmt.getEncoding() != AudioFormat.Encoding.PCM_UNSIGNED) {
            ais.close();
            throw new UnsupportedAudioFileException("Only PCM encoding supported.");
        }

        sampleRate = fmt.getSampleRate();
        int channels = fmt.getChannels();
        int bits = fmt.getSampleSizeInBits();
        boolean bigEndian = fmt.isBigEndian();

        byte[] raw = ais.readAllBytes();
        ais.close();

        int frameSize = fmt.getFrameSize();
        int numFrames = raw.length / frameSize;
        samples = new float[numFrames];

        for (int i = 0; i < numFrames; i++) {
            int frameOffset = i * frameSize;
            float frameSum = 0f;
            for (int ch = 0; ch < channels; ch++) {
                int off = frameOffset + ch * (bits / 8);
                int val = 0;
                if (bits == 8) {
                    val = ((raw[off] & 0xFF) - 128) << 8;
                } else if (bits == 16) {
                    int b1 = raw[off] & 0xFF;
                    int b2 = raw[off + 1] & 0xFF;
                    val = bigEndian ? ((b1 << 8) | b2) : ((b2 << 8) | b1);
                } else if (bits == 24) {
                    int b1 = raw[off] & 0xFF;
                    int b2 = raw[off + 1] & 0xFF;
                    int b3 = raw[off + 2] & 0xFF;
                    val = bigEndian
                        ? (b1 << 16) | (b2 << 8) | b3
                        : (b3 << 16) | (b2 << 8) | b1;
                    if ((val & 0x800000) != 0) val |= 0xFF000000;
                } else if (bits == 32) {
                    int b1 = raw[off] & 0xFF;
                    int b2 = raw[off + 1] & 0xFF;
                    int b3 = raw[off + 2] & 0xFF;
                    int b4 = raw[off + 3] & 0xFF;
                    val = bigEndian
                        ? (b1 << 24) | (b2 << 16) | (b3 << 8) | b4
                        : (b4 << 24) | (b3 << 16) | (b2 << 8) | b1;
                }
                frameSum += val;
            }
            float avg = frameSum / channels;
            float norm;
            switch (bits) {
                case 8: norm = 32768f; break;
                case 16: norm = 32768f; break;
                case 24: norm = 8388608f; break;
                case 32: norm = 2147483648f; break;
                default: norm = 32768f; break;
            }
            samples[i] = avg / norm;
        }
    }

    @Override
    public float amplitude(float time) {
        if (time < 0 || samples.length == 0) return 0;
        if (time * sampleRate >= samples.length) return 0;
        float idxF = time * sampleRate;
        int idx = (int) idxF;
        float frac = idxF - idx;
        if (idx >= samples.length - 1) return samples[samples.length - 1];
        return samples[idx] * (1 - frac) + samples[idx + 1] * frac;
    }

    public float getDuration() {
        return samples.length / sampleRate;
    }
}
