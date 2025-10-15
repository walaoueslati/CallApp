package com.example.contactmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NouveauContent extends AppCompatActivity {

    private EditText ednom, edpseudo, edNumero;
    private Button btnAjout, btnCancel;
    private ContactManager contactManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau_content);

        ednom = findViewById(R.id.TextName_content);
        edpseudo = findViewById(R.id.PseudoContent_content);
        edNumero = findViewById(R.id.NumeroText_content);

        btnAjout = findViewById(R.id.btnAjout_content);
        btnCancel = findViewById(R.id.btnCancel_content);

        contactManager = new ContactManager(this);
        contactManager.ouvrir();

        btnAjout.setOnClickListener(v -> {
            String nom = ednom.getText().toString().trim();
            String pseudo = edpseudo.getText().toString().trim();
            String numero = edNumero.getText().toString().trim();

            if (nom.isEmpty() || pseudo.isEmpty() || numero.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            long id = contactManager.ajout(nom, pseudo, numero);
            if (id > 0) {
                Toast.makeText(this, "Contact ajouté avec succès", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout du contact", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (contactManager != null) contactManager.fermer();
    }
}
