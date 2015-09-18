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
import static com.tcl.controlmenu.Constant.*;
/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-11
 */
public class HelloScene extends CCLayer {
	
	
	public static float angle;
	private boolean checkInput;
	public static CCLabelTTF noticeText;
	public static CCSize winSize;
	public static MicroPhone microPhone;
	public static CCSprite erroState;
	public static CCSprite checkState;
	private static CCSprite backGroud;
	private static CCSprite starSky;
	private ArrayList<Circle> circleList ;
	//private String [] infoList = {"碟中谍5","购物","听歌", "关灯","新闻","电视剧" };,
	private String [] infoList = {"碟中谍5", "最新的电影","控制空调","购物","听歌", "关灯","新闻","电视剧"};
	private static Activity sceneContext;
	public static CCScene scene(Activity activity){
		sceneContext= activity;
		winSize = CCDirector.sharedDirector().getVisibleSize();
		CCScene scene = new CCScene();
		backGround(scene);
		scene.init();
		//microPhone(scene);
		CCLayer layer = new HelloScene();
		layer.init();
		
		scene.addChild(layer, 1, 1);
		return scene;
		
	}
	
	@Override
	public boolean init()
	{
		checkInput = false;
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
		
		microPhone = new MicroPhone();
		microPhone.setPosition(SCREEN_CENTER);
		this.addChild(microPhone);
		
		checkState = new CCSprite("launcher/analysis.png");
		checkState.setPosition(SCREEN_CENTER);
		checkState.setVisible(false);
		this.addChild(checkState);
		
		noticeText = new CCLabelTTF("", fontName, fontSize)
		
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
	
	public void update(float dt){
		
		
		if (checkInput) {
			
		}
		if (circleList.size() > 0) {
			for (int i = 0; i < circleList.size(); i++) {
				circleList.get(i).CheckCollision(circleList, dt);
			}
		}
		
		microPhone.updateState(dt);
		
		
	}
	
	@Override
	public void touchEnded(CCTouch pTouch)
	{
			checkInput = true;
			int num =  (int)MathUtils.random(Integer.MAX_VALUE -1) % 6 + (int)3;
			int index = (int)MathUtils.random(Integer.MAX_VALUE -1) % 7;
			float scale = num/10.0f;
			Circle circle = new Circle(infoList[index],scale);
			
			circle.setPosition(winSize.width/2, winSize.height/2);
			
			circleList.add(circle);
			this.addChild(circle);
			/*
			for (int i = 0; i < circleList.size(); i++) {
				CCSprite  target = circleList.get(i);
				this.removeChild(target,true);
				target = null;
			}
			circleList.clear();
			for (int i = 0; i < infoList.length; i++) {
				int num =  (int)MathUtils.random(Integer.MAX_VALUE -1) % 6 + (int)3;
				float scale = num/10.0f;
				Circle project = new Circle(infoList[i],scale);
				circleList.add(project);
			}
			
			for (int i = 0; i < circleList.size(); i++) {
//				float distX =  MathUtils.random(Integer.MAX_VALUE -1) % 1820.0f + 100.0f;
//				float distY =  MathUtils.random(Integer.MAX_VALUE -1) % 980.0f + 100.0f;
//				CCPoint point = new CCPoint(distX, distY);	

				circleList.get(i).vx =  (MathUtils.random(Integer.MAX_VALUE -1) % 1820.0f - 910.0f);
				circleList.get(i).vy =  (MathUtils.random(Integer.MAX_VALUE -1) % 980.0f - 490.0f);
				circleList.get(i).setPosition(winSize.width/2, winSize.height/2);
				//circleList.get(i).TranslateTo(2.5f, point);
				this.addChild(circleList.get(i));
				}
			*/
		// Add to projectiles array
		//projectile.setTag(2);
		//_projectiles.add(projectile);
		
	}
	
	
	
}
