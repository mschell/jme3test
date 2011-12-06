package jme3test.water;

import com.jme3.math.FastMath;

/**
 *
 * @author matsch1
 */
public class MyHeightGenerator implements HeightGenerator {

    float omega = 1f;
    float kx =1f;

    public float getHeight(float x, float z, float time){
        return -2+ 1f * FastMath.sin(kx * x - omega * time );
    }
}
