package com.gbraille.libraries;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.immersion.uhl.Launcher;

public class HapticClass {
	private Launcher launcher;
	/* Handler used by Haptic SDK through which response listener callbacks will be invoked. */
	private Handler handler = new Handler();	
	private int extraVibration;
	private int vibrationRepetition;
	private final int vibrationTypeSimple = Launcher.LONG_TRANSITION_RAMP_DOWN_100;
	private final int vibrationTypeOnExit = Launcher.TRIPLE_STRONG_CLICK_100;
	private int vibrationTypeSelected;
	private final String TAG = "HapticFunctions";
	private Context context;
	
	public void setExtraVibration(int valor){
		extraVibration = valor;
	}
	public int getExtraVibration(){
		return extraVibration;
	}
	public void setVibrationRepetition(int valor){
		extraVibration = valor;
	}
	public int getVibrationRepetition(){
		return extraVibration;
	}
	
	public HapticClass(Context context){
		setExtraVibration(0);
		setVibrationRepetition(0);
		this.context = context;
	}
	
	/**
	 * vibrationRunnable
	 *     Haptic SDK Runnable
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @see http://www.immersion.com/products/haptic-sdk/
	 */
	Runnable vibrationRunnable = new Runnable() {
		  @Override
		  public void run() {
			launcher.play(vibrationTypeSelected);			
			if (vibrationRepetition < getExtraVibration()) {
				setVibrationRepetition(getVibrationRepetition() + 1);
		        handler.postDelayed(vibrationRunnable, 500);
		    }
		  }
	};
	
	/**
	 * vibrate
	 *     Vibration function
	 * @author Antonio Rodrigo
	 * @param  qtde
	 * 		     how many times the cellphone will vibrate
	 * @version 1.0
	 */
	public boolean vibrate(int qtde){
		if (qtde >= 1){
			setExtraVibration(qtde - 1);
			vibrationTypeSelected = vibrationTypeSimple;
			handler.postDelayed(vibrationRunnable, 500);
			return true;
		}
		return false;
	}
	
	public boolean vibrateOnExit(){
		setExtraVibration(0);
		vibrationTypeSelected = vibrationTypeOnExit;
		handler.postDelayed(vibrationRunnable, 500);
		return true;
	}
	
	public boolean setHapticLauncher(){
		/* Haptic SDK Launcher */
		try{
			launcher = new Launcher(context);
			return true;
		}
		catch(RuntimeException e){
			Log.i(TAG, e.getMessage());
		}
		return false;
	}
}