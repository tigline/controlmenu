/**
 * 
 */
package com.tcl.controlmenu;



import android.util.Log;



import cocos2d.actions.action_intervals.CCMoveTo;
import cocos2d.cocoa.CCPoint;
import cocos2d.cocoa.CCRect;
import cocos2d.label_nodes.CCLabelTTF;
import cocos2d.predefine.CCTouch;
import cocos2d.sprite_nodes.CCSprite;

import static com.tcl.controlmenu.Constant.*;
/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-10
 */
public class Circle extends CCSprite {
	
	/**
	 * @param string
	 */
	
	public float ACC = 0.5f; //加速度
	public float SPEED;		//
	public float MASS = 10;
	public float COF = 0.05f;
	public float vx ;  //X 分量速度
	public float vy ;
	public float BALL_R;
	public float xOffset;
	public float yOffset;
	
	//private static  Circle circle;
	public Circle(String text, float scale) {
		super("launcher/circle4.png");
		setScale(scale);
		CCRect rect = getBoundingBox(this, scale);
		CCLabelTTF content = new CCLabelTTF( text, "fangzheng.ttf", 36);
		//setAnchorPoint(new CCPoint(0.5f, 0.5f));		
		//content.setPosition(getContentSiye().width*scale/2, getContentSiye().height*scale/2);		
		content.setPosition((rect.getMaxX() - rect.getMinX())/2, (rect.getMaxY() - rect.getMinY())/2);	
		this.setBallR((rect.getMaxX() - rect.getMinX())/2);
		addChild(content);

		//setTouchEnabled(true);
		//setTouchMode(CCTouchMode.OneByOne);
		//registerWithTouchDispatcher();

	}
	
	public void setBallR(float ballR){
		this.BALL_R = ballR;
	}
	
	public float getBallR(){
		return this.BALL_R;
	}
	
	public CCRect getBoundingBox(CCSprite sprite, float scale){
		
		float spriteW = sprite.getContentSize().width;
		float spriteH = sprite.getContentSize().height;
		float scaleW = sprite.getContentSize().width * scale;
		float scaleH = sprite.getContentSize().height * scale;
		
		CCRect m_obRect = new CCRect(
				sprite.getPosition().x - spriteW + (spriteW - scaleW)/2,
				sprite.getPosition().y - spriteH + (spriteH - scaleH)/2,
				sprite.getContentSize().width*scale,
				sprite.getContentSize().height*scale);
		
		return m_obRect;
	}
	
	public void TranslateTo(float dt){
		xOffset = getPositionX() + vx*dt;
		yOffset = getPositionY() + vy*dt;
		this.setPosition(xOffset, yOffset);
	}
	
	public void Checkborder(){

		if(this.yOffset< (0 + BALL_R + DIS_OFFSET)||this.yOffset>(SCREEN_HEIGHT - BALL_R - DIS_OFFSET))//外围
		{
			//碰左挡板或右挡板，y向速度置反
			this.vy=-this.vy;
			//flag=true;
//			Log.d("coll 1"+texId,"x="+this.xOffset+",y="+this.yOffset+", vx="+this.vx+",vy="+this.vy);
		}
		if(this.xOffset< (0 + DIS_OFFSET + BALL_R)||this.xOffset>(SCREEN_WIDTH - BALL_R - DIS_OFFSET))//外围
		{
			//碰前挡板或后挡板，X向速度置反
			this.vx=-this.vx;
			//flag=true;
//			Log.d("coll 2"+texId,"x="+this.xOffset+",y="+this.yOffset+", vx="+this.vx+",vy="+this.vy);
		}
		
	
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
//		//CCLabelTTF content = new CCLabelTTF(textTure, "fangyheng.ttf", 24);
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
