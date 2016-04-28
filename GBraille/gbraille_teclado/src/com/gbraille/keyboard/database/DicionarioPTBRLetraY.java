package com.gbraille.keyboard.database;

import android.database.sqlite.SQLiteDatabase;

public class DicionarioPTBRLetraY {
	public DicionarioPTBRLetraY(DbHelper dbh, SQLiteDatabase db){
		dicionarioLetraY(dbh, db);
	}
	
	private void dicionarioLetraY(DbHelper dbh, SQLiteDatabase db){
		db.execSQL( dbh.createInsertDicionario("y", "a vigésima quinta letra do alfabeto latino") );
		db.execSQL( dbh.createInsertDicionario("yakisoba", "macarrão com verduras e carne preparado em frigideira ou chapa quente.") );
		db.execSQL( dbh.createInsertDicionario("yamdena", "idioma, do grupo malaio-polinésio, falado na ilha de Yamdena.") );
		db.execSQL( dbh.createInsertDicionario("Yamoussoukro", "capital da Costa do Marfim") );
		db.execSQL( dbh.createInsertDicionario("Yaoundé", "capital dos Camarões") );
		db.execSQL( dbh.createInsertDicionario("yorkshire", "raça de cachorro") );
		db.execSQL( dbh.createInsertDicionario("yorkshiriano", "de ou relativo à Yorkshire, região do Reino Unido") );
	}
}
