package com.tcl.controlmenu;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;



/**
 * @author chgang
 * @create time��2015-8-21 ����10:19:53
 * ����ʶ����
 */
public class VoiceIdentifyListener {

	private static final String TAG = "VoiceIdentifyListener";

	private static final int TIME_VOICE_BAR_LAST = 20 * 1000;

//	private RecognizerTalk mEngineTalk = null;
//	private IWakeupOperate mWakeupOperate = null;
//	public static boolean isSpeaking = false;
//	private ITTSControl mTTSControl = null;

	private IVoiceListenerCallback mIVoiceListenerCallback = null;
	
	SharedPreferences mSharedPreferences;
	
	public interface IVoiceListenerCallback {
		/**
		 * ����
		 */
		void onWakeup();
		/**
		 * ����˵����ʼ
		 */
		void onTalkStart();
		/**
		 * ����˵������
		 */
		void onTalkEnd();
		/**
		 * ʶ��������
		 */
		void onTalkResult(String result);
		/**
		 * ʶ����ת����������
		 */
		void onTalkVoiceResult(Object response);
		
		/**
		 * ����״̬��������������״̬����
		 */
		void onSleep();
		/**
		 * ���ִ���
		 * @param error
		 */
		void onError(String error);
		/**
		 * �ص�¼����������С
		 * @param volume
		 */
		void onVolumeUpdate(int volume);
		/**
		 * �ص���������
		 * @param question
		 */
		void onRequestCommand(String question);
	}
	
	public IVoiceListenerCallback getVoiceListenerCallback() {
		return mIVoiceListenerCallback;
	}

	public void setVoiceListenerCallback(IVoiceListenerCallback mIVoiceListenerCallback) {
		this.mIVoiceListenerCallback = mIVoiceListenerCallback;
	}
	
	public VoiceIdentifyListener(Context context, IVoiceListenerCallback voiceListenerCallback) {
		this.mContext = context;
		this.setVoiceListenerCallback(voiceListenerCallback);
		init();
	}

//	public void init() {
//		// TODO ��������ʶ�����
//		mEngineTalk = new RecognizerTalk(LauncherApp.getInstance());
//		// TODO Ϊ����ʶ�����ü����ص�
//		mEngineTalk.setListener(mRecognizerTalkListener);
//		mEngineTalk.setFarFeild(true);
//		// TODO ���������ó�ʼ��
//		mEngineTalk.init();
//		// TODO ���ø��Ի�����
//		// ע�⣬��û�и��Ի����ݣ��÷���Ҳ��Ҫ����һ�Σ�������������Ϊnull
//		mEngineTalk.setUserData(null);
//
//		mEngineTalk.setVadEnable(true);
//		mEngineTalk.setVADTimeout(20000, 2000);
////		mEngineTalk.setRecordingTimeout(20000);
//		
//		mWakeupOperate = (IWakeupOperate) mEngineTalk.getOperate("OPERATE_WAKEUP");
//		//���뻽�Ѵ�
//		List<String> command = new ArrayList<String>();
//		command.add(LauncherApp.getContext().getString(R.string.wake_up_word_1));
//		command.add(LauncherApp.getContext().getString(R.string.wake_up_word_2));
//
//		if (mWakeupOperate != null) {
//			mWakeupOperate.setCommandData(command);
//			mWakeupOperate.setWakeupListener(mWakeupListener);
//			mWakeupOperate.setFarFeild(true);
//		}
//		
//		mTTSControl = TTSFactory.createTTSControl(LauncherApp.getContext(), "");
//		mTTSControl.setStreamType(AudioManager.STREAM_MUSIC);
//		mTTSControl.initTTSEngine(LauncherApp.getContext());
//	}
//
//	/**
//	 * @Description : TODO ����ʶ������ͣ�����ָ�����ص�Э���������໹�������ı����
//	 * @Author : Dancindream
//	 * @CreateDate : 2013-9-29
//	 */
//	private String mTalkType = RecognizerTalk.TYPE_FREETEXT;
//
//	/**
//	 * @Description : TODO ����ʶ��ļ����ص�
//	 * @Author : Dancindream
//	 * @CreateDate : 2013-9-29
//	 */
//	private IRecognizerTalkListener mRecognizerTalkListener = new IRecognizerTalkListener() {
//
//		@Override
//		public void onDataDone() {
//			Log.d(TAG, "onDataDone");
//		}
//
//		@Override
//		public void onInitDone() {
//			Log.d(TAG, "onInitDone+ģ���ʼ�����");
//		}
//
//		@Override
//		public void onTalkStart() {
//			Log.d(TAG, "onTalkStart+����ʶ�����̿�ʼ");
//			mIVoiceListenerCallback.onTalkStart();
//			Utils.recordCurrentTime("onTalkStart");
//			//������start����֮�󣬻���ٻص���֪ͨ�ϲ㣬����ʶ��������Ѿ���ʼ��
//			cancelVoiceTimer();
//			createVoiceTimer();
//		}
//
//		@Override
//		public void onTalkRecordingStart() {
//			Log.d(TAG, "onTalkRecordingStart+¼����ʽ��ʼ");
//		}
//		
//		@Override
//		public void onTalkStop() {
//			Utils.recordCurrentTime("onTalkStop");
//			Log.d(TAG, "onTalkStop+¼���ѽ���");
//			mIVoiceListenerCallback.onTalkEnd();
//		}
//		
//		@Override
//		public void onTalkRecordingStop() {
//			Log.d(TAG, "onTalkRecordingStop+��˷�رպ�Ļص�");
//		}
//		
//		@Override
//		public void onTalkParticalResult(String arg0) {
//			Log.d(TAG, "onTalkParticalResult");
//		}
//		
//		@Override
//		public void onTalkResult(String arg0) {
//			Utils.recordCurrentTime("onTalkResult");
//			mIVoiceListenerCallback.onTalkResult(arg0);
//			Log.d(TAG, "onTalkResultʶ��Ľ��: " + arg0);
//			String temp_question = Utils.punctuation(arg0);
//			Log.d(TAG, "user_question result��" + temp_question);
//			mTTSControl.play(temp_question);
//			if (Utils.isNotEmpty(temp_question) && temp_question.length() > 1) {
//				// ��ȡ���������б� �б��� VoiceCommandCallbackʵ�ָ�������Ķ�̬����
//				List<Command> localCommandList = null;
//				//��ȡ��ǰ�����ִ�еĶ�̬����
//				VoiceCommandCallback callback = LauncherApp.getInstance().getmVoiceCommandCallback();
//				if (callback != null && callback.getLocalCommand() != null) {
//					localCommandList = callback.getLocalCommand();
//				}
//				//��������ʶ������ȡ���ص�������û�У������������������
//				VoiceResponse response = LocalDynamicCommand.getInstance().getLocalCommand(localCommandList, temp_question);
//				
//				if (response != null) {
//					//����ʶ��ɹ�
//					Log.d(TAG, "����ʶ��ok:" + response.getCommand());
//					Log.d(TAG, "����ʶ��ok:" + response.toString());
//					if(CommandConstants.ROSE_TV_WAKE_UP.equals(response.getCommand())){
//						//�ص�������״̬
//						isSpeaking = false;
//						judgeHandler.sendEmptyMessage(Integer.MAX_VALUE);
//						return;
//					}
//					mIVoiceListenerCallback.onTalkVoiceResult(response);
//				} else {
//					Log.d(TAG, "����ʶ��no:");
//					mIVoiceListenerCallback.onRequestCommand(temp_question);
//				}
//				
//////				voice_controller();
//			} else {
//				//δ��ȡ������ʶ����
//				mTTSControl.play(LauncherApp.getContext().getString(R.string.tips_no_result));
//				mIVoiceListenerCallback.onError(LauncherApp.getContext().getString(R.string.tips_no_result));
//			}
//			judgeHandler.sendEmptyMessageDelayed(Integer.MAX_VALUE, 1500);
//		}
//		
//		@Override
//		public void onTalkError(ErrorUtil arg0) {
//			Log.d(TAG, "onTalkError+�쳣����");
//			mIVoiceListenerCallback.onError(arg0.message);
//			mIVoiceListenerCallback.onSleep();
//			judgeHandler.sendEmptyMessage(Integer.MAX_VALUE);
//		}
//		
//		@Override
//		public void onTalkCancel() {
//			Log.d(TAG, "onTalkCancel+����ʶ��������ȡ��");
//			judgeHandler.sendEmptyMessage(Integer.MAX_VALUE);
//		}
//
//		@Override
//		public void onTalkProtocal(String arg0) {
//			Log.d(TAG, "onTalkProtocal+����Э��ص�"+arg0);
//			judgeHandler.sendEmptyMessage(Integer.MAX_VALUE);
//		}
//
//		@Override
//		public void onUserDataCompile() {
//			Log.d(TAG, "onUserDataCompile+���Ի����ݿ�ʼ����");
//		}
//
//		@Override
//		public void onUserDataCompileDone() {
//			Log.d(TAG, "onUserDataCompileDone+���Ի����ݱ������");
//		}
//
//		@Override
//		public void onVolumeUpdate(int arg0) {
//			mIVoiceListenerCallback.onVolumeUpdate(arg0);
////			Log.d(TAG, "onVolumeUpdate¼��ʱ�������ݵĻص�");
//		}
//	};
//	
//	/**
//	 * ���ѻص��ӿ�
//	 */
//	IWakeupListener mWakeupListener = new IWakeupListener() {
//
//		@Override
//		public void onSuccess(String arg0) {
//			Log.d(TAG, "mWakeupListener onSuccess ���ѳɹ�ʱ�ص�: " + arg0);
//			isSpeaking = true;
//			cancelVoiceTimer();
//			createVoiceTimer();
////			TipsVoicePlayer.getInstance(LauncherApp.getContext()).play();
//			mTTSControl.play("�������");
//			mIVoiceListenerCallback.onWakeup();
//			mEngineTalk.start(mTalkType);
//		}
//
//		@Override
//		public void onStop() {
//			Log.d(TAG, "mWakeupListener : onStop���ѷ���ֹͣ");
//		}
//
//		@Override
//		public void onStart() {
//			Log.d(TAG, "mWakeupListener : onStart�������ѷ���");
//		}
//
//		@Override
//		public void onInitDone() {
//			Log.d(TAG, "mWakeupListener onInitDone���ѳ�ʼ������");
//			mWakeupOperate.startWakeup();
//		}
//
//		@Override
//		public void onError(ErrorUtil arg0) {
//			Log.d(TAG, "mWakeupListener onError����ģ���쳣");
//		}
//	};
//	
//
//	/**
//	 * ��Ҫ�ӳ�1.5��ִ��
//	 */
//	private Handler judgeHandler = new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			judgeVoiceOrWackup();
//		}
//		
//	};
//	
//	/**
//	 * �ж��������ǻ�������
//	 */
//	public void judgeVoiceOrWackup() {
//		Log.d(TAG, "judge_voice_wackup :" + isSpeaking);
//		if (isSpeaking) {
//			mEngineTalk.start(mTalkType);
//		} else {
//			mIVoiceListenerCallback.onSleep();
//			mWakeupOperate.startWakeup();
//		}
//	}
//
//	/**
//	 * ��¼����ʱ�䣬��һ��ʱ��û����������֮�󣬻ص�����״̬
//	 */
//	private Timer voice_timer = new Timer();
//	private TimerTask voice_timer_task = null;
//	/**
//	 * ����¼�붨ʱ��
//	 */
//	private void createVoiceTimer() {
//		if (voice_timer_task == null) {
//			voice_timer_task = new TimerTask(){
//
//				@Override
//				public void run() {
//					Log.d(TAG, "VoiceTimerTask:" + isSpeaking);
//					isSpeaking = false;
//				}
//				
//			};
//		}
//		
//		if(voice_timer != null){
//			voice_timer.schedule(voice_timer_task, TIME_VOICE_BAR_LAST);
//		}
//	}
//	
//	/**
//	 * ȡ����ʱ����
//	 */
//	private void cancelVoiceTimer(){
//		if (null != voice_timer_task) {
//			voice_timer_task.cancel();
//			voice_timer_task = null;
//		}
//	}
//	
//
//	/**
//	 * ��������
//	 * @param text �����ŵ�����
//	 */
//	public void playTTS(String text) {
//		if (TextUtils.isEmpty(text)) {
//			LogUtil.w(TAG, "playTTS:text empty!");
//			return;
//		}
//		if (mTTSControl == null) {
//			Log.d(TAG, "playTTS:mTTSControl null!");
//			return;
//		}
//		LogUtil.d(TAG, "playTTS:text " + text);
//		cancelTTS();
//		mTTSControl.play(text);
//	}
//
//	private void cancelTTS() {
//		if (mTTSControl == null) {
//			Log.d(TAG, "cancelTTS:mTTSControl null!");
//			return;
//		}
//		mTTSControl.cancel();
//	}
//
//	TTSPlayerListener mTTSPlayerListener = new TTSPlayerListener() {
//
//		@Override
//		public void onTtsData(byte[] arg0) {
//		}
//
//		@Override
//		public void onPlayEnd() {
//			Log.d(TAG, "onPlayEnd");
//		}
//
//		@Override
//		public void onPlayBegin() {
//		}
//
//		@Override
//		public void onInitFinish() {
//		}
//
//		@Override
//		public void onError(USCError arg0) {
//		}
//
//		@Override
//		public void onCancel() {
//		}
//
//		@Override
//		public void onBuffer() {
//		}
//	};
	
	
	/*****************************Ѷ������****************************************/
	private Context mContext;
	// ������д����
	private SpeechRecognizer mIat;
	// �������Ѷ���
	private VoiceWakeuper mIvw;
//	private SpeechSynthesizer mTts;
	// ���ѽ������
	private String resultString;
	// ��������ֵ �� ����ֵԽ��Խ���ױ�����
	private final static int MIN = -20;
	private int curThresh = MIN;	
	private String user_request = "";
	
	private void init() {
		if (mSharedPreferences == null){
    		mSharedPreferences = mContext.getSharedPreferences("ControlMenu", Context.MODE_PRIVATE);
    	}
		
		// ��ʼ�����Ѷ���
		VoiceWakeuper.createWakeuper(mContext, null);
		// ��ʼ��ʶ�����
		mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
		mIvw = VoiceWakeuper.getWakeuper();
		if(mIvw != null){
			// ��ղ���
			mIvw.setParameter(SpeechConstant.PARAMS, null);
			// ����ʶ������
			mIvw.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
			// ��������ֵ��������ԴЯ���Ļ��Ѵʸ������ա�id:����;id:���ޡ��ĸ�ʽ����
			mIvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:" + curThresh);
			// wakeup ���û���ģʽ
			mIvw.setParameter(SpeechConstant.IVW_SST, "oneshot");//oneshot ���û���+ʶ��ģʽ
			// ���÷��ؽ����ʽ
			mIvw.setParameter(SpeechConstant.RESULT_TYPE, "json");
			// ���ó������л���
//			mIvw.setParameter(SpeechConstant.KEEP_ALIVE, "1");
//			mIvw.setParameter(SpeechConstant.CLOUD_GRAMMAR, mCloudGrammarID);// �����ƶ�ʶ��ʹ�õ��﷨id
		}
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				start_xf_wackup();
			}
		}, 5000);
//		mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
	}
	
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d(TAG, "InitListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				Log.d(TAG, "onInit error : " + code);
			}
		}
	};
	
	private WakeuperListener mWakeuperListener = new WakeuperListener() {

		@Override
		public void onResult(WakeuperResult result) {
			try {
				if(mIVoiceListenerCallback != null){
					isSpeaking = true;
//					TipsVoicePlayer.getInstance(mContext).play();
					String text = result.getResultString();
					Log.d(TAG, "onResult��"+text);
					voice_controller();
					stop_xf_wackup();
					start_xf_voice();
					mIVoiceListenerCallback.onWakeup();
				}
			} catch (Exception e) {
				Log.d(TAG, "onResult Error");
				e.printStackTrace();
			}
			
		}

		@Override
		public void onError(SpeechError error) {
			Log.d(TAG, "onError��"+error.getPlainDescription(true));
		}

		@Override
		public void onBeginOfSpeech() {
			Log.d(TAG, "onBeginOfSpeech��"+"��ʼ˵��");
			if(mIVoiceListenerCallback != null){
				mIVoiceListenerCallback.onSleep();
			}
		}

		@Override
		public void onEvent(int eventType, int isLast, int arg2, Bundle obj) {

		}
	};
	
	/**
	 * ��ʼ����������
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				Log.d(TAG, "��ʼ��ʧ��,�����룺"+code);
        	}
		}
	};
	
	/**
	 * ��������
	 * @param param
	 * @return 
	 */
	private void setParam(){
		// ��ղ���
		mIat.setParameter(SpeechConstant.PARAMS, null);
		String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
		// ��������
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

		if (lag.equals("en_us")) {
			// ��������
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
		}else {
			// ��������
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// ������������
			mIat.setParameter(SpeechConstant.ACCENT, lag);
		}

		// ��������ǰ�˵�
		mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
		// ����������˵�
		mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
		// ���ñ�����
		mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));
		// ������Ƶ����·��
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/iflytek/wavaudio.pcm");// ������Ƶ����·��
	}
	
	/**
	 * ��д��������
	 */
	private RecognizerListener recognizerListener=new RecognizerListener(){

		@Override
		public void onBeginOfSpeech() {	
			Log.d(TAG, "��д������---��ʼ˵��");
			if(mIVoiceListenerCallback != null){
				mIVoiceListenerCallback.onTalkStart();
			}
		}


		@Override
		public void onError(SpeechError error) {
			if(mIVoiceListenerCallback != null){
				mIVoiceListenerCallback.onError("������û��˵��Ŷ.");
			}
//			error.getErrorCode();
			Log.d(TAG, "��д������---onError" + error.getPlainDescription(true));
			judge_voice_wackup();
		}

		@Override
		public void onEndOfSpeech() {
			if(mIVoiceListenerCallback != null){
				mIVoiceListenerCallback.onTalkEnd();
			}
			Log.d(TAG, "��д������---����˵��");
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {	
			String text = JsonParser.parseIatResult(results.getResultString());
			Log.d(TAG, "��д������---text"+text+isLast);
			user_request = user_request + text;
			if(isLast) {
				String temp_question = Utils.punctuation(user_request);
				Log.d(TAG, "��д������---temp_question:"+temp_question);
				if (Utils.isNotEmpty(temp_question) && temp_question.length() > 1) {
					mIVoiceListenerCallback.onRequestCommand(temp_question);
					mIVoiceListenerCallback.onTalkVoiceResult(temp_question);
//					// ��ȡ���������б� �б��� VoiceCommandCallbackʵ�ָ�������Ķ�̬����
//					List<Command> localCommandList = null;
//					//��ȡ��ǰ�����ִ�еĶ�̬����
//					VoiceCommandCallback callback = LauncherApp.getInstance().getVoiceManagerService().getmVoiceCommandCallback();
//					if (callback != null && callback.getLocalCommand() != null) {
//						localCommandList = callback.getLocalCommand();
//					}
//					//��������ʶ������ȡ���ص�������û�У������������������
//					VoiceResponse response = LocalDynamicCommand.getInstance().getLocalCommand(localCommandList, temp_question);
//					
//					if (response != null) {
//						//����ʶ��ɹ�
//						Log.d(TAG, "����ʶ��ok:" + response.toString());
//						if(Constants.ROSE_TV_WAKE_UP.equals(response.getCommand())){
//							//�ص�������״̬
//							isSpeaking = false;
//							judge_voice_wackup();
//							return;
//						}
//						mIVoiceListenerCallback.onTalkVoiceResult(response);
//					} else {
//						Log.d(TAG, "����ʶ��no:");
//						mIVoiceListenerCallback.onRequestCommand(temp_question);
//					}
//					voice_controller();
				}
				
				judge_voice_wackup();
				user_request = "";
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			if(mIVoiceListenerCallback != null){
				mIVoiceListenerCallback.onVolumeUpdate(volume);
			}
//			Log.d(TAG, "��ǰ����˵����������С��" + volume);
		}


		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			
		}
	};
	
	/**
	 * ��ʼ˵��¼��
	 */
	private void start_xf_voice() {
		user_request = "";
		setParam();
		int retSpeech = mIat.startListening(recognizerListener);
		if(retSpeech != ErrorCode.SUCCESS){
			Log.d(TAG, "��дʧ��,�����룺" + retSpeech);
		}else {
			Log.d(TAG, "��ʼ˵��¼��!");
		}
	}
	
	/**
	 * ����Ѷ����������
	 */
	private void start_xf_wackup() {
		Log.d(TAG, "����Ѷ���������ѣ�");
		mIvw = VoiceWakeuper.getWakeuper();
		// �ǿ��жϣ���ֹ���ָ��ʹ�������
		if (mIvw != null) {
			mIvw.startListening(mWakeuperListener);
		} else {
			Log.d(TAG, "����δ��ʼ����");
		}
	}
	
	/**
	 * ֹͣѶ����������
	 */
	private void stop_xf_wackup() {
		mIvw = VoiceWakeuper.getWakeuper();
		if (mIvw != null) {
			mIvw.stopListening();
		} else {
			Log.d(TAG, "wakeup uninit");
		}
	}
	
	/**
	 * �������ǻ��������ж�
	 */
	public void judge_voice_wackup() {
		Log.d(TAG, "judge_voice_wackup:"+isSpeaking);
		if (isSpeaking) {
			start_xf_voice();
		} else {
			start_xf_wackup();
		}
	}
	
	public boolean isSpeaking() {
		return isSpeaking;
	}
	
	/**
	 * ¼��������
	 */
	private boolean isSpeaking = true;
	private Timer voice_timer = new Timer();
	private VoiceTimerTask voice_timerTask = new VoiceTimerTask();

	class VoiceTimerTask extends TimerTask {
		@Override
		public void run() {
			Log.d(TAG, "VoiceTimerTask" + isSpeaking);
			isSpeaking = false;
		}
	}
	/**
	 * ����¼�붨ʱ��
	 */
	private void voice_controller() {
		if (null != voice_timerTask) {
			voice_timerTask.cancel();
		}

		voice_timerTask = new VoiceTimerTask();
		voice_timer.schedule(voice_timerTask, TIME_VOICE_BAR_LAST);
	}
	
}
