package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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

public class MainActivity extends AppCompatActivity {


    String address="http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/add_nameorder.php";
    String address1="http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/connect_tableHome.php";

    InputStream is=null;
    String line=null;
    String result=null;
    String tableHome;
    String id_tableHome;



    static final String AB = "0123456789";
    static SecureRandom rnd = new SecureRandom();
    StringBuilder sb;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button gohome_button = (Button) findViewById(R.id.typemenu_button);
        Button table_button = (Button) findViewById(R.id.table_button);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        getdata();


        randomString(6);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        gohome_button.setOnClickListener(new View.OnClickListener() {
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
                        parameters.put("nameOrder",sb.toString());
                        return parameters;
                    }
                };
                requestQueue.add(request);
                Intent intent = new Intent(MainActivity.this, TypemenuActivity.class);
                intent.putExtra("nameTable",tableHome);
                intent.putExtra("nameOrder",sb.toString());
                intent.putExtra("idTable",id_tableHome);

                //delay
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
                startActivity(getIntent());
                startActivity(intent);
            }
        });

        table_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                startActivity(intent);
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

            jo=ja.getJSONObject(0);
                id_tableHome=jo.getString("id");
                tableHome=jo.getString("table_name");
            //Toast.makeText(this, "123"+jo.getString("id"), Toast.LENGTH_SHORT).show();
        }
        catch(Exception e)
        {

            e.printStackTrace();
        }

    }
}
