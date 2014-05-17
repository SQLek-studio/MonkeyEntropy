/*
 * The MIT License
 *
 * Copyright 2014 Piotr SQLek Skólski.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.jme3.noise.common;

import com.jme3.noise.Fader;
import com.jme3.noise.Permutator;
import com.jme3.noise.basic.PermutedNoise;

/**
 * Under Construction.
 *
 * @author Piotr SQLek Skólski
 */
public class ImprovedPerlin extends PermutedNoise {

    /**
     * Default fader for ImprovedPerlin is PERLIN.
     *
     */
    public static final Fader DEFAULT_PERLIN_FADER = Fader.PERLIN;

    /**
     * Creates generator with specified permutator and fader.
     *
     * @param permutator Permutation table to be used in generation.
     * @param fader Fader for internal filtering.
     */
    public ImprovedPerlin(Permutator permutator, Fader fader) {
        super(permutator, fader);
    }

    /**
     * Creates generator with specified permutator and fader.
     *
     * @param permutator Permutation table to be used in generation.
     */
    public ImprovedPerlin(Permutator permutator) {
        this(permutator, DEFAULT_PERLIN_FADER);
    }

    /**
     * Creates generator with specified fader and seed.
     *
     * @param seed Seed for creating permutation table.
     * @param fader Fader for internal filtering.
     */
    public ImprovedPerlin(long seed, Fader fader) {
        this(new Permutator(seed), fader);
    }

    /**
     * Creates generator with default fader and permutator for given seed.
     *
     * @param seed Seed for creating permutation table.
     */
    public ImprovedPerlin(long seed) {
        this(new Permutator(seed));
    }

    /**
     * Creates generator with specified fader and random seed.
     *
     * @param fader Fader for internal filtering.
     */
    public ImprovedPerlin(Fader fader) {
        this(new Permutator(), fader);
    }

    @Override
    public float value(float x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float value(float x, float y) {
        int X = (int) Math.floor(x);
        int Y = (int) Math.floor(y);
        x -= X;
        y -= Y;

        //corners int values A means lower value B means +1 value, vec(xyzw)
        int AA = valueInt(X, Y);
        int AB = valueInt(X, Y + 1);
        int BA = valueInt(X + 1, Y);
        int BB = valueInt(X + 1, Y + 1);

        return fader.fade(x,
                fader.fade(y,
                        grad(AA, x, y),
                        grad(AB, x, y - 1)),
                fader.fade(y,
                        grad(BA, x - 1, y),
                        grad(BB, x - 1, y - 1)));
    }

    @Override
    public float value(float x, float y, float z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float value(float x, float y, float z, float w) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float grad(int index, float x, float y) {
        int h = index & 3;
        return ((h & 1) == 0 ? x : -x) + ((h & 2) == 0 ? y : -y);
    }

    public float grad(int index, float x, float y, float z) {
        switch (index & 0x0f) {
            case 0:
                return x + y;
            case 1:
                return -x + y;
            case 2:
                return x + y;
            case 3:
                return -x + y;
            case 4:
                return x + y;
            case 5:
                return -x + y;
            case 6:
                return x + y;
            case 7:
                return -x + y;
            case 8:
                return x + y;
            case 9:
                return x + y;
            case 10:
                return x + y;
            case 11:
                return x + y;
            case 12:
                return x + y;
            case 13:
                return x + y;
            case 14:
                return x + y;
            case 15:
                return x + y;
        }
        return 0;
    }

}
