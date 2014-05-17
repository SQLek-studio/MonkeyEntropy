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

package com.jme3.noise;

/**
 * Interface for fade method.
 *
 * If You lookinf for some kind of mix or larp, You looking in correct place.
 *
 * @author Piotr SQLek Skólski
 * @see #NEAREST
 * @see #LINEAR
 * @see #PERLIN
 */
public interface Fader {

    /**
     * Behaves like GL_LINEAR.
     *
     */
    public static Fader LINEAR = new Fader() {
        public float fade(float factor, float valA, float valB) {
            return Utils.mix(factor, valA, valB);
        }
    };

    /**
     * Behaves like GL_NEAREST.
     *
     */
    public static Fader NEAREST = new Fader() {
        public float fade(float factor, float valA, float valB) {
            return factor < 0.5f ? valA : valB;
        }
    };

    /**
     * Fade function used by Perlin in his improved noise. This Fader computes
     * 6x^5 - 15x^4 + 10x^3 as a factor to mix.
     */
    public static Fader PERLIN = new Fader() {
        public float fade(float f, float a, float b) {
            float m = f * f * f * (f * (f * 6 - 15) + 10);
            return Utils.mix(m, a, b);
        }
    };

    /**
     * Mix values. This method shoud return valA for factors less-equal zero,
     * and valB for greather-equal.
     *
     * For every factor, returned value must be in range valA-valB inlusive.
     *
     * Other names used for this method are: larp mix fade.
     *
     * @param factor Value from zero to one inclusive. NaN and INF prohibited.
     * @param valA Leftmost value to mix. NaN and INF prohibited.
     * @param valB Rightmost value to mix. NaN and INF prohibited.
     * @return mixed value.
     * @see #NEAREST
     * @see #LINEAR
     * @see #PERLIN
     */
    float fade(float factor, float valA, float valB);

}
