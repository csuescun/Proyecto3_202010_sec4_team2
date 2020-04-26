package model.data_structures;

import java.util.Comparator;

import model.logic.Comparendo;

public class Gravedad implements Comparator<Comparendo>{
	
	

	@Override
	public int compare(Comparendo c1, Comparendo c2) {
		
		// TODO Auto-generated method stub
		
		int a = c1.darTipoServicio().equals("Público")?3:c1.equals("Oficial")?2:1;
		
		int b = c2.darTipoServicio().equals("Público")?3:c1.equals("Oficial")?2:1;
		
		return (a-b)>0?1:(a-b)<0?-1:c1.darInfraccion().compareTo(c2.darInfraccion());
		
	}

}
