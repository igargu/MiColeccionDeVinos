package org.izv.igg.ad.micolecciondevinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "xyzyzx";

    private Context context;

    private String fileName;
    private String listaVinos;
    private String[] arrayVinos;
    private String[] arrayIdVinos;
    private boolean idExiste = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        context = this;
        initialize();
    }

    private void addVino() {

        EditText etId = findViewById(R.id.etId);
        Long idVino = Long.parseLong(etId.getText().toString());

        EditText etNombre = findViewById(R.id.etNombre);
        String nombreVino = etNombre.getText().toString();

        EditText etBodega = findViewById(R.id.etBodega);
        String bodegaVino = etBodega.getText().toString();

        EditText etColor = findViewById(R.id.etColor);
        String colorVino = etColor.getText().toString();

        EditText etOrigen = findViewById(R.id.etOrigen);
        String origenVino = etOrigen.getText().toString();

        EditText etGraduacion = findViewById(R.id.etGraduacion);
        Double graduacionVino = Double.parseDouble(etGraduacion.getText().toString());

        EditText etFecha = findViewById(R.id.etFecha);
        Integer fechaVino = Integer.parseInt(etFecha.getText().toString());

        for (int i = 0; i < arrayVinos.length; i++) {
            arrayIdVinos = arrayVinos[i].split(";");
            if (etId.getText().toString().equals(arrayIdVinos[0])) {
                idExiste = true;
                System.out.println("El Id introducido ya existe");
                Toast.makeText(context, "El Id introducido ya existe", Toast.LENGTH_SHORT).show();
                i = arrayVinos.length;
            }
        }
        if (idExiste == false) {
            Vino vino = new Vino(idVino, nombreVino, bodegaVino, colorVino, origenVino, graduacionVino, fechaVino);
            String csv = Csv.getCsv(vino);
            writeFile(getFilesDir(), fileName, csv);
            System.out.println(csv);
            finish();
        }

    }

    private void defineAddListener() {
        Button btAdd = findViewById(R.id.btAdd);
        btAdd.setOnClickListener((View v) -> {
            idExiste = false;
            addVino();
        });
    }

    private void initialize() {
        fileName = "coleccionVinos.txt";

        // Leemos el txt
        listaVinos = readFile(getFilesDir(), fileName);

        // Separamos los Vinos por cada \n
        arrayVinos = listaVinos.split("\n");

        defineAddListener();
    }

    private String readFile(File file, String fileName){
        File f = new File(file, fileName);
        String texto = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }
            br.close();
        }catch (IOException e){
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