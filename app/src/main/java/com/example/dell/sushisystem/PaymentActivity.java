package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;


public class PaymentActivity extends AppCompatActivity {

    String nameorder;
    String address = "http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/bill_confirm.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        nameorder = intent.getExtras().getString("nameorder");//id

        Button cash_button = (Button) findViewById(R.id.cash_button);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        cash_button.setOnClickListener(new View.OnClickListener() {
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
                        parameters.put("id_nameorder",nameorder);
                        return parameters;
                    }
                };
                requestQueue.add(request);

//                Intent intent = new Intent(PaymentActivity.this, PaymentActivity.class);
//                intent.putExtra("nameorder",nameorder);
//                startActivity(intent);

            }
        });
    }
}
