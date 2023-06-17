package co.edu.javeriana.logica;

import co.edu.javeriana.model.ArchivoPeticion;
import co.edu.javeriana.model.Peticion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LecturaRequerimientos {

    private ArrayList<ArchivoPeticion> peticionesArchivo = new ArrayList<>();

    public ArrayList<Peticion> leerArchivo(String archivo) {
        peticionesArchivo = new ArrayList<>();
        try {

            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            StringBuilder json = new StringBuilder();
            boolean primeraLinea = true; // Variable para omitir la primera línea del archivo
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue; // Omitir la primera línea
                }
                json.append(linea);
            }
            br.close();

            org.json.JSONArray jsonArray = new org.json.JSONArray(json.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                org.json.JSONObject jsonObject = jsonArray.getJSONObject(i);
                ArchivoPeticion archivoPeticion = new ArchivoPeticion();
                archivoPeticion.setISBN(jsonObject.getString("ISBN"));
                archivoPeticion.setRequerimiento(jsonObject.getString("requerimiento"));

                // Crear un objeto Peticion y agregarlo a la lista de peticionesArchivo
                ArchivoPeticion peticion = new ArchivoPeticion(archivoPeticion.getISBN(), archivoPeticion.getRequerimiento());
                peticionesArchivo.add(peticion);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        return generarPeticiones();
    }

    private ArrayList<Peticion> generarPeticiones() {
        ArrayList<Peticion> peticiones = new ArrayList<>();
        for (ArchivoPeticion peticionArchivo : peticionesArchivo) {
            Peticion peticion = new Peticion(peticionArchivo.getISBN(), peticionArchivo.getRequerimiento());
            peticiones.add(peticion);
        }
        return peticiones;
    }

}
