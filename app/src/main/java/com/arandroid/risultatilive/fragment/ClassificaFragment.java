package com.arandroid.risultatilive.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arandroid.risultatilive.R;
import com.arandroid.risultatilive.TaskInfo;
import com.arandroid.risultatilive.core.GlobalState;
import com.arandroid.risultatilive.core.Squadra;
import com.arandroid.risultatilive.ui.ClassArrayAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ClassificaFragment extends Fragment implements
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

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.classifica, container, false);

		url = getActivity().getIntent().getStringExtra("url");
		listView = (ListView) view.findViewById(R.id.ListView);
		int layoutID = R.layout.notizia;

		arrayAdapter = new ClassArrayAdapter(getActivity(), layoutID,
				listaRis);
		gs = (GlobalState) getActivity().getApplication();
		tvv = (TextView) view.findViewById(R.id.textdata);

		aggiorna = (Button) view.findViewById(R.id.buttonaggiorna);
		aggiorna.setOnClickListener(this);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(this);
		p = new ProgressDialog(getActivity());

		// Create an ad.
	    interstitialAd = new InterstitialAd(getActivity());
	    interstitialAd.setAdUnitId(GlobalState.AD_UNIT_ID_INTERSTITIAL);

	    // Create ad request.
	    AdRequest adRequest = new AdRequest.Builder().addTestDevice("C35CE538C6C4C40FBC539EA1081013D7").build();

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

		return view;

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

        getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listaRis.clear();
				listaRis.addAll(li);
                listaRis.add(new Squadra());
				if (arrayAdapter != null)
					arrayAdapter.notifyDataSetChanged();
				p.dismiss();
				tvv.setText(gs.getOraClass());
				if (li.isEmpty()) {
					Toast.makeText(getActivity(), "Errore di connessione", Toast.LENGTH_SHORT)
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
		TaskInfo ti = new TaskInfo(s, getActivity());
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
}
