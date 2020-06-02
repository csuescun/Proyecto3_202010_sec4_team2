package model.data_structures;

import model.data_structures.Arco;

public class Vertice<Key extends Comparable<Key>, Value, Adicional1, Adicional2> 
{
	private Key idVertex;

	private Value valorVertex;

	private boolean marca; 

	private Queue<Arco<Key>> adyacentes;
	
	private Queue<Adicional1> adicion1;
	
	private Queue<Adicional2> adicion2;

	public Vertice(Key id, Value valor)
	{
		idVertex  = id;
		valorVertex  = valor;
		adyacentes  = new Queue<Arco<Key>>();
		adicion1 = new Queue<Adicional1>();
		adicion2 = new Queue<Adicional2>();
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

	public Queue<Adicional1> darAdicional1()
	{
		return adicion1;
	}
	
	public Queue<Adicional2> darAdicional2()
	{
		return adicion2;
		
	}
	public void desmarcar()
	{
		marca = false;
	}
	
	public void agregarA1(Adicional1 v)
	{
		adicion1.enqueue(v);
	}
	
	public void agregarA2(Adicional2 v)
	{
		adicion2.enqueue(v);
	}

	
	
}
