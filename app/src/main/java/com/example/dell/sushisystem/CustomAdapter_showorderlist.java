package com.example.dell.sushisystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 8/12/2560.
 */

public class CustomAdapter_showorderlist extends BaseAdapter {

    Context mContext;
    String[] strName;
    String[] num;
    String[] price;
    String[] orderId;
    String[] numPrice;
    String address="http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/delete_menu.php";
    RequestQueue requestQueue;


    public CustomAdapter_showorderlist(Context context, String[] strName, String[] num, String[] price, String[] order_id, String[] num_price) {
        this.mContext= context;
        this.strName = strName;
        this.num = num;
        this.price = price;
        this.orderId = order_id;
        this.numPrice = num_price;

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


    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        requestQueue = Volley.newRequestQueue(mContext);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_showorder, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.name_menu);
        textView.setText(strName[position]);

        TextView textView1 = (TextView)view.findViewById(R.id.num);
        textView1.setText(num[position]);

        TextView textView2 = (TextView)view.findViewById(R.id.price);
        textView2.setText(price[position]);

        TextView textView3 = (TextView)view.findViewById(R.id.total_price);
        textView3.setText(numPrice[position]);

        Button delete1_button = (Button)view.findViewById(R.id.delete1_button);
        Button note_button = (Button)view.findViewById(R.id.note_button);


        delete1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest request = new StringRequest(Request.Method.POST, address, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters  = new HashMap<String, String>();
                        parameters.put("id_order",orderId[position]);
                        return parameters;
                    }
                };
                requestQueue.add(request);
                Toast.makeText(v.getContext(), strName[position] + "Delete Success", Toast.LENGTH_SHORT).show();
            }
        });

        note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,NoteOrderActivity.class);
                intent.putExtra("namemenu_edit",strName[position]);
                intent.putExtra("id_order",orderId[position]);
                mContext.startActivity(intent);
            }
        });

        return view;
    }


}
