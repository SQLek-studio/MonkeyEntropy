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
 * Improved perlin noise generator.
 * 
 * Third dimension version is very similar to reference implementation from
 * Ken Perlin.
 * 
 * In addition it support tiling, one and two dimensions as well.
 * Support for four dimension is planed but not done.
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
        int X = (int) Math.floor(x);
        x -= X;

        //corners int values A means lower value B means +1 value, vec(xyzw)
        int A = valueInt(X);
        int B = valueInt(X + 1);

        return fader.fade(x,
                grad(A, x),
                grad(B, x - 1));
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
        int X = (int) Math.floor(x);
        int Y = (int) Math.floor(y);
        int Z = (int) Math.floor(z);
        x -= X;
        y -= Y;
        z -= Z;

        //corners int values A means lower value B means +1 value, vec(xyzw)
        int AAA = valueInt(X, Y, Z);
        int AAB = valueInt(X, Y, Z + 1);
        int ABA = valueInt(X, Y + 1, Z);
        int ABB = valueInt(X, Y + 1, Z + 1);
        int BAA = valueInt(X + 1, Y, Z);
        int BAB = valueInt(X + 1, Y, Z + 1);
        int BBA = valueInt(X + 1, Y + 1, Z);
        int BBB = valueInt(X + 1, Y + 1, Z + 1);

        return fader.fade(x,
                fader.fade(y,
                        fader.fade(z,
                                grad(AAA, x, y, z),
                                grad(AAB, x, y, z - 1)),
                        fader.fade(z,
                                grad(ABA, x, y - 1, z),
                                grad(ABB, x, y - 1, z - 1))),
                fader.fade(y,
                        fader.fade(z,
                                grad(BAA, x - 1, y, z),
                                grad(BAB, x - 1, y, z - 1)),
                        fader.fade(z,
                                grad(BBA, x - 1, y - 1, z),
                                grad(BBB, x - 1, y - 1, z - 1))));
    }

    /** 4d version not ready.
     * 
     *  This method will throw {@link UnsupportedOperationException}.
     * 
     * @param x Input coordinate.
     * @param y Input coordinate.
     * @param z Input coordinate.
     * @param w Input coordinate.
     * @return Computed noise.
     */
    @Override
    public float value(float x, float y, float z, float w) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** Hash a gradiend.
     *
     * @param index Index of gradiend.
     * @param x Fract of x coord.
     * @return Computed gradiend.
     */
    public static float grad(int index, float x) {
        return ((index & 1) == 0 ? x : -x);
    }

    /** Hash a gradiend.
     *
     * @param index Index of gradiend.
     * @param x Fract of x coord.
     * @param y Fract of y coord.
     * @return Computed gradiend.
     */
    public static float grad(int index, float x, float y) {
        int h = index & 3;
        return ((h & 1) == 0 ? x : -x) + ((h & 2) == 0 ? y : -y);
    }

    /** Hash a gradiend.
     *
     * @param index Index of gradiend.
     * @param x Fract of x coord.
     * @param y Fract of y coord.
     * @param z Fract of z coord.
     * @return Computed gradiend.
     */
    public static float grad(int index, float x, float y, float z) {
        int h = index & 15;
        float u = h < 8 ? x : y,
                v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }
    
    /** Hash a gradiend. ToDO.
     *
     * This method will throw {@link UnsupportedOperationException}.
     * 
     * @param index Index of gradiend.
     * @param x Fract of x coord.
     * @param y Fract of y coord.
     * @param z Fract of z coord.
     * @param w Fract of w coord.
     * @return Computed gradiend.
     */
    public static float grad(int index, float x, float y, float z, float w) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
