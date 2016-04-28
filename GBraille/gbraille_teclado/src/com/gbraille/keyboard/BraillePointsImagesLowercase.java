package com.gbraille.keyboard;

public class BraillePointsImagesLowercase extends AbstractBraillePointsImages {
	
	public BraillePointsImagesLowercase(){
		createMap();
	}
	
	private void createMap(){
		map.clear();
		
		map.putUniqueKey(braille.getBraille_999999(), getBrailleSimbolError()); // if a symbol doesnt exists
		map.putUniqueKey(braille.getBraille_000000(), getBrailleSimbolDrawing_Space()); //white space
		map.putUniqueKey(braille.getBraille_001111(), getBrailleSimbolDrawing_Number()); //number
		map.putUniqueKey(braille.getBraille_000011(), getBrailleSimbolRestituidor()); //restituidor
		map.putUniqueKey(braille.getBraille_100000(), getBrailleLetterLowercaseDrawing_A()); //A
		map.putUniqueKey(braille.getBraille_110000(), getBrailleLetterLowercaseDrawing_B()); //B
		map.putUniqueKey(braille.getBraille_100100(), getBrailleLetterLowercaseDrawing_C()); //C
		map.putUniqueKey(braille.getBraille_100110(), getBrailleLetterLowercaseDrawing_D()); //D
		map.putUniqueKey(braille.getBraille_100010(), getBrailleLetterLowercaseDrawing_E()); //E
		map.putUniqueKey(braille.getBraille_110100(), getBrailleLetterLowercaseDrawing_F()); //F
		map.putUniqueKey(braille.getBraille_110110(), getBrailleLetterLowercaseDrawing_G()); //G
		map.putUniqueKey(braille.getBraille_110010(), getBrailleLetterLowercaseDrawing_H()); //H
		map.putUniqueKey(braille.getBraille_010100(), getBrailleLetterLowercaseDrawing_I()); //I
		map.putUniqueKey(braille.getBraille_010110(), getBrailleLetterLowercaseDrawing_J()); //J
		map.putUniqueKey(braille.getBraille_101000(), getBrailleLetterLowercaseDrawing_K()); //K
		map.putUniqueKey(braille.getBraille_111000(), getBrailleLetterLowercaseDrawing_L()); //L
		map.putUniqueKey(braille.getBraille_101100(), getBrailleLetterLowercaseDrawing_M()); //M
		map.putUniqueKey(braille.getBraille_101110(), getBrailleLetterLowercaseDrawing_N()); //N		
		map.putUniqueKey(braille.getBraille_101010(), getBrailleLetterLowercaseDrawing_O()); //O
		map.putUniqueKey(braille.getBraille_111100(), getBrailleLetterLowercaseDrawing_P()); //P
		map.putUniqueKey(braille.getBraille_111110(), getBrailleLetterLowercaseDrawing_Q()); //Q
		map.putUniqueKey(braille.getBraille_111010(), getBrailleLetterLowercaseDrawing_R()); //R		
		map.putUniqueKey(braille.getBraille_011100(), getBrailleLetterLowercaseDrawing_S()); //S
		map.putUniqueKey(braille.getBraille_011110(), getBrailleLetterLowercaseDrawing_T()); //T
		map.putUniqueKey(braille.getBraille_101001(), getBrailleLetterLowercaseDrawing_U()); //U		
		map.putUniqueKey(braille.getBraille_111001(), getBrailleLetterLowercaseDrawing_V()); //V
		map.putUniqueKey(braille.getBraille_010111(), getBrailleLetterLowercaseDrawing_W()); //W
		map.putUniqueKey(braille.getBraille_101101(), getBrailleLetterLowercaseDrawing_X()); //X
		map.putUniqueKey(braille.getBraille_101111(), getBrailleLetterLowercaseDrawing_Y()); //Y
		map.putUniqueKey(braille.getBraille_101011(), getBrailleLetterLowercaseDrawing_Z()); //Z			
		map.putUniqueKey(braille.getBraille_111011(), getBrailleLetterLowercaseDrawing_A_Acute()); //a acute
		map.putUniqueKey(braille.getBraille_111111(), getBrailleLetterLowercaseDrawing_E_Acute()); //e acute
		map.putUniqueKey(braille.getBraille_001100(), getBrailleLetterLowercaseDrawing_I_Acute()); //i acute
		map.putUniqueKey(braille.getBraille_001101(), getBrailleLetterLowercaseDrawing_O_Acute()); //o acute
		map.putUniqueKey(braille.getBraille_011111(), getBrailleLetterLowercaseDrawing_U_Acute()); //u acute			
		map.putUniqueKey(braille.getBraille_110101(), getBrailleLetterLowercaseDrawing_A_Agrave()); // a grave			
		map.putUniqueKey(braille.getBraille_100001(), getBrailleLetterLowercaseDrawing_A_Circ()); // a circumflex
		map.putUniqueKey(braille.getBraille_110001(), getBrailleLetterLowercaseDrawing_E_Circ()); // e circumflex
		map.putUniqueKey(braille.getBraille_100111(), getBrailleLetterLowercaseDrawing_O_Circ()); // o circumflex		
		map.putUniqueKey(braille.getBraille_001110(), getBrailleLetterLowercaseDrawing_A_Tilde()); // a tilde
		map.putUniqueKey(braille.getBraille_010101(), getBrailleLetterLowercaseDrawing_O_Tilde()); // o tilde		
		map.putUniqueKey(braille.getBraille_110011(), getBrailleLetterLowercaseDrawing_U_Trema()); // u trema			
		map.putUniqueKey(braille.getBraille_111101(), getBrailleLetterLowercaseDrawing_C_Cedil()); // cedilla
		map.putUniqueKey(braille.getBraille_001000(), getBrailleSimbolDrawing_Period()); //.
		map.putUniqueKey(braille.getBraille_100011(), getBrailleSimbolDrawing_At()); //@
		map.putUniqueKey(braille.getBraille_010010(), getBrailleSimbolDrawing_Colon()); //:
		map.putUniqueKey(braille.getBraille_011011(), getBrailleSimbolDrawing_Equals()); //=		
		map.putUniqueKey(braille.getBraille_011010(), getBrailleSimbolDrawing_Plus()); //+
		map.putUniqueKey(braille.getBraille_001001(), getBrailleSimbolDrawing_Hifen()); //-
		map.putUniqueKey(braille.getBraille_010001(), getBrailleSimbolDrawing_QuestionMark()); //?
		map.putUniqueKey(braille.getBraille_011000(), getBrailleSimbolDrawing_Semicolon()); //;
		map.putUniqueKey(braille.getBraille_011101(), getBrailleSimbolDrawing_Tilde()); //~
		map.putUniqueKey(braille.getBraille_010000(), getBrailleSimbolDrawing_Comma()); //,
		map.putUniqueKey(braille.getBraille_001010(), getBrailleSimbolDrawing_Star()); //*
	}
}