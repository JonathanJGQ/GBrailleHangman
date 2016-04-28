package com.gbraille.keyboard;

public class BraillePointsImagesNumbers extends AbstractBraillePointsImages {
	
	public BraillePointsImagesNumbers(){
		createMap();
	}
	
	private void createMap(){
		map.clear();
		
		map.putUniqueKey(braille.getBraille_999999(), getBrailleSimbolError()); // if a symbol doesnt exists
		map.putUniqueKey(braille.getBraille_000000(), getBrailleSimbolDrawing_Space()); //white space
		map.putUniqueKey(braille.getBraille_001111(), getBrailleSimbolDrawing_Number()); //number
		map.putUniqueKey(braille.getBraille_000011(), getBrailleSimbolRestituidor()); //restituidor
		map.putUniqueKey(braille.getBraille_100000(), getBrailleNumberDrawing_1()); //1
		map.putUniqueKey(braille.getBraille_110000(), getBrailleNumberDrawing_2()); //2
		map.putUniqueKey(braille.getBraille_100100(), getBrailleNumberDrawing_3()); //3
		map.putUniqueKey(braille.getBraille_100110(), getBrailleNumberDrawing_4()); //4
		map.putUniqueKey(braille.getBraille_100010(), getBrailleNumberDrawing_5()); //5
		map.putUniqueKey(braille.getBraille_110100(), getBrailleNumberDrawing_6()); //6
		map.putUniqueKey(braille.getBraille_110110(), getBrailleNumberDrawing_7()); //7
		map.putUniqueKey(braille.getBraille_110010(), getBrailleNumberDrawing_8()); //8
		map.putUniqueKey(braille.getBraille_010100(), getBrailleNumberDrawing_9()); //9
		map.putUniqueKey(braille.getBraille_010110(), getBrailleNumberDrawing_0()); //0		
		map.putUniqueKey(braille.getBraille_101000(), getBrailleLetterLowercaseDrawing_K()); //k
		map.putUniqueKey(braille.getBraille_111000(), getBrailleLetterLowercaseDrawing_L()); //l
		map.putUniqueKey(braille.getBraille_101100(), getBrailleLetterLowercaseDrawing_M()); //m
		map.putUniqueKey(braille.getBraille_101110(), getBrailleLetterLowercaseDrawing_N()); //n		
		map.putUniqueKey(braille.getBraille_101010(), getBrailleLetterLowercaseDrawing_O()); //o
		map.putUniqueKey(braille.getBraille_111100(), getBrailleLetterLowercaseDrawing_P()); //p
		map.putUniqueKey(braille.getBraille_111110(), getBrailleLetterLowercaseDrawing_Q()); //q
		map.putUniqueKey(braille.getBraille_111010(), getBrailleLetterLowercaseDrawing_R()); //r
		map.putUniqueKey(braille.getBraille_011100(), getBrailleLetterLowercaseDrawing_S()); //s
		map.putUniqueKey(braille.getBraille_011110(), getBrailleLetterLowercaseDrawing_T()); //t
		map.putUniqueKey(braille.getBraille_101001(), getBrailleLetterLowercaseDrawing_U()); //u		
		map.putUniqueKey(braille.getBraille_111001(), getBrailleLetterLowercaseDrawing_V()); //v
		map.putUniqueKey(braille.getBraille_010111(), getBrailleLetterLowercaseDrawing_W()); //w
		map.putUniqueKey(braille.getBraille_101101(), getBrailleLetterLowercaseDrawing_X()); //x
		map.putUniqueKey(braille.getBraille_101111(), getBrailleLetterLowercaseDrawing_Y()); //y
		map.putUniqueKey(braille.getBraille_101011(), getBrailleLetterLowercaseDrawing_Z()); //z	
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