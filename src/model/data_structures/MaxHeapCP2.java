package model.data_structures;

import java.util.Comparator;

public class MaxHeapCP2<Key extends Comparable<Key>, Value, Adicional1, Adicional2> 
{

	private Vertice<Key, Value, Adicional1, Adicional2>[] elementosHeap;

	private int tamanoMaximo;

	private int tamano;
	
	private Comparator<Vertice<Key, Value, Adicional1, Adicional2>> comparador;


	public MaxHeapCP2(Comparator<Vertice<Key, Value, Adicional1, Adicional2>> pComparador)
	{

		tamanoMaximo = 10;

		elementosHeap =(Vertice<Key, Value, Adicional1, Adicional2>[]) new Comparable[tamanoMaximo+1];

		tamano = 0 ;

		comparador = pComparador;

	}


	public int darNumElementos()
	{
		return tamano;
	}

	public void agregar(Vertice<Key, Value, Adicional1, Adicional2> el)
	{
		if(tamano == tamanoMaximo)
		{
			Vertice<Key, Value, Adicional1, Adicional2>[] temp = (Vertice<Key, Value, Adicional1, Adicional2>[]) new Comparable[2*tamanoMaximo+1];
			for(int i= 1; i <= tamano; i++)
			{
				temp[i]= elementosHeap[i];
			}

			elementosHeap = temp;
			tamanoMaximo = 2*tamanoMaximo; 
		}

		tamano ++;
		elementosHeap[tamano] = el;

		swim(tamano);
	}


	public Vertice<Key, Value, Adicional1, Adicional2>[] darElementos()
	{
		return elementosHeap;
	}
	

	public Vertice<Key, Value, Adicional1, Adicional2> darMax()
	{
		return elementosHeap[1];
	}

	public Vertice<Key, Value, Adicional1, Adicional2> sacarMax()
	{
		Vertice<Key, Value, Adicional1, Adicional2> buscado = elementosHeap[1];
		elementosHeap[1] = elementosHeap[tamano--];
		elementosHeap[tamano +1]= null; 

		sink(1);

		return buscado;

	}
	


	public boolean esVacia()
	{
		return tamano == 0;
	}


	public void swim(int k)
	{
		while(k > 1 && !less(k,k/2))
		{
			exch(k, k/2);
			k = k/2;
		}
	}


	public void sink(int k)
	{
		while (2*k <= tamano)
		{
			int j = 2*k;

			if (j < tamano && less(j, j+1))
				j++;

			if (!less(k, j))
				break;

			exch(k, j);
			k = j;
		}
	}


	private boolean less (int i, int j)
	{
		return comparador.compare(elementosHeap[i], elementosHeap[j]) < 0;

	}


	private void exch (int i, int j)
	{
		Vertice<Key, Value, Adicional1, Adicional2> t = elementosHeap[i];
		elementosHeap[i] = elementosHeap[j];
		elementosHeap[j] = t;
	}




}

