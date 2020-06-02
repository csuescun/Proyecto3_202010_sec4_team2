package model.data_structures;

public class PrimMST <Key extends Comparable<Key>> {
    private static final double FLOATING_POINT_EPSILON = 1E-12;

    private Arco[] edgeTo;        // edgeTo[v] = shortest edge from tree vertex to non-tree vertex
    private double[] distTo;      // distTo[v] = weight of shortest such edge
    private boolean[] marked;     // marked[v] = true if v on tree, false otherwise
    private IndexMinPQ<Double> pq;

    /**
     * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
     * @param G the edge-weighted graph
     */
    public PrimMST(GrafoNoDirigido G) {
        edgeTo = new Arco[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;

        for (int v = 0; v < G.V(); v++)      // run from each vertex to find
            if (!marked[v]) prim(G, v);      // minimum spanning forest

    
    }

    // run Prim's algorithm in graph G, starting from vertex s
    private void prim(GrafoNoDirigido G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            scan(G, v);
        }
    }

    // scan vertex v
    private void scan(GrafoNoDirigido G, int v) {
        marked[v] = true;
        for (Object x : G.adj(v)) {
        	Arco e = (Arco)x;
            int w = (int)(int)G.getVertex((Key)e.darFin()).darID();
            if (marked[w]) continue;         // v-w is obsolete edge
            if (e.darCostoDistancia() < distTo[w]) {
                distTo[w] = e.darCostoDistancia();
                edgeTo[w] = e;
                if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
                else                pq.insert(w, distTo[w]);
            }
        }
    }

    /**
     * Returns the edges in a minimum spanning tree (or forest).
     * @return the edges in a minimum spanning tree (or forest) as
     *    an iterable of edges
     */
    public Iterable<Arco> edges() {
        Queue<Arco> mst = new Queue<Arco>();
        for (int v = 0; v < edgeTo.length; v++) {
            Arco e = edgeTo[v];
            if (e != null) {
                mst.enqueue(e);
            }
        }
        return mst;
    }

    /**
     * Returns the sum of the edge weights in a minimum spanning tree (or forest).
     * @return the sum of the edge weights in a minimum spanning tree (or forest)
     */
    public double weight() {
        double weight = 0.0;
        for (Object x : edges()){
        	Arco e = (Arco)x;
            weight += e.darCostoDistancia();
            }
        return weight;
    }


   


