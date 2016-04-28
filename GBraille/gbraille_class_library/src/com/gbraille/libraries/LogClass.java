package com.gbraille.libraries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import de.akquinet.android.androlog.Log;
import android.os.Environment;

public class LogClass {
	/* log vars */
	private boolean logActivated;
	private final String appFolder = "gbraille_logs";	
	private String logFileString;
	private final File sdCardDirectory = Environment.getExternalStorageDirectory();
	private File logDirectory;
	private File logFile;
	private DeviceClass deviceClass = new DeviceClass();
	private final String TAG = "LogFunctions";
	private String prefixLogFile;
	
	private Calendar calendar;
	private int year, month, day, hour, minute, second, millis;
	
	public LogClass(boolean value, String prefixLogFile){
		setLogActivated(value);
		
		this.prefixLogFile = prefixLogFile;
		
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH); // Note: zero based!
		day = calendar.get(Calendar.DAY_OF_MONTH);
		hour = calendar.get(Calendar.HOUR_OF_DAY);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);
		
		logFileString = String.format(getPrefixLogFile() + "_%d_%d_%d_%d_%d_%d.log", day, month + 1, year, hour, minute, second);		
		logDirectory   = new File(sdCardDirectory + File.separator + appFolder);
		logFile  = new File(logDirectory,logFileString);
		
		if (getLogDirectory().exists() == false){
			try{
				logDirectory.mkdir();
				Log.i(TAG, LogMessages.MSG_LOG_ERROR_LOG_FILE_CREATED_SUCCESSFULLY);
			}
			catch (Exception e){
			    Log.i(TAG, LogMessages.MSG_LOG_ERROR_LOG_FOLDER_CREATION);
			    return;
			}
		}
		   
		if (!logFile.exists()){
		    try{
		         logFile.createNewFile();
		         Log.i(TAG, LogMessages.MSG_LOG_ERROR_LOG_FILE_CREATED_SUCCESSFULLY);
		    }
		    catch (Exception e){
		    	 Log.i(TAG, ""+e.getMessage());
		    	 return;
		    }
		}
		
		deviceInfo();
	}
	
	public void setPrefixLogFile(String prefix){
		prefixLogFile = prefix;
	}
	public String getPrefixLogFile(){
		return prefixLogFile;
	}
	
	public boolean getLogActivated(){
		return logActivated;
	}
	
	public void setLogActivated(boolean value){
		logActivated = value;
	}
	
	public String getAppFolder(){
		return appFolder;
	}
	
	public String getLogFileString(){
		return logFileString;
	}
	
	public File getSdCardDirectory(){
		return sdCardDirectory;
	}
	
	public File getLogDirectory(){
		return logDirectory;
	}
	
	public File getLogFile(){
		return logFile;
	}
	
	/**
	 * Send a string to a log file created in the SDCard
	 *
	 * @author      Antonio Rodrigo
	 * @param       text
	 * 					What will be write in the file
	 * @version     1.0
	 */
	public boolean logTextFile(String text){
	   try{
		  Calendar now = Calendar.getInstance();
		  year = now.get(Calendar.YEAR);
		  month = now.get(Calendar.MONTH); // Note: zero based!
		  day = now.get(Calendar.DAY_OF_MONTH);
		  hour = now.get(Calendar.HOUR_OF_DAY);
		  minute = now.get(Calendar.MINUTE);
		  second = now.get(Calendar.SECOND);
		  millis = now.get(Calendar.MILLISECOND);
		  String myData = String.format("%d-%02d-%02d %02d:%02d:%02d.%03d", year, month + 1, day, hour, minute, second, millis);

		  BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
	      buf.append("------------------");
	      buf.newLine();
	      buf.append(myData);
	      buf.newLine();
	      buf.append(text);
	      buf.newLine();
	      buf.close();
	   }
	   catch (Exception e){
		   Log.i(TAG, LogMessages.MSG_LOG_ERROR_LOG_FILE_EDITION);
		   return false;
	   }
	   
	   return true;
	}
	
	/**
	 * get the device characteristics and send to the log file
	 * 
	 * @author Antonio Rodrigo
	 * @version 1.0
	 */
	public void deviceInfo() {
		if (getLogActivated() == true){
			String deviceId = deviceClass.getDeviceId(); // device ID
			String deviceModel = deviceClass.getDeviceModel(); // device model
			String deviceManufacturer = deviceClass.getDeviceManufacturer(); // device manufacturer
			String totalRam = String.valueOf(deviceClass.getRAMInfo()); // RAM total
			String processorsNumber = String.valueOf(deviceClass.getNumberProcessors()); // number of processors
			String processorFrequency = String.valueOf(deviceClass.getMaxCPUFreqMHz()) + " MHZ"; // processors frequency
			String androidVersion = deviceClass.getAndroidVersion();
			String separator = System.getProperty("line.separator");
			String txt = "DEVICE ID: " + deviceId + separator
				+ "DEVICE MODEL:" + deviceModel + separator
				+ "DEVICE MANUFACTURER: " + deviceManufacturer + separator
				+ "ANDROID VERSION: " + androidVersion + separator
				+ "RAM (MB): " + totalRam + separator
				+ "No. PROCESSORS: " + processorsNumber + separator
				+ "PROCESSOR FREQUENCY: " + processorFrequency + separator;
			logTextFile(txt);
		}
	}
}