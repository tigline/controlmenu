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
	
	public static ArrayList<int[]> outRat = new ArrayList<int[]>();//����������
	static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;  
    static final int audioEncodeing = AudioFormat.ENCODING_PCM_16BIT; 
	int minBufferSize;//�ɼ�������Ҫ�Ļ�������С
	static  int frequency = 8000;//�ֱ���  
	AudioRecord audioRecord;//¼��
	AudioProcess audioProcess = new AudioProcess();//����
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
			//¼��
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
					"��ǰ�豸��֧������ѡ��Ĳ�����"+String.valueOf(frequency)+",������ѡ��", 
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