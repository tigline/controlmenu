package com.tcl.controlmenu;

import java.util.ArrayList;

import android.app.Activity;

import com.badlogic.gdx.graphics.Color;

import cocos2d.CCApplication;
import cocos2d.CCDirector;
import cocos2d.layers_scenes_transitions_nodes.CCScene;

public class AppDelegate extends CCApplication {
	public CCScene scene ;
	public Activity appActivity;
	public ArrayList<int[]> Rat;
	AudioProcess Process;
	public AppDelegate(MainActivity mainActivity, ArrayList<int[]> outRat ,AudioProcess audioProcess){
		super();
		appActivity = mainActivity;
		Rat = outRat;
		Process = audioProcess;
	}
	


	@Override
	public boolean applicationDidFinishLaunching() {
		//initialize director
        CCDirector pDirector = CCDirector.sharedDirector();
        pDirector.setGlClearColor(Color.BLACK);
        pDirector.setOpenGlView();
        
        //CCScene scene = HelloWorldScene.scene();
        scene = HelloScene.scene(appActivity,Rat,Process);
    
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
