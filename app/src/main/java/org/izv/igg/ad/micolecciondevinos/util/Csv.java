package org.izv.igg.ad.micolecciondevinos.util;

import android.util.Log;

import org.izv.igg.ad.micolecciondevinos.MainActivity;
import org.izv.igg.ad.micolecciondevinos.data.Vino;

public class Csv {

    public static Vino getVino(String str) {
        String[] atributos = str.split(";");
        Vino v = null;
        if (atributos.length >= 7) {
            v = new Vino();
            try {
                v.setId(Long.parseLong(atributos[0].trim()));
            } catch (NumberFormatException e) {
                Log.v(MainActivity.TAG, e.toString());
            }
            v.setNombre(atributos[1].trim());
            v.setBodega(atributos[2].trim());
            v.setColor(atributos[3].trim());
            v.setOrigen(atributos[4].trim());
            try {
                v.setGraduacion(Double.parseDouble(atributos[5].trim()));
            } catch (NumberFormatException e) {

            }
            try {
                v.setFecha(Integer.parseInt(atributos[6].trim()));
            } catch (NumberFormatException e) {

            }
        }
        return v;
    }

    public static String getCsv(Vino v) {
        return v.getId() + "; " +
                v.getNombre() + "; " +
                v.getBodega() + "; " +
                v.getColor() + "; " +
                v.getOrigen() + "; " +
                v.getGraduacion() + "; " +
                v.getFecha() + "\n";
    }

    public static String getCsvVinoEdit(Vino v) {
        return v.getId() + "; " +
                v.getNombre() + "; " +
                v.getBodega() + "; " +
                v.getColor() + "; " +
                v.getOrigen() + "; " +
                v.getGraduacion() + "; " +
                v.getFecha();
    }
}
