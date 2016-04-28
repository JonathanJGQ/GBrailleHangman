package com.gbraille.libraries;

import java.util.HashMap;

public class Narrator {
	private HashMap<String, String> map = new HashMap<String, String>();
	
	/**
     * Class Constructor
	 */
	public Narrator(){
		createMap();
	}
	
	/**
     * createMap
	 *     fills an map with brazilian phonetics
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	private void createMap(){
		map.clear();
		
		map.put("a", "a");
		map.put("b", "b");
		map.put("c", "c");
		map.put("d", "d");
		map.put("e", "héh");
		map.put("f", "f");
		map.put("g", "g");
		map.put("h", "h");
		map.put("i", "i");
		map.put("j", "j");
		map.put("k", "k");
		map.put("l", "l");
		map.put("m", "m");
		map.put("n", "n");
		map.put("o", "hóh");
		map.put("p", "p");
		map.put("q", "q");
		map.put("r", "r");
		map.put("s", "s");
		map.put("t", "t");
		map.put("u", "u");
		map.put("v", "v");
		map.put("w", "w");
		map.put("x", "x");
		map.put("y", "ipsilon");
		map.put("z", "z");
		map.put("ç", "cedilha");
		
		map.put("A", "a");
		map.put("B", "b");
		map.put("C", "c");
		map.put("D", "d");
		map.put("E", "héh");
		map.put("F", "f");
		map.put("G", "g");
		map.put("H", "h");
		map.put("I", "i");
		map.put("J", "j");
		map.put("K", "k");
		map.put("L", "l");
		map.put("M", "m");
		map.put("N", "n");
		map.put("O", "hóh");
		map.put("P", "p");
		map.put("Q", "q");
		map.put("R", "r");
		map.put("S", "s");
		map.put("T", "t");
		map.put("U", "u");
		map.put("V", "v");
		map.put("W", "w");
		map.put("X", "x");
		map.put("Y", "ipsilon");
		map.put("Z", "z");
		map.put("ç", "cedilha");
		
		map.put("1", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");
		map.put("5", "5");
		map.put("6", "6");
		map.put("7", "7");
		map.put("8", "8");
		map.put("9", "9");
		map.put("0", "0");		
		
		map.put("á", "a acento agudo");
		map.put("é", "hé acento agudo");
		map.put("í", "i acento agudo");
		map.put("ó", "óh acento agudo");
		map.put("ú", "u acento agudo");
		
		map.put("Á", "a acento agudo");
		map.put("É", "hé acento agudo");
		map.put("Í", "i acento agudo");
		map.put("Ó", "óh acento agudo");
		map.put("Ú", "u acento agudo");
		
		map.put("ã", "a til");
		map.put("õ", "óh til");
		
		map.put("Ã", "a til");
		map.put("Õ", "óh til");
		
		map.put("â", "a circunflexo");
		map.put("ê", "héh circunflexo");
		map.put("ô", "óh circunflexo");
		
		map.put("Â", "a circunflexo");
		map.put("Ê", "héh circunflexo");
		map.put("Ô", "o circunflexo");
		
		map.put("ü", "u trema");
		
		map.put("à", "a grave");
		map.put("À", "a grave");
		
		map.put("?", "interrogassão");
		map.put("!", "exclamassão");
		map.put(",", "vírgula");
		map.put(".", "ponto");
		map.put(";", "ponto e vírgula");
		map.put("-", "hífen");
		map.put(":", "dois pontos");
		map.put("$", "cifrão");
		map.put("*", "asterisco");
		map.put("+", "mais");
		map.put("~", "til");
		map.put("@", "arroba");
		map.put("=", "igual");		
		map.put(" ", "espasso");
		map.put("ESPACO", "espasso");
		map.put("RESTITUIDOR", "restituidor");
		map.put("MAIUSCULA1", "uma letra maiúscula");
		map.put("MAIUSCULA2", "todas as letras maiúsculas");
		map.put("TRANSLINEACAO", "translineassão");
		map.put("NUMERO", "número");
	}
	
	/**
     * getMap
	 *     retrieves the phonetic map
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return phonetic map
	 */
	public HashMap<String,String> getMap(){
		return map;
	}
	
	/**
     * getTTS
	 *     retrieves the sound of the string
	 * @author Antonio Rodrigo
	 * @param str
	 * 			string 
	 * @version 1.0
	 * @return phonetic map
	 */
	public String getTTS(String str){
		if (map.get(str) == null){
			return "";
		}
		else{
			if (str.isEmpty() || str.equals("")){
				return "caractere não encontrado";
			}
			return (String) map.get(str);
		}
	}
}