package com.gbraille.keyboard;

public class BrailleCharsUppercase extends AbstractBrailleChars {
	public BrailleCharsUppercase(){
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
		
		map.putUniqueKey(braille.getBraille_100000(), "A"); //A
		map.putUniqueKey(braille.getBraille_110000(), "B"); //B
		map.putUniqueKey(braille.getBraille_100100(), "C"); //C
		map.putUniqueKey(braille.getBraille_100110(), "D"); //D
		map.putUniqueKey(braille.getBraille_100010(), "E"); //E
		map.putUniqueKey(braille.getBraille_110100(), "F"); //F
		map.putUniqueKey(braille.getBraille_110110(), "G"); //G
		map.putUniqueKey(braille.getBraille_110010(), "H"); //H
		map.putUniqueKey(braille.getBraille_010100(), "I"); //I
		map.putUniqueKey(braille.getBraille_010110(), "J"); //J
		map.putUniqueKey(braille.getBraille_101000(), "K"); //K
		map.putUniqueKey(braille.getBraille_111000(), "L"); //L
		map.putUniqueKey(braille.getBraille_101100(), "M"); //M
		map.putUniqueKey(braille.getBraille_101110(), "N"); //N		
		map.putUniqueKey(braille.getBraille_101010(), "O"); //O
		map.putUniqueKey(braille.getBraille_111100(), "P"); //P
		map.putUniqueKey(braille.getBraille_111110(), "Q"); //Q
		map.putUniqueKey(braille.getBraille_111010(), "R"); //R		
		map.putUniqueKey(braille.getBraille_011100(), "S"); //S
		map.putUniqueKey(braille.getBraille_011110(), "T"); //T
		map.putUniqueKey(braille.getBraille_101001(), "U"); //U		
		map.putUniqueKey(braille.getBraille_111001(), "V"); //V
		map.putUniqueKey(braille.getBraille_010111(), "W"); //W
		map.putUniqueKey(braille.getBraille_101101(), "X"); //X
		map.putUniqueKey(braille.getBraille_101111(), "Y"); //Y
		map.putUniqueKey(braille.getBraille_101011(), "Z"); //Z			
		map.putUniqueKey(braille.getBraille_111011(), "\u00c1"); //A acute
		map.putUniqueKey(braille.getBraille_111111(), "\u00C9"); //E acute
		map.putUniqueKey(braille.getBraille_001100(), "\u00CD"); //I acute
		map.putUniqueKey(braille.getBraille_001101(), "\u00D3"); //O acute
		map.putUniqueKey(braille.getBraille_011111(), "\u00FA"); //U acute		
		map.putUniqueKey(braille.getBraille_110101(), "\u00C0"); // A grave	
		map.putUniqueKey(braille.getBraille_100001(), "\u00C2"); // A circumflex
		map.putUniqueKey(braille.getBraille_110001(), "\u00CA"); // E circumflex
		map.putUniqueKey(braille.getBraille_100111(), "\u00D4"); // O circumflex		
		map.putUniqueKey(braille.getBraille_001110(), "\u00C3"); // A tilde
		map.putUniqueKey(braille.getBraille_010101(), "\u00D5"); // O tilde			
		map.putUniqueKey(braille.getBraille_110011(), "\u00DC"); // u diaeresis (trema)			
		map.putUniqueKey(braille.getBraille_111101(), "\u00C7"); // cedilla
		map.putUniqueKey(braille.getBraille_001000(), "."); //period
		map.putUniqueKey(braille.getBraille_100011(), "@"); //at
		map.putUniqueKey(braille.getBraille_010010(), ":"); //colon
		map.putUniqueKey(braille.getBraille_011011(), "="); //equals		
		map.putUniqueKey(braille.getBraille_011010(), "+"); //plus
		map.putUniqueKey(braille.getBraille_001001(), "-"); //hifen
		map.putUniqueKey(braille.getBraille_010001(), "?"); //question mark
		map.putUniqueKey(braille.getBraille_011000(), ";"); //semicolon
		map.putUniqueKey(braille.getBraille_011101(), "~"); //til
		map.putUniqueKey(braille.getBraille_010000(), ","); //comma
		map.putUniqueKey(braille.getBraille_001010(), "*"); //star
	}
}