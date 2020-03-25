package model.data_structures;

import java.util.Iterator;

public class LinearProbingHash<Key,Value>  {

	private int m;//tamano de la tabla
	private int n;//numero de elementos
	private Key[] keys;
	private Queue<Value>[] values;


	public LinearProbingHash(int tamano)
	{
		m = tamano;
		n = 0;
		
		keys = (Key[]) new Object[m];
		values = (Queue<Value>[]) new Object[m];
	}

	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % m;
	}


	void putInSet(Key k, Value v) 
	{
		if(k == null)
		{
			throw new IllegalArgumentException("La llave ingresada es null");
		}

		if(v == null)
		{
			throw new IllegalArgumentException("El valor ingresado es null");
		}

		if(n/m >= .75)
		{
			resize(2*m);
		}

		int i;
		for(i = hash(k); keys[i] != null; i = (i+1)%m)
		{
			if(keys[i].equals(k))
			{
				values[i].enqueue(v);
			}
		}

		keys[i] = k;
		values[i] = new Queue<Value>();
		values[i].enqueue(v);
		n++;

	}
	public Queue<Value> getSet(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
		for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
			if (keys[i].equals(key))
				return values[i];
		return null;
	}

	public boolean contains(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");
		return getSet(key) != null;
	}

	public Iterator<Value> deleteSet(Key key) {

		if (key == null) throw new IllegalArgumentException("argument to delete() is null");

		if (!contains(key)) return null;


		int i = hash(key);
		Queue<Value> conjunto = values[i];
		
		while (!key.equals(keys[i])) 
		{
			i = (i + 1) % m;
		}


		keys[i] = null;
		values[i] = null;


		i = (i + 1) % m;
		
		while (keys[i] != null) 
		{

			Key   keyToRehash = keys[i];
			Queue<Value> valToRehash = values[i];
			keys[i] = null;
			values[i] = null;
			n--;
			
			Node<Value> actual = valToRehash.darPrimerNodo();
			
			while(actual.darSiguiente() != null)
			{
				Value eliminado = (Value) actual.darElemento();
				putInSet(keyToRehash, eliminado);
				
				actual = actual.darSiguiente();
			}
			
			i = (i + 1) % m;
		}

		n--;


		if (n > 0 && n <= m/8) resize(m/2);

		return conjunto.iterator();
	}

	private void resize(int capacity) {
		LinearProbingHash<Key, Value> temp = new LinearProbingHash<Key, Value>(capacity);
		
		for (int i = 0; i < m; i++) 
		{
			if (keys[i] != null) 
			{
				Node<Value> actual = values[i].darPrimerNodo();
				
				while(actual != null)
				{
					Value v = actual.darElemento();
					temp.putInSet(keys[i], v);
					actual = actual.darSiguiente();
				}
				
			}
		}
		keys = temp.keys;
		values = temp.values;
		
		m    = temp.m;
	}





}
