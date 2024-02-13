package test.scv.EjemplosServerWeb;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GestorPeticionesHTTPPost {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la URL a la que desea hacer la solicitud POST: ");
        String urlStr = scanner.nextLine();

        System.out.print("Ingrese los datos a enviar en la solicitud (en formato JSON, por ejemplo): ");
        String postData = scanner.nextLine();

        System.out.print("Ingrese el nombre del archivo donde desea guardar la respuesta (unicamente el nombre): ");
        String fileName = scanner.nextLine();

        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json"); // Especifica el tipo de contenido como JSON
            connection.setDoOutput(true); // Habilita la escritura de datos

            // Enviar los datos POST
            OutputStream os = connection.getOutputStream();
            os.write(postData.getBytes());
            os.flush();
            os.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Guardar la respuesta en un archivo HTML
                FileWriter writer = new FileWriter("src/main/resources/ArchivosWeb/" + fileName + ".html");
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

