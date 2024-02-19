package test.scv.EjemplosServerWeb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GestorPeticionesHTTPDelete {

    public static void main(String[] args) {
        try {
            // URL a la que se enviará la solicitud DELETE
            String url = "https://es.wikipedia.org/wiki/API_Java";

            // Crear objeto URL
            URL obj = new URL(url);

            // Abrir conexión HttpURLConnection
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Método HTTP DELETE
            con.setRequestMethod("DELETE");

            // Leer la respuesta del servidor
            int responseCode = con.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprimir respuesta del servidor
            System.out.println("Respuesta del servidor: " + response.toString());
        } catch (Exception e) {
            System.err.println("Error al enviar la solicitud DELETE: " + e.getMessage());
        }
    }

}
