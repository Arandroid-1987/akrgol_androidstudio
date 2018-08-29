package com.arandroid.risultatilive.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.arandroid.risultatilive.R;

import java.util.ArrayList;

public class EntryAdapter extends ArrayAdapter<Item> {
    private ArrayList<Item> items;
    private LayoutInflater vi;

    public EntryAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
        this.items = items;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        final Item i = items.get(position);
        if (i != null) {
            if (i.isSection()) {
                SectionItem si = (SectionItem) i;
                v = vi.inflate(R.layout.list_item_section, parent);

                v.setOnClickListener(null);
                v.setOnLongClickListener(null);
                v.setLongClickable(false);

                final TextView sectionView = v.findViewById(R.id.list_item_section_text);
                sectionView.setText(si.getTitle());
            } else {
                EntryItem ei = (EntryItem) i;
                v = vi.inflate(R.layout.list_item_entry, parent);
                final TextView title = v.findViewById(R.id.list_item_entry_title);
                final TextView subtitle = v.findViewById(R.id.list_item_entry_summary);

                if (title != null)
                    title.setText(ei.title);
                if (subtitle != null)
                    subtitle.setText(ei.subtitle);
            }
        }
        return v;
    }

}
