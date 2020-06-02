package view;

import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("\n---------");
			System.out.println("Carga" + "\n---------");
			System.out.println("1. Cargar datos");
			System.out.println("\n---------");
			System.out.println("Requerimientos iniciales" + "\n---------");
			System.out.println("2. Buscar vertice mas cercano dada una localizacion geografica");
			System.out.println("3. Adicionar la informacion de los comparendos a la malla vial");
			System.out.println("4. Adicionar los costos de distancia y comparendos al grafo");
			System.out.println("5. Adicionar la informacion de las estaciones de policia al grafo");
			System.out.println("\n---------");
			System.out.println("Requerimientos" + "\n---------");
			System.out.println("6. Obtener el camino de costo mínimo entre dos ubicaciones geográficas por distancia");
			System.out.println("7. Determinar la red de comunicaciones que soporte la instalación de cámaras de video en los M puntos donde se presentan los comparendos de mayor gravedad");
			System.out.println("8. Obtener el camino de costo mínimo entre dos ubicaciones geográficas por número de comparendos");
			System.out.println("9. Determinar la red de comunicaciones que soporte la instalación de cámaras de video en los M puntos donde se presenta el mayor número de comparendos en la ciudad.");
			System.out.println("10. Obtener los caminos más cortos para que los policías puedan atender los M comparendos más graves");
			System.out.println("11. Identificar las zonas de impacto de las estaciones de policía.");
			System.out.println("12. Exit");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}		
		
	
}
