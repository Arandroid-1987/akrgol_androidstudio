package com.arandroid.risultatilive.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arandroid.risultatilive.R;
import com.arandroid.risultatilive.core.Risultato;

import java.util.List;

public class RisArrayAdapter extends ArrayAdapter<Risultato> {
    private List<Risultato> items;

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

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                row = inflater.inflate(R.layout.chartrow, parent, false);
            }
        }

        Risultato item = getItem(position);

        if (item != null && row != null) {
            TextView itemDate = row.findViewById(R.id.textViewDate);
            TextView itemName = row.findViewById(R.id.LibroText);
            TextView itemRis = row.findViewById(R.id.textView1);

            itemName.setText(item.getMatch());
            itemRis.setText(item.getRisultato());
            itemDate.setText(item.getDate());
        }

        return row;
    }
}
