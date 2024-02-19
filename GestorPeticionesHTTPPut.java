package test.scv.EjemplosServerWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GestorPeticionesHTTPPut {
    public static void main(String[] args) throws IOException {
        String url = "https://es.wikipedia.org/wiki/API_Java";
        String requestBody = "{\"clave\": \"valor\"}";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Configurar la petición como PUT
        con.setRequestMethod("PUT");

        // Establecer el Content-Type del cuerpo de la petición
        con.setRequestProperty("Content-Type", "application/json");

        // Habilitar la escritura en la conexión para enviar datos
        con.setDoOutput(true);

        // Escribir el cuerpo de la petición en la conexión
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Leer la respuesta del servidor
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Respuesta del servidor: " + response.toString());
        }

        // Cerrar la conexión
        con.disconnect();
    }
}
