package model.data_structures;

public class SeparateChainingHash<Key,Value> {
	
	private int n;
	private int m;
	private ST<Key, Value>[] st;
	
	public SeparateChainingHash(int tamano) {
        
		m = tamano;
        
		st = (ST<Key, Value>[]) new ST[m];
        
		for (int i = 0; i < m; i++)
            st[i] = new ST<Key, Value>();
    } 
	
	private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    } 
	
	private void resize(int chains) {
        SeparateChainingHash<Key, Value> temp = new SeparateChainingHash<Key, Value>(chains);
        for (int i = 0; i < m; i++) {
            for (Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m  = temp.m;
        this.n  = temp.n;
        this.st = temp.st;
    }
	
	public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        int i = hash(key);
        return st[i].get(key);
    } 
	
	 public void put(Key key, Value val) {
	        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
	        if (val == null) {
	            delete(key);
	            return;
	        }

	        // double table size if average length of list >= 10
	        if (n >= 10*m) resize(2*m);

	        int i = hash(key);
	        if (!st[i].contains(key)) n++;
	        st[i].put(key, val);
	    } 
	 
	 public void delete(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to delete() is null");

	        int i = hash(key);
	        if (st[i].contains(key)) n--;
	        st[i].delete(key);

	        // halve table size if average length of list <= 2
	        if ( n <= 2*m) resize(m/2);
	    } 
	 
	 

	

}
