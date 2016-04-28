package com.gbraille.keyboard;

public class Braille {
	private final String BRAILLE_999999 = "999999";
	
	private final String BRAILLE_000000 = "000000";
	private final String BRAILLE_001000 = "001000";
	private final String BRAILLE_001001 = "001001";
	private final String BRAILLE_000001 = "000001";
	
	private final String BRAILLE_010000 = "010000";
	private final String BRAILLE_011000 = "011000";
	private final String BRAILLE_011001 = "011001";
	private final String BRAILLE_010001 = "010001";
	
	private final String BRAILLE_010010 = "010010";
	private final String BRAILLE_011010 = "011010";
	private final String BRAILLE_011011 = "011011";
	private final String BRAILLE_010011 = "010011";
	
	private final String BRAILLE_000010 = "000010";
	private final String BRAILLE_001010 = "001010";
	private final String BRAILLE_001011 = "001011";
	private final String BRAILLE_000011 = "000011";
	
	// --------------------------------------
	
	private final String BRAILLE_100000 = "100000";
	private final String BRAILLE_101000 = "101000";
	private final String BRAILLE_101001 = "101001";
	private final String BRAILLE_100001 = "100001";
	
	private final String BRAILLE_110000 = "110000";
	private final String BRAILLE_111000 = "111000";
	private final String BRAILLE_111001 = "111001";
	private final String BRAILLE_110001 = "110001";
	
	private final String BRAILLE_110010 = "110010";
	private final String BRAILLE_111010 = "111010";
	private final String BRAILLE_111011 = "111011";
	private final String BRAILLE_110011 = "110011";
	
	private final String BRAILLE_100010 = "100010";
	private final String BRAILLE_101010 = "101010";
	private final String BRAILLE_101011 = "101011";
	private final String BRAILLE_100011 = "100011";	
	// --------------------------------------
	
	private final String BRAILLE_100100 = "100100";
	private final String BRAILLE_101100 = "101100";
	private final String BRAILLE_101101 = "101101";
	private final String BRAILLE_100101 = "100101";
	
	private final String BRAILLE_110100 = "110100";
	private final String BRAILLE_111100 = "111100";
	private final String BRAILLE_111101 = "111101";
	private final String BRAILLE_110101 = "110101";
	
	private final String BRAILLE_110110 = "110110";
	private final String BRAILLE_111110 = "111110";
	private final String BRAILLE_111111 = "111111";
	private final String BRAILLE_110111 = "110111";
	
	private final String BRAILLE_100110 = "100110";
	private final String BRAILLE_101110 = "101110";
	private final String BRAILLE_101111 = "101111";
	private final String BRAILLE_100111 = "100111";
	
	// --------------------------------------
	
	private final String BRAILLE_000100 = "000100";
	private final String BRAILLE_001100 = "001100";
	private final String BRAILLE_001101 = "001101";
	private final String BRAILLE_000101 = "000101";
	
	private final String BRAILLE_010100 = "010100";
	private final String BRAILLE_011100 = "011100";
	private final String BRAILLE_011101 = "011101";
	private final String BRAILLE_010101 = "010101";
	
	private final String BRAILLE_010110 = "010110";
	private final String BRAILLE_011110 = "011110";
	private final String BRAILLE_011111 = "011111";
	private final String BRAILLE_010111 = "010111";
	
	private final String BRAILLE_000110 = "000110";
	private final String BRAILLE_001110 = "001110";
	private final String BRAILLE_001111 = "001111";
	private final String BRAILLE_000111 = "000111";
	
	/* keyboard flags */
	private int totalCapitalFlag = 0;
	private int totalNumberFlag = 0;
	private int totalTranslineacaoFlag = 0;
	
	// braille error code
	public String getBraille_999999(){
		return BRAILLE_999999;
	}
	public String getBraille_000000(){
		return BRAILLE_000000;
	}
	public String getBraille_001000(){
		return BRAILLE_001000;
	}
	public String getBraille_001001(){
		return BRAILLE_001001;
	}
	public String getBraille_000001(){
		return BRAILLE_000001;
	}
	public String getBraille_010000(){
		return BRAILLE_010000;
	}
	public String getBraille_011000(){
		return BRAILLE_011000;
	}
	public String getBraille_011001(){
		return BRAILLE_011001;
	}
	public String getBraille_010001(){
		return BRAILLE_010001;
	}
	public String getBraille_010010(){
		return BRAILLE_010010;
	}
	public String getBraille_011010(){
		return BRAILLE_011010;
	}
	public String getBraille_011011(){
		return BRAILLE_011011;
	}
	public String getBraille_010011(){
		return BRAILLE_010011;
	}
	public String getBraille_000010(){
		return BRAILLE_000010;
	}
	public String getBraille_001010(){
		return BRAILLE_001010;
	}
	public String getBraille_001011(){
		return BRAILLE_001011;
	}
	public String getBraille_000011(){
		return BRAILLE_000011;
	}
	
	
	// --------------------------------------
	public String getBraille_100000(){
		return BRAILLE_100000;
	}
	public String getBraille_101000(){
		return BRAILLE_101000;
	}
	public String getBraille_101001(){
		return BRAILLE_101001;
	}
	public String getBraille_100001(){
		return BRAILLE_100001;
	}
	public String getBraille_110000(){
		return BRAILLE_110000;
	}
	public String getBraille_111000(){
		return BRAILLE_111000;
	}
	public String getBraille_111001(){
		return BRAILLE_111001;
	}
	public String getBraille_110001(){
		return BRAILLE_110001;
	}
	public String getBraille_110010(){
		return BRAILLE_110010;
	}
	public String getBraille_111010(){
		return BRAILLE_111010;
	}
	public String getBraille_111011(){
		return BRAILLE_111011;
	}
	public String getBraille_110011(){
		return BRAILLE_110011;
	}
	public String getBraille_100010(){
		return BRAILLE_100010;
	}
	public String getBraille_101010(){
		return BRAILLE_101010;
	}
	public String getBraille_101011(){
		return BRAILLE_101011;
	}
	public String getBraille_100011(){
		return BRAILLE_100011;
	}		
	// --------------------------------------
	
	public String getBraille_100100(){
		return BRAILLE_100100;
	}
	public String getBraille_101100(){
		return BRAILLE_101100;
	}
	public String getBraille_101101(){
		return BRAILLE_101101;
	}
	public String getBraille_100101(){
		return BRAILLE_100101;
	}
	public String getBraille_110100(){
		return BRAILLE_110100;
	}
	public String getBraille_111100(){
		return BRAILLE_111100;
	}
	public String getBraille_111101(){
		return BRAILLE_111101;
	}
	public String getBraille_110101(){
		return BRAILLE_110101;
	}
	public String getBraille_110110(){
		return BRAILLE_110110;
	}
	public String getBraille_111110(){
		return BRAILLE_111110;
	}
	public String getBraille_111111(){
		return BRAILLE_111111;
	}
	public String getBraille_110111(){
		return BRAILLE_110111;
	}
	public String getBraille_100110(){
		return BRAILLE_100110;
	}
	public String getBraille_101110(){
		return BRAILLE_101110;
	}
	public String getBraille_101111(){
		return BRAILLE_101111;
	}
	public String getBraille_100111(){
		return BRAILLE_100111;
	}
	// --------------------------------------
	
	public String getBraille_000100(){
		return BRAILLE_000100;
	}
	public String getBraille_001100(){
		return BRAILLE_001100;
	}
	public String getBraille_001101(){
		return BRAILLE_001101;
	}
	public String getBraille_000101(){
		return BRAILLE_000101;
	}
	public String getBraille_010100(){
		return BRAILLE_010100;
	}
	public String getBraille_011100(){
		return BRAILLE_011100;
	}
	public String getBraille_011101(){
		return BRAILLE_011101;
	}
	public String getBraille_010101(){
		return BRAILLE_010101;
	}	
	public String getBraille_010110(){
		return BRAILLE_010110;
	}
	public String getBraille_011110(){
		return BRAILLE_011110;
	}
	public String getBraille_011111(){
		return BRAILLE_011111;
	}
	public String getBraille_010111(){
		return BRAILLE_010111;
	}
	public String getBraille_000110(){
		return BRAILLE_000110;
	}	
	public String getBraille_001110(){
		return BRAILLE_001110;
	}
	public String getBraille_001111(){
		return BRAILLE_001111;
	}
	public String getBraille_000111(){
		return BRAILLE_000111;
	}
	
	public void setFlagNumber(int valor){
    	totalNumberFlag = valor;
    }
	public int getFlagNumber(){
    	return totalNumberFlag;
    }
    
    public void setFlagCapital(int valor){
    	totalCapitalFlag = valor;    	
    }
    public int getFlagCapital(){
    	return totalCapitalFlag;
    }
    
    public void setFlagTranslineacao(int valor){
    	totalTranslineacaoFlag = valor;    	
    }
    public int getFlagTranslineacao(){
    	return totalTranslineacaoFlag;
    }
    
    public String getBraillePattern(int dots){
    	switch(dots){
    		case 0:
    			return getBraille_000000();
    		case 1:
    			return getBraille_100000();    			
    		case 2:
    			return getBraille_010000();
    		case 12:
    			return getBraille_110000();
    		case 3:
    			return getBraille_001000();
    		case 13:
    			return getBraille_101000();
    		case 23:
    			return getBraille_011000();
    		case 123:
    			return getBraille_111000();
    		case 4:
    			return getBraille_000100();
    		case 14:
    			return getBraille_100100();
    		case 24:
    			return getBraille_010100();
    		case 124:
    			return getBraille_110100();
    		case 34:
    			return getBraille_001100();
    		case 134:
    			return getBraille_101100();
    		case 234:
    			return getBraille_011100();
    		case 1234:
    			return getBraille_111100();
    		case 5:
    			return getBraille_000010();
    		case 15:
    			return getBraille_100010();
    		case 25:
    			return getBraille_010010();
    		case 125:
    			return getBraille_110010();
    		case 35:
    			return getBraille_001010();
    		case 135:
    			return getBraille_101010();
    		case 235:
    			return getBraille_011010();
    		case 1235:
    			return getBraille_111010();
    		case 45:
    			return getBraille_000110();
    		case 145:
    			return getBraille_100110();
    		case 245:
    			return getBraille_010110();
    		case 1245:
    			return getBraille_110110();
    		case 345:
    			return getBraille_001110();
    		case 1345:
    			return getBraille_101110();
    		case 2345:
    			return getBraille_011110();
    		case 12345:
    			return getBraille_111110();
    		case 6:
    			return getBraille_000001();
    		case 16:
    			return getBraille_100001();
    		case 26:
    			return getBraille_010001();
    		case 126:
    			return getBraille_110001();
    		case 36:
    			return getBraille_001001();
    		case 136:
    			return getBraille_101001();
    		case 236:
    			return getBraille_011001();
    		case 1236:
    			return getBraille_111001();
    		case 46:
    			return getBraille_000101();
    		case 146:
    			return getBraille_100101();
    		case 246:
    			return getBraille_010101();
    		case 1246:
    			return getBraille_110101();
    		case 346:
    			return getBraille_001101();
    		case 1346:
    			return getBraille_101101();
    		case 2346:
    			return getBraille_011101();
    		case 12346:
    			return getBraille_111101();
    		case 56:
    			return getBraille_000011();
    		case 156:
    			return getBraille_100011();
    		case 256:
    			return getBraille_010011();
    		case 1256:
    			return getBraille_110011();
    		case 356:
    			return getBraille_001011();
    		case 1356:
    			return getBraille_101011();
    		case 2356:
    			return getBraille_011011();
    		case 12356:
    			return getBraille_111011();
    		case 456:
    			return getBraille_000111();
    		case 1456:
    			return getBraille_100111();
    		case 2456:
    			return getBraille_010111();
    		case 12456:
    			return getBraille_110111();
    		case 3456:
    			return getBraille_001111();
    		case 13456:
    			return getBraille_101111();
    		case 23456:
    			return getBraille_011111();
    		case 123456:
    			return getBraille_111111();
    	}
    	return "";
    }
}