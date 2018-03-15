package com.example.dell.sushisystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNoteActivity extends AppCompatActivity {

    String note;
    String strName;

    String empty="null";

    Button Close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);

        Intent intent = getIntent();
        note = intent.getExtras().getString("note");
        strName = intent.getExtras().getString("namemenu");

        Close = (Button) findViewById(R.id.close_button);

        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });

        Toast.makeText(this,empty+"5555" +note, Toast.LENGTH_SHORT).show();


        if(note.equals(empty))
        {
            note="NO Note!!!!";
        }


        final TextView namemenu = (TextView) findViewById(R.id.name_menu);
        namemenu.setText(strName);

        final TextView show_note = (TextView) findViewById(R.id.shownote);
        show_note.setText(note);
    }
}
