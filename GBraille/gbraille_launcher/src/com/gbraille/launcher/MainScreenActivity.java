package com.gbraille.launcher;

import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gbraille.launcher.R;

import de.akquinet.android.androlog.Log;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * The main Activity class 
 * @author Antonio Rodrigo
 */
public class MainScreenActivity extends AccessibleAbstractBrailleLauncherTemplate{
	/* views */
	TextView t;
	int currentScreen;
	int totalMaximoTelas = 2;
	int totalMaximoBotoes = 6;
	String tema = "blue_contrast";
	String systemLanguage = Locale.getDefault().getLanguage(); // pt, en, es
	
	@Override
	protected void setOnClickForButtons() {
		// TODO Auto-generated method stub		
	}
	
	/**
	 * onCreate
	 *     Called when the activity is first created.
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		screenName = "gÊ braile";		
		super.onCreate(savedInstanceState);
		
		nodePackageString = new String[totalButtons];
		nodeActivityString = new String[totalButtons];
		nodeTipoString = new String[totalButtons];
		nodeFunctionString = new String[totalButtons];
		
		currentScreen = 1;
		fillScreenOptionsFromXMLFile("applications1.xml","app",tema);
		
		mainView = (LinearLayout) findViewById(R.id.layoutMainActivity);
		//mainactivityoptions
		changeThemeNormal();
		
		Log.i(TAG, "IDIOMA = "+systemLanguage);
	}
	
	private void changeThemeBlueContrast(){
		tema = "blue_contrast";
		for (int i = 0; i<6; i++){
			buttons[i].setBackgroundResource(R.drawable.button_bluecontrast);
		}
		if (currentScreen == 1){
			fillScreenOptionsFromXMLFile("applications1.xml","app",tema);
		}
		else if (currentScreen == 2){
			fillScreenOptionsFromXMLFile("applications2.xml","app",tema);
		}
	}
	
	private void changeThemeNormal(){
		tema = "normal";
		for (int i = 0; i<6; i++){
			buttons[i].setBackgroundResource(R.drawable.button);
		}
		if (currentScreen == 1){
			fillScreenOptionsFromXMLFile("applications1.xml","app",tema);
		}
		else if (currentScreen == 2){
			fillScreenOptionsFromXMLFile("applications2.xml","app",tema);
		}
	}
	
	/**
	 * getLayoutResourceId
	 *     retrieves the Layout resource
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return layout resource id
	 */
	@Override
	protected int getLayoutResourceId() {
		return R.layout.activity_main;
	}

	/**
	 * getActivity
	 *     retrieves the current activity 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return current activity
	 */
	@Override
	protected Activity getActivity() {
		return MainScreenActivity.this;
	}

	/**
	 * setViews
	 *     creates the buttons 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	@Override
	protected void setViews() {
		buttons = new ImageButton[totalButtons];		
		buttons[0] = (ImageButton) findViewById(R.id.botao1);
		buttons[1] = (ImageButton) findViewById(R.id.botao2);
		buttons[2] = (ImageButton) findViewById(R.id.botao3);
		buttons[3] = (ImageButton) findViewById(R.id.botao4);		
		buttons[4] = (ImageButton) findViewById(R.id.botao5);
		buttons[5] = (ImageButton) findViewById(R.id.botao6);		
	}
	
	/**
	 * setNumberButtons
	 *     set the number of buttons in the current activity
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	@Override
	protected void setNumberButtons() {
		totalButtons = 6;		
	}
	
	/**
	 * getLayout
	 *     retrieves the Activity's Linear Layout
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return LinearLayout
	 */
	@Override
	protected LinearLayout getLayout() {
		return (LinearLayout) this.findViewById(R.id.layoutMainActivity);
	}
	
	protected void setTts() { 
        if( Build.VERSION.SDK_INT  >= 15 ){
        	final String thisTAG = this.TAG;
            myTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {            	
                @Override
                public void onDone(String utteranceId){
                	Log.i(thisTAG, "Text to speech finished");
                	if (utteranceId.equals("EXIT_APPLICATION")){
			        	hapticFunctions.vibrateOnExit();
			        	destroyServices();
			        	finish();
					}
                	else if (utteranceId.equals("STARTING_APPLICATION")){
            			MainScreenActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            	// vibrates and ebables keyboard when application starts
                            	hapticFunctions.vibrate(1);                    			                    			
                            }
                        });      			
                	}
                }
 
                @Override
                public void onError(String utteranceId){
                	ttsNotFoundError();
                }
 
                @Override
                public void onStart(String utteranceId){
                	Log.i(thisTAG, "Started speaking");
                }
            });
        }
    }
	
	private void clearOptions(){
		for (int i = 0; i < totalMaximoBotoes; i++){
			buttons[i].setContentDescription("opção não definida");
    		buttons[i].setTag("opção não definida");
    		Drawable res = getResources().getDrawable(R.drawable.icon_empty);    		
    		buttons[i].setImageDrawable(res);
    		/* button onclick listener */
    		OnClickListener buttonClickListener = new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				// nothing to do
    			}
    		};
    		
    		/* button onfocuschange listener */
    		OnFocusChangeListener buttonOnFocusChangeListener = new View.OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) {
						// nothing to do
					}
				}
			};
    		
			buttons[i].setOnClickListener(buttonClickListener);
			buttons[i].setOnFocusChangeListener(buttonOnFocusChangeListener);    		
		}
	}
	
	/**
	 * fillScreenOptionsFromXMLFile
	 *     Loads the contents of the file named 'applications.xml' and get its params
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void fillScreenOptionsFromXMLFile(String fileName, String nodeScreenOptionsTag, String theme) {
		try {
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();    
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); 
		    Document doc = dBuilder.parse(getAssets().open(fileName));
		    doc.getDocumentElement().normalize();

		    NodeList nodeScreenOptions = doc.getElementsByTagName(nodeScreenOptionsTag);
		    int qtdeEntradasXML = nodeScreenOptions.getLength();
		    
		    clearOptions();
		    
		    for (int i = 0; i < qtdeEntradasXML; i++) {
		    	final int j = i;
		    	Node item = nodeScreenOptions.item(i);

		    	if (item.getNodeType() == Node.ELEMENT_NODE) {
		    		Element element = (Element) item;
		    		Node nodeDescricao = element.getElementsByTagName("description").item(0).getChildNodes().item(0);
		    		Node nodeDescricaoEn = element.getElementsByTagName("description_en").item(0).getChildNodes().item(0);
		    		Node nodeDescricaoEs = element.getElementsByTagName("description_es").item(0).getChildNodes().item(0);
		    		Node nodeIcone = element.getElementsByTagName("icon").item(0).getChildNodes().item(0);
		    		Node nodeIconeBlueContrast = element.getElementsByTagName("icon_bluecontrast").item(0).getChildNodes().item(0);
		    		Node nodePackage = element.getElementsByTagName("package").item(0).getChildNodes().item(0);
		    		Node nodeActivity = element.getElementsByTagName("activity").item(0).getChildNodes().item(0);
		    		Node nodeTipo = element.getElementsByTagName("type").item(0).getChildNodes().item(0);
		    		Node nodeFunction = element.getElementsByTagName("function").item(0).getChildNodes().item(0);
		    		
		    		Log.i(TAG, nodeDescricao.getNodeValue());
		    		Log.i(TAG, nodePackage.getNodeValue());
		    		Log.i(TAG, nodeActivity.getNodeValue());
		    		Log.i(TAG, nodeTipo.getNodeValue());
		    		Log.i(TAG, nodeFunction.getNodeValue());
		    		
		    		String description = nodeDescricao.getNodeValue();
		    		
		    		/* english language support */
		    		if (systemLanguage.equalsIgnoreCase("en")){
		    			description = nodeDescricaoEn.getNodeValue();
		    		}
		    		/* spanish language support */
		    		else if (systemLanguage.equalsIgnoreCase("es")){
		    			description = nodeDescricaoEs.getNodeValue();
		    		}
		    		
		    		final String buttonDesc = description; /* used in log */
		    		
		    		nodePackageString[i] = nodePackage.getNodeValue();
		    		nodeActivityString[i] = nodeActivity.getNodeValue();
		    		nodeTipoString[i] = nodeTipo.getNodeValue();
		    		nodeFunctionString[i] = nodeFunction.getNodeValue();
		    		
		    		int imageResource = 0;
		    		if (theme.equals("normal")){
		    		    imageResource = getResources().getIdentifier(nodeIcone.getNodeValue(), null, getPackageName());
		    		}
		    		else if (theme.equals("blue_contrast")){
		    		    imageResource = getResources().getIdentifier(nodeIconeBlueContrast.getNodeValue(), null, getPackageName());
		    		} 
		    		Drawable res = getResources().getDrawable(imageResource);
		    				    
		    		buttons[i].setContentDescription(description);		    		
		    		buttons[i].setImageDrawable(res);
		    		buttons[i].setTag(description);
		    		
		    		/* button onclick listener */
		    		OnClickListener buttonClickListener = new OnClickListener() {
		    			@Override
		    			public void onClick(View v) {
		    				if (nodeTipoString[j].equalsIgnoreCase("application")){
		    					Log.i(TAG, "Button " + buttonDesc + "was accessed");
		    					logFunctions.logTextFile("Button " + buttonDesc + " was accessed");
		    					stopServices();
		    					callApp(MainScreenActivity.this, nodePackageString[j], nodeActivityString[j]);
		    				} 
		    				else if (nodeTipoString[j].equalsIgnoreCase("widget")){
		    					/* increase audio widget */
		    					if (nodeFunctionString[j].equalsIgnoreCase("-")){
		    						// do nothing
		    					}
		    					else if (nodeFunctionString[j].equalsIgnoreCase("changeThemeBlueContrast")){
		    						changeThemeBlueContrast();
		    					}
		    					else if (nodeFunctionString[j].equalsIgnoreCase("changeThemeNormal")){
		    						changeThemeNormal();
		    					}
		    					else if (nodeFunctionString[j].equalsIgnoreCase("increaseVolume")){
		    						if (audioFunctions.increaseVolume(getActivity())){        				
		    	        				speakWords(myTTS, getString(R.string.speakAudioVolumeIncreased));
		    	        			}
		    	        			else{
		    	        				speakWords(myTTS, getString(R.string.speakAudioVolumeMax));
		    	        			}
		    					}
		    					/* decrease audio widget */
		    					else if (nodeFunctionString[j].equalsIgnoreCase("decreaseVolume")){
		    						if (audioFunctions.decreaseVolume(getActivity())){        				
		    	        				speakWords(myTTS, getString(R.string.speakAudioVolumeDecreased));
		    	        			}
		    	        			else{
		    	        				speakWords(myTTS, getString(R.string.speakAudioVolumeMin));
		    	        			}
		    					}
		    					/* battery level */
		    					else if (nodeFunctionString[j].equalsIgnoreCase("batteryLevel")){
		    						float batteryLevel = deviceClass.getBatteryLevel(context);
		    						String batteryLevelS = String.valueOf((int) batteryLevel);
		    						speakWords(myTTS,  getString(R.string.speakBatteryLevel) + ":" + batteryLevelS + getString(R.string.speakPercent));		    						
		    					}
		    					/* nextScreen */
		    					else if (nodeFunctionString[j].equalsIgnoreCase("nextScreen")){
		    						if (currentScreen == 1){
		    							currentScreen++;
		    							fillScreenOptionsFromXMLFile("applications2.xml","app",tema);
		    						}
		    						else if (currentScreen == totalMaximoTelas){
		    							currentScreen = 1;
		    							fillScreenOptionsFromXMLFile("applications1.xml","app",tema);
		    						}
		    					}
		    					/* date time info */
		    					else if (nodeFunctionString[j].equalsIgnoreCase("dateTime")){
		    						Calendar now = Calendar.getInstance();	    						
		    						
		    						int segundos = now.get(Calendar.SECOND);
		    						int minutos = now.get(Calendar.MINUTE);
		    						int horas = now.get(Calendar.HOUR);
		    						
		    						String dia = String.valueOf(now.get(Calendar.DATE));
		    						String mesS = "";
		    						int diaMes = now.get(Calendar.DAY_OF_WEEK);
		    						String diaMesS = "";
		    						
		    						switch(diaMes){
		    							case Calendar.SUNDAY:
		    								diaMesS = getString(R.string.speakSunday);
		    								break;
		    							case Calendar.MONDAY:
		    								diaMesS = getString(R.string.speakMonday);
		    								break;
		    							case Calendar.TUESDAY:
		    								diaMesS = getString(R.string.speakTuesday);
		    								break;
		    							case Calendar.WEDNESDAY:
		    								diaMesS = getString(R.string.speakWednesday);
		    								break;
		    							case Calendar.THURSDAY:
		    								diaMesS = getString(R.string.speakThursday);
		    								break;
		    							case Calendar.FRIDAY:
		    								diaMesS = getString(R.string.speakFriday);
		    								break;
		    							case Calendar.SATURDAY:
		    								diaMesS = getString(R.string.speakSaturday);
		    								break;
		    						}
		    						
		    						int mes = now.get(Calendar.MONTH) + 1;
		    						switch(mes){
		    							case 1:
		    								mesS = getString(R.string.speakJanuary);
		    								break;
		    							case 2:
		    								mesS = getString(R.string.speakFebruary);
		    								break;
		    							case 3:
		    								mesS = getString(R.string.speakMarch);
		    								break;
		    							case 4:
		    								mesS = getString(R.string.speakApril);
		    								break;
		    							case 5:
		    								mesS = getString(R.string.speakMay);
		    								break;
		    							case 6:
		    								mesS = getString(R.string.speakJune);
		    								break;
		    							case 7:
		    								mesS = getString(R.string.speakJuly);
		    								break;
		    							case 8:
		    								mesS = getString(R.string.speakAugust);
		    								break;
		    							case 9:
		    								mesS = getString(R.string.speakSeptember);
		    								break;
		    							case 10:
		    								mesS = getString(R.string.speakOctober);
		    								break;
		    							case 11:
		    								mesS = getString(R.string.speakNovember);
		    								break;
		    							case 12:
		    								mesS = getString(R.string.speakDecember);
		    								break;
		    						}
		    						String ano = String.valueOf(now.get(Calendar.YEAR));
		    						
		    						String data = diaMesS + ", " + dia + " de " + mesS + " de " + ano;
		    						if (systemLanguage.equalsIgnoreCase("en")){
		    							data = diaMesS + ", " + mesS + " " + dia + ", " + ano;
		    						}
		    						else if (systemLanguage.equalsIgnoreCase("es")){
		    							data = diaMesS + ", el " + dia + " de " + mesS + " de " + ano;
		    						}
		    						
		    						String horario = horas + getString(R.string.speakHours) + "," + minutos + getString(R.string.speakMinutes) + " e " + segundos + getString(R.string.speakSeconds);
		    						speakWords(myTTS,  data + " : " + horario);		    						
		    					}
		    				}
		    			}
		    		};		
		    		
		    		/* button onfocuschange listener */
		    		OnFocusChangeListener buttonOnFocusChangeListener = new View.OnFocusChangeListener() {
						public void onFocusChange(View v, boolean hasFocus) {
							if (hasFocus) {
								Log.i(TAG, "Button " + buttonDesc + " was selected");
								logFunctions.logTextFile("Button " + buttonDesc + " was selected");
								if (j == 0 || j == 1 || j == 2){
									speakButton(buttonDesc, 0, 1.0f);
								}
								else{
									speakButton(buttonDesc, 1.0f, 0);
								}
							}
						}
					};
		    		
					buttons[i].setOnClickListener(buttonClickListener);
					buttons[i].setOnFocusChangeListener(buttonOnFocusChangeListener);		    		
		    	}
		    }
		}
		catch(Exception e){
		     e.printStackTrace();
		}
	}
}