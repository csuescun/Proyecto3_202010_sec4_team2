package controller;

import model.data_structures.Arco;
import model.data_structures.GrafoNoDirigido;
import model.data_structures.Queue;
import model.data_structures.Vertice;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.logic.Comparendo;
import model.logic.EstacionPolicia;
import model.logic.LatitudYLongitud;
import model.logic.MapaNuevo;
import model.logic.Modelo;
import view.View;

public class Controller {

	private int N_MAX = 20;

	public static double MIN_LONGITUD =  -74.094723 ;

	public static double MIN_LATITUD = 4.597714;

	public static double MAX_LONGITUD = -74.062707;

	public static double MAX_LATITUD = 4.621360;

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String dato = "";
		String respuesta = "";

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){

			case 1:

				modelo = new Modelo();

				try {
					long start = System.currentTimeMillis();
					modelo.cargarDatos();;
					modelo.cargarEstaciones();
					modelo.cargarVertices();
					modelo.cargarArcos();
					long end = System.currentTimeMillis();

					view.printMessage("Datos cargados.");
					view.printMessage("Tiempo de carga (seg): " + (end-start)/1000.0 + "\n---------");

					view.printMessage("Comparendos" + "\n---------");
					view.printMessage("Numero total de comparendos: " + modelo.darTamanoComparendos() + "\n---------");		
					view.printMessage("El comparendo con mayor OBJECTID es:");
					view.printMessage(modelo.darMayorComparendo().datosCluster() + "\n--------");

					view.printMessage("Estaciones cargadas" + "\n---------");
					view.printMessage("Numero total de estaciones: " + modelo.darTamanoEstaciones());
					view.printMessage("La estacion con el mayor OBJECTID es:");
					view.printMessage(modelo.darMayorEstacion().datosCluster()+ "\n--------" );

					view.printMessage("Vertices" + "\n---------");
					view.printMessage("Numero total de vertices: " + modelo.darNumeroVertices());
					view.printMessage("El vertice con el mayor OBJECTID es:");
					view.printMessage(modelo.darMayorVertice());


					view.printMessage("\n---------");
					view.printMessage("Arcos" + "\n---------");
					view.printMessage("Numero total de arcos: " + modelo.darNumeroArcos());
					view.printMessage("Los arcos del vertice con el mayor OBJECTID son:");
					view.printMessage(modelo.darArcosMayor());

				

				}	


				catch (Exception e)
				{
					view.printMessage("Hubo un error al cargar los archivos: " + e.getMessage());
					e.printStackTrace();
				}

				break;				


			case 2:

				view.printMessage("Digite la latitud de la posicion geografica deseada");
				double latitud = Double.parseDouble(lector.next());

				view.printMessage("Digite la longitud de la posicion geografica deseada");
				double longitud = Double.parseDouble(lector.next());

				int idBuscado = modelo.darVerticeMasCercano(latitud, longitud).darID();

				view.printMessage("El ID del vertice mas cercano es:" + idBuscado);

				break;

			case 3:

				modelo.adicionarComparendosAVertices();
				view.printMessage("Se ha adicionado cada comparendo al vertice mas cercano correspondiente");


				break;

			case 4:

				modelo.adicionarComparendosArcos();
				view.printMessage("Se ha cargado a cada arco su costo de distancia y su costo de comparendos");
				break;

			case 5:

				modelo.adicionarEstacionesDePolicia();
				view.printMessage("Se ha adicionado cada estacion de policia al vertice mas cercano correspondiente");

				break;

			case 6:
				view.printMessage("Ingrese la latitud del vertice inicial");
				double lat1 = Double.parseDouble(lector.next());
				view.printMessage("Ingrese la longitud del vertice inicial");
				double longit1 = Double.parseDouble(lector.next());
				view.printMessage("Ingrese la latitud del vertice final");
				double lat2 = Double.parseDouble(lector.next());
				view.printMessage("Ingrese la longitud del vertice final");
				double longit2 = Double.parseDouble(lector.next());

				Vertice v1 = modelo.darVerticeMasCercano(lat1, longit1);
				Vertice v2 = modelo.darVerticeMasCercano(lat2, longit2);

				int s = (int)v1.darID();
				int v = (int)v2.darID();

				Iterable<Arco> arcos =  modelo.dijkstra(s).pathTo(v);


				for(Arco e : arcos)
				{
					Vertice i = modelo.darGrafo().getVertex((Integer)(e.darInicio()));
					Comparendo actual = (Comparendo)i.darValor();
					view.printMessage(actual.datos2());	

				}
				view.printMessage(""+modelo.dijkstra(s).distTo(v));


				break;

			case 7:


				break;

			case 8:
				view.printMessage("Ingrese la latitud del vertice inicial");
				double latitudInicial = Double.parseDouble(lector.next());
				view.printMessage("Ingrese la longitud del vertice inicial");
				double longitudInicial = Double.parseDouble(lector.next());
				view.printMessage("Ingrese la latitud del vertice final");
				double latitudFinal = Double.parseDouble(lector.next());
				view.printMessage("Ingrese la longitud del vertice final");
				double longitudFinal = Double.parseDouble(lector.next());

				if(latitudInicial < MIN_LATITUD || latitudInicial > MAX_LATITUD || longitudInicial < MIN_LONGITUD || longitudInicial > MAX_LONGITUD)
				{
					view.printMessage("Hay un error con las coordenadas iniciales");
					break;
				}

				if(latitudFinal < MIN_LATITUD || latitudFinal > MAX_LATITUD || longitudFinal < MIN_LONGITUD || longitudFinal > MAX_LONGITUD)
				{
					view.printMessage("Hay un error con las coordenadas finales");
					break;
				}

				GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> nuevo = modelo.requerimiento1B(latitudInicial, longitudInicial, latitudFinal, longitudFinal);
				MapaNuevo nuevoMapa = new MapaNuevo(nuevo);
				nuevoMapa.iniciarFrame();

				break;

			case 9: 
				

				modelo.crearHeapVerticesComparendos();
				
				view.printMessage("Ingrese el numero M de lugares para la generacion de la red");
				int M = lector.nextInt();
				
				GrafoNoDirigido<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> nuevo1 = modelo.requerimiento2B(M);
				MapaNuevo nuevoMapa1 = new MapaNuevo(nuevo1);
				nuevoMapa1.iniciarFrame();


				break;

			case 10:

				break;

			case 11:
				break;


			case 12:

				view.printMessage("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;	

			default: 
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	
}
