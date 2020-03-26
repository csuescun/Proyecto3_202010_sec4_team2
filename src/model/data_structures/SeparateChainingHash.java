package model.data_structures;

import java.util.Iterator;


public class SeparateChainingHash<Key,Value> {

	private int n;
	private int m;
	private NodoHash[] st;

	public SeparateChainingHash(int tamano) {

		m = tamano;
		st = new SeparateChainingHash.NodoHash[tamano];
	} 

	private class NodoHash 
	{
		private Key key; 
		private Queue<Value> values;
		private NodoHash siguiente; 


		public NodoHash(Key pKey, Queue<Value> pValues, NodoHash pSiguiente)
		{
			key = pKey; 
			values = pValues;
			siguiente = pSiguiente;
		}
		
	}

	private boolean contains(Key key)
	{
		return getSet(key) != null;
	}


	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % m;
	} 

	private void resize(int chains) 
	{
		SeparateChainingHash<Key, Value> temp = new SeparateChainingHash<Key, Value>(chains);
		
		for (int i = 0; i < m; i++) 
		{
			for(int j = 0; j < st.length; j++)
			{
				if(st[j] != null)
				{
					NodoHash actual = st[j];
					
					while(actual != null)
					{
						temp.put(actual.key, actual.values);
						actual = actual.siguiente;
					}
				}
			}
		}
		this.m  = temp.m;
		this.n  = temp.n;
		this.st = temp.st;
	}

	public Iterator<Value> getSet(Key key) {

		if (key == null) throw new IllegalArgumentException("La llave ingresada es null");

		int i = hash(key);
		NodoHash actual = st[i];
		Iterator<Value> aDevolver = null; 

		while(actual != null)
		{
			if(key.equals(actual.key))
			{
				aDevolver = actual.values.iterator();
			}
		}

		return aDevolver;

	} 

	public void putInSet(Key key, Value val) 
	{
		if (key == null) throw new IllegalArgumentException("La llave ingresada es null");
		if (val == null) throw new IllegalArgumentException("El valor ingresado es null");

		if (n/m >= 5) resize(2*m);

		int i = hash(key);
		NodoHash actual = st[i];
		while(actual != null)
		{
			if(key.equals(actual.key))
			{
				actual.values.enqueue(val);
				return;
			}

			actual = actual.siguiente;
		}


		Queue<Value> valores = new Queue<Value>();
		valores.enqueue(val);

		st[i] = new NodoHash(key, valores, st[i]);
		n++;
	} 
	
	public void put(Key key, Queue<Value> val)
	{
		if(n/m >= 5) resize(2*m);
		
		int i = hash(key);
		NodoHash actual = st[i];
		
		while(actual != null)
		{
			if(key.equals(actual.key))
			{
				actual.values = val;
			}
		}
		
		st[i] = new NodoHash(key, val, st[i]);
		n++;
		
	}


	public Iterator<Value> deleteSet(Key key) 
	{
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");

		if(!contains(key)) return null; 

		int i = hash(key);
		Iterator<Value> aDevolver = null; 
		NodoHash actual = st[i];

		while(actual != null)
		{
			if(key.equals(actual.key))
			{

				actual = actual.siguiente;
				aDevolver = actual.values.iterator();
				n--;
			}
		}

		if ( n/m <= 5) resize(m/2);

		return aDevolver;
	} 

	
	public Iterator<Key> keys() 
	{
		 Queue<Key> llaves = new Queue<Key>();
	     for (int i = 0; i < m; i++)
	         if (st[i] != null) llaves.enqueue(st[i].key);
	     return llaves.iterator();
	}
	




}
