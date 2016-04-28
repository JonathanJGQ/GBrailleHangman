package com.gbraille.forca.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.gbraille.forca.QuestionClass;
import com.gbraille.forca.R;

import de.akquinet.android.androlog.Log;

public class UpdateQuestion extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		
		System.out.println("ESTAMOS DENTRO DO ONCREATE");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_question);
			
		System.out.println("ESTAMOS NO ONCREATE");
		
		XmlPullParserFactory pullParserFactory;
		try {
			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();
				System.out.println("ENTROU NO TRY");
			    InputStream in_s = getApplicationContext().getAssets().open("BancoDeDadosPerguntas.xml");
		        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(in_s, null);
	            System.out.println("LEU O XML");
	            parseXML(parser);
	            System.out.println("PASSOU DO PARSER");

		} catch (XmlPullParserException e) {

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
		ArrayList<QuestionClass> respostas = null;
        int eventType = parser.getEventType();
        QuestionClass currentProduct = null;
        int count = 0;
        String name = null;
        
        while (eventType != XmlPullParser.END_DOCUMENT){
             
            System.out.println("ENTROU NO WHILE");
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                	System.out.println("STARTDOCUMENT");
                	respostas = new ArrayList<QuestionClass>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    System.out.println("STARTAG");
                    if (name == "questao"){
                    	System.out.println("CHEGOU NA CRIAÇÃO DO OBJETO");
                        currentProduct = new QuestionClass();
                    } else if (currentProduct != null){
                        if (name == "question"){
                        	System.out.println("QUESTION");
                            currentProduct.question = parser.nextText();
                        } else if (name == "answer"){
                        	System.out.println("ANSWER");
                        	currentProduct.answer = parser.nextText();
                        } else if (name == "missingCharPos"){
                        	System.out.println("MISSINGCHAR");
                            currentProduct.missingCharPos= parser.nextText();
                        } else if (name == "dificuldade") {
                        	System.out.println("DIFICULDADE");
                        	currentProduct.dificuldade = parser.nextText();
                        } else if (name == "jogo") {
                        	System.out.println("JOGO");
                        	currentProduct.jogo = parser.nextText();
                        } else if (name == "lingua") {
                        	System.out.println("LINGUA");
                        	currentProduct.lingua = parser.nextText();
                       	
                        }
                        

                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    System.out.println("ENDTAG");
                    if (name.equalsIgnoreCase("questao") && currentProduct != null){
                    	respostas.add(currentProduct);
                    }
                    break;
                
                    
            }
            respostas.add(currentProduct);
            count = (count+1);
            eventType = parser.next();
        }
        System.out.println("RODOU :" + count);

        printProducts(respostas);
	}

	private void printProducts(ArrayList<QuestionClass> respostas)
	{
		String content = "";
		Iterator<QuestionClass> it = respostas.iterator();
		
		while(it.hasNext())
		{
			System.out.println("ENTROU NO WHILE");
			QuestionClass currProduct  = it.next();
			System.out.println("IMPRIME QUESTÃO: " + currProduct.question);
			content = content + "Question :" +  currProduct.question + "n";
			content = content + "Answer :" +  currProduct.answer + "n";
			content = content + "Character que falta: " +  currProduct.missingCharPos + "n";
			content = content + "Dificuldade :" +  currProduct.dificuldade + "n";
			content = content + "Jogo :" +  currProduct.jogo + "n";
			content = content + "Linguagem :" +  currProduct.lingua + "n";

		}

		ListView display = (ListView)findViewById(R.id.info);
		System.out.println("ESSE É O CONTENT: " + content);
		display.setTag(content);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
	
	public void onBackPressed () {
		Intent i = new Intent(UpdateQuestion.this, MainScreenActivity.class);
		startActivity(i);
		finish();
	}


		
		
		
		
} 