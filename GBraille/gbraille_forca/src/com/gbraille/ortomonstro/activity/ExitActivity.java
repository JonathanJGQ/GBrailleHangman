package com.gbraille.ortomonstro.activity;

import java.util.Locale;

import com.gbraille.ortomonstro.MainFunctions;
import com.gbraille.ortomonstro.R;

import de.akquinet.android.androlog.Log;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.os.Build;

public class ExitActivity extends AccessibleAbstractActivityLinearTemplate {

	@Override
	protected void setOnClickForButtons() {
		/* write text - onclick event */
		this.buttons[0].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), MainScreenActivity.class);
				startActivity(i);
				MainFunctions.selectedOption = 1;
				destroyServices();
				finish();
			}
		});

		/* message box - onclick event */
		this.buttons[1].setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				destroyServices();
				MainFunctions.selectedOption = 2;
				finish();
			}
		});
		
		/* one tick sound */
		for (int i = 0; i < this.totalButtons; i++){
			final String desc = buttons[i].getTag().toString();
			buttons[i].setOnFocusChangeListener(new View.OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) {
						Log.i(TAG, "Button " + desc + " was selected");
						logFunctions.logTextFile("Button " + desc + " was selected");
						speakButton(desc, 1.0f, 1.0f);						
					}
				}
			});
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		String systemLanguage = Locale.getDefault().getLanguage();
		
		String pt = "pt";
		String en = "en";
		
		if (systemLanguage.equalsIgnoreCase(pt)) {
			screenName = "Deseja sair? Escolha: Sim ou não.";
		} else if (systemLanguage.equals(en)) {
			screenName = "Did you wish get out? Choose: Yes or no.";
		} else {
			screenName = "Quieres dejar? Elija sí o no.";
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	protected LinearLayout getLayout() {
		return (LinearLayout) this.findViewById(R.id.layoutExitActivity);		
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.activity_exit;
	}

	@Override
	protected Activity getActivity() {
		return ExitActivity.this;
	}

	@Override
	protected void setViews() {
		buttons = new ImageButton[totalButtons];
		buttons[0] = (ImageButton) findViewById(R.id.botaoSim);
		buttons[1] = (ImageButton) findViewById(R.id.botaoNao);		
	}

	@Override
	protected void setNumberButtons() {
		this.totalButtons = 2;
	}

	
	@Override
	protected void setTts() {
		if( Build.VERSION.SDK_INT  >= 15 ){
        	final String thisTAG = this.TAG;
            myTTS.setOnUtteranceProgressListener(new UtteranceProgressListener() {            	
                @Override
                public void onDone(String utteranceId){
                	Log.i(thisTAG, "Text to speech finished");                	
                }
 
                @Override
                public void onError(String utteranceId){
                	Log.i(thisTAG, "Error in processing Text to speech");
                }
 
                @Override
                public void onStart(String utteranceId){
                	Log.i(thisTAG, "Started speaking");
                }
            });
        }		
	}
}