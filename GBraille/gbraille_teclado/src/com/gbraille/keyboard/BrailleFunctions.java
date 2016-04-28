package com.gbraille.keyboard;

public class BrailleFunctions {
	private String writtingMode;
    
	public BrailleFunctions(){
		writtingMode = "normal";
	}
	
    public void setWrittingMode(String mode){
    	if (mode.equalsIgnoreCase("normal") || mode.equalsIgnoreCase("slate")){ 
    		writtingMode = mode;
    	}
    	else{
    		writtingMode = "normal";
    	}
    }
    public String getWrittingMode(){
    	return writtingMode;
    }
}
