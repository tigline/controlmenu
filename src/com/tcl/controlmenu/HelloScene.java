/**
 * 
 */
package com.tcl.controlmenu;

import com.badlogic.gdx.Gdx;
import com.tcl.roselauncher.PlayerActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cocos2d.CCDirector;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCSize;
import cocos2d.layers_scenes_transitions_nodes.CCLayer;
import cocos2d.layers_scenes_transitions_nodes.CCScene;
import cocos2d.menu_nodes.CCMenu;
import cocos2d.menu_nodes.CCMenuItemSprite;
import cocos2d.sprite_nodes.CCSprite;

/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-11
 */
public class HelloScene extends CCLayer {
	public static CCSize winSize;
	
	private static Activity sceneContext;
	public static CCScene scene(Activity activity){
		sceneContext= activity;
		winSize = CCDirector.sharedDirector().getVisibleSize();
		CCScene scene = new CCScene();
		scene.init();
		
		CCLayer layer = new HelloScene();
		layer.init();
		
		scene.addChild(layer, 1, 1);
		return scene;
		
	}
	
	@Override
	public boolean init()
	{
		/*
		CCSprite sprite = new CCSprite("data/circle.png");
		sprite.setPosition(winSize.width/2, winSize.height/2);
		this.addChild(sprite);
		8*/
		
		
		Circle circle = new Circle("data/circle.png","超能陆战队");
		circle.setPosition(winSize.width/2, winSize.height/2);
		this.addChild(circle);
		
		
		
		
		//CCLabelTTF title = new CCLabelTTF("开始菜单", "fangzheng.ttf", 24);
		
		
		CCMenuItemSprite pCloseItem = new CCMenuItemSprite("data/CloseNormal.png", "data/CloseSelected.png",this, "goToPlayer");
		// Place the menu item bottom-right conner.
        CCSize visibleSize = CCDirector.sharedDirector().getVisibleSize();
        CCPoint origin = CCDirector.sharedDirector().getVisibleOrigin();
        
        pCloseItem.setPosition(origin.x + visibleSize.width - pCloseItem.getContentSize().width/2,
                origin.y + pCloseItem.getContentSize().height/2);
        //title.setPosition(winSize.width/2, winSize.height/2);
        //this.addChild(title,10);
		CCMenu pMenu = new CCMenu(pCloseItem);
		pMenu.setPosition(new CCPoint(0,0));
		this.addChild(pMenu);				
		
		CCSprite player = new CCSprite();
		player.initWithFile("data/player.png");
		
		player.setPosition(0 + player.getContentSize().width/2,
                0 + visibleSize.height/2);
		
		this.addChild(player, 2, 1);

		this.setTouchEnabled(true);
		

		
		this.scheduleUpdate();
		

		
		return true;
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
	
	
	
}
