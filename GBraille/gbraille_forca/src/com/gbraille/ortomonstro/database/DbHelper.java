package com.gbraille.ortomonstro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	public static String DATABASE_NAME = "gbrailleForca.db";
	private static int DATABASE_VERSION = 1;
	
	public static String TABLE_RESPOSTAS = "respostas";
	public static String TABLE_DIFICULDADE = "dificuldade";
	public static String TABLE_TIPO_JOGO = "jogo";
	public static String TABLE_PONTUACAO = "pontuacao";
	public static String TABLE_INSTALACAO = "instalacao";
	public static String TABLE_TIPO_LINGUAGEM = "linguagem";
	public static String TABLE_ATIVIDADES = "atividades";
	
	/* table dificuldade */
	public static String COLUMN_DIFICULDADE = "dificuldade";
	
	/* table tipo de jogo*/
	
	public static String COLUMN_TIPO_JOGO = "tipodejogo";
	
	/* table linguagens */
	
	public static String COLUMN_TIPO_LINGUAGEM = "tipodelinguagem";
	
	/* table respostas */
	
	public static String COLUMN_ID = "_id";	
	public static String COLUMN_PERGUNTA = "pergunta";
	public static String COLUMN_RESPOSTA = "resposta";	
	public static String COLUMN_LETRA_FALTA_POS = "letrafaltapos";
	public static String COLUMN_ACESSADO = "acessado";
	
	/* table atividades*/
	
	public static String CODE = "codigo";
	public static String NAME = "nome";
	public static String DATE_INITIAL = "dataInicial";
	public static String DATE_FINAL = "dataFinal";
	public static String NUMBER_OF_QUESTIONS = "numeroDeQuestoes";
	public static String DESCRIPTION = "descricao";
	public static String LEVEL = "level";
	
	/* table pontuacao */
	
	public static String COLUMN_PONTUACAO = "pontuacao";
	
	/* table instalacao */
	
	public static String COLUMN_INSTALADO = "instalado";
		
	/* criação de tabelas */
	
	private static String CREATE_TABLE_ATIVIDADES = "create table " + TABLE_ATIVIDADES
			+ "( "
			+ CODE + " INTEGER PRIMARY KEY, "
			+ NAME + " VARCHAR(255), "
			+ DATE_INITIAL + " VARCHAR(255), "
			+ DATE_FINAL + " VARCHAR(255), "
			+ NUMBER_OF_QUESTIONS + " VARCHAR(255), "
			+ DESCRIPTION + " VARCHAR(255), "
			+ LEVEL + " VARCHAR(255) ";
	
	private static String CREATE_TABLE_INSTALACAO = "create table " + TABLE_INSTALACAO 
			+ "( " 
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_INSTALADO + " VARCHAR(255) "
			+ ");";
	
	private static String CREATE_TABLE_DIFICULDADE = "create table " + TABLE_DIFICULDADE 
			+ "( " 
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_DIFICULDADE + " VARCHAR(255) "
			+ ");";
	
	private static String CREATE_TABLE_TIPO_JOGO = "create table " + TABLE_TIPO_JOGO 
			+ "( " 
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_TIPO_JOGO + " VARCHAR(255) "
			+ ");";
	
	private static String CREATE_TABLE_TIPO_LINGUAGEM = " create table " + TABLE_TIPO_LINGUAGEM
			+ "( "
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_TIPO_LINGUAGEM + " VARCHAR(255) "
			+ ");";
	
	private static String CREATE_TABLE_RESPOSTAS = "create table " + TABLE_RESPOSTAS 
			+ "( " 
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_PERGUNTA + " VARCHAR(255), "
			+ COLUMN_RESPOSTA + " VARCHAR(255), "
			+ COLUMN_LETRA_FALTA_POS + " VARCHAR(2), "
			+ COLUMN_DIFICULDADE + " INTEGER REFERENCES " + TABLE_DIFICULDADE + "(" + COLUMN_ID + "), "
			+ COLUMN_TIPO_JOGO + " INTEGER REFERENCES " + TABLE_TIPO_JOGO + "(" + COLUMN_ID + "), "
			+ COLUMN_TIPO_LINGUAGEM + " INTEGER REFERENCES " + TABLE_TIPO_LINGUAGEM + "(" + COLUMN_ID + "), "
			+ COLUMN_ACESSADO + " VARCHAR(1) " 
			+ ");";
	
	/* funções de inserção */
	
	private static final String INSERT_INSTALACAO_NAO = "insert into " 
		    + TABLE_INSTALACAO + " (" 
		    + COLUMN_ID + ", "		         
		    + COLUMN_INSTALADO + ") values (1,'N')";
	
	
	private static final String INSERT_DIFICULDADE_FACIL = "insert into " 
		    + TABLE_DIFICULDADE + " (" 
		    + COLUMN_ID + ", "		         
		    + COLUMN_DIFICULDADE + ") values (1,'facil')";
	
	private static final String INSERT_DIFICULDADE_DIFICIL = "insert into " 
		    + TABLE_DIFICULDADE + " (" 
		    + COLUMN_ID + ", "		         
		    + COLUMN_DIFICULDADE + ") values (2,'dificil')";
	
	private static final String INSERT_TIPO_JOGO_ORTHO = "insert into "
			+ TABLE_TIPO_JOGO + " ("
			+ COLUMN_ID + ", "
			+ COLUMN_TIPO_JOGO + ") values (1, 'ortográfico')";
	
	private static final String INSERT_TIPO_JOGO_SIN = "insert into "
			+ TABLE_TIPO_JOGO + " ("
			+ COLUMN_ID + ", "
			+ COLUMN_TIPO_JOGO + ") values (2, 'sinônimo')";
	
	private static final String INSERT_TIPO_LINGUAGEM_PT = "insert into "
			+ TABLE_TIPO_LINGUAGEM + " ("
			+ COLUMN_ID + ", "
			+ COLUMN_TIPO_LINGUAGEM + ") values (1, 'pt')";
	
	private static final String INSERT_TIPO_LINGUAGEM_ES = "insert into "
			+ TABLE_TIPO_LINGUAGEM + " ("
			+ COLUMN_ID + ", "
			+ COLUMN_TIPO_LINGUAGEM + ") values (2, 'es')";
	
	private static final String INSERT_TIPO_LINGUAGEM_EN = "insert into "
			+ TABLE_TIPO_LINGUAGEM + " ("
			+ COLUMN_ID + ", "
			+ COLUMN_TIPO_LINGUAGEM + ") values (3, 'en')";
	
	/* Criação/inserção tabela potuação */
	
	private static String CREATE_TABLE_PONTUACAO = "create table " + TABLE_PONTUACAO 
			+ "( " 
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_DIFICULDADE + " INTEGER REFERENCES " + TABLE_DIFICULDADE + "(" + COLUMN_ID + "), "
			+ COLUMN_PONTUACAO + " INTEGER "
			+ ");";
	
	private static final String INSERT_PONTUACAO_FACIL = "insert into " 
		    + TABLE_PONTUACAO + " (" 
		    + COLUMN_ID + ", "
		    + COLUMN_DIFICULDADE + ", "
		    + COLUMN_PONTUACAO + ") values (1,1,0)";
	
	private static final String INSERT_PONTUACAO_DIFICIL = "insert into " 
		    + TABLE_PONTUACAO + " (" 
		    + COLUMN_ID + ", "
		    + COLUMN_DIFICULDADE + ", "
		    + COLUMN_PONTUACAO + ") values (2,2,0)";
	
	public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_DIFICULDADE);
		db.execSQL(INSERT_DIFICULDADE_FACIL);
		db.execSQL(INSERT_DIFICULDADE_DIFICIL);	
		db.execSQL(CREATE_TABLE_TIPO_JOGO);
		db.execSQL(INSERT_TIPO_JOGO_ORTHO);
		db.execSQL(INSERT_TIPO_JOGO_SIN);
		db.execSQL(CREATE_TABLE_TIPO_LINGUAGEM);
		db.execSQL(INSERT_TIPO_LINGUAGEM_PT);
		db.execSQL(INSERT_TIPO_LINGUAGEM_ES);
		db.execSQL(INSERT_TIPO_LINGUAGEM_EN);
		db.execSQL(CREATE_TABLE_RESPOSTAS);	
		db.execSQL(CREATE_TABLE_PONTUACAO);
		db.execSQL(INSERT_PONTUACAO_FACIL);
		db.execSQL(INSERT_PONTUACAO_DIFICIL);
		db.execSQL(CREATE_TABLE_INSTALACAO);
		db.execSQL(INSERT_INSTALACAO_NAO);
		db.execSQL(CREATE_TABLE_ATIVIDADES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 Log.i(DbHelper.class.getName(),"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIFICULDADE);
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_JOGO);
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_LINGUAGEM);
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESPOSTAS);
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_PONTUACAO);
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTALACAO);
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATIVIDADES);
		 onCreate(db);
	}
	
	public SQLiteDatabase getDatabase() {
	    return this.getWritableDatabase();
	}
}