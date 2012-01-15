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
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
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
    Node sceneNode;
    MySimpleWaterProcessor waterProcessor;
    private Vector3f lightPos = new Vector3f(33, 12, -29);

    public static void main(String[] args) {
        TestProjectedGrid app = new TestProjectedGrid();
        app.start(); // start JME3
    }

    @Override
    public void simpleInitApp() {
        //cam.setLocation(new Vector3f(100, 50, 100));
        //cam.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);
        cam.setFrustumPerspective(45.0f, (float) settings.getWidth() / (float) settings.getHeight(), 1f, 2000f);
        cam.setLocation(new Vector3f(0, 1, 0));
        //cam.setRotation(new Quaternion().fromAngleNormalAxis(FastMath.DEG_TO_RAD * 180 , Vector3f.UNIT_Y));
        cam.update();

        grid = new MyProjectedGrid(timer, cam, 100, 70, 1f, new WaterHeightGenerator());
        projectedGridGeometry = new Geometry("Projected Grid", grid);  // create cube geometry from the shape

        /** A simple textured cube. Uses Texture from jme3-test-data library! */
        Box boxshape1 = new Box(1f, 1f, 1f);
        Geometry cube = new Geometry("A Textured Box", boxshape1);
        cube.setLocalTranslation(0, 2, -10);
        Material mat_stl = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture tex_ml = assetManager.loadTexture("Interface/Logo/Monkey.jpg");
        mat_stl.setTexture("DiffuseMap", tex_ml);
        tex_ml.setAnisotropicFilter(3);
        cube.setMaterial(mat_stl);
        rootNode.attachChild(cube);
        addSkybox();
        //Material unshadedMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); // create a simple material
        //unshadedMat.setColor("Color", ColorRGBA.White); // set color of material to blue

        
        
        
        
        projectedGridGeometry.setCullHint(CullHint.Never);
        //mat_tl.setTexture("DiffuseMap", t);
        //mat_tl.setColor("Color", new ColorRGBA(1f,1f,1f, 1f)); // purple
        projectedGridGeometry.setMaterial(setFlatWaterProcessor());
        projectedGridGeometry.setLocalTranslation(0, 0, 0);



        rootNode.attachChild(projectedGridGeometry);

        inputManager.addMapping("fix", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addListener(new ActionListener() {

            public void onAction(String name, boolean isPressed, float tpf) {
                if (isPressed && "fix".equals(name)) {
                    grid.switchFreeze();
                }
            }
        }, "fix");

        /** A white, directional light source */
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(1, -1, -2).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
        //projectedGridGeometry.setCullHint(CullHint.Never);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        FogFilter fog = new FogFilter();
        fog.setFogColor(new ColorRGBA(0.9f, 0.9f, 0.9f, 1.0f));
        fog.setFogDistance(1550);
        fog.setFogDensity(2.0f);
        fpp.addFilter(fog);
       // viewPort.addProcessor(fpp);
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        //grid = // new MyProjectedGrid(timer, cam, 5, 5, 1.0f , new HeightGenerator());
        float[] angles = new float[3];
        cam.getRotation().toAngles(angles);
        System.out.println("yaw angle:" + angles[1] * FastMath.RAD_TO_DEG);

        grid.update(cam.getViewMatrix().clone());

    }

    private void addSkybox() {

        String dir = "jme3test/water/data/skybox/";
        Texture north = assetManager.loadTexture(dir + "1.jpg");
        Texture south = assetManager.loadTexture(dir + "3.jpg");
        Texture east = assetManager.loadTexture(dir + "2.jpg");
        Texture west = assetManager.loadTexture(dir + "4.jpg");
        Texture up = assetManager.loadTexture(dir + "6.jpg");
        Texture down = assetManager.loadTexture(dir + "5.jpg");

        Spatial sky = SkyFactory.createSky(assetManager, west, east, north, south, up, down);
        
        
        sceneNode = new Node("Scene");
        sceneNode.attachChild(sky);
        rootNode.attachChild(sceneNode);
    }
    
    
    private Material setFlatWaterProcessor(){
        
        waterProcessor = new MySimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(sceneNode);
        waterProcessor.setDebug(true);
        viewPort.addProcessor(waterProcessor);

                
        return waterProcessor.getMaterial();
    }
    
    
    private Material getFlatWater(){
        Material flatWater = new Material(assetManager, "jme3test/water/data/FlatWater.j3md");
        flatWater.setVector3("binormal", new Vector3f(0.3f, 0.3f, 0.0f));
        flatWater.setVector3("tangent", new Vector3f(0.0f, 1.0f, 0.0f));
        flatWater.setFloat("normalTranslation", 0.1f);
        flatWater.setFloat("refractionTranslation", 0.1f);
        flatWater.setBoolean("abovewater",true);
        flatWater.setBoolean("useFadeToFogColor",false);
        flatWater.setColor("waterColor", new ColorRGBA( 0.2f, 0.2f, 1.0f, 1.0f ));
        flatWater.setColor("waterColorEnd", new ColorRGBA( 0.3f, 0.3f, 1.0f, 1.0f ));
        flatWater.setColor("fogColor", new ColorRGBA( 0.4f, 0.4f, 0.4f, 0.1f ));
        
        Texture2D dudv = (Texture2D) assetManager.loadTexture("jme3test/water/data/dudvmap.png");
        dudv.setWrap(Texture.WrapMode.Repeat);
        Texture2D normalMap = (Texture2D) assetManager.loadTexture("jme3test/water/data/normalmap3.dds");
        normalMap.setWrap(Texture.WrapMode.Repeat);
        flatWater.setTexture("reflection", assetManager.loadTexture("jme3test/water/data/skybox/1.jpg"));
        flatWater.setTexture("dudvMap", dudv);
        flatWater.setTexture("normalMap", normalMap);
        
        return flatWater;
        
    }
    
    private Material getSimpleWater(){
        
        Material material = new Material(assetManager, "Common/MatDefs/Water/SimpleWater.j3md");
        material.setFloat("waterDepth", 10.0f);
        material.setFloat("waterTransparency", 1 / 10);
        material.setColor("waterColor", ColorRGBA.White);
        material.setVector3("lightPos", new Vector3f(1, -1, 1));

        material.setColor("distortionScale", new ColorRGBA(0.2f, 0.2f, 0.2f, 0.2f));
        material.setColor("distortionMix", new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
        material.setColor("texScale", new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        
        return material;
    }
    
    
//    private Material setWaterProcessor(){
//        waterProcessor = new SimpleWaterProcessor(assetManager);
//        waterProcessor.setReflectionScene(sceneNode);
//        waterProcessor.setDebug(true);
//        viewPort.addProcessor(waterProcessor);        
//        waterProcessor.setLightPosition(lightPos);
//        
//        return waterProcessor.getMaterial();
//    
//    }
}