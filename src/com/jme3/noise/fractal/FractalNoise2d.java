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
package com.jme3.noise.fractal;

import com.jme3.noise.Noise2d;
import com.jme3.noise.filter.Filter2d;

/** Under Construction.
 *
 * @author Piotr SQLek Skólski
 */
public class FractalNoise2d extends Filter2d {
    
    public final int octaves;
    
    public FractalNoise2d(Noise2d source, int octaves) {
        super(source);
        this.octaves = octaves;
    }

    @Override
    public float value(float x, float y) {
        float factor = 0;
        float scale = 1;
        float value = 0;
        for (int i = 0; i < octaves; ++i) {
            value += sourceValue(x/scale, y/scale)*scale;
            factor += scale;
            scale /= 2;
        }
        return value/factor;
    }
    
    
}
