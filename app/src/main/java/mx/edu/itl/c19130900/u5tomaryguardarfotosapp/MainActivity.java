package mx.edu.itl.c19130900.u5tomaryguardarfotosapp;

import static android.os.Environment.getExternalStorageDirectory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {

    Button camara;
    RadioButton escuela, fiesta, deportes, comida, familia, selfie;
    ImageView imgView;
    int CODIGO_ABRIR_CAMARA = 1;
    String rutaImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        escuela = findViewById(R.id.radioEscuela);
        fiesta = findViewById(R.id.radioFiesta);
        deportes = findViewById(R.id.radioDeportes);
        comida = findViewById(R.id.radioComida);
        familia = findViewById(R.id.radioFamilia);
        selfie = findViewById(R.id.radioSelfie);
        imgView = findViewById(R.id.imageView);

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCamara();
            }
        });
    }

    private void abrirCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File imagenArchivo = null;
            try {
                imagenArchivo = crearImagen();
            } catch (IOException e){
                Log.e("Error", e.toString());
            }

            if (imagenArchivo != null){
                Uri fotoUri = FileProvider.getUriForFile(this, "mx.edu.itl.c19130900.u5tomaryguardarfotosapp.fileprovider",imagenArchivo);
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

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen, ".jpg", directorio);
        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }

    public void btnAcercaDeClic(View v) {
        Intent intent = new Intent(this, AcercaDeActivity.class);
        startActivity(intent);
    }
}