package com.finder.algorithm;

import java.security.SecureRandom;

/**
 * Created by Deep6 on 2016-12-16.
 * Random class is used to create random numbers
 */

public class Random
{
    // global field
    private SecureRandom random;

    // constructor initializes global field
    public Random()
    {
        this.random = new SecureRandom();
    }

    // produceInt(startInt, endInt) produces a random number between startInt and endInt.
    // It includes startInt and endInt values
    public int produceInt (int startInt, int endInt)
    {
        int length = (endInt - startInt) + 1;

        return (startInt + random.nextInt(length));
    }
}
