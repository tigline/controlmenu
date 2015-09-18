/**
 * 
 */
package com.tcl.controlmenu;

import cocos2d.cocoa.CCPoint;

/**
 * @Project ControlMenu	
 * @author houxb
 * @Date 2015-9-16
 */
public class Constant {
	public static final float V_THRESHOLD=0.1f;//速度阈值，小于此阈值的速度认为为0
	
	public final static float SCREEN_WIDTH=1920.0f;//桌子底长度
	public final static float SCREEN_HEIGHT=1080.0f;//桌子底宽度
	public final static float DIS_OFFSET=10.0f;//桌子底宽度
	public static final float V_TENUATION=0.999f;//速度衰减系数
	public static final float STANDA_BAll_R = 392.0f;
	public static final CCPoint SCREEN_CENTER = new CCPoint(SCREEN_WIDTH/2, SCREEN_HEIGHT/2);
}
