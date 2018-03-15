package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class PopUpActivity extends AppCompatActivity {

    String strName;
    String pricemanu;
    String image;
    String descrip;
    String num;
    String idfood;
    String id_nameTable;
    String id_nameorder;
    String address8="http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/addmenu.php";
    RequestQueue requestQueue;

    //Button
    Button SubmitOrder;
    ElegantNumberButton BtnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        Intent intent = getIntent();
        strName = intent.getExtras().getString("namemenu");
        pricemanu = intent.getExtras().getString("price");
        image = intent.getExtras().getString("image");
        descrip = intent.getExtras().getString("description");
        idfood = intent.getExtras().getString("idfood");
        id_nameTable = intent.getExtras().getString("id_nameTable");
        id_nameorder = intent.getExtras().getString("id_nameorder");

        //Toast.makeText(this, "" +id_nameTable, Toast.LENGTH_SHORT).show();


        final ImageView img = (ImageView)findViewById(R.id.ImageMenu);
        final TextView name = (TextView)findViewById(R.id.NameMenu);
        final TextView price = (TextView)findViewById(R.id.PriceMenu);
        final TextView description = (TextView)findViewById(R.id.Description);

        name.setText(strName);
        price.setText(pricemanu);
        description.setText(descrip);
        Picasso.with(getApplicationContext()).load(image).into(img);

        //Order
        SubmitOrder = (Button) findViewById(R.id.order_button);
        BtnOrder = (ElegantNumberButton)findViewById(R.id.Count_Button);

        BtnOrder.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                num=BtnOrder.getNumber();
                Log.e("Num",num);
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        SubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest request = new StringRequest(Request.Method.POST, address8, new Response.Listener<String>() {
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
                        parameters.put("num",num);
                        parameters.put("idfood",idfood);
                        parameters.put("id_nameTable",id_nameTable);
                        parameters.put("id_nameorder",id_nameorder);
                        return parameters;
                    }
                };
                requestQueue.add(request);
                finish();

            }

        });
    }
}
