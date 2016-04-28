package com.gbraille.keyboard;

public class ModeClass {
	public enum Mode {	 
		LER(1), SOLETRAR(2), DEFINIR(3);
		private final int value;
		Mode(int valorOpcao){ 
			value = valorOpcao; 
		}
		public int getValue(){ 
			return value; 
		}
	}
}
