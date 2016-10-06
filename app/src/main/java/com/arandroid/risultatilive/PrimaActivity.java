package com.arandroid.risultatilive;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arandroid.risultatilive.core.GlobalState;
import com.arandroid.risultatilive.net.NetworkUtility;
import com.arandroid.risultatilive.ui.DialogBuilder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class PrimaActivity extends Activity implements OnClickListener {
	private View eccellenzaView;
	private View primaView;
	private View secondaView;
	private View serieDView;
	private View aboutView;
	private LinearLayout adsLL;
	private GlobalState gs;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.prima);

		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.window_title);

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

		// banners
		//manager = BannerManager.getInstance();
//		containerLayout = (LinearLayout) findViewById(R.id.bannerLL);
//		GlobalState gs = (GlobalState) getApplication();
//		gs.setupBanners(this);
		
		gs = (GlobalState) getApplication();

		// banners -- caricamento da sito arandroid
		/*manager = BannerManager.getInstance();
		containerLayout = (LinearLayout) findViewById(R.id.bannerLL);
		GlobalState gs = (GlobalState) getApplication();
		gs.setupBanners(this);*/
		
		// banners -- caricamento da ADMOB
		adsLL = (LinearLayout) findViewById(R.id.bannerLL);
		AdView adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(GlobalState.AD_UNIT_ID_BANNER);
		// Effettuiamo la lookup della ViewGroup che conterra' il nostro banner
		// Nel nostro caso e' un LinearLayout con id linearLayout
		// Aggiungiamo la view adView al LinearLayout
		adsLL.addView(adView);
		// Richiediamo un nuovo banner al server di AdMod
		AdRequest adRequest = new AdRequest.Builder().build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);
		
		
		
	}

	@Override
	public void onBackPressed() {
		Dialog d = DialogBuilder.createExitDialog(this);
		d.show();
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
					url = "http://www.radioakr.it/sport/risultati-e-classifiche/prima-categoria-girone-a/";
				} else if (v.equals(eccellenzaView)) {
					url = "http://www.radioakr.it/sport/risultati-e-classifiche/eccellenza/";
				} else if (v.equals(serieDView)) {
					url = "http://www.radioakr.it/sport/risultati-e-classifiche/serie-d-girone-i/";
				}else if (v.equals(secondaView)) {
					url = "http://www.radioakr.it/sport/risultati-e-classifiche/calcio-seconda-cat-girone-a/";
				}
				Intent intent = new Intent(this, TabLayoutActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);
			}
		}
	}

}
