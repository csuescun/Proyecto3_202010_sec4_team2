package model.data_structures;

public class Kruskal<Key extends Comparable<Key>, Value, Adicional1, Adicional2> {
	
	 private static final double FLOATING_POINT_EPSILON = 1E-12;

	    private double weight;                        // weight of MST
	    private Queue<Arco<Key>> mst = new Queue<Arco<Key>>();  // edges in MST

	    /**
	     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
	     * @param G the edge-weighted graph
	     */
	    public Kruskal(GrafoNoDirigido<Key, Value, Adicional1, Adicional2> grafo) {
	       
	    	MaxHeapCP<Arco<Key>> heapCeci = new MaxHeapCP<Arco<Key>>(new ComparadorDistancias()); 
	       
	    	for (Arco<Key> e : grafo.edges()) 
	    	{
	            heapCeci.agregar(e);;
	        }

	        // run greedy algorithm
	        UF uf = new UF(grafo.V());
	        
	        while (!heapCeci.esVacia() && mst.darTamano() < grafo.V() - 1) {
	            Arco<Key> e = heapCeci.sacarMax();
	            int v = (int) e.darInicio();
	            int w = (int) e.darFin();
	            
	            if (uf.find(v) != uf.find(w)) { // v-w does not create a cycle
	                uf.union(v, w);  // merge v and w components
	                mst.enqueue(e);  // add edge e to mst
	                weight += e.darCostoDistancia();
	            }
	        }

	       
	    }

	    /**
	     * Returns the edges in a minimum spanning tree (or forest).
	     * @return the edges in a minimum spanning tree (or forest) as
	     *    an iterable of edges
	     */
	    public Iterable<Arco<Key>> edges() {
	        return mst;
	    }

	    /**
	     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
	     * @return the sum of the edge weights in a minimum spanning tree (or forest)
	     */
	    public double weight() {
	        return weight;
	    }
	   

}
