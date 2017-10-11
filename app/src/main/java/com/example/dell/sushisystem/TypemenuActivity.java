package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TypemenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typemenu);

        int[] resId = { R.drawable.setfood
                , R.drawable.sushi, R.drawable.rice
                , R.drawable.snack, R.drawable.salad
                , R.drawable.rameng, R.drawable.dessert
                , R.drawable.drink};

        final String[] list = { "อาหารชุด", "ข้าวปั้น", "ข้าว", "ของทานเล่น", "สลัด", "ราเมง", "ขนมหวาน", "เครื่องดื่ม"};

        CustomAdapter_typemenulist adapter = new CustomAdapter_typemenulist(getApplicationContext(), list, resId);


        ListView listView = (ListView) findViewById(R.id.typemenu);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    Intent myintent = new Intent(view.getContext(),Typefood1Activity.class);
                    startActivityForResult(myintent,0);
                }

                if(position==1){
                    Intent myintent = new Intent(view.getContext(),Typefood2Activity.class);
                    startActivityForResult(myintent,0);
                }

                if(position==2){
                    Intent myintent = new Intent(view.getContext(),Typefood3Activity.class);
                    startActivityForResult(myintent,0);
                }

                if(position==3){
                    Intent myintent = new Intent(view.getContext(),Typefood4Activity.class);
                    startActivityForResult(myintent,0);
                }

                if(position==4){
                    Intent myintent = new Intent(view.getContext(),Typefood5Activity.class);
                    startActivityForResult(myintent,0);
                }

                if(position==5){
                    Intent myintent = new Intent(view.getContext(),Typefood6Activity.class);
                    startActivityForResult(myintent,0);
                }

                if(position==6){
                    Intent myintent = new Intent(view.getContext(),Typefood7Activity.class);
                    startActivityForResult(myintent,0);
                }

                if(position==7){
                    Intent myintent = new Intent(view.getContext(),Typefood8Activity.class);
                    startActivityForResult(myintent,0);
                }


            }
        });
    }

}