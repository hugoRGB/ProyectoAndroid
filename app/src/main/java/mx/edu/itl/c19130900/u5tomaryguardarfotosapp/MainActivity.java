/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2022    HORA: 08-09 HRS
:*
:*             Clase que permite seleccionar el identificador de la foto
:*
:*  Archivo     : MainActivity.java
:*  Autores     : Juan Francisco Barragán Barron 19130891
:*                Javier Arath De La Cerda Martínez 19130900
:*                Hugo René Guerra Barajas 19130917
:*  Fecha       : 13/12/2022
:*  Compilador  : Android Studio Chipmunk | 2021.2.1 Patch 2
:*  Descripción : Clase que le da un identificador a cada foto tomada y las guarda en
:*                la carpeta de la aplicación
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl.c19130900.u5tomaryguardarfotosapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables
    Button btnCamara;
    RadioButton escuela, fiesta, deportes, comida, familia, selfie;
    RadioGroup radioGroup;
    ImageView imgView;
    int CODIGO_ABRIR_CAMARA = 1;
    String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencia de los elementos del layout
        escuela = findViewById(R.id.radioEscuela);
        fiesta = findViewById(R.id.radioFiesta);
        deportes = findViewById(R.id.radioDeportes);
        comida = findViewById(R.id.radioComida);
        familia = findViewById(R.id.radioFamilia);
        selfie = findViewById(R.id.radioSelfie);

        btnCamara = findViewById(R.id.btnCamara);
        imgView = findViewById(R.id.imageView);

        radioGroup = findViewById(R.id.radioGroup);

        // Al hacer clic en el btnCamara lanzará abrirCamara()
        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
    }

    // Permite abrir la cámara
    public void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagenArchivo = null;
        try {
            imagenArchivo = crearImagen();
        } catch (IOException e){
            Log.e("Error", e.toString());
        }

        if (imagenArchivo != null){
            Uri fotoUri = FileProvider.getUriForFile(this,
                    "mx.edu.itl.c19130900.u5tomaryguardarfotosapp.fileprovider", imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
            startActivityForResult(intent, CODIGO_ABRIR_CAMARA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_ABRIR_CAMARA && resultCode == RESULT_OK){
            //Bundle extra = data.getExtras();
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imgView.setImageBitmap(imgBitmap);
        }
    }

    // Crea el archivo de imagen y la guarda con identificador en la carpeta
    public File crearImagen() throws IOException {
        // Obtenemos el identificador por medio del radioButton seleccionado
        int id = radioGroup.getCheckedRadioButtonId();
        View radioB = radioGroup.findViewById(id);
        int indice = radioGroup.indexOfChild(radioB);

        RadioButton rb = (RadioButton) radioGroup.getChildAt(indice);
        String nombre = rb.getText().toString();

        // Lo guardamos en la carpeta
        String nombreImagen = nombre + "-";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        File imagen =  File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();

        // Es para verificar si se guarda con el nombre del RadioButton
        Toast.makeText(this, imagen.toString(), Toast.LENGTH_LONG).show();
        return imagen;
    }

    public void btnAcercaDeClic(View v) {
        Intent intent = new Intent(this, AcercaDeActivity.class);
        startActivity(intent);
    }
}