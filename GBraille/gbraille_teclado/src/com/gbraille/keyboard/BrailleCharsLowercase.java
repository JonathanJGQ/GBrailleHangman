package com.gbraille.keyboard;

public class BrailleCharsLowercase extends AbstractBrailleChars {
	public BrailleCharsLowercase(){
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
		
		map.putUniqueKey(braille.getBraille_100000(), "a"); //A
		map.putUniqueKey(braille.getBraille_110000(), "b"); //B
		map.putUniqueKey(braille.getBraille_100100(), "c"); //C
		map.putUniqueKey(braille.getBraille_100110(), "d"); //D
		map.putUniqueKey(braille.getBraille_100010(), "e"); //E
		map.putUniqueKey(braille.getBraille_110100(), "f"); //F
		map.putUniqueKey(braille.getBraille_110110(), "g"); //G
		map.putUniqueKey(braille.getBraille_110010(), "h"); //H
		map.putUniqueKey(braille.getBraille_010100(), "i"); //I
		map.putUniqueKey(braille.getBraille_010110(), "j"); //J
		map.putUniqueKey(braille.getBraille_101000(), "k"); //K
		map.putUniqueKey(braille.getBraille_111000(), "l"); //L
		map.putUniqueKey(braille.getBraille_101100(), "m"); //M
		map.putUniqueKey(braille.getBraille_101110(), "n"); //N		
		map.putUniqueKey(braille.getBraille_101010(), "o"); //O
		map.putUniqueKey(braille.getBraille_111100(), "p"); //P
		map.putUniqueKey(braille.getBraille_111110(), "q"); //Q
		map.putUniqueKey(braille.getBraille_111010(), "r"); //R		
		map.putUniqueKey(braille.getBraille_011100(), "s"); //S
		map.putUniqueKey(braille.getBraille_011110(), "t"); //T
		map.putUniqueKey(braille.getBraille_101001(), "u"); //U		
		map.putUniqueKey(braille.getBraille_111001(), "v"); //V
		map.putUniqueKey(braille.getBraille_010111(), "w"); //W
		map.putUniqueKey(braille.getBraille_101101(), "x"); //X
		map.putUniqueKey(braille.getBraille_101111(), "y"); //Y
		map.putUniqueKey(braille.getBraille_101011(), "z"); //Z			
		map.putUniqueKey(braille.getBraille_111011(), "\u00E1"); //a acute
		map.putUniqueKey(braille.getBraille_111111(), "\u00E9"); //e acute
		map.putUniqueKey(braille.getBraille_001100(), "\u00ED"); //i acute
		map.putUniqueKey(braille.getBraille_001101(), "\u00F3"); //o acute
		map.putUniqueKey(braille.getBraille_011111(), "\u00FA"); //u acute			
		map.putUniqueKey(braille.getBraille_110101(), "\u00E0"); //a grave			
		map.putUniqueKey(braille.getBraille_100001(), "\u00E2"); //a circ
		map.putUniqueKey(braille.getBraille_110001(), "\u00EA"); //e circ
		map.putUniqueKey(braille.getBraille_100111(), "\u00F4"); //o circ			
		map.putUniqueKey(braille.getBraille_001110(), "\u00E3"); //a tilde
		map.putUniqueKey(braille.getBraille_010101(), "\u00F5"); //o tilde			
		map.putUniqueKey(braille.getBraille_110011(), "\u00FC"); //u diaeresis (trema)			
		map.putUniqueKey(braille.getBraille_111101(), "\u00E7"); // cedilla
		map.putUniqueKey(braille.getBraille_001000(), "."); //period
		map.putUniqueKey(braille.getBraille_100011(), "@"); //at
		map.putUniqueKey(braille.getBraille_010010(), ":"); //colon
		map.putUniqueKey(braille.getBraille_011011(), "="); //equals		
		map.putUniqueKey(braille.getBraille_011010(), "+"); //plus
		map.putUniqueKey(braille.getBraille_001001(), "-"); //hifen
		map.putUniqueKey(braille.getBraille_010001(), "?"); //question mark
		map.putUniqueKey(braille.getBraille_011000(), ";"); //semicolon
		map.putUniqueKey(braille.getBraille_011101(), "~"); //tilde
		map.putUniqueKey(braille.getBraille_010000(), ","); //comma
		map.putUniqueKey(braille.getBraille_001010(), "*"); //star
	}
}