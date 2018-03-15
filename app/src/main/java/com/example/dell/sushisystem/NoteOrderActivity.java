package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NoteOrderActivity extends AppCompatActivity {

    String strName;
    String id_order;
    String note;

    Button NoteOrder;
    EditText note_edit;

    String address="http://student.coe.phuket.psu.ac.th/~s5735512060/db_sushi/note_order.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_order);

        Intent intent = getIntent();
        strName = intent.getExtras().getString("namemenu_edit");
        id_order = intent.getExtras().getString("id_order");

        final TextView namemenu_edit = (TextView) findViewById(R.id.name_menu);
        namemenu_edit.setText(strName);

        NoteOrder = (Button) findViewById(R.id.note_button);
        note_edit = (EditText) findViewById(R.id.note_edit);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Toast.makeText(this, "" +id_order, Toast.LENGTH_SHORT).show();

        NoteOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note = note_edit.getText().toString();
                //Toast msg = Toast.makeText(getBaseContext(),name,Toast.LENGTH_LONG);
                //msg.show();

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
                        parameters.put("id_order",id_order);
                        parameters.put("note",note);
                        return parameters;
                    }
                };
                requestQueue.add(request);
                finish();
            }

        });

    }



}
