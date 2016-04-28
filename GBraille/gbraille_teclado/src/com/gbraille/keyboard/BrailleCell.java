package com.gbraille.keyboard;

public class BrailleCell {
	private int[] brailleCell = new int[6];
	
	public BrailleCell(){
		clearBrailleCell();
	}
	
	public void setBrailleCell(int index, int value) throws ArrayIndexOutOfBoundsException{
		try {
			brailleCell[index] = value;
	    }
		catch (ArrayIndexOutOfBoundsException e) {	
	        return;
	    }		
	}

	public int getBrailleCell(int index){
		return brailleCell[index];
	}
	
	public void clearBrailleCell(){
		for (int i = 0; i<=5; i++){
			setBrailleCell(i, 0);
		}
	}
	
	public boolean isEmpty(){
		int contador = 0;
		for (int i = 0; i<=5; i++){
			if (getBrailleCell(i) == 0){
				contador++;
			}
		}
		if (contador == 6){
			return true;
		}
		return false;
	}
}