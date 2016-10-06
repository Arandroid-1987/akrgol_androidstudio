package com.arandroid.risultatilive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class VisualizzaInfo extends Activity {
	private TextView nomesquadra;
	private TextView Posizione;
	private TextView Incontri;
	private TextView Vinte;
	private TextView Pareggiate;
	private TextView Perse;
	private TextView UltimoMatch;
	private ImageView logo;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infosquadra);
		logo = (ImageView) findViewById(R.id.imageView1);
		nomesquadra = (TextView) findViewById(R.id.nomeSquadra);
		Posizione = (TextView) findViewById(R.id.valuePos);
		Incontri = (TextView) findViewById(R.id.valueInc);
		Vinte = (TextView) findViewById(R.id.valueWin);
		Pareggiate = (TextView) findViewById(R.id.ValueTied);
		Perse = (TextView) findViewById(R.id.valueLost);
		UltimoMatch = (TextView) findViewById(R.id.valueLastMatch);
		Intent intent = getIntent();
		String[] s = intent.getStringArrayExtra("Squadra");
		Bitmap b = ((Bitmap) intent.getParcelableExtra("img"));
		logo.setImageBitmap(b);
		nomesquadra.setText(s[0]);
		Posizione.setText(s[1]);
		Incontri.setText(s[2]);
		Vinte.setText(s[3]);
		Pareggiate.setText(s[4]);
		Perse.setText(s[5]);
		UltimoMatch.setText(s[6]);
		
	}
}
