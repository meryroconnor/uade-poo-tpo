package utils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;

import javax.swing.*;

public abstract class GenericDAO<T> {
    final Class<T> clase;
    protected File archivo;

    //recibe la clase y el archivo a guardar
    public GenericDAO(Class<T> clase, String file) throws Exception {
        this.clase = clase;
        this.archivo = new File(file);
        this.archivo.createNewFile();
    }

    public List<T> getAll(Class<T> clase) throws Exception {
        List<T> list = new ArrayList<T>();
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        Gson g = new Gson();
        String line = "";

        try {

            while ((line = b.readLine()) != null && !line.equals("")) {
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = parser.parse(line).getAsJsonObject();
                list.add(g.fromJson(jsonObject, clase));
            }
            b.close();
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public List<T> getAll() throws Exception {
        return getAll(clase);
    }

    public void saveAll(List<T> list) throws Exception {
        Gson g = new Gson();
        String texto = "";
        for (Object obj : list) {
            texto = texto.concat(g.toJson(obj));
            texto = texto.concat(System.lineSeparator());
        }

        FileWriter fileWriter = new FileWriter(archivo);
        fileWriter.write(texto);
        BufferedWriter bwEscritor = new BufferedWriter(fileWriter);
        bwEscritor.close();
    }

    public void save(T obj) throws Exception {
        Gson g = new Gson();
        String texto = g.toJson(obj);
        texto = texto.concat(System.lineSeparator());
        FileWriter fileWriter = new FileWriter(archivo, true);
        fileWriter.write(texto);
        BufferedWriter bwEscritor = new BufferedWriter(fileWriter);
        bwEscritor.close();
    }

    public int getLastInsertId() throws Exception {

        BufferedReader input = new BufferedReader(new FileReader(archivo));
        String last = "";
        String line;
        String index = "0";
        JsonParser parser = new JsonParser();

        try {
            while ((line = input.readLine()) != null) {
                last = line;
            }

            if (last != "") {
                JsonObject jsonObject = parser.parse(last).getAsJsonObject();
                index = jsonObject.get("id").toString();
            }
            return Integer.parseInt(index);

        } catch (Exception e) {
            return 0;
        }
    }

    public boolean delete(int id) throws Exception {
        boolean wasDeleted = false;
        try {
            BufferedReader b = new BufferedReader(new FileReader(archivo));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            JsonParser parser = new JsonParser();

            while ((line = b.readLine()) != null) {
                JsonObject jsonObject = parser.parse(line).getAsJsonObject();
                if (Integer.parseInt(jsonObject.get("id").toString()) != id) {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                } else {
                    wasDeleted = true;
                }
            }
            b.close();
            String inputStr = inputBuffer.toString();

            //System.out.println(inputStr);

            FileOutputStream fileOut = new FileOutputStream(archivo);
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
        return wasDeleted;
    }

    public boolean update(T obj) throws Exception {
        Boolean wasUpdate = false;
        try {
            BufferedReader b = new BufferedReader(new FileReader(archivo));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            JsonParser parser = new JsonParser();
            Gson g = new Gson();

            while ((line = b.readLine()) != null) {
                JsonObject jsonObject = parser.parse(line).getAsJsonObject();
                if (g.fromJson(jsonObject, clase).equals(obj)) {
                    line = g.toJson(obj);
                    wasUpdate = true;
                }
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            b.close();
            String inputStr = inputBuffer.toString();

            //System.out.println(inputStr);

            FileOutputStream fileOut = new FileOutputStream(archivo);
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
        return wasUpdate;
    }

    public T search(int id) throws FileNotFoundException {
        return search(id, clase);
    }

    public T search(int id, Class<T> clase) throws FileNotFoundException {
        BufferedReader b = new BufferedReader(new FileReader(archivo));
        String line;
        JsonParser parser = new JsonParser();
        Gson g = new Gson();
        Boolean flag = false;

        try {
            while ((line = b.readLine()) != null && flag == false) {
                JsonObject jsonObject = parser.parse(line).getAsJsonObject();
                if (Integer.parseInt(jsonObject.get("id").toString()) == id) {
                    b.close();
                    return g.fromJson(jsonObject, clase);
                }
            }
            b.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<T> searchByAttribute(String attr, String value) throws FileNotFoundException {
        return searchByAttribute(attr, value, clase);
    }

    public List<T> searchByAttribute(String attr,String value, Class<T> clase) throws FileNotFoundException {
        List<T> list = new ArrayList<T>();
        BufferedReader b = new BufferedReader(new FileReader(archivo));
        String line;
        JsonParser parser = new JsonParser();
        Gson g = new Gson();
        Boolean flag = false;
        try {
            while ((line = b.readLine()) != null && flag == false) {
                JsonObject jsonObject = parser.parse(line).getAsJsonObject();
                if (jsonObject.get(attr).toString().equals("\""+value+"\"") || jsonObject.get(attr).toString().equals(value)) {
                    //b.close();
                    list.add(g.fromJson(jsonObject, clase));
                    //return g.fromJson(jsonObject, clase);
                }
            }
            b.close();
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }
}
