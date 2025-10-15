package com.example.contactmanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Contact> contacts;
    private final ContactManager contactManager;

    // ✅ Constructeur corrigé
    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
        this.contactManager = new ContactManager(context); // <-- correction ici
        this.contactManager.ouvrir();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);

        holder.tvName.setText(contact.getNom());
        holder.tvPhone.setText(contact.getNumero());
        holder.tvPseudo.setText(contact.getPseudo());

        // ----- Bouton Modifier -----
        holder.btnEdit.setOnClickListener(v -> showEditDialog(contact, position));

        // ----- Bouton Supprimer -----
        holder.btnDelete.setOnClickListener(v -> showDeleteDialog(contact, position));

        // ----- Bouton Appeler -----
        holder.btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contact.getNumero()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    private void showEditDialog(Contact contact, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_edit_contact, null);

        EditText inputName = viewInflated.findViewById(R.id.etName);
        EditText inputPseudo = viewInflated.findViewById(R.id.etPseudo);
        EditText inputPhone = viewInflated.findViewById(R.id.etPhone);
        Button btnSave = viewInflated.findViewById(R.id.btnSave);
        Button btnCancel=viewInflated.findViewById(R.id.btnCancel);    //
        inputName.setText(contact.getNom());
        inputPseudo.setText(contact.getPseudo());
        inputPhone.setText(contact.getNumero());

        builder.setView(viewInflated);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnSave.setOnClickListener(v -> {
            String newNom = inputName.getText().toString().trim();
            String newPseudo = inputPseudo.getText().toString().trim();
            String newNumero = inputPhone.getText().toString().trim();

            if (newNom.isEmpty() || newNumero.isEmpty()) {
                Toast.makeText(context, "Le nom et le numéro sont obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = contactManager.modifierContact(contact.getId(), newNom, newPseudo, newNumero);
            if (result > 0) {
                contact.setNom(newNom);
                contact.setPseudo(newPseudo);
                contact.setNumero(newNumero);
                notifyItemChanged(position);
                Toast.makeText(context, "Contact modifié avec succès", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // ✅ fermer le dialogue après sauvegarde
            } else {
                Toast.makeText(context, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(v->{
            dialog.dismiss();
        });
    }


    private void showDeleteDialog(Contact contact, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Supprimer le contact");
        builder.setMessage("Voulez-vous vraiment supprimer " + contact.getNom() + " ?");

        builder.setPositiveButton("Oui", (dialog, which) -> {
            int result = contactManager.supprimerContact(contact.getId());
            if (result > 0) {
                contacts.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Contact supprimé avec succès", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Non", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // ======================
    // VIEWHOLDER
    // ======================
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPseudo, tvPhone;
        MaterialButton btnEdit, btnDelete, btnCall;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPseudo = itemView.findViewById(R.id.tvPseudo);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnCall = itemView.findViewById(R.id.btnCall);
        }
    }
}
