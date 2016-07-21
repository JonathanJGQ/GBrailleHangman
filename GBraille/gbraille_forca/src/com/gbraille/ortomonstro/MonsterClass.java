package com.gbraille.ortomonstro;

import com.gbraille.ortomonstro.R;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class MonsterClass {
	private ImageView primeiro, segundo, terceiro;
	
	public MonsterClass(Activity activity){
		primeiro     = (ImageView) activity.findViewById(R.id.primeiroMonstro);
		segundo    = (ImageView) activity.findViewById(R.id.segundoMonstro);
		terceiro  = (ImageView) activity.findViewById(R.id.terceiroMonstro);
	}
	
	public ImageView getPrimeiro() {
		return primeiro;
	}

	public void setPrimeiro(ImageView primeiro) {
		this.primeiro = primeiro;
	}

	public ImageView getSegundo() {
		return segundo;
	}

	public void setSegundo(ImageView segundo) {
		this.segundo = segundo;
	}

	public ImageView getTerceiro() {
		return terceiro;
	}

	public void setTerceiro(ImageView terceiro) {
		this.terceiro = terceiro;
	}
	
	public void setAllMonstersInvisible(){
		primeiro.setVisibility(View.INVISIBLE);
		segundo.setVisibility(View.INVISIBLE);
		terceiro.setVisibility(View.INVISIBLE);
	}
	
	public void setAllMonstersVisible(){
		primeiro.setVisibility(View.VISIBLE);
		segundo.setVisibility(View.VISIBLE);
		terceiro.setVisibility(View.VISIBLE);
	}
	
	public void showVisibleMonsters(int monsters,int erros){
		switch(monsters){
			case 0:
				if(erros == 1){
					primeiro.setImageResource(R.drawable.monstro_parte1);
				}
				else if(erros == 2){
					primeiro.setImageResource(R.drawable.monstro_parte2);
				}
				if(erros != 0){
					primeiro.setVisibility(View.VISIBLE);
				}
				break;
			case 1:
				if(erros == 3){
					segundo.setImageResource(R.drawable.monstro_parte1);
				}
				else if(erros == 4){
					segundo.setImageResource(R.drawable.monstro_parte2);
				}
				if(erros != 2){
					primeiro.setVisibility(View.VISIBLE);
					segundo.setVisibility(View.VISIBLE);
				}
				break;
			case 2:
				if(erros == 5){
					terceiro.setImageResource(R.drawable.monstro_parte1);
				}
				else if(erros == 6){
					terceiro.setImageResource(R.drawable.monstro_parte2);
				}
				if(erros != 4){
					primeiro.setVisibility(View.VISIBLE);
					segundo.setVisibility(View.VISIBLE);
					terceiro.setVisibility(View.VISIBLE);
				}
				break;
		}
	}
}