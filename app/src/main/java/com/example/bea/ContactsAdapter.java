package com.example.bea;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    private ContactsAdapter adapter;
    private ArrayList<Contact> contacts;

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

        final TextView name, address, number, notes;

        name = view.findViewById(R.id.name_tv);
        address = view.findViewById(R.id.address_tv);
        number = view.findViewById(R.id.number_tv);
        notes = view.findViewById(R.id.note_tv);

        name.setText(contact.name);
        address.setText(contact.address);
        number.setText(contact.number);
        notes.setText(contact.note);

        return view;
    }
}