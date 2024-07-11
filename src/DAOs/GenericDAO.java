package DAOs;

import LocalDateGsonAdapter.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
        Gson g = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
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
        Gson g = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
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
        Gson g = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
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
        Field[] campos = clase.getDeclaredFields(); // recupero los nombres de atributos de las clases
        String campoIdentificador = campos[0].getName(); //obtengo el nombre identificador de la clase
        boolean wasDeleted = false;
        try {
            BufferedReader b = new BufferedReader(new FileReader(archivo));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            JsonParser parser = new JsonParser();

            while ((line = b.readLine()) != null) {
                JsonObject jsonObject = parser.parse(line).getAsJsonObject();
                if (Integer.parseInt(jsonObject.get(campoIdentificador).toString()) != id) {
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

    public boolean update2(T obj, int id) throws Exception {
        Field[] campos = clase.getDeclaredFields(); // recupero los nombres de atributos de las clases
        String campoIdentificador = campos[0].getName(); //obtengo el nombre identificador de la clase
        boolean wasUpdated = false;
        try {
            BufferedReader b = new BufferedReader(new FileReader(archivo));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            JsonParser parser = new JsonParser();

            while ((line = b.readLine()) != null) {
                JsonObject jsonObject = parser.parse(line).getAsJsonObject();
                if (Integer.parseInt(jsonObject.get(campoIdentificador).toString()) != id) {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                } else {
                    // aca save updated obj
                    Gson g = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
                    String updated_obj = g.toJson(obj);
                    inputBuffer.append(updated_obj);
                    inputBuffer.append('\n');

                    wasUpdated = true;
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
        return wasUpdated;
    }

    public boolean update(T obj) throws Exception {
        Boolean wasUpdate = false;
        try {
            BufferedReader b = new BufferedReader(new FileReader(archivo));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            JsonParser parser = new JsonParser();
            Gson g = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

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
        Field[] campos = clase.getDeclaredFields(); // recupero los nombres de atributos de las clases
        String campoIdentificador = campos[0].getName(); //obtengo el nombre identificador de la clase
        BufferedReader b = new BufferedReader(new FileReader(archivo));
        String line;
        JsonParser parser = new JsonParser();
        Gson g = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        Boolean flag = false;

        try {
            while ((line = b.readLine()) != null && flag == false) {
                JsonObject jsonObject = parser.parse(line).getAsJsonObject();
                if (Integer.parseInt(jsonObject.get(campoIdentificador).toString()) == id) {
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

    public List<T> listSearchByAttribute(String attr, String value) throws FileNotFoundException {
        return listSearchByAttribute(attr, value, clase);
    }


    public List<T> listSearchByAttribute(String attr,String value, Class<T> clase) throws FileNotFoundException {
        List<T> list = new ArrayList<T>();
        BufferedReader b = new BufferedReader(new FileReader(archivo));
        String line;
        JsonParser parser = new JsonParser();
        Gson g = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
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

    public T searchByAttribute(String attr, String value) throws FileNotFoundException {
        return searchByAttribute(attr, value, clase);
    }
    public T searchByAttribute(String attr,String value, Class<T> clase) throws FileNotFoundException { //para buscar segun atributo que queramos del DAO
        BufferedReader b = new BufferedReader(new FileReader(archivo));
        String line;
        JsonParser parser = new JsonParser();
        Gson g = new Gson().newBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        Boolean flag = false;
        try {
            while ((line = b.readLine()) != null && flag == false) {
                JsonObject jsonObject = parser.parse(line).getAsJsonObject();
                if (jsonObject.get(attr).toString().equals("\""+value+"\"") || jsonObject.get(attr).toString().equals(value)) {
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
}
