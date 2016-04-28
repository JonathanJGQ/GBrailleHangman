package com.gbraille.forca.activity;

import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

import com.gbraille.forca.MainFunctions;
import com.gbraille.forca.R;
import com.gbraille.forca.DifficultyClass.DifficultyLevel;
import com.gbraille.forca.LanguageClass.TipoLanguage;
import com.gbraille.forca.TipoClass.TipoLevel;
import com.gbraille.forca.database.DbAdapter;
import com.gbraille.libraries.LogMessages;

import de.akquinet.android.androlog.Log;

public class SelectDifficultySynonymous extends AccessibleAbstractActivityBrailleTemplate {
	
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
				insertSampleDataEasyMode();
			}
			catch (Exception ex){
	            Toast.makeText(this, "Problems inserting questions in easy mode", Toast.LENGTH_LONG).show();
	        }
			
			try{
				insertSampleDataHardMode();
			}
			catch (Exception ex){
	            Toast.makeText(this, "Problems inserting questions in hard mode", Toast.LENGTH_LONG).show();
	        }					
			dbAdapter.updateInstalacao("S");
		}
		
		dbAdapter.setAllQuestionsToUnplayed();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(getActivity(), MainScreenActivity.class);
		startActivity(i);
		finish();
		
	}

	public void insertSampleDataEasyMode() {
		String question = "";
	    String answer = "";
	    String missingCharPos = "";
	    
	    if (systemLanguage.equalsIgnoreCase("pt")){
	    	question = "Com que letra se escreve a palavra: joelho? com g? Ou com j?";
	    	answer = "joelho";
	    	missingCharPos = "1";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Com que letra se escreve a palavra: gelo? Com g? Ou com j?";
	    	answer = "gelo";
	    	missingCharPos = "1";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Com que letra se escreve a palavra: fácil? Com s? Ou com c?";
	    	answer = "fácil";
	    	missingCharPos = "3";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Com que letra se escreve a palavra: louça? com cedilha? Ou com c?";
	    	answer = "louça";
	    	missingCharPos = "4";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    	    
	    	question = "Com que letra se escreve a palavra: gesto? com: ó? Ou com: u?";
	    	answer = "gesto";
	    	missingCharPos = "5";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Com que letra se escreve a palavra: xingar? com: ch? Ou com: x?";
	    	answer = "xingar";
	    	missingCharPos = "1";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Com que letra se escreve a palavra: casa? com: s? Ou com: z?";
	    	answer = "casa";
	    	missingCharPos = "3";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Com que letra se escreve a palavra: baliza? com: s? Ou com: z?";
	    	answer = "baliza";
	    	missingCharPos = "5";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    	    
	    	question = "Com que letra se escreve a palavra: show? com: w? Ou com: u?";
	    	answer = "show";
	    	missingCharPos = "4";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Com que letra se escreve a palavra: empecilho? com: é? Ou com: i?";
	    	answer = "empecilho";
	    	missingCharPos = "1";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Com que letra se escreve a palavra: Natal? com: l? Ou com: u?";
	    	answer = "Natal";
	    	missingCharPos = "5";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Com que letra se escreve a palavra: quadrado? com: c? Ou com: q?";
	    	answer = "quadrado";
	    	missingCharPos = "1";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	/* Questões de sinônimo nível fácil*/

	    	question = "Qual é o sinônimo de: residência";
	    	answer = "casa";
	    	missingCharPos = "3";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(), TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o antônimo de: alto";
	    	answer = "baixo";
	    	missingCharPos = "4";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o antônimo de: bonito";
	    	answer = "feio";
	    	missingCharPos = "4";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o sinônimo de: verdadeiro";
	    	answer = "correto";
	    	missingCharPos = "6";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o sinônimo de: macio";
	    	answer = "fofo";
	    	missingCharPos = "3";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o antônimo de: forte";
	    	answer = "fraco";
	    	missingCharPos = "2";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    	
	    	Toast.makeText(getApplicationContext(), "REGISTRO CADASTRADO COM SUCESSO", Toast.LENGTH_LONG).show();
	    }
	    else if (systemLanguage.equalsIgnoreCase("en")){
	    	
	    	/*Ortográfico*/
	    	
	    	question = "How to spell the word meet? With a? Or whith e?";
	    	answer = "meet";
	    	missingCharPos = "3";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	question = "How to spell the word hypocrite? With y? Or with i?";
	    	answer = "hypocrite";
	    	missingCharPos = "2";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	question = "How to spell the word jealous? With e? Or with a?";
	    	answer = "jealous";
	    	missingCharPos = "3";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	question = "How to spell the word later? With a? Or with e?";
	    	answer = "later";
	    	missingCharPos = "2";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	/*Sinônimos*/
	    	
	    	question = "Which the synonym to felling alone?";
	    	answer = "loneliness";
	    	missingCharPos = "7";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(), TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	
	    	
	    	question = "which the synonym to know people?";
	    	answer = "meet";
	    	missingCharPos = "3";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	
	    }
	    else if (systemLanguage.equalsIgnoreCase("es")){
	    	
	    	question = "Cuál letra si escribe la palabra: Energía? Con g? O con j?";
	    	answer = "energía";
	    	missingCharPos = "5";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.ES.getValue());
	    	
	    	question = "Cuál letra si escribe la palabra: dibujo? Con b? O con v?";
	    	answer = "dibujo";
	    	missingCharPos = "3";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.ES.getValue());
	    	
	    	question = "Cuál letra si escribe la palabra: sobremesa? Con s? O con z?";
	    	answer = "sobremesa";
	    	missingCharPos = "8";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.ES.getValue());
	    	
	    	question = "Cuál letra si escribe la palabra: estrenar? Con s? O con x?";
	    	answer = "estrenar";
	    	missingCharPos = "2";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.FACIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.ES.getValue());

	    }
	}
	
	public void insertSampleDataHardMode() {
		String question = "";
	    String answer = "";
	    String missingCharPos = "";
	    
	    
	    if (systemLanguage.equalsIgnoreCase("pt")){
	    	
	    	/*Questões ortográficas nível difícil*/
	    	
	    	question = "Como se escreve a palavra: joelho?";
	    	answer = "joelho";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Como se escreve a palavra: gelo?";
	    	answer = "gelo";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Como se escreve a palavra: fácil?";
	    	answer = "fácil";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Como se escreve a palavra: louça?";
	    	answer = "louça";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    	    
	    	question = "Como se escreve a palavra: gesto?";
	    	answer = "gesto";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Como se escreve a palavra: xingar?";
	    	answer = "xingar";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Como se escreve a palavra: casa?";
	    	answer = "casa";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Como se escreve a palavra: baliza?";
	    	answer = "baliza";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    	    
	    	question = "Como se escreve a palavra: show?";
	    	answer = "show";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Como se escreve a palavra: empecilho?";
	    	answer = "empecilho";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Como se escreve a palavra: Natal?";
	    	answer = "Natal";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Como se escreve a palavra: quadrado?";
	    	answer = "quadrado";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	/*Questões de sinônimo nível difícil*/
	    	
	    	question = "Qual é o sinônimo de: residência";
	    	answer = "casa";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o antônimo de: alto";
	    	answer = "baixo";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o antônimo de: bonito";
	    	answer = "feio";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o sinônimo de: verdadeiro";
	    	answer = "correto";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o sinônimo de: macio";
	    	answer = "fofo";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    
	    	question = "Qual é o antônimo de: forte";
	    	answer = "fraco";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.PT.getValue());
	    	
	    	
	    	
	    	Toast.makeText(getApplicationContext(), "REGISTRO CADASTRADO COM SUCESSO", Toast.LENGTH_LONG).show();
	    
	    }else if (systemLanguage.equalsIgnoreCase("es")){
	    	
	    	/*Questões ortográficas nível difícil*/
	    	
	    	question = "Cómo se escrebe la palabra: Energía?";
	    	answer = "energía";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.ES.getValue());
	    	
	    	question = "Cómo se escrebe la palabra: dibujo?";
	    	answer = "dibujo";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.ES.getValue());
	    	
	    	question = "Cómo se escrebe la palabra: sobremesa?";
	    	answer = "sobremesa";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.ES.getValue());
	    	
	    	question = "Cómo se escrebe la palabra: estrenar?";
	    	answer = "estrenar";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.ES.getValue());
	    
	    	
	    	}
	    else if (systemLanguage.equalsIgnoreCase("en")){
	    	/*Ortográficos*/
	    	
	    	question = "How to spell the word meet?";
	    	answer = "meet";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	question = "How to spell the word hypocrite?";
	    	answer = "hypocrite";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	question = "How to spell the word jealous?";
	    	answer = "jealous";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	question = "How to spell the word later?";
	    	answer = "later";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(), TipoLevel.ORTOGRAFICO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	/*Sinônimos*/
	    	
	    	question = "Which the synonym to felling alone?";
	    	answer = "loneliness";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(), TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	question = "which the synonym to know people?";
	    	answer = "meet";
	    	missingCharPos = "0";
	    	dbAdapter.insertQuestion(question, answer, missingCharPos, DifficultyLevel.DIFICIL.getValue(),TipoLevel.SINONIMO.getValue(),
	    			TipoLanguage.EN.getValue());
	    	
	    	Toast.makeText(getApplicationContext(), "REGISTRATION REGISTERED WITH SUCCESS", Toast.LENGTH_LONG).show();
	    }

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
		return SelectDifficultySynonymous.this;
	}

	@Override
	protected void setViews() {
		// TODO Auto-generated method stub
		buttons = new ImageButton[totalButtons];
		buttons[0] = (ImageButton) findViewById(R.id.botao1);
		buttons[1] = (ImageButton) findViewById(R.id.botao2);
		buttons[2] = (ImageButton) findViewById(R.id.botao3);
		buttons[3] = (ImageButton) findViewById(R.id.botao4);	
		buttons[4] = (ImageButton) findViewById(R.id.botao5);
		buttons[5] = (ImageButton) findViewById(R.id.botao6);
		
	}

	@Override
	protected void setNumberButtons() {
		// TODO Auto-generated method stub
		this.totalButtons = 6;
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
					MainFunctions.tipoLevel = TipoLevel.SINONIMO.getValue();
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
				if (dbAdapter.getCountEasyQuestions() > 0){
					stopServices();
					logFunctions.logTextFile(LogMessages.MSG_BUTTON_WAS_ACCESSED + (String) buttons[0].getTag());
					/* reset current score */
					MainFunctions.score = 0;
					/* easy game mode */
					MainFunctions.dificultyLevel = DifficultyLevel.FACIL.getValue();
					MainFunctions.tipoLevel = TipoLevel.SINONIMO.getValue();
					Intent i = new Intent(getActivity(), PlayGameActivity.class);
					startActivity(i);
					finish();
				}
				else{
					speakWords(myTTS, getString(R.string.txtNoQuestionsEasyLevel));
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

		/* insert question - onclick event */
		this.buttons[3].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dbAdapter.getCountHardQuestions() > 0){
					stopServices();
					logFunctions.logTextFile(LogMessages.MSG_BUTTON_WAS_ACCESSED + (String) buttons[1].getTag());
					/* reset current score */
					MainFunctions.score = 0;
					/* hard game mode */
					MainFunctions.dificultyLevel = DifficultyLevel.DIFICIL.getValue();
					MainFunctions.tipoLevel = TipoLevel.SINONIMO.getValue();
					Intent i = new Intent(getActivity(), PlayGameActivity.class);
					startActivity(i);
					finish();
				}
				else{
					speakWords(myTTS, getString(R.string.txtNoQuestionsHardLevel) );
				}
			}
		});

		/* delete question - onclick event */
		this.buttons[4].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dbAdapter.getCountEasyQuestions() > 0){
					stopServices();
					logFunctions.logTextFile(LogMessages.MSG_BUTTON_WAS_ACCESSED + (String) buttons[0].getTag());
					/* reset current score */
					MainFunctions.score = 0;
					/* easy game mode */
					MainFunctions.dificultyLevel = DifficultyLevel.DIFICIL.getValue();
					MainFunctions.tipoLevel = TipoLevel.SINONIMO.getValue();
					Intent i = new Intent(getActivity(), PlayGameActivity.class);
					startActivity(i);
					finish();
				}
				else{
					speakWords(myTTS, getString(R.string.txtNoQuestionsEasyLevel));
				}
			
			}
		});		
		
		/* exit - onclick event */
		this.buttons[5].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			
				Intent i = new Intent(getActivity(), MainScreenActivity.class);
				startActivity(i);
				finish();
			
			}
		});
	}
	

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
            			SelectDifficultySynonymous.this.runOnUiThread(new Runnable() {
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
