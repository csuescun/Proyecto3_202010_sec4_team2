package model.data_structures;

import java.util.Comparator;

import model.logic.Comparendo;

public class Fechas implements Comparator<Comparendo> {

	@Override
	public int compare(Comparendo c1, Comparendo c2) {
		// TODO Auto-generated method stub
		return c2.darFecha().compareTo(c1.darFecha());
	}
}
