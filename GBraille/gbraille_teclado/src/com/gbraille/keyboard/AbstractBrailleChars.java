package com.gbraille.keyboard;

public abstract class AbstractBrailleChars {
	Braille braille = new Braille();
	BrailleHashMap map = new BrailleHashMap();
	
	public BrailleHashMap getMap(){
		return map;
	}
	
	abstract void createMap();
}
