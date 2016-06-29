package com.gbraille.ortomonstro.activity;

import java.util.ArrayList;
import java.util.List;

import com.gbraille.ortomonstro.R;
import com.gbraille.ortomonstro.database.DbAdapter;
import com.gbraille.ortomonstro.database.DbHelper;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/*
 * OBS: Ver o Layout da listView em res/layout/listitem.xml
 */
public class DeleteQuestionActivity extends ListActivity {
	private DbAdapter source;
	ListView lstView;
	Cursor cursor;
	SimpleCursorAdapter adapter;
	int pos;
	AdapterView<?> av;
	PerguntasViewAdapter perguntasViewAdapter;
	
	public class PerguntasClass {
        String titulo;
        String pergunta;
    }
	
	public List<PerguntasClass> getDataForListView(){
		source = new DbAdapter(getApplicationContext());
		cursor = source.getAllQuestions();		
		
		List<PerguntasClass> codeLearnChaptersList = new ArrayList<PerguntasClass>();
		
		if (cursor.moveToFirst()) {
			do {
				PerguntasClass chapter = new PerguntasClass();
				chapter.titulo = "";
				chapter.pergunta = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_PERGUNTA));
				codeLearnChaptersList.add(chapter);
			}
			while (cursor.moveToNext());
		}
		return codeLearnChaptersList;
    }
	
	public class PerguntasViewAdapter extends BaseAdapter {
		List<PerguntasClass> PerguntasList = getDataForListView();
        
		@Override
        public int getCount() {
            return PerguntasList.size();
        }

        @Override
        public PerguntasClass getItem(int arg0) {
            return PerguntasList.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            if(arg1==null){
                LayoutInflater inflater = (LayoutInflater) DeleteQuestionActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                arg1 = inflater.inflate(R.layout.listitem, arg2,false);
            }

            TextView chapterName = (TextView)arg1.findViewById(R.id.textView1);
            TextView chapterDesc = (TextView)arg1.findViewById(R.id.textView2);

            PerguntasClass chapter = PerguntasList.get(arg0);

            chapterName.setText(chapter.titulo);
            chapterDesc.setText(chapter.pergunta);

            return arg1;
        }
    }
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_excluir_pergunta);		
		
		perguntasViewAdapter = new PerguntasViewAdapter();
		lstView = this.getListView();
		lstView.setAdapter(perguntasViewAdapter);
		
		List<PerguntasClass> listaDePerguntas = getDataForListView();
		
		lstView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				// Get the cursor, positioned to the corresponding row in the result set
				pos = position;
				av = a;
				
				AlertDialog.Builder myDialog = new AlertDialog.Builder(DeleteQuestionActivity.this);
				myDialog.setTitle(getString(R.string.txtConfirmacao));
				myDialog.setMessage(getString(R.string.txtConfirmarExclusaoPergunta));
						
				myDialog.setPositiveButton(getString(R.string.txtSim), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {			        	
				       try{
				       		Cursor listCursor = (Cursor) av.getItemAtPosition(pos);
							final int item_id = listCursor.getInt(listCursor.getColumnIndex(DbHelper.COLUMN_ID));
							source.deleteQuestion(item_id);
					        // update de list
					        cursor.requery();
					        adapter.notifyDataSetChanged();
					        Toast.makeText(DeleteQuestionActivity.this, getString(R.string.txtPerguntaExcluidaSucesso), Toast.LENGTH_LONG).show();
					   }
					   catch (Exception ex){
					   		Toast.makeText(DeleteQuestionActivity.this, getString(R.string.txtErroExcluirPergunta), Toast.LENGTH_LONG).show();
					   }
				}
			});
			myDialog.setNegativeButton(getString(R.string.txtNao), new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface arg0, int arg1) {
					            
				}
			});
			AlertDialog alerta = myDialog.create();
			alerta.show();			
			}
		});
		
	}
	
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent i = new Intent(DeleteQuestionActivity.this, MainScreenActivity.class);
		startActivity(i);
		finish();
	}
}