package com.gbraille.keyboard.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {
	public static String DATABASE_NAME = "gbrailleKeyboard.db";
	private static int DATABASE_VERSION = 1;
	
	public static String TABLE_INSTALACAO = "instalacao";
	public static String TABLE_DICIONARIO = "dicionario";
	
	/* table dicionario */
	public static String COLUMN_ID = "_id";	
	public static String COLUMN_PALAVRA = "palavra";
	public static String COLUMN_SIGNIFICADO = "significado";	
	
	/* table instalacao */
	public static String COLUMN_INSTALADO = "instalado";
		
	private static String CREATE_TABLE_INSTALACAO = "create table " + TABLE_INSTALACAO 
			+ "( " 
			+ COLUMN_ID + " INTEGER PRIMARY KEY, "
			+ COLUMN_INSTALADO + " VARCHAR(255) "
			+ ");";
	
	private static String CREATE_TABLE_DICIONARIO = "create table " + TABLE_DICIONARIO 
			+ "( " 
			+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_PALAVRA + " VARCHAR(255), "
			+ COLUMN_SIGNIFICADO + " TEXT "
			+ ");";
		
	private static final String INSERT_INSTALACAO_NAO = "insert into " 
		    + TABLE_INSTALACAO + " (" 
		    + COLUMN_ID + ", "		         
		    + COLUMN_INSTALADO + ") values (1,'N')";
	
	public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public String createInsertDicionario(String palavra, String significado){
		String str = "insert into " 
			    + TABLE_DICIONARIO + " (" 
			    + COLUMN_PALAVRA + ", "
			    + COLUMN_SIGNIFICADO + ") values ('" + palavra + "','" + significado + "')";
		return str;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_INSTALACAO);
		db.execSQL(INSERT_INSTALACAO_NAO);		
		db.execSQL(CREATE_TABLE_DICIONARIO);

		Log.i(DbHelper.class.getName(),"Creating database");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 Log.i(DbHelper.class.getName(),"Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTALACAO);
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_DICIONARIO);		 
		 onCreate(db);
	}
	
	public SQLiteDatabase getDatabase() {
	    return this.getWritableDatabase();
	}	
}