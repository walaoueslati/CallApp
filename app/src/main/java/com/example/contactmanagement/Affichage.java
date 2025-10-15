package com.example.contactmanagement;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Affichage extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private ArrayList<Contact> contactList;
    private ContactManager contactManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);

        searchView = findViewById(R.id.Recherche_content);
        recyclerView = findViewById(R.id.recyclerViewContacts);

        contactManager = new ContactManager(this);
        contactManager.ouvrir();

        contactList = contactManager.getAllContact();

        adapter = new ContactAdapter(this, contactList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text){
        ArrayList<Contact> filteredList = new ArrayList<>();
        for(Contact contact : contactList){
            if(contact.getNom().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(contact);
            }
        }
        adapter = new ContactAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        contactList.clear();
        contactList.addAll(contactManager.getAllContact());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(contactManager != null) contactManager.fermer();
    }
}
