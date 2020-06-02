package model.data_structures;

import java.util.Comparator;

public class ComparadorDistancias<Key extends Comparable<Key>> implements Comparator<Arco<Key>> {

	@Override
	public int compare(Arco<Key> arco1, Arco<Key> arco2) 
	{
		double distancia1 = arco1.darCostoDistancia();
		double distancia2 = arco2.darCostoDistancia();
		
		if(distancia1 < distancia2)
			return 1;
		
		else if(distancia1 > distancia2)
			return -1;
		
		else
		return 0;
	}

}
