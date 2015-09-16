/**
 * 
 */
package com.tcl.controlmenu;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.tcl.roselauncher.PlayerActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cocos2d.CCDirector;
import cocos2d.actions.action_instants.callfunc.CCCallFuncN;
import cocos2d.actions.action_intervals.CCMoveTo;
import cocos2d.actions.action_intervals.CCSequence;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCRect;
import cocos2d.cocoa.CCSize;
import cocos2d.denshion.CCEffectPlayer;
import cocos2d.label_nodes.CCLabelTTF;
import cocos2d.layers_scenes_transitions_nodes.CCLayer;
import cocos2d.layers_scenes_transitions_nodes.CCScene;
import cocos2d.menu_nodes.CCMenu;
import cocos2d.menu_nodes.CCMenuItemSprite;
import cocos2d.predefine.CCTouch;
import cocos2d.sprite_nodes.CCSprite;
import cocos2d.utils.MathUtils;

/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-11
 */
public class HelloScene extends CCLayer {
	
	private boolean run = true;
	public static CCSize winSize;

	private static CCSprite backGroud;
	private static CCSprite starSky;
	private ArrayList<Circle> circleList ;
	private String [] infoList = {"碟中谍5","购物" ,"关灯","最新的电影","控制空调","听歌","新闻","电视剧"};
	private static Activity sceneContext;
	public static CCScene scene(Activity activity){
		sceneContext= activity;
		winSize = CCDirector.sharedDirector().getVisibleSize();
		CCScene scene = new CCScene();
		backGround(scene);
		scene.init();
		microPhone(scene);
		CCLayer layer = new HelloScene();
		layer.init();
		
		scene.addChild(layer, 1, 1);
		return scene;
		
	}
	
	@Override
	public boolean init()
	{
		circleList = new ArrayList<Circle>();
				
		CCMenuItemSprite pCloseItem = new CCMenuItemSprite("data/CloseNormal.png", "data/CloseSelected.png",this, "goToPlayer");
		CCMenuItemSprite pushItem = new CCMenuItemSprite("data/CloseNormal.png", "data/CloseSelected.png",this, "menuCloseCallback");
		// Place the menu item bottom-right conner.
        CCSize visibleSize = CCDirector.sharedDirector().getVisibleSize();
        CCPoint origin = CCDirector.sharedDirector().getVisibleOrigin();
        
        pCloseItem.setPosition(origin.x + visibleSize.width - pCloseItem.getContentSize().width/2,
                origin.y + pCloseItem.getContentSize().height/2);
        pushItem.setPosition(origin.x  + pCloseItem.getContentSize().width/2,
                origin.y + pCloseItem.getContentSize().height/2);
        //title.setPosition(winSize.width/2, winSize.height/2);
        //this.addChild(title,10);
		CCMenu pMenu = new CCMenu(pCloseItem,pushItem);
		pMenu.setPosition(new CCPoint(0,0));
		this.addChild(pMenu);				
		//suggestInit(this);
		this.setTouchEnabled(true);

		this.scheduleUpdate();

		return true;
	}
	public void menuCloseCallback(Object o)
	{
		Gdx.app.exit();
	}
	public static void pushInfo(){
		
	}
	public static void backGround(CCScene scene){
		backGroud = new CCSprite("launcher/background.jpg");
		backGroud.setPosition(winSize.width/2, winSize.height/2);
		scene.addChild(backGroud,-2,12);
		
		starSky = new CCSprite("launcher/starsky.png");
		starSky.setPosition(winSize.width/2, winSize.height/2);
		scene.addChild(starSky, -1, 13);
		
	}
	
	public static void microPhone(CCScene scene){
		CCSprite circleBig = new CCSprite("launcher/circle4.png");
		circleBig.setPosition(winSize.width/2, winSize.height/2);
		scene.addChild(circleBig,1,4);

		CCSprite circle3 = new CCSprite("launcher/circle3.png");
		circle3.setPosition(circleBig.getContentSize().width/2, circleBig.getContentSize().height/2);
		circleBig.addChild(circle3,2,3);
		
		CCSprite circle2 = new CCSprite("launcher/circle2.png");
		circle2.setPosition(circleBig.getContentSize().width/2, circleBig.getContentSize().height/2);
		circleBig.addChild(circle2,3,2);
		
		CCSprite circle1 = new CCSprite("launcher/circle1.png");
		circle1.setPosition(circleBig.getContentSize().width/2, circleBig.getContentSize().height/2);
		circleBig.addChild(circle1,4,1);
		
		CCSprite mic = new CCSprite("launcher/mic.png");
		mic.setPosition(circleBig.getContentSize().width/2, circleBig.getContentSize().height/2);
		circleBig.addChild(mic,5,11);
		
	}
	
	
	
	public void openActivity(Activity activity, Class<?> pClass, Bundle bundle) {
		Intent intent = new Intent(activity, pClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//Gdx.app.exit();
		activity.startActivity(intent);
		//Gdx.app.exit();
	}
	public void goToPlayer(Object o){
		Bundle b = new Bundle();
		//Intent intent = new Intent(SceneContext, PlayerActivity.class);
		openActivity(sceneContext, PlayerActivity.class, b);
		//startActivity(intent);
		//finish();
	}
	/**
	 * 默认推荐
	 */
	public void suggestInit(HelloScene helloScene){
		for (int i = 0; i < infoList.length; i++) {
			int num =  (int)MathUtils.random(Integer.MAX_VALUE -1) % 8 + (int)2;
			float scale = num/10.0f;
			Circle projectile = new Circle(infoList[i],scale);
			circleList.add(projectile);
		}
		
		for (int i = 0; i < circleList.size(); i++) {
			float distX =  MathUtils.random(Integer.MAX_VALUE -1) % 1820.0f + 100.0f;
			float distY =  MathUtils.random(Integer.MAX_VALUE -1) % 980.0f + 100.0f;
			CCPoint point = new CCPoint(distX, distY);			
			helloScene.addChild(circleList.get(i));
			circleList.get(i).setPosition(winSize.width/2, winSize.height/2);
			circleList.get(i).runAction(new CCMoveTo(0.5f, point));
			
		}
	}
	

	@Override
	public void touchEnded(CCTouch pTouch)
	{
		// Choose one of the touches to work with
		//CCPoint location = pTouch.getLocation();
		
		// Set up initial location of projectile
		
	    //CCPoint origin = CCDirector.sharedDirector().getVisibleOrigin();
	    //Log.e("Touch", "originX = " + origin.x);
	    //Log.e("Touch", "originY = " + origin.y);
	    
	    /*
		CCSprite projectile = new CCSprite("data/circle.png");
		CCLabelTTF content = new CCLabelTTF("超能陆战队", "fangzheng.ttf", 36);
		content.setPosition(projectile.getContentSize().width/2.5f, projectile.getContentSize().height/2.5f);
		setScale(0.8f);
		projectile.addChild(content);
		*/
	    //Circle projectile = new Circle("超能陆战队",0.8f);
		//projectile.setPosition( new CCPoint(winSize.width/2, winSize.height/2) );

		// Determinie offset of location to projectile
//		float offX = location.x - projectile.getPosition().x;
//		float offY = location.y - projectile.getPosition().y;
		
		// Bail out if we are shooting down or backwards
//		if (offX <= 0) 
//		{
//			return;
//		}
		
		// Ok to add now - we've double checked position
		//this.addChild(projectile);

		// Determine where we wish to shoot the projectile to
//		float realX = origin.x+winSize.width + (projectile.getContentSize().width/2);
//		float ratio = offY / offX;
//		float realY = (realX * ratio) + projectile.getPosition().y;
		//CCPoint realDest = new CCPoint(location.x, location.y);

		// Determine the length of how far we're shooting
		//float offRealX = location.x - projectile.getPosition().x;
		//float offRealY = location.y - projectile.getPosition().y;
		//float length = (float)Math.sqrt((offRealX * offRealX) + (offRealY*offRealY));
		//float velocity = 480/1; // 480pixels/1sec
		//float realMoveDuration = length/velocity;
		
		// Move projectile to actual endpoint
//		projectile.runAction( new CCSequence(
//			new CCMoveTo(realMoveDuration, realDest)
//			//new CCCallFuncN(this.spriteMoveFinished)
//			));
		
		//if (run) {
		/*
			for (CCSprite jt : circleList)
	 		{
	    		CCSprite  target = jt;
	    		circleList.remove(target);
	 			this.removeChild(target, true);
	 			//target.getTexture().getTexture().dispose();
	 			target = null;

	 		}
	 		*/
			//Circle[] projectile = {};
			for (int i = 0; i < circleList.size(); i++) {
				CCSprite  target = circleList.get(i);
				this.removeChild(target,true);
				target = null;
			}
			circleList.clear();
			for (int i = 0; i < infoList.length; i++) {
				int num =  (int)MathUtils.random(Integer.MAX_VALUE -1) % 6 + (int)3;
				float scale = num/10.0f;
				Circle project = new Circle(infoList[i],0.5f);
				circleList.add(project);
			}
			
			for (int i = 0; i < circleList.size(); i++) {
				float distX =  MathUtils.random(Integer.MAX_VALUE -1) % 1820.0f + 100.0f;
				float distY =  MathUtils.random(Integer.MAX_VALUE -1) % 980.0f + 100.0f;
				CCPoint point = new CCPoint(distX, distY);			
				this.addChild(circleList.get(i));
				circleList.get(i).setPosition(winSize.width/2, winSize.height/2);
				circleList.get(i).TranslateTo(2.5f, point);
			}

		// Add to projectiles array
		//projectile.setTag(2);
		//_projectiles.add(projectile);
		
	}
	
	
	
}
