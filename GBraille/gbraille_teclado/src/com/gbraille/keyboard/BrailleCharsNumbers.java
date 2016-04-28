package com.gbraille.keyboard;

public class BrailleCharsNumbers extends AbstractBrailleChars {
	public BrailleCharsNumbers(){
		createMap();
	}
	
	void createMap(){
		map.clear();
		
		// white space
		map.putUniqueKey(braille.getBraille_000000(), "ESPACO");
				
		// prefix
		map.putUniqueKey(braille.getBraille_001111(), "NUMERO");
		map.putUniqueKey(braille.getBraille_000101(), "MAIUSCULA");
		map.putUniqueKey(braille.getBraille_000010(), "TRANSLINEACAO");
		map.putUniqueKey(braille.getBraille_000011(), "RESTITUIDOR");
				
		map.putUniqueKey(braille.getBraille_100000(), "1"); //1
		map.putUniqueKey(braille.getBraille_110000(), "2"); //2
		map.putUniqueKey(braille.getBraille_100100(), "3"); //3
		map.putUniqueKey(braille.getBraille_100110(), "4"); //4
		map.putUniqueKey(braille.getBraille_100010(), "5"); //5
		map.putUniqueKey(braille.getBraille_110100(), "6"); //6
		map.putUniqueKey(braille.getBraille_110110(), "7"); //7
		map.putUniqueKey(braille.getBraille_110010(), "8"); //8
		map.putUniqueKey(braille.getBraille_010100(), "9"); //9
		map.putUniqueKey(braille.getBraille_010110(), "0"); //0
		
		map.putUniqueKey(braille.getBraille_101000(), "k"); //k
		map.putUniqueKey(braille.getBraille_111000(), "l"); //l
		map.putUniqueKey(braille.getBraille_101100(), "m"); //m
		map.putUniqueKey(braille.getBraille_101110(), "n"); //n		
		map.putUniqueKey(braille.getBraille_101010(), "o"); //o
		map.putUniqueKey(braille.getBraille_111100(), "p"); //p
		map.putUniqueKey(braille.getBraille_111110(), "q"); //q
		map.putUniqueKey(braille.getBraille_111010(), "r"); //r
		map.putUniqueKey(braille.getBraille_011100(), "s"); //s
		map.putUniqueKey(braille.getBraille_011110(), "t"); //t
		map.putUniqueKey(braille.getBraille_101001(), "u"); //u		
		map.putUniqueKey(braille.getBraille_111001(), "v"); //v
		map.putUniqueKey(braille.getBraille_010111(), "w"); //w
		map.putUniqueKey(braille.getBraille_101101(), "x"); //x
		map.putUniqueKey(braille.getBraille_101111(), "y"); //y
		map.putUniqueKey(braille.getBraille_101011(), "z"); //z
		map.putUniqueKey(braille.getBraille_001000(), "."); //ponto final
		map.putUniqueKey(braille.getBraille_100011(), "@"); //arroba
		map.putUniqueKey(braille.getBraille_010010(), ":"); //dois pontos
		map.putUniqueKey(braille.getBraille_011011(), "="); //igual		
		map.putUniqueKey(braille.getBraille_011010(), "+"); //mais
		map.putUniqueKey(braille.getBraille_001001(), "-"); //hifen
		map.putUniqueKey(braille.getBraille_010001(), "?"); //interrogacao
		map.putUniqueKey(braille.getBraille_011000(), ";"); //ponto e virgula
		map.putUniqueKey(braille.getBraille_011101(), "~"); //til
		map.putUniqueKey(braille.getBraille_010000(), ","); //virgula
		map.putUniqueKey(braille.getBraille_001010(), "*"); //asterisco
	}	
}