package model.data_structures;

public class Arco<Key> 
{
	private Key idVertexInicio;

	private Key idVertexFin;

	private double costoArco;


	public Arco(Key idI, Key idF, double costo)
	{
		idVertexInicio = idI;
		idVertexFin = idF;
		costoArco = costo;
	}


	public void cambiarCosto(double pCosto)
	{
		costoArco  = pCosto;
	}

	public Key darInicio()
	{
		return idVertexInicio;
	}

	public Key darFin()
	{
		return idVertexFin;
	}

	public double darCosto()
	{
		return costoArco;
	}


}
