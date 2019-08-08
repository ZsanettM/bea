package com.example.bea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {
    private DbHelper dbHelper;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        dbHelper = new DbHelper(getApplicationContext());

        Button save_btn = findViewById(R.id.save_button);


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText name = findViewById(R.id.name_edit);
                EditText addr = findViewById(R.id.addr_edit);
                EditText num  = findViewById(R.id.num_edit);
                EditText note = findViewById(R.id.notes_edit);

                contact = new Contact(name.getText().toString(),addr.getText().toString(),num.getText().toString(),note.getText().toString());

                dbHelper.addToTable(contact);
                finish();
            }
        });

    }
}
