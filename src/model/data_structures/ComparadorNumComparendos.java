package model.data_structures;

import java.util.Comparator;

import model.logic.Comparendo;
import model.logic.EstacionPolicia;
import model.logic.LatitudYLongitud;

public class ComparadorNumComparendos implements Comparator<Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia>>
{


	@Override
	public int compare(Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> o1,
			Vertice<Integer, LatitudYLongitud, Comparendo, EstacionPolicia> o2) {
		
		int tam1 = o1.darAdicional1().darTamano();
		int tam2 = o2.darAdicional1().darTamano();

		if(tam1 > tam2)
			return 1;


		else if(tam2 < tam1)
			return -1;


		else
			return  0;

	}


}
