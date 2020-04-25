package model.data_structures;

import java.util.Comparator;

import model.logic.Comparendo;

public class ComparadorDistancias implements Comparator<Comparendo>
{
	// Constantes

	private static final int EARTH_RADIUS = 6371;

	private static final double LATITUD_POLICIA = 4.647586;

	private static final double LONGITUD_POLICIA = 74.078122;


	//Haversin

	public static double distance(double startLat, double startLong,
			double endLat, double endLong) {

		double dLat  = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat   = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c; // <-- d
	}

	public static double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}

	@Override
	public int compare(Comparendo c1, Comparendo c2) 
	{

		double dist1=distance(c1.darLatitud(),c1.darLongitud(),LATITUD_POLICIA, LONGITUD_POLICIA);
		double dist2=distance(c2.darLatitud(),c2.darLongitud(),LATITUD_POLICIA, LONGITUD_POLICIA);

		if(dist1 > dist2)
			return 1;

		else if(dist1 < dist2)
			return -1;

		else
			return 0;
	}


}
