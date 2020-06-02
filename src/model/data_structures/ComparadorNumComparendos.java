package model.data_structures;

import java.util.Comparator;

public class ComparadorNumComparendos<Key extends Comparable<Key>, Value, Adicional1, Adicional2> implements Comparator<Vertice<Key, Value, Adicional1, Adicional2>>
{

	@Override
	public int compare(Vertice<Key, Value, Adicional1, Adicional2> vertice1, Vertice<Key, Value, Adicional1, Adicional2> vertice2) 
	{
		int tam1 = vertice1.darAdicional1().darTamano();
		int tam2 = vertice2.darAdicional1().darTamano();

		if(tam1 > tam2)
			return 1;


		else if(tam2 < tam1)
			return -1;


		else
			return  0;

	}


}
