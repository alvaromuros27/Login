package com.example.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

public class Options {
    @SerializedName("languages")
    @Expose
    private String [][] lenguajes = new String[][]{};
    @SerializedName("styles")
    @Expose
    public String [][] estilos = new String[][]{};

    public Options(String [][] lenguajes, String [][] estilos){
        this.estilos=estilos;
        this.lenguajes=lenguajes;
    }



    public ArrayList<String> getLenguajes(){
        ArrayList<String> arrayList3 = new ArrayList<>();
        for(int i=0; i<lenguajes.length; i++){
            arrayList3.add(lenguajes[i][0]);

        }

        return arrayList3;
    }



    public ArrayList<String> getEstilos(){
        ArrayList<String> arrayList4 = new ArrayList<>();
        for(int i=0; i<estilos.length; i++){
            arrayList4.add(estilos[i][0]);

        }

        return arrayList4;
    }

}
