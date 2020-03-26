package controller;

import java.util.Scanner;

import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller {

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
				
				int tamanoInicialLP = modelo.darTamanoLinearProbing();
				int tamanoInicialSC = modelo.darTamanoSeparateChaining();
				
				long start = System.currentTimeMillis();
				modelo.cargarDatos(); 
				long end = System.currentTimeMillis();
										
				view.printMessage("Datos de comparendos cargados.");
				view.printMessage("Numero total de comparendos " + modelo.darTamano() + "\n---------");		
				view.printMessage("Tiempo de carga (seg): " + (end-start)/1000.0);
				
				view.printMessage("Primer comparendo: " + modelo.darPrimeroCola() + "\n");
				view.printMessage("Ultimo comparendo: "+modelo.darUltimoCola()+"\n");
				
				
				view.printMessage("\n---------");
				view.printMessage("        Linear Probing         | Separate Chaining");
				view.printMessage("Numero de duplas: " + modelo.darTotalDuplasLP() + "    |" + modelo.darTotalDuplasSC() );
				view.printMessage("Tamano inicial: " + tamanoInicialLP + "   |" + tamanoInicialSC);
				view.printMessage("Tamano final: " + modelo.darTamanoLinearProbing() + "   |" + modelo.darTamanoSeparateChaining());
				view.printMessage("Factor de recarga: " + (double) modelo.darTotalDuplasLP()/modelo.darTamanoLinearProbing() + "   |" + (double) modelo.darTotalDuplasSC()/modelo.darTamanoSeparateChaining());
				view.printMessage("Rehashes: " + modelo.darTotalRehashesLP() + "   |" + modelo.darTotalRehashesSC());
				break;				
		

				case 2:
					
				view.printMessage("Ingrese la fecha que desea buscar (YYYY/mm/DD)");
				String fecha = lector.next();
				
				view.printMessage("Ingrese la clase de vehiculo que desea buscar (en mayusculas):");
				String clase = lector.next();
				
				view.printMessage("Ingrese la infraccion que desea buscar (en mayusculas):");
				String infraccion = lector.next();
						
				Comparable[] buscados = modelo.busquedaLP(fecha, clase, infraccion);
				
				for(int i = 0; i< buscados.length ;i++)
				{
					Comparendo c = (Comparendo) buscados[i];
					view.printMessage(c.datosCluster());
				}
					break;

				case 3:
					
					view.printMessage("Ingrese la fecha que desea buscar (YYYY/mm/DD)");
					String fecha2 = lector.next();
					
					view.printMessage("Ingrese la clase de vehiculo que desea buscar (en mayusculas):");
					String clase2 = lector.next();
					
					view.printMessage("Ingrese la infraccion que desea buscar (en mayusculas):");
					String infraccion2 = lector.next();
							
					Comparable[] buscados2 = modelo.busquedaLP(fecha2, clase2, infraccion2);
					
					for(int i = 0; i< buscados2.length ;i++)
					{
						Comparendo c = (Comparendo) buscados2[i];
						view.printMessage(c.datosCluster());
					}
					break;

				case 4:
					
					view.printMessage(modelo.pruebaDesempeño());
					
					break;
					
				case 5:
		
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
