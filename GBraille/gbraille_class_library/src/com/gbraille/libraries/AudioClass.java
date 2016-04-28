package com.gbraille.libraries;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;

public class AudioClass {
	private AudioManager audioManager;
	private Context context;
	
	// soundpool vars
	private SoundPool soundPool;
	private boolean loaded = false;
	private int introSoundId, oneTickId, twoTicksId;
	
	public int getIntroSoundId(){
		return introSoundId;
	}
	
	public void setIntroSoundId(int soundId){
		introSoundId = soundPool.load(context, soundId, 1);
	}
	
	public int getOneTickId(){
		return oneTickId;
	}
	
	public void setOneTickId(int soundId){
		oneTickId = soundPool.load(context, soundId, 1);
	}
	
	public int getTwoTicksId(){
		return twoTicksId;
	}
	
	public void setTwoTicksId(int soundId){
		twoTicksId = soundPool.load(context, soundId, 1);
	}	
	
	public SoundPool getSoundPool(){
		return soundPool;
	}
	
	public int getAudioState(){		
		return audioManager.getRingerMode();		
	}
	
	public AudioManager getAudioManager(){
		return audioManager;
	}
	
	public AudioClass(Context context){
		this.context = context;
		audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
		    public void onLoadComplete(SoundPool sound, int sampleId,int status) {
		       loaded = true;
		    }		    
		});	
	}
	
	public void setAudioStateToNormal(){
		switch( getAudioState() ){
			case AudioManager.RINGER_MODE_NORMAL:
				break;
			case AudioManager.RINGER_MODE_SILENT:
				getAudioManager().setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				break;
			case AudioManager.RINGER_MODE_VIBRATE:
				getAudioManager().setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				break;
		}
	}
	
	/**
	 * set media volume to 100%
	 *
	 * @author      Antonio Rodrigo
	 * @version     1.0 
	 */
	public void setMaxAudioVolume(){		
		int maxVolume = getAudioManager().getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolume = getAudioManager().getStreamVolume(AudioManager.STREAM_MUSIC);
		if (currentVolume < maxVolume){
			getAudioManager().setStreamVolume(AudioManager.STREAM_MUSIC, getAudioManager().getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);			
		}
	}
	
	/**
	 * decreaseVolume
	 *    reduces the audio volume
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	public boolean decreaseVolume(Context context){
		AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);		
		if (currentVolume > (maxVolume/2) ){
			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
			return true;
		}
		return false;
	}
	
	/**
	 * increaseVolume
	 *    increase the audio volume
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	public boolean increaseVolume(Context context){
		AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		if (currentVolume < maxVolume){
			audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
			return true;
		}
		return false;
	}
	
	/**
	 * playSound
	 *     Plays a mp3 sound
	 * @param soundId
	 *     Sound File reference (ID)
	 * @param leftVolume
	 *     left volume between 0.0f and 1.0f 
	 * @param rightVolume
	 *     right volume between 0.0f and 1.0f
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	public void playSound(int soundId, float leftVolume, float rightVolume){						
		if (loaded) {
			soundPool.play(soundId, leftVolume, rightVolume, 0, 0, 1f);
		}
	}
	
	/**
	 * play1Tick
	 *     plays a tick sound when user touches 1x a button
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	public void play1Tick(float volumeLeft, float volumeRight){
		playSound(getOneTickId(), volumeLeft, volumeRight);
	}
	
	/**
	 * play2Tick
	 *     plays a tick sound when user touches 2x a button
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	public void play2Ticks(){
		playSound(getTwoTicksId(), 1.0f, 1.0f);
	}
}