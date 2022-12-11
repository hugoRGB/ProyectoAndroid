package mx.edu.itl.c19130900.u5tomaryguardarfotosapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ExploradorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    
    private List <String> nombreArchivos;
    private List <String> rutaArchivos;
    private ArrayAdapter <String> adapter;
    private String raiz;
    private TextView carpetaActual;
    ListView listas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorador);

        carpetaActual = (TextView) findViewById(R.id.txtRutaActual);
        listas = (ListView) findViewById(R.id.lista);

        raiz = Environment.getExternalStorageDirectory().getPath();

        listas.setOnItemClickListener(this);
        verDirectorio(raiz);
    }

    private void verDirectorio (String ruta) {
        nombreArchivos = new ArrayList<String>();
        rutaArchivos = new ArrayList<String>();
        int count = 0;
        File directorioActual = new File(ruta);
        File[] listaArchivos = directorioActual.listFiles();

        /* Si no esta en la raíz se crea un elemento para volver al directorio padre del
           directorio actual */
        if(!ruta.equals(raiz)) {
            nombreArchivos.add("../");
            rutaArchivos.add(directorioActual.getParent()); /* getParent() da el nombre de
                                                               la ruta de los padres */
            count = 1;
        }

        // Almacena las ruta de todos los archivos y carpetas del directorio
        for(File archivo : listaArchivos)
            rutaArchivos.add(archivo.getPath());

        // Ordenado alfabéticamente
        Collections.sort(rutaArchivos, String.CASE_INSENSITIVE_ORDER);

        if(rutaArchivos.size()==1 ){
            nombreArchivos.add("No hay archivos");
            adapter = new ArrayAdapter<String>(this, R.layout.lista_archivos, nombreArchivos);
            listas.setAdapter(adapter);
            return;
        }

        // Crear la lista de archivos ordenados para mostrar en el listView
        for(int i = count; i < rutaArchivos.size(); i++) {
            File archivo = new File(rutaArchivos.get(i));
            if(archivo.isFile())
                nombreArchivos.add(archivo.getName());
            else
                nombreArchivos.add("/" + archivo.getName());

            // Se indica si no hay archivos
            if(listaArchivos.length < i) {
                nombreArchivos.add("No hay archivos");
                rutaArchivos.add(ruta);
            }

            // Se crea el adaptador y se le pasa la lista de nombres y layout para los elementos
            adapter = new ArrayAdapter<String>(this, R.layout.lista_archivos, nombreArchivos);
            listas.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Obtiene la ruta del archivo que se ha seleccionado en el listView
        File archivo = new File(rutaArchivos.get(i));
        // Si es un archivo se muestra un Toast, si es directorio secargan los archivos
        if(archivo.isFile())
            Toast.makeText(this, "Has seleccionado un archivo.", Toast.LENGTH_LONG).show();
        else
            verDirectorio(rutaArchivos.get(i));
    }

    public void btnCancelarClic(View v) {
        finish();
    }

    public void btnSeleccionarClic(View v) {

    }

    private View layout_carpeta;
    private EditText edtNombre;
    private String nombre;

    public void btnCrearClic(View v) {
        layout_carpeta =getLayoutInflater().inflate(R.layout.crear_carpeta, null);
        edtNombre = layout_carpeta.findViewById(R.id.edtTextNombre);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Crear Carpeta").setView(layout_carpeta)
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nombre = edtNombre.getText().toString();
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setCancelable(false).create().show();
    }
}