package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class TableActivity extends AppCompatActivity {

    String address="http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/add_nameorder.php";
    String address1="http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/connect_table.php";
    static final String AB = "0123456789";
    static SecureRandom rnd = new SecureRandom();
    StringBuilder sb;

    InputStream is=null;
    String line=null;
    String result=null;
    String[] values;
    String[] table_id;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        getdata();
        GridAdapter adapter = new GridAdapter(getApplicationContext(), values);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                randomString(6);
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
                        parameters.put("nameOrder",sb.toString());
                        return parameters;
                    }
                };
                requestQueue.add(request);

                //Toast.makeText(TableActivity.this, "" +sb, Toast.LENGTH_SHORT).show();
                    Intent myintent = new Intent(view.getContext(),TypemenuActivity.class);
                    myintent.putExtra("nameTable",values[position]);
                    myintent.putExtra("nameOrder",sb.toString());
                    myintent.putExtra("idTable",table_id[position].toString());

                //delay
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
                startActivity(getIntent());

                    startActivity(myintent);
            }
        });
    }

    //random ordername
    String randomString( int len ){
        sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    private void getdata()
    {
        try
        {
            URL url=new URL(address1);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            is=new BufferedInputStream(con.getInputStream());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();

            while((line=br.readLine()) != null)
            {
                sb.append(line+"\n");
            }
            is.close();
            result=sb.toString();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            JSONArray ja=new JSONArray(result);
            JSONObject jo=null;

            values=new String[ja.length()];
            table_id=new String[ja.length()];

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                table_id[i]=jo.getString("id");
                values[i]=jo.getString("table_name");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


}
