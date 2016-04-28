package com.gbraille.keyboard;

import java.util.ArrayList;
import java.util.List;

import com.gbraille.libraries.LogMessages;

import de.akquinet.android.androlog.Log;
import android.widget.TextView;

public class BrailleDisplay {
	private int cursorPosition;
	private List<String> displayTextList = new ArrayList<String>();
	private final String TAG = "BrailleDisplay";
	private final int CHAR_DELETED_TIME_INTERVAL = 800;
	
	public BrailleDisplay(){
		cursorPosition = 0;
		displayTextList.clear();
	}
	
	public int getCursorPosition(){
		return cursorPosition;
	}
	
	public void setCursorPosition(int value){
		cursorPosition = value;
	}

	public void decreaseCursorPos(){
		cursorPosition--;
	}
	
	public void increaseCursorPos(){
		cursorPosition++;
	}
	
	public List<String> getDisplayTextList(){
		return displayTextList;
	}
	
	public int getCharDeletedTimeInterval(){
		return CHAR_DELETED_TIME_INTERVAL;
	}
		
	public String remove(int cursorPos){
		String character = displayTextList.remove(cursorPos);
		return character;
	}
	
	public String remove(){
		String character = displayTextList.remove(cursorPosition);
		return character;
	}
	
	public String getCharacterAtCursorPosition(){
		String cursor = "";
		try{
			cursor = getDisplayTextList().get(cursorPosition);
			return cursor;
		}
		catch(Exception e){
			Log.i(TAG,LogMessages.MSG_EXCEPTION_CURSOR_POSITION);
		}
		return "";
	}
	
	public String getText(){
		String message = "";
		for (int i = 0; i < getDisplayTextList().size(); i++){
			message += getDisplayTextList().get(i);
		}
		return message;
	}
	
	public void insertCharacter(String character){
		// nothing inserted
		if (getDisplayTextList().size() == 0){
			displayTextList.add(character);
			cursorPosition++;
		}
		else{
			getDisplayTextList().add(cursorPosition,character);
			cursorPosition++;
		}
		Log.i(TAG, LogMessages.MSG_DISPLAYED_TEXT + getDisplayTextList().toString());
	}
	
	public void showTextAtTextView(TextView v){
		v.setText(getText());
	}
	
	public String deleteCharacter(){
		if (getDisplayTextList().size() != 0){
			if (getCursorPosition() > 0){
				decreaseCursorPos();
				String deleted = remove();
				Log.i(TAG, LogMessages.MSG_CHARACTER_DELETED);
				Log.i(TAG, LogMessages.MSG_DISPLAYED_TEXT + getDisplayTextList().toString());
				return deleted;
			}
			return null;
		}
		else{
			return null;
		}
	}
	
	public int moveCursorToLeft(){
		if (getCursorPosition() > 0){
			decreaseCursorPos();
			return 1;
		}
		Log.i(TAG, LogMessages.MSG_CURSOR_POSITION + cursorPosition);
		return 0;
	}
	
	public int moveCursorToRight(){
		if (getCursorPosition() < getDisplayTextList().size()){
			increaseCursorPos();
			if (getCursorPosition() == getDisplayTextList().size()){
				return 2;
			}
			else{
				return 1;
			}
		}
		Log.i(TAG, LogMessages.MSG_CURSOR_POSITION + cursorPosition);
		return 0;
	}
}