package model.data_structures;

public class Arco<Key extends Comparable<Key>> implements Comparable<Arco<Key>>
{
	private Key idVertexInicio;

	private Key idVertexFin;

	private double costoDistancia;
	
	private int costoComparendos;


	public Arco(Key idI, Key idF, double costoDist)
	{
		idVertexInicio = idI;
		idVertexFin = idF;
		costoDistancia = costoDist;
	}


	public void cambiarCostoDistancia(double pCosto)
	{
		costoDistancia  = pCosto;
	}
	
	public void cambiarCostoComparendos(int totalComparendos)
	{
		costoComparendos = totalComparendos; 
	}

	public Key darInicio()
	{
		return idVertexInicio;
	}

	public Key darFin()
	{
		return idVertexFin;
	}

	public double darCostoDistancia()
	{
		return costoDistancia;
	}
	
	public int darCostoComparendos()
	{
		return costoComparendos;
	}


	@Override
	public int compareTo(Arco<Key> o) {
		// TODO Auto-generated method stub
		return 0;
	}


}
