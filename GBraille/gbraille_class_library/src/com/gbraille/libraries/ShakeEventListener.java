/**
 * Copyright (C) 2014 Antonio Rodrigo
 * 
 * This file is part of GBraille Project.
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; If not, see <http://www.gnu.org/licenses/>.
 */

package com.gbraille.libraries;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * From:
 * {@link http://stackoverflow.com/questions/2317428/android-i-want-to-shake-it
 * thanks for {@link http://stackoverflow.com/users/387064/brian-d}
 * */
public class ShakeEventListener implements SensorEventListener {
	/** Minimum movement force to consider. */
	private static final int MIN_FORCE = 10;

	/**
	 * Minimum times in a shake gesture that the direction of movement needs to
	 * change.
	 */
	private static final int MIN_DIRECTION_CHANGE = 3;

	/** Maximum pause between movements. */
	private static final int MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE = 200;

	/** Maximum allowed time for shake gesture. */
	private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;

	/** Time when the gesture started. */
	private long mFirstDirectionChangeTime = 0;

	/** Time when the last movement started. */
	private long mLastDirectionChangeTime;

	/** How many movements are considered so far. */
	private int mDirectionChangeCount = 0;

	/** The last x position. */
	private float lastX = 0;

	/** The last y position. */
	private float lastY = 0;

	/** The last z position. */
	private float lastZ = 0;

	/** OnShakeListener that is called when shake is detected. */
	private OnShakeListener mShakeListener;

	/**
	 * Interface for shake gesture.
	 */
	public interface OnShakeListener {

		/**
		 * Called when shake gesture is detected.
		 */
		void onShake();
	}

	public void setOnShakeListener(OnShakeListener listener) {
		mShakeListener = listener;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void onSensorChanged(SensorEvent se) {
		// get sensor data		
		float x = se.values[SensorManager.DATA_X];
		float y = se.values[SensorManager.DATA_Y];
		float z = se.values[SensorManager.DATA_Z];

		// calculate movement
		float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

		if (totalMovement > MIN_FORCE) {

			// get time
			long now = System.currentTimeMillis();

			// store first movement time
			if (mFirstDirectionChangeTime == 0) {
				mFirstDirectionChangeTime = now;
				mLastDirectionChangeTime = now;
			}

			// check if the last movement was not long ago
			long lastChangeWasAgo = now - mLastDirectionChangeTime;
			if (lastChangeWasAgo < MAX_PAUSE_BETHWEEN_DIRECTION_CHANGE) {

				// store movement data
				mLastDirectionChangeTime = now;
				mDirectionChangeCount++;

				// store last sensor data
				lastX = x;
				lastY = y;
				lastZ = z;

				// check how many movements are so far
				if (mDirectionChangeCount >= MIN_DIRECTION_CHANGE) {

					// check total duration
					long totalDuration = now - mFirstDirectionChangeTime;
					if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
						mShakeListener.onShake();
						resetShakeParameters();
					}
				}

			} else {
				resetShakeParameters();
			}
		}
	}

	/**
	 * Resets the shake parameters to their default values.
	 */
	private void resetShakeParameters() {
		mFirstDirectionChangeTime = 0;
		mDirectionChangeCount = 0;
		mLastDirectionChangeTime = 0;
		lastX = 0;
		lastY = 0;
		lastZ = 0;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

}