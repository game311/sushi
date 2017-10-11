package com.example.dell.sushisystem;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Typefood3Activity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    String address="http://10.0.2.2/sushidb/connect_typefood3.php";
    InputStream is=null;
    String line=null;
    String result=null;
    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typefood3);

        int[] resId = { R.drawable.ricekimji, R.drawable.ricefish
                , R.drawable.riceguro, R.drawable.riceseafood};

        lv=(ListView) findViewById(R.id.typefood3);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        getdata();

        CustomAdapter_typemenulist adapter = new CustomAdapter_typemenulist(getApplicationContext(), data, resId);


        // adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);
    }

    private void getdata()
    {
        try
        {
            URL url=new URL(address);
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

            data=new String[ja.length()];

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                data[i]=jo.getString("menu_name")+" "+jo.getString("menu_size")+"                   ราคา "+jo.getString("menu_price");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }




    }
}
