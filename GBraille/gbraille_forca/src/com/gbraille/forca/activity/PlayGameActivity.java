package com.gbraille.forca.activity;

import java.util.HashMap;
import java.util.Locale;

import  com.splunk.mint.Mint;
import com.gbraille.forca.DifficultyClass.DifficultyLevel;
import com.gbraille.forca.database.DbAdapter;
import com.gbraille.forca.HangmanClass;
import com.gbraille.forca.MainFunctions;
import com.gbraille.forca.R;

import de.akquinet.android.androlog.Log;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.TextView;

import com.gbraille.libraries.*;

public class PlayGameActivity extends Activity implements OnInitListener {
	/* activity tag */
	private final String TAG = "PlayEasyGameActivity";
	
	/* text to speech */
	private TextToSpeech myTTS;
	
	/* views */
	private TextView answerTextView;
	
	/* Hangman class */
	private HangmanClass hangman;
	
	/* timers */
    private Handler timerHandler = new Handler();
    
    /* task time */
	private int task1InitializationTime = 100;
	private int task1SubsequentTime = 800;
	
	/* expected response from Braille Keyboard */
	private static final int BRAILLE_KEYBOARD_RESPONSE = 1;
	private static final int EXIT_CODE = 999;
	private static final int QUIT_CODE = 888;
	
	int charIndexCounter;	
	private String returnedText = "";
	private int letterInputErrors = 0;
	private final int MAXERRORS = 6;
	
	/* functions */
	private SplunkMintClass bugsenseFunctions;
	private LogClass logFunctions;
	private HapticClass hapticFunctions;
	private ScreenClass screenFunctions;
	private KeyboardClass keyboardFunctions;
	private TextToSpeechClass textToSpeechFunctions;
	private Narrator ttsPhonetics;
		
	/* soundpool vars */
	private SoundPool soundPool;
	private boolean loaded = false;
	private int tick1;
	
	DbAdapter dbAdapter;
	
	/* check if a char was found - only for hard mode */
	boolean encontrado = false;
	
	// ------------------------------------------------
	// HANGMAN FUNCTIONS
	// ------------------------------------------------
	
	/**
	 * hangMan
	 *     Draws a hangman on screen
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void showHangmanBodyParts(){
		task1InitializationTime = 3000;
		hangman.setAllBodyPartsInvisible();
		hangman.showVisibleBodyParts(letterInputErrors);
	}
	
	/**
	 * speakBodyPartDrawn
	 *     Speaks the body part that is being drawing
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void speakBodyPartDrawn(){
		switch(letterInputErrors){
			case 1:
				speakWords("NEW_TRY", getString(R.string.speakHeadWasDrawn));
				break;
			case 2:
				speakWords("NEW_TRY", getString(R.string.speakTummyWasDrawn));
				break;
			case 3:
				speakWords("NEW_TRY", getString(R.string.speakRightArmWasDrawn));
				break;
			case 4:
				speakWords("NEW_TRY", getString(R.string.speakLeftArmWasDrawn));
				break;
			case 5:
				speakWords("NEW_TRY", getString(R.string.speakRightLegWasDrawn));
				break;
			case 6:
				speakWords("NEW_TRY", getString(R.string.speakLeftLegWasDrawn));
				break;
		}
	}
	
	private void speakTip(){
		speakWords("SPELL_LETTERS", MainFunctions.question);
	}
	
	// ------------------------------------------------
	// TIMER FUNCTIONS
	// ------------------------------------------------
	
	/**
	 * stopTimer
	 *     stops the runnable
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void stopTimer(){
		Log.i(TAG, "STOPPING TIMER");
		timerHandler.removeCallbacksAndMessages(runnable);
    }
	
	/**
	 * runnable
	 *     A runnable that spells a char
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private Runnable runnable = new Runnable() {	   
	   @Override
	   public void run() {
		   charIndexCounter++;
		   Log.i(TAG, "charIndexCounter = " + charIndexCounter);
       	   String character = MainFunctions.answerCharList.get(charIndexCounter - 1);
       	   Log.i(TAG, "character = " + character);
		   answerTextView.append( " " + character + " " );
			
		   // if the char is not the last
		   if (charIndexCounter < MainFunctions.selectedAnswerLength){
		       if (character.equals("_")){
					play1Tick();
			   }
			   else{
					speakWords(myTTS, ttsPhonetics.getTTS(character));							
			  }
		   }
		   // if the char is the last
		   else{
				if (character.equals("_")){
					play1Tick();
					speakWords("INPUT_CHAR", getString(R.string.speakWriteALetter));
				}
				else{
					speakWords("INPUT_CHAR", character + " : " + getString(R.string.speakWriteALetter));
				}
		   }
		   
		   /* repeat the action if the current char is not the last */
		   if (charIndexCounter < MainFunctions.selectedAnswerLength){
		       timerHandler.postDelayed(this, task1SubsequentTime);
		   }
		   /* if the char spelled is the last, dont repeat and stop the timer */
		   else{
			   stopTimer();
		   }
	   }
	};
	
	/**
	 * startGame
	 *     Starts the game
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void startGame(){		
		timerHandler.postDelayed(runnable, task1InitializationTime);
	}

	// ------------------------------------------------
	// ANDROID EVENTS FUNCTIONS
	// ------------------------------------------------
	
	/**
	 * onCreate
	 *     Called when the activity is first created.
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);		
				
		/* toggle to fullscreen */
		screenFunctions = new ScreenClass();
		screenFunctions.toggleFullScreen(PlayGameActivity.this);
		
		setContentView(R.layout.activity_playgame);
		
		/* Starts the Bugsense */
		bugsenseFunctions = new SplunkMintClass(this);
		Mint.initAndStartSession(getActivity(), bugsenseFunctions.getBugsenseKey());
				
		/* database reference */
		dbAdapter = new DbAdapter(this.getApplicationContext());
		
		/* log Functions */
		logFunctions = new LogClass(true, "forca");
		
		/* tts phonectics */
		ttsPhonetics = new Narrator();
		
		/* tts functions */
		textToSpeechFunctions = new TextToSpeechClass();
		
		/* keyboard functions */
		keyboardFunctions = new KeyboardClass();		
		
		/* Haptic SDK functions */
		hapticFunctions = new HapticClass(this);
		hapticFunctions.setHapticLauncher();
		
		/* SoundPool */
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
		    public void onLoadComplete(SoundPool sound, int sampleId,int status) {
		       loaded = true;	       
		    }
		});
		
		/* Tick sound effect */
		tick1 = soundPool.load(this, R.raw.onetick, 1);
		
		/* Check to be sure that TTS exists and is okay to use */
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, textToSpeechFunctions.getReqTTSStatusCheck());
		
		/* Get a question */
		//dbAdapter.getRandomQuestion(MainFunctions.dificultyLevel, MainFunctions.tipoLevel, 2);
		dbAdapter.getRandomQuestion(MainFunctions.dificultyLevel, MainFunctions.tipoLevel, MainFunctions.tipolingua);
		
		/* TextView that will show the answer */
		answerTextView = (TextView) findViewById(R.id.txtQuestion);
		answerTextView.setText("");
		
		/* a counter */
		charIndexCounter = 0;
		
		/* Loads the Hangman */
		hangman = new HangmanClass( getActivity() );
		showHangmanBodyParts();
		
		Log.i(TAG, LogMessages.MSG_ON_CREATE_FINISHED);
	}
	
	void registerScoreRecord(){
		Log.i(TAG,"registerScoreRecord() entered ");
		if(MainFunctions.dificultyLevel == DifficultyLevel.FACIL.getValue()){
			int databaseScore = dbAdapter.getScore(DifficultyLevel.FACIL.getValue());
			Log.i(TAG,"databaseScore = " + databaseScore + " || Current Score = " + MainFunctions.score);
			if (MainFunctions.score > databaseScore){
				Log.i(TAG,"REGISTRANDO RECORD => " + MainFunctions.score);
				dbAdapter.updateScore(DifficultyLevel.FACIL.getValue(), MainFunctions.score);
			}
		}
		else{
			int databaseScore = dbAdapter.getScore(DifficultyLevel.DIFICIL.getValue());
			Log.i(TAG,"databaseScore = " + databaseScore + " || Current Score = " + MainFunctions.score);
			if (MainFunctions.score > databaseScore){
				Log.i(TAG,"REGISTRANDO RECORD => " + MainFunctions.score);
				dbAdapter.updateScore(DifficultyLevel.DIFICIL.getValue(), MainFunctions.score);
			}
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
			switch(resultCode){
				case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
					Log.i(TAG, LogMessages.MSG_TTS_UP_RUNNING);
					myTTS = new TextToSpeech(this, this);
					break;
				case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
					Log.i(TAG, LogMessages.MSG_TTS_MISSING_DATA);
					Intent installTTSIntent = new Intent();
		            installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
		            startActivity(installTTSIntent);
		            break;
				default:
					Log.i(TAG, LogMessages.MSG_TTS_NOT_AVAILABLE);
			}
	    }
		else if (requestCode == QUIT_CODE){
			
		}
		else if (requestCode == EXIT_CODE){
			if (MainFunctions.selectedOption == 1){
				hapticFunctions.vibrateOnExit();
				final Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				setResult(RESULT_OK, sendIntent);
				finish();
			}
			else{
				// reiniciar o jogo
				getActivity().recreate();
			}
		}
		// occurs after closing braille keyboard
		else if (requestCode == BRAILLE_KEYBOARD_RESPONSE){
			if(resultCode == 2){
				speakWords("QUIT_APPLICATION", "");
			}
			else if(resultCode == RESULT_OK){
				answerTextView.setText("");
				returnedText = data.getStringExtra("key").trim();
	           	Log.i("TEXTO RETORNADO DO TECLADO", returnedText);
	           	
	           	// if the char was found, congratulations
	           	if ( checkIfCharWasFound() == true){
	           		if (MainFunctions.dificultyLevel == DifficultyLevel.FACIL.getValue()){
	           			MainFunctions.score++;
	           			registerScoreRecord();
	           			speakWords("EXIT_APPLICATION", getString(R.string.speakCongrats));
	           		}
	           		else{
	           			if (checkIfUserHitsAllChars()){
	           				MainFunctions.score++;
		           			registerScoreRecord();
		           			speakWords("EXIT_APPLICATION", getString(R.string.speakCongrats));
	           			}
	           			else{
	           				
	           				//speakWords("NEW_TRY", "muito bem!");
	           				
	           				speakWords("EXIT_APPLICATION", getString(R.string.speakCongrats));
	           			}
	           		}
	           	}
	           	// if the char was not found...
	           	else{
	           		Log.i(TAG, LogMessages.MSG_LETTER_NOT_FOUND);
	            	letterInputErrors++;

	            	showHangmanBodyParts();

	            	// if the user gets MAXERRORS, game over
	            	if (letterInputErrors == MAXERRORS){
	            		Log.i(TAG, LogMessages.MSG_GAME_OVER);
	            		MainFunctions.score = 0;
	                	speakWords("EXIT_APPLICATION", getString(R.string.speakGameOver));
	                }
	            	// else, give him a new try
	                else{
	                	Log.i(TAG, LogMessages.MSG_INCORRECT_LETTER);
	                	answerTextView.setText("");
	            		Log.i(TAG, LogMessages.MSG_APPINFO_AFTER_KEYBOARD_RETURN);
	            		speakBodyPartDrawn();	
	                }
	           	}
	        }
		}
	    super.onActivityResult(requestCode, resultCode, data);
	}
	
	/** 
	 * onInit
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 == TextToSpeech.SUCCESS) {
			setTtsListener();
	        myTTS.setLanguage(Locale.getDefault());
	        // This appIntro() will be executed only once when the app starts
	        Log.i(TAG,"EXECUTANDO APPINTRO DO ONINIT");
	     	appIntro();
	    }
		else {
			myTTS = null;
			Log.i(TAG, LogMessages.MSG_FAILED_INITIALIZE_TTS);
			finish();
		}
		Log.i(LogMessages.MSG_ON_INIT);
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
		screenFunctions.toggleFullScreen(PlayGameActivity.this);
				
        super.onResume();
        Log.i(TAG, LogMessages.MSG_ON_RESUME_FINISHED);
    }
	
	/** 
	 * onPause 
	 *     called before the screen dissaper for the user
	 * @see android.app.Activity#onPause
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	@Override
    protected void onPause() {
		Mint.closeSession(getActivity());
        super.onPause();
        if (myTTS != null){
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myTTS != null){
        	myTTS.shutdown();
        }
        Mint.closeSession(getActivity());
        stopTimer();
        Log.i(TAG,LogMessages.MSG_ON_DESTROY_FINISHED);
		logFunctions.logTextFile(LogMessages.MSG_HANGMAN_WAS_FINISHED);
    }
    
    /**
	 * callBrailleKeyboard
	 *     Calls Braille Keyboard
	 * @author Antonio Rodrigo
	 * @param activity
	 * 			activity that calls the Keyboard
	 * @version 1.0 
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
	
	public void testGetOutOfApp() {
		try{
			System.out.println("chegou aqui!");
			wait(4000);
			System.out.println("4 segundos depois aqui!");
		}
		catch(Exception e){
			
		}
	}
    
    /**
	 * getActivity
	 *     retrieves the current activity 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return current activity
	 */
	protected Activity getActivity() {
		return PlayGameActivity.this;
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
	
	
	
	
	// ------------------------------------------------
	// SOUND FUNCTIONS
	// ------------------------------------------------
	
	/**
	 * playSound
	 *     Plays a mp3 sound
	 * @param nota
	 *     Sound File reference (ID)
	 * @param leftVolume
	 *     left volume between 0.0f and 1.0f 
	 * @param rightVolume
	 *     right volume between 0.0f and 1.0f
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void playSound(int nota, float leftVolume, float rightVolume){
		Log.i(TAG, LogMessages.MSG_PLAYING_SOUNDPOOL);
		if (loaded) {
			soundPool.play(nota, leftVolume, rightVolume, 0, 0, 1f);
		}
	}
	
	/**
	 * play1Tick
	 *     plays a tick sound when user touches 1x a button
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	void play1Tick(){
		playSound(tick1, 1.0f, 1.0f);
	}
	
	// ------------------------------------------------
	// TEXT TO SPEECH FUNCTIONS
	// ------------------------------------------------
	
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
		if (tts != null){
    		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    	}
    }
    
    /**
	 * speakWords
     *     Speaks the string with utterance completion - no queuing
     * @param utteranceID
     *     the identifier of the utterance.
     * @param text
     *     The string of text to be spoken.
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	public void speakWords(String utteranceID, String text) {
		final HashMap<String,String> params=new HashMap<String,String>();
	    params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,utteranceID);
	    if (myTTS != null){	    	
	    	try{
	    		myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, params);
			}
			catch (Exception e){
				Log.i(TAG, e.getMessage());
				logFunctions.logTextFile(e.getMessage());
			}
	    }
    }
	
	/** 
     * speakWordsWithDelay
	 *     Convert text to speech with delay if Talkback is disabled
	 * @author Antonio Rodrigo
	 * @version	1.0
	 */
    public void speakWordsWithDelay(TextToSpeech tts, String speech, int delay) {        
		if (tts != null){
			tts.playSilence(delay, TextToSpeech.QUEUE_FLUSH, null);
    		tts.speak(speech, TextToSpeech.QUEUE_ADD, null);
    	}
    }
    
    /** 
     * setTtsListener
	 *     Sets the listener that will be notified when synthesis of an utterance completes.
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void setTtsListener() { 
        if( Build.VERSION.SDK_INT  >= 15 ){
            myTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onDone(String utteranceId){
                	Log.i(TAG, "Text to speech finished");
                	if (utteranceId.equals("STARTING_APPLICATION")){
            			PlayGameActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {          	
                            	speakTip();
                            }
                        });
                	}
                	// spelling chars
                	else if (utteranceId.equals("SPELL_LETTERS")){
                		charIndexCounter = 0;
                		startGame();
                	}
                	// calls braille keyboard
                	else if (utteranceId.equals("INPUT_CHAR")){
                		// TESTAR COLOCAR UM TIMER AQUI ANTES DE CHAMAR O TECLADO GBRAILLE!!!!!!!!!!!-----------------------
                		
                		testGetOutOfApp();
                		
                		callBrailleKeyboard(getActivity());
                		
                	}
                	// restart the game when a char is wrong
                	else if (utteranceId.equals("NEW_TRY")){
                		appIntro();
                	}
                	// victory
                	else if (utteranceId.equals("EXIT_APPLICATION")){
                		dbAdapter.close();
        				Intent i = new Intent(getActivity(), ExitActivity.class);
        				startActivityForResult(i, EXIT_CODE);
					}
                	else if (utteranceId.equals("QUIT_APPLICATION")){
                		dbAdapter.close();
        				Intent i = new Intent(getActivity(), ExitActivity.class);
        				startActivityForResult(i, EXIT_CODE);
					}
                }
 
                @Override
                public void onError(String utteranceId){
                	Log.i(TAG, "Error in processing Text to speech");
                }
 
                @Override
                public void onStart(String utteranceId){
                	Log.i(TAG, "Started speaking");
                }
            });
        }
    }
	
	// ------------------------------------------------
	// GENERAL FUNCTIONS
	// ------------------------------------------------
       
    /**
	 * checkIfCharWasFound
	 *     check if the char was found
	 * @return true or false
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
    private boolean checkIfCharWasFound(){
		Log.i(TAG, LogMessages.MSG_CHECKING_ONE_LETTER_ANSWER);
		if (MainFunctions.dificultyLevel == DifficultyLevel.FACIL.getValue()){
			int index = MainFunctions.missingCharPos - 1;
			String mychar = Character.toString( MainFunctions.answer.charAt(index) );		
			if (mychar.equals(returnedText)){
				Log.i(TAG, LogMessages.MSG_LETTER_WAS_FOUND + mychar + " - " + LogMessages.MSG_INDEX + index);
				MainFunctions.answerCharList.set(index, mychar);	
				return true;
			}
			return false;
		}
		else{
			String mychar = "";
			encontrado = false;
			for(int i = 0; i < MainFunctions.selectedAnswerLength; i++){
				mychar = mychar + Character.toString(MainFunctions.answer.charAt(i));		
				if (mychar.equals(returnedText)){
					Log.i(TAG, LogMessages.MSG_LETTER_WAS_FOUND + mychar + " - " + LogMessages.MSG_INDEX + i);
					for(int j = 0; j < MainFunctions.selectedAnswerLength; j++){
						MainFunctions.answerCharList.set(j, Character.toString(MainFunctions.answer.charAt(j)));
					}
					encontrado = true;
				}
			}
			if (encontrado){
				return true;
			}
		}
		return false;
		//return true;
	}
    
    /**
	 * checkIfUserHitsAllChars
	 *     check if the user hits the missing char
	 * @return true or false
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
    private boolean checkIfUserHitsAllChars(){    	
    	if (MainFunctions.answerCharList.contains("_")){
    		return false;
    	}
    	return true;
    }
    
    /**
	 * generatedWordFromList
	 *     creates a word from a list
	 * @return word
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
    private String generatedWordFromList(){
    	String s="";
    	for (int i=0; i < MainFunctions.answerCharList.size(); i++){
    		s += MainFunctions.answerCharList.get(i);
    	}
    	return s;
    }
        
    /**
	 * appIntro
	 *     Plays a sound effect and speaks the screen name
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void appIntro(){
		Log.i(TAG, LogMessages.MSG_HANGMAN_INTRO_BEING_PLAYED);
		logFunctions.logTextFile(LogMessages.MSG_HANGMAN_INTRO_BEING_PLAYED);	
		if (textToSpeechFunctions.getIsAppNameSpoken() == false){
			speakWords("STARTING_APPLICATION", getString(R.string.speakAppName));
			textToSpeechFunctions.setIsAppNameSpoken(true);
			hapticFunctions.vibrate(1);
		}
		else{
			int missingAttempts = MAXERRORS - letterInputErrors;
			if (missingAttempts == 1){
				speakWords("STARTING_APPLICATION", getString(R.string.speakMissing) + " " + missingAttempts + " " + getString(R.string.speakAttempt) );
			}
			else{
				speakWords("STARTING_APPLICATION", getString(R.string.speakMissings) + " " + missingAttempts + " " + getString(R.string.speakAttempts) );
			}
		}
	}
}