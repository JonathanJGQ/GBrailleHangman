package com.gbraille.ortomonstro.activity;

import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gbraille.libraries.LogMessages;
import com.gbraille.ortomonstro.MainFunctions;
import com.gbraille.ortomonstro.R;
import com.gbraille.ortomonstro.TipoClass;
import com.gbraille.ortomonstro.DifficultyClass.DifficultyLevel;
import com.gbraille.ortomonstro.LanguageClass.TipoLanguage;
import com.gbraille.ortomonstro.TipoClass.TipoLevel;
import com.gbraille.ortomonstro.controller.BaixaJson;
import com.gbraille.ortomonstro.database.DbAdapter;

import de.akquinet.android.androlog.Log;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;



@SuppressLint("NewApi")
public class SelectDifficultyOrthographic extends AccessibleAbstractActivityBrailleTemplate {

	DbAdapter dbAdapter;
    String systemLanguage = Locale.getDefault().getLanguage(); // pt, en, es
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		screenName = getString(R.string.title_activity_select_difficulty);
		super.onCreate(savedInstanceState);
		nodeDescriptionString = new String[totalButtons];
		fillScreenOptionsFromXMLFile("selectdifficulty.xml","option");
		dbAdapter = new DbAdapter(this.getApplicationContext());
		
		if (dbAdapter.getInstalacao().equals("N")){			
			try{
				BaixaJson.makeJsonArrayRequest();
			}
			catch (Exception ex){
	            Toast.makeText(this, "Problems inserting questions", Toast.LENGTH_LONG).show();
	        }
	        }					
			dbAdapter.updateInstalacao("S");
		
		dbAdapter.setAllQuestionsToUnplayed();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(getActivity(), MainScreenActivity.class);
		startActivity(i);
		finish();
		
	}

	@Override
	protected LinearLayout getLayout() {
		// TODO Auto-generated method stub
		return (LinearLayout) this.findViewById(R.id.layoutSelectDifficulty);
	}

	@Override
	protected int getLayoutResourceId() {
		// TODO Auto-generated method stub
		return R.layout.activity_select_difficulty_orthographic;
	}

	@Override
	protected Activity getActivity() {
		// TODO Auto-generated method stub
		return SelectDifficultyOrthographic.this;
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		buttons = new ImageButton[totalButtons];
		buttons[0] = (ImageButton) findViewById(R.id.botao1);
		buttons[1] = (ImageButton) findViewById(R.id.botao2);
		buttons[2] = (ImageButton) findViewById(R.id.botao3);
		
	}

	@Override
	protected void setNumberButtons() {
		// TODO Auto-generated method stub
		this.totalButtons = 3;
	}

	@Override
	protected void setOnClickForButtons() {
		// TODO Auto-generated method stub
		this.buttons[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dbAdapter.getCountEasyQuestions() > 0){
					stopServices();
					logFunctions.logTextFile(LogMessages.MSG_BUTTON_WAS_ACCESSED + (String) buttons[0].getTag());
					/* reset current score */
					MainFunctions.score = 0;
					/* easy game mode */
					MainFunctions.dificultyLevel = DifficultyLevel.FACIL.getValue();
					MainFunctions.tipoLevel = TipoLevel.ORTOGRAFICO.getValue();
					
					if (Locale.getDefault().getLanguage().toString().equals("pt")) {
			   			 MainFunctions.tipolingua = 1;
			   		} else if (Locale.getDefault().getLanguage().toString().equals("es")) {
			   			MainFunctions.tipolingua = 2;
			   		} else if (Locale.getDefault().getLanguage().toString().equals("en")) {
			   			MainFunctions.tipolingua = 3;
			   		}
					
					Intent i = new Intent(getActivity(), PlayGameActivity.class);
					startActivity(i);
					finish();
				}
				else{
					speakWords(myTTS, getString(R.string.txtNoQuestionsEasyLevel));
				}
			}
		});
		
		/* hard game - onclick event */
		this.buttons[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dbAdapter.getCountHardQuestions() > 0){
					stopServices();
					logFunctions.logTextFile(LogMessages.MSG_BUTTON_WAS_ACCESSED + (String) buttons[1].getTag());
					/* reset current score */
					MainFunctions.score = 0;
					/* hard game mode */
					MainFunctions.dificultyLevel = DifficultyLevel.DIFICIL.getValue();
					MainFunctions.tipoLevel = TipoLevel.ORTOGRAFICO.getValue();
					Intent i = new Intent(getActivity(), PlayGameActivity.class);
					startActivity(i);
					finish();
				}
				else{
					speakWords(myTTS, getString(R.string.txtNoQuestionsHardLevel) );
				}
			}
		});
		
		/* score - onclick event */
		this.buttons[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), MainScreenActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	

	@SuppressLint("NewApi")
	@Override
	protected void setTts() {
		// TODO Auto-generated method stub
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
            			SelectDifficultyOrthographic.this.runOnUiThread(new Runnable() {
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

	private void fillScreenOptionsFromXMLFile(String fileName, String nodeScreenOptionsTag) {
		try {
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();    
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); 
		    Document doc = dBuilder.parse(getAssets().open(fileName));
		    doc.getDocumentElement().normalize();

		    NodeList nodeScreenOptions = doc.getElementsByTagName(nodeScreenOptionsTag);
		    
		    for (int i = 0; i < nodeScreenOptions.getLength(); i++) {
		    	final int j = i;
		    	Node item = nodeScreenOptions.item(i);

		    	if (item.getNodeType() == Node.ELEMENT_NODE) {
		    		Element element = (Element) item;
		    		Node nodeDescricao = element.getElementsByTagName("description").item(0).getChildNodes().item(0);
		    		Node nodeDescricaoEn = element.getElementsByTagName("description_en").item(0).getChildNodes().item(0);
		    		Node nodeDescricaoEs = element.getElementsByTagName("description_es").item(0).getChildNodes().item(0);		    		
		    		Node nodeIcone = element.getElementsByTagName("icon").item(0).getChildNodes().item(0);
		    		
		    		Log.i(TAG, nodeDescricao.getNodeValue());
		    		
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
		    		
		    		nodeDescriptionString[i] = description;
		    		
		    		int imageResource = getResources().getIdentifier(nodeIcone.getNodeValue(), null, getPackageName());
		    		Drawable res = getResources().getDrawable(imageResource);
		    				    
		    		buttons[i].setTag(nodeDescricao.getNodeValue());
		    		buttons[i].setImageDrawable(res);
		    		buttons[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
					});
		    		
		    	}
		    }
		}
		catch(Exception e){
		     e.printStackTrace();
		}
	}
	
}
