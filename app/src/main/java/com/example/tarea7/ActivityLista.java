package com.example.tarea7;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.tarea7.datos.conexion;
import com.example.tarea7.datos.consulta;
import com.example.tarea7.datos.informacion;
import java.util.ArrayList;
public class ActivityLista extends AppCompatActivity {
    private ListView lista;
    private conexion conectar;
    private ArrayList<informacion> listapersona;
    private ArrayList<String> arreglopersona;
    private ArrayList<String> arreglofoto;
    private ArrayList<String> arreglonumero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        lista=(ListView) findViewById(R.id.txt_lista);
        conectar=new conexion(this, consulta.DataBase,null,1);
        ObtenerLista();
        lista.setAdapter(new Adaptador(this,arreglopersona,arreglofoto,arreglonumero));
    }
    private void ObtenerLista() {
        SQLiteDatabase db=conectar.getReadableDatabase();
        informacion persona=null;
        listapersona=new ArrayList<informacion>();
        String envio="SELECT * FROM "+consulta.persona+" WHERE 1";
        Cursor cursor=db.rawQuery(envio,null);
        while (cursor.moveToNext()){
            persona=new informacion();
            persona.setId(cursor.getInt(0));
            persona.setUrl(cursor.getString(1));
            persona.setDescripcion(cursor.getString(2));
            listapersona.add(persona);
        }
        cursor.close();
        fllList();
    }
    private void fllList() {
        arreglopersona=new ArrayList<String>();
        arreglofoto=new ArrayList<String>();
        arreglonumero=new ArrayList<String>();
        for(int i=0;i<listapersona.size();i++){
            arreglopersona.add(
                    listapersona.get(i).getDescripcion()
            );
            arreglofoto.add(
                    listapersona.get(i).getUrl()
            );
            arreglonumero.add(
                    listapersona.get(i).getId().toString()
            );
        }
    }
    public void atras(View view){
        Intent crear=new Intent(this,MainActivity.class);
        startActivity(crear);
    }
}