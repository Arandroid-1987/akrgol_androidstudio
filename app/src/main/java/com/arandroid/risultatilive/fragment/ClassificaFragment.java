package com.arandroid.risultatilive.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

public class ClassificaFragment extends Fragment implements OnItemClickListener, OnClickListener {
    private ProgressDialog p;
    private List<Squadra> listaRis = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<Squadra> arrayAdapter;
    private String url;
    private GlobalState gs;
    private TextView tvv;
    private InterstitialAd interstitialAd;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classifica, container, false);

        activity = getActivity();
        if (activity != null) {
            url = activity.getIntent().getStringExtra("url");
            listView = view.findViewById(R.id.ListView);
            int layoutID = R.layout.notizia;

            arrayAdapter = new ClassArrayAdapter(activity, layoutID, listaRis);
            gs = (GlobalState) activity.getApplication();
            tvv = view.findViewById(R.id.textdata);

            Button aggiorna = view.findViewById(R.id.buttonaggiorna);
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
                public void onAdLoaded() {
                    displayInterstitial();
                    CaricaRis cl = new CaricaRis();
                    new Thread(cl).start();
                }
            });

            p.show();
            p.setCancelable(false);
            p.setMessage("Caricamento in corso");
        }
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

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listaRis.clear();
                listaRis.addAll(li);
                listaRis.add(new Squadra());
                if (arrayAdapter != null) {
                    arrayAdapter.notifyDataSetChanged();
                }
                p.dismiss();
                tvv.setText(gs.getOraClass());
                if (li.isEmpty()) {
                    Toast.makeText(activity, "Errore di connessione", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long arg3) {
        if (parent.equals(listView)) {
            visualizzaInfoSquadra(position);
        }
    }

    private void visualizzaInfoSquadra(int i) {
        Squadra s = listaRis.get(i);
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
