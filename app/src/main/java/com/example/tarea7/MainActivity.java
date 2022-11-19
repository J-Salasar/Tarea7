package com.example.tarea7;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.tarea7.datos.conexion;
import com.example.tarea7.datos.consulta;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
public class MainActivity extends AppCompatActivity {
    private EditText text_descripcion;
    private Button guardar;
    private ImageView imagen;
    private static final int REQUESTCODECAMARA=100;
    private static final int REQUESTTAKEFOTO=101;
    private String currentPhotoPath;
    private conexion conectar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_descripcion=(EditText) findViewById(R.id.txt_descripcion);
        guardar=(Button) findViewById(R.id.bt_guardar);
        imagen=(ImageView) findViewById(R.id.img_imagen);
        conectar=new conexion(this, consulta.DataBase,null,1);
    }
    public void tomar_foto(View view){
        permisos();
    }
    public boolean validar(String dato, int numero){
        String opcion1="[A-Z,a-z,0-9,Á,É,Í,Ó,Ú,Ñ,ñ,.,_,' ',á,é,í,ó,ú,-]{1,300}";
        switch (numero){
            case 1:{return dato.matches(opcion1);}
            default:{return false;}
        }
    }
    public void crear(View view){
            if(validar(text_descripcion.getText().toString().trim(),1)){
                agregar();
            }
            else{
                Toast.makeText(this,"Descripcion no valida",Toast.LENGTH_LONG).show();
            }
    }
    public void dispatchTakePictureIntent(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent,REQUESTTAKEFOTO);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    //Agrega la foto al cuadro
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTTAKEFOTO && resultCode==RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();
            currentPhotoPath = Base64.getEncoder().encodeToString(bytes);
            Toast.makeText(this,currentPhotoPath,Toast.LENGTH_SHORT).show();
            guardar.setEnabled(true);
        }
    }
    public void permisos(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUESTCODECAMARA);
        }
        else{
            dispatchTakePictureIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUESTCODECAMARA){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }
            else{
                Toast.makeText(getApplicationContext(),"Permiso Denegado",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void agregar(){
        SQLiteDatabase db=conectar.getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put(consulta.url,currentPhotoPath);
        valores.put(consulta.descripcion,text_descripcion.getText().toString());
        Long resultado=db.insert(consulta.persona,consulta.id,valores);
        Toast.makeText(getApplicationContext(),"Registro guardado",Toast.LENGTH_LONG).show();
        db.close();
        limpiar();
    }
    public void limpiar(){
        retroceder();
    }
    public void volver(View view){
        retroceder();
    }
    public void retroceder(){
        text_descripcion.setText("");
        guardar.setEnabled(false);
        Intent principal=new Intent(this,ActivityLista.class);
        startActivity(principal);
    }
}