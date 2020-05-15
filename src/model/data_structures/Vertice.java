package model.data_structures;

import model.data_structures.Arco;

public class Vertice<Key, Value> 
{
	private Key idVertex;

	private Value valorVertex;

	private boolean marca; 

	private Queue<Arco<Key>> adyacentes;

	public Vertice(Key id, Value valor)
	{
		idVertex  = id;
		valorVertex  = valor;
		adyacentes  = new Queue<Arco<Key>>();
	}
	
	public Key darID()
	{
		return idVertex;
	}

	public Value darValor()
	{
		return valorVertex;
		
	}
	public void agregarArco(Arco<Key> arco)
	{
		adyacentes.enqueue(arco);
	}

	public void cambiarInfoVertice(Value valor)
	{
		valorVertex = valor;
	}

	public Queue<Arco<Key>> darAdyacentes()
	{
		return adyacentes;
	}

	public boolean darMarked()
	{
		return marca;
	}

	public void marcar()
	{
		marca = true;
	}

	public void desmarcar()
	{
		marca = false;
	}

	
}
