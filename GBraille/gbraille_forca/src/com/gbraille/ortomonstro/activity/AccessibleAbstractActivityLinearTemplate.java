/**
 * Copyright (C) 2014 Antonio Rodrigo
 * 
 * This file is part of GBraille Project.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.gbraille.ortomonstro.activity;

import java.util.HashMap;
import java.util.Locale;

import com.splunk.mint.Mint;

import de.akquinet.android.androlog.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.gbraille.libraries.*;
import com.gbraille.ortomonstro.R;

public abstract class AccessibleAbstractActivityLinearTemplate extends Activity implements OnInitListener,
	View.OnTouchListener, GestureDetector.OnDoubleTapListener, OnGestureListener{
	
	/* TAG used with the Log output */
	String TAG = getClassName();
	
	/* Gesture detector */
	GestureDetector gestureDetector;
	
	/* tts var */
	TextToSpeech myTTS = null;
	
	/* LinearLayout reference var */
	LinearLayout container;
	
	/* screen buttons */
	ImageButton[] buttons;
	
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
	
	boolean firstAccess;
	
	// -------------------------------------------------------------------
	// SOUND FUNCTIONS
	// -------------------------------------------------------------------	
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
	
	/* (non-Javadoc)
     * @see android.app.Service#onCreate()
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
		
		firstAccess = true;
		
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
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {		
		Mint.startSession(getActivity());

		/* Application context */
		context = getApplicationContext();
		
		if (firstAccess == false){
			speakWords(myTTS, screenName);			
		}
		
		mSensorManager.registerListener(mSensorListener,
		        mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
		        SensorManager.SENSOR_DELAY_UI);

		super.onResume();
		Log.i(TAG, LogMessages.MSG_ON_RESUME_FINISHED);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStart()
	 */
	protected void onStart() {				
	    super.onStart();
	    clearButtonFocus();
	    Log.i(TAG, LogMessages.MSG_ON_START_FINISHED);
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
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
	
	/* (non-Javadoc)
     * @see android.app.Service#onDestroy()
     */
	protected void onDestroy() {
		super.onDestroy();
		if (myTTS != null) {
			myTTS.shutdown();
			Log.i(TAG, LogMessages.MSG_TTS_FINISHED);
		}
		/* shake unregistration */
    	mSensorManager.unregisterListener(mSensorListener);
		Log.i(TAG, LogMessages.MSG_ON_DESTROY_FINISHED);
		logFunctions.logTextFile(LogMessages.MSG_ON_DESTROY_FINISHED);
	}
	
	/**
	 * double tap on screen
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @param me
	 *        Motion Event
	 * @return true 
	 * @see android.view.GestureDetector.OnDoubleTapListener#onDoubleTapEvent(android.view.MotionEvent)
	 */
	public boolean onDoubleTap(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_ON_DOUBLETAP);
		if (lastKey >= 0 && lastKey < 6) {
			audioFunctions.play2Ticks();
			
			//Inserting delay here
			try {
			    Thread.sleep(250);
			} catch (InterruptedException exception) {
			    exception.printStackTrace();
			}
			
			buttons[lastKey].performClick();
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnDoubleTapListener#onDoubleTapEvent(android.view.MotionEvent)
	 */
	public boolean onDoubleTapEvent(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_ON_DOUBLETAP_EVENT);
		return false;
	}
	
	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnDoubleTapListener#onSingleTapConfirmed(android.view.MotionEvent)
	 */
	public boolean onSingleTapConfirmed(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_SINGLE_TAP_CONFIRMED);
		return false;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onSingleTapUp(android.view.MotionEvent)
	 */
	public boolean onSingleTapUp(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_SINGLE_TAP_UP_SCREEN);
		return false;
	}

	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.MotionEvent)
	 */
	public boolean onDown(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_ON_DOWN);
		return false;
	}

	/* (non-Javadoc)
	 * happens if the user puts his finger down and without moving
	 * @see android.view.GestureDetector.OnGestureListener#onLongPress(android.view.MotionEvent)
	 */
	public void onLongPress(MotionEvent e) {
		Log.i(TAG, LogMessages.MSG_ON_LONG_PRESS);
	}
	
	/* (non-Javadoc)
	 * happens after the user puts his finger down on the screen
	 * and slides his finger across the screen without lifting it (drag event)	 * 
	 * @see android.view.GestureDetector.OnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
	 */
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Log.i(TAG, LogMessages.MSG_ON_SCROLL);
		return false;
	}
	
	/* (non-Javadoc)
	 * @see android.view.GestureDetector.OnGestureListener#onShowPress(android.view.MotionEvent)
	 */
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
	
	void handleTouch(MotionEvent m){
		int pointerCount = m.getPointerCount(); // number of fingers
    	int x, y, id;
    	
    	switch (m.getAction() & MotionEvent.ACTION_MASK){
    		// This is the primary finger
			case MotionEvent.ACTION_DOWN:
				Log.i(TAG, LogMessages.MSG_ACTION_DOWN);
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
				Log.i(TAG, LogMessages.MSG_ACTION_MOVE);
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
				installTTSIntent
						.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installTTSIntent);
				break;			
			default:
				Log.i(TAG, LogMessages.MSG_TTS_NOT_AVAILABLE);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * gestures
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}
	
	/**
	 * oninit method
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void onInit(int arg0) {
		if (arg0 == TextToSpeech.SUCCESS) {
			setTts();
			myTTS.setLanguage(Locale.getDefault());
			speakWords(myTTS, screenName);
		}
		else {
			myTTS = null;
			Log.i(TAG, LogMessages.MSG_FAILED_INITIALIZE_TTS);
			logFunctions.logTextFile(LogMessages.MSG_FAILED_INITIALIZE_TTS);
			
			// if tts is not ok, exit the application
			destroyServices();
			logFunctions.logTextFile(LogMessages.MSG_BUTTON_EXIT_APPLICATION);
			System.exit(0);
			moveTaskToBack(true);
		}

		/* button gestures recognition */
		gestureDetector = new GestureDetector(this, this);

		Log.i(TAG, LogMessages.MSG_ON_INIT);
	}
		
	/**
	 * stop services when the application changes the activity
	 * 
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
	 * remove tts and bugsense when application closes
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void destroyServices(){
		Log.i(TAG, LogMessages.MSG_CLOSING_SERVICES);
		if (myTTS != null) {
			myTTS.shutdown();			
		}
		Mint.closeSession(getActivity());
	}
	
	/**
	 * remove button focus on start
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void clearButtonFocus(){
		getLayout().requestFocus();
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
		Intent intent = new Intent(getActivity(), PlayGameActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear the backstack
		startActivity(intent);
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
			intent.setClassName(keyboardFunctions.getKeyboardPackageName(), keyboardFunctions.getKeyboardActivityName());
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
		if (tts != null && accessibilityFunctions.isAccessibilityEnabled(context) == false){
    		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    	}
    }    
    
    /** 
	 * Convert text to speech with delay if Talkback is disabled
	 *
	 * @author      Antonio Rodrigo
	 * @version		1.0
	 */
    public void speakWordsWithDelay(TextToSpeech tts, String speech, int delay) {        
    	if (tts != null && accessibilityFunctions.isAccessibilityEnabled(context) == false){
			tts.playSilence(delay, TextToSpeech.QUEUE_FLUSH, null);
    		tts.speak(speech, TextToSpeech.QUEUE_ADD, null);
    	}
    }    
	
	/* required classes - the activities need to implement */
	
	/**
	 * retrieves the Activity's Linear Layout
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return LinearLayout
	 */
	protected abstract LinearLayout getLayout();
	
	/**
	 * retrieves the Layout resource
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return layout resource id
	 */
	protected abstract int getLayoutResourceId();
	
	/**
	 * retrieves the current activity
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return current activity
	 */
	protected abstract Activity getActivity();
	
	/**
	 * adds the buttons to variables
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	protected abstract void setViews();
	
	/**
	 * set the number of buttons in the current activity
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	protected abstract void setNumberButtons();
	
	/**
	 * onclick actions for buttons
	 * 
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