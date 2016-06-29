package com.gbraille.ortomonstro.activity;
import com.gbraille.ortomonstro.database.DbAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SingleMenuItemActivity extends Activity {
	
	
	// XML node keys
		static final String KEY_NAME = "question";
		static final String KEY_ANSWER = "answer";
		static final String KEY_MISS = "missingCharPos"; 
		static final String KEY_DIFICULDADE = "dificuldade";
		static final String KEY_JOGO = "jogo";
		static final String KEY_LINGUA = "lingua";
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        
	        // getting intent data
	        Intent in = getIntent();
	        
	        // Get XML values from previous intent
	        String pergunta = in.getStringExtra(KEY_NAME);
	        String resposta = in.getStringExtra(KEY_ANSWER);
	        String letraFaltaPos = in.getStringExtra(KEY_MISS);
	        String dificuldade = in.getStringExtra(KEY_DIFICULDADE);
	        String jogo = in.getStringExtra(KEY_JOGO);
	        String linguagem = in.getStringExtra(KEY_LINGUA);
	        /*
	        // Displaying all values on the screen
	        TextView lblName = (TextView) findViewById(R.id.);
	        TextView lblCost = (TextView) findViewById(R.id.cost_label);
	        TextView lblDesc = (TextView) findViewById(R.id.description_label);
	        */
	        
	        DbAdapter.insertQuestion(pergunta, resposta, letraFaltaPos, Integer.parseInt(dificuldade), Integer.parseInt(jogo), Integer.parseInt(linguagem));
	    }
	
}