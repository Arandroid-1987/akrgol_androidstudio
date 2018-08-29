package com.arandroid.risultatilive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arandroid.risultatilive.core.GlobalState;
import com.arandroid.risultatilive.net.NetworkUtility;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class PrimaActivity extends AppCompatActivity implements OnClickListener {
	private View eccellenzaView;
	private View primaView;
	private View secondaView;
	private View serieDView;
	private View aboutView;
	private GlobalState gs;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_prima);

		eccellenzaView = findViewById(R.id.eccellenzaView);
		primaView = findViewById(R.id.primaView);
		serieDView = findViewById(R.id.serieDView);
		aboutView = findViewById(R.id.aboutView);
		secondaView = findViewById(R.id.secondaView);
		
		eccellenzaView.setOnClickListener(this);
		primaView.setOnClickListener(this);
		secondaView.setOnClickListener(this);
		serieDView.setOnClickListener(this);
		aboutView.setOnClickListener(this);
		
		gs = (GlobalState) getApplication();
		
		// banners -- caricamento da ADMOB
		LinearLayout adsLL = findViewById(R.id.bannerLL);
		AdView adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(GlobalState.AD_UNIT_ID_BANNER);
		// Effettuiamo la lookup della ViewGroup che conterra' il nostro banner
		// Nel nostro caso e' un LinearLayout con id linearLayout
		// Aggiungiamo la view adView al LinearLayout
		adsLL.addView(adView);
		// Richiediamo un nuovo banner al server di AdMod
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("C35CE538C6C4C40FBC539EA1081013D7").build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);
	}

	@Override
	public void onClick(View v) {
		gs.setAdsBannerShown(false);
		
		if (v.equals(aboutView)) {
			Intent intent = new Intent(this, AboutActivityComplete.class);
			startActivity(intent);
		} else {
			if (!NetworkUtility.reteFunzionante(this)) {
				Toast.makeText(this, "Connessione assente", Toast.LENGTH_SHORT).show();
			} else {
				String url = "";
				if (v.equals(primaView)) {
                    url = "http://www.radioakr.it/promozione-calabrese-20162017/";
				} else if (v.equals(eccellenzaView)) {
					url = "http://www.radioakr.it/eccellenza-calabrese-20132014/";
				} else if (v.equals(serieDView)) {
					url = "http://www.radioakr.it/sport/risultati-e-classifiche/serie-d-girone-i/";
				}else if (v.equals(secondaView)) {
					url = "http://www.radioakr.it/sport/risultati-e-classifiche/calcio-seconda-cat-girone-a/";
				}
				Intent intent = new Intent(this, LegaActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);
			}
		}
	}

}
