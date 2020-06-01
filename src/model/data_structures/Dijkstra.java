package model.data_structures;

import java.util.Stack;

public class Dijkstra <Key extends Comparable<Key>> {

	private GrafoNoDirigido grafo;
	private Arco[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq;
	
	public Dijkstra (GrafoNoDirigido G, int s)
	{
		grafo = G;
		edgeTo = new Arco[G.V()];
		distTo = new double[G.V()];
		
		 validateVertex(s);

        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        
        pq = new IndexMinPQ<Double>(G.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            
            for (Object e : G.adj(v)){
            	Arco x = (Arco) e;
                relax(x);
                }
        }
        
	}

	private void relax(Arco e) {
		int v = (int)grafo.getVertex((Key)e.darInicio()).darID();
		int w = (int)grafo.getVertex((Key)e.darFin()).darID();
		
        if (distTo[w] > distTo[v] + e.darCostoDistancia()) {
            distTo[w] = distTo[v] + e.darCostoDistancia();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }
	
	
	public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

	
	 public boolean hasPathTo(int v) {
	        validateVertex(v);
	        return distTo[v] < Double.POSITIVE_INFINITY;
	    }
	
	 public Iterable<Arco> pathTo(int v) {
	        validateVertex(v);
	        if (!hasPathTo(v)) return null;
	        Stack<Arco> path = new Stack<Arco>();
	        for (Arco e = edgeTo[v]; e != null; e = edgeTo[(int)grafo.getVertex((Key)e.darInicio()).darID()]) {
	            path.push(e);
	        }
	        return path;
	    }
	 
	 
	 
	 private boolean check(GrafoNoDirigido G, int s) {

	        // check that edge weights are nonnegative
	        for (Object x : G.edges()) {
	        	
	        	Arco e = (Arco)x;
	            if (e.darCostoDistancia() < 0) {
	                System.err.println("negative edge weight detected");
	                return false;
	            }
	        }

	        // check that distTo[v] and edgeTo[v] are consistent
	        if (distTo[s] != 0.0 || edgeTo[s] != null) {
	            System.err.println("distTo[s] and edgeTo[s] inconsistent");
	            return false;
	        }
	        for (int v = 0; v < G.V(); v++) {
	            if (v == s) continue;
	            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
	                System.err.println("distTo[] and edgeTo[] inconsistent");
	                return false;
	            }
	        }

	        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
	        for (int v = 0; v < G.V(); v++) {
	            for (Object x : G.adj(v)) {
	            	
	            	Arco e = (Arco) x;
	                int w = (int)grafo.getVertex((Key)e.darFin()).darID();
	                if (distTo[v] + e.darCostoDistancia() < distTo[w]) {
	                    System.err.println("edge " + e + " not relaxed");
	                    return false;
	                }
	            }
	        }

	        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
	        for (int w = 0; w < G.V(); w++) {
	            if (edgeTo[w] == null) continue;
	            Arco e = edgeTo[w];
	            int v = (int)grafo.getVertex((Key)e.darInicio()).darID();
	            if (w != (int)grafo.getVertex((Key)e.darFin()).darID()) return false;
	            if (distTo[v] + e.darCostoDistancia() != distTo[w]) {
	                System.err.println("edge " + e + " on shortest path not tight");
	                return false;
	            }
	        }
	        return true;
	    }

	    // throw an IllegalArgumentException unless {@code 0 <= v < V}
	    private void validateVertex(int v) {
	        int V = distTo.length;
	        if (v < 0 || v >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }
	
}
