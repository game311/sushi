package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Typefood2Activity extends AppCompatActivity {

    ListView lv;
    String address="http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/connect_typefood2.php";
    InputStream is=null;
    String line=null;
    String result=null;
    String[] namemenu;
    String[] price;
    String[] image;
    String[] description;
    String[] idfood;
    String id_nameTable;
    String id_nameorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_namefood);

        Intent intent = getIntent();
        id_nameTable = intent.getExtras().getString("id_nameTable");
        id_nameorder = intent.getExtras().getString("id_nameorder");

        final TextView name_type = (TextView) findViewById(R.id.name_type);
        name_type.setText("Sushi");

        lv=(ListView) findViewById(R.id.typefood);

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));

        getdata();

        CustomAdapter_namemenulist adapter = new CustomAdapter_namemenulist(getApplicationContext(), namemenu, price, image);

        //adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), PopUpActivity.class);
                intent.putExtra("namemenu",namemenu[position]);
                intent.putExtra("price",price[position]);
                intent.putExtra("image",image[position]);
                intent.putExtra("description",description[position]);
                intent.putExtra("idfood",idfood[position]);
                intent.putExtra("id_nameTable",id_nameTable);
                intent.putExtra("id_nameorder",id_nameorder);
                startActivity(intent);
            }
        });

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

            namemenu=new String[ja.length()];
            price=new String[ja.length()];
            image=new String[ja.length()];
            description=new String[ja.length()];
            idfood=new String[ja.length()];

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                namemenu[i]=jo.getString("menu_name")+" "+jo.getString("menu_size");
                price[i]=jo.getInt("menu_price")+" ฿";
                image[i]=jo.getString("image");
                description[i]=jo.getString("description");
                idfood[i]=jo.getString("id");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}

