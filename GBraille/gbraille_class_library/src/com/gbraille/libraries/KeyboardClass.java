package com.gbraille.libraries;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;

public class KeyboardClass {
	/* keyboard parameters */
	private String keyboardPackageName;
	private String keyboardActivityName;
	/* Response code to indicate that Braille Keyboard was successfull */
	private final int BRAILLE_KEYBOARD_RESPONSE = 1;
	
	public KeyboardClass(){
		
	}
	
	public void setKeyboardPackageName(String value){
		keyboardPackageName = value;
	}
	
	public void setKeyboardActivityName(String value){
		keyboardActivityName = value;
	}
	
	public String getKeyboardPackageName(){
		return keyboardPackageName;
	}
	
	public String getKeyboardActivityName(){
		return keyboardActivityName;
	}
	
	public int getKeyboardResponse(){
		return BRAILLE_KEYBOARD_RESPONSE;
	}
	
	public void readKeyboardXMLFile(Context context) {
		try {
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();    
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); 
		    Document doc = dBuilder.parse(context.getAssets().open("keyboard.xml"));
		    doc.getDocumentElement().normalize();

		    NodeList nodeContatos = doc.getElementsByTagName("entrada");

		    Node item = nodeContatos.item(0);

		    if (item.getNodeType() == Node.ELEMENT_NODE) {
		        Element element = (Element) item;
		        Node packageKey = element.getElementsByTagName("package").item(0).getChildNodes().item(0);
		        Node activityKey = element.getElementsByTagName("activity").item(0).getChildNodes().item(0);
		        
		        keyboardPackageName = packageKey.getNodeValue().toString();		    
		        keyboardActivityName = activityKey.getNodeValue().toString();
		    }
		}
		catch(Exception e){
		     e.printStackTrace();
		}
	}
}
