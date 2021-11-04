package org.izv.igg.ad.micolecciondevinos.data;

import java.util.ArrayList;

public class Vinos {

    private ArrayList<String> listaVinos;

    public Vinos(ArrayList<String> listaVinos) {
        this.listaVinos = listaVinos;
    }

    public void insertaVino(String csv){
        listaVinos.add(csv);
    }

    public ArrayList<String> getListaVinos() {
        return listaVinos;
    }

    public void setListaVinos(ArrayList<String> listaVinos) {
        this.listaVinos = listaVinos;
    }

    @Override
    public String toString() {
        return "Vinos{" +
                "listaVinos=" + listaVinos +
                '}';
    }
}
