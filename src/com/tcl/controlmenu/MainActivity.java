package com.tcl.controlmenu;

import android.os.Bundle;
import android.util.Log;
import cocos2d.Cocos2d;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;

import com.tcl.controlmenu.AppDelegate;
import com.tcl.controlmenu.VoiceIdentifyListener.IVoiceListenerCallback;
import static com.tcl.controlmenu.Constant.*;
public class MainActivity extends AndroidApplication {
	

	
	//private AppDelegate appDelegate;09-22 14:45:39.386: E/AndroidRuntime(12291): java.lang.UnsatisfiedLinkError: Native method not found: com.iflytek.msc.MSC.QIVWSessionBegin:([B[BLcom/iflytek/msc/MSCSessionInfo;)[C


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        weakup = false;
        talk = false;
        talkEnd = false;
        onResult = false;
        initMsc();
        VoiceIdentifyListener listener = new VoiceIdentifyListener(this, voiceListenerCallback);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
    	new AppDelegate(this);        
        initialize(Cocos2d.Game(), cfg);
//        
        
	}
    
    private void initMsc() {
		// 设置你申请的应用appid
		StringBuffer paramInit = new StringBuffer();
		paramInit.append("appid=").append(getString(R.string.app_id)).append(",");
		// 设置使用v5+
		paramInit.append(SpeechConstant.ENGINE_MODE).append("=").append(SpeechConstant.MODE_MSC);
		SpeechUtility.createUtility(this, paramInit.toString());
		
		// 加载识唤醒地资源，resPath为本地识别资源路径
		StringBuffer param = new StringBuffer();
		String resPath = ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "ivw/" + getString(R.string.app_id)	+ ".jet");
		param.append(ResourceUtil.IVW_RES_PATH + "=" + resPath);
		param.append("," + ResourceUtil.ENGINE_START + "=" + SpeechConstant.ENG_IVW);
		boolean ret = SpeechUtility.getUtility().setParameter(ResourceUtil.ENGINE_START, param.toString());
		if (!ret) {
			Log.d("LauncherApp", "启动本地引擎失败");
		}
	}
    
    private VoiceIdentifyListener.IVoiceListenerCallback voiceListenerCallback = new IVoiceListenerCallback() {
		
		@Override
		public void onWakeup() {
			// TODO Auto-generated method stub
			weakup = true;
			talkEnd = false;
			talk = false;
			onResult = false;
		}
		
		@Override
		public void onVolumeUpdate(int volume) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onTalkVoiceResult(Object response) {
			// TODO Auto-generated method stub
			onResult = true;
			resultText = response.toString();
		}
		
		@Override
		public void onTalkStart() {
			// TODO Auto-generated method stub
			talk = true;
			talkEnd = false;
		}
		
		@Override
		public void onTalkResult(String result) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onTalkEnd() {
			// TODO Auto-generated method stub
			talkEnd = true;
			talk = false;
			onResult = false;
		}
		
		@Override
		public void onSleep() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onRequestCommand(String question) {
			// TODO Auto-generated method stub
			
		}
		 
		@Override
		public void onError(String error) {
			// TODO Auto-generated method stub
			
		}
	};
    

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    } 
}