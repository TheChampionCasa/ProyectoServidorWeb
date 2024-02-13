package test.scv.EjemplosServerWeb;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class GestorPeticionesHTTPGet {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la URL a la que desea hacer la solicitud: ");
        String urlStr = scanner.nextLine();

        System.out.print("Ingrese el nombre del archivo donde desea guardarlo (unicamente el nombre): ");
        String fileName = scanner.nextLine();
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "text/plain");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("User-Agent","Mozilla/5.0");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                // Guardar la respuesta en un archivo HTML
                FileWriter writer = new FileWriter("src/main/resources/ArchivosWeb/"+fileName+".html");//La ruta es donde quieras crear el archivo HTML
                writer.write(response.toString());
                writer.close();


                System.out.println("La respuesta se ha guardado en el archivo " + fileName);
            } else {
                System.out.println("Error al obtener la respuesta. Código de respuesta: " + responseCode);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


