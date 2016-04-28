package com.gbraille.keyboard;

public class BraillePointsImagesUppercase extends AbstractBraillePointsImages {
	
	public BraillePointsImagesUppercase(){
		createMap();
	}
	
	private void createMap(){
		map.clear();
		
		map.putUniqueKey(braille.getBraille_999999(), getBrailleSimbolError()); // if a symbol doesnt exists
		map.putUniqueKey(braille.getBraille_000000(), getBrailleSimbolDrawing_Space()); // white space
		map.putUniqueKey(braille.getBraille_001111(), getBrailleSimbolDrawing_Number()); //number
		map.putUniqueKey(braille.getBraille_000011(), getBrailleSimbolRestituidor()); //restituidor
		map.putUniqueKey(braille.getBraille_100000(), getBrailleLetterUppercaseDrawing_A()); //A
		map.putUniqueKey(braille.getBraille_110000(), getBrailleLetterUppercaseDrawing_B()); //B
		map.putUniqueKey(braille.getBraille_100100(), getBrailleLetterUppercaseDrawing_C()); //C
		map.putUniqueKey(braille.getBraille_100110(), getBrailleLetterUppercaseDrawing_D()); //D
		map.putUniqueKey(braille.getBraille_100010(), getBrailleLetterUppercaseDrawing_E()); //E
		map.putUniqueKey(braille.getBraille_110100(), getBrailleLetterUppercaseDrawing_F()); //F
		map.putUniqueKey(braille.getBraille_110110(), getBrailleLetterUppercaseDrawing_G()); //G
		map.putUniqueKey(braille.getBraille_110010(), getBrailleLetterUppercaseDrawing_H()); //H
		map.putUniqueKey(braille.getBraille_010100(), getBrailleLetterUppercaseDrawing_I()); //I
		map.putUniqueKey(braille.getBraille_010110(), getBrailleLetterUppercaseDrawing_J()); //J
		map.putUniqueKey(braille.getBraille_101000(), getBrailleLetterUppercaseDrawing_K()); //K
		map.putUniqueKey(braille.getBraille_111000(), getBrailleLetterUppercaseDrawing_L()); //L
		map.putUniqueKey(braille.getBraille_101100(), getBrailleLetterUppercaseDrawing_M()); //M
		map.putUniqueKey(braille.getBraille_101110(), getBrailleLetterUppercaseDrawing_N()); //N
		map.putUniqueKey(braille.getBraille_101010(), getBrailleLetterUppercaseDrawing_O()); //O
		map.putUniqueKey(braille.getBraille_111100(), getBrailleLetterUppercaseDrawing_P()); //P
		map.putUniqueKey(braille.getBraille_111110(), getBrailleLetterUppercaseDrawing_Q()); //Q
		map.putUniqueKey(braille.getBraille_111010(), getBrailleLetterUppercaseDrawing_R()); //R		
		map.putUniqueKey(braille.getBraille_011100(), getBrailleLetterUppercaseDrawing_S()); //S
		map.putUniqueKey(braille.getBraille_011110(), getBrailleLetterUppercaseDrawing_T()); //T
		map.putUniqueKey(braille.getBraille_101001(), getBrailleLetterUppercaseDrawing_U()); //U	
		map.putUniqueKey(braille.getBraille_111001(), getBrailleLetterUppercaseDrawing_V()); //V
		map.putUniqueKey(braille.getBraille_010111(), getBrailleLetterUppercaseDrawing_W()); //W
		map.putUniqueKey(braille.getBraille_101101(), getBrailleLetterUppercaseDrawing_X()); //X
		map.putUniqueKey(braille.getBraille_101111(), getBrailleLetterUppercaseDrawing_Y()); //Y
		map.putUniqueKey(braille.getBraille_101011(), getBrailleLetterUppercaseDrawing_Z()); //Z
		map.putUniqueKey(braille.getBraille_111011(), getBrailleLetterUppercaseDrawing_A_Acute()); //A acute
		map.putUniqueKey(braille.getBraille_111111(), getBrailleLetterUppercaseDrawing_E_Acute()); //E acute
		map.putUniqueKey(braille.getBraille_001100(), getBrailleLetterUppercaseDrawing_I_Acute()); //I acute
		map.putUniqueKey(braille.getBraille_001101(), getBrailleLetterUppercaseDrawing_O_Acute()); //O acute
		map.putUniqueKey(braille.getBraille_011111(), getBrailleLetterUppercaseDrawing_U_Acute()); //U acute			
		map.putUniqueKey(braille.getBraille_110101(), getBrailleLetterUppercaseDrawing_A_Agrave()); // A grave			
		map.putUniqueKey(braille.getBraille_100001(), getBrailleLetterUppercaseDrawing_A_Circ()); // A circumflex
		map.putUniqueKey(braille.getBraille_110001(), getBrailleLetterUppercaseDrawing_E_Circ()); // E circumflex
		map.putUniqueKey(braille.getBraille_100111(), getBrailleLetterUppercaseDrawing_O_Circ()); // O circumflex	
		map.putUniqueKey(braille.getBraille_001110(), getBrailleLetterUppercaseDrawing_A_Tilde()); // A tilde
		map.putUniqueKey(braille.getBraille_010101(), getBrailleLetterUppercaseDrawing_O_Tilde()); // O tilde		
		map.putUniqueKey(braille.getBraille_110011(), getBrailleLetterUppercaseDrawing_U_Trema()); // u trema			
		map.putUniqueKey(braille.getBraille_111101(), getBrailleLetterUppercaseDrawing_C_Cedil()); // cedilla
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