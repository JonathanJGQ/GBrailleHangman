package com.gbraille.keyboard;


public class FingerArea {
	// finger area when its down
	private int finger1DownArea, finger2DownArea, finger3DownArea;
		
	// finger area when its up
	private int finger1UpArea, finger2UpArea, finger3UpArea;
	
	public enum enumFingerArea {
		LEFT(1), RIGHT(2);
		 
		private int fingerAreaCode;
	 
		private enumFingerArea(int n) {
			fingerAreaCode = n;
		}
	 
		public int getCode() {
			return fingerAreaCode;
		}
	}
	
	public void setFinger1DownArea(int value){
		finger1DownArea = value;
	}
	public void setFinger2DownArea(int value){
		finger2DownArea = value;
	}
	public void setFinger3DownArea(int value){
		finger3DownArea = value;
	}
	
	public void setFinger1UpArea(int value){
		finger1UpArea = value;
	}
	public void setFinger2UpArea(int value){
		finger2UpArea = value;
	}
	public void setFinger3UpArea(int value){
		finger3UpArea = value;
	}
	
	public int getFinger1DownArea(){
		return finger1DownArea;
	}
	public int getFinger2DownArea(){
		return finger2DownArea;
	}
	public int getFinger3DownArea(){
		return finger3DownArea;
	}
	
	public int getFinger1UpArea(){
		return finger1UpArea;
	}
	public int getFinger2UpArea(){
		return finger2UpArea;
	}
	public int getFinger3UpArea(){
		return finger3UpArea;
	}
	
	public FingerArea(){
		clearAllFingerAreas();
	}
	
	public int getFingerDownArea(int finger){
		if (finger > 0 && finger < 4){
			switch(finger){
				case 1:
					return getFinger1DownArea();
				case 2:
					return getFinger2DownArea();
				case 3:
					return getFinger3DownArea();
			}
		}
		return 0;
	}
	
	public int getFingerUpArea(int finger){
		if (finger > 0 && finger < 4){
			switch(finger){
				case 1:
					return getFinger1UpArea();
				case 2:
					return getFinger2UpArea();
				case 3:
					return getFinger3UpArea();
			}
		}
		return 0;
	}
	
	public void setFingerDownArea(int finger, int value){
		if ( (finger > 0 && finger < 4) && (value > 0 && value < 3) ){
			switch(finger){
				case 1:
					setFinger1DownArea(value);
					break;
				case 2:
					setFinger2DownArea(value);
					break;
				case 3:
					setFinger3DownArea(value);
					break;
			}
		}
	}
	
	public void setFingerUpArea(int finger, int value){
		if ( (finger > 0 && finger < 4) && (value > 0 && value < 3) ){
			switch(finger){
				case 1:
					setFinger1UpArea(value);
					break;
				case 2:
					setFinger2UpArea(value);
					break;
				case 3:
					setFinger3UpArea(value);
					break;
			}
		}		
	}
	
	public void clearAllFingerAreas(){
		setFinger1DownArea(0);
		setFinger2DownArea(0);
		setFinger3DownArea(0);
		setFinger1UpArea(0);
		setFinger2UpArea(0);
		setFinger3UpArea(0);
	}
	
	public void clearFingerAreasDown(){
		setFinger1DownArea(0);
		setFinger2DownArea(0);
		setFinger3DownArea(0);
	}
	
	public void clearFingerAreasUp(){
		setFinger1UpArea(0);
		setFinger2UpArea(0);
		setFinger3UpArea(0);
	}
}