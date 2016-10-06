package com.arandroid.risultatilive.ui;

import java.util.ArrayList;
import java.util.List;

import com.arandroid.risultatilive.R;
import com.arandroid.risultatilive.core.Squadra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassArrayAdapter extends ArrayAdapter<Squadra> {
	private ImageView notiziaImage;
	private TextView itemName;
	private TextView itemRis;
	private List<Squadra> items = new ArrayList<Squadra>();

	public ClassArrayAdapter(Context context, int textViewResourceId,
			List<Squadra> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
	}

	public int getCount() {
		return this.items.size();
	}

	public Squadra getItem(int index) {
		return this.items.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.notizia, parent, false);
		}

		Squadra item = getItem(position);

		itemName = (TextView) row.findViewById(R.id.notiziaTitle);
		itemRis = (TextView) row.findViewById(R.id.notiziaAuthor);

		itemName.setText((position+1)+". "+item.getNome());
		itemRis.setText(item.getScore());
		notiziaImage = (ImageView) row.findViewById(R.id.notiziaImage);
		if (item.getLogo() != null)
			notiziaImage.setImageBitmap(item.getLogo());
		return row;
	}
}
