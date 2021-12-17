package org.izv.igg.ad.micolecciondevinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.izv.igg.ad.micolecciondevinos.data.Vino;
import org.izv.igg.ad.micolecciondevinos.data.Vinos;
import org.izv.igg.ad.micolecciondevinos.util.Csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName() + "xyzyx";

    //private ArrayList<String> listaVinos = new ArrayList<>();
    //private Vinos vinos = new Vinos(listaVinos);

    private Context context;

    private String fileName;
    private String listaVinos;
    private String[] arrayVinos;
    private String[] arrayIdVinos;
    private boolean idExiste = false;

    private String vinoAEditar;
    private String idVinoAEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        initialize();
    }

    protected void onResume() {
        super.onResume();
        TextView tvListaVinos = findViewById(R.id.tvListaVinos);
        tvListaVinos.setText("");
        initialize();
    }

    private void defineAddListener() {
        Button btAdd = findViewById(R.id.btAddMain);
        btAdd.setOnClickListener((View v) -> {
            openAddActivity();
        });
    }

    private void defineEditListener() {
        EditText etIdVino = findViewById(R.id.etIdVino);
        Button btEdit = findViewById(R.id.btEditMain);
        btEdit.setOnClickListener((View v) -> {
            idExiste = false;
            if (etIdVino.getText().toString().isEmpty()) {
                System.out.println("No se ha introducido ningún Id");
                Toast.makeText(context, "No se ha introducido ningún Id", Toast.LENGTH_SHORT).show();
            } else {
                // Separamos los Id de los vinos
                for (int i = 0; i < arrayVinos.length; i++) {
                    arrayIdVinos = arrayVinos[i].split(";");
                    if (etIdVino.getText().toString().equals(arrayIdVinos[0])) {
                        idExiste = true;

                        vinoAEditar = "vinoAEditar.txt";
                        String strVinoAEditar = arrayVinos[i];
                        deleteFile(getFilesDir(), vinoAEditar);
                        writeFile(getFilesDir(), vinoAEditar, strVinoAEditar);

                        idVinoAEditar = "idVinoAEditar.txt";
                        String strIdVinoAEditar = String.valueOf(i);
                        deleteFile(getFilesDir(), idVinoAEditar);
                        writeFile(getFilesDir(), idVinoAEditar, strIdVinoAEditar);

                        openEditActivity();

                        i = arrayVinos.length;
                    }
                }
                if (idExiste == false) {
                    System.out.println("El Id introducido no existe");
                    Toast.makeText(context, "El Id introducido no existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialize() {

        System.out.println("SE HA INICIADO EL MAIN");
        fileName = "coleccionVinos.txt";

        // Leemos el txt
        listaVinos = readFile(getFilesDir(), fileName);

        // Separamos los Vinos por cada \n
        arrayVinos = listaVinos.split("\n");

        isEmptyMyCollection();
        defineAddListener();
        defineEditListener();
    }

    private void isEmptyMyCollection() {
        TextView tvListaVinos = findViewById(R.id.tvListaVinos);

        if (readFile(getFilesDir(), fileName).isEmpty()) {
            System.out.println("VACIO");
            tvListaVinos.setText("No hay ningún vino en tu colección");
        } else {
            System.out.println("LLENO");
            tvListaVinos.setText(listaVinos.replace(";", "\t"));
        }
    }

    private void openAddActivity() {
        Intent intencion = new Intent(this, AddActivity.class);
        startActivity(intencion);
    }

    private void openEditActivity() {
        Intent intencion = new Intent(this, EditActivity.class);
        startActivity(intencion);
    }

    private boolean deleteFile(File file, String fileName) {
        File f = new File(file, fileName);
        FileWriter fw = null; // FileWriter(File f,boolean append)
        boolean ok = true;
        f.delete();
        return ok;
    }

    private String readFile(File file, String fileName) {
        File f = new File(file, fileName);
        String texto = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }
            br.close();
        } catch (IOException e) {
            texto = null;
            Log.v(TAG, e.toString());
        }
        return texto;
    }

    private boolean writeFile(File file, String fileName, String string) {
        File f = new File(file, fileName);
        FileWriter fw = null; // FileWriter(File f,boolean append)
        boolean ok = true;
        try {
            fw = new FileWriter(f, true);
            fw.write(string);
            fw.write("\n");
            fw.flush();
            fw.close();
            //return true;
        } catch (IOException e) {
            ok = false;
            Log.v(TAG, e.toString());
        }
        return ok;
    }

}