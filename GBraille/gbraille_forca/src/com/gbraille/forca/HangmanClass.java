package com.gbraille.forca;

import com.gbraille.forca.R;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class HangmanClass {
	private ImageView head, tummy, leftarm, rightarm, leftleg, rightleg;
	
	public HangmanClass(Activity activity){
		head     = (ImageView) activity.findViewById(R.id.head);
		tummy    = (ImageView) activity.findViewById(R.id.tummy);
		leftarm  = (ImageView) activity.findViewById(R.id.lefthand);
		rightarm = (ImageView) activity.findViewById(R.id.righthand);
		leftleg  = (ImageView) activity.findViewById(R.id.leftleg);
		rightleg = (ImageView) activity.findViewById(R.id.rightleg);
	}

	public ImageView getHead() {
		return head;
	}

	public void setHead(ImageView head) {
		this.head = head;
	}

	public ImageView getTummy() {
		return tummy;
	}

	public void setTummy(ImageView tummy) {
		this.tummy = tummy;
	}

	public ImageView getLeftarm() {
		return leftarm;
	}

	public void setLeftarm(ImageView leftarm) {
		this.leftarm = leftarm;
	}

	public ImageView getRightarm() {
		return rightarm;
	}

	public void setRightarm(ImageView rightarm) {
		this.rightarm = rightarm;
	}

	public ImageView getLeftleg() {
		return leftleg;
	}

	public void setLeftleg(ImageView leftleg) {
		this.leftleg = leftleg;
	}

	public ImageView getRightleg() {
		return rightleg;
	}

	public void setRightleg(ImageView rightleg) {
		this.rightleg = rightleg;
	}
	
	public void setAllBodyPartsInvisible(){
		head.setVisibility(View.INVISIBLE);
		tummy.setVisibility(View.INVISIBLE);
		leftarm.setVisibility(View.INVISIBLE);
		rightarm.setVisibility(View.INVISIBLE);
		leftleg.setVisibility(View.INVISIBLE);
		rightleg.setVisibility(View.INVISIBLE);
	}
	
	public void setAllBodyPartsVisible(){
		head.setVisibility(View.VISIBLE);
		tummy.setVisibility(View.VISIBLE);
		leftarm.setVisibility(View.VISIBLE);
		rightarm.setVisibility(View.VISIBLE);
		leftleg.setVisibility(View.VISIBLE);
		rightleg.setVisibility(View.VISIBLE);
	}
	
	public void showVisibleBodyParts(int size){
		switch(size){
			case 1:
				head.setVisibility(View.VISIBLE);
				break;
			case 2:
				head.setVisibility(View.VISIBLE);
				tummy.setVisibility(View.VISIBLE);
				break;
			case 3:
				head.setVisibility(View.VISIBLE);
				tummy.setVisibility(View.VISIBLE);
				leftarm.setVisibility(View.VISIBLE);
				break;
			case 4:
				head.setVisibility(View.VISIBLE);
				tummy.setVisibility(View.VISIBLE);
				leftarm.setVisibility(View.VISIBLE);
				rightarm.setVisibility(View.VISIBLE);
				break;
			case 5:
				head.setVisibility(View.VISIBLE);
				tummy.setVisibility(View.VISIBLE);
				leftarm.setVisibility(View.VISIBLE);
				rightarm.setVisibility(View.VISIBLE);
				leftleg.setVisibility(View.VISIBLE);
				break;
			case 6:
				head.setVisibility(View.VISIBLE);
				tummy.setVisibility(View.VISIBLE);
				leftarm.setVisibility(View.VISIBLE);
				rightarm.setVisibility(View.VISIBLE);
				leftleg.setVisibility(View.VISIBLE);
				rightleg.setVisibility(View.VISIBLE);
				break;
		}
	}
}