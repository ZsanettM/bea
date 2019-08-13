package com.example.bea;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {
    private DbHelper dbHelper;
    private Contact contact;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        dbHelper = new DbHelper(getApplicationContext());

        Button save_btn = findViewById(R.id.save_button);

        final EditText name = findViewById(R.id.name_edit);
        final EditText addr = findViewById(R.id.addr_edit);
        final EditText num  = findViewById(R.id.num_edit);
        final EditText note = findViewById(R.id.notes_edit);

        Intent i = getIntent();
        if (i.getExtras() != null){
            name.setText(i.getStringExtra("name"));
            addr.setText(i.getStringExtra("address"));
            num.setText(i.getStringExtra("number"));
            note.setText(i.getStringExtra("note"));
            isEditing = true;
        }

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contact = new Contact(name.getText().toString(),addr.getText().toString(),num.getText().toString(),note.getText().toString());

                if (isEditing){
                    dbHelper.editContact(contact);
                }
                else {
                    dbHelper.addToTable(contact);
                }

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}
