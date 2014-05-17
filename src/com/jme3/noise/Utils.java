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

/** Utility functions.
 *
 * @author Piotr SQLek Skólski
 */
public class Utils {
    
    /** Computes floored division.
     * Donald E Knuth "The Art of Computer Programming" 1972
     *
     * @param dividend An dividend.
     * @param divisor An divisor.
     * @return floored division.
     */
    public static int floorDiv(int dividend, int divisor) {
        return (int)Math.floor((double)dividend/(double)divisor);
    }
    
    /** Computes floored remainder.
     * Donald E Knuth "The Art of Computer Programming" 1972
     *
     * @param dividend An dividend.
     * @param divisor An divisor.
     * @return floored remainder.
     */
    public static int floorMod(int dividend, int divisor) {
        return dividend - divisor*floorDiv(dividend,divisor);
    }
    
    /** Computes floored remainder or return divident for zero division.
     * Donald E Knuth "The Art of Computer Programming" 1972
     *
     * @param dividend An dividend.
     * @param divisor An divisor.
     * @return floored remainder or divident for zero divisor.
     */
    public static int floorModExt(int dividend, int divisor) {
        if (divisor == 0)
            return dividend;
        return dividend - divisor*floorDiv(dividend,divisor);
    }
    
    /** Coputes linear mix of two arguments.
     * 
     * It is also known as a larp.
     *
     * @param factor Factor for mixing values.
     * @param valA Leftmost value.
     * @param valB Rightmost value.
     * @return Linearly interpolated value in range from valA to valB.
     */
    public static float mix(float factor, float valA, float valB) {
        if (factor < 0.0f) return valA;
        if (factor > 1.0f) return valB;
        return valB * factor + valA * (1.0f-factor);
    }
    
    /** Computes closest power of two that is not less than argument.
     *
     * @param arg Greather than zero and not greather than 2^31.
     * @return Closest power of two not less than argument, or argument itself.
     */
    public static int ceilPoT(int arg) {
        if (arg < 1)
            throw new IllegalArgumentException(
                    "Argument of ceilPoT must be two or greater.");
        if (arg > 0x40000000)
            throw new IllegalArgumentException(
                    "Argument of ceilPoT greater than 2^31.");
        --arg;
        arg |= arg >> 1;
        arg |= arg >> 2;
        arg |= arg >> 4;
        arg |= arg >> 8;
        arg |= arg >> 16;
        return ++arg;
    }
}
