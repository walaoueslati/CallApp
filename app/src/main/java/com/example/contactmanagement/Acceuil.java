package com.example.contactmanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Acceuil extends AppCompatActivity {

    private TextView tvusername;
    private Button btnajout;
    private Button btnaff;
    private Button btndeconn;
    private Button btncancel;

    public static ArrayList<Contact> listContact = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);

        tvusername = findViewById(R.id.TextAcceuil_acceuil);
        btnajout = findViewById(R.id.btnAjout_acceuil);
        btnaff = findViewById(R.id.btnAff_acceuil);
        btndeconn = findViewById(R.id.btnDeconn_acceuil);
        btncancel = findViewById(R.id.btnCancel_acceuil);


        Intent x = getIntent();
        Bundle b = x.getExtras();
        if (b != null) {
            String u = b.getString("USER");
            tvusername.setText("Accueil de M. " + u);
        }



        btnajout.setOnClickListener(v -> {
            Intent i = new Intent(Acceuil.this,NouveauContent.class);
            startActivity(i);
        });

        btnaff.setOnClickListener(v -> {
            Intent intent = new Intent(Acceuil.this, Affichage.class);
            startActivity(intent);
        });

        btndeconn.setOnClickListener(v -> {
            Intent intent = new Intent(Acceuil.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        btncancel.setOnClickListener(v -> finishAffinity());
    }
}
