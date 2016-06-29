package com.gbraille.ortomonstro;

public class LanguageClass {
	public enum TipoLanguage {	 
		PT(1), ES(2), EN(3);
		private final int value;
		TipoLanguage(int valorOpcao){ 
			value = valorOpcao; 
		}
		public int getValue(){ 
			return value; 
		}
	}
}


