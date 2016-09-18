package com.gbraille.ortomonstro.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.gbraille.libraries.SpinnerClass;
import com.gbraille.ortomonstro.MainFunctions;
import com.gbraille.ortomonstro.DifficultyClass.DifficultyLevel;
import com.gbraille.ortomonstro.LanguageClass.TipoLanguage;
import com.gbraille.ortomonstro.TipoClass.TipoLevel;

import de.akquinet.android.androlog.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter {
	private static SQLiteDatabase db;
	private DbHelper helper;
	String TAG = "DBAdapter";
	Context ctx;
	
	public DbAdapter(Context context) {
		ctx = context;
	    helper = new DbHelper(ctx);
	    db = helper.getDatabase();
	}
	
	/* update instalacao */
	
	public boolean updateInstalacao(String value) {
	    ContentValues args = new ContentValues();
	    args.put(DbHelper.COLUMN_INSTALADO, value);
	    return db.update(DbHelper.TABLE_INSTALACAO, args, DbHelper.COLUMN_ID + "= 1", null) > 0;
	}
	
	/* get instalacao */
	
	public String getInstalacao(){
		Cursor cursor = db.rawQuery("SELECT " 
	        + DbHelper.COLUMN_INSTALADO
			+ " FROM " + DbHelper.TABLE_INSTALACAO
			+ " WHERE " + DbHelper.COLUMN_ID  + " = 1", null);
		cursor.moveToFirst();
		String value = cursor.getString(0);
		cursor.close();
		return value;
	}
	
	/* score functions */
	
	public boolean updateScore(int dificulty, int score) {
	    ContentValues args = new ContentValues();
	    args.put(DbHelper.COLUMN_PONTUACAO, score);
	    return db.update(DbHelper.TABLE_PONTUACAO, args, DbHelper.COLUMN_DIFICULDADE + "=" + dificulty, null) > 0;
	}
	
	public int getScore(int dificulty){
		Cursor cursor = db.rawQuery("SELECT " 
	        + DbHelper.COLUMN_PONTUACAO
			+ " FROM " + DbHelper.TABLE_PONTUACAO
			+ " WHERE " + DbHelper.COLUMN_DIFICULDADE  + " = " + dificulty, null);
		cursor.moveToFirst();
		int score = cursor.getInt(0);
		cursor.close();
		return score;
	}
	
	/* contador de questões */
	
	public int getCountEasyQuestions(){
		int count = 0;
		String query = "SELECT count(*) FROM " + DbHelper.TABLE_RESPOSTAS
			+ " WHERE " + DbHelper.COLUMN_DIFICULDADE  + " = " + DifficultyLevel.FACIL.getValue();
		Cursor cursor = db.rawQuery(query, null);		
		if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
		cursor.close();
		return count;
	}
	
	public int getCountHardQuestions(){
		int count = 0;
		String query = "SELECT count(*) FROM " + DbHelper.TABLE_RESPOSTAS
			+ " WHERE " + DbHelper.COLUMN_DIFICULDADE  + " = " + DifficultyLevel.DIFICIL.getValue();
		Cursor cursor = db.rawQuery(query, null);		
		if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
		cursor.close();
		return count;
	}
	
	public int getCountOrthoQuestions() {
		int count = 0;
		String query = "SELECT count(*) FROM " + DbHelper.TABLE_RESPOSTAS
				+ " WHERE " + DbHelper.COLUMN_TIPO_JOGO + " = " + TipoLevel.ORTOGRAFICO.getValue();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			count = cursor.getInt(0);
		}
		cursor.close();
		return count;
	}
	
	public int getCountSinonQuestions() {
		int count = 0;
		String query = "SELECT count(*) FROM " + DbHelper.TABLE_RESPOSTAS
				+ " WHERE " + DbHelper.COLUMN_TIPO_JOGO + " = " + TipoLevel.SINONIMO.getValue();
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			count = cursor.getInt(0);
		}
		cursor.close();
		return count;
	}
	
	/* question functions */
	
	public static void insertQuestion(String pergunta, String resposta, String letraFaltaPos, int dificuldade, int jogo, int linguagem){
		ContentValues values = new ContentValues();
		values.put(DbHelper.COLUMN_PERGUNTA, pergunta);
		values.put(DbHelper.COLUMN_RESPOSTA, resposta);		
		values.put(DbHelper.COLUMN_LETRA_FALTA_POS, letraFaltaPos);
		values.put(DbHelper.COLUMN_DIFICULDADE, dificuldade);
		values.put(DbHelper.COLUMN_TIPO_JOGO, jogo);
		values.put(DbHelper.COLUMN_TIPO_LINGUAGEM, linguagem);
		values.put(DbHelper.COLUMN_ACESSADO, "N");
		
		db.insert(DbHelper.TABLE_RESPOSTAS, null, values);
	}
	
	public static void insertAtividade(String codigo, String nome, String dataInicial, String dataFinal, String numeroDeQuestoes, String descricao, String level){
		ContentValues values = new ContentValues();
		values.put(DbHelper.CODE, codigo);
		values.put(DbHelper.NAME, nome);		
		values.put(DbHelper.DATE_INITIAL, dataInicial);
		values.put(DbHelper.DATE_FINAL, dataFinal);
		values.put(DbHelper.NUMBER_OF_QUESTIONS, numeroDeQuestoes);
		values.put(DbHelper.DESCRIPTION, descricao);
		values.put(DbHelper.LEVEL, level);
		
		db.insert(DbHelper.TABLE_ATIVIDADES, null, values);
	}
	
	public void deleteQuestion(int id){
		db.delete(DbHelper.TABLE_RESPOSTAS, DbHelper.COLUMN_ID+" = " + id, null); 
	}
	
	public boolean setAllQuestionsToUnplayed() {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_ACESSADO, "N");
        return db.update(DbHelper.TABLE_RESPOSTAS, values, DbHelper.COLUMN_ID + ">=" + 1, null) > 0;
    }
	
	public boolean setQuestionToPlayed(int id) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_ACESSADO, "S");
        return db.update(DbHelper.TABLE_RESPOSTAS, values, DbHelper.COLUMN_ID + "=" + id, null) > 0;
    }
	
	/* get questions methods */
	
	public Cursor getAllQuestions(){
		return db.rawQuery("SELECT " + DbHelper.COLUMN_ID + "," 
	        + DbHelper.COLUMN_RESPOSTA + "," 
			+ DbHelper.COLUMN_PERGUNTA 
			+ " FROM " + DbHelper.TABLE_RESPOSTAS, null);
	}
	
	public Cursor getAllEasyQuestions(){
		return db.rawQuery("SELECT " + DbHelper.COLUMN_ID + "," 
	        + DbHelper.COLUMN_RESPOSTA + "," 
			+ DbHelper.COLUMN_PERGUNTA 
			+ " FROM " + DbHelper.TABLE_RESPOSTAS
			+ " WHERE " + DbHelper.COLUMN_DIFICULDADE  + " = " + DifficultyLevel.FACIL.getValue(), null);
	}
	
	public Cursor getAllHardQuestions(){
		return db.rawQuery("SELECT " + DbHelper.COLUMN_ID + "," 
	        + DbHelper.COLUMN_RESPOSTA + "," 
			+ DbHelper.COLUMN_PERGUNTA 
			+ " FROM " + DbHelper.TABLE_RESPOSTAS
			+ " WHERE " + DbHelper.COLUMN_DIFICULDADE  + " = " + DifficultyLevel.DIFICIL.getValue(), null);
	}
	
	public Cursor getAllOrthoQuestions() {
		return db.rawQuery("SELECT " + DbHelper.COLUMN_ID + ","
				+ DbHelper.COLUMN_RESPOSTA + ","
				+ DbHelper.COLUMN_PERGUNTA 
				+ " FROM " + DbHelper.TABLE_RESPOSTAS
				+ " WHERE " + DbHelper.COLUMN_TIPO_JOGO + " = " + TipoLevel.ORTOGRAFICO.getValue(), null);
	}
	
	public Cursor getAllSinonQuestions() {
		return db.rawQuery("SELECT " + DbHelper.COLUMN_ID + ","
				+ DbHelper.COLUMN_RESPOSTA + ","
				+ DbHelper.COLUMN_PERGUNTA
				+ " FROM " + DbHelper.TABLE_RESPOSTAS
				+ " WHERE " + DbHelper.COLUMN_TIPO_JOGO + " = " + TipoLevel.SINONIMO.getValue(), null);
	}
	
	public Cursor getAllUnplayedQuestions(){
		return db.rawQuery("SELECT " + DbHelper.COLUMN_ID + "," 
	        + DbHelper.COLUMN_RESPOSTA + "," 
			+ DbHelper.COLUMN_PERGUNTA 
			+ " FROM " + DbHelper.TABLE_RESPOSTAS
			+ " WHERE " + DbHelper.COLUMN_ACESSADO + " = 'N'; ", null);
	}
	
	public Cursor getARandomUnplayedQuestion(){
		return db.rawQuery("SELECT " + DbHelper.COLUMN_ID + "," 
	        + DbHelper.COLUMN_PERGUNTA + "," 
			+ DbHelper.COLUMN_RESPOSTA + "," 
			+ DbHelper.COLUMN_LETRA_FALTA_POS + ","
			+ DbHelper.COLUMN_ACESSADO 
			+ " FROM " + DbHelper.TABLE_RESPOSTAS
			+ " WHERE " + DbHelper.COLUMN_ACESSADO + " = 'N' "
			+ " ORDER BY random() LIMIT 1", null);
	}
	
	public Cursor getARandomUnplayedQuestion(int dificulty, int tipo, int lingua){
		
		
		Cursor c = db.rawQuery("SELECT " + DbHelper.COLUMN_ID + "," 
	        + DbHelper.COLUMN_PERGUNTA + "," 
			+ DbHelper.COLUMN_RESPOSTA + "," 
			+ DbHelper.COLUMN_LETRA_FALTA_POS + ","
			+ DbHelper.COLUMN_ACESSADO 
			+ " FROM " + DbHelper.TABLE_RESPOSTAS
			+ " WHERE " + DbHelper.COLUMN_ACESSADO + " = 'N' "
			+ " AND " + DbHelper.COLUMN_DIFICULDADE + " = " + dificulty
			+ " AND " + DbHelper.COLUMN_TIPO_JOGO + " = " + tipo
			+ " AND " + DbHelper.COLUMN_TIPO_LINGUAGEM + " = " + lingua
			+ " ORDER BY random() LIMIT 1", null);
		
		/*Cursor c = db.rawQuery("SELECT * "
				+ " FROM " + DbHelper.TABLE_RESPOSTAS
				+ " WHERE " + DbHelper.COLUMN_TIPO_LINGUAGEM + " = " + lingua
				+ " ORDER BY random()", null);
		
		while (c.moveToNext()) {  
		      
		      c.getString(c.getColumnIndex("pergunta"));
		      c.getString(c.getColumnIndex("resposta"));
		      c.getString(c.getColumnIndex("acessado"));
		      c.getString(c.getColumnIndex("tipodelinguagem"));
		      
		   }  */
		
		return c;
	}
	
	/* Criação do Spinner para preencher o activity */
	
	public List<SpinnerClass> getAllDificulty(){
		List<SpinnerClass> labels = new ArrayList<SpinnerClass>();
		String selectQuery = "SELECT * FROM " + DbHelper.TABLE_DIFICULDADE;				
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add ( new SpinnerClass ( cursor.getInt(0) , cursor.getString(1) ) );
            }
            while (cursor.moveToNext());
        }        
        cursor.close();
        return labels;
	}
	
	public List<SpinnerClass> getAllType() {
		List<SpinnerClass> labels = new ArrayList<SpinnerClass>();
		String selectQuerry = "SELECT * FROM " + DbHelper.TABLE_TIPO_JOGO;
		Cursor cursor = db.rawQuery(selectQuerry, null);
		
		if (cursor.moveToFirst()) {
			do {
				labels.add(new SpinnerClass (cursor.getInt(0), cursor.getString(1)));
			}
			while (cursor.moveToNext());
		}
		cursor.close();
		return labels;
	}
	
	public List<SpinnerClass> getAllLanguages() {
		List<SpinnerClass> labels = new ArrayList<SpinnerClass>();
		String selectQuerry = "SELECT * FROM " + DbHelper.TABLE_TIPO_LINGUAGEM;
		Cursor cursor = db.rawQuery(selectQuerry, null);
		
		if (cursor.moveToFirst()) {
			do {
				labels.add(new SpinnerClass (cursor.getInt(0), cursor.getString(1)));
			}
			while (cursor.moveToNext());
		}
		cursor.close();
		return labels;
	}
	
	/* abrir e fechar o banco de dados padrão */
	
	public void open() throws SQLException {
	    db = helper.getWritableDatabase();
	}
	
	public void close() {
		helper.close();
	}
	
	/**
	 * getAnswers
	 *     Get all answers from database
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
    public void getRandomQuestion(int dificulty, int tipo, int lingua){
    	MainFunctions.questionId = 0;
    	MainFunctions.question = "";
    	MainFunctions.answer = "";
    	MainFunctions.selectedAnswerLength = 0;
    	MainFunctions.missingChar = "";
    	MainFunctions.missingCharPos = 0;  	
	   	
	   	Cursor cursor = getARandomUnplayedQuestion(dificulty,tipo,lingua);
	   	
	   	if (cursor.moveToFirst()) {
	   		
	   		if(lingua == TipoLanguage.PT.getValue()) {
		   		if (tipo == TipoLevel.ORTOGRAFICO.getValue()) {
		   			MainFunctions.tipoLevel = 1;
		   			MainFunctions.tipolingua = 1;
			   		MainFunctions.questionId = cursor.getInt(0);
					MainFunctions.question = cursor.getString(1);
					MainFunctions.answer = cursor.getString(2);			
					MainFunctions.selectedAnswerLength = MainFunctions.answer.length();
								
					if (dificulty == DifficultyLevel.FACIL.getValue()){
						MainFunctions.missingCharPos = Integer.parseInt( cursor.getString(3) );
						MainFunctions.missingChar = Character.toString ( MainFunctions.answer.charAt(MainFunctions.missingCharPos - 1) );
						}
		   		} else {
				MainFunctions.tipoLevel = 2;
				MainFunctions.tipolingua = 1;
			   	MainFunctions.questionId = cursor.getInt(0);
				MainFunctions.question = cursor.getString(1);
				MainFunctions.answer = cursor.getString(2);			
				MainFunctions.selectedAnswerLength = MainFunctions.answer.length();
					if (dificulty == DifficultyLevel.FACIL.getValue()){
						MainFunctions.missingCharPos = Integer.parseInt( cursor.getString(3) );
						MainFunctions.missingChar = Character.toString ( MainFunctions.answer.charAt(MainFunctions.missingCharPos - 1) );
					}
		   		}
	   		} else if (lingua == TipoLanguage.ES.getValue()) {
	   			if (tipo == TipoLevel.ORTOGRAFICO.getValue()) {
		   			MainFunctions.tipoLevel = 1;
		   			MainFunctions.tipolingua = 2;
			   		MainFunctions.questionId = cursor.getInt(0);
					MainFunctions.question = cursor.getString(1);
					MainFunctions.answer = cursor.getString(2);			
					MainFunctions.selectedAnswerLength = MainFunctions.answer.length();
								
					if (dificulty == DifficultyLevel.FACIL.getValue()){
						MainFunctions.missingCharPos = Integer.parseInt( cursor.getString(3) );
						MainFunctions.missingChar = Character.toString ( MainFunctions.answer.charAt(MainFunctions.missingCharPos - 1) );
						}
		   		} else {
				MainFunctions.tipoLevel = 2;
				MainFunctions.tipolingua = 2;
			   	MainFunctions.questionId = cursor.getInt(0);
				MainFunctions.question = cursor.getString(1);
				MainFunctions.answer = cursor.getString(2);			
				MainFunctions.selectedAnswerLength = MainFunctions.answer.length();
					if (dificulty == DifficultyLevel.FACIL.getValue()){
						MainFunctions.missingCharPos = Integer.parseInt( cursor.getString(3) );
						MainFunctions.missingChar = Character.toString ( MainFunctions.answer.charAt(MainFunctions.missingCharPos - 1) );
					}
		   		}
	   		} else if (lingua == TipoLanguage.EN.getValue()) {
	   			if (tipo == TipoLevel.ORTOGRAFICO.getValue()) {
		   			MainFunctions.tipoLevel = 1;
		   			MainFunctions.tipolingua = 3;
			   		MainFunctions.questionId = cursor.getInt(0);
					MainFunctions.question = cursor.getString(1);
					MainFunctions.answer = cursor.getString(2);			
					MainFunctions.selectedAnswerLength = MainFunctions.answer.length();
								
					if (dificulty == DifficultyLevel.FACIL.getValue()){
						MainFunctions.missingCharPos = Integer.parseInt( cursor.getString(3) );
						MainFunctions.missingChar = Character.toString ( MainFunctions.answer.charAt(MainFunctions.missingCharPos - 1) );
						}
		   		} else {
				MainFunctions.tipoLevel = 2;
				MainFunctions.tipolingua = 3;
			   	MainFunctions.questionId = cursor.getInt(0);
				MainFunctions.question = cursor.getString(1);
				MainFunctions.answer = cursor.getString(2);			
				MainFunctions.selectedAnswerLength = MainFunctions.answer.length();
					if (dificulty == DifficultyLevel.FACIL.getValue()){
						MainFunctions.missingCharPos = Integer.parseInt( cursor.getString(3) );
						MainFunctions.missingChar = Character.toString ( MainFunctions.answer.charAt(MainFunctions.missingCharPos - 1) );
					}
		   		}
	   		}
			
	   	
	   		setQuestionToPlayed(MainFunctions.questionId);
			
			/* Fill the list with some chars from answer */
			MainFunctions.answerCharList.clear();
			for (int i = 0; i < MainFunctions.selectedAnswerLength; i++){
				if (dificulty == DifficultyLevel.FACIL.getValue()){
					if (i == MainFunctions.missingCharPos - 1){
						MainFunctions.answerCharList.add("_");
					}
					else{
						MainFunctions.answerCharList.add( Character.toString(MainFunctions.answer.charAt(i)) );
					}
				}
				else{
					MainFunctions.answerCharList.add("_");
				}
			}
			
			Log.i(TAG, "questionId = " + MainFunctions.questionId);
			Log.i(TAG, "question = " + MainFunctions.question);
			Log.i(TAG, "answer = " + MainFunctions.answer);
			Log.i(TAG, "selectedAnswerLength = " + MainFunctions.selectedAnswerLength);
			Log.i(TAG, "missingCharPos = " + MainFunctions.missingCharPos);
			Log.i(TAG, "missingChar = " + MainFunctions.missingChar);
			Log.i(TAG, "StringList = " + MainFunctions.answerCharList);
		}
	   	cursor.close();
    }
}

