package com.example.dell.sushisystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomAdapter_namemenulist extends BaseAdapter {

    Context mContext;
    String[] strName;
    String[] price;
    String[] image;

    public CustomAdapter_namemenulist(Context context, String[] strName, String[] price, String[] image) {
        this.mContext= context;
        this.strName = strName;
        this.price = price;
        this.image = image;
    }

    public int getCount() {
        return strName.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_namefood, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.textView1);
        textView.setText(strName[position]);

        ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
        Picasso.with(mContext).load(image[position]).into(imageView);

        TextView textView1 = (TextView)view.findViewById(R.id.price);
        textView1.setText(price[position]);

        return view;
    }
}
