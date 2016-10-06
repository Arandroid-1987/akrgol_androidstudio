package com.arandroid.risultatilive.ui;

import java.util.ArrayList;
import java.util.List;

import com.arandroid.risultatilive.R;
import com.arandroid.risultatilive.core.Risultato;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RisArrayAdapter extends ArrayAdapter<Risultato> {
	private TextView itemName;
	private TextView itemDate;
	private TextView itemRis;
	private List<Risultato> items = new ArrayList<Risultato>();

	public RisArrayAdapter(Context context, int textViewResourceId,
			List<Risultato> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
	}

	public int getCount() {
		return this.items.size();
	}

	public Risultato getItem(int index) {
		return this.items.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.chartrow, parent, false);
		}

		Risultato item = getItem(position);

		itemDate = (TextView) row.findViewById(R.id.textViewDate);
		itemName = (TextView) row.findViewById(R.id.LibroText);
		itemRis = (TextView) row.findViewById(R.id.textView1);

		itemName.setText(item.getMatch());
		itemRis.setText(item.getRisultato());
		itemDate.setText(item.getDate());

		return row;
	}
}
