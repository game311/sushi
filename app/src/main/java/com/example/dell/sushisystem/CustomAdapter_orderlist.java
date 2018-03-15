package com.example.dell.sushisystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Dell on 16/2/2561.
 */

public class CustomAdapter_orderlist extends BaseAdapter{
    Context mContext;
    String[] strName;
    String[] num;
    String[] price;
    String[] numPrice;
    String[] note;


    public CustomAdapter_orderlist(Context context, String[] strName, String[] num, String[] price, String[] note, String[] num_price) {
        this.mContext= context;
        this.strName = strName;
        this.num = num;
        this.price = price;
        this.numPrice = num_price;
        this.note = note;

    }

    public int getCount() {
        return num.length;
    }


    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_order, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.name_menu);
        textView.setText(strName[position]);

        TextView textView1 = (TextView)view.findViewById(R.id.num);
        textView1.setText(num[position]);

        TextView textView2 = (TextView)view.findViewById(R.id.price);
        textView2.setText(price[position]);

        TextView textView3 = (TextView)view.findViewById(R.id.total_price);
        textView3.setText(numPrice[position]);

        Button note_button = (Button)view.findViewById(R.id.look_button);

        note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ShowNoteActivity.class);
                intent.putExtra("note",note[position]);
                intent.putExtra("namemenu",strName[position]);
                mContext.startActivity(intent);
            }
        });

        return view;
    }


}

