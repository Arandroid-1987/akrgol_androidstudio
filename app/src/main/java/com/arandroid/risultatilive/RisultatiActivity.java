package com.arandroid.risultatilive;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arandroid.risultatilive.core.GlobalState;
import com.arandroid.risultatilive.core.Risultati;
import com.arandroid.risultatilive.core.Risultato;
import com.arandroid.risultatilive.ui.RisArrayAdapter;

public class RisultatiActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {
	private ProgressDialog p;
	private List<Risultato> listaRis = new ArrayList<Risultato>();
	private ListView listView;
	private ArrayAdapter<Risultato> arrayAdapter;
	private String url;
	private Button aggiorna;
	private GlobalState gs;
	private TextView tvv;
	private Spinner selector;
	private final static int NUMERO_GIORNATE = 34;
	
	private List<Risultato> li;
	private Risultati ris;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		url = getIntent().getStringExtra("url");

		setContentView(R.layout.risultati);
		listView = (ListView) findViewById(R.id.ListView);
		int layoutID = R.layout.chartrow;
		arrayAdapter = new RisArrayAdapter(RisultatiActivity.this, layoutID,
				listaRis);
		gs = (GlobalState) getApplication();
		p = new ProgressDialog(RisultatiActivity.this);
		CaricaRis cl = new CaricaRis();
		tvv = (TextView) findViewById(R.id.textdata);

		selector = (Spinner) findViewById(R.id.spinner1);

		// codice spinner
		ArrayAdapter<CharSequence> adp = new ArrayAdapter<CharSequence>(
				RisultatiActivity.this, android.R.layout.simple_spinner_item);

		for (int i = 0; i < NUMERO_GIORNATE; i++) {
			adp.add("" + (i+1));
		}

		adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		selector.setAdapter(adp);
		

		aggiorna = (Button) findViewById(R.id.buttonaggiorna);
		aggiorna.setOnClickListener(this);
		listView.setAdapter(arrayAdapter);
		new Thread(cl).start();
		p.show();
		p.setCancelable(false);
		p.setMessage("Caricamento in corso");

	}

	class CaricaRis implements Runnable {
		private int giornata;

		public CaricaRis() {
			giornata = -1;
		}

		public CaricaRis(int giornata) {
			this.giornata = giornata;
		}

		@Override
		public void run() {

			load(giornata);
		}

	}

	private void load(int day) {
		// RisultatiReader rr = new RisultatiReader(this.getResources());
		// List<Risultato> li = rr.read(url);
		ris = null;
		li = null;
		if (day == -1) {
			ris = gs.getRisultati(url);
			li = ris.getList();
		}
		else{
			ris = gs.getRisultati(url+"?match_day="+day);
			li = ris.getList();
		}

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				listaRis.clear();
				listaRis.addAll(li);
				String giornata = ris.getGiornata();
				int gior;
				try {
					gior = Integer.parseInt(giornata);
				} catch (NumberFormatException ex) {
					gior = 1;
				}
				selector.setSelection(gior-1, false);
				selector.setOnItemSelectedListener(RisultatiActivity.this);
				if (arrayAdapter != null)
					arrayAdapter.notifyDataSetChanged();
				p.dismiss();
				tvv.setText(gs.getOra());
				if (li==null || li.isEmpty()) {
					Toast.makeText(RisultatiActivity.this,
							"Errore di connessione", Toast.LENGTH_SHORT).show();
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
	public void onBackPressed() {
		super.onBackPressed();
		gs.reset();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		p.show();
		gs.setRisultatiaggiornati(true);
		CaricaRis cl = new CaricaRis(position+1);
		new Thread(cl).start();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
