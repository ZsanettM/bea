package com.example.bea;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Info_DB";
    private static final String DB_TABLE = "Contacts";
    private static final String[] COLUMNS = {"Name", "Address", "Number", "Note"};

    private static final String CREATE = "CREATE TABLE " + DB_TABLE + " (" +
            COLUMNS[0] + " TEXT, " +
            COLUMNS[1] + " TEXT, " +
            COLUMNS[2] + " TEXT, " +
            COLUMNS[3] + " TEXT);" ;

    DbHelper(Context c){
        super(c, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }

    //Add new person
    public void addToTable(Contact contact){
        ContentValues row = new ContentValues();
        row.put(this.COLUMNS[0], contact.name);
        row.put(this.COLUMNS[1], contact.address);
        row.put(this.COLUMNS[2], contact.number);
        row.put(this.COLUMNS[3], contact.note);

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DB_TABLE, null, row);
        db.close();
    }

    //Retrieve data
    public ArrayList<Contact> getAllData(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor returned = db.query(DB_TABLE, COLUMNS,null, null, null, null, this.COLUMNS[0], null);

        ArrayList<Contact> r = new ArrayList<>();

        for(int i=0; i<returned.getCount(); i++){
            returned.moveToPosition(i);
            r.add(new Contact(
                    returned.getString(0),
                    returned.getString(1),
                    returned.getString(2),
                    returned.getString(3)
            ));
        }
        return r;
    }

    //Remove data
    public void removeContact(Contact c){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Name= '" + c.name + "' AND Number= '" + c.number +"'";
        db.delete(DB_TABLE, query, null);
    }

    //Edit data
    public void editContact(Contact c){
        SQLiteDatabase db = getWritableDatabase();
        String query = "Name= '" + c.name + "'";

        ContentValues row = new ContentValues();
        row.put(this.COLUMNS[0], c.name);
        row.put(this.COLUMNS[1], c.address);
        row.put(this.COLUMNS[2], c.number);
        row.put(this.COLUMNS[3], c.note);
        db.update(DB_TABLE, row, query, null);
    }

}
