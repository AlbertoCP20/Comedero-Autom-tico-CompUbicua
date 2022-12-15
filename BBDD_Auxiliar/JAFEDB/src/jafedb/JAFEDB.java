/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jafedb;

/**
 *
 * @author eleee
 */
import java.io.*;
import java.util.*;
import java.text.Normalizer;
import java.util.stream.Collectors;

public class JAFEDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String ruta = "C:/Users/eleee/Documents/Universidad/5º Quinto año/Primer cuatrimestre/Computación ubicua/Laboratorio/Comedero-Autom-tico-CompUbicua/datos_prueba_bd.txt";

        File archivo = new File(ruta);

        String[] listaNombres = {"Antonio", "María", "Jose", "Carmen", "Manuel", "Josefa", "David", "Ana", "Juan", "Isabel",
            "Javier", "Laura", "Francisco", "Cristina", "Daniel", "Marta", "Carlos", "Francisca", "Jesús", "Antonia",
            "Alejandro", "Lucía", "Miguel", "Dolores", "Rafael", "Sara", "Pedro", "Paula", "Ángel", "Elena",
            "Pablo", "Pilar", "Sergio", "Raquel", "Fernando", "Concepción", "Luis", "Manuela", "Jorge", "Mercedes",
            "Alberto", "Beatriz", "Álvaro", "Julia", "Adrián", "Rosario", "Diego", "Nuria", "Raúl", "Juana",
            "Iván", "Silvia", "Enrique", "Teresa", "Rubén", "Encarnación", "Ramón", "Irene", "Óscar", "Alba"};
        String[] listaApellidos = {"García", "González", "Rodríguez", "Fernández", "López", "Martínez", "Sánchez", "Pérez", "Gómez", "Martín",
            "Jiménez", "Raíz", "Hernández", "Díaz", "Moreno", "Muñoz", "Álvarez", "Romero", "Alonso", "Gutiérrez",
            "Navarro", "Torres", "Domínguez", "Vázquez", "Ramos", "Gil", "Ramírez", "Serrano", "Blanco", "Molina",
            "Morales", "Suarez", "Ortega", "Delgado", "Castro", "Ortiz", "Rubio", "Marín", "Sanz", "Núñez",
            "Iglesias", "Medina", "Garrido", "Cortes", "Castillo", "Santos", "Lozano", "Guerrero", "Cano", "Prieto",
            "Méndez", "Cruz", "Calvo", "Gallego", "Vidal", "León", "Márquez", "Herrera", "Peña", "Flores"};
        String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        String[] listaPerroF = {"Luna", "Lola", "Kira", "Nala", "Kiara", "Sasha", "Canela", "Dana", "Mia", "Linda"};
        String[] listaPerroM = {"Lucas", "Bruno", "Rocky", "Toby", "Coco", "Max", "Rocco", "Zeus", "Jack", "Simon"};
        String[] listaGatoF = {"Pelusa", "Mimi", "Lulú", "Kitty", "Ágatha", "Princesa", "Dama", "Duquesa", "Iris", "Isis"};
        String[] listaGatoM = {"Garfield", "Tom", "Simba", "Silvestre", "Tito", "Ulises", "Apolo", "Zarpas", "Doreamon", "Pica"};
        String[] petType = {"Perro", "Gato"};
        String[] gender = {"M", "F"};
        String[] sensorType = {"Presión", "Carga", "Infrarrojo"};
        List<String> nombresUsados = new ArrayList<>();
        List<String> idsComederoUsados = new ArrayList<>();
        Map<String, Object> recordfeeder = new HashMap<>();

        try {
            if (!archivo.exists()) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));

                //Creamos el usuario
                for (int i = 1; i <= 100; i++) {
                    int nomRandom = (int) (Math.random() * (listaNombres.length));
                    String nombre = listaNombres[nomRandom];
                    int pApeRandom = (int) (Math.random() * (listaApellidos.length));
                    String primApellido = listaApellidos[pApeRandom];
                    int sApeRandom = (int) (Math.random() * (listaApellidos.length));
                    String segApellido = listaApellidos[sApeRandom];
                    String nombreCompleto = nombre + primApellido + segApellido;
                    if (!nombresUsados.contains(nombreCompleto.toLowerCase())) {
                        nombresUsados.add(nombreCompleto);
                        String email = cleanString(nombre.toLowerCase()) + "." + cleanString(primApellido.toLowerCase()) + "." + cleanString(segApellido.toLowerCase()) + "@gmail.com";
                        String password = "";
                        for (int s = 0; s <= 2; s++) {
                            int numRandom = (int) (Math.random() * (10));
                            int charRandom = (int) (Math.random() * (letras.length));
                            password = password + numRandom + letras[charRandom];
                        }
                        //System.out.print("insert into user values(" + i + ", '" + nombre + "', '" + primApellido + "', '" + segApellido + "', '" + email + "', '" + password + "');\n");
                        bw.write("insert into user values(" + i + ", '" + nombre + "', '" + primApellido + "', '" + segApellido + "', '" + email + "', '" + password + "');\n");

                        //Creamos su mascota
                        int genRandom = (int) (Math.random() * (2));
                        String genero = gender[genRandom];
                        int tipoRandom = (int) (Math.random() * (2));
                        String tipo = petType[tipoRandom];
                        int nomMascRandom = (int) (Math.random() * (10));
                        String nombreM;
                        if (genero.equals("M") && tipo.equals("Perro")) {
                            nombreM = listaPerroM[nomMascRandom];
                        } else if (genero.equals("M") && tipo.equals("Gato")) {
                            nombreM = listaGatoM[nomMascRandom];
                        } else if (genero.equals("F") && tipo.equals("Perro")) {
                            nombreM = listaPerroF[nomMascRandom];
                        } else {
                            nombreM = listaGatoF[nomMascRandom];
                        }
                        int peso;
                        if (tipo.equals("Perro")) {
                            peso = (int) (Math.random() * (100) + 5);
                        } else {
                            peso = (int) (Math.random() * (10) + 1);
                        }
                        int estadoRandom = (int) (Math.random() * (2));
                        boolean estado = false;
                        if (estadoRandom == 0) {
                            estado = true;
                        }
                        //System.out.print("insert into pet values(" + i + ", '" + nombreM + "', '" + genero + "', " + peso + ", '" + tipo + "', " + estado + ", " + i + ");\n");
                        bw.write("insert into pet values(" + i + ", '" + nombreM + "', '" + genero + "', " + peso + ", '" + tipo + "', " + estado + ", " + i + ");\n");                        
                    }
                }

                //Creamos el comedero
                int comederosNull = 0;
                int comederosNoNull = 1;
                int contSensores = 0;
                int contRations = 1;
                int contRecords = 1;

                //Para los registros que necesitaremos dentro del bucle
                float presion = 300;
                float carga = 500;
                float infrarrojo = 100;
                float value;
                List<String> getKeysFromValue;
                for (int i = 1; i <= 150; i++) {
                    int nullRandom = (int) (Math.random() * (2));
                    float percentFood = (float) (Math.round((Math.random() * (101)) * 100d) / 100d);
                    float percentWater = (float) (Math.round((Math.random() * (101)) * 100d) / 100d);
                    String id = "";
                    for (int s = 0; s <= 2; s++) {
                        int numRandom = (int) (Math.random() * (10));
                        int charRandom = (int) (Math.random() * (letras.length));
                        id = id + numRandom + letras[charRandom];

                    }
                    if (!idsComederoUsados.contains(id)) {
                        idsComederoUsados.add(id);
                        if (nullRandom != 0 && comederosNull < 50) { 
                            //System.out.print("insert into feeder values('" + id + "', " + percentFood + ", " + percentWater + ", NULL);\n");
                            bw.write("insert into feeder values('" + id + "', " + percentFood + ", " + percentWater + ", NULL);\n");    
                            comederosNull++;
                        } else {
                            //System.out.print("insert into feeder values('" + id + "', " + percentFood + ", " + percentWater + ", " + comederosNoNull + ");\n");
                            bw.write("insert into feeder values('" + id + "', " + percentFood + ", " + percentWater + ", " + comederosNoNull + ");\n");    
                            comederosNoNull++;
                        }
                    }

                    //Creamos sus sensores
                    //System.out.print("insert into sensor values(" + (i + contSensores) + ", '" + sensorType[0] + "', '" + id + "');\n");
                    bw.write("insert into sensor values(" + (i + contSensores) + ", '" + sensorType[0] + "', '" + id + "');\n");  
                    recordfeeder.put(String.valueOf((i + contSensores)), id);
                    contSensores++;
                    //System.out.print("insert into sensor values(" + (i + contSensores) + ", '" + sensorType[1] + "', '" + id + "');\n");
                    bw.write("insert into sensor values(" + (i + contSensores) + ", '" + sensorType[1] + "', '" + id + "');\n");
                    recordfeeder.put(String.valueOf((i + contSensores)), id);
                    contSensores++;
                    //System.out.print("insert into sensor values(" + (i + contSensores) + ", '" + sensorType[2] + "', '" + id + "');\n");
                    bw.write("insert into sensor values(" + (i + contSensores) + ", '" + sensorType[2] + "', '" + id + "');\n");
                    recordfeeder.put(String.valueOf((i + contSensores)), id);

                    //Creamos sus raciones
                    int numRatRandom = (int) (Math.random() * (5) + 1);
                    for (int t = 1; t <= numRatRandom; t++) {
                        int horaRandom = (int) (Math.random() * (23) + 1);
                        int minRandom = (int) (Math.random() * (59) + 1);
                        String sMinRandom;
                        if (minRandom < 10) {
                            sMinRandom = "0" + minRandom;
                        } else {
                            sMinRandom = "" + minRandom;
                        }
                        float gramosRandom = (float) (Math.round((Math.random() * (500) + 1) * 100d) / 100d);
                        //System.out.print("insert into ration values(" + contRations + ", '" + horaRandom + ":" + sMinRandom + "', " + gramosRandom + ", '" + id + "');\n");
                        bw.write("insert into ration values(" + contRations + ", '" + horaRandom + ":" + sMinRandom + "', " + gramosRandom + ", '" + id + "');\n");
                        contRations++;

                        //Añado los primeros registros
                        int mes = (int) (Math.random() * (12 - 1 + 1) + 1);
                        int dia;
                        if ((mes == 1) || (mes == 3) || (mes == 5) || (mes == 7) || (mes == 8) || (mes == 10) || (mes == 12)) {
                            dia = (int) (Math.random() * (31 - 1 + 1) + 1);
                        } else if (mes == 2) {
                            dia = (int) (Math.random() * (29 - 1 + 1) + 1);
                        } else {
                            dia = (int) (Math.random() * (30 - 1 + 1) + 1);
                        }
                        if (t == 1) {
                            getKeysFromValue = getMultipleKeysByValue(recordfeeder, id);
                            //System.out.print("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + horaRandom + ":" + sMinRandom + "', " + presion + ", " + (contRations - 1) + ", " + getKeysFromValue.get(2) + ", '" + id + "');\n");
                            bw.write("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + horaRandom + ":" + sMinRandom + "', " + presion + ", " + (contRations - 1) + ", " + getKeysFromValue.get(2) + ", '" + id + "');\n");
                            contRecords++;
                            //System.out.print("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + horaRandom + ":" + sMinRandom + "', " + carga + ", " + (contRations - 1) + ", " + getKeysFromValue.get(0) + ", '" + id + "');\n");
                            bw.write("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + horaRandom + ":" + sMinRandom + "', " + carga + ", " + (contRations - 1) + ", " + getKeysFromValue.get(0) + ", '" + id + "');\n");
                            contRecords++;
                            //System.out.print("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + horaRandom + ":" + sMinRandom + "', " + infrarrojo + ", " + (contRations - 1) + ", " + getKeysFromValue.get(1) + ", '" + id + "');\n");
                            bw.write("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + horaRandom + ":" + sMinRandom + "', " + infrarrojo + ", " + (contRations - 1) + ", " + getKeysFromValue.get(1) + ", '" + id + "');\n");
                            contRecords++;
                        }

                    }
                    //Creamos algunos registros más registros
                    for (int v = 0; v < sensorType.length; v++) {
                        int numRecRandom = (int) (Math.random() * (5) + 1);
                        int mes = (int) (Math.random() * (12 - 1 + 1) + 1);
                        int dia;
                        if ((mes == 1) || (mes == 3) || (mes == 5) || (mes == 7) || (mes == 8) || (mes == 10) || (mes == 12)) {
                            dia = (int) (Math.random() * (31 - 1 + 1) + 1);
                        } else if (mes == 2) {
                            dia = (int) (Math.random() * (29 - 1 + 1) + 1);
                        } else {
                            dia = (int) (Math.random() * (30 - 1 + 1) + 1);
                        }
                        int min2Random = (int) (Math.random() * (59) + 1);
                        String sMin2Random;
                        if (min2Random < 10) {
                            sMin2Random = "0" + min2Random;
                        } else {
                            sMin2Random = "" + min2Random;
                        }
                        int hora2Random = (int) (Math.random() * (23) + 1);
                        for (int s = 1; s < numRecRandom; s++) {
                            getKeysFromValue = getMultipleKeysByValue(recordfeeder, id);
                            switch (v) {
                                case 0:
                                    value = (float) (Math.round((Math.random() * (presion) + 1) * 100d) / 100d);
                                    //System.out.print("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + hora2Random + ":" + sMin2Random + "', " + value + ", " + (contRations - 1) + ", " + getKeysFromValue.get(2) + ", '" + id + "');\n");
                                    bw.write("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + hora2Random + ":" + sMin2Random + "', " + value + ", " + (contRations - 1) + ", " + getKeysFromValue.get(2) + ", '" + id + "');\n");
                                    contRecords++;
                                    break;
                                case 1:
                                    value = (float) (Math.round((Math.random() * (carga) + 1) * 100d) / 100d);
                                    //System.out.print("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + hora2Random + ":" + sMin2Random + "', " + value + ", " + (contRations - 1) + ", " + getKeysFromValue.get(0) + ", '" + id + "');\n");
                                    bw.write("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + hora2Random + ":" + sMin2Random + "', " + value + ", " + (contRations - 1) + ", " + getKeysFromValue.get(0) + ", '" + id + "');\n");
                                    contRecords++;
                                    break;
                                default:
                                    value = (float) (Math.round((Math.random() * (infrarrojo) + 1) * 100d) / 100d);                                    
                                    //System.out.print("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + hora2Random + ":" + sMin2Random + "', " + value + ", " + (contRations - 1) + ", " + getKeysFromValue.get(1) + ", '" + id + "');\n");
                                    bw.write("insert into record values(" + contRecords + ", '2022-" + mes + "-" + dia + "', '" + hora2Random + ":" + sMin2Random + "', " + value + ", " + (contRations - 1) + ", " + getKeysFromValue.get(1) + ", '" + id + "');\n");
                                    break;
                            }
                            contRecords++;
                        }
                    }
                }
                bw.close();
            }
        } catch (IOException e) {
        }
    }

    public static String cleanString(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }

    public static <String> List<String> getMultipleKeysByValue(Map<String, Object> map, Object value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
