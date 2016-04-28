package com.gbraille.keyboard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DbAdapter {
	private SQLiteDatabase db;
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
	
	/* instalar o dicionario */
	public void instalarDicionario(){
		new DicionarioPTBRLetraA(helper, db);
		new DicionarioPTBRLetraW(helper, db);
		new DicionarioPTBRLetraX(helper, db);
		new DicionarioPTBRLetraY(helper, db);
		new DicionarioPTBRLetraZ(helper, db);
		updateInstalacao("S");
	}
	
	/* get instalacao: S or N */
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
	
	/* get significado dicionario */
	public String getSignificadoDicionario(String palavra){
		Cursor cursor = db.rawQuery("SELECT " 
	        + DbHelper.COLUMN_SIGNIFICADO
			+ " FROM " + DbHelper.TABLE_DICIONARIO
			+ " WHERE " + DbHelper.COLUMN_PALAVRA  + " = '" + palavra +"'", null);
		cursor.moveToFirst();
		String value = cursor.getString(0);
		cursor.close();
		return value;
	}
	
	public void open() throws SQLException {
	    db = helper.getWritableDatabase();
	}
	
	public void close() {
		helper.close();
	}
}