package com.gbraille.forca.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import  com.splunk.mint.Mint;

import de.akquinet.android.androlog.Log;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.GestureDetector.OnGestureListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gbraille.forca.R;
import com.gbraille.libraries.*;

public abstract class AccessibleAbstractActivityBrailleTemplate extends Activity implements OnInitListener,
	View.OnTouchListener, GestureDetector.OnDoubleTapListener, OnGestureListener{
	
	/* TAG used with the Log output */
	final String TAG = getClassName();
	
	/* Gesture detector */
	GestureDetector gestureDetector;
	
	/* tts var */
	TextToSpeech myTTS = null;
	
	/* LinearLayout reference var */
	LinearLayout container;
	
	/* screen buttons */
	ImageButton[] buttons;
	
	String[] nodeDescriptionString;
	
	/* total number of buttons */
	int totalButtons;
	
	/* last button pressed */
	int lastKey = -1;
	
	long longPressStartTm;
	long longPressDuration;
	
	/* screen name */
	String screenName;
	
	/* context */
	Context context;
	
	/* */
	boolean canISpeakScreenName = false;
	
	// functions
	AccessibilityClass accessibilityFunctions;
	AudioClass audioFunctions;
	SplunkMintClass splunkMintFunctions;
	HapticClass hapticFunctions;
	KeyboardClass keyboardFunctions;
	LogClass logFunctions;
	ScreenClass screenFunctions;
	TextToSpeechClass textToSpeechFunctions;
	
	/* accelerometer vars */
	private SensorManager mSensorManager;
	private ShakeEventListener mSensorListener;
		
	// -------------------------------------------------------------------
	// SOUND FUNCTIONS
	// -------------------------------------------------------------------	
	/**
	 * appIntro
	 *     Plays a sound effect and speaks the screen name
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void appIntro(){
		hapticFunctions.vibrate(1);		
		Log.i(TAG, LogMessages.MSG_LAUNCHER_INTRO_BEING_PLAYED);
		logFunctions.logTextFile(LogMessages.MSG_LAUNCHER_INTRO_BEING_PLAYED);
		audioFunctions.playSound(audioFunctions.getIntroSoundId(),1.0f,1.0f);
		speakWordsWithDelay(myTTS, screenName, 1000);
		textToSpeechFunctions.setIsAppNameSpoken(true);
		canISpeakScreenName = true;
	}
	
	/**
	 * speakButton
	 *     pronounces the text and vibrates
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param text
	 *        text to be pronounced
	 */
	void speakButton(String text, float volumeLeft, float volumeRight) {		
		audioFunctions.play1Tick(volumeLeft, volumeRight);
		speakWordsWithDelay(myTTS, text, 400);
	}
	
	/**
	 * getClassName
	 *     retrieves the class name
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return the class name
	 */
	String getClassName(){
		String name;
		Class<?> enclosingClass = getClass().getEnclosingClass();
		if (enclosingClass != null) {
			name = enclosingClass.getSimpleName();
		}
		else {
			name = getClass().getSimpleName();
		}
		return name;
	}
	
	/**
	 * Locates the button on which the motion event occurred and gives focus to that button.
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 * @return true or false 
	 */
	public boolean findButton(MotionEvent me) {
		double y = me.getRawY();
		double x = me.getRawX();
		int[] loc = new int[2];
		int[] dim = new int[2];
		for (int i = 0; i < totalButtons; i++) {
			buttons[i].getLocationOnScreen(loc);
			dim[0] = buttons[i].getWidth();
			dim[1] = buttons[i].getHeight();
			// If the motion event goes over the button, have the button request focus
			if (y <= (loc[1] + dim[1]) && x <= (loc[0] + dim[0])) {
				Log.i(TAG, LogMessages.MSG_BUTTON_WAS_SELECTED + (String) buttons[i].getTag());
				buttons[i].requestFocus();
				if (i != lastKey) {
					longPressStartTm = me.getEventTime();
				}
				lastKey = i;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * onCreate
	 *     Called when the activity is first created.
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* toggle to fullscreen */
		screenFunctions = new ScreenClass();
		screenFunctions.toggleFullScreen(getActivity());
		
		/* Application context */
		context = getApplicationContext();
		
		/* Starts the Bugsense */
		splunkMintFunctions = new SplunkMintClass(this);
		Mint.initAndStartSession(getActivity(), splunkMintFunctions.getBugsenseKey());
		
		setContentView(getLayoutResourceId());
		
		accessibilityFunctions = new AccessibilityClass();
		
		textToSpeechFunctions = new TextToSpeechClass();
				
		/* Check to be sure that TTS exists and is okay to use */
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, textToSpeechFunctions.getReqTTSStatusCheck());
		
		keyboardFunctions = new KeyboardClass();
		
		/* Haptic SDK */
		hapticFunctions = new HapticClass(this);
		hapticFunctions.setHapticLauncher();
				
		setNumberButtons();
		
		setViews();
		
		/* onfocuschange listeners for the buttons*/
		setOnFocusForButtons();
		
		/* onclick listeners for the buttons*/
		setOnClickForButtons();
		
		/* remove focus from buttons */
		clearButtonFocus();
				
		audioFunctions = new AudioClass(this);		
		
		/* sound effects */
		audioFunctions.setOneTickId(R.raw.onetick);
		audioFunctions.setTwoTicksId(R.raw.twoticks);
		audioFunctions.setIntroSoundId(R.raw.magicchime);
		
		/* if the phone is on vibrate or silent mode, change to normal */
		audioFunctions.setAudioStateToNormal();
		
		logFunctions = new LogClass(true,"forca");
		
		/* ShakeDetector initialization */
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);			
		mSensorListener = new ShakeEventListener();
		
		// enables shake to read the text
		mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
		    public void onShake(){
		    	if (! myTTS.isSpeaking()){
		    		Log.i(TAG,LogMessages.MSG_SHAKE);
		    		logFunctions.logTextFile(LogMessages.MSG_SHAKE);		    		
		    		speakWords(myTTS,screenName);		    		
		    	}
			}
		});
		
		Log.i(TAG, LogMessages.MSG_ON_CREATE_FINISHED);
	}

	/**
	 * onResume
	 *     Called after the screen creation, after onStart
	 * @see android.app.Activity#onResume 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	@Override
	protected void onResume() {
		Mint.startSession(getActivity());

		/* Application context */
		context = getApplicationContext();
		
		if (canISpeakScreenName){
			appIntro();
		}
		
		mSensorManager.registerListener(mSensorListener,
		        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		        SensorManager.SENSOR_DELAY_UI);

		super.onResume();

		Log.i(TAG, LogMessages.MSG_ON_RESUME_FINISHED);
	}

	/**
	 * onStart method
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	protected void onStart() {
	    super.onStart();
	    clearButtonFocus();
	    Log.i(TAG, LogMessages.MSG_ON_START_FINISHED);
	}
	
	/** 
	 * onPause 
	 *     called before the screen dissaper for the user
	 * @see android.app.Activity#onPause
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	protected void onPause() {
		Mint.closeSession(getActivity());
		
		/* shake unregistration */
    	mSensorManager.unregisterListener(mSensorListener);
    	
		super.onPause();
		if (myTTS != null) {
			myTTS.stop();
		}
		Log.i(TAG, LogMessages.MSG_ON_PAUSE_FINISHED);
	}
	
	/**
	 * onDestroy
	 *     Android Service destroy - called before Activity destruction
	 * @see android.app.Service#onDestroy()
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	protected void onDestroy() {
		super.onDestroy();
		if (myTTS != null) {
			myTTS.shutdown();
			Log.i(TAG, LogMessages.MSG_TTS_DESTROYED);
		}
		/* shake unregistration */
    	mSensorManager.unregisterListener(mSensorListener);
		Log.i(TAG,LogMessages.MSG_ON_DESTROY_FINISHED);
		logFunctions.logTextFile(LogMessages.MSG_LAUNCHER_WAS_FINISHED);
	}
	
	/**
	 * onDoubleTap
	 *     double tap on screen
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 * @return true 
	 */
	public boolean onDoubleTap(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_DOUBLE_TAP_SCREEN);
		if (lastKey >= 0 && lastKey < 6) {
			audioFunctions.play2Ticks();
			
			//Inserting delay here
			try {
			    Thread.sleep(250);
			} 
			catch (InterruptedException exception) {
			    exception.printStackTrace();
			}
			
			buttons[lastKey].performClick();
		}
		return true;
	}
	
	/**
	 * onDoubleTapEvent
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 * @return false
	 */
	public boolean onDoubleTapEvent(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_ON_DOUBLETAP_EVENT);
		return false;
	}
	
	/**
	 * onSingleTapConfirmed
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 * @return false
	 */
	public boolean onSingleTapConfirmed(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_SINGLE_TAP_CONFIRMED);
		return false;
	}

	/**
	 * onSingleTapUp
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 * @return false
	 */
	public boolean onSingleTapUp(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_SINGLE_TAP_UP_SCREEN);
		return false;
	}

	/**
	 * onDown method
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 * @return false
	 */
	public boolean onDown(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_ON_DOWN);
		return false;
	}

	/**
	 * onLongPress
	 *     happens if the user puts his finger down and without moving
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 */
	public void onLongPress(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_ON_LONG_PRESS);
	}
	
	/**
	 * onScroll
	 *     happens after the user puts his finger down on the screen
	 *     and slides his finger across the screen without lifting it (drag event)
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 * @return false
	 */
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Log.i(TAG, LogMessages.MSG_ON_SCROLL);
		return false;
	}
	
	public void onShowPress(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_ON_SHOW_PRESS);
	}
	
	/**
	 * happens when a touch event occurs. Its the first method to be called
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 * @see android.app.Activity#dispatchTouchEvent(android.view.MotionEvent)
	 */
	public boolean dispatchTouchEvent(MotionEvent me) {
		return super.dispatchTouchEvent(me);
	}
	
	/*
	 * Called for touches inside the button display (pressing, releasing or
	 * dragging)
	 */
	/* ORDER BUTTON TOUCHED: ONTOUCH -> ONMOVE -> SINGLE TAP */
	/*
	 * ORDER BUTTON MOVE: ONTOUCH (BTN 1) - ONMOVE (BTN 1) - FOCUS CHANGED - ON
	 * MOVE (BTN 2) - FOCUS CHANGED
	 */
	public boolean onTouch(View v, MotionEvent m) {
		Log.i(TAG, LogMessages.MSG_ON_TOUCH);

		gestureDetector.onTouchEvent(m);		
		handleTouch(m);

		// Event has been consumed
		return true;
	}
	
	/**
	 * handleTouch
	 *     what is happen when user tap the screen.
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void handleTouch(MotionEvent m){
		int pointerCount = m.getPointerCount(); // number of fingers
    	int x, y, id;
    	
    	switch (m.getAction() & MotionEvent.ACTION_MASK){
    		// This is the primary finger
			case MotionEvent.ACTION_DOWN:
				Log.i(TAG, LogMessages.MSG_MOTION_ACTION_DOWN);
				id = m.getPointerId(0);
				if (id == 0){
					findButton(m);
				}
				break;
			// Another finger is going down. This is not the primary finger
    		case MotionEvent.ACTION_POINTER_DOWN:
    			break;
    		case MotionEvent.ACTION_UP: // for single touch
    			break;
    		case MotionEvent.ACTION_POINTER_UP:
    			break;
			case MotionEvent.ACTION_MOVE:
				Log.i(TAG, LogMessages.MSG_MOTION_ACTION_MOVE);
				id = m.getPointerId(0);
				if (id == 0){
					findButton(m);
				}
				break;
			default:
    			break;
    	}
	}

	/**
	 * onTouchEvent
	 *     Called for touches outside the button display
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public boolean onTouchEvent(MotionEvent me) {
		Log.i(TAG, LogMessages.MSG_ON_TOUCH_EVENT);

		// First finger is touching the screen or Finger is moving on the screen
		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			Log.i(TAG, LogMessages.MSG_MOTION_ACTION_DOWN);
			findButton(me);
		} 
		else if (me.getAction() == MotionEvent.ACTION_MOVE) {
			Log.i(TAG, LogMessages.MSG_MOTION_ACTION_MOVE);
			findButton(me);
		}

		// Event has been consumed
		return true;
	}
	
	/** 
     * onBackPressed
	 *     disables physical back button
	 * @see android.app.Activity#onBackPressed()
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void onBackPressed() {
		// do nothing
	}
	
	/**
	 * onuserinteraction
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void onUserInteraction() {
		super.onUserInteraction();
		Log.i(TAG, LogMessages.MSG_USER_INTERACTION_OCCURRED);
	}
	
	/**
	 * set onfocus actions for buttons
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void setOnFocusForButtons(){
		for (int i = 0; i < totalButtons; i++) {
			/* Called for touches outside the button display (onTouch Method) */
			buttons[i].setOnTouchListener(this);		
		}
	}
	
	/**
	 * onActivityResult
	 * @see #onActivityResult(int, int, android.content.Intent) 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("ON ACTIVITY RESULT");
		if (requestCode == textToSpeechFunctions.getReqTTSStatusCheck()) {
			switch (resultCode) {
			case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
				Log.i(TAG, LogMessages.MSG_TTS_UP_RUNNING);
				myTTS = new TextToSpeech(getApplicationContext(), this);
				break;
			case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
				Intent installTTSIntent = new Intent();
				installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
			default:
				Log.i(TAG, LogMessages.MSG_TTS_NOT_AVAILABLE);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * onFling
	 *     gestures
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/** 
	 * onInit
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	public void onInit(int arg0) {
		if (arg0 == TextToSpeech.SUCCESS) {
			setTts();
			myTTS.setLanguage(Locale.getDefault());			
			// when tts is ok, the button will be enabled
			for (int i = 0; i < totalButtons; i++) {
				buttons[i].setEnabled(true);
			}
						
			// vibra e fala o nome do aplicativo ao iniciar
			appIntro();
		}
		else {
			myTTS = null;
			Log.i(TAG, LogMessages.MSG_FAILED_INITIALIZE_TTS);
			logFunctions.logTextFile(LogMessages.MSG_FAILED_INITIALIZE_TTS);
			
			// if tts is not ok, exit the application
			destroyServices();
			System.exit(0);
			moveTaskToBack(true);
		}

		/* button gestures recognition */
		gestureDetector = new GestureDetector(this, this);

		Log.i(LogMessages.MSG_ON_INIT);
	}
	
	public void speakAppName(){
		if (textToSpeechFunctions.getIsAppNameSpoken() == false) {
			hapticFunctions.vibrate(1);
			textToSpeechFunctions.setIsAppNameSpoken(true);
			canISpeakScreenName = true;
			speakWords(myTTS,getString(R.string.speakBemVindoApp));
		}
		else{
			speakWords(myTTS,screenName);
		}
	}
	
	/**
	 * stopServices
	 *     stop services when the application changes the activity
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void stopServices(){
		Log.i(TAG, LogMessages.MSG_STOPPING_SERVICES);
		if (myTTS != null) {
			myTTS.stop();
		}
		Mint.closeSession(getActivity());
	}
	
	/**
	 * destroyServices
	 *     remove tts and bugsense when application closes
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void destroyServices(){
		Log.i(TAG, LogMessages.MSG_DESTROYING_SERVICES);
		if (myTTS != null) {
			myTTS.shutdown();			
		}
		Mint.closeSession(getActivity());
	}
	
	/**
	 * clearButtonFocus
	 *     remove button focus on start
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void clearButtonFocus(){
		getLayout().requestFocus();
	}
	
	/**
	 * exitActivity
	 *     finishes the activity
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void exitActivity(){
		stopServices();
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		finish();
	}
	
	/**
	 * callBrailleKeyboard
	 *     Calls Braille Keyboard
	 * @author      Antonio Rodrigo
	 * @param       activity
	 * 					activity that calls the Keyboard
	 * @version     1.0 
	 */
	public void callBrailleKeyboard(Activity activity){
		keyboardFunctions.readKeyboardXMLFile(this);
		try{
			logFunctions.logTextFile(LogMessages.MSG_TECLADO_BRAILLE_ACESSADO);
			final Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			intent.setClassName(keyboardFunctions.getKeyboardPackageName(),keyboardFunctions.getKeyboardActivityName());
			activity.startActivityForResult(intent, keyboardFunctions.getKeyboardResponse());
		}
		catch(Exception e){
			logFunctions.logTextFile(LogMessages.MSG_ERROR_ACCESS_BRAILLE_KEYBOARD);
			speakWords(myTTS, getString(R.string.speakErrorAccessBrailleKeyboard));			
		}
	}
		
	/**
	 * speakWords
     *     Speaks the string - no queuing
     * @see TextToSpeech#speak(String, int, HashMap)
     * @param text
     *     The string of text to be spoken.
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
    public void speakWords(TextToSpeech tts, String text) {        
    	if (myTTS != null){
			try{
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			}
			catch (Exception e){
				ttsNotFoundError();
			}
    	}
    }
    
    /** 
     * speakWordsWithDelay
	 *     Convert text to speech with delay if Talkback is disabled
	 * @author      Antonio Rodrigo
	 * @version		1.0
	 */
    public void speakWordsWithDelay(TextToSpeech tts, String speech, int delay) {        
    	if (myTTS != null){
			try{
				tts.playSilence(delay, TextToSpeech.QUEUE_FLUSH, null);
	    		tts.speak(speech, TextToSpeech.QUEUE_ADD, null);
			}
			catch (Exception e){
				ttsNotFoundError();
			}
    	}
    }    
	
	/**
	 * callApp
	 *    it opens an application
	 * @param activity
	 *     Activity name
	 * @param packageName
	 *     Package Name
	 * @param activityName
	 *     Activity Name
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	public void callApp(Activity activity, String packageName, String activityName){
		final Intent intent = new Intent();
		intent.setClassName(packageName, activityName);
		
		if (isCallable(intent) == true){
			try{
				activity.startActivity(intent);
			}
			catch(Exception e){
				Log.i(TAG, LogMessages.MSG_ERROR_OPEN_APP);
				logFunctions.logTextFile(LogMessages.MSG_ERROR_OPEN_APP);
				speakWords(myTTS, getString(R.string.speakErrorOpenApp));
			}
		}
		else{
			Log.i(TAG, LogMessages.MSG_APP_IS_NOT_INSTALLED);
			logFunctions.logTextFile(LogMessages.MSG_APP_IS_NOT_INSTALLED);
			speakWords(myTTS, getString(R.string.speakAplicacaoNaoInstalada));			
		}
	}
	
	public boolean isCallable(Intent intent) {  
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);  
        return list.size() > 0;  
	}
	
	/**
	 * calls the MainActivity
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void returnToMainMenu(){
		Log.i(TAG, LogMessages.MSG_BUTTON_EXIT_APPLICATION + getActivity().getLocalClassName().toString());
		stopServices();
		Intent intent = new Intent(getActivity(), MainScreenActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear the backstack
		startActivity(intent);
		finish();
	}
	
	// tts nao encontrado - precisa fazer a instalacao
	protected void ttsNotFoundError(){
		Log.i(TAG, LogMessages.MSG_TTS_NOT_AVAILABLE);
	   	finish();
	}
	
	/**
	 * dispatchKeyEvent
     *     adjusts audio volume with volume key
     * @see android.view.KeyEvent
     * @param event 
     *     Description of the key event.
	 * @author Antonio Rodrigo
	 * @return {@code true} or {@code false}	
	 * @version 1.0
	 */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
    	int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
        	case KeyEvent.KEYCODE_VOLUME_UP:
        		if (action == KeyEvent.ACTION_DOWN) {     			
        			// do nothing
        		}
        		else if (action == KeyEvent.ACTION_UP) {     			
        			if (audioFunctions.increaseVolume(getActivity())){        				
        				speakWords(myTTS, getString(R.string.speakAudioVolumeIncreased));
        			}
        			else{
        				speakWords(myTTS, getString(R.string.speakAudioVolumeMax));
        			}
        		}        		
        		return true;
        	case KeyEvent.KEYCODE_VOLUME_DOWN:
        		if (action == KeyEvent.ACTION_DOWN) {
        			// do nothing
        		}
        		else if (action == KeyEvent.ACTION_UP) {
        			if (audioFunctions.decreaseVolume(getActivity())){        				
        				speakWords(myTTS, getString(R.string.speakAudioVolumeDecreased));
        			}
        			else{
        				speakWords(myTTS, getString(R.string.speakAudioVolumeMin));
        			}
        		}
        		return true;
        	default:
        		return super.dispatchKeyEvent(event);
        }
    }
	
	/* required classes - the activities need to implement */
	
	/**
	 * getLayout
	 *     retrieves the Activity's Linear Layout
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return LinearLayout
	 */
	protected abstract LinearLayout getLayout();
	
	/**
	 * getLayoutResourceId
	 *     retrieves the Layout resource
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return layout resource id
	 */
	protected abstract int getLayoutResourceId();
	
	/**
	 * getActivity
	 *     retrieves the current activity
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return current activity
	 */
	protected abstract Activity getActivity();
	
	/**
	 * setViews
	 *     adds the buttons to variables
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	protected abstract void setViews();
	
	/**
	 * setNumberButtons
	 *     set the number of buttons in the current activity
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	protected abstract void setNumberButtons();
	
	/**
	 * setOnClickForButtons
	 *     onclick actions for buttons
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	protected abstract void setOnClickForButtons();
	
	/**
	 * tts actions
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */	
	protected abstract void setTts();
}