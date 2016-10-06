package com.arandroid.risultatilive;

import com.arandroid.risultatilive.core.Squadra;
import com.arandroid.risultatilive.net.InfoReader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class TaskInfo extends AsyncTask<Void, Void, Void> {
	private ProgressDialog pd;
	private Squadra s;
	private Context c;

	public TaskInfo(Squadra s, Context c) {
		this.s = s;
		this.c = c;

	}

	@Override
	protected void onPreExecute() {
		pd = new ProgressDialog(c);
		pd.setCancelable(false);
		pd.show();
		pd.setMessage("Caricamento in corso...");
	}

	@Override
	protected void onPostExecute(Void result) {
		pd.dismiss();
		String[] sa = { s.getNome(), s.getPosizione(), s.getIncontri(), s.getWon(),
				s.getTied(), s.getLost(), s.getProssimoMatch() };
		Intent intent = new Intent(c, VisualizzaInfo.class);
		intent.putExtra("Squadra", sa);
		intent.putExtra("img", s.getLogo());
		c.startActivity(intent);
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		InfoReader tr = new InfoReader(s);
		s = tr.read();
		return null;
	}

}
