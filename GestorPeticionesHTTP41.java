package ejerciciosHTTP;

import java.io.BufferedReader;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GestorPeticionesHTTP41 {
	
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
		//Parseamos el html generado
		Document doc = Jsoup.parse(html);
		//Seleccionamos los elementos que contienen los nodos a añadir
		Elements elementos = doc.select(".header__region.region.region-header");
		//Establecemos la ruta a guardar
		String origen= System.getProperty("user.dir")+"//src//ejerciciosHTTP//ficheroPrueba.html";
		Path fichero= Paths.get(origen);
		//generamos el contenido que se cargará desde los nodos
		StringBuilder contenidoParaGuardar = new StringBuilder();
		for (Element elemento : elementos) {
			//Sumamos el contenido por cada selector
			contenidoParaGuardar.append(elemento.outerHtml()).append(System.lineSeparator());
		}
		try {
			//Guardamos el fichero
			Files.write(fichero, contenidoParaGuardar.toString().getBytes(StandardCharsets.UTF_8));
			//Confirmación de la acción, no sale a la excepción
			System.out.println("Archivo guardado en: " + fichero.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void obtenerDatosDeAPI(String urlAPI) throws Exception {
		GestorPeticionesHTTP41 gp = new GestorPeticionesHTTP41();
		StringBuilder respuesta = gp.getContenidoMetodoGet(urlAPI);
		JsonObject jsonObject = JsonParser.parseString(respuesta.toString()).getAsJsonObject();
		// Y lo que se quiera hacer con los datos
	}
	
	public static void main (String[] args) {
		try {
			String esquema = "https://";
			String servidor="dle.rae.es/";
			String recurso=URLEncoder.encode("Tiburón",StandardCharsets.UTF_8.name());
			GestorPeticionesHTTP41 gp=new GestorPeticionesHTTP41();
			String direccion=esquema+servidor+recurso;
			StringBuilder resultado=gp.getContenidoMetodoGet(direccion);
			GestorPeticionesHTTP41.writeFile("D:\\IES_INFANTA_ELENA\\tiburon.html", resultado.toString());
			System.out.println("Descarga finalizada");
			extraerDatos(resultado.toString());
			obtenerDatosDeAPI("https://jsonplaceholder.typicode.com/posts");

		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
