package model.data_structures;

import java.util.Comparator;

import model.logic.Comparendo;

public class Costoso implements Comparator<Comparendo> {

	@Override
	public int compare(Comparendo c1, Comparendo c2) {
		// TODO Auto-generated method stub
		
		int a = c1.darDesInfraccion().contains("INMOVILIZADO")?3:c1.darDesInfraccion().contains("LICENCIA DE CONDUCCIÓN")?2:1;
		int b = c2.darDesInfraccion().contains("INMOVILIZADO")?3:c2.darDesInfraccion().contains("LICENCIA DE CONDUCCIÓN")?2:1;
		
		return (a-b)>0?1:(a-b)<0?-1:0;
	}

	
}
