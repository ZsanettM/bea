package com.example.bea;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private ContactsAdapter adapter;
    private ArrayList<Contact> contacts;
    private ArrayList<Contact> contactsAllTemp = new ArrayList<>();

    public ContactsAdapter(Context c, ArrayList<Contact> items){
        super(c, 0, items);
        this.adapter = this;
        this.contacts = items;
    }

    private DbHelper helper = new DbHelper(getContext());

    @Override
    public View getView(int position, View view, ViewGroup parent){
        final  Contact contact = getItem(position);

        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.contact, parent, false);
        }
        this.notifyDataSetChanged();

        final TextView name, address, number, notes;
        final ImageButton deleteBtn = view.findViewById(R.id.btn_delete);
        final ImageButton editBtn = view.findViewById(R.id.btn_edit);

        name = view.findViewById(R.id.name_tv);
        address = view.findViewById(R.id.address_tv);
        number = view.findViewById(R.id.number_tv);
        notes = view.findViewById(R.id.note_tv);

        name.setText(contact.name);
        address.setText(contact.address);
        number.setText(contact.number);
        notes.setText(contact.note);

        //Delete contact
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.removeContact(contact);
                contacts.remove(contact);
                if (contactsAllTemp.contains(contact)) {
                    contactsAllTemp.remove(contact);
                }
                adapter.notifyDataSetChanged();

            }
        });

        //Edit contact
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editIntent = new Intent(getContext(), AddContact.class);
                editIntent.putExtra("name", contact.name);
                editIntent.putExtra("address", contact.address);
                editIntent.putExtra("number", contact.number);
                editIntent.putExtra("note", contact.note);
                getContext().startActivity(editIntent);

            }
        });

        return view;
    }

    public void filter(String name){
        if(contactsAllTemp.isEmpty()) {
            this.contactsAllTemp.addAll(contacts);
        }

        ArrayList<Contact> temp  = new ArrayList<>();
        for (Contact c : this.contacts){
            if (c.name.toLowerCase().contains(name.toLowerCase())){
                temp.add(c);
                //Log.d(TAG, "filter: "+c.name+" added");
            }
        }

        this.clear();
        //Log.d(TAG, "filter: "+temp.get(0).address);
        this.contacts.addAll(temp);
        this.notifyDataSetChanged();
    }

    public void clearSearch(){
        this.clear();
        this.contacts.addAll(contactsAllTemp);
        Log.i("CLEAR", Integer.toString(contacts.size()));
        this.contactsAllTemp.clear();
        this.notifyDataSetChanged();
    }

}
