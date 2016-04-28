package com.gbraille.libraries;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneClass {
	/**
     * getViberPhoneNumber
	 *     retrieves the viber username
	 * @author Antonio Rodrigo
	 * @param context
	 * 			application context
	 * @version 1.0
	 * @return phone number
	 */
	public String getViberPhoneNumber(Context context){
		String phoneNumber = "";
		AccountManager am = AccountManager.get(context);
		Account[] accounts = am.getAccounts();
		
		for (Account ac : accounts) {
		    if(ac.type.equals("com.viber.voip.account")){
		        phoneNumber = ac.name;
		        break;
		    }
		}
		return phoneNumber;
	}
	
	/**
     * getIMEI
	 *     retrieves the International Mobile Equipment Identity
	 * @author Antonio Rodrigo
 	 * @param context
	 * 			application context
	 * @version 1.0
	 * @return imei
	 */
	public String getIMEI(Context context){
	    TelephonyManager mngr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE); 
	    String imei = mngr.getDeviceId();
	    return imei;
	}
}