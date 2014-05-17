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

/** Interface for noise sources that can do tiling.
 *
 * @author Piotr SQLek Skólski
 */
public interface TiledNoise {
    
    /** Sets tiling factors for each axis or disable tiling for any axis.
     *
     * Implementators should ignore tiling factors for dimension that
     * don't support.
     * 
     * If tiling can't be set, because of internal object state,
     * {@ling UnsupportedOperationException} should be thrown.
     * 
     * Implementators should not throw any exception for 0 arguments.
     * 
     * Behavior for negative inputs is unspecified.
     * 
     * @param tx Tiling size or 0 for tiling disable.
     * @param ty Tiling size or 0 for tiling disable.
     * @param tz Tiling size or 0 for tiling disable.
     * @param tw Tiling size or 0 for tiling disable.
     */
    void setTiling(int tx, int ty, int tz, int tw);
    
}
