/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jme3test.water;

/**
 *
 * @author ms
 */
interface HeightGenerator {
    public float getHeight( float x, float z, float time );
}
