/**
 * 
 */
package com.tcl.controlmenu;



import android.util.Log;

import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.actions.AddListenerAction;

import cocos2d.cocoa.CCPoint;
import cocos2d.label_nodes.CCLabelTTF;
import cocos2d.predefine.CCTouch;
import cocos2d.sprite_nodes.CCSprite;
import cocos2d.touch_dispatcher.CCTouchDispatcher;

/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-10
 */
public class Circle extends CCSprite {
	
	/**
	 * @param string
	 */
	
	private float ACC = 0.5f;
	private float SPEED;
	private float MASS = 10;
	private float COF = 0.05f;
	
	//private static  Circle circle;
	public Circle(String text, float scale) {
		super("launcher/circle4.png");

		CCLabelTTF content = new CCLabelTTF( text, "fangzheng.ttf", 36);
		//setAnchorPoint(new CCPoint(0.5f, 0.5f));
		setScale(scale);
		content.setPosition(getContentSize().width*scale/2, getContentSize().height*scale/2);
		
		addChild(content);
		//setTouchEnabled(true);
		//setTouchMode(CCTouchMode.OneByOne);
		//registerWithTouchDispatcher();

	}
    /*
	public static Circle getInstance(String text, float scale)
	{
		if (null == circle)
		{
			circle = new Circle(text,scale);
		}
		return circle;
	} 
	*/
	@Override
	public Boolean touchBegan(CCTouch pTouch)
	{	
		Log.e("Circle", "touchBegan" );
		//float x = pTouch.getLocation().x;
		//float y = pTouch.getLocation().y;

		//setPosition(x, y);
		return true;
	}

	@Override
	public void touchMoved(CCTouch pTouch)
	{
		float x = pTouch.getLocation().x;
		float y = pTouch.getLocation().y;
		setPosition(x, y);
	}

	@Override
	public void touchEnded(CCTouch pTouch)
	{
		float x = pTouch.getLocation().x;
		float y = pTouch.getLocation().y;
		//setPosition(x, y);
	}
	
//	public Circle(String image){
//
//		//sprite = new CCSprite(image);
//		//CCLabelTTF content = new CCLabelTTF(textTure, "fangzheng.ttf", 24);
//		//content.setPosition(halo.getContentSize().width/2.5f, halo.getContentSize().height/2.5f);
//		//halo.addChild(content);
//		//sprite.setPosition(x, y);
//
//	}
	/*
	public static Circle getInstance(String image)
	{
		if (null == circle)
		{
			circle = new Circle(image);
		}
		return circle;
	}
	

	/*
	public void setLocation(float x, float y){
		this.x = x;
		this.y = y;
		circle.setPosition(x, y);
	}
	
	public static Circle getInstance()
	{
		if (null == parse)
		{
			parse = new Circle();
		}

		return parse;
	}
	
	
	
	
	public CCPoint getPositon(){
		CCPoint point = new CCPoint(x, y);
		return point;
	}
	*/
	
}
