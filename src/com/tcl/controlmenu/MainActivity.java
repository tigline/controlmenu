package com.tcl.controlmenu;

import android.os.Bundle;
import cocos2d.Cocos2d;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.tcl.controlmenu.AppDelegate;
public class MainActivity extends AndroidApplication {
	
	//private AppDelegate appDelegate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;

    	new AppDelegate(this);

        
        initialize(Cocos2d.Game(), cfg);
        
        
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    } 
}