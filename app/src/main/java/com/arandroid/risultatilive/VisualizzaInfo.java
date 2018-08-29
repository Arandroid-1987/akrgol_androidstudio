package com.arandroid.risultatilive;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class VisualizzaInfo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infosquadra);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageView logo = findViewById(R.id.imageView1);
        TextView nomesquadra = findViewById(R.id.nomeSquadra);
        TextView posizione = findViewById(R.id.valuePos);
        TextView incontri = findViewById(R.id.valueInc);
        TextView vinte = findViewById(R.id.valueWin);
        TextView pareggiate = findViewById(R.id.ValueTied);
        TextView perse = findViewById(R.id.valueLost);
        TextView ultimoMatch = findViewById(R.id.valueLastMatch);

        Intent intent = getIntent();
        String[] s = intent.getStringArrayExtra("Squadra");

        Bitmap b = intent.getParcelableExtra("img");
        logo.setImageBitmap(b);
        nomesquadra.setText(s[0]);
        posizione.setText(s[1]);
        incontri.setText(s[2]);
        vinte.setText(s[3]);
        pareggiate.setText(s[4]);
        perse.setText(s[5]);
        ultimoMatch.setText(s[6]);
    }
}
