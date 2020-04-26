package controller;

import model.data_structures.Queue;

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
				view.printMessage("Numero total de comparendos " + modelo.darTamano() + "\n---------");		
				view.printMessage("Tiempo de carga (seg): " + (end-start)/1000.0 + "\n---------");
				view.printMessage("El comparendo con mayor OBJECTID es:");
				view.printMessage(modelo.darMayorComparendo().datosCluster() + "\n--------");


				break;				


			case 2:
			
				view.printMessage("Digite la cantidad de comparendos que quiere buscar");
				int num = Integer.parseInt(lector.next());
				
				Comparable [] graves = modelo.darMComparendosMasGraves(num);
				for(int i = 0;i<graves.length;i++)
				{
					Comparendo actual = (Comparendo)graves[i];
					view.printMessage(actual.datosCluster3());
				}
				
				break;

			case 3:
				view.printMessage("Ingrese un mes del año en forma numerica");
				int mes = Integer.parseInt(lector.next());
				view.printMessage("Ingrese un dia de la semana utilizando la primera letra");
				String dia = lector.next();
				Comparable[] busc = modelo.buscarComparendosMesDia(mes, dia);
				for(int i = 0;i<busc.length;i++)
				{
					Comparendo actual = (Comparendo)busc[i];
					view.printMessage(actual.datosCluster3());
				}
				
				break;

			case 4:
				try{
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				view.printMessage("Escriba una fecha");
				String a = lector.next();
				Date f1 = formatter.parse(a);
				view.printMessage("Escriba otra fecha");
				String b = lector.next();
				Date f2 = formatter.parse(b);
				view.printMessage("Ingrese una localidad");
				String local = lector.next();
						
				modelo.buscarRangoFHLocalidad(f1, f2, local);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

				break;

			case 5:

				view.printMessage("Indique el numero M de comparendos que desea buscar" );
				int M = lector.nextInt();

				Comparable[] comparendosCercanos =  modelo.darMComparendosMasCercanos(M);

				for(int i = 0; i < comparendosCercanos.length; i++)
				{
					Comparendo actual = (Comparendo) comparendosCercanos[i] ;
					view.printMessage(actual.datosCluster3());
				}

				break;

			case 6:

				view.printMessage("Indique el medio de detecciÃ³n que desea buscar:");
				String medio = lector.next();

				view.printMessage("Indique la clase de vehiculo que desea buscar:");
				String clase = lector.next();

				view.printMessage("Indique el tipo de servicio que desea buscar:");
				String tipo = lector.next();

				view.printMessage("Indique la localidad que desea buscar:");
				String localidad = lector.next();

				Comparable[] buscados = modelo.buscarComparendosCaracteristicas(medio, clase, tipo, localidad);

				int maximo = Math.min(N_MAX, buscados.length);

				for(int i =0; i<maximo; i++)
				{
					Comparendo actual = (Comparendo) buscados[i] ;
					view.printMessage(actual.datosCluster3());
				}

				break;

			case 7:

				view.printMessage("Ingrese el valor inferior del rango de latitudes que desea buscar:");
				double lat_min = lector.nextDouble();

				view.printMessage("Ingrese el valor superior del rango de latitudes que desea buscar:");
				double lat_max = lector.nextDouble();

				view.printMessage("Ingrese el tipo de vehiculo que desea buscar");
				String tipov= lector.next();

				Comparable[] losBuscados = modelo.copiarArreglo(modelo.buscarRangosLatitudTipo(lat_min, lat_max, tipov));

				int maxi = Math.min(N_MAX, losBuscados.length);

				for(int i =0; i<maxi; i++)
				{
					Comparendo actual = (Comparendo) losBuscados[i] ;
					view.printMessage(actual.datosCluster3());
				}

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
