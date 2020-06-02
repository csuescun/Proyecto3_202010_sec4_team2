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


	public static String PATH = "./data/Comparendos_DEI_2018_Bogotá_D.C_small_50000_sorted.geojson";

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

	private GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> grafo;

	private GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> grafoArchivo;

	private MaxHeapCP<Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>> heapCecilia;

	private Dijkstra dijkstra;

	//ADICIONALES


	private Comparendo mayorComparendo;

	private EstacionPolicia mayorEstacion;

	private Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> mayorVertice;



	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------

	public Modelo()
	{
		comparendos = new Queue<Comparendo>();

		estaciones = new Queue<EstacionPolicia>();
		heapCecilia  = new MaxHeapCP<Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>>(new ComparadorNumComparendos<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>());

		grafo = new GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>();
		grafoArchivo = new GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>();

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

			int OBJECTID;
			String FECHA_HORA;
			String MEDIO_DETE;
			String CLASE_VEHI;
			String TIPO_SERVI;
			String INFRACCION;
			String DES_INFRAC;
			String LOCALIDAD;
			String MUNICIPIO;
			double longitud;
			double latitud;

			for(JsonElement e: e2) {
				OBJECTID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();


				FECHA_HORA = e.getAsJsonObject().get("properties").getAsJsonObject().get("FECHA_HORA").getAsString();

				MEDIO_DETE = e.getAsJsonObject().get("properties").getAsJsonObject().get("MEDIO_DETECCION").getAsString();
				CLASE_VEHI = e.getAsJsonObject().get("properties").getAsJsonObject().get("CLASE_VEHICULO").getAsString();
				TIPO_SERVI = e.getAsJsonObject().get("properties").getAsJsonObject().get("TIPO_SERVICIO").getAsString();
				INFRACCION = e.getAsJsonObject().get("properties").getAsJsonObject().get("INFRACCION").getAsString();
				DES_INFRAC = e.getAsJsonObject().get("properties").getAsJsonObject().get("DES_INFRACCION").getAsString();	
				LOCALIDAD = e.getAsJsonObject().get("properties").getAsJsonObject().get("LOCALIDAD").getAsString();
				MUNICIPIO = e.getAsJsonObject().get("properties").getAsJsonObject().get("MUNICIPIO").getAsString();

				longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();


				Comparendo c = new Comparendo(OBJECTID, FECHA_HORA, MEDIO_DETE, CLASE_VEHI, TIPO_SERVI, INFRACCION,DES_INFRAC, LOCALIDAD, longitud, latitud, MUNICIPIO);


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
			int mayorID  = 0;
			reader = new JsonReader(new FileReader(ARCHIVO_ESTACIONES));
			JsonParser jsonp = new JsonParser();

			JsonElement elem = jsonp.parse(reader);
			JsonArray e2 = elem.getAsJsonObject().get("features").getAsJsonArray();

			for(JsonElement e: e2)
			{


				int objectID = e.getAsJsonObject().get("properties").getAsJsonObject().get("OBJECTID").getAsInt();
				String nombre = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPONOMBRE").getAsString();

				String descripcion = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODESCRIP").getAsString();
				String direccion = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPODIR_SITIO").getAsString();
				String servicio = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOSERVICIO").getAsString() ;
				String horario =  e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOHORARIO").getAsString();
				String telefono = e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOTELEFON").getAsString() ;
				String iu =  e.getAsJsonObject().get("properties").getAsJsonObject().get("EPOIULOCAL").getAsString();

				double longitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(0).getAsDouble();

				double latitud = e.getAsJsonObject().get("geometry").getAsJsonObject().get("coordinates").getAsJsonArray()
						.get(1).getAsDouble();

				EstacionPolicia ep = new EstacionPolicia(nombre, objectID, descripcion, direccion, servicio, horario, telefono, iu, latitud, longitud);
				estaciones.enqueue(ep);

				if(objectID > mayorID)
				{
					mayorEstacion = ep;
				}
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
		int mayorID  = 0;
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

			if(objectID > mayorID)
			{
				mayorVertice  = new Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>(objectID, ubicacion); 
			}

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
		int mayorID  = 0;
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
				arcoTemp.addProperty("COSTO_DISTANCIA", a.darCostoDistancia());
				arcoTemp.addProperty("COSTO_COMPARENDOS", a.darCostoComparendos());

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
					double costoDist = a.getAsJsonObject().get("COSTO_DISTANCIA").getAsDouble();

					grafoArchivo.addEdge(objectID, idVertexFin, costoDist);
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

	public EstacionPolicia darMayorEstacion()
	{
		return mayorEstacion;
	}

	public String darMayorVertice()
	{
		return "ID: "+ mayorVertice.darID() + " "+ mayorVertice.darValor().toString();
	}

	public String darArcosMayor()
	{
		if( mayorVertice.darAdyacentes().darTamano() > 0)
		{
			String respuesta = "( " + mayorVertice.darID();
			Iterator<Arco<Integer>> arcos = mayorVertice.darAdyacentes().iterator();

			while(arcos.hasNext())
			{
				Arco<Integer> actual = arcos.next();
				respuesta = respuesta + " , " + actual.darFin();
			}

			respuesta = respuesta + " )";



			return respuesta;
		}

		else
		{
			return "Este vertice no tiene adyacentes";
		}
	}

	public Queue<EstacionPolicia> darEstaciones()
	{
		return estaciones;
	}

	public GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> darGrafo()
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


	public GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> darGrafoCreado()
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



	// -----------------------------------------------------------------
	// Requerimientos iniciales
	// -----------------------------------------------------------------

	public Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> darVerticeMasCercano(double pLatitud, double pLongitud)
	{
		Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> menor = grafo.getVertex(0);
		double distanciaMin = distance(pLatitud, pLongitud, menor.darValor().darLatitud(), menor.darValor().darLongitud());

		int i = 1; 
		while( i < grafo.V())
		{
			Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> actual = grafo.getVertex(i);
			double distancia = distance(pLatitud, pLongitud, actual.darValor().darLatitud(), actual.darValor().darLongitud());

			if(distancia < distanciaMin)
			{
				menor = actual;
			}

			i++;
		}

		return menor;
	}


	public Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> darVerticeMasCercano2(double pLatitud, double pLongitud)
	{
		Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> menor = grafo.getVertex(0);
		double distanciaMin = distance(pLatitud, pLongitud, menor.darValor().darLatitud(), menor.darValor().darLongitud());


		int i = 1; 
		boolean encontrado = false;
		while( i < grafo.V() & !encontrado)
		{
			Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> actual = grafo.getVertex(i);
			double distancia = distance(pLatitud, pLongitud, actual.darValor().darLatitud(), actual.darValor().darLongitud());

			if(distancia < 0.025)
			{
				menor = actual;
				encontrado = true;	
			}
			i++;
		}

		return menor;
	}

	public void adicionarComparendosAVertices()
	{

		Iterator<Comparendo> comp = comparendos.iterator();
		int i = 0;
		while(comp.hasNext() & i <600)
		{
			Comparendo actual = comp.next();
			Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> masCercano = darVerticeMasCercano2(actual.darLatitud(), actual.darLongitud());

			masCercano.agregarA1(actual);
			i++;
		}
	}


	public void adicionarComparendosArcos()
	{
		for(int i = 0; i < grafo.V() ; i++)
		{
			Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> buscado = grafo.getVertex(i);
			int comp = buscado.darAdicional1().darTamano();

			Iterator<Arco<Integer>> adyacentes = buscado.darAdyacentes().iterator();

			while(adyacentes.hasNext())
			{
				Arco<Integer> actual = adyacentes.next();
				Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> fin = grafo.getVertex(actual.darFin());
				int costoComparendos = comp + fin.darAdicional1().darTamano();

				actual.cambiarCostoComparendos(costoComparendos);
			}
		}

	}



	public void adicionarEstacionesDePolicia()
	{
		Iterator<EstacionPolicia> est = estaciones.iterator();

		while(est.hasNext())
		{
			EstacionPolicia actual = est.next();
			Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> masCercano = darVerticeMasCercano(actual.darLatitud(), actual.darLongitud());

			masCercano.agregarA2(actual);

		}
	}

	// -----------------------------------------------------------------
	// Requerimientos 
	// -----------------------------------------------------------------

	public Dijkstra dijkstra(int s)
	{
		return dijkstra = new Dijkstra<>(grafo,s );
	}

	public void requerimiento2A()
	{

	}

	public GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> requerimiento1B(double latitudInicial, double longitudInicial, double latitudFinal, double longitudFinal)
	{
		GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> respuesta = new GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>();
		int costoComparendos = 0;
		int costoDistancia = 0 ;

		Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> vertexInicial = darVerticeMasCercano(latitudInicial, longitudInicial);
		Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> vertexFinal = darVerticeMasCercano(latitudFinal, longitudFinal);

		respuesta.addVertex(vertexInicial.darID(), vertexInicial.darValor());
		respuesta.addVertex(vertexFinal.darID(), vertexFinal.darValor());

		System.out.println("El ID del vertice de inicio es: "+ vertexInicial.darID());
		System.out.println("El ID del vertice de destino es: " + vertexFinal.darID());

		Dikjstra2<LatitudYLongitud, Comparendo, EstacionPolicia> DJ = new Dikjstra2<LatitudYLongitud, Comparendo, EstacionPolicia>(grafo, vertexInicial);
		Iterator<Arco<Integer>> ruta = DJ.pathTo(vertexFinal);

		if(ruta != null)
		{
			while(ruta.hasNext())
			{
				Arco<Integer> actual = ruta.next();

				Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> inicioActual = grafo.getVertex(actual.darInicio());
				Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> finalActual = grafo.getVertex(actual.darFin());

				respuesta.addVertex(actual.darInicio(), inicioActual.darValor());
				respuesta.addVertex(actual.darFin(), finalActual.darValor());
				respuesta.addEdge(actual.darInicio(), actual.darFin(), actual.darCostoDistancia());

				System.out.println("Siguiente paso: " + "ID: " + actual.darFin() + finalActual.darValor().toString());
				costoComparendos += actual.darCostoComparendos();
				costoDistancia += actual.darCostoDistancia();
			}	

			System.out.println("Se ha llegado al destino deseado" + "---------\n");
			System.out.println("Total de vértices del recorrido:" + respuesta.V());
			System.out.println("Costo por comparendos: " + costoComparendos);
			System.out.println("Costo por distancia: " + costoDistancia);

		}

		else
		{
			System.out.println("Lastimosamente no hay camino entre las dos ubicaciones deseadas");
		}

		return respuesta;
	}

	public GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> requerimiento2B(int M)
	{
		GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> respuesta = new GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>();
		int costo = 0;

		for(int i = 0; i < M; i++)
		{
			Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> actual = (Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>) heapCecilia.darElementos()[i];
			respuesta.addVertex(actual.darID(), actual.darValor());

		}

		for(int i= 0; i < M; i++)
		{
			Dikjstra2<LatitudYLongitud, Comparendo, EstacionPolicia> DJ = new Dikjstra2<LatitudYLongitud, Comparendo, EstacionPolicia>(grafo, heapCecilia.darElementos()[i]);

			for(int j=i+1; j < M; j++)
			{
				Iterator<Arco<Integer>> ruta = DJ.pathTo(heapCecilia.darElementos()[j]);

				if(ruta != null)
				{
					while(ruta.hasNext())
					{
						Arco<Integer> actual = ruta.next();

						Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> inicioActual = grafo.getVertex(actual.darInicio());
						Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> finalActual = grafo.getVertex(actual.darFin());

						if(respuesta.getVertex(actual.darInicio())== null)
							respuesta.addVertex(actual.darInicio(), inicioActual.darValor());

						if(respuesta.getVertex(actual.darFin())==null)
							respuesta.addVertex(actual.darFin(), finalActual.darValor());

						if(respuesta.getVertex(actual.darFin())!=null && respuesta.getVertex(actual.darInicio())!= null)
						respuesta.addEdge(actual.darInicio(), actual.darFin(), actual.darCostoDistancia());

					}	

				}
			}
		}
		
		return MST(respuesta);

	}


	public GrafoNoDirigido<Integer,LatitudYLongitud, Comparendo, EstacionPolicia> MST(GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> graf)
	{
		GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> respuesta = new GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>();
		
		Kruskal mst = new Kruskal(graf);
		Iterable<Arco<Integer>> arcos = mst.edges();
		
		int costoTotal =0;
		
		if(arcos != null)
		{
			Iterator<Arco<Integer>> iter = arcos.iterator();
			while(iter.hasNext())
			{
				Arco<Integer> actual = iter.next();
				
				Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> inicioActual = grafo.getVertex(actual.darInicio());
				Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> finalActual = grafo.getVertex(actual.darFin());
				costoTotal += actual.darCostoDistancia();
				
				if(respuesta.getVertex(actual.darInicio())== null)
					respuesta.addVertex(actual.darInicio(), inicioActual.darValor());

				if(respuesta.getVertex(actual.darFin())==null)
					respuesta.addVertex(actual.darFin(), finalActual.darValor());

				if(respuesta.getVertex(actual.darFin())!=null && respuesta.getVertex(actual.darInicio())!= null)
				respuesta.addEdge(actual.darInicio(), actual.darFin(), actual.darCostoDistancia());
			}
		}
		
		System.out.println("Dentro del MST, se visitaron los siguientes vertices:");
		
		for(int i=0; i < respuesta.V(); i++)
		{
			Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> act = respuesta.darVertices().get(i);
			if(act != null)
			{
				System.out.println("" + (i+1)+ ". ID:" + act.darID());
			}
			
		}
		
		System.out.println("Los arcos que se visitaron fueron: ");
		Iterator<Arco<Integer>> iter2= respuesta.edges().iterator();
		int j =1;
		
		while(iter2.hasNext())
		{
			Arco<Integer> arc = iter2.next();
			System.out.println(""+ j + ". Inicio: " + arc.darInicio() + "- Fin:" + arc.darFin());
			j++;	
		}
		
		System.out.println("El número total de vertices fue:" + respuesta.V());
		System.out.println("El número total de arcos fue:" + respuesta.E());
		System.out.println("La distancia total fue: "+  costoTotal);
		System.out.println("Por lo tanto, el costo total es de:" + costoTotal*10000 + "USD");
		return respuesta;	
	}

	public void crearHeapVerticesComparendos()
	{

		int i = 1; 
		while( i < grafo.V())
		{
			Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> actual = grafo.getVertex(i);
			heapCecilia.agregar(actual);
			i++;

		}


	}

	public void requerimiento1C()
	{

	}

	public void requerimiento2C()
	{

	}
}

