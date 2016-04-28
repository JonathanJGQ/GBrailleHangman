package com.gbraille.keyboard.database;

import android.database.sqlite.SQLiteDatabase;

public class DicionarioPTBRLetraW {
	public DicionarioPTBRLetraW(DbHelper dbh, SQLiteDatabase db){
		dicionarioLetraW(dbh, db);
	}
	
	/* obtido de http://idnael.pegada.net/wikcionario/bd/ - está finalizado */
	private void dicionarioLetraW(DbHelper dbh, SQLiteDatabase db){
		db.execSQL( dbh.createInsertDicionario("w", "a vigésima terceira letra do alfabeto latino. símbolo do elemento químico de nome tungstênio. unidade de medida de potência Watt") );
		db.execSQL( dbh.createInsertDicionario("Wagner", "município brasileiro do estado da Bahia") );
		db.execSQL( dbh.createInsertDicionario("wagnerismo", "sistema musical de Wagner.") );
		db.execSQL( dbh.createInsertDicionario("Wall Ferraz", "município brasileiro do estado do Piauí") );
		db.execSQL( dbh.createInsertDicionario("Wallis e Futuna", "território francês situado no Oceano Pacífico, cuja capital é Mata-Utu") );
		db.execSQL( dbh.createInsertDicionario("Wanderlândia", "município brasileiro do estado do Tocantins") );
		db.execSQL( dbh.createInsertDicionario("Wanderley", "município brasileiro do estado da Bahia") );
		db.execSQL( dbh.createInsertDicionario("waray-waray", "língua malaio-polinésia falada nas Filipinas pela etnia visaya, nas ilhas de of Samar, Biliran, e no norte de Leyte.") );
		db.execSQL( dbh.createInsertDicionario("warfarina", "fármaco do grupo dos anticoagulantes, que é usado na prevenção das tromboses; também usado em altas doses como veneno para roedores") );
		db.execSQL( dbh.createInsertDicionario("warrant", " título emitido por estabelecimentos encarregados da guarda e conservação de mercadorias, passível de ser vendido ou negociado, e que atesta ao seu portador a propriedade do objeto em custódia.") );
		db.execSQL( dbh.createInsertDicionario("Washington", "estado dos Estados Unidos da América, banhado pelo oceano Pacífico, faz fronteira com a província canadiana da Colúmbia Britânica e confina com os estados do Idaho e Oregon; a capital é Olimpia") );
		db.execSQL( dbh.createInsertDicionario("Washington DC", "cidade capital dos Estados Unidos da América") );
		db.execSQL( dbh.createInsertDicionario("washingtoniano", "de ou relativo a Washington, cidade dos Estados Unidos") );
		db.execSQL( dbh.createInsertDicionario("watt", "unidade do Sistema Internacional de Unidades utilizada para potência") );
		db.execSQL( dbh.createInsertDicionario("wattímetro", "instrumento de medida de potência elétrica fornecida ou dissipada por um elemento") );
		db.execSQL( dbh.createInsertDicionario("watubela", "língua da Indonésia") );
		db.execSQL( dbh.createInsertDicionario("webmail", "serviço de e-mail acessível de um navegador de Web") );
		db.execSQL( dbh.createInsertDicionario("webmaster", "profissional que administra um website.") );
		db.execSQL( dbh.createInsertDicionario("wellingtoniano", "de ou relativo à Wellington, cidade da Nova Zelândia") );
		db.execSQL( dbh.createInsertDicionario("Wenceslau Braz", "município brasileiro do estado de Minas Gerais. município brasileiro do estado do Paraná.") );
		db.execSQL( dbh.createInsertDicionario("Wenceslau Guimarães", "município brasileiro do estado da Bahia") );
		db.execSQL( dbh.createInsertDicionario("Westfália", "município brasileiro do estado do Rio Grande do Sul. região da República Federal da Alemanha.") );
		db.execSQL( dbh.createInsertDicionario("whisky", "bebida alcoólica destilada de grãos, muitas vezes incluindo malte, e envelhecida em barris de carvalho.") );
		db.execSQL( dbh.createInsertDicionario("wik-mungkan", "idioma aborígene falado no norte de Cape York (Queensland, Austrália)") );
		db.execSQL( dbh.createInsertDicionario("wildiano", "relativo a Oscar Wilde, escritor irlandês.") );
		db.execSQL( dbh.createInsertDicionario("Windhoek", "capital da Namíbia") );
		db.execSQL( dbh.createInsertDicionario("wipfel", "copa, topo de árvore.") );
		db.execSQL( dbh.createInsertDicionario("Wisconsin", "Estado dos Estados Unidos da América, banhado pelo lago Superior e pelo lago Michigan, confina com os estados do Illinois, Michigan, Iowa e Minnesota. A capital é Madison.") );
		db.execSQL( dbh.createInsertDicionario("Witmarsum", "município brasileiro do estado de Santa Catarina") );
		db.execSQL( dbh.createInsertDicionario("wolfrâmio", "elemento químico de símbolo W, também conhecido como tungstênio.") );
		db.execSQL( dbh.createInsertDicionario("won", "moeda tanto da Coreia do Norte (simbolizada por Wn) como da Coreia do Sul (simbolizada por W)") );
		db.execSQL( dbh.createInsertDicionario("writ", "mandado") );
		db.execSQL( dbh.createInsertDicionario("wronskiano", "referente ou pertencente ao matemático polonês Józef Maria Hoene-Wronski ou aos seus trabalhos em matemática.") );
		db.execSQL( dbh.createInsertDicionario("wuvulu-aua", "língua malaio-polinésia, que, juntamente com o kaniet e seimat, constitui o grupo ocidental dos idiomas das Ilhas do Almirantado (Papua-Nova Guinè).") );
		db.execSQL( dbh.createInsertDicionario("Wyoming", "Estado dos Estados Unidos da América, que confina com os estados do Montana, Dakota do Sul, Nebrasca, Colorado, Utah e Idaho. A capital é Cheyenne.") );
	}
}
