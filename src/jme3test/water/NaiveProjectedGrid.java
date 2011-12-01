///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package jme3test.water;
//
//import com.jme3.math.Matrix4f;
//import com.jme3.math.Vector3f;
//import com.jme3.renderer.Camera;
//import com.jme3.scene.Mesh;
//import com.jme3.scene.VertexBuffer.Type;
//import com.jme3.util.BufferUtils;
//
//import java.nio.FloatBuffer;
//import java.nio.IntBuffer;
//
///**
// *
// * @author matsch1
// */
//public class NaiveProjectedGrid extends Mesh {
//
//    protected Camera cam;
//    int sizeX, sizeY;
//    IntBuffer indexBuffer;
//    FloatBuffer vertBuf;
//    Matrix4f modelViewMatrix,projectionMatrix,modelViewProjectionInverse;
//
//    public NaiveProjectedGrid(Camera cam, int sizeX, int sizeY) {
//        this.cam = cam;
//        this.sizeX = sizeX;
//        this.sizeY = sizeY;
//
//        buildVertices();
//        updateBound();
//    }
//
//    private void buildVertices() {
//        vertBuf = BufferUtils.createVector3Buffer(vertBuf, sizeX * sizeY);
//
//
//
//        Vector3f point = new Vector3f();
//        for (int x = 0; x < sizeX; x++) {
//            for (int y = 0; y < sizeY; y++) {
//                point.set(x, 0, y);
//                BufferUtils.setInBuffer(point, vertBuf, (x + (y * sizeX)));
//            }
//        }
//
//        //set up the indices
//        int triangleQuantity = ((sizeX - 1) * (sizeY - 1)) * 2;
//
//        indexBuffer = BufferUtils.createIntBuffer(triangleQuantity * 3);
//
//        setBuffer(Type.Index, 3, indexBuffer);
//
//        //go through entire array up to the second to last column.
//        for (int i = 0; i < (sizeX * (sizeY - 1)); i++) {
//            //we want to skip the top row.
//            if (i % ((sizeX * (i / sizeX + 1)) - 1) == 0 && i != 0) {
//                continue;
//            }
//            //set the top left corner.
//            indexBuffer.put(i);
//            //set the bottom right corner.
//            indexBuffer.put((1 + sizeX) + i);
//            //set the top right corner.
//            indexBuffer.put(1 + i);
//            //set the top left corner
//            indexBuffer.put(i);
//            //set the bottom left corner
//            indexBuffer.put(sizeX + i);
//            //set the bottom right corner
//            indexBuffer.put((1 + sizeX) + i);
//        }
//    }
//
//    public void update() {
//
//        modelViewMatrix.set(cam.getViewMatrix());
//        projectionMatrix.set(cam.getProjectionMatrix());
//        modelViewProjectionInverse.set(modelViewMatrix).multLocal(projectionMatrix);
//        modelViewProjectionInverse.invertLocal();
//
//    }
//}
