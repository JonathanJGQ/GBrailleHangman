package com.gbraille.forca;

public class DifficultyClass {
	public enum DifficultyLevel {	 
		FACIL(1), DIFICIL(2);
		private final int value;
		DifficultyLevel(int valorOpcao){ 
			value = valorOpcao; 
		}
		public int getValue(){ 
			return value; 
		}
	}
}
