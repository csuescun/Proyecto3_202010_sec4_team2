package model.data_structures;

import java.util.Iterator;
import model.data_structures.Vertice;
import model.data_structures.Arco;

public class GrafoNoDirigido<Key extends Comparable<Key>, Value> 
{


	private int nVertices;

	private int nArcos;

	private SeparateChainingHashST<Key, Vertice<Key, Value>> vertices;
	
	private Queue<Arco<Key>> arcos;


	public GrafoNoDirigido()
	{
		nVertices = 0;
		nArcos = 0;

		vertices = new SeparateChainingHashST<Key, Vertice<Key, Value>>(1000);
		arcos = new Queue<Arco<Key>>();

	}

	public GrafoNoDirigido(int num)
	{
		nVertices = num;
		nArcos = 0;

		vertices = new SeparateChainingHashST<Key, Vertice<Key, Value>>(1000);


	}

	public SeparateChainingHashST<Key, Vertice<Key, Value>> darVertices()
	{
		return vertices;
	}
	
	
	public int V()
	{
		return nVertices;
	}

	public int E()
	{
		return nArcos;
	}


	public void addEdge(Key VI, Key VF, double cost)
	{
		if(getInfoVertex(VI) != null && getInfoVertex(VF) != null)
		{
			Vertice<Key, Value> inicio = vertices.get(VI);
			Vertice<Key, Value> fin = vertices.get(VF);


			Arco<Key> arco =new Arco<Key>(VI, VF, cost);

			inicio.agregarArco(arco);
			fin.agregarArco(arco);
			arcos.enqueue(arco);
			nArcos++;
		}
	}

	public Value getInfoVertex(Key id)
	{
		Vertice<Key,Value> buscado = vertices.get(id);

		if (buscado != null)
		{
			return buscado.darValor();
		}

		return null;

	}

	public Vertice<Key, Value> getVertex(Key id)
	{
		Vertice<Key,Value> buscado = vertices.get(id);

		if (buscado != null)
		{
			return buscado;
		}

		return null;
	}

	public void setInfoVertex(Key id, Value info)
	{
		Vertice<Key,Value> buscado = vertices.get(id);

		if (buscado != null)
		{
			buscado.cambiarInfoVertice(info);
		}
	}

	public double getCostArc(Key VI, Key VF)
	{
		Vertice<Key, Value> buscadoInicial =  vertices.get(VI);
		Vertice<Key, Value> buscadoFinal = vertices.get(VF);

		Iterator<Arco<Key>> ady = buscadoInicial.darAdyacentes().iterator();

		while(ady.hasNext())
		{
			Arco<Key> actual = ady.next();
			if(actual.darFin().compareTo(VF) == 0)
			{
				return actual.darCosto();
			}
		}

		return -1;

	}

	public void setCostArc(Key idVI,Key idVF, double cost)
	{
		Vertice<Key, Value> buscadoInicial =  vertices.get(idVI);
		Vertice<Key, Value> buscadoFinal = vertices.get(idVF);

		Iterator<Arco<Key>> ady = buscadoInicial.darAdyacentes().iterator();

		while(ady.hasNext())
		{
			Arco<Key> actual = ady.next();
			if(actual.darFin().compareTo(idVI) == 0)
			{
				actual.cambiarCosto(cost);
				break;
			}
		}

		ady = buscadoFinal.darAdyacentes().iterator();

		while(ady.hasNext())
		{
			Arco<Key> actual = ady.next();
			if(actual.darFin().compareTo(idVI) == 0)
			{
				actual.cambiarCosto(cost);
				break;
			}
		}

	}

	public void addVertex(Key idVertix, Value infoVertix)
	{
		Vertice<Key, Value> nuevoVertice = new Vertice<Key, Value>(idVertix, infoVertix);
		vertices.put(idVertix, nuevoVertice);
		nVertices++;
	}

	public Iterable<Key> adj(Key idVertix)
	{
		Vertice<Key, Value> buscado =  vertices.get(idVertix);

		Iterator<Arco<Key>> ady = buscado.darAdyacentes().iterator();
		Queue<Key> aRetornar = new Queue<Key>();

		while(ady.hasNext())
		{
			Arco<Key> actual = ady.next();
			aRetornar.enqueue(actual.darFin());
		}

		return aRetornar;

	}

	public void uncheck()
	{
		Iterator<Key> llaves = vertices.keys();

		while(llaves.hasNext())
		{
			Key llaveActual = llaves.next();
			vertices.get(llaveActual).desmarcar();
		}
	}

	public void dfs(Key s)
	{
		Vertice<Key,Value> v =  vertices.get(s);
		v.marcar();
		vertices.put(s, v);
		for(Key e: adj(s))
		{
			Vertice<Key,Value> actual = vertices.get(e);
			if(!actual.darMarked())
			{
				dfs(e);
			}
			
		}
	}

	public int cc()
	{
		uncheck();
		Iterator<Key> llaves = vertices.keys();
		int cc  = 0;

		while(llaves.hasNext())
		{
			Key actual = llaves.next();
			Vertice<Key,Value> v = vertices.get(actual);
			if(!v.darMarked()){
				dfs(actual);
				cc++;
			}
		}
		
		return cc;
	}

	public Iterable<Key> getCC(Key idVertix)
	{
		Queue<Key> resp  = new Queue<Key>();
		uncheck();
		dfs(idVertix);
		Iterator<Key> llaves = vertices.keys();
		while(llaves.hasNext())
		{
			Key actual = llaves.next();
			if(vertices.get(actual).darMarked())
			{
				resp.enqueue(actual);
			}
		}
		return resp;
	}
}
