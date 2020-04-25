package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.google.gson.*;
import com.google.gson.stream.*;

import model.data_structures.*;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {


	public static String PATH = "./data/Comparendos_DEI_2018_Bogotá_D.C.geojson";

	public static int Nmax = 20;
	/**
	 * Atributos del modelo del mundo
	 */

	//ESTRUCTURAS DE DATOS

	private Queue<Comparendo> datos;

	private MaxHeapCP<Comparendo> maxHeapCecilia;

	private SeparateChainingHash<String, Comparendo> hashSC_Cecilia;

	private RedBlackBST<Double, Comparendo> arbolRN_Cecilia;
	



	//ADICIONALES
	private static Comparable[] aux;

	private Comparendo mayorComparendo;


	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */

	public Modelo()
	{
		datos = new Queue<Comparendo>();
		maxHeapCecilia = new MaxHeapCP<Comparendo>(new ComparadorDistancias());
		hashSC_Cecilia = new SeparateChainingHash<String, Comparendo>(7);
		arbolRN_Cecilia = new RedBlackBST<Double, Comparendo>();
	}


	//Iniciales

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
				datos.enqueue(c);
				
				// Requerimientos B:
				maxHeapCecilia.agregar(c);
				
				String llaveCecilia = MEDIO_DETE+"_"+CLASE_VEHI+"_"+TIPO_SERVI+"_"+LOCALIDAD;
				hashSC_Cecilia.putInSet(llaveCecilia, c);
				
				arbolRN_Cecilia.put(latitud, c);

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

				datos.enqueue(c);


			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}


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


	public Comparendo[] copiarDatos()
	{
		Comparendo[] comparendos = new Comparendo[datos.darTamano()];
		int i = 0;
		for(Comparendo e : datos)
		{
			comparendos[i] = e;
			i++;
		}
		return comparendos;
	}


	public Queue<Comparendo> cargarMuestra(int N)
	{
		Queue<Comparendo> aRetornar = new Queue<Comparendo>();
		Comparendo[] copia = copiarDatos();
		shuffle(copia);

		for(int i = 1; i <= N; i++ )
		{
			aRetornar.enqueue(copia[i]);
		}

		return aRetornar;
	}


	public int darTamano()
	{
		return datos.darTamano();
	}


	public Comparendo darPrimeroCola()
	{
		return datos.darPrimerElemento();
	}

	public Comparendo darUltimoCola()
	{
		return datos.darUltimoElemento();
	}


	public Comparendo darMayorComparendo()
	{
		return mayorComparendo;
	}

	//REQUERIMIENTOS FUNCIONALES

	//A1
	public MaxHeapCP<Comparendo> darMComparendosMasGraves(int M)
	{
		return null;
	}

	//A2
	public Queue<Comparendo> buscarComparendosMesDia(int mes, String dia)
	{
		return null;
	}

	//A3
	public void buscarRangoFHLocalidad(String fechas, String localidad)
	{

	}

	//B1
	public Queue<Comparendo> darMComparendosMasCercanos(int M)
	{
		Queue<Comparendo> aRetornar = new Queue<Comparendo>();
		
		while(M > 0)
		{
			aRetornar.enqueue(maxHeapCecilia.sacarMax());
			M--;
		}
		
		return aRetornar;
	}

	//B2
	public Comparable[] buscarComparendosCaracteristicas(String medio_dete, String clase, String tipo, String localidad)
	{
		String llave = medio_dete + "_" + clase + "_" + tipo + "_" + localidad;
		
		Comparable[] respuesta  = copiarArreglo(hashSC_Cecilia.getSet(llave));
		shellSortPorFecha(respuesta);
		
		return respuesta;
		
	}

	//B3

	public Queue<Comparendo> buscarRangosLatitudTipo(double latitud1, double latitud2, String tipo)
	{
		Iterator<Comparendo> buscados = arbolRN_Cecilia.valuesInRange(latitud1, latitud2);
		Queue<Comparendo> aRetornar  = new Queue<Comparendo>();
		
		while(buscados.hasNext())
		{
			Comparendo agregado = buscados.next();
			
			if(agregado.darTipoServicio().equals(tipo))
			{
				aRetornar.enqueue(agregado);
			}
			
		}
		
		return aRetornar;
		
	}

	//C1
	public void tablaASCII(int dias)
	{

	}

	//C2
	public void tiemposDeEspera()
	{

	}

	//C3
	public void tiemposNuevoSistema()
	{

	}



	// ORDENAMIENTOS

	//Shell


	public static  boolean less(Comparable a, Comparable b)
	{
		return a.compareTo(b)<0;
	}


	public void shellSortPorFecha(Comparable[] a)
	{
		int N = a.length;
		int h = 1;
		while (h < N/3)
			h = 3*h + 1;
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
					exch(a, j, j-h);
			}
			h = h/3;
		}

	}

	public static void exch(Comparable[] a, int i, int j)
	{
		Comparable temporal = a[i];
		a[i] = a[j];
		a[j] = temporal;		
	}


	public void  shuffle(Comparendo[] total)
	{
		Random rnd = ThreadLocalRandom.current();
		for (int i = total.length - 1; i > 0; i--)
		{
			int index = rnd.nextInt(i + 1);
			// Simple swap
			Comparendo a = total[index];
			total[index] = total[i];
			total[i] = a;
		}
	}

}
