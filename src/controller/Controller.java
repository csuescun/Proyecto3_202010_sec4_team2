package controller;

import model.data_structures.Queue;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller {

	private int N_MAX = 20;

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
					modelo.cargarDatos();
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


				break;

			case 7:


				break;

			case 8:


				break;

			case 9: 



				break;

			case 10:

				break;

			case 11:

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
