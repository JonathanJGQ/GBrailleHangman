package com.gbraille.keyboard;

import java.util.HashMap;

public class BrailleHashMap extends HashMap {
	public void putUniqueKey(String key, String value){
		if (key.length() != 6){
			throw new IllegalStateException("Invalid Braille key code = " + key + " - it must have 6 digits!");
		}
		else if ( containsKey( key ) ) {
			throw new IllegalStateException("The key " + key + " already exists!");
		}
		else{
			put( key, value );	
		}
	}
	
	public void putUniqueKey(String key, int value){
		if (key.length() != 6){
			throw new IllegalStateException("Invalid Braille key code - this must have 6 digits!");
		}
		else if ( containsKey( key ) ) {
			throw new IllegalStateException("The key " + key + " already exists!");
		}
		else{
			put( key, value );	
		}
	}
	
	public String getCharacter(String brailleCode){
		if (get(brailleCode) == null){
			return "";
		}
		return (String) get(brailleCode);
	}
	
	public int getDrawing(String brailleCode){
		if (get(brailleCode) == null){
			return 0;
		}
		return (Integer) get(brailleCode);
	}
}