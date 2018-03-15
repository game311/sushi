package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    String nameorder;
    String nametable;

    String[] num;
    String[] menus_id;
    String[] menu_id;
    String[] nameorders_id;
    String[] note;
    String[] namemenu;
    String[] price;
    int j =0,k=0;

    int totalNum;
    float totalPrice;

    String totalNum_want;
    String totalPrice_want;

    String address = "http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/order.php";
    String address1 = "http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/showorder1.php";
    String address2 = "http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/add_bill.php";
    InputStream is = null;
    String line = null;
    String result = null;
    RequestQueue requestQueue;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent = getIntent();
        nameorder = intent.getExtras().getString("nameorder");//id
        nametable = intent.getExtras().getString("nametable");

        Button chekbill_button = (Button) findViewById(R.id.checkbill_button);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        chekbill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest request = new StringRequest(Request.Method.POST, address2, new Response.Listener<String>() {
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
                        parameters.put("id_nameorder",nameorder);
                        parameters.put("total_price",totalPrice_want);
                        return parameters;
                    }
                };
                requestQueue.add(request);

                Intent intent = new Intent(OrderActivity.this, PaymentActivity.class);
                intent.putExtra("nameorder",nameorder);
                startActivity(intent);

            }
        });


        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        getdata();
        getdata1();

        final String[] namemenusend = new String[j];
        final String[] numsend = new String[j];
        final String[] pricesend = new String[j];
        final String[] notesend = new String[j];
        float[] num_price =  new float[j];
        final String[] num_pricesend = new String[j];

        if(j>0){
            for (int i = 0; i < j; i++) {
                namemenusend[i] = namemenu[i];
                numsend[i] = num[i];
                pricesend[i] = price[i];
                notesend[i] = note[i];
                num_price[i] = Float.parseFloat(numsend[i])*Float.parseFloat(pricesend[i]);
                num_pricesend[i] = String.valueOf(num_price[i]);

            }
        }


        lv = (ListView) findViewById(R.id.order);

        CustomAdapter_orderlist adapter = new CustomAdapter_orderlist(getApplicationContext(), namemenusend, numsend, pricesend, notesend, num_pricesend);
        lv.setAdapter(adapter);


        totalNum=0;
        totalPrice=0;


        for(int i = 0; i< numsend.length; i++){

            //Toast.makeText(this, "7777"+numsend[i], Toast.LENGTH_SHORT).show();
            totalNum += Integer.parseInt(numsend[i]);
        }

        for(int i = 0; i< num_pricesend.length; i++){

            totalPrice += Float.parseFloat(num_pricesend[i]);
        }

        totalNum_want = String.valueOf(totalNum);
        totalPrice_want = String.valueOf(totalPrice);

        Toast.makeText(this, "331"+totalNum, Toast.LENGTH_SHORT).show();


        final TextView namemenu_edit = (TextView) findViewById(R.id.name_table);
        namemenu_edit.setText(nametable);

        // Toast.makeText(this, "7777"+numsend[2], Toast.LENGTH_SHORT).show();
        final TextView total_num  = (TextView) findViewById(R.id.total_num);
        total_num.setText(totalNum_want);

        final TextView total_price  = (TextView) findViewById(R.id.total_price);
        total_price.setText(totalPrice_want);

    }


    private void getdata() {
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            note = new String[ja.length()];
            menus_id = new String[ja.length()];
            num = new String[ja.length()];
            nameorders_id = new String[ja.length()];

            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                nameorders_id[i] = jo.getString("nameorders_id");
                // Toast.makeText(this, nameorders_id[i], Toast.LENGTH_SHORT).show();
                if (Integer.parseInt(nameorders_id[i]) == Integer.parseInt(nameorder)) {
                    menus_id[j] = jo.getString("menus_id");
                    num[j] = jo.getString("num_menu");
                    note[j] = jo.getString("note");


                    j++;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getdata1() {
        try {
            URL url = new URL(address1);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            con.setRequestMethod("GET");
            is = new BufferedInputStream(con.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONArray ja = new JSONArray(result);
            JSONObject jo = null;

            menu_id = new String[ja.length()];
            namemenu = new String[ja.length()];
            price = new String[ja.length()];

            for (int i = 0; i < ja.length(); i++) {

                jo = ja.getJSONObject(i);
                menu_id[i] = jo.getString("id");

                for(int k=0; k<j; k++) {
                    if (Integer.parseInt(menu_id[i]) == Integer.parseInt(menus_id[k])) {
                        //Toast.makeText(this, namemenu[k], Toast.LENGTH_SHORT).show();
                        namemenu[k] = jo.getString("menu_name")+" "+jo.getString("menu_size");
                        price[k]=jo.getString("menu_price");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
