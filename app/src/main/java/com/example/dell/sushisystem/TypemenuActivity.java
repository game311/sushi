package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class TypemenuActivity extends AppCompatActivity {

    private Toolbar toolbar;
    String nameTable;
    String id_nametable;
    String nameOrder;
    String[] id_nameorder;
    String address="http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/test.php";
    InputStream is=null;
    String line=null;
    String result=null;
    String id_nameorderneed;

    Button BasketButton;
    Button TurnbackButton;

    int clickfeie = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typefood);

        //table
        Intent intent = getIntent();
        id_nametable= intent.getExtras().getString("idTable");
        nameTable = intent.getExtras().getString("nameTable");
        nameOrder = intent.getExtras().getString("nameOrder");

        BasketButton = (Button) findViewById(R.id.basket_button);
        TurnbackButton = (Button) findViewById(R.id.turnback_button);

        BasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TypemenuActivity.this, ShowOrderActivity.class);
                intent.putExtra("nameorder",id_nameorderneed);
                intent.putExtra("nametable",nameTable);
                startActivity(intent);
            }
        });

        TurnbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickfeie == 3) {
                    finish();
                    clickfeie = 0;
                }
                clickfeie = clickfeie + 1;
            }
        });


        final TextView name_type = (TextView) findViewById(R.id.name_table);
        name_type.setText(nameTable);


        int[] resId = { R.drawable.setfood
                , R.drawable.sushi, R.drawable.rice
                , R.drawable.snack, R.drawable.salad
                , R.drawable.rameng, R.drawable.dessert
                , R.drawable.drink};

        final String[] list = { "Set Food", "Sushi", "Rice", "Snack", "Salad", "Rameng", "Dessert", "Drink"};

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));


        CustomAdapter_typemenulist adapter = new CustomAdapter_typemenulist(getApplicationContext(), list, resId);

        ListView listView = (ListView) findViewById(R.id.typemenu);
        listView.setAdapter(adapter);
        getdata();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    Intent myintent = new Intent(view.getContext(),Typefood1Activity.class);
                    myintent.putExtra("id_nameTable",id_nametable);
                    myintent.putExtra("id_nameorder",id_nameorderneed);
                    startActivity(myintent);
                }

                else if (position==1){
                    Intent myintent = new Intent(view.getContext(),Typefood2Activity.class);
                    myintent.putExtra("id_nameTable",id_nametable);
                    myintent.putExtra("id_nameorder",id_nameorderneed);
                    startActivity(myintent);
                }

                else if (position==2){
                    Intent myintent = new Intent(view.getContext(),Typefood3Activity.class);
                    myintent.putExtra("id_nameTable",id_nametable);
                    myintent.putExtra("id_nameorder",id_nameorderneed);
                    startActivityForResult(myintent,0);
                }

                else if(position==3){
                    Intent myintent = new Intent(view.getContext(),Typefood4Activity.class);
                    myintent.putExtra("id_nameTable",id_nametable);
                    myintent.putExtra("id_nameorder",id_nameorderneed);
                    startActivityForResult(myintent,0);
                }

                else if(position==4){
                    Intent myintent = new Intent(view.getContext(),Typefood5Activity.class);
                    myintent.putExtra("id_nameTable",id_nametable);
                    myintent.putExtra("id_nameorder",id_nameorderneed);
                    startActivityForResult(myintent,0);
                }

                else if(position==5){
                    Intent myintent = new Intent(view.getContext(),Typefood6Activity.class);
                    myintent.putExtra("id_nameTable",id_nametable);
                    myintent.putExtra("id_nameorder",id_nameorderneed);
                    startActivityForResult(myintent,0);
                }

                else if(position==6){
                    Intent myintent = new Intent(view.getContext(),Typefood7Activity.class);
                    myintent.putExtra("id_nameTable",id_nametable);
                    myintent.putExtra("id_nameorder",id_nameorderneed);
                    startActivityForResult(myintent,0);
                }

                else if(position==7){
                    Intent myintent = new Intent(view.getContext(),Typefood8Activity.class);
                    myintent.putExtra("id_nameTable",id_nametable);
                    myintent.putExtra("id_nameorder",id_nameorderneed);
                    startActivityForResult(myintent,0);
                }


            }
        });

        //Toast.makeText(this, "" +id_nameorder, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
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

            id_nameorder=new String[ja.length()];

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                id_nameorder[i]=jo.getString("name_order");

                //Toast.makeText(this, id_nameorder[i]    +nameOrder, Toast.LENGTH_SHORT).show();
                if(Integer.parseInt(id_nameorder[i]) == Integer.parseInt(nameOrder)) {
                    id_nameorderneed = jo.getString("id");

                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {

    }

}