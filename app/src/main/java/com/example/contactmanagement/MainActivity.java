package com.example.contactmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {

    EditText endnom , edmp;
    private Button btnval;
    private Button btnqte;
    private Button btndecon;
    private Button btnferm;
    ListView lv;
    private CheckBox rememberMe;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LoginPrefs";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recuperation des composantes

        edmp = findViewById(R.id.edemp_auth);
        endnom = findViewById(R.id.ednom);
        btnval = findViewById(R.id.btn_auth);
        btnqte = findViewById(R.id.btn_quitter);
        btndecon = findViewById(R.id.btnDeconn_acceuil);
        btnferm = findViewById(R.id.btn_quitter);
        lv=findViewById(R.id.recyclerViewContacts);
        rememberMe = findViewById(R.id.rememberMe);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedName = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if(!savedName.isEmpty() && !savedPassword.isEmpty()){
            endnom.setText(savedName);
            edmp.setText(savedPassword);
            rememberMe.setChecked(true);
        }


        btnqte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });

        btnval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = endnom.getText().toString();
                String mdp = edmp.getText().toString();

                if(nom.equalsIgnoreCase("test") && mdp.equals("000")){

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(rememberMe.isChecked()){
                        editor.putString("username", nom);
                        editor.putString("password", mdp);
                    } else {
                        editor.remove("username");
                        editor.remove("password");
                    }
                    editor.apply();

                    Intent i = new Intent(MainActivity.this, Acceuil.class);
                    i.putExtra("USER", nom);
                    startActivity(i);

                } else {
                    Toast.makeText(MainActivity.this, "valeur non valide ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}