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
package com.jme3.noise.basic;

import com.jme3.noise.*;

/**
 * Very basic and fast noise source.
 *
 * Internaly computes permutation in form P[x+P[y+P[z+P[w]]]], where P[] is a
 * {@link Permutator#permute(int)}.
 *
 * @author Piotr SQLek Skólski
 */
public class PermutedNoise implements Noise1d, Noise2d, Noise3d, Noise4d, TiledNoise {

    /**
     * Default fader is a linear one.
     *
     */
    public static final Fader DEFAULT_FADER = Fader.LINEAR;

    protected final Permutator permutator;
    protected final Fader fader;

    private int tx = 0, ty = 0, tz = 0, tw = 0;

    /**
     * Creates generator with specified permutator and fader.
     *
     * @param permutator Permutation table to be used in generation.
     * @param fader Fader for internal filtering.
     */
    public PermutedNoise(Permutator permutator, Fader fader) {
        this.permutator = permutator;
        this.fader = fader;
    }

    /**
     * Creates generator with specified permutator and fader.
     *
     * @param permutator Permutation table to be used in generation.
     */
    public PermutedNoise(Permutator permutator) {
        this(permutator, DEFAULT_FADER);
    }

    /**
     * Creates generator with specified fader and seed.
     *
     * @param seed Seed for creating permutation table.
     * @param fader Fader for internal filtering.
     */
    public PermutedNoise(long seed, Fader fader) {
        this(new Permutator(seed), fader);
    }

    /**
     * Creates generator with default fader and permutator for given seed.
     *
     * @param seed Seed for creating permutation table.
     */
    public PermutedNoise(long seed) {
        this(new Permutator(seed));
    }

    /**
     * Creates generator with specified fader and random seed.
     *
     * @param fader Fader for internal filtering.
     */
    public PermutedNoise(Fader fader) {
        this(new Permutator(), fader);
    }

    /**
     * Creates generator with default fader and random seed.
     *
     */
    public PermutedNoise() {
        this(new Permutator());
    }

    public float value(float x) {
        int X = (int) Math.floor(x);
        x -= X;

        //corners int values A means lower value B means +1 value, vec(xyzw)
        int A = valueInt(X);
        int B = valueInt(X + 1);

        float val = fader.fade(x, A, B);
        return val * 2 / permutator.size() - 1;
    }

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

        float val = fader.fade(x,
                fader.fade(y, AA, AB),
                fader.fade(y, BA, BB));
        return val * 2 / permutator.size() - 1;
    }

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

        float val = fader.fade(x,
                fader.fade(y,
                        fader.fade(z, AAA, AAB),
                        fader.fade(z, ABA, ABB)),
                fader.fade(y,
                        fader.fade(z, BAA, BAB),
                        fader.fade(z, BBA, BBB)));
        return val * 2 / permutator.size() - 1;
    }

    public float value(float x, float y, float z, float w) {
        int X = (int) Math.floor(x);
        int Y = (int) Math.floor(y);
        int Z = (int) Math.floor(z);
        int W = (int) Math.floor(w);
        x -= X;
        y -= Y;
        z -= Z;
        w -= W;
        
        //corners int values A means lower value B means +1 value, vec(xyzw)
        int AAAA = valueInt(X, Y, Z, W);
        int AAAB = valueInt(X, Y, Z, W + 1);
        int AABA = valueInt(X, Y, Z + 1, W);
        int AABB = valueInt(X, Y, Z + 1, W + 1);
        int ABAA = valueInt(X, Y + 1, Z, W);
        int ABAB = valueInt(X, Y + 1, Z, W + 1);
        int ABBA = valueInt(X, Y + 1, Z + 1, W);
        int ABBB = valueInt(X, Y + 1, Z + 1, W + 1);
        int BAAA = valueInt(X + 1, Y, Z, W);
        int BAAB = valueInt(X + 1, Y, Z, W + 1);
        int BABA = valueInt(X + 1, Y, Z + 1, W);
        int BABB = valueInt(X + 1, Y, Z + 1, W + 1);
        int BBAA = valueInt(X + 1, Y + 1, Z, W);
        int BBAB = valueInt(X + 1, Y + 1, Z, W + 1);
        int BBBA = valueInt(X + 1, Y + 1, Z + 1, W);
        int BBBB = valueInt(X + 1, Y + 1, Z + 1, W + 1);

        float val = fader.fade(x,
                fader.fade(y,
                        fader.fade(z,
                                fader.fade(w,AAAA,AAAB),
                                fader.fade(w,AABA,AABB)),
                        fader.fade(z,
                                fader.fade(w,ABAA,ABAB),
                                fader.fade(w,ABBA,ABBB))),
                fader.fade(y,
                        fader.fade(z,
                                fader.fade(w,BAAA,BAAB),
                                fader.fade(w,BABA,BABB)),
                        fader.fade(z,
                                fader.fade(w,BBAA,BBAB),
                                fader.fade(w,BBBA,BBBB))));
        return val * 2 / permutator.size() - 1;
    }

    /**
     * Computes one dimension noise for integer cordinates.
     *
     * @param x Input cordinate. NaN and INF prohibited.
     * @return Noise sample in range zero inclusive, to permuatator size
     * exclusive.
     */
    public int valueInt(int x) {
        return permutator.permute(Utils.floorModExt(x, tx));
    }

    /**
     * Computes two dimension noise for integer cordinates.
     *
     * @param x Input cordinate. NaN and INF prohibited.
     * @param y Input cordinate. NaN and INF prohibited.
     * @return Noise sample in range zero inclusive, to permuatator size
     * exclusive.
     */
    public int valueInt(int x, int y) {
        return permutator.permute(Utils.floorModExt(x, tx)
                + permutator.permute(Utils.floorModExt(y, ty)));
    }

    /**
     * Computes tree dimension noise for integer cordinates.
     *
     * @param x Input cordinate. NaN and INF prohibited.
     * @param y Input cordinate. NaN and INF prohibited.
     * @param z Input cordinate. NaN and INF prohibited.
     * @return Noise sample in range zero inclusive, to permuatator size
     * exclusive.
     */
    public int valueInt(int x, int y, int z) {
        return permutator.permute(Utils.floorModExt(x, tx)
                + permutator.permute(Utils.floorModExt(y, ty)
                        + permutator.permute(Utils.floorModExt(z, tz))));
    }

    /**
     * Computes four dimension noise for integer cordinates.
     *
     * @param x Input cordinate. NaN and INF prohibited.
     * @param y Input cordinate. NaN and INF prohibited.
     * @param z Input cordinate. NaN and INF prohibited.
     * @param w Input cordinate. NaN and INF prohibited.
     * @return Noise sample in range zero inclusive, to permuatator size
     * exclusive.
     */
    public int valueInt(int x, int y, int z, int w) {
        return permutator.permute(Utils.floorModExt(x, tx)
                + permutator.permute(Utils.floorModExt(y, ty)
                        + permutator.permute(Utils.floorModExt(z, tz)
                                + permutator.permute(Utils.floorModExt(w, tw)))));
    }

    public void setTiling(int tx, int ty, int tz, int tw) {
        this.tx = tx;
        this.ty = ty;
        this.tz = tz;
        this.tw = tw;
    }

}
