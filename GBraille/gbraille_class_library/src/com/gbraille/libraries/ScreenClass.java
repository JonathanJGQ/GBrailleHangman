package com.gbraille.libraries;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

public class ScreenClass {
    /**
     * getScreenDensity
	 *     retrieves the screen dimensions
	 * @author Antonio Rodrigo
	 * @version 1.0
	 * @return density
	 * @see http://stefan222devel.blogspot.com.br/2012/10/android-screen-densities-sizes.html
	 */
    public String getScreenDensity(Activity activity){
    	float density = activity.getResources().getDisplayMetrics().density;
    	if (density >= 4.0) {
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            return "xxhdpi";
        }
        if (density >= 2.0) {
            return "xhdpi";
        }
        if (density >= 1.5) {
            return "hdpi";
        }
        if (density >= 1.0) {
            return "mdpi";
        }
        return "ldpi";
    }
    
    /**
     * toggleFullScreen
	 *     Toggle application to fullscreen
	 * @author Antonio Rodrigo
	 * @param activity
	 * 			activity that will be in fullscreen mode 
	 * @version 1.0
	 */
    @SuppressLint({ "InlinedApi", "NewApi" })
	public void toggleFullScreen(Activity activity) {
		/* disable the status bar - android <= 4.0 */
		if (Build.VERSION.SDK_INT < 16) {
			activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		/* disable the status bar - android >= 4.1 */
		else{
			int mUIFlag = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
						| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
						| View.SYSTEM_UI_FLAG_FULLSCREEN  // hide status bar              
		                | View.SYSTEM_UI_FLAG_LOW_PROFILE
		                | View.SYSTEM_UI_FLAG_IMMERSIVE;
			activity.getWindow().getDecorView().setSystemUiVisibility(mUIFlag);
		}
	}
}