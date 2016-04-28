package com.gbraille.libraries;

public class TextToSpeechClass {
	/* Response code to indicate that tts was successfull */
	private final int REQ_TTS_STATUS_CHECK;
	
	/* set if the App Name was spoken */
	private boolean isAppNameSpoken;

	/**
     * Class Constructor
	 */
	public TextToSpeechClass(){
		isAppNameSpoken = false;
		REQ_TTS_STATUS_CHECK = 1234;
	}
	
	public void setIsAppNameSpoken(boolean value){
		isAppNameSpoken = value;
	}
	
	public boolean getIsAppNameSpoken(){
		return isAppNameSpoken;
	}
	
	public int getReqTTSStatusCheck(){
		return REQ_TTS_STATUS_CHECK;
	}
}