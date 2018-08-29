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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arandroid.risultatilive.R;
import com.arandroid.risultatilive.core.GlobalState;
import com.arandroid.risultatilive.core.Risultati;
import com.arandroid.risultatilive.core.Risultato;
import com.arandroid.risultatilive.ui.RisArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class RisultatiFragment extends Fragment implements OnClickListener, OnItemSelectedListener {
    private ProgressDialog p;
    private List<Risultato> listaRis = new ArrayList<>();
    private ArrayAdapter<Risultato> arrayAdapter;
    private String url;
    private GlobalState gs;
    private TextView tvv;
    private Spinner selector;
    private final static int NUMERO_GIORNATE = 34;

    private List<Risultato> li;
    private Risultati ris;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.risultati, container, false);
        activity = getActivity();
        if (activity != null) {
            url = activity.getIntent().getStringExtra("url");

            ListView listView = view.findViewById(R.id.ListView);
            int layoutID = R.layout.chartrow;
            arrayAdapter = new RisArrayAdapter(activity, layoutID, listaRis);
            gs = (GlobalState) activity.getApplication();
            p = new ProgressDialog(activity);
            CaricaRis cl = new CaricaRis();
            tvv = view.findViewById(R.id.textdata);

            selector = view.findViewById(R.id.spinner1);

            // codice spinner
            ArrayAdapter<CharSequence> adp = new ArrayAdapter<CharSequence>(activity, android.R.layout.simple_spinner_item);

            for (int i = 0; i < NUMERO_GIORNATE; i++) {
                adp.add("" + (i + 1));
            }

            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            selector.setAdapter(adp);

            Button aggiorna = view.findViewById(R.id.buttonaggiorna);
            aggiorna.setOnClickListener(this);
            listView.setAdapter(arrayAdapter);
            new Thread(cl).start();
            p.show();
            p.setCancelable(false);
            p.setMessage("Caricamento in corso");
        }
        return view;
    }

    class CaricaRis implements Runnable {
        private int giornata;

        CaricaRis() {
            giornata = -1;
        }

        CaricaRis(int giornata) {
            this.giornata = giornata;
        }

        @Override
        public void run() {
            load(giornata);
        }
    }

    private void load(int day) {
        ris = null;
        li = null;
        if (day == -1) {
            ris = gs.getRisultati(url);
            li = ris.getList();
        } else {
            ris = gs.getRisultati(url + "?match_day=" + day);
            li = ris.getList();
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listaRis.clear();
                listaRis.addAll(li);
                listaRis.add(new Risultato());
                String giornata = ris.getGiornata();
                int gior;
                try {
                    gior = Integer.parseInt(giornata.substring(0, giornata.indexOf(".")));
                } catch (NumberFormatException ex) {
                    gior = 1;
                }
                selector.setSelection(gior - 1, false);
                selector.setOnItemSelectedListener(RisultatiFragment.this);
                if (arrayAdapter != null) {
                    arrayAdapter.notifyDataSetChanged();
                }
                p.dismiss();
                tvv.setText(gs.getOra());
                if (li == null || li.isEmpty()) {
                    Toast.makeText(activity, "Errore di connessione", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        p.show();
        gs.setRisultatiaggiornati(true);
        CaricaRis cl = new CaricaRis();
        new Thread(cl).start();
        tvv.setText(gs.getOra());
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
        p.show();
        gs.setRisultatiaggiornati(true);
        CaricaRis cl = new CaricaRis(position + 1);
        new Thread(cl).start();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

}
