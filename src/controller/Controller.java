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


				long start = System.currentTimeMillis();
				modelo.cargarDatos(); 
				long end = System.currentTimeMillis();

				view.printMessage("Datos de comparendos cargados.");
				view.printMessage("Numero total de comparendos " + modelo.darTamanoComparendos() + "\n---------");		
				view.printMessage("Tiempo de carga (seg): " + (end-start)/1000.0 + "\n---------");
				view.printMessage("El comparendo con mayor OBJECTID es:");
				view.printMessage(modelo.darMayorComparendo().datosCluster() + "\n--------");


				break;				


			case 2:

				

				break;

			case 3:
				

				break;

			case 4:
				
				break;

			case 5:

				
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
