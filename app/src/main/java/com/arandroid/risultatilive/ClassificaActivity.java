package com.arandroid.risultatilive;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arandroid.risultatilive.core.GlobalState;
import com.arandroid.risultatilive.core.Squadra;
import com.arandroid.risultatilive.ui.ClassArrayAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ClassificaActivity extends Activity implements
		OnItemClickListener, OnClickListener {
	private ProgressDialog p;
	private List<Squadra> listaRis = new ArrayList<Squadra>();
	private ListView listView;
	private ArrayAdapter<Squadra> arrayAdapter;
	private String url;
	private Button aggiorna;
	private GlobalState gs;
	private TextView tvv;
	private InterstitialAd interstitialAd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classifica);

		url = getIntent().getStringExtra("url");
		listView = (ListView) findViewById(R.id.ListView);
		int layoutID = R.layout.notizia;
		arrayAdapter = new ClassArrayAdapter(ClassificaActivity.this, layoutID,
				listaRis);
		gs = (GlobalState) getApplication();
		tvv = (TextView) findViewById(R.id.textdata);

		aggiorna = (Button) findViewById(R.id.buttonaggiorna);
		aggiorna.setOnClickListener(this);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(this);
		p = new ProgressDialog(ClassificaActivity.this);

		// Create an ad.
	    interstitialAd = new InterstitialAd(this);
	    interstitialAd.setAdUnitId(GlobalState.AD_UNIT_ID_INTERSTITIAL);
	    
	    // Create ad request.
	    AdRequest adRequest = new AdRequest.Builder().build();

	    // Begin loading your interstitial.
	    interstitialAd.loadAd(adRequest);
	    
	   
	    
	    // Set the AdListener.
	    interstitialAd.setAdListener(new AdListener() {
	    	public void onAdLoaded(){
	               displayInterstitial();
	               CaricaRis cl = new CaricaRis();
	               new Thread(cl).start();
	          }
	    });
	    
		p.show();
		p.setCancelable(false);
		p.setMessage("Caricamento in corso");

	}

	public void displayInterstitial() {
	    if (interstitialAd.isLoaded() && !gs.isAdsBannerShown()) {
	    	interstitialAd.show();
	    	gs.setAdsBannerShown(true);
	    }
	  }
	
	class CaricaRis implements Runnable {

		@Override
		public void run() {

			load();
		}

	}

	private void load() {
		
		final List<Squadra> li = gs.getClassifica(url);

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listaRis.clear();
				listaRis.addAll(li);
				if (arrayAdapter != null)
					arrayAdapter.notifyDataSetChanged();
				p.dismiss();
				tvv.setText(gs.getOraClass());
				if (li.isEmpty()) {
					Toast.makeText(ClassificaActivity.this, "Errore di connessione", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position,
			long arg3) {
		if (parent.equals(listView)) {
			visualizzaInfoSquadra(position);
		}
	}

	private void visualizzaInfoSquadra(int i) {
		Squadra s = listaRis.get(i);
		// String link = s.getLink();
		TaskInfo ti = new TaskInfo(s, ClassificaActivity.this);
		ti.execute();
	}

	@Override
	public void onClick(View arg0) {
		p.show();
		gs.setClassificaaggiornata(true);
		CaricaRis cl = new CaricaRis();
		new Thread(cl).start();
		tvv.setText(gs.getOraClass());

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		gs.reset();
	}
}
