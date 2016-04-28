package com.gbraille.keyboard;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.gbraille.libraries.*;
import com.splunk.mint.Mint;
import com.gbraille.keyboard.database.DbAdapter;
import com.gbraille.keyboard.FingerArea.enumFingerArea;
import com.gbraille.keyboard.ModeClass.Mode;
import com.gbraille.keyboard.R;

import de.akquinet.android.androlog.Log;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.speech.tts.TextToSpeech.OnInitListener;

/**
 * The main Activity class
 * @author Antonio Rodrigo
 */
public class MainActivity extends Activity implements OnInitListener {
	private String TAG = "MainActivity";
	
	private TextView txtViewDisplay;

	// Braille points
	private Braille braille = new Braille();
	private BrailleCell brailleCell = new BrailleCell();
		
	// Braille Cell current line (1,2 or 3)
	private int brailleCurrentLine;
	
	// Braille codes
	private BrailleCharsNumbers brailleToNumber = new BrailleCharsNumbers();
	private BrailleCharsLowercase brailleToLowercaseLetter = new BrailleCharsLowercase();
	private BrailleCharsUppercase brailleToUppercaseLetter = new BrailleCharsUppercase();
	
	// Braille images
	private BraillePointsCodes braillePointsCodes = new BraillePointsCodes();	 
	private BraillePointsImagesNumbers braillePointsAndNumbers = new BraillePointsImagesNumbers();
	private BraillePointsImagesLowercase braillePointsImagesLowercase = new BraillePointsImagesLowercase();
	private BraillePointsImagesUppercase braillePointsImagesUppercase = new BraillePointsImagesUppercase();
	
	// Braille display text
	private BrailleDisplay brailleDisplay = new BrailleDisplay();
	
	// Braille TTS
	private Narrator ttsPhonetics = new Narrator();
	
	// touch areas
	private FingerArea fingerArea = new FingerArea();
	
	// vibration functions
	private HapticClass hapticFunctions;
	
	// audio functions
	private AudioClass audioFunctions;
	
	// screen functions
	private ScreenClass screenFunctions = new ScreenClass();
	
	// Splunk Mint functions
	private SplunkMintClass splunkMintFunctions;
	private boolean isBugsenseEnabled;
	
	// log functions
	private LogClass logFunctions;
		
	private Map<String, Integer> mapSelecionado, mapNumber, mapUppercaseLetter, mapLowercaseLetter;
	private ImageView braillePointsImageView, brailleCharIconImageView;
		
	/* x and y coordinates */
	private Point screenCoordinates;
	
	/* accelerometer vars */
	private SensorManager mSensorManager = null;
	private ShakeEventListener mSensorListener;
	
	/* sensor vars*/
	private Sensor accelerometerSensor = null;
	private Sensor proximitySensor = null;
			
	/* time */
	private long currentTime;	
	private final int TOUCH_TIME_INTERVAL = 150;
	
	/* text-to-speech */
	/* obs: use Loquendo with Fernanda voice or SVOX with Luciana voice */
	private TextToSpeech myTTS;
	private int MY_DATA_CHECK_CODE = 0;
	
	/* volume keys */
	private boolean volumeDownKeyPressed, volumeUpKeyPressed;
	
	/* layouts */
	private TableLayout defaultLayout;
	private LinearLayout bottomLayout;
	
	/* multitouch fingers vars */
	private int pntr1_x1, pntr1_x2, pntr1_y1, pntr1_y2,
    pntr2_x1, pntr2_x2, pntr2_y1, pntr2_y2,
    pntr3_x1, pntr3_x2, pntr3_y1, pntr3_y2;
	private boolean finger1move, finger2move, finger3move;
	private boolean finger1up, finger2up, finger3up;	
	
	private SoundPool soundPool;
	private boolean loaded = false;
	private int notaDo,notaDoSustenido,notaRe,notaReSustenido,notaMi,notaFa,notaSi;
	
	private DbAdapter dbAdapter;
	
	/* spell vars */
	private Handler timerHandler = new Handler();
	int charIndexCounter;
	private int task1InitializationTime = 100;
	private int task1SubsequentTime = 800;
	
	private void playSound(int nota, float leftVolume, float rightVolume){
		Log.i(TAG,"PLAYING SOUNDPOOL");				
		if (loaded) {
			soundPool.play(nota, leftVolume, rightVolume, 0, 0, 1f);
		}
	}
		
	/**
	 * restartingInput
	 * using to correct inputs and after cursor movement
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void restartingInput(){
		brailleCurrentLine = 1;
		currentTime = System.currentTimeMillis();
		brailleCell.clearBrailleCell();
		fingerArea.clearAllFingerAreas();
		setBraillePointsImageViewDrawing();
		setBrailleCharIconImageViewDrawing();
	}
	
	void clearAction(){
		fingerArea.clearAllFingerAreas();
		currentTime = System.currentTimeMillis();
	}
	
	/** 
	 * deleteCharacter
	 *     Deletes a character
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	private void deleteCharacter(){
		if (System.currentTimeMillis() - currentTime >= brailleDisplay.getCharDeletedTimeInterval()){			
			if (brailleCell.isEmpty() == false){
				speakWords(getString(R.string.speakDeletingInputError));
				logFunctions.logTextFile(LogMessages.MSG_DELETING_INPUT_ERROR);
				restartingInput();
			}
			else{
				String deleted = brailleDisplay.deleteCharacter();
				logFunctions.logTextFile(LogMessages.MSG_CHARACTER_DELETED + deleted);				
				if (deleted != null){
					String ttsText = ttsPhonetics.getTTS(deleted);
					brailleDisplay.showTextAtTextView(txtViewDisplay);
					speakWords(ttsText + " " + getString(R.string.speakDeleted));					
				}
				else{
					speakWords(getString(R.string.speakNothingToDelete));
				}
				restartingInput();
			}
		}
	}	
	
	/** 
	 * Called when the activity is first created.
	 * @see android.app.Activity#onCreate(android.os.Bundle) 
	 **/
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/* a counter */
		charIndexCounter = 0;
		
		splunkMintFunctions = new SplunkMintClass(this);
		isBugsenseEnabled = true;
		
		hapticFunctions = new HapticClass(this);
		hapticFunctions.setHapticLauncher();
		
		/* toggle to fullscreen */
		screenFunctions.toggleFullScreen(MainActivity.this);
		getScreenResolution();
		
		/* the app will be always on screen */
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);		
		
		setContentView(R.layout.activity_main);
		
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
		    public void onLoadComplete(SoundPool sound, int sampleId,int status) {
		       loaded = true;		       
		    }		    
		});
		
		notaDo = soundPool.load(this, R.raw.notado, 1);
		notaDoSustenido = soundPool.load(this, R.raw.notadosustenido, 1);
		notaRe = soundPool.load(this, R.raw.notare, 1);
		notaReSustenido = soundPool.load(this, R.raw.notaresustenido, 1);
		notaMi = soundPool.load(this, R.raw.notami, 1);
		notaFa = soundPool.load(this, R.raw.notafa, 1);
		notaSi = soundPool.load(this, R.raw.notasi, 1);
		
		/* Starts the Bugsense */
		if (isBugsenseEnabled == true){
			Mint.initAndStartSession(MainActivity.this, splunkMintFunctions.getBugsenseKey());
		}
		
		/* Disables log if the logActivated var is false */
		logFunctions = new LogClass(true, "kb");
		if (logFunctions.getLogActivated() == false){
			Log.deactivateLogging();
		}		
		
		/* screen areas touched by fingers */
		fingerArea.clearAllFingerAreas();
		
		/* Braille line */
		brailleCurrentLine = 1;
		
		/* volume keys */
		volumeDownKeyPressed = volumeUpKeyPressed = false;
		
		/* Braille symbols to image map */
		mapNumber = braillePointsAndNumbers.getMap();
		mapLowercaseLetter = braillePointsImagesLowercase.getMap();
		mapUppercaseLetter = braillePointsImagesUppercase.getMap();
		
		/* Braille flags */
		braille.setFlagCapital(0);
		braille.setFlagNumber(0);
		braille.setFlagTranslineacao(0);
		
		/* App layouts */
		defaultLayout = (TableLayout)findViewById(R.id.RelativeLayout1);
		bottomLayout = (LinearLayout)findViewById(R.id.square3);
		
		// default layout touch action
		defaultLayout.setOnTouchListener(
			new TableLayout.OnTouchListener() {
				public boolean onTouch(View v, MotionEvent m) {
					handleTouch(m);     				
		        	return true;
		        }
		    }
		);
		
		/* touch fingers vars */
		finger1move = finger2move = finger3move = false;
		finger1up = finger2up = finger3up = false;
		
		// enables default layout
		enableKeyboard(false);
		
		// Text-to-speech
		Intent checkTTSIntent = new Intent();
		checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
		
		/* SensorManager initialization */
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		
		/* ShakeDetector initialization */
		accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		/* proximity sensor initialization */
		proximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
				
		mSensorListener = new ShakeEventListener();
		
		// enables shake to read, spell or explain the inserted text
		mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
		    public void onShake(){
		    	if (! myTTS.isSpeaking()){
		    		Log.i(TAG,LogMessages.MSG_SHAKE);
		    		logFunctions.logTextFile(LogMessages.MSG_SHAKE);		    	
		    		
		    		if (MainFunctions.keyboardMode == Mode.LER.getValue()){
		    			speakWords(txtViewDisplay.getText()+"");
			    		logFunctions.logTextFile(LogMessages.MSG_TEXT_READ + txtViewDisplay.getText());
			    		clearAction();	
		    		}
		    		else if (MainFunctions.keyboardMode == Mode.SOLETRAR.getValue()){
		    			String ttsText = brailleDisplay.getText();		    			
		    			speakWords("SPELL_LETTERS", ttsText);
		    			clearAction();
		    		}
		    		else if (MainFunctions.keyboardMode == Mode.DEFINIR.getValue()){
		    			String ttsText = brailleDisplay.getText();
		    			try{
		    				String significado = dbAdapter.getSignificadoDicionario(ttsText);
		    				speakWords( "definição da palavra: " + ttsText + " : " + significado );
		    			}
		    			catch (Exception e){
		    				speakWords( "não foi encontrada a definição da palavra: " + ttsText);
		    			}
		    			finally{
		    				clearAction();
		    			}
		    		}
		    	}
			}
		});
		
		/* text display at top of application */
		txtViewDisplay = new TextView(this);
		txtViewDisplay.setVisibility(View.VISIBLE);
		txtViewDisplay.setText("");
		txtViewDisplay.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
		txtViewDisplay.setBackgroundDrawable(getResources().getDrawable(R.drawable.customborder));
		txtViewDisplay.setPadding(10, 0, 2, 0);
		defaultLayout.addView(txtViewDisplay,0);
		
		/* Braille clicked points image */
		braillePointsImageView = new ImageView(this);
		braillePointsImageView.setImageResource(getBrailleDrawing());
		bottomLayout.addView(braillePointsImageView);
		
		/* Braille char icon image */
		brailleCharIconImageView = new ImageView(this);
		brailleCharIconImageView.setImageResource(getBrailleDrawing());
		bottomLayout.addView(brailleCharIconImageView);
		
		/* if the phone is on vibrate or silent mode, change to normal */
		audioFunctions = new AudioClass(this);	
		audioFunctions.setAudioStateToNormal();
		
		// get the current time in milisseconds
		currentTime = System.currentTimeMillis();
		
		dbAdapter = new DbAdapter(this.getApplicationContext());
		
		/* instalar o dicionario */
		startDictionayInstallation();
		
		Log.i(TAG, "onCreate() finished");
	}
	
	public void startDictionayInstallation() {
		// do something long
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (dbAdapter.getInstalacao().equalsIgnoreCase("N")){
					dbAdapter.instalarDicionario();
				}
			}			
		};
		new Thread(runnable).start();
	}
	
	@SuppressLint("NewApi")
	
	/** 
	 * getScreenResolution
	 *     get the screen height and width
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void getScreenResolution(){
	    Display display = getWindowManager().getDefaultDisplay();	 
	    screenCoordinates = new Point();	 
	    display.getSize(screenCoordinates);
	}
	
	/** 
	 * getFingerDownArea
	 *     Down finger Area (1 or 2)
	 * @param x
	 *     x-coordinate from screen
	 * @param finger
	 *     finger (1, 2 or 3)
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	void getFingerDownArea(int x, int finger){
		fingerArea.setFingerDownArea(finger, x < (screenCoordinates.x / 2)  ? 1 : 2);		
	}
	
	/**
	 * getFingerUpArea
	 *     Up finger area (1 or 2)
	 * @param x
	 *     x-coordinate from screen
	 * @param finger
	 *     finger (1, 2 or 3)
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	void getFingerUpArea(int x, int finger){
		fingerArea.setFingerUpArea(finger, x < (screenCoordinates.x / 2)  ? 1 : 2);		
	}	
	
	// -------------------------------------------------------------------
	// BRAILLE IMAGE VIEWS DRAWING
	// -------------------------------------------------------------------
	
	public void setBraillePointsImageViewDrawing(){
		braillePointsImageView.setImageResource(getBrailleDrawing());
	}

	public void setBrailleCharIconImageViewDrawing(){
		brailleCharIconImageView.setImageResource(getDesenhoBrailleComLetra());
	}
	
	// mostra os pontos digitados na caixinha na parte inferior da tela
	public int getBrailleDrawing(){
		String sequence = getBrailleSequence();	
		return braillePointsCodes.getDrawing(sequence);
	}
	
	public int getDesenhoBrailleComLetra(){
		String sequence = getBrailleSequence();
		
		mapSelecionado = new HashMap<String, Integer>();
		mapSelecionado.clear();	
		
		// flag number on
		if (braille.getFlagNumber() == 1 && braille.getFlagCapital() == 0){			
			mapSelecionado = mapNumber;
		}
		// flag uppercase on
		else if (braille.getFlagNumber() == 0 && braille.getFlagCapital() >= 1){
			mapSelecionado = mapUppercaseLetter;
		}
		// without flags
		else if (braille.getFlagNumber() == 0 && braille.getFlagCapital() == 0){			
			mapSelecionado = mapLowercaseLetter;
		}				
		Log.i(TAG,LogMessages.MSG_BRAILLE_SEQUENCE + sequence + " | " +LogMessages.MSG_RETURNING + mapSelecionado.get(sequence));
		if (mapSelecionado.get(sequence) == null){
			return mapSelecionado.get("999999");
		}
		return mapSelecionado.get(sequence);		
	}	
	
	// -------------------------------------------------------------------
	// BRAILLE FUNCTIONS
	// -------------------------------------------------------------------	
	public String getBrailleCode(){
		String sequence = getBrailleSequence();
		
		BrailleHashMap map = new BrailleHashMap();
		map.clear();
		
		// symbols from flag "Number"
		if (braille.getFlagNumber() == 1 && braille.getFlagCapital() == 0){
			map = brailleToNumber.getMap();
		}		
		// symbols from flag "Uppercase"
		else if (braille.getFlagNumber() == 0 && braille.getFlagCapital() >= 1){
			map = brailleToUppercaseLetter.getMap();			
		}
		// symbols without flags (lowercase)
		else if (braille.getFlagNumber() == 0 && braille.getFlagCapital() == 0){
			map = brailleToLowercaseLetter.getMap();		
		}

		Log.i(TAG,LogMessages.MSG_BRAILLE_SEQUENCE + sequence + " | " +LogMessages.MSG_RETURNING + map.get(sequence));
		if (map.get(sequence) == null){
			return "";
		}
		return (String) map.get(sequence);
	}
	
	String getBrailleSequence(){
		String mapping = "";
		for (int i=0; i < 6; i++){
			mapping += brailleCell.getBrailleCell(i);
		}		
		return mapping;
	}
	
	void checkBrailleChar(){
		String character = "";
		if (brailleCurrentLine > 3){
			character = getBrailleCode();
			
			if (character == ""){
				Log.i(TAG, LogMessages.MSG_CHAR_NOT_FOUND);
				logFunctions.logTextFile(LogMessages.MSG_CHAR_NOT_FOUND);
				speakWords(getString(R.string.speakSymbolNotFound));
				brailleCurrentLine = 1;
				currentTime = System.currentTimeMillis();
				brailleCell.clearBrailleCell();
				fingerArea.clearAllFingerAreas();
			}
			else{
				Log.i(TAG, LogMessages.MSG_CHARACTER_WRITTEN + character);
				logFunctions.logTextFile(LogMessages.MSG_CHARACTER_WRITTEN + getBrailleCode());
    			String ttsText = ttsPhonetics.getTTS(getBrailleCode());			
			
    			if (character == "ESPACO"){
    				braille.setFlagNumber(0);
    				braille.setFlagCapital(0);
    				braille.setFlagTranslineacao(0);
    				character = " ";
    				brailleDisplay.insertCharacter(character);
    				brailleDisplay.showTextAtTextView(txtViewDisplay);
    			}
    			else if (character == "RESTITUIDOR"){
    				braille.setFlagNumber(0);
    				braille.setFlagCapital(0);
    				braille.setFlagTranslineacao(0);
    				character = "";
    				brailleDisplay.insertCharacter(character);
    				brailleDisplay.showTextAtTextView(txtViewDisplay);
    			}
    			else if (getBrailleCode() == "TRANSLINEACAO"){
    				braille.setFlagNumber(0);
    				braille.setFlagCapital(0);
    				braille.setFlagTranslineacao(1);
    			}
    			else if (getBrailleCode() == "NUMERO"){
    				braille.setFlagNumber(1);
    				braille.setFlagCapital(0);
    				braille.setFlagTranslineacao(0);
    			}
    			else if (getBrailleCode() == "MAIUSCULA"){
    				braille.setFlagNumber(0);
    				braille.setFlagTranslineacao(0);
    				if (braille.getFlagCapital() == 0){
    					braille.setFlagCapital(1);
    					ttsText = ttsPhonetics.getTTS("MAIUSCULA1");
    					brailleCharIconImageView.setImageResource(R.drawable.s_000101);
    				}
    				else if (braille.getFlagCapital() == 1){
    					braille.setFlagCapital(2);
    					ttsText = ttsPhonetics.getTTS("MAIUSCULA2");
    					brailleCharIconImageView.setImageResource(R.drawable.s_000101_2);
    				}
    			}
    			else{
    				if (braille.getFlagCapital() == 1){
    					braille.setFlagCapital(0);					
    				}
    				brailleDisplay.insertCharacter(character);
    				brailleDisplay.showTextAtTextView(txtViewDisplay);
    			}			
    			speakWords(ttsText);
				brailleCurrentLine = 1;
				currentTime = System.currentTimeMillis();
				brailleCell.clearBrailleCell();
				fingerArea.clearAllFingerAreas();
			}
		}
	}
		
    // -------------------------------------------------------------------
 	// TONES FUNCTIONS
 	// -------------------------------------------------------------------
	/**
	 * playTone
	 *     what is happen when user tap the layout.
	 *     ex: playTone(ToneGenerator.TONE_PROP_BEEP, 100);
	 * @see #ToneGenerator(int, int)
	 * @param toneType
	 *     The type of tone generate
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	private void playTone(int toneType, int waitTime){
		final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
	    tg.startTone(toneType);
	    // its important to release the resource or it will be crash 
	    synchronized(tg) {
	    	try{
	    		tg.wait(waitTime);
	    		tg.release();
	    	}
	    	catch(Exception e){}
	    }
	}
	
	/**
	 * playDot1Sound
	 *     plays the "Do" music note
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playDot1Sound(){
		playSound(notaMi, 0, 1.0f);
	}
	
	/**
	 * playDot4Sound
	 *     plays the "Fa" music note
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playDot4Sound(){
		playSound(notaFa, 1.0f, 0);
	}
	
	/**
	 * playDot14Sound
	 *     plays the "Do" and "Fa" music notes at same time
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playDot14Sound(){	
		playDot1Sound();
		playDot4Sound();
	}
	
	/**
	 * playDot2Sound
	 *     plays the "Re" music note
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playDot2Sound(){
		playSound(notaRe, 0, 1.0f);
	}
	
	/**
	 * playDot5Sound
	 *     plays the "Re sustenido" music note
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playDot5Sound(){
		playSound(notaReSustenido, 1.0f, 0);
	}
	
	/**
	 * playDot25Sound
	 *     plays the "Re" and "Re sustenido" music notes at same time
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playDot25Sound(){
		playDot2Sound();
		playDot5Sound();
	}
	
	/**
	 * playDot3Sound
	 *     plays the "Do" music note
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playDot3Sound(){
		playSound(notaDo, 0, 1.0f);
	}
	
	/**
	 * playDot6Sound
	 *     plays the "Do Sustenido" music note
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playDot6Sound(){
		playSound(notaDoSustenido, 1.0f, 0);
	}
	
	/**
	 * playDot36Sound
	 *     plays the "Do" and "Do sustenido" music notes at same time
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playDot36Sound(){
		playDot3Sound();
		playDot6Sound();
	}
	
	/**
	 * playNothingSound
	 *     plays the "Si" music note
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void playNothingSound(){
		playSound(notaSi, 1.0f, 1.0f);
	}
	
	void playSoundOneFingerLeftArea(){
		switch(brailleCurrentLine){
			case 1:
				playDot1Sound();
				break;
			case 2:
				playDot2Sound();
				break;
			case 3:
				playDot3Sound();
				break;
			default:
				playDot1Sound();
		}		
	}
	
	void playSoundOneFingerRightArea(){
		switch(brailleCurrentLine){
			case 1:
				playDot4Sound();
				break;
			case 2:
				playDot5Sound();
				break;
			case 3:
				playDot6Sound();
				break;
			default:
				playDot4Sound();
		}
	}
	
	void playSoundTwoFingers(){
		switch(brailleCurrentLine){
			case 1:
				playDot14Sound();
				break;
			case 2:
				playDot25Sound();
				break;
			case 3:
				playDot36Sound();
				break;
			default:
				playDot14Sound();
		}
	}
	
	void playSoundSwipeLeftToRight(){
		playNothingSound();
	}
	
	// -------------------------------------------------------------------
	// TOUCH FUNCTIONS
	// -------------------------------------------------------------------

	/**
	 * handleTouch
	 *     what is happen when user tap the layout.
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
    void handleTouch(MotionEvent m){
    	for (int i = 0 ; i < m.getPointerCount() ; i++){
        	Log.i("contador = "+i);
            int x = (int) m.getX(i);
            int y = (int) m.getY(i);
            int id = m.getPointerId(i);
            
            switch (id){
            case 0:
                switch (m.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        pntr1_x1=x;
                        pntr1_y1=y;
                        getFingerDownArea(x,1);
                        finger1up = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        pntr1_x2=x;
                        pntr1_y2=y;
                        finger1up = false;
                        break;
                    case MotionEvent.ACTION_UP:
                    	Log.i(TAG, LogMessages.MSG_ACTION_UP);
                    	getFingerUpArea(x,1);
                    	finger1up = true;
                        break;
                }
                
                if (Math.abs(pntr1_x2 - pntr1_x1) > 25 || Math.abs(pntr1_y2 - pntr1_y1) > 25){
                    finger1move = true;
                }
                else{
                	finger1move = false;
                }
                break;
            case 1:
                switch (m.getActionMasked()){
                    case MotionEvent.ACTION_POINTER_DOWN:
                        pntr2_x1=x;
                        pntr2_y1=y;
                        finger2up = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        pntr2_x2=x;
                        pntr2_y2=y;
                        finger2up = false;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                    	Log.i(TAG, LogMessages.MSG_ACTION_POINTER_UP);
                    	finger2up = true;
                    	break;
                }
                if (Math.abs(pntr2_x2 - pntr2_x1) > 25 || Math.abs(pntr2_y2 - pntr2_y1) > 25){
                    finger2move = true;
                }
                else{
                	finger2move = false;
                }
                break;
            case 2:
                switch (m.getActionMasked()){
                    case MotionEvent.ACTION_POINTER_DOWN:
                        pntr3_x1=x;
                        pntr3_y1=y;
                        finger3up = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        pntr3_x2=x;
                        pntr3_y2=y;
                        finger3up = false;
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                    	Log.i(TAG, LogMessages.MSG_ACTION_POINTER_UP);
                    	finger3up = true;
                    	break;
                }
                if (Math.abs(pntr3_x2 - pntr3_x1) > 25 || Math.abs(pntr3_y2 - pntr3_y1) > 25){
                    finger3move = true;
                }
                else{
                	finger3move = false;
                }
                break;
            }            
        }
    	
    	Log.i(TAG,"move1 = " + finger1move + " move2 = " + finger2move + " move3 = " + finger3move);
    	Log.i(TAG,"finger1up = " + finger1up + " finger2up = " + finger2up + " finger3up = " + finger3up);
        
    	if (finger1up == true && finger2up == false && finger3up == false){
    		Log.i(TAG,"UM DEDO");                        	
            setBrailleOneFinger();
            finger1up = finger2up = finger3up = false;
    	}
    	else if (finger1up == false && finger2up == true && finger3up == false){
    		Log.i(TAG,"DOIS DEDOS");
        	setBrailleTwoFingers();
        	finger1up = finger2up = finger3up = false;
    	}
    	else if (finger1up == false && finger2up == true && finger3up == true){
    		Log.i(TAG,"TRES DEDOS");
        	setBrailleThreeFingers();
        	finger1up = finger2up = finger3up = false;
    	}    	
    }
	
    /**
	 * setBrailleOneFinger
	 *     called when one finger is touched on screen
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */	
	private void setBrailleOneFinger(){
		if (System.currentTimeMillis() - currentTime >= TOUCH_TIME_INTERVAL){
			// the user touched at left screen area
			if ( fingerArea.getFingerDownArea(1) == enumFingerArea.LEFT.getCode() && fingerArea.getFingerUpArea(1) == enumFingerArea.LEFT.getCode() ){				
				touchedLeftAreaWithOneFinger();
				checkBrailleChar();
				clearAction();
			}
			// the user touched at right screen area
			else if ( fingerArea.getFingerDownArea(1) == enumFingerArea.RIGHT.getCode() && fingerArea.getFingerUpArea(1) == enumFingerArea.RIGHT.getCode() ){				
				touchedRightAreaWithOneFinger();
				checkBrailleChar();
				clearAction();
			}
			// the user swipes left to right
			else if (fingerArea.getFingerDownArea(1) == enumFingerArea.LEFT.getCode() && fingerArea.getFingerUpArea(1) == enumFingerArea.RIGHT.getCode()){				
				swipeLeftToRightWithOneFinger();
				checkBrailleChar();				
				clearAction();
			}
			// the user swipes right to left
			else if (fingerArea.getFingerDownArea(1) == enumFingerArea.RIGHT.getCode() && fingerArea.getFingerUpArea(1) == enumFingerArea.LEFT.getCode()){
				swipeRightToLeftWithOneFinger();					
			}
		}
		finger1move = finger2move = finger3move = false;
	}
	
	/**
	 * touchedLeftAreaWithOneFinger
	 *     called when one finger is touched on the left area on screen
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void touchedLeftAreaWithOneFinger(){
		Log.i(TAG, LogMessages.MSG_CURRENT_LINE + brailleCurrentLine);
		Log.i(TAG, LogMessages.MSG_FINGER_1_AREA + fingerArea.getFingerDownArea(1));
		Log.i(TAG, LogMessages.MSG_AREA1_TOUCHED);
		logFunctions.logTextFile("1" + LogMessages.MSG_ONE_FINGER_LEFT_AREA_ON_LINE + brailleCurrentLine);
		
		playSoundOneFingerLeftArea();
		
		if (brailleCurrentLine == 1){
			brailleCell.setBrailleCell(0, 1);
			brailleCell.setBrailleCell(3, 0);
			brailleCurrentLine++;
			fingerArea.clearFingerAreasDown();
			setBraillePointsImageViewDrawing();
		}
		else if (brailleCurrentLine == 2){
			brailleCell.setBrailleCell(1, 1);
			brailleCell.setBrailleCell(4, 0);
			brailleCurrentLine++;
			fingerArea.clearFingerAreasDown();
			setBraillePointsImageViewDrawing();
		}
		else if (brailleCurrentLine == 3){
			brailleCell.setBrailleCell(2, 1);
			brailleCell.setBrailleCell(5, 0);
			brailleCurrentLine++;
			fingerArea.clearFingerAreasDown();
			setBraillePointsImageViewDrawing();
			setBrailleCharIconImageViewDrawing();
		}
	}
	
	/**
	 * touchedRightAreaWithOneFinger
	 *     called when one finger is touched on the right area on screen
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void touchedRightAreaWithOneFinger(){
		Log.i(TAG, LogMessages.MSG_CURRENT_LINE + brailleCurrentLine);
		Log.i(TAG, LogMessages.MSG_FINGER_1_AREA + fingerArea.getFingerDownArea(1));
		Log.i(TAG, LogMessages.MSG_FINGER_2_AREA);
		logFunctions.logTextFile("1" + LogMessages.MSG_ONE_FINGER_RIGHT_AREA_ON_LINE + brailleCurrentLine);
		
		playSoundOneFingerRightArea();
		
		if (brailleCurrentLine == 1){
			brailleCell.setBrailleCell(0, 0);
			brailleCell.setBrailleCell(3, 1);
			brailleCurrentLine++;
			fingerArea.clearFingerAreasDown();
			setBraillePointsImageViewDrawing();
		}
		else if (brailleCurrentLine == 2){
			brailleCell.setBrailleCell(1, 0);
			brailleCell.setBrailleCell(4, 1);
			brailleCurrentLine++;
			fingerArea.clearFingerAreasDown();
			setBraillePointsImageViewDrawing();
		}
		else if (brailleCurrentLine == 3){
			brailleCell.setBrailleCell(2, 0);
			brailleCell.setBrailleCell(5, 1);
			brailleCurrentLine++;
			fingerArea.clearFingerAreasDown();
			setBraillePointsImageViewDrawing();
			setBrailleCharIconImageViewDrawing();
		}
	}
	
	/**
	 * swipeLeftToRightWithOneFinger
	 *     called when the user swipes the screen - from left to right
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void swipeLeftToRightWithOneFinger(){
		Log.i(TAG, LogMessages.MSG_CURRENT_LINE + brailleCurrentLine);
		Log.i(TAG, LogMessages.MSG_FINGER_1_AREA + fingerArea.getFingerDownArea(1));
		Log.i(TAG, LogMessages.MSG_SWIPE_FROM_LEFT_TO_RIGHT);
		
		playSoundSwipeLeftToRight();
		
		if (fingerArea.getFingerDownArea(1) == 0 || fingerArea.getFingerDownArea(2) != 0 || fingerArea.getFingerDownArea(3) != 0){
			Log.i(TAG, LogMessages.MSG_INVALID_MOVEMENT);
			logFunctions.logTextFile(LogMessages.MSG_INVALID_MOVEMENT);
		}
		else {
			if ( fingerArea.getFingerDownArea(1) == enumFingerArea.LEFT.getCode() ){				
				logFunctions.logTextFile("1" + LogMessages.MSG_SWIPE_LEFT_TO_RIGHT_ON_LINE + brailleCurrentLine);
				
				if (brailleCurrentLine == 1){
					brailleCell.setBrailleCell(0, 0);
					brailleCell.setBrailleCell(3, 0);
					brailleCurrentLine++;					
					fingerArea.clearFingerAreasDown();
					setBraillePointsImageViewDrawing();
				}
				else if (brailleCurrentLine == 2){
					brailleCell.setBrailleCell(1, 0);
					brailleCell.setBrailleCell(4, 0);
					brailleCurrentLine++;
					fingerArea.clearFingerAreasDown();
					setBraillePointsImageViewDrawing();
				}
				else if (brailleCurrentLine == 3){
					brailleCell.setBrailleCell(2, 0);
					brailleCell.setBrailleCell(5, 0);
					brailleCurrentLine++;
					fingerArea.clearFingerAreasDown();
					setBraillePointsImageViewDrawing();
					setBrailleCharIconImageViewDrawing();
				}
			}
		}
	}	
	
	/**
	 * swipeRightToLeftWithOneFinger
	 *     called when the user swipes the screen - from right to left
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void swipeRightToLeftWithOneFinger(){
		Log.i(TAG, LogMessages.MSG_SWIPE_FROM_RIGHT_TO_LEFT);
		deleteCharacter();
	}
	
	/**
	 * setBrailleTwoFingers
	 *     called when two fingers touches on screen
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	private void setBrailleTwoFingers(){
		if (System.currentTimeMillis() - currentTime >= TOUCH_TIME_INTERVAL){
			touchedWithTwoFingers();
			checkBrailleChar();
			clearAction();			
		}
		finger1move = finger2move = finger3move = false;
	}
	
	/**
	 * touchedWithTwoFingers
	 *     called when two fingers touches on screen
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	void touchedWithTwoFingers(){
		Log.i(TAG, LogMessages.MSG_CURRENT_LINE + brailleCurrentLine);
		Log.i(TAG, "2" + LogMessages.MSG_TWO_FINGERS_ON_LINE + brailleCurrentLine);
		logFunctions.logTextFile("2" + LogMessages.MSG_TWO_FINGERS_ON_LINE + brailleCurrentLine);
		
		playSoundTwoFingers();
		
		if (brailleCurrentLine == 1){
			brailleCell.setBrailleCell(0, 1);
			brailleCell.setBrailleCell(3, 1);
			brailleCurrentLine++;
			fingerArea.clearFingerAreasDown();
			setBraillePointsImageViewDrawing();
		}
		else if (brailleCurrentLine == 2){
			brailleCell.setBrailleCell(1, 1);
			brailleCell.setBrailleCell(4, 1);				
			brailleCurrentLine++;
			fingerArea.clearFingerAreasDown();
			setBraillePointsImageViewDrawing();
		}
		else if (brailleCurrentLine == 3){
			brailleCell.setBrailleCell(2, 1);
			brailleCell.setBrailleCell(5, 1);
			brailleCurrentLine++;
			fingerArea.clearFingerAreasDown();
			setBraillePointsImageViewDrawing();
			setBrailleCharIconImageViewDrawing();
		}
	}
	
	/**
	 * setBrailleThreeFingers
	 *     called when three fingers touches on screen
	 * @author Antonio Rodrigo 
	 * @version 1.0
	 */
	private void setBrailleThreeFingers(){
		if (System.currentTimeMillis() - currentTime >= TOUCH_TIME_INTERVAL){
			exitApplication();
			finger1move = finger2move = finger3move = false;
		}
	}
	
	// -------------------------------------------------------------------
	// TEXT TO SPEECH FUNCTIONS
	// -------------------------------------------------------------------
	
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
                	if (utteranceId.equals("EXIT_APPLICATION")){
			        	hapticFunctions.vibrateOnExit();
			        	final Intent sendIntent = new Intent();
			        	sendIntent.setAction(Intent.ACTION_SEND);
			        	sendIntent.putExtra("key", brailleDisplay.getText());
			        	setResult(RESULT_OK, sendIntent);
			        	shutdownServices();
			        	finish();
					}
                	else if (utteranceId.equals("STARTING_APPLICATION")){
            			MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	// vibrates and enables keyboard when application starts
                            	hapticFunctions.vibrate(1);
                            	enableKeyboard(true);
                            }
                        });
                	}
                	else if (utteranceId.equals("SPELL_LETTERS")){
                		charIndexCounter = 0;
                		startSpell();
                	}
                }
 
                @Override
                public void onError(String utteranceId){
                	ttsNotFoundError();
                }
 
                @Override
                public void onStart(String utteranceId){
                	Log.i(TAG, "Started speaking");
                }
            });
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
	public void speakWords(String text) {
		if (myTTS != null){
			try{
				myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			}
			catch (Exception e){
				ttsNotFoundError();
			}
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
    public void speakWordsWithDelay(String speech, int delay) {        
    	if (myTTS != null){
			try{
				myTTS.playSilence(delay, TextToSpeech.QUEUE_FLUSH, null);
				myTTS.speak(speech, TextToSpeech.QUEUE_ADD, null);
			}
			catch (Exception e){
				ttsNotFoundError();
			}
    	}
    }
	
	// -------------------------------------------------------------------
	// VOLUME KEYS AND CURSOR MOVEMENT FUNCTIONS
	// -------------------------------------------------------------------
	
	/**
	 * dispatchKeyEvent
     *     moves the cursor with volume key
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
        			cursorRight();
        		}
        		else if (action == KeyEvent.ACTION_UP) {        			
        			volumeUpKeyPressed = false;
        		}        		
        		return true;
        	case KeyEvent.KEYCODE_VOLUME_DOWN:
        		if (action == KeyEvent.ACTION_DOWN) {     			
        			cursorLeft();
        		}
        		else if (action == KeyEvent.ACTION_UP) {
        			volumeDownKeyPressed = false;
        		}
        		return true;
        	default:
        		return super.dispatchKeyEvent(event);
        }
    }
    
    /** 
	 * cursorLeft
	 *     Left cursor movement
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	private void cursorLeft(){
		if (volumeDownKeyPressed == false){			
			volumeDownKeyPressed = true;
			int action = brailleDisplay.moveCursorToLeft();
			if (action == 1){
				// speak the character after the cursor (Google Talkback's default pattern)
				String ttsText = ttsPhonetics.getTTS( brailleDisplay.getCharacterAtCursorPosition() );
				speakWords( ttsText );
				logFunctions.logTextFile(LogMessages.MSG_CURSOR_TO_LEFT + " | speaking: " + brailleDisplay.getCharacterAtCursorPosition());
			}
			else{
				speakWords(getString(R.string.speakBeginningOfTheText));
				logFunctions.logTextFile(LogMessages.MSG_CURSOR_AT_BEGINNING);
			}
			restartingInput();
		}
	}
	
	/** 
	 * cursorRight
	 *     Right cursor movement
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	private void cursorRight(){
		if (volumeUpKeyPressed == false){			
			volumeUpKeyPressed = true;
			int action = brailleDisplay.moveCursorToRight();
			if (action == 2){
				speakWords(getString(R.string.speakEndOfTheText));
				logFunctions.logTextFile(LogMessages.MSG_CURSOR_AT_END);
			}
			else if (action == 1){
				// speak the character after the cursor (Google Talkback's default pattern)
				String ttsText = ttsPhonetics.getTTS( brailleDisplay.getCharacterAtCursorPosition() );
				speakWords( ttsText );				
				logFunctions.logTextFile(LogMessages.MSG_CURSOR_TO_RIGHT + " | speaking: " + brailleDisplay.getCharacterAtCursorPosition());
			}
			else{
				speakWords(getString(R.string.speakEndOfTheText));
				logFunctions.logTextFile(LogMessages.MSG_CURSOR_AT_END);
			}
			restartingInput();
		}
	}
    
	/**
	 * enableKeyboard
	 *     enable or disable the keyboard
	 * @param value
	 *     if {@code true} Enables the keyboard; if {@code false} disables the keyboard
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	protected void enableKeyboard(boolean value){
		defaultLayout.setEnabled(value);
	}
	
	// -------------------------------------------------------------------
	// EXIT AND SHUTDOWN FUNCTIONS
	// -------------------------------------------------------------------
	
	/**
	 * shutdownServices
	 *     finishes the tts and sensors services
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	private void shutdownServices(){
		Log.i(TAG, LogMessages.MSG_CLOSING_SERVICES);
		
		/* tts shutdown */
        if (myTTS != null){
        	myTTS.shutdown();
        }
        
        /* shake unregistration */
    	mSensorManager.unregisterListener(mSensorListener);
	}
	
	/**
	 * exitApplication
	 *     finishes the application
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	public void exitApplication(){
		logFunctions.logTextFile(LogMessages.MSG_KEYBOARD_EXITING);
		enableKeyboard(false);
		speakWords("EXIT_APPLICATION", "Saindo do teclado");		    	
	}
	
	// -------------------------------------------------------------------
	// ANDROID ACTIVITY FUNCTIONS
	// -------------------------------------------------------------------
	
	/** 
	 * onInit
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	@Override
	public void onInit(int arg0) {
		/* tts initialization */
		if (arg0 == TextToSpeech.SUCCESS) {
			setTtsListener();
			myTTS.setLanguage(Locale.getDefault());
			speakWords("STARTING_APPLICATION", getString(R.string.speakGBrailleKeyboard));
		}
		else {
			myTTS = null;			
			ttsNotFoundError();
		}
		Log.i(TAG, "onActivityResult() finished");
	}
	
	/**
	 * onActivityResult
	 * @see #onActivityResult(int, int, android.content.Intent) 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == MY_DATA_CHECK_CODE) {
	        if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {      
	            myTTS = new TextToSpeech(this, this);
	        }
	        else {
	        	ttsNotFoundError();
	        }
        }
	    Log.i(TAG,"onActivityResult() finished.");
	}
	
	// tts nao encontrado - precisa fazer a instalacao
	private void ttsNotFoundError(){
		Log.i(TAG, LogMessages.MSG_TTS_NOT_AVAILABLE);
    	finish();
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
    	Mint.startSession(MainActivity.this);
    	
    	super.onResume();
    	
    	/* shake register listener - SENSOR_DELAY_UI: 85-87 ms | SENSOR_DELAY_NORMAL: 215-230 ms | SENSOR_DELAY_GAME: 37-39 ms | SENSOR_DELAY_FASTEST: 18-20 ms */
    	mSensorManager.registerListener(mSensorListener, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
    	
    	/* proximity sensor register listener */
    	mSensorManager.registerListener(sensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    	    	
    	Log.i(TAG, "onResume() finished");
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
    	/* bugsense close session */
    	Mint.closeSession(MainActivity.this);
    	
    	/* shake unregistration */
    	mSensorManager.unregisterListener(mSensorListener);
    	
    	/* proximity sensor unregistration */
    	mSensorManager.unregisterListener(sensorEventListener, proximitySensor);
    	
    	super.onPause();
    	
    	/* tts stop */
    	if (myTTS != null){
        	myTTS.stop();
        }
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
        shutdownServices();
    }
    
    /** 
     * onBackPressed
	 *     changing option
	 * @see android.app.Activity#onBackPressed()
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
    @Override
    public void onBackPressed() {    
		if (MainFunctions.keyboardMode == Mode.LER.getValue()){
			MainFunctions.keyboardMode = Mode.SOLETRAR.getValue();
			speakWords( "Modo soletrar" );
		}
		else if (MainFunctions.keyboardMode == Mode.SOLETRAR.getValue()){
			MainFunctions.keyboardMode = Mode.DEFINIR.getValue();
			speakWords( "Modo dicionário" );
		}
		else if (MainFunctions.keyboardMode == Mode.DEFINIR.getValue()){
			MainFunctions.keyboardMode = Mode.LER.getValue();
			speakWords( "Modo leitura" );
		}					
	}
    
    /* proximity sensor */
    SensorEventListener sensorEventListener = new SensorEventListener() {
		@Override
		public void onSensorChanged(SensorEvent arg0) {
			if (arg0.sensor == proximitySensor) {
				float current_value = arg0.values[0]; // Proximity sensor distance measuredin centimeters (or binary near-far)
				float maxRange = proximitySensor.getMaximumRange();
				
				if (current_value >= maxRange){
					Log.i(TAG, "proximity far: " + maxRange);
				}
				else{
					Log.i(TAG, "proximity near");
				}				
    		}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
		}
    };
	
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
		   
		   String character = Character.toString(brailleDisplay.getText().charAt(charIndexCounter - 1));
       	   Log.i(TAG, "character = " + character);
			
		   // spell the word
       	   speakWords(ttsPhonetics.getTTS(character));
       	   
		   /* repeat the action if the current char is not the last */
		   if (charIndexCounter < brailleDisplay.getText().length()){
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
	 *     Starts the spell
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void startSpell(){		
		timerHandler.postDelayed(runnable, task1InitializationTime);
	}
	
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
	
}