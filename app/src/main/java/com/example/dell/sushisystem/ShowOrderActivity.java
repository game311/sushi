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

public class ShowOrderActivity extends AppCompatActivity {

    ListView lv;
    String address = "http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/showorder.php";
    String address1 = "http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/showorder1.php";
    String address3 = "http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/delete_allmenu.php";
    String address4 = "http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/status_confirm.php";
    InputStream is = null;
    String line = null;
    String result = null;
    RequestQueue requestQueue;

    String[] num;
    String[] menus_id;
    String[] menu_id;
    String[] nameorders_id;
    String[] order_id;

    //String nameTable;
    String nameorder;
    String nametable;
    String[] price;
    int j =0,k=0;
    //String id_nameorder;

    String[] namemenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showorder);

        Button deleteAll_button = (Button) findViewById(R.id.deleteAll_button);
        Button confirm_button = (Button) findViewById(R.id.confirm_button);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        deleteAll_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest request = new StringRequest(Request.Method.POST, address3, new Response.Listener<String>() {
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
                        return parameters;
                    }
                };
                requestQueue.add(request);
                //delay
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
                startActivity(getIntent());
            }
        });



        Intent intent = getIntent();
        nameorder = intent.getExtras().getString("nameorder");//id
        nametable = intent.getExtras().getString("nametable");
        //Toast.makeText(this, nameorder, Toast.LENGTH_SHORT).show();

        final TextView namemenu_edit = (TextView) findViewById(R.id.name_table);
        namemenu_edit.setText(nametable);

        Toast.makeText(this, "555"+nametable, Toast.LENGTH_SHORT).show();


        lv = (ListView) findViewById(R.id.showorder);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        getdata();
        getdata1();

        final String[] namemenusend = new String[j];
        final String[] numsend = new String[j];
        final String[] pricesend = new String[j];
        final String[] orderidsend = new String[j];
        float[] num_price =  new float[j];
        final String[] num_pricesend = new String[j];

        if(j>0){
            for (int i = 0; i < j; i++) {
                namemenusend[i] = namemenu[i];
                numsend[i] = num[i];
                pricesend[i] = price[i];
                orderidsend[i] = order_id[i];
                num_price[i] = Float.parseFloat(numsend[i])*Float.parseFloat(pricesend[i]);
                num_pricesend[i] = String.valueOf(num_price[i]);

            }
        }


        CustomAdapter_showorderlist adapter = new CustomAdapter_showorderlist(getApplicationContext(), namemenusend, numsend, pricesend, orderidsend,num_pricesend);
        //adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest request = new StringRequest(Request.Method.POST, address4, new Response.Listener<String>() {
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
                        return parameters;
                    }
                };
                requestQueue.add(request);

                //delay
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(ShowOrderActivity.this, OrderActivity.class);
                intent.putExtra("nametable",nametable);
                intent.putExtra("nameorder",nameorder);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
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

            order_id = new String[ja.length()];
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
                    order_id[j] = jo.getString("id");

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