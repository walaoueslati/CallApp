package com.example.contactmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import kotlinx.coroutines.channels.ProduceKt;

public class ContactManager {
    SQLiteDatabase db=null;
    Context con;
    ContactManager(Context con){
        this.con=con;
    }
    public  void ouvrir(){
           ContactHelper helper=new ContactHelper(con,"mabase.db",null,1);
           db=helper.getWritableDatabase();

    }
    public  long ajout(String nom,String pseudo, String numero){
        long a=0;
        ContentValues values=new ContentValues();
        values.put(ContactHelper.col_nom,nom);
        values.put(ContactHelper.col_pseudo,pseudo);
        values.put(ContactHelper.col_Numero,numero);
       a=db.insert(ContactHelper.table_contact,null,values);
       return a;
    }
    public ArrayList<Contact> getAllContact() {
        ArrayList<Contact> contacts = new ArrayList<>();

        if (db == null || !db.isOpen()) {
            ouvrir();
        }

        Cursor cursor = db.query(
                ContactHelper.table_contact,
                new String[] { ContactHelper.col_id, ContactHelper.col_nom, ContactHelper.col_pseudo, ContactHelper.col_Numero },
                null, null, null, null, ContactHelper.col_nom + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ContactHelper.col_id)); // ✅ récup ID
                String nom = cursor.getString(cursor.getColumnIndexOrThrow(ContactHelper.col_nom));
                String pseudo = cursor.getString(cursor.getColumnIndexOrThrow(ContactHelper.col_pseudo));
                String numero = cursor.getString(cursor.getColumnIndexOrThrow(ContactHelper.col_Numero));

                contacts.add(new Contact(id, nom, pseudo, numero));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return contacts;
    }


    public int supprimerContact(int id) {
        if (db == null || !db.isOpen()) ouvrir();
        return db.delete(ContactHelper.table_contact, ContactHelper.col_id + " = ?", new String[]{String.valueOf(id)});
    }

    public int modifierContact(int id, String nom, String pseudo, String numero) {
        if(db == null || !db.isOpen()) ouvrir();
        ContentValues values = new ContentValues();
        values.put(ContactHelper.col_nom, nom);
        values.put(ContactHelper.col_pseudo, pseudo);
        values.put(ContactHelper.col_Numero, numero);

        return db.update(ContactHelper.table_contact, values, ContactHelper.col_id + " = ?", new String[]{String.valueOf(id)});
    }

    public void fermer() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

}
