package com.gbraille.forca.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.gbraille.forca.R;
import com.gbraille.forca.activity.SingleMenuItemActivity;
import com.gbraille.forca.activity.XMLParser;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class UpdateQuestion extends ListActivity {

	// All static variables
	static final String URL = "http://pesquisa.great.ufc.br/greattourv2/Banco.xml";
	// XML node keys
	static final String KEY_ITEM = "questao";
	static final String KEY_NAME = "question";
	static final String KEY_ANSWER = "answer";
	static final String KEY_MISS = "missingCharPos"; 
	static final String KEY_DIFICULDADE = "dificuldade";
	static final String KEY_JOGO = "jogo";
	static final String KEY_LINGUA = "lingua";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element

		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		// looping through all item nodes <item>
		for (int i = 0; i < nl.getLength(); i++) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// adding each child node to HashMap key => value
			map.put(KEY_ITEM, parser.getValue(e, KEY_ITEM));
			map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
			map.put(KEY_ANSWER, parser.getValue(e, KEY_ANSWER));
			map.put(KEY_MISS, parser.getValue(e, KEY_MISS));
			map.put(KEY_DIFICULDADE, parser.getValue(e, KEY_DIFICULDADE));
			map.put(KEY_JOGO, parser.getValue(e, KEY_JOGO));
			map.put(KEY_LINGUA, parser.getValue(e, KEY_LINGUA));
			
			// adding HashList to ArrayList
			menuItems.add(map);
		}

		// Adding menuItems to ListView
		ListAdapter adapter = new SimpleAdapter(this, menuItems,
				R.layout.list_item,
				new String[] { KEY_ITEM,KEY_NAME,KEY_ANSWER, KEY_MISS, KEY_DIFICULDADE, KEY_JOGO, KEY_LINGUA }, new int[] {
						R.id.questao, R.id.question, R.id.answer, R.id.missingCharPos, R.id.dificuldade, R.id.jogo, R.id.lingua});

		setListAdapter(adapter);

		// selecting single ListView item
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String questao = ((TextView) view.findViewById(R.id.questao)).getText().toString();
				String question = ((TextView) view.findViewById(R.id.question)).getText().toString();
				String answer = ((TextView) view.findViewById(R.id.answer)).getText().toString();
				String missingCharPos = ((TextView) view.findViewById(R.id.missingCharPos)).getText().toString();
				String dificuldade = ((TextView) view.findViewById(R.id.dificuldade)).getText().toString();
				String jogo = ((TextView) view.findViewById(R.id.jogo)).getText().toString();
				String lingua = ((TextView) view.findViewById(R.id.lingua)).getText().toString();
				
				// Starting new intent
				Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
				in.putExtra(KEY_ITEM, questao);
				in.putExtra(KEY_NAME, question);
				in.putExtra(KEY_ANSWER, answer);
				in.putExtra(KEY_MISS, missingCharPos);
				in.putExtra(KEY_DIFICULDADE, dificuldade);
				in.putExtra(KEY_JOGO, jogo);
				in.putExtra(KEY_LINGUA, lingua);
				startActivity(in);

			}
		});
	}
}