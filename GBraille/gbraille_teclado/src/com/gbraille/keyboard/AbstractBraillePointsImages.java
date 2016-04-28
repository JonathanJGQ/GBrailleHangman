package com.gbraille.keyboard;

import com.gbraille.keyboard.R;

public class AbstractBraillePointsImages {
	Braille braille = new Braille();
	BrailleHashMap map = new BrailleHashMap();
	
	private final int DRAWABLE_BRAILLE_SPACE = R.drawable.s_000000;
	private final int DRAWABLE_BRAILLE_NUMBER = R.drawable.s_001111;
	private final int DRAWABLE_BRAILLE_ONE_LETTER_UPPERCASE = R.drawable.s_000101;
	private final int DRAWABLE_BRAILLE_ALL_LETTERS_UPPERCASE = R.drawable.s_000101_2;	
	private final int DRAWABLE_BRAILLE_RESTITUIDOR = R.drawable.s_000011;
	private final int DRAWABLE_BRAILLE_ERROR = R.drawable.s_999999;
	
	private final int DRAWABLE_BRAILLE_NUMBER_1 = R.drawable.n_100000;
	private final int DRAWABLE_BRAILLE_NUMBER_2 = R.drawable.n_110000;
	private final int DRAWABLE_BRAILLE_NUMBER_3 = R.drawable.n_100100;
	private final int DRAWABLE_BRAILLE_NUMBER_4 = R.drawable.n_100110;
	private final int DRAWABLE_BRAILLE_NUMBER_5 = R.drawable.n_100010;
	private final int DRAWABLE_BRAILLE_NUMBER_6 = R.drawable.n_110100;
	private final int DRAWABLE_BRAILLE_NUMBER_7 = R.drawable.n_110110;
	private final int DRAWABLE_BRAILLE_NUMBER_8 = R.drawable.n_110010;
	private final int DRAWABLE_BRAILLE_NUMBER_9 = R.drawable.n_010100;
	private final int DRAWABLE_BRAILLE_NUMBER_0 = R.drawable.n_010110;
	
	private final int DRAWABLE_BRAILLE_LETTER_A_LOWERCASE = R.drawable.lmin_100000;
	private final int DRAWABLE_BRAILLE_LETTER_B_LOWERCASE = R.drawable.lmin_110000;
	private final int DRAWABLE_BRAILLE_LETTER_C_LOWERCASE = R.drawable.lmin_100100;
	private final int DRAWABLE_BRAILLE_LETTER_D_LOWERCASE = R.drawable.lmin_100110;
	private final int DRAWABLE_BRAILLE_LETTER_E_LOWERCASE = R.drawable.lmin_100010;
	private final int DRAWABLE_BRAILLE_LETTER_F_LOWERCASE = R.drawable.lmin_110100;
	private final int DRAWABLE_BRAILLE_LETTER_G_LOWERCASE = R.drawable.lmin_110110;
	private final int DRAWABLE_BRAILLE_LETTER_H_LOWERCASE = R.drawable.lmin_110010;
	private final int DRAWABLE_BRAILLE_LETTER_I_LOWERCASE = R.drawable.lmin_010100;
	private final int DRAWABLE_BRAILLE_LETTER_J_LOWERCASE = R.drawable.lmin_010110;
	private final int DRAWABLE_BRAILLE_LETTER_K_LOWERCASE = R.drawable.lmin_101000;
	private final int DRAWABLE_BRAILLE_LETTER_L_LOWERCASE = R.drawable.lmin_111000;
	private final int DRAWABLE_BRAILLE_LETTER_M_LOWERCASE = R.drawable.lmin_101100;
	private final int DRAWABLE_BRAILLE_LETTER_N_LOWERCASE = R.drawable.lmin_101110;
	private final int DRAWABLE_BRAILLE_LETTER_O_LOWERCASE = R.drawable.lmin_101010;
	private final int DRAWABLE_BRAILLE_LETTER_P_LOWERCASE = R.drawable.lmin_111100;
	private final int DRAWABLE_BRAILLE_LETTER_Q_LOWERCASE = R.drawable.lmin_111110;
	private final int DRAWABLE_BRAILLE_LETTER_R_LOWERCASE = R.drawable.lmin_111010;
	private final int DRAWABLE_BRAILLE_LETTER_S_LOWERCASE = R.drawable.lmin_011100;
	private final int DRAWABLE_BRAILLE_LETTER_T_LOWERCASE = R.drawable.lmin_011110;
	private final int DRAWABLE_BRAILLE_LETTER_U_LOWERCASE = R.drawable.lmin_101001;
	private final int DRAWABLE_BRAILLE_LETTER_V_LOWERCASE = R.drawable.lmin_111001;
	private final int DRAWABLE_BRAILLE_LETTER_W_LOWERCASE = R.drawable.lmin_010111;
	private final int DRAWABLE_BRAILLE_LETTER_X_LOWERCASE = R.drawable.lmin_101101;
	private final int DRAWABLE_BRAILLE_LETTER_Y_LOWERCASE = R.drawable.lmin_101111;
	private final int DRAWABLE_BRAILLE_LETTER_Z_LOWERCASE = R.drawable.lmin_101011;
	
	private final int DRAWABLE_BRAILLE_LETTER_A_UPPERCASE = R.drawable.lma_100000;
	private final int DRAWABLE_BRAILLE_LETTER_B_UPPERCASE = R.drawable.lma_110000;
	private final int DRAWABLE_BRAILLE_LETTER_C_UPPERCASE = R.drawable.lma_100100;
	private final int DRAWABLE_BRAILLE_LETTER_D_UPPERCASE = R.drawable.lma_100110;
	private final int DRAWABLE_BRAILLE_LETTER_E_UPPERCASE = R.drawable.lma_100010;
	private final int DRAWABLE_BRAILLE_LETTER_F_UPPERCASE = R.drawable.lma_110100;
	private final int DRAWABLE_BRAILLE_LETTER_G_UPPERCASE = R.drawable.lma_110110;
	private final int DRAWABLE_BRAILLE_LETTER_H_UPPERCASE = R.drawable.lma_110010;
	private final int DRAWABLE_BRAILLE_LETTER_I_UPPERCASE = R.drawable.lma_010100;
	private final int DRAWABLE_BRAILLE_LETTER_J_UPPERCASE = R.drawable.lma_010110;
	private final int DRAWABLE_BRAILLE_LETTER_K_UPPERCASE = R.drawable.lma_101000;
	private final int DRAWABLE_BRAILLE_LETTER_L_UPPERCASE = R.drawable.lma_111000;
	private final int DRAWABLE_BRAILLE_LETTER_M_UPPERCASE = R.drawable.lma_101100;
	private final int DRAWABLE_BRAILLE_LETTER_N_UPPERCASE = R.drawable.lma_101110;
	private final int DRAWABLE_BRAILLE_LETTER_O_UPPERCASE = R.drawable.lma_101010;
	private final int DRAWABLE_BRAILLE_LETTER_P_UPPERCASE = R.drawable.lma_111100;
	private final int DRAWABLE_BRAILLE_LETTER_Q_UPPERCASE = R.drawable.lma_111110;
	private final int DRAWABLE_BRAILLE_LETTER_R_UPPERCASE = R.drawable.lma_111010;
	private final int DRAWABLE_BRAILLE_LETTER_S_UPPERCASE = R.drawable.lma_011100;
	private final int DRAWABLE_BRAILLE_LETTER_T_UPPERCASE = R.drawable.lma_011110;
	private final int DRAWABLE_BRAILLE_LETTER_U_UPPERCASE = R.drawable.lma_101001;
	private final int DRAWABLE_BRAILLE_LETTER_V_UPPERCASE = R.drawable.lma_111001;
	private final int DRAWABLE_BRAILLE_LETTER_W_UPPERCASE = R.drawable.lma_010111;
	private final int DRAWABLE_BRAILLE_LETTER_X_UPPERCASE = R.drawable.lma_101101;
	private final int DRAWABLE_BRAILLE_LETTER_Y_UPPERCASE = R.drawable.lma_101111;
	private final int DRAWABLE_BRAILLE_LETTER_Z_UPPERCASE = R.drawable.lma_101011;
	
	private final int DRAWABLE_BRAILLE_PERIOD = R.drawable.s_001000; // .
	private final int DRAWABLE_BRAILLE_AT = R.drawable.s_100011; // @
	private final int DRAWABLE_BRAILLE_COLON = R.drawable.s_010010; // :
	private final int DRAWABLE_BRAILLE_EQUALS = R.drawable.s_011011; // = 
	private final int DRAWABLE_BRAILLE_PLUS = R.drawable.s_011010; // +
	private final int DRAWABLE_BRAILLE_HIFEN = R.drawable.s_001001; // -
	private final int DRAWABLE_BRAILLE_QUESTIONMARK = R.drawable.s_010001; // ?
	private final int DRAWABLE_BRAILLE_SEMICOLON = R.drawable.s_011000; // ;
	private final int DRAWABLE_BRAILLE_TILDE = R.drawable.s_011101; // ~
	private final int DRAWABLE_BRAILLE_COMMA = R.drawable.s_010000; // ,
	private final int DRAWABLE_BRAILLE_STAR = R.drawable.s_001010; // *	
	
	private final int DRAWABLE_BRAILLE_LETTER_A_UPPERCASE_ACUTE = R.drawable.lma_111011;
	private final int DRAWABLE_BRAILLE_LETTER_E_UPPERCASE_ACUTE = R.drawable.lma_111111;
	private final int DRAWABLE_BRAILLE_LETTER_I_UPPERCASE_ACUTE = R.drawable.lma_001100;
	private final int DRAWABLE_BRAILLE_LETTER_O_UPPERCASE_ACUTE = R.drawable.lma_001101;
	private final int DRAWABLE_BRAILLE_LETTER_U_UPPERCASE_ACUTE = R.drawable.lma_011111;	
	private final int DRAWABLE_BRAILLE_LETTER_A_UPPERCASE_GRAVE = R.drawable.lma_110101;	
	private final int DRAWABLE_BRAILLE_LETTER_A_UPPERCASE_CIRC = R.drawable.lma_100001;
	private final int DRAWABLE_BRAILLE_LETTER_E_UPPERCASE_CIRC = R.drawable.lma_110001;
	private final int DRAWABLE_BRAILLE_LETTER_O_UPPERCASE_CIRC = R.drawable.lma_100111;	
	private final int DRAWABLE_BRAILLE_LETTER_A_UPPERCASE_TILDE = R.drawable.lma_001110;
	private final int DRAWABLE_BRAILLE_LETTER_O_UPPERCASE_TILDE = R.drawable.lma_010101;	
	private final int DRAWABLE_BRAILLE_LETTER_U_UPPERCASE_TREMA = R.drawable.lma_110011;	
	private final int DRAWABLE_BRAILLE_LETTER_C_UPPERCASE_CEDIL = R.drawable.lma_111101;	
	
	private final int DRAWABLE_BRAILLE_LETTER_A_LOWERCASE_ACUTE = R.drawable.lmin_111011;
	private final int DRAWABLE_BRAILLE_LETTER_E_LOWERCASE_ACUTE = R.drawable.lmin_111111;
	private final int DRAWABLE_BRAILLE_LETTER_I_LOWERCASE_ACUTE = R.drawable.lmin_001100;
	private final int DRAWABLE_BRAILLE_LETTER_O_LOWERCASE_ACUTE = R.drawable.lmin_001101;
	private final int DRAWABLE_BRAILLE_LETTER_U_LOWERCASE_ACUTE = R.drawable.lmin_011111;
	private final int DRAWABLE_BRAILLE_LETTER_A_LOWERCASE_GRAVE = R.drawable.lmin_110101;
	private final int DRAWABLE_BRAILLE_LETTER_A_LOWERCASE_CIRC = R.drawable.lmin_100001;
	private final int DRAWABLE_BRAILLE_LETTER_E_LOWERCASE_CIRC = R.drawable.lmin_110001;
	private final int DRAWABLE_BRAILLE_LETTER_O_LOWERCASE_CIRC = R.drawable.lmin_100111;	
	private final int DRAWABLE_BRAILLE_LETTER_A_LOWERCASE_TILDE = R.drawable.lmin_001110;
	private final int DRAWABLE_BRAILLE_LETTER_O_LOWERCASE_TILDE = R.drawable.lmin_010101;
	private final int DRAWABLE_BRAILLE_LETTER_U_LOWERCASE_TREMA = R.drawable.lmin_110011;
	private final int DRAWABLE_BRAILLE_LETTER_C_LOWERCASE_CEDIL = R.drawable.lmin_111101;
	
	/* SYMBOLS */	
	public int getBrailleSimbolDrawing_Space(){
		return DRAWABLE_BRAILLE_SPACE;
	}
	public int getBrailleSimbolDrawing_Number(){
		return DRAWABLE_BRAILLE_NUMBER;
	}
	public int getBraille_OneLetterUppercase(){
		return DRAWABLE_BRAILLE_ONE_LETTER_UPPERCASE;
	}
	public int getBraille_AllLettersUppercase(){
		return DRAWABLE_BRAILLE_ALL_LETTERS_UPPERCASE;
	}
	public int getBrailleSimbolRestituidor(){
		return DRAWABLE_BRAILLE_RESTITUIDOR;
	}
	public int getBrailleSimbolError(){
		return DRAWABLE_BRAILLE_ERROR;
	}
	
	public int getBrailleSimbolDrawing_Period(){
		return DRAWABLE_BRAILLE_PERIOD;
	}
	public int getBrailleSimbolDrawing_At(){
		return DRAWABLE_BRAILLE_AT;
	}
	public int getBrailleSimbolDrawing_Colon(){
		return DRAWABLE_BRAILLE_COLON;
	}
	public int getBrailleSimbolDrawing_Equals(){
		return DRAWABLE_BRAILLE_EQUALS;
	}
	public int getBrailleSimbolDrawing_Plus(){
		return DRAWABLE_BRAILLE_PLUS;
	}
	public int getBrailleSimbolDrawing_Hifen(){
		return DRAWABLE_BRAILLE_HIFEN;
	}
	public int getBrailleSimbolDrawing_QuestionMark(){
		return DRAWABLE_BRAILLE_QUESTIONMARK;
	}
	public int getBrailleSimbolDrawing_Semicolon(){
		return DRAWABLE_BRAILLE_SEMICOLON;
	}
	public int getBrailleSimbolDrawing_Tilde(){
		return DRAWABLE_BRAILLE_TILDE;
	}
	public int getBrailleSimbolDrawing_Comma(){
		return DRAWABLE_BRAILLE_COMMA;
	}
	public int getBrailleSimbolDrawing_Star(){
		return DRAWABLE_BRAILLE_STAR;
	}
	
	/* NUMBERS */
	
	public int getBrailleNumberDrawing_1(){
		return DRAWABLE_BRAILLE_NUMBER_1;
	}
	public int getBrailleNumberDrawing_2(){
		return DRAWABLE_BRAILLE_NUMBER_2;
	}
	public int getBrailleNumberDrawing_3(){
		return DRAWABLE_BRAILLE_NUMBER_3;
	}
	public int getBrailleNumberDrawing_4(){
		return DRAWABLE_BRAILLE_NUMBER_4;
	}
	public int getBrailleNumberDrawing_5(){
		return DRAWABLE_BRAILLE_NUMBER_5;
	}
	public int getBrailleNumberDrawing_6(){
		return DRAWABLE_BRAILLE_NUMBER_6;
	}
	public int getBrailleNumberDrawing_7(){
		return DRAWABLE_BRAILLE_NUMBER_7;
	}
	public int getBrailleNumberDrawing_8(){
		return DRAWABLE_BRAILLE_NUMBER_8;
	}
	public int getBrailleNumberDrawing_9(){
		return DRAWABLE_BRAILLE_NUMBER_9;
	}
	public int getBrailleNumberDrawing_0(){
		return DRAWABLE_BRAILLE_NUMBER_0;
	}
	
	/* LOWERCASE LETTERS */
	
	public int getBrailleLetterLowercaseDrawing_A(){
		return DRAWABLE_BRAILLE_LETTER_A_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_B(){
		return DRAWABLE_BRAILLE_LETTER_B_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_C(){
		return DRAWABLE_BRAILLE_LETTER_C_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_D(){
		return DRAWABLE_BRAILLE_LETTER_D_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_E(){
		return DRAWABLE_BRAILLE_LETTER_E_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_F(){
		return DRAWABLE_BRAILLE_LETTER_F_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_G(){
		return DRAWABLE_BRAILLE_LETTER_G_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_H(){
		return DRAWABLE_BRAILLE_LETTER_H_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_I(){
		return DRAWABLE_BRAILLE_LETTER_I_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_J(){
		return DRAWABLE_BRAILLE_LETTER_J_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_K(){
		return DRAWABLE_BRAILLE_LETTER_K_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_L(){
		return DRAWABLE_BRAILLE_LETTER_L_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_M(){
		return DRAWABLE_BRAILLE_LETTER_M_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_N(){
		return DRAWABLE_BRAILLE_LETTER_N_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_O(){
		return DRAWABLE_BRAILLE_LETTER_O_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_P(){
		return DRAWABLE_BRAILLE_LETTER_P_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_Q(){
		return DRAWABLE_BRAILLE_LETTER_Q_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_R(){
		return DRAWABLE_BRAILLE_LETTER_R_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_S(){
		return DRAWABLE_BRAILLE_LETTER_S_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_T(){
		return DRAWABLE_BRAILLE_LETTER_T_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_U(){
		return DRAWABLE_BRAILLE_LETTER_U_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_V(){
		return DRAWABLE_BRAILLE_LETTER_V_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_W(){
		return DRAWABLE_BRAILLE_LETTER_W_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_X(){
		return DRAWABLE_BRAILLE_LETTER_X_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_Y(){
		return DRAWABLE_BRAILLE_LETTER_Y_LOWERCASE;
	}
	public int getBrailleLetterLowercaseDrawing_Z(){
		return DRAWABLE_BRAILLE_LETTER_Z_LOWERCASE;
	}
	
	/* UPPERCASE LETTERS */
	
	public int getBrailleLetterUppercaseDrawing_A(){
		return DRAWABLE_BRAILLE_LETTER_A_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_B(){
		return DRAWABLE_BRAILLE_LETTER_B_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_C(){
		return DRAWABLE_BRAILLE_LETTER_C_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_D(){
		return DRAWABLE_BRAILLE_LETTER_D_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_E(){
		return DRAWABLE_BRAILLE_LETTER_E_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_F(){
		return DRAWABLE_BRAILLE_LETTER_F_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_G(){
		return DRAWABLE_BRAILLE_LETTER_G_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_H(){
		return DRAWABLE_BRAILLE_LETTER_H_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_I(){
		return DRAWABLE_BRAILLE_LETTER_I_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_J(){
		return DRAWABLE_BRAILLE_LETTER_J_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_K(){
		return DRAWABLE_BRAILLE_LETTER_K_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_L(){
		return DRAWABLE_BRAILLE_LETTER_L_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_M(){
		return DRAWABLE_BRAILLE_LETTER_M_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_N(){
		return DRAWABLE_BRAILLE_LETTER_N_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_O(){
		return DRAWABLE_BRAILLE_LETTER_O_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_P(){
		return DRAWABLE_BRAILLE_LETTER_P_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_Q(){
		return DRAWABLE_BRAILLE_LETTER_Q_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_R(){
		return DRAWABLE_BRAILLE_LETTER_R_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_S(){
		return DRAWABLE_BRAILLE_LETTER_S_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_T(){
		return DRAWABLE_BRAILLE_LETTER_T_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_U(){
		return DRAWABLE_BRAILLE_LETTER_U_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_V(){
		return DRAWABLE_BRAILLE_LETTER_V_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_W(){
		return DRAWABLE_BRAILLE_LETTER_W_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_X(){
		return DRAWABLE_BRAILLE_LETTER_X_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_Y(){
		return DRAWABLE_BRAILLE_LETTER_Y_UPPERCASE;
	}
	public int getBrailleLetterUppercaseDrawing_Z(){
		return DRAWABLE_BRAILLE_LETTER_Z_UPPERCASE;
	}
	
	/* accent - uppercase letters */
	public int getBrailleLetterUppercaseDrawing_A_Acute(){
		return DRAWABLE_BRAILLE_LETTER_A_UPPERCASE_ACUTE;
	}
	public int getBrailleLetterUppercaseDrawing_E_Acute(){
		return DRAWABLE_BRAILLE_LETTER_E_UPPERCASE_ACUTE;
	}
	public int getBrailleLetterUppercaseDrawing_I_Acute(){
		return DRAWABLE_BRAILLE_LETTER_I_UPPERCASE_ACUTE;
	}
	public int getBrailleLetterUppercaseDrawing_O_Acute(){
		return DRAWABLE_BRAILLE_LETTER_O_UPPERCASE_ACUTE;
	}
	public int getBrailleLetterUppercaseDrawing_U_Acute(){
		return DRAWABLE_BRAILLE_LETTER_U_UPPERCASE_ACUTE;
	}
	public int getBrailleLetterUppercaseDrawing_A_Agrave(){
		return DRAWABLE_BRAILLE_LETTER_A_UPPERCASE_GRAVE;
	}
	public int getBrailleLetterUppercaseDrawing_A_Circ(){
		return DRAWABLE_BRAILLE_LETTER_A_UPPERCASE_CIRC;
	}
	public int getBrailleLetterUppercaseDrawing_E_Circ(){
		return DRAWABLE_BRAILLE_LETTER_E_UPPERCASE_CIRC;
	}
	public int getBrailleLetterUppercaseDrawing_O_Circ(){
		return DRAWABLE_BRAILLE_LETTER_O_UPPERCASE_CIRC;
	}
	public int getBrailleLetterUppercaseDrawing_A_Tilde(){
		return DRAWABLE_BRAILLE_LETTER_A_UPPERCASE_TILDE;
	}
	public int getBrailleLetterUppercaseDrawing_O_Tilde(){
		return DRAWABLE_BRAILLE_LETTER_O_UPPERCASE_TILDE;
	}
	public int getBrailleLetterUppercaseDrawing_U_Trema(){
		return DRAWABLE_BRAILLE_LETTER_U_UPPERCASE_TREMA;
	}
	public int getBrailleLetterUppercaseDrawing_C_Cedil(){
		return DRAWABLE_BRAILLE_LETTER_C_UPPERCASE_CEDIL;
	}
	
	/* accent - lowercase letters */
	public int getBrailleLetterLowercaseDrawing_A_Acute(){
		return DRAWABLE_BRAILLE_LETTER_A_LOWERCASE_ACUTE;
	}
	public int getBrailleLetterLowercaseDrawing_E_Acute(){
		return DRAWABLE_BRAILLE_LETTER_E_LOWERCASE_ACUTE;
	}
	public int getBrailleLetterLowercaseDrawing_I_Acute(){
		return DRAWABLE_BRAILLE_LETTER_I_LOWERCASE_ACUTE;
	}
	public int getBrailleLetterLowercaseDrawing_O_Acute(){
		return DRAWABLE_BRAILLE_LETTER_O_LOWERCASE_ACUTE;
	}
	public int getBrailleLetterLowercaseDrawing_U_Acute(){
		return DRAWABLE_BRAILLE_LETTER_U_LOWERCASE_ACUTE;
	}
	public int getBrailleLetterLowercaseDrawing_A_Agrave(){
		return DRAWABLE_BRAILLE_LETTER_A_LOWERCASE_GRAVE;
	}
	public int getBrailleLetterLowercaseDrawing_A_Circ(){
		return DRAWABLE_BRAILLE_LETTER_A_LOWERCASE_CIRC;
	}
	public int getBrailleLetterLowercaseDrawing_E_Circ(){
		return DRAWABLE_BRAILLE_LETTER_E_LOWERCASE_CIRC;
	}
	public int getBrailleLetterLowercaseDrawing_O_Circ(){
		return DRAWABLE_BRAILLE_LETTER_O_LOWERCASE_CIRC;
	}
	public int getBrailleLetterLowercaseDrawing_A_Tilde(){
		return DRAWABLE_BRAILLE_LETTER_A_LOWERCASE_TILDE;
	}
	public int getBrailleLetterLowercaseDrawing_O_Tilde(){
		return DRAWABLE_BRAILLE_LETTER_O_LOWERCASE_TILDE;
	}
	public int getBrailleLetterLowercaseDrawing_U_Trema(){
		return DRAWABLE_BRAILLE_LETTER_U_LOWERCASE_TREMA;
	}
	public int getBrailleLetterLowercaseDrawing_C_Cedil(){
		return DRAWABLE_BRAILLE_LETTER_C_LOWERCASE_CEDIL;
	}
	
	public BrailleHashMap getMap(){
		return map;
	}
}