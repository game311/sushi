package com.example.dell.sushisystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Dell on 12/10/2560.
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    private final String [] values;

    public GridAdapter(Context context, String[] values) {
        mContext = context;
        this.values = values;

    }

    public int getCount() {
        return values.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = mInflater.inflate(R.layout.gridview_table, parent, false);

        TextView textView = (TextView)convertView.findViewById(R.id.gridview);
        textView.setText(values[position]);
        return convertView;
    }
}