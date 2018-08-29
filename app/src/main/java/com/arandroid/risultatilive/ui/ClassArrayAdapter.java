package com.arandroid.risultatilive.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.arandroid.risultatilive.R;
import com.arandroid.risultatilive.core.Squadra;

import java.util.List;

public class ClassArrayAdapter extends ArrayAdapter<Squadra> {
    private List<Squadra> items;

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

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                row = inflater.inflate(R.layout.notizia, parent, false);
            }
        }

        Squadra item = getItem(position);
        if (item != null && row != null) {

            TextView itemName = row.findViewById(R.id.notiziaTitle);
            TextView itemRis = row.findViewById(R.id.notiziaAuthor);

            String text = (position + 1) + ". " + item.getNome();
            itemName.setText(text);
            itemRis.setText(item.getScore());
            ImageView notiziaImage = row.findViewById(R.id.notiziaImage);
            if (item.getLogo() != null) {
                notiziaImage.setImageBitmap(item.getLogo());
            }
        }
        return row;
    }
}
