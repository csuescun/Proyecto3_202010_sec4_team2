package model.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.gson.*;
import com.google.gson.stream.*;

import model.data_structures.*;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {


	public static String PATH = "./data/Comparendos_DEI_2018_Bogotá_D.C.geojson";

	public static String ARCHIVO_ESTACIONES = "./data/estacionpolicia.geojson";

	public static String RUTA_VERTICES = "./data/bogota_vertices.txt";

	public static String RUTA_ARCOS = "./data/bogota_arcos.txt";
	
	private static final int EARTH_RADIUS = 6371;


	/**
	 * Atributos del modelo del mundo
	 */

	//ESTRUCTURAS DE DATOS

	private Queue<Comparendo> comparendos;

	private Queue<EstacionPolicia> estaciones;

	private GrafoNoDirigido<Integer, LatitudYLongitud> grafo;

	private GrafoNoDirigido<Integer, LatitudYLongitud> grafoArchivo;



	//ADICIONALES


	private Comparendo mayorComparendo;

	private EstacionPolicia mayorEstacion;


	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------

	public Modelo()
	{
		comparendos = new Queue<Comparendo>();

		estaciones = new Queue<EstacionPolicia>();

		grafo = new GrafoNoDirigido<Integer, LatitudYLongitud>();
		grafoArchivo = new GrafoNoDirigido<Integer, LatitudYLongitud>();
	}



	// -----------------------------------------------------------------
	// Métodos de carga
	// -----------------------------------------------------------------


	/**
	 * Cargar comparendos
	 */
	public void cargarDatos() 

	{


		JsonReader reader;

		try {


			int mayorID  = 0;
			reader = new JsonReader(new FileReader( PATH));
			JsonParser jsonp = new JsonParser();

			JsonElement elem = jsonp.parse(reader);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();


			SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

			for(JsonElement e: e2) {
				int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();


				String FECHA_HORA = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();

				String MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETECCION").getAsString();
				String CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHICULO").getAsString();
				String TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVICIO").getAsString();
				String INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				String DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRACCION").getAsString();	
				String LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();
				String MUNICIPIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("MUNICIPIO").getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();

				Comparendo c = new Comparendo(OBJECTID, FECHA_HORA, MEDIO_DETE, CLASE_VEHI, TIPO_SERVI, INFRACCION,DES_INFRAC, LOCALIDAD, longitud, latitud, MUNICIPIO);
				String key = c.darSimpleDate()+c.darClaseVehiculo()+c.darInfraccion();


				//Faltan las otras estructuras
				comparendos.enqueue(c);




				if(OBJECTID > mayorID)
				{
					mayorComparendo = c;
				}

			}

		} 


		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}


	/**
	 * Cargar comparendos - archivo small
	 */

	public void cargarDatosSmall() 

	{
		JsonReader reader;

		try {

			int mayorID  = 0;
			reader = new JsonReader(new FileReader("./data/comparendos_dei_2018_small.geojson"));
			JsonParser jsonp = new JsonParser();

			JsonElement elem = jsonp.parse(reader);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();


			SimpleDateFormat parser=new SimpleDateFormat("yyyy/MM/dd");

			for(JsonElement e: e2) {
				int OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();

				String FECHA_HORA = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();	


				String MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETE").getAsString();
				String CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHI").getAsString();
				String TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVI").getAsString();
				String INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				String DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRAC").getAsString();	
				String LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();

				Comparendo c = new Comparendo(OBJECTID, FECHA_HORA, MEDIO_DETE, CLASE_VEHI, TIPO_SERVI, INFRACCION,DES_INFRAC, LOCALIDAD, longitud, latitud,"");
				String key = c.darSimpleDate()+c.darClaseVehiculo()+c.darInfraccion();

				comparendos.enqueue(c);


			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


	}

	/**
	 * Cargar estaciones
	 */

	public void cargarEstaciones()
	{
		JsonReader reader;

		try
		{
			reader = new JsonReader(new FileReader(ARCHIVO_ESTACIONES));
			JsonParser jsonp = new JsonParser();

			JsonElement elem = jsonp.parse(reader);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();

			for(JsonElement e: e2)
			{


				int objectID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
				String nombre = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPONOMBRE").getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();

				EstacionPolicia ep = new EstacionPolicia(nombre, objectID, latitud, longitud);
				estaciones.enqueue(ep);

			}


		}

		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


	}

	/**
	 * Cargar vertices
	 * @throws IOException
	 */

	public void cargarVertices() throws IOException
	{
		FileReader fr = new FileReader(new File(RUTA_VERTICES));
		BufferedReader br = new BufferedReader(fr);

		String l = br.readLine();

		while(l != null)
		{
			String[] info = l.split(",");
			int objectID = Integer.parseInt(info[0]);
			double longitud = Double.parseDouble(info[1]);
			double latitud = Double.parseDouble(info[2]);

			LatitudYLongitud ubicacion = new LatitudYLongitud(latitud, longitud);

			grafo.addVertex(objectID, ubicacion);

			l= br.readLine();

		}

		br.close();
		fr.close();
	}


	/**
	 * Cargar arcos
	 * @throws IOException
	 */
	public void cargarArcos() throws IOException
	{
		FileReader fr = new FileReader(new File(RUTA_ARCOS));
		BufferedReader br = new BufferedReader(fr);

		String l = br.readLine();

		while(l.startsWith("#"))
		{
			l = br.readLine();
		}
		while(l != null)
		{
			String[] info = l.split(" ");
			int idVerticeInicial = Integer.parseInt(info[0]);
			double latitudInicial = grafo.getInfoVertex(idVerticeInicial).darLatitud();
			double longitudInicial = grafo.getInfoVertex(idVerticeInicial).darLongitud();

			int i = 1;
			while(i < info.length)
			{
				int idVerticeFinal = Integer.parseInt(info[i]);
				double latitudFinal= grafo.getInfoVertex(idVerticeFinal).darLatitud();
				double longitudFinal = grafo.getInfoVertex(idVerticeFinal).darLongitud();

				grafo.addEdge(idVerticeInicial, idVerticeFinal, distance(latitudInicial, longitudInicial, latitudFinal, longitudFinal));

				i++;
			}

			l= br.readLine(); 

		}

		br.close();
		fr.close();

	}

	// -----------------------------------------------------------------
	// Métodos crear archivo y cargar JSON
	// -----------------------------------------------------------------


	public void crearJSON(String rutaArchivo) throws IOException
	{
		FileWriter fw = new FileWriter(rutaArchivo);

		JsonObject g = new JsonObject();
		JsonArray listaVertices = new JsonArray();

		int i = 0;
		while(i < grafo.V())
		{
			JsonObject vertice = new JsonObject(); 

			vertice.addProperty("OBJECTID", i);

			vertice.addProperty("LONGITUD", grafo.getInfoVertex(i).darLongitud());
			vertice.addProperty("LATITUD", grafo.getInfoVertex(i).darLatitud());
			Iterator<Arco<Integer>> arcos = grafo.getVertex(i).darAdyacentes().iterator();
			JsonArray listaArcos = new JsonArray();


			while(arcos.hasNext())
			{
				Arco<Integer> a = arcos.next();
				JsonObject arcoTemp = new JsonObject(); 
				arcoTemp.addProperty("IDVERTEX_FIN", a.darFin());
				arcoTemp.addProperty("COSTO", a.darCosto());

				listaArcos.add(arcoTemp);
			}

			vertice.add("arcos", listaArcos);
			listaVertices.add(vertice);
			i++;
		}



		g.add("features", listaVertices);
		fw.write(g.toString());


		fw.flush();
		fw.close();

	}


	public void leerJSON(String pRutaArchivo) 
	{
		JsonReader reader;

		try
		{
			reader = new JsonReader(new FileReader(pRutaArchivo));	
			JsonParser jsonp = new JsonParser();

			JsonElement elem = jsonp.parse(reader);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();

			for(JsonElement e: e2)
			{
				int objectID = e.getAsJsonObject().get("OBJECTID").getAsInt();
				double longitud = e.getAsJsonObject().get("LONGITUD").getAsDouble();
				double latitud = e.getAsJsonObject().get("LATITUD").getAsDouble();

				LatitudYLongitud ubicacion = new LatitudYLongitud(latitud, longitud);
				grafoArchivo.addVertex(objectID, ubicacion);

				JsonArray arcos = e.getAsJsonObject().get("arcos").getAsJsonArray();
				for(JsonElement a : arcos)
				{
					int idVertexFin = a.getAsJsonObject().get("IDVERTEX_FIN").getAsInt();
					double costo = a.getAsJsonObject().get("COSTO").getAsDouble();

					grafoArchivo.addEdge(objectID, idVertexFin, costo);
				}
			}

		}

		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}


	// -----------------------------------------------------------------
	// Métodos básicos
	// -----------------------------------------------------------------

	public int darTamanoComparendos()
	{
		return comparendos.darTamano();
	}


	public Comparendo darPrimerComparendo()
	{
		return comparendos.darPrimerElemento();
	}

	public Comparendo darUltimoComparendo()
	{
		return comparendos.darUltimoElemento();
	}


	public Comparendo darMayorComparendo()
	{
		return mayorComparendo;
	}

	public Queue<EstacionPolicia> darEstaciones()
	{
		return estaciones;
	}

	public GrafoNoDirigido<Integer, LatitudYLongitud> darGrafo()
	{
		return grafo;
	}
	
	
	public Comparendo[] copiarArreglo(Queue<Comparendo> arreglo)
	{
		Comparendo[] comparendos = new Comparendo[arreglo.darTamano()];
		int i = 0;
		for(Comparendo e : arreglo)
		{
			comparendos[i] = e;
			i++;
		}
		return comparendos;
	}


	public int darTamanoEstaciones()
	{
		return estaciones.darTamano();
	}
	

	public int darNumeroVertices()
	{
		return grafo.V();
	}

	
	public int darNumeroArcos()
	{
		return grafo.E();
	}
	

	public GrafoNoDirigido<Integer, LatitudYLongitud> darGrafoCreado()
	{
		return grafoArchivo;
	}


	//Distancia haversine (tomado de: https://github.com/jasonwinn/haversine/blob/master/Haversine.java)

	public static double distance(double startLat, double startLong, double endLat, double endLong) 
	{

		double dLat  = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat   = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c; // <-- d
	}

	public static double haversin(double val) 
	{
		return Math.pow(Math.sin(val / 2), 2);
	}

	


}
