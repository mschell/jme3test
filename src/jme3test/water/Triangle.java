/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jme3test.water;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.util.BufferUtils;
import java.nio.FloatBuffer;

/**
 *
 * @author matsch1
 */
public class Triangle extends Mesh{

    FloatBuffer fpb;

    public Triangle(){
        fpb = BufferUtils.createFloatBuffer(9);
        Vector3f[] v = new Vector3f[3];
        v[0] = new Vector3f(0.0f, 1.0f, -0.5f);
        v[1] = new Vector3f(-0.5f, 0.0f, -0.5f);
        v[2] = new Vector3f(0.5f, 0.0f, -0.5f);

        fpb.put(new float[] {
                v[0].x, v[0].y, v[0].z, v[1].x, v[1].y, v[1].z, v[2].x, v[2].y, v[2].z
        });
        setBuffer(Type.Position, 3, fpb);
        updateBound();
    }


    public void update(){
        Vector3f[] v = new Vector3f[3];
        v[0] = new Vector3f(0.0f, 1.0f  , -0.5f);
        v[1] = new Vector3f(-0.5f, 0.0f, -0.5f);
        v[2] = new Vector3f(0.5f, 0.0f, -0.5f);

        fpb.rewind();
        fpb.put(new float[] {
                v[0].x, v[0].y, v[0].z, v[1].x, v[1].y, v[1].z, v[2].x, v[2].y, v[2].z
        });
        getBuffer(Type.Position).updateData(fpb);
    }

}
