package org.izv.igg.ad.micolecciondevinos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.izv.igg.ad.micolecciondevinos.data.Vino;
import org.izv.igg.ad.micolecciondevinos.data.Vinos;
import org.izv.igg.ad.micolecciondevinos.util.Csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "xyzyzx";
    //private Vinos vinos;

    private String fileName;
    private String listaVinos;

    private String vinoAEditar;
    private String vinoModificado;

    private String idVinoAEditar;
    private String posicionVino;

    private String[] arrayVino;

    private String[] arrayVinos;
    private String[] arrayIdVinos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initialize();
    }

    private void deleteVino() {

        String csv = new String();
        int posicion = Integer.parseInt(posicionVino.trim());
        int posicionSaltoLinea = posicion + 1;

        for (int i = 0; i < arrayVinos.length; i++) {
            if (i != posicion){
                if (i != posicionSaltoLinea){
                    csv += arrayVinos[i] + "\n";
                }
            }
        }

        deleteFile(getFilesDir(), fileName);
        writeFile(getFilesDir(), fileName, csv);

        if (arrayVinos.length == 1){
            deleteFile(getFilesDir(), fileName);
            File f = new File(getFilesDir(), fileName);
            try {
                FileWriter fw = new FileWriter(f, true);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        System.out.println(csv);
        finish();
        openMainActivity();
    }

    private void editVino() {
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
        //System.out.println(etGraduacion.getText().toString());
        Double graduacionVino = Double.parseDouble(etGraduacion.getText().toString());

        EditText etFecha = findViewById(R.id.etFecha);
        //System.out.println(etFecha.getText().toString());
        Integer fechaVino = Integer.parseInt(etFecha.getText().toString().trim());

        Vino vino = new Vino(idVino, nombreVino, bodegaVino, colorVino, origenVino, graduacionVino, fechaVino);
        String csv = Csv.getCsvVinoEdit(vino);
        System.out.println(csv);
        arrayVinos[Integer.parseInt(posicionVino.trim())] = csv;

        csv = "";

        for (int i = 0; i < arrayVinos.length; i++) {
            csv += arrayVinos[i] + "\n";
        }

        deleteFile(getFilesDir(), fileName);
        writeFile(getFilesDir(), fileName, csv);
        System.out.println(csv);
        finish();
        openMainActivity();
    }

    private void initialize() {

        EditText etId = findViewById(R.id.etId);
        etId.setEnabled(false);
        EditText etNombre = findViewById(R.id.etNombre);
        EditText etBodega = findViewById(R.id.etBodega);
        EditText etColor = findViewById(R.id.etColor);
        EditText etOrigen = findViewById(R.id.etOrigen);
        EditText etGraduacion = findViewById(R.id.etGraduacion);
        EditText etFecha = findViewById(R.id.etFecha);

        fileName = "coleccionVinos.txt";
        listaVinos = readFile(getFilesDir(), fileName);
        arrayVinos = listaVinos.split("\n");

        vinoAEditar = "vinoAEditar.txt";
        vinoModificado = readFile(getFilesDir(), vinoAEditar);

        idVinoAEditar = "idVinoAEditar.txt";
        posicionVino = readFile(getFilesDir(), idVinoAEditar);

        arrayVino = vinoModificado.split(";");
        etId.setText(arrayVino[0]);
        etNombre.setText(arrayVino[1]);
        etBodega.setText(arrayVino[2]);
        etColor.setText(arrayVino[3]);
        etOrigen.setText(arrayVino[4]);
        etGraduacion.setText(arrayVino[5]);
        etFecha.setText(arrayVino[6]);

        Button btEdit = findViewById(R.id.btEdit);
        btEdit.setOnClickListener((View v) -> {
            editVino();
        });

        Button btDelete = findViewById(R.id.btDelete);
        btDelete.setOnClickListener((View v) -> {
            deleteVino();
        });
    }

    private boolean deleteFile(File file, String fileName){
        File f = new File(file, fileName);
        FileWriter fw = null; // FileWriter(File f,boolean append)
        boolean ok = true;
        f.delete();
        return ok;
    }

    private void openMainActivity() {
        Intent intencion = new Intent(this, MainActivity.class);
        startActivity(intencion);
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