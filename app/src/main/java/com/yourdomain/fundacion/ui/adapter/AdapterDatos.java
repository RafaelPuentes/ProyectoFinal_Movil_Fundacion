package com.yourdomain.fundacion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yourdomain.fundacion.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {
    ArrayList<usuario> listDatos;

    public AdapterDatos(ArrayList<usuario> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public AdapterDatos.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDatos.ViewHolderDatos holder, int position) {
        holder.txtDoc.setText(listDatos.get(position).getDocumento().toString());
        holder.txtNom.setText(listDatos.get(position).getNombre().toString());
        holder.txtTel.setText(listDatos.get(position).getTelefono().toString());
        holder.txtEdad.setText(listDatos.get(position).getEdad().toString());
        holder.txtFecha.setText(listDatos.get(position).getFecha().toString());

    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView txtDoc,txtNom,txtTel,txtEdad,txtFecha;
        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txtDoc=itemView.findViewById(R.id.documento);
            txtNom=itemView.findViewById(R.id.nombre);
            txtTel=itemView.findViewById(R.id.telefono);
            txtEdad=itemView.findViewById(R.id.edad);
            txtFecha=itemView.findViewById(R.id.fecha);
        }
    }
}
