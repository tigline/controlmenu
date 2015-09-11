package com.tcl.controlmenu;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import com.tcl.roselauncher.PlayerActivity;

import cocos2d.CCDirector;
import cocos2d.actions.action.CCFiniteTimeAction;
import cocos2d.actions.action_instants.callfunc.CCCallFuncN;
import cocos2d.actions.action_intervals.CCMoveTo;
import cocos2d.actions.action_intervals.CCSequence;
import cocos2d.base_nodes.CCNode;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCRect;
import cocos2d.cocoa.CCSize;
import cocos2d.denshion.CCEffectPlayer;
import cocos2d.denshion.CCMusicPlayer;
import cocos2d.label_nodes.CCLabelTTF;
import cocos2d.layers_scenes_transitions_nodes.CCLayer;
import cocos2d.layers_scenes_transitions_nodes.CCScene;
import cocos2d.menu_nodes.CCMenu;
import cocos2d.menu_nodes.CCMenuItemSprite;
import cocos2d.platform.ISelector;
import cocos2d.platform.Selector;
import cocos2d.predefine.CCColor3B;
import cocos2d.predefine.CCTouch;
import cocos2d.sprite_nodes.CCSprite;
import cocos2d.utils.MathUtils;

public class MenuScene extends CCLayer 
{
	public static CCSize winSize;
	public CCMusicPlayer music;
	public CCEffectPlayer sound;
	private Array<CCSprite> _targets;
	private Array<CCSprite> _projectiles;
	protected int _projectilesDestroyed;
	private ISelector spriteMoveFinished;
	private ISelector gamelogic;
	private static Activity sceneContext;
	public static CCScene scene(Activity context) {
		winSize = CCDirector.sharedDirector().getVisibleSize();
		// TODO Auto-generated method stub
		sceneContext = context;
		CCScene scene = new CCScene();
		scene.init();
		
		CCLayer layer = new MenuScene();
		layer.init();
		
		scene.addChild(layer, 1, 1);
		
		return scene;		
	}
	static CCScene scene()
	{
		CCScene scene = new CCScene();
		scene.init();
		
		CCLayer layer = new MenuScene();
		layer.init();
		
		scene.addChild(layer, 1, 1);
		
		return scene;
	}
	public MenuScene()
	{
		_projectilesDestroyed = 0;
		spriteMoveFinished = new spriteMoveFinished();
		gamelogic = new gamelogic();
	}
	
	@Override
	public boolean init()
	{

		CCLabelTTF title = new CCLabelTTF("开始菜单", "fangzheng.ttf", 24);
		title.setColor(new CCColor3B(255,0,0));
		
		CCMenuItemSprite pCloseItem = new CCMenuItemSprite("data/CloseNormal.png", "data/CloseSelected.png",this, "goToPlayer");
		// Place the menu item bottom-right conner.
        CCSize visibleSize = CCDirector.sharedDirector().getVisibleSize();
        CCPoint origin = CCDirector.sharedDirector().getVisibleOrigin();
        
        pCloseItem.setPosition(origin.x + visibleSize.width - pCloseItem.getContentSize().width/2,
                origin.y + pCloseItem.getContentSize().height/2);
        title.setPosition(winSize.width/2, winSize.height/2);
        this.addChild(title,10);
		CCMenu pMenu = new CCMenu(pCloseItem);
		pMenu.setPosition(new CCPoint(0,0));
		this.addChild(pMenu);				
		
		CCSprite player = new CCSprite();
		player.initWithFile("data/player.png");
		
		player.setPosition(0 + player.getContentSize().width/2,
                0 + visibleSize.height/2);
		
		this.addChild(player, 2, 1);
		
		this.schedule(this.gamelogic, 1.0f);
		this.setTouchEnabled(true);
		
		_targets = new Array<CCSprite>();
		_projectiles = new Array<CCSprite>();
		
		this.scheduleUpdate();
		
		music = new CCMusicPlayer();
		music.open("data/background-music-aac.wav", 1);
		music.play(true);
		
		return true;
	}
	public void openActivity(Activity activity, Class<?> pClass, Bundle bundle) {
		Intent intent = new Intent(activity, pClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Gdx.app.exit();
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
	
	public void menuCloseCallback(Object o)
	{
		Gdx.app.exit();
	}
	
	class gamelogic extends Selector
	{
		@Override
		public void method(float elapsed) {
			MenuScene.this.addTarget();
			
		}	
	}
	
	
	public void gamelogic(float dt)
	{
		this.addTarget();
	}
	
	public void update(float dt)
	{
		Array<CCSprite> projectilesToDelete = new Array<CCSprite>();
	    
	    for (CCSprite it : _projectiles)
	    {
	    	CCSprite projectile = it;
	    	CCRect projectileRect = new CCRect(
	    			projectile.getPosition().x - (projectile.getContentSize().width/2),
	    			projectile.getPosition().y - (projectile.getContentSize().height/2),
	    			projectile.getContentSize().width,
	    			projectile.getContentSize().height);
	    	
	    	Array<CCSprite> targetsToDelete =new Array<CCSprite>();
	    	
	    	for (CCSprite jt : _targets)
	    	{
	    		CCSprite target = jt;
				CCRect targetRect = new CCRect(
					target.getPosition().x - (target.getContentSize().width/2),
					target.getPosition().y - (target.getContentSize().height/2),
					target.getContentSize().width,
					target.getContentSize().height);

				// if (CCRect::CCRectIntersectsRect(projectileRect, targetRect))
	            if (projectileRect.IntersectsRect(targetRect))
				{
					targetsToDelete.add(target);
				}
	    	}
	    	
	    	for (CCSprite jt : targetsToDelete)
	 		{
	    		CCSprite  target = jt;
	 			_targets.removeValue(target,true);
	 			this.removeChild(target, true);
	 			//target.getTexture().getTexture().dispose();
	 			target = null;

	 			_projectilesDestroyed++;
//	 			if (_projectilesDestroyed >= 5)
//	 			{
//	 				GameOverScene *gameOverScene = GameOverScene::create();
//	 				gameOverScene->getLayer()->getLabel()->setString("You Win!");
//	 				CCDirector::sharedDirector()->replaceScene(gameOverScene);
//	 			}
	 		}
	    }
	}
	
	public void addTarget()
	{
		CCSprite target = new CCSprite("data/CCSprite.png");
		CCLabelTTF title = new CCLabelTTF("超能陆战队", "fangzheng.ttf", 48);
		title.setPosition(target.getContentSize().width/3, target.getContentSize().height/3);
		target.addChild(title);
		//CCSprite target = new CCSprite("");
		// Determine where to spawn the target along the Y axis
		CCSize winSize = CCDirector.sharedDirector().getWinSize();
		float minY = target.getContentSize().height/2;
		float maxY = winSize.height -  target.getContentSize().height/2;
		int rangeY = (int)(maxY - minY);
		// srand( TimGetTicks() );
		int actualY =  (int)MathUtils.random(Integer.MAX_VALUE -1) % rangeY + (int)minY;
		
		
		// Create the target slightly off-screen along the right edge,
		// and along a random position along the Y axis as calculated
		target.setPosition( 
			 new CCPoint(winSize.width + (target.getContentSize().width/2), 
	            CCDirector.sharedDirector().getVisibleOrigin().y + actualY) );
		target.setScale(0.5f);
		this.addChild(target);
		
		// Determine speed of the target
		int minDuration = (int)4.0;
		int maxDuration = (int)8.0;
		int rangeDuration = maxDuration - minDuration;
		// srand( TimGetTicks() );
		int actualDuration = (int)MathUtils.random(Integer.MAX_VALUE -1)  % rangeDuration + minDuration;
		
		// Create the actions
		CCFiniteTimeAction actionMove = new CCMoveTo((float)actualDuration, new CCPoint(0 - target.getContentSize().width/2, actualY));
		CCFiniteTimeAction actionMoveDone = new CCCallFuncN (this.spriteMoveFinished);
		
		target.runAction(new CCSequence(actionMove, actionMoveDone));
		
		// Add to targets array
		target.setTag(1);
		_targets.add(target);
	}
	
	
	class spriteMoveFinished extends Selector
	{
		@Override
		public void method(CCNode sender)
		{
			CCSprite sprite = (CCSprite)sender;
			MenuScene.this.removeChild(sprite, true);
		}		
	}

	@Override
	public void touchEnded(CCTouch pTouch)
	{
		// Choose one of the touches to work with
		CCPoint location = pTouch.getLocation();
		
		// Set up initial location of projectile
		
	    CCPoint origin = CCDirector.sharedDirector().getVisibleOrigin();
		CCSprite projectile = new CCSprite("data/projectile.png");
		projectile.setPosition( new CCPoint(origin.x+20, origin.y+winSize.height/2) );

		// Determinie offset of location to projectile
		float offX = location.x - projectile.getPosition().x;
		float offY = location.y - projectile.getPosition().y;
		

		// Bail out if we are shooting down or backwards
		if (offX <= 0) 
		{
			return;
		}

		// Ok to add now - we've double checked position
		this.addChild(projectile);

		// Determine where we wish to shoot the projectile to
		float realX = origin.x+winSize.width + (projectile.getContentSize().width/2);
		float ratio = offY / offX;
		float realY = (realX * ratio) + projectile.getPosition().y;
		CCPoint realDest = new CCPoint(realX, realY);

		// Determine the length of how far we're shooting
		float offRealX = realX - projectile.getPosition().x;
		float offRealY = realY - projectile.getPosition().y;
		float length = (float)Math.sqrt((offRealX * offRealX) + (offRealY*offRealY));
		float velocity = 480/1; // 480pixels/1sec
		float realMoveDuration = length/velocity;
		
		// Move projectile to actual endpoint
		projectile.runAction( new CCSequence(
			new CCMoveTo(realMoveDuration, realDest),
			new CCCallFuncN(this.spriteMoveFinished)
			));

		// Add to projectiles array
		projectile.setTag(2);
		_projectiles.add(projectile);
		
		sound = new CCEffectPlayer();
		sound.open("data/pew-pew-lei.wav", 1);
		sound.setVolume(1);
		sound.play();
	}
	
	
	
	
}
