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
package com.jme3.noise.filter;

import com.jme3.noise.*;

/** Under Construction.
 *
 * @author Piotr SQLek Skólski
 */
public abstract class Filter2d implements Noise1d, Noise2d {

    public final static int DEFAULT_CACHE_SIZE = 4096;
    
    private final Permutator permutator;
    private final Noise2d source;
    private final float[] xArray;
    private final float[] yArray;
    private final float[] valArray;
    private final int size;
    
    protected Filter2d(Noise2d source, int size) {
        this.source = source;
        this.size = size;
        permutator = new Permutator(size);
        xArray = new float[size];
        yArray = new float[size];
        valArray = new float[size];
        for (int i = 0; i < size; ++i)
            valArray[i] = Float.NaN;
    }
    
    protected Filter2d(Noise2d source) {
        this(source,DEFAULT_CACHE_SIZE);
    }
    
    protected float sourceValue(float x, float y) {
        int index = permutator.permute(Float.floatToRawIntBits(x)
                + permutator.permute(Float.floatToRawIntBits(x)));
        
        //check for chache miss
        if (Float.isNaN(valArray[index])
                || xArray[index] != x
                || yArray[index] != y) {
            xArray[index] = x;
            yArray[index] = y;
            valArray[index] = source.value(x, y);
        }
        
        return valArray[index];
    }
    
    @Override
    public float value(float x) {
        return value(x,0);
    }

    @Override
    public abstract float value(float x, float y);
    
}
