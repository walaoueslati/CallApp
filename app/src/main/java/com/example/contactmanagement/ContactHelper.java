package com.example.contactmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ContactHelper extends SQLiteOpenHelper {
    public static final String table_contact = "Contact";
    public static final String col_id = "id";
    public static final String col_nom = "nom";
    public static final String col_pseudo = "pseudo";
    public static final String col_Numero = "numero";

    private static final String requete = "CREATE TABLE " + table_contact + " (" +
            col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            col_nom + " TEXT, " +
            col_pseudo + " TEXT, " +
            col_Numero + " TEXT" +
            ");";

    public ContactHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(requete);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_contact);
        onCreate(db);
    }
}
