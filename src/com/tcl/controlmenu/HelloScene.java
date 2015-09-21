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
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
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

	
	public static ArrayList<int[]> outRat = new ArrayList<int[]>();//处理后的数据
	public static int rate;
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
	private static int count;
	//private String [] infoList = {"碟中谍5","购物","听歌", "关灯","新闻","电视剧" };,
	private String [] infoList = {"碟中谍5", "最新的电影","控制空调","购物","听歌", "关灯","新闻","电视剧"};
	private static Activity sceneContext;
	public static AudioProcess Audioprocess;
	public static CCScene scene(Activity activity, ArrayList<int[]> rat, AudioProcess process){
		sceneContext= activity;
		outRat = rat;
		Audioprocess = process;
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

		CCMenu pMenu = new CCMenu(pCloseItem,pushItem);
		pMenu.setPosition(new CCPoint(0,0));
		this.addChild(pMenu);
		
		//麦克风
		microPhone = new MicroPhone();
		microPhone.setPosition(SCREEN_CENTER);
		microPhone.setVisible(false);
		this.addChild(microPhone);
		//识别提示状态
		checkState = new CCSprite("launcher/analysis.png");
		checkState.setPosition(SCREEN_CENTER);
		checkState.setVisible(false);
		this.addChild(checkState);		
		noticeText = new CCLabelTTF("识别中..。"+"\n" + "请稍后", "fangzheng.ttf", 36);
		noticeText.setPosition(SCREEN_CENTER);
		noticeText.setVisible(false);
		this.addChild(noticeText);
		
		//错误提示状态
		erroState = new CCSprite("launcher/error.png");
		erroState.setPosition(SCREEN_CENTER);
		erroState.setVisible(false);
		this.addChild(erroState);


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
	
	//@SuppressWarnings("unchecked")
	public void update(float dt){
		/*
		while (Audioprocess.isRecording) {
			ArrayList<int[]>buf = new ArrayList<int[]>();
			synchronized (Audioprocess.outBuf) {
				if (Audioprocess.outBuf.size() == 0) {
					continue;
				}
				buf = (ArrayList<int[]>)Audioprocess.outBuf.clone();
				Audioprocess.outBuf.clear();
			}
			//根据ArrayList中的short数组开始绘图
			for(int i = 0; i < buf.size(); i++){
				int[]tmpBuf = buf.get(i);
				Log.d("HelloScene", "FRQ = " + tmpBuf);
			}
		}
			*/
		if (checkInput) {
			angle += 2;
			checkState.setRotation(angle);
			//microPhone.setVisibleValue(false);
			
		}else {
		if (rate > 0) {
			rate -= 0.001;
		}else{
			rate = 100;
		}
		}
		
		if (circleList.size() > 0) {
			for (int i = 0; i < circleList.size(); i++) {
				circleList.get(i).CheckCollision(circleList, dt);
			}
		}
		
		microPhone.updateState(rate);

	}
	
	@Override
	public Boolean touchBegan(CCTouch pTouch)
	{
		
		microPhone.setVisible(true);
		checkInput = false;
		noticeText.setVisible(false);
		checkState.setVisible(false);
		return true;
	
	}
	
	@Override
	public void touchEnded(CCTouch pTouch)
	{
			
			rate = 100;
			/*
			if ((count++)%2==0) {
				microPhone.setVisible(true);
				checkInput = false;
				noticeText.setVisible(false);
				checkState.setVisible(false);
			}else{
				microPhone.setVisible(false);
				checkInput = true;
				noticeText.setVisible(true);
				checkState.setVisible(true);
				
			}
			*/
			microPhone.setVisible(false);
			checkInput = true;
			noticeText.setVisible(true);
			checkState.setVisible(true);
			int num =  (int)MathUtils.random(Integer.MAX_VALUE -1) % 6 + (int)3;
			int index = (int)MathUtils.random(Integer.MAX_VALUE -1) % 7;
			float scale = num/10.0f;
			Circle circle = new Circle(infoList[index],scale);
			
			circle.setPosition(winSize.width/2, winSize.height/2);
			
			circleList.add(circle);
			this.addChild(circle);

	}

}
