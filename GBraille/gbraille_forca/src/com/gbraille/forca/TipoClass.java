package com.gbraille.forca;

public class TipoClass {
	public enum TipoLevel {	 
		ORTOGRAFICO(1), SINONIMO(2);
		private final int value;
		TipoLevel(int valorOpcao){ 
			value = valorOpcao; 
		}
		public int getValue(){ 
			return value; 
		}
	}
}
