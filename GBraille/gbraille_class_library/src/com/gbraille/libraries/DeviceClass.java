package com.gbraille.libraries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.provider.Settings;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import java.lang.reflect.Field;

public class DeviceClass {
	/** 
	 * getMaxCPUFreqMHz
	 *     Retrieves the CPU frequency (in MHZ)
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return CPU frequency
	 */
    public int getMaxCPUFreqMHz() {
        int maxFreq = -1;
        try {
            RandomAccessFile reader = new RandomAccessFile( "/sys/devices/system/cpu/cpu0/cpufreq/stats/time_in_state", "r" );
            boolean done = false;
            while ( ! done ) {
                String line = reader.readLine();
                if ( null == line ) {
                    done = true;
                    break;
                }
                String[] splits = line.split( "\\s+" );
                assert ( splits.length == 2 );
                int timeInState = Integer.parseInt( splits[1] );
                if ( timeInState > 0 ) {
                    int freq = Integer.parseInt( splits[0] ) / 1000;
                    if ( freq > maxFreq ) {
                        maxFreq = freq;
                    }
                }
            }
            reader.close();
        } 
        catch ( IOException ex ) {
            ex.printStackTrace();
        }
        return maxFreq;
    }
    
    /** 
     * getNumberProcessors
	 *     Retrieves the number of processors
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return number of processors
	 */
    public int getNumberProcessors(){
    	return Runtime.getRuntime().availableProcessors();
    }
    
    /**
     * getRAMInfo
	 *     Retrieves the total RAM memory
	 * @author Antonio Rodrigo
	 * @version	1.0
	 * @return Total RAM Memory 
	 */    
    @SuppressLint("NewApi")
	public long getRAMInfo(){
    	String str1 = "/proc/meminfo";
    	String str2;        
    	String[] arrayOfString;
    	long initial_memory = 0;
    	try {
    	    FileReader localFileReader = new FileReader(str1);
    	    BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
    	    str2 = localBufferedReader.readLine();//meminfo
    	    arrayOfString = str2.split("\\s+");
    	    //total Memory
    	    initial_memory = Integer.valueOf(arrayOfString[1]).intValue();   
    	    initial_memory = initial_memory/1024;
    	    localBufferedReader.close();
    	} 
    	catch (IOException e){}
    	return initial_memory;
    }
    
    /** 
     * getDeviceModel
	 *     Retrieves the Device model
	 * @author Antonio Rodrigo
	 * @version	1.0
	 * @return Device model
	 */
    public String getDeviceModel(){
    	return android.os.Build.MODEL;
    }
    
    /** 
     * getDeviceManufacturer
	 *    Retrieves the device manufacturer
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return Device Manufacturer 
	 */
    public String getDeviceManufacturer(){
    	return android.os.Build.MANUFACTURER;
    }
    
    /**
     * getDeviceId
	 *     Retrieves the Device ID
	 * @author Antonio Rodrigo
	 * @version	1.0
	 * @return Device ID
	 */
    public String getDeviceId(){
    	return android.os.Build.DEVICE;
    }
    
    /**
     * getKernelVersion
	 *     Retrieves the kernel version
	 * @author Antonio Rodrigo
	 * @version	1.0
	 * @return kernel version
	 */
    public String getKernelVersion(){
    	return System.getProperty("os.version");
    }
    
    /**
     * getAndroidVersion
	 *     Retrieves the current Android version
	 * @author      Antonio Rodrigo
	 * @version		1.0
	 * @return		Android version
	 */
    public String getAndroidVersion(){
    	return Build.VERSION.RELEASE;
    }
    
    /** 
     * isThisDeviceATablet
	 *     Check if the device is a tablet
	 * @author Antonio Rodrigo
	 * @param  context
	 * 		     application context
	 * @version	1.0
	 * @return true or false
	 */
    public boolean isThisDeviceATablet(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= 11) { // honeycomb
            // test screen size, use reflection because isLayoutSizeAtLeast is only available since 11
            Configuration con = context.getResources().getConfiguration();
            try {
                Method mIsLayoutSizeAtLeast = con.getClass().getMethod("isLayoutSizeAtLeast", int.class);
                Boolean r = (Boolean) mIsLayoutSizeAtLeast.invoke(con, 0x00000004); // Configuration.SCREENLAYOUT_SIZE_XLARGE
                return r;
            }
            catch (Exception x) {
                x.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    public void turnBluetoothOnOff(String value){
    	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    	if (value.equalsIgnoreCase("on")){
    		if (! mBluetoothAdapter.isEnabled()) {
    			mBluetoothAdapter.enable();
    		}
    	}
    	else{
    		if (mBluetoothAdapter.isEnabled()) {
    			mBluetoothAdapter.disable();
    		}
    	}
    }
    
    public void turnWifiOnOff(Context context, String value) {
    	WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    	if (value.equalsIgnoreCase("on")){
    		if (! wifiManager.isWifiEnabled() ){
    			wifiManager.setWifiEnabled(true);
    		}
    	}
    	else{
    		if (wifiManager.isWifiEnabled()){
    			wifiManager.setWifiEnabled(false);
    		}
    	}
    }
    
    public void turnMobileDataOnOff(Context context, String value) {
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class conmanClass = null;
        Object iConnectivityManager = null;
        Class iConnectivityManagerClass = null;
        Method setMobileDataEnabledMethod = null;
        Field iConnectivityManagerField = null;
        
		try {
			conmanClass = Class.forName(conman.getClass().getName());
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        
		try {
			iConnectivityManagerField = conmanClass.getDeclaredField("mService");
			iConnectivityManagerField.setAccessible(true);
		} 
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}                
        
		try {
			iConnectivityManager = iConnectivityManagerField.get(conman);
		} 
		catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		try {
			iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
        
		try {
			setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
		} 
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
        setMobileDataEnabledMethod.setAccessible(true);
        boolean enabled;
        if (value.equals("on")){
        	enabled = true;
        }
        else{
        	enabled = false;
        }
        
        try {
			setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
		} 
        catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
        catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
        catch (InvocationTargetException e) {
			e.printStackTrace();
		}
    }
    
    public float getBatteryLevel(Context context) {
        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float value;
        // Error checking that probably isn't needed but I added just in case.
        if(level == -1 || scale == -1) {
        	value = 50.0f;
        }

        value = ((float)level / (float)scale) * 100.0f;
        System.out.println("BATERIA = " + value);
        return value;
    }
}