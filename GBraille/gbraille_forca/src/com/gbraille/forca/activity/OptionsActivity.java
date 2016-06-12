package com.gbraille.forca.activity;

import java.util.Locale;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gbraille.forca.MainFunctions;
import com.gbraille.forca.R;
import com.gbraille.forca.DifficultyClass.DifficultyLevel;
import com.gbraille.forca.controller.BaixaJson;
import com.gbraille.forca.database.DbAdapter;
import com.gbraille.libraries.LogMessages;

import de.akquinet.android.androlog.Log;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.gbraille.forca.controller.BaixaJson;

public class OptionsActivity extends AccessibleAbstractActivityBrailleTemplate {
    DbAdapter dbAdapter;
    String systemLanguage = Locale.getDefault().getLanguage(); // pt, en, es
    
	/**
	 * onclick actions for buttons
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	protected void setOnClickForButtons() {		
		/* easy game - onclick event */
		this.buttons[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stopServices();
				logFunctions.logTextFile(LogMessages.MSG_BUTTON_WAS_ACCESSED + (String) buttons[0].getTag());
				if (Locale.getDefault().getLanguage().toString().equals("pt")){
					speakWords(myTTS,"Atualizando Banco de Perguntas! Aguarde!");
				}
				else if (Locale.getDefault().getLanguage().toString().equals("en")){
					speakWords(myTTS,"Updating Questions Bank! Wait!");
				}
				else if (Locale.getDefault().getLanguage().toString().equals("es")){
					speakWords(myTTS,"Actualización Banco de preguntas! Espera!");
				}
								
				
				//chamando so o metodo da classe que fará a requisição,
				//assim não precisaremos 
				BaixaJson.makeJsonArrayRequest();
				if (Locale.getDefault().getLanguage().toString().equals("pt")){
					speakWords(myTTS,"Banco de perguntas atualizado!");
				}
				else if (Locale.getDefault().getLanguage().toString().equals("en")){
					speakWords(myTTS,"Question Bank upgraded!");
				}
				else if (Locale.getDefault().getLanguage().toString().equals("es")){
					speakWords(myTTS,"Banco de preguntas actualizado!");
				}
			}
		});
		
		/* hard game - onclick event */
		this.buttons[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getActivity(), MainScreenActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		/* score - onclick event */
		this.buttons[2].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//do nothing
				if (Locale.getDefault().getLanguage().toString().equals("pt")){
					speakWords(myTTS,"Opção em branco");
				}
				else if (Locale.getDefault().getLanguage().toString().equals("en")){
					speakWords(myTTS,"blank option");
				}
				else if (Locale.getDefault().getLanguage().toString().equals("es")){
					speakWords(myTTS,"Opción en blanco");
				}
			}
		});

		/* insert question - onclick event */
		this.buttons[3].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int easyModeScore = dbAdapter.getScore(DifficultyLevel.FACIL.getValue());
				int hardModeScore = dbAdapter.getScore(DifficultyLevel.DIFICIL.getValue());
				speakWords(myTTS,easyModeScore + " " + getString(R.string.txtPointsEasyLevel) +". " + hardModeScore + getString(R.string.txtPointsHardLevel));				
				
			}
		});

		/* delete question - onclick event */
		this.buttons[4].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), MainScreenActivity.class);
				startActivity(i);
				finish();
			}
		});		
		
		/* exit - onclick event */
		this.buttons[5].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//do nothing
				if (Locale.getDefault().getLanguage().toString().equals("pt")){
					speakWords(myTTS,"Opção em branco");
				}
				else if (Locale.getDefault().getLanguage().toString().equals("en")){
					speakWords(myTTS,"blank option");
				}
				else if (Locale.getDefault().getLanguage().toString().equals("es")){
					speakWords(myTTS,"Opción en blanco");
				}
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		screenName = getString(R.string.options);
		super.onCreate(savedInstanceState);
		nodeDescriptionString = new String[totalButtons];
		fillScreenOptionsFromXMLFile("opcoes.xml","option");
		
		dbAdapter = new DbAdapter(this.getApplicationContext());
	}
	
	/**
	 * retrieves the Layout resource
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return layout resource id
	 */
	@Override
	protected int getLayoutResourceId() {
		return R.layout.activity_opcoes;
	}

	/**
	 * retrieves the current activity
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return current activity
	 */
	@Override
	protected Activity getActivity() {
		return OptionsActivity.this;
	}

	/**
	 * adds the buttons to variables
	 * 
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
	 * set the number of buttons in the current activity
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0 
	 */
	@Override
	protected void setNumberButtons() {
		this.totalButtons = 6;
	}
	
	/**
	 * retrieves the Activity's Linear Layout
	 * 
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
            			OptionsActivity.this.runOnUiThread(new Runnable() {
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
	
	/**
	 * fillScreenOptionsFromXMLFile
	 *     Loads the contents of the file named 'applications.xml' and get its params
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
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