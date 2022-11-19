package com.example.tarea7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Base64;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater=null;
    Context contexto;
    ArrayList<String> datos;
    ArrayList<String> fotos;
    ArrayList<String> numero;
    private ImageView foto;
    private TextView descripcion,numerofila;
    public Adaptador(Context contexto, ArrayList<String> datos, ArrayList<String> fotos, ArrayList<String> numero){
        this.contexto=contexto;
        this.datos=datos;
        this.fotos=fotos;
        this.numero=numero;
        inflater=(LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View vista=inflater.inflate(R.layout.elemento_lista,null);
        foto=(ImageView) vista.findViewById(R.id.imagen_diseño);
        descripcion=(TextView) vista.findViewById(R.id.texto_largo);
        numerofila=(TextView) vista.findViewById(R.id.numero_fila);
        descripcion.setText(datos.get(i));
        byte[] bytes=Base64.getDecoder().decode(fotos.get(i));
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        foto.setImageBitmap(bitmap);
        numerofila.setText("Número: "+numero.get(i));
        return vista;
    }
    @Override
    public int getCount() {
        return fotos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
