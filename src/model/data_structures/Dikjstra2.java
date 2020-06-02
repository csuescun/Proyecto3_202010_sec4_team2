package model.data_structures;

import java.util.Iterator;
import java.util.Stack;

public class Dikjstra2<Value, Adicional1, Adicional2> 
{

	private GrafoNoDirigido<Integer, Value, Adicional1, Adicional2> grafo;

	private Arco<Integer>[] edgeTo;

	private double[] distTo;

	private IndexMinPQ<Double> pq;

	public Dikjstra2(GrafoNoDirigido<Integer, Value, Adicional1, Adicional2> pGrafo, Vertice<Integer, Value, Adicional1, Adicional2> s)
	{
		grafo = pGrafo;
		distTo = new double[grafo.V()];
		edgeTo = (Arco<Integer>[]) new Arco[grafo.V()];

		SeparateChainingHashST<Integer, Vertice<Integer, Value, Adicional1, Adicional2>> vertices = grafo.darVertices();
		validateVertex(s);

		for (int v = 0; v < grafo.V(); v++)
		{
			distTo[v] = Double.POSITIVE_INFINITY;
		}

		distTo[(int)s.darID()] = 0.0;

		pq = new IndexMinPQ<Double>(grafo.V());
		pq.insert((int)s.darID(), distTo[(int)s.darID()]);

		while(!pq.isEmpty())
		{
			int v = pq.delMin();
			Vertice<Integer, Value, Adicional1, Adicional2> vertexActual = vertices.get(v);
			Iterator<Arco<Integer>> ady = vertexActual.darAdyacentes().iterator();
			
			while(ady.hasNext())
			{
				Arco<Integer> arcoActual = ady.next();
				relax(arcoActual);
			}

		}

	}


	private void relax(Arco<Integer> arco)
	{
		int v = (int) arco.darInicio();
		int w = (int) arco.darFin();

		if (distTo[w] > distTo[v] + arco.darCostoDistancia()) {
			distTo[w] = distTo[v] + arco.darCostoDistancia();
			edgeTo[w] = arco;
			
			if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
			else                pq.insert(w, distTo[w]);
		}
	}


	private void validateVertex(Vertice<Integer, Value, Adicional1, Adicional2> vertex) 
	{
		int V = distTo.length;
		int v=(int) vertex.darID();

		if (v < 0 || v >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}

	
	public double distTo(Vertice<Integer, Value, Adicional1, Adicional2>  vertex) {
		validateVertex(vertex);
		return distTo[(int) vertex.darID()];
	}


	public boolean hasPathTo(Vertice<Integer, Value, Adicional1, Adicional2>  vertex) 
	{
		validateVertex(vertex);
		return distTo[(int) vertex.darID()] < Double.POSITIVE_INFINITY;
	}


	public Iterator<Arco<Integer>> pathTo(Vertice<Integer, Value, Adicional1, Adicional2> vertex ) 
	{
		validateVertex(vertex);
		if (!hasPathTo(vertex)) return null;
		Queue<Arco<Integer>> path = new Queue<Arco<Integer>>();
		
		for (Arco<Integer> e = edgeTo[(int)vertex.darID()]; e != null; e = edgeTo[(int)e.darInicio()]) 
		{
			path.enqueue(e);
		}
		return path.iterator();
	}

}
