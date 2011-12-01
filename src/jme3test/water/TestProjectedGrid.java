/*
 * Copyright (c) 2009-2010 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jme3test.water;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial.CullHint;

/** Sample 1 - how to get started with the most simple JME 3 application.
 * Display a blue 3D cube and view from all sides by
 * moving the mouse and pressing the WASD keys. */
public class TestProjectedGrid extends SimpleApplication {

    MyProjectedGrid grid;
    Geometry geom1;
    Triangle t;

    public static void main(String[] args){
        TestProjectedGrid app = new TestProjectedGrid();
        app.start(); // start JME3
    }

    @Override
    public void simpleInitApp() {
        cam.setLocation(new Vector3f(0,2,10));
        Box b = new Box(Vector3f.ZERO, 1, 1, 1); // create cube shape
        t = new Triangle();
        Geometry geom = new Geometry("Box", t );  // create cube geometry from the shape
        Material mat = new Material(assetManager,
         "Common/MatDefs/Misc/Unshaded.j3md"); // create a simple material
        mat.setColor("Color", ColorRGBA.Orange); // set color of material to blue
        geom.setMaterial(mat);                   // set the cube's material
        rootNode.attachChild(geom);              // make the cube appear in the scene

        grid = new MyProjectedGrid(timer, cam, 70, 50, 1.0f , new HeightGenerator());
        geom1 = new Geometry("Box",  grid);  // create cube geometry from the shape

        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); // create a simple material
        mat1.setColor("Color", ColorRGBA.Blue); // set color of material to blue
        geom1.setMaterial(mat1);                   // set the cube's material
        rootNode.attachChild(geom1);              // make the cube appear in the scene

        geom1.setCullHint(CullHint.Never);
    }

    @Override
    public void simpleUpdate(float tpf) {
       //grid = // new MyProjectedGrid(timer, cam, 5, 5, 1.0f , new HeightGenerator());
       grid.update(cam.getViewMatrix().clone());
       t.update();
    }


}