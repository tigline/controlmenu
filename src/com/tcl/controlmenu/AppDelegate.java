package com.tcl.controlmenu;

import android.app.Activity;

import com.badlogic.gdx.graphics.Color;

import cocos2d.CCApplication;
import cocos2d.CCDirector;
import cocos2d.layers_scenes_transitions_nodes.CCScene;

public class AppDelegate extends CCApplication {
	public CCScene scene ;
	public Activity appActivity;
	public AppDelegate(){
		super();
	}
	
	public AppDelegate(MainActivity mainActivity) {
		// TODO Auto-generated constructor stub
		appActivity = mainActivity;
		//scene = HelloWorldScene.scene(mainActivity);
	}

	@Override
	public boolean applicationDidFinishLaunching() {
		//initialize director
        CCDirector pDirector = CCDirector.sharedDirector();
        pDirector.setGlClearColor(Color.BLACK);
        pDirector.setOpenGlView();
        
        //CCScene scene = HelloWorldScene.scene();
        scene = HelloScene.scene(appActivity);
    
        pDirector.runWithScene(scene);

		return true;
	}

	@Override	
	public void applicationDidEnterBackground() {
		
	}

	@Override
	public void applicationWillEnterForeground() {
		
	}

	@Override
	public void setAnimationInterval(double interval) {
		
	}
	
}
