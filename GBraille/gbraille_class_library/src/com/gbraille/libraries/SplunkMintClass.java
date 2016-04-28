package com.gbraille.libraries;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.util.Log;

public class SplunkMintClass {
	/* The API key of the BugSense app. */
	private String SPLUNKMINT_KEY_APP;
	
	/* Bugsense XML File */
	private String splunkMintXmlFile = "splunkmint.xml";
	
	/* Context */
	private Context context;
	
	/* Tag for logging */
	private final String TAG = "SplunkMintFunctions";
	
	public SplunkMintClass(Context context){
		this.context = context;
		readBugsenseXMLFile();
	}
	
	public String getBugsenseKey(){
		return SPLUNKMINT_KEY_APP;
	}
	
	public void setBugsenseKey(String key){
		SPLUNKMINT_KEY_APP = key;
	}
	
	public String getBugsenseXmlFile(){
		return splunkMintXmlFile;
	}
	
	public boolean readBugsenseXMLFile() {
		try {
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();    
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder(); 
		    Document doc = dBuilder.parse(context.getAssets().open(splunkMintXmlFile));
		    doc.getDocumentElement().normalize();

		    NodeList nodeContatos = doc.getElementsByTagName("entrada");

		    Node item = nodeContatos.item(0);

		    if (item.getNodeType() == Node.ELEMENT_NODE) {
		        Element element = (Element) item;
		        Node nodeAppKey = element.getElementsByTagName("appkey").item(0).getChildNodes().item(0);
		        SPLUNKMINT_KEY_APP = nodeAppKey.getNodeValue().toString();
		        Log.i(TAG, "Bugsense Key has been read");
		        return true;
		    }
		    Log.i(TAG, "An error occurred during file reading");
		    return false;
		}
		catch(Exception e){
			Log.i(TAG,"Exception: " + e);
		}
	    return false;
	}
}