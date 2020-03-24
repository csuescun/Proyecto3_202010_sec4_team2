package model.data_structures;

import java.util.Iterator;

public class LinearProbingHash<Key,Value> implements Iterable<Key> {
	
	private int m;//tamano de la tabla
	private int n;//numero de elementos
	private Key[] keys;
	private Value[]values;
	
	
	public LinearProbingHash(int tamano)
	{
		m = tamano;
		n = 0;
		keys = (Key[]) new Object[m];
		values = (Value[]) new Object[m];
	}
	
	 private int hash(Key key) {
	        return (key.hashCode() & 0x7fffffff) % m;
	    }
	
	void put(Key k,Value v)
	{
		 if (k == null) throw new IllegalArgumentException("");

	        if (v == null) {
	            delete(k);
	            return;
	        }
	        if (n >= m/2) resize(2*m);

	        int i;
	        for (i = hash(k); keys[i] != null; i = (i + 1) % m) {
	            if (keys[i].equals(k)) {
	                values[i] = v;
	                return;
	            }
	        }
	        keys[i] = k;
	        values[i] = v;
	        n++;
	}
	
	 public Value get(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to get() is null");
	        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
	            if (keys[i].equals(key))
	                return values[i];
	        return null;
	    }
	 
	 public boolean contains(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
	        return get(key) != null;
	    }
	 
	 public void delete(Key key) {
	        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
	        if (!contains(key)) return;

	      
	        int i = hash(key);
	        while (!key.equals(keys[i])) {
	            i = (i + 1) % m;
	        }

	        
	        keys[i] = null;
	        values[i] = null;

	      
	        i = (i + 1) % m;
	        while (keys[i] != null) {
	            
	            Key   keyToRehash = keys[i];
	            Value valToRehash = values[i];
	            keys[i] = null;
	            values[i] = null;
	            n--;
	            put(keyToRehash, valToRehash);
	            i = (i + 1) % m;
	        }

	        n--;

	       
	        if (n > 0 && n <= m/8) resize(m/2);

	    }

	 private void resize(int capacity) {
	        LinearProbingHash<Key, Value> temp = new LinearProbingHash<Key, Value>(capacity);
	        for (int i = 0; i < m; i++) {
	            if (keys[i] != null) {
	                temp.put(keys[i], values[i]);
	            }
	        }
	        keys = temp.keys;
	        values = temp.values;
	        m    = temp.m;
	    }
	 
	@Override
	public Iterator<Key> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	 
	 protected class KeyIterator implements Iterator<Key>
	 {
		 Key actual;
		 
		 public KeyIterator()
		 {
			 actual = keys[0];
		 }

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Key next() {
			// TODO Auto-generated method stub
			return null;
		}
		 
	 }
	
	

}
