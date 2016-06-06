package com.gbraille.forca.activity;

import java.util.List;

import com.gbraille.forca.LanguageClass.TipoLanguage;
import com.gbraille.forca.R;
import com.gbraille.forca.DifficultyClass.DifficultyLevel;
import com.gbraille.forca.R.id;
import com.gbraille.forca.R.layout;
import com.gbraille.forca.TipoClass.TipoLevel;
import com.gbraille.forca.database.DbAdapter;
import com.gbraille.libraries.SpinnerClass;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertQuestionActivity extends ListActivity {
	private DbAdapter dbAdapter;
	Button btnCadastrar;
	Button btnExit;
	Button btnSampleData;
	
	EditText txtAnswer;
	EditText txtQuestion;
	EditText txtMissingCharPos;
	Spinner spinnerDificulty;
	Spinner spinnerTypeGame;
	Spinner spinnerTypeLanguage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar);
		
		txtQuestion = (EditText)findViewById(R.id.txtPergunta);
		txtAnswer = (EditText)findViewById(R.id.txtResposta);
		txtMissingCharPos = (EditText)findViewById(R.id.txtLetraFaltaPos);
		spinnerDificulty = (Spinner)findViewById(R.id.spinnerDificuldade);
		spinnerTypeGame = (Spinner)findViewById(R.id.spinnerTypeGame);
		spinnerTypeLanguage = (Spinner)findViewById(R.id.spinnerTypeLanguage);
		btnCadastrar = (Button)findViewById(R.id.cadastrar_button);
		btnExit = (Button)findViewById(R.id.sair_button);
		
		dbAdapter = new DbAdapter(getApplicationContext());		
		
		// listeners	
		btnCadastrar.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				dbAdapter.open();
				try{
					onInsert();
				}
				catch (Exception ex)
                {
                  Toast.makeText(InsertQuestionActivity.this, "cannot insert", Toast.LENGTH_LONG).show();
                }
				dbAdapter.close();
			}
		});
		
		btnExit.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(InsertQuestionActivity.this, MainScreenActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		loadSpinnerData();
		
		spinnerDificulty.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {		    	
		    	SpinnerClass spo = (SpinnerClass) spinnerDificulty.getItemAtPosition(position);	    
			    int dificuldade =  spo.getId();
			    if (dificuldade == DifficultyLevel.DIFICIL.getValue()){
			    	txtMissingCharPos.setText("0");
			    	txtMissingCharPos.setEnabled(false);
			    }
			    else{
			    	txtMissingCharPos.setText("");
			    	txtMissingCharPos.setEnabled(true);
			    }
		    }
		    
		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
		
		spinnerTypeGame.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {		    	
		    	SpinnerClass spo1 = (SpinnerClass) spinnerTypeGame.getItemAtPosition(position);	    
			    int dificuldade =  spo1.getId();
			    if (dificuldade == TipoLevel.SINONIMO.getValue()){
			    	txtMissingCharPos.setText("");
			    	txtMissingCharPos.setEnabled(true);
			    }
			    else{
			    	txtMissingCharPos.setText("");
			    	txtMissingCharPos.setEnabled(true);
			    }
		    }
		    
		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }
		});
		
		spinnerTypeLanguage.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				SpinnerClass spo2 = (SpinnerClass) spinnerTypeLanguage.getItemAtPosition(position);
				int linguagem = spo2.getId();
				
			}
			
			public void onNothingSelected(AdapterView<?> parentView) {
				
			}
		});
		
	}
	
	private void loadSpinnerData() {
		dbAdapter.open();
		// Spinner Drop down elements
		List<SpinnerClass> labels = dbAdapter.getAllDificulty();
		// Creating adapter for spinner
		ArrayAdapter<SpinnerClass> dataAdapter = new ArrayAdapter<SpinnerClass>(this, android.R.layout.simple_spinner_item, labels);
		// Drop down layout style - list view with radio button
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinnerDificulty.setAdapter(dataAdapter);
		
		List<SpinnerClass> labels2 = dbAdapter.getAllType();
		
		ArrayAdapter<SpinnerClass> dataAdapter2 = new ArrayAdapter<SpinnerClass>(this, android.R.layout.simple_spinner_item, labels2);
		
		dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinnerTypeGame.setAdapter(dataAdapter2);
		
		List<SpinnerClass> labels3 = dbAdapter.getAllLanguages();
		
		ArrayAdapter<SpinnerClass> dataAdapter3 = new ArrayAdapter<SpinnerClass>(this, android.R.layout.simple_spinner_item, labels3);
		
		dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		spinnerTypeLanguage.setAdapter(dataAdapter3);
		
		dbAdapter.close();
	}
	
	public void onInsert() {
		String question = txtQuestion.getText().toString();
	    String answer = txtAnswer.getText().toString();    
	    String missingCharPos = txtMissingCharPos.getText().toString();
	    
	    SpinnerClass spo = (SpinnerClass) spinnerDificulty.getSelectedItem ();	    
	    int dificulty =  spo.getId();
	    
	    SpinnerClass spo1 = (SpinnerClass) spinnerTypeGame.getSelectedItem();
	    int tipo = spo1.getId();
	    
	    SpinnerClass spo2 = (SpinnerClass) spinnerTypeLanguage.getSelectedItem();
	    int lingua = spo2.getId();
	    /*
	    
	    // Save the new comment to the database	    
	    long id;
	    id = dbAdapter.insertQuestion(question, answer, missingCharPos, dificulty, tipo, lingua);
	    
	    if (id > 0){
	    	Toast.makeText(getApplicationContext(), "REGISTRO CADASTRADO COM SUCESSO", Toast.LENGTH_LONG).show();
	    }
	    else{
	    	Toast.makeText(getApplicationContext(), "FALHA AO CADASTRAR O REGISTRO", Toast.LENGTH_LONG).show();
	    }  
	    txtQuestion.setText("");
	    txtAnswer.setText("");
	    txtMissingCharPos.setText("");
	    txtAnswer.requestFocus();
	    */
	}
	
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(InsertQuestionActivity.this, MainScreenActivity.class);
		startActivity(i);
		finish();
	}
	
}