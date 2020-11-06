package com.example.pm_mal_pr01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ListaAdaptador extends BaseAdapter
{
    private Context c;
    private int diseno;
    private static final String URL_INTERNET_PICASSO="https://i.imgur.com/DvpvklR.png";
    private ArrayList<contacto> contactos;
    public ListaAdaptador(Context c, int diseno, ArrayList<contacto> contactos){
        this.c=c;
        this.diseno = diseno;
        this.contactos = contactos;
    }
    @Override
    public int getCount() {
        return contactos.size();
    }
    @Override
    public Object getItem(int position) {
        return contactos.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vistaDiseno = convertView;
        if(vistaDiseno==null){
            //Creamos la vista
            LayoutInflater LayoutInflater = android.view.LayoutInflater.from(c);
            vistaDiseno = LayoutInflater.inflate(diseno,null);
        }
        //Asociamos los objetos de la vista con los de la clase "contacto"
        TextView tvNombres = vistaDiseno.findViewById(R.id.nombre);
        TextView tvUsuarios = vistaDiseno.findViewById(R.id.usuario);
        TextView tvContrasenas = vistaDiseno.findViewById(R.id.contrasena);
        ImageView perfil = vistaDiseno.findViewById(R.id.perfil);
        Picasso.with(perfil.getContext())
                .load(URL_INTERNET_PICASSO)
                .resize(70, 70)
                .centerCrop()
                .into(perfil);
        tvNombres.setText(contactos.get(position).getNombre());
        tvUsuarios.setText(contactos.get(position).getUsuario());
        tvContrasenas.setText(contactos.get(position).getContrasena());
        return vistaDiseno;
    }
}
