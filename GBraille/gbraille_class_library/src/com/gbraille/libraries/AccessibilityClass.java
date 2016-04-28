package com.gbraille.libraries;

import android.content.Context;
import android.provider.Settings;

public class AccessibilityClass {
	/**
	 * Ckeck if Talkback is activated
	 *
	 * @author      Antonio Rodrigo
	 * @version     1.0
	 * @return		true or false 
	 */
	public boolean isAccessibilityEnabled(Context context) {
        final int enabled = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED, -1);
        return (enabled == 1);
    }
}
