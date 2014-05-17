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

/** Hashing permutation table.
 * 
 * Almost every noise algorithm uses permutation table like this internaly.
 * Vornoi is the only one algorithm known to author that dosen't use it,
 * but it is fractal algrithm not pure noise one.
 *
 * @author Piotr SQLek Skólski
 */
public class Permutator {
    
    /** Noise permutators almost allways have this size.
     *
     */
    public final static int DEFAULT_SIZE = 256;
    
    private final int[] permutations;
    
    private Permutator(java.util.Random random, int size) {
        permutations = new int[size];
        for (int i = 0; i < permutations.length; ++i)
            permutations[i] = i;
        
        for (int i = 0; i < permutations.length; ++i) {
            int swapIndex = random.nextInt() & (DEFAULT_SIZE-1);
            //xor-swap not desired, aliasing problem.
            int swapValue = permutations[swapIndex];
            permutations[swapIndex] = permutations[i];
            permutations[i] = swapValue;
        }
    }
    
    /** Creates new permutator with randomly generated seed.
     *
     */
    public Permutator() {
        this(new java.util.Random(),DEFAULT_SIZE);
    }
    
    /** Creates new permutator with specified seed.
     *
     * @param seed Value to initialize random generator.
     */
    public Permutator(long seed) {
        this(new java.util.Random(seed),DEFAULT_SIZE);
    }
    
    /** Creates new permutator with specified size and random seed.
     * 
     * This permutator is used mainly for caching indexes in filters.
     *
     * @param size Size of the permutation table.
     */
    public Permutator(int size) {
        this(new java.util.Random(),Utils.ceilPoT(size));
    }
    
    /** Permutes a int value.
     *
     * @param val Any integer value for permutation.
     * @return Permutabled index in range zero inclusive to size exclusive.
     */
    public int permute(int val) {
        return permutations[val & (permutations.length-1)];
    }
    
    /** Retrive permutation table size.
     *
     * @return Size of permutation table.
     */
    public int size() {
        return permutations.length;
    }
    
}
