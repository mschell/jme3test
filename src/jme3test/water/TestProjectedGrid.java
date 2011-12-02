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
import com.jme3.math.FastMath;
import com.jme3.math.Plane;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Sphere;
import com.jme3.util.SkyFactory;
import com.jme3.water.SimpleWaterProcessor;

/** Sample 1 - how to get started with the most simple JME 3 application.
 * Display a blue 3D cube and view from all sides by
 * moving the mouse and pressing the WASD keys. */
public class TestProjectedGrid extends SimpleApplication {

    MyProjectedGrid grid;
    Geometry projectedGridGeometry;
    Triangle t;
    
    Geometry lightSphere;
    SimpleWaterProcessor waterProcessor;
        
    private Vector3f lightPos =  new Vector3f(33,12,-29);
    

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

        grid = new MyProjectedGrid(timer, cam, 70, 50, 1f , new HeightGenerator());
        projectedGridGeometry = new Geometry("Projected Grid",  grid);  // create cube geometry from the shape

        setSimpleWater();
        //Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); // create a simple material
        
        //mat1.setColor("Color", ColorRGBA.Blue); // set color of material to blue
        //Material mat_tl = new Material(assetManager, "Common/MatDefs/Misc/ColoredTextured.j3md");
        //Texture t = assetManager.loadTexture("Textures/BumpMapTest/Tangent.png");
        //t.setWrap(Texture.WrapMode.Repeat);
        //t.setAnisotropicFilter(1);
        //mat_tl.setTexture("ColorMap",t );
       // mat_tl.setColor("Color", new ColorRGBA(1f,0f,1f, 1f)); // purple
        //projectedGridGeometry.setMaterial(mat_tl);
        
        
        
        //geom1.setMaterial(mat1);                   // set the cube's material
        rootNode.attachChild(projectedGridGeometry);              // make the cube appear in the scene

        projectedGridGeometry.setCullHint(CullHint.Never);
    }

    @Override
    public void simpleUpdate(float tpf) {
       //grid = // new MyProjectedGrid(timer, cam, 5, 5, 1.0f , new HeightGenerator());
       grid.update(cam.getViewMatrix().clone());
       t.update();
    }

    private void setSimpleWater(){
        Node sceneNode = new Node("Scene");
        // load sky
        sceneNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Sky/Bright/BrightSky.dds", false));
        rootNode.attachChild(sceneNode);

      
        
       waterProcessor = new SimpleWaterProcessor(assetManager);
       //setting the scene to use for reflection
       waterProcessor.setReflectionScene(sceneNode);
       //setting the light position
       waterProcessor.setLightPosition(lightPos);
       
       //setting the water plane
       Vector3f waterLocation=new Vector3f(0,-20,0);
       waterProcessor.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y)));
       //setting the water color 
       waterProcessor.setWaterColor(ColorRGBA.Brown);
 
       viewPort.addProcessor(waterProcessor);
 
       //the texture coordinates define the general size of the waves
       grid.scaleTextureCoordinates(new Vector2f(6f,6f));
 
       //creating a geom to attach the water material 
       
       //projectedGridGeometry.setLocalTranslation(-200, -20, 250);
       //projectedGridGeometry.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
       //finally setting the material
       projectedGridGeometry.setMaterial(waterProcessor.getMaterial());
     

        
        
        
    }

}