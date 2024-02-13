package test.scv;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GestorPeticionesHTTPGet {

    public StringBuilder getContenidoMetodoGet (String direccion) throws Exception{
        StringBuilder respuesta = new StringBuilder();
        URL url=new URL(direccion);
        HttpURLConnection conexion=(HttpURLConnection)url.openConnection();
        conexion.setRequestMethod("GET");
        conexion.setRequestProperty("Content-type", "text/plain");
        conexion.setRequestProperty("charset", "utf-8");
        conexion.setRequestProperty("User-Agent","Mozilla/5.0");
        int estado=conexion.getResponseCode();
        Reader streamReader=null;
        if (estado==HttpURLConnection.HTTP_OK) {
            streamReader=new InputStreamReader(conexion.getInputStream());
            int caracter;
            while((caracter=streamReader.read())!=-1){
                respuesta.append((char)caracter);
            }
        }else {
            throw new Exception("Error HTTP "+estado);
        }
        conexion.disconnect();
        return respuesta;
    }

    public static void writeFile(String strPath, String contenido) throws IOException {
        Path path=Paths.get(strPath);
        byte[] strToBytes = contenido.getBytes();
        Files.write(path, strToBytes);
    }

    public static void extraerDatos(String html) {
        Document doc = Jsoup.parse(html);
        Elements elementos = doc.select("tuSelectorCSS"); // Reemplaza "tuSelectorCSS" con el selector CSS adecuado
        for (Element elemento : elementos) {
            System.out.println(elemento.text());
        }
    }

    public static void extraerDatos2(String html) throws IOException {
        Document doc = Jsoup.connect(html).get();

        // Seleccionar los elementos que contienen los títulos de las noticias
        Elements titulos = doc.select("h2.articulo-titulo");

        // Imprimir los títulos de las noticias
        for (int i = 0; i < titulos.size(); i++) {
            System.out.println(titulos.get(i).text());
        }
    }

    public static void main (String[] args) {
        try {
            String esquema = "https://";
            String servidor="www.twitch.tv";
            String path="/find";
            String texto= URLEncoder.encode("Tiburón",StandardCharsets.UTF_8.name());
            String parametros="?q"+texto;
            GestorPeticionesHTTPGet gp=new GestorPeticionesHTTPGet();
            String direccion=esquema+servidor+path+parametros;
            String html = esquema + servidor;
            StringBuilder resultado=gp.getContenidoMetodoGet(direccion);
            GestorPeticionesHTTP42.writeFile("E:\\FP Grado Superior\\2 DAM\\PSP\\ArchivosWeb\\skidrow.html", resultado.toString());
            System.out.println("Descarga finalizada");
            String origen= System.getProperty("user.dir")+"//src";
            System.out.println(origen);
            extraerDatos2(html);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
