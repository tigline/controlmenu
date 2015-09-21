package com.tcl.controlmenu;

import java.util.ArrayList;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.widget.Toast;
import cocos2d.Cocos2d;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import com.tcl.controlmenu.AppDelegate;
public class MainActivity extends AndroidApplication {
	
	public static ArrayList<int[]> outRat = new ArrayList<int[]>();//处理后的数据
	static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;  
    static final int audioEncodeing = AudioFormat.ENCODING_PCM_16BIT; 
	int minBufferSize;//采集数据需要的缓冲区大小
	static  int frequency = 8000;//分辨率  
	AudioRecord audioRecord;//录音
	AudioProcess audioProcess = new AudioProcess();//处理
	public int frequence = 0;
	
	//private AppDelegate appDelegate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
    	new AppDelegate(this,outRat,audioProcess);        
        initialize(Cocos2d.Game(), cfg);
        
        try {
			//录音
	        minBufferSize = AudioRecord.getMinBufferSize(frequency, 
	        		channelConfiguration, 
	        		audioEncodeing);
	        //minBufferSize = 2 * minBufferSize; 
	        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,frequency,
	        		channelConfiguration,
	        		audioEncodeing,
	        		minBufferSize);

			audioProcess.frequence = frequency;
			audioProcess.start(audioRecord, minBufferSize);
			
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(MainActivity.this, 
					"当前设备不支持你所选择的采样率"+String.valueOf(frequency)+",请重新选择", 
					Toast.LENGTH_SHORT).show();
		}
        
		
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