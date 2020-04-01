package model.logic;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


	public static String PATH = "./data/Comparendos_DEI_2018_Bogota_D.C_small.geojson";
	/**
	 * Atributos del modelo del mundo
	 */

	private Queue<Comparendo> datos;
	
	

	private static Comparable[] aux;


	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */

	public Modelo()
	{
		datos = new Queue<Comparendo>();
		
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
				
				datos.enqueue(c);
				


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

	
	
	






	// ORDENAMIENTOS

	//Merge

	public static void sort(Comparable[] a) {
		aux = new Comparable[a.length];
		sort(a, 0, a.length - 1); 
	}

	private static void sort(Comparable[] a, int lo, int hi) {
		if (hi <= lo) return; 
		int mid = lo + (hi - lo)/2;
		sort(a, lo, mid);

		sort(a, mid+1, hi);

		merge(a, lo, mid, hi);
	}

	public static void merge(Comparable[] a, int lo, int mid, int hi)
	{

		int i = lo, j = mid+1;
		for (int k = lo; k <= hi; k++)

			aux[k] = a[k];
		for (int k = lo; k <= hi; k++)

			if (i > mid) a[k] = aux[j++];
			else if (j > hi ) a[k] = aux[i++];
			else if (less(aux[j], aux[i])) a[k] = aux[j++];
			else a[k] = aux[i++];
	}

	public static  boolean less(Comparable a, Comparable b)
	{
		return a.compareTo(b)<0;
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
