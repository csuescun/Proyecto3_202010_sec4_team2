package model.data_structures;

import java.util.Comparator;

public class MaxHeapCP <T> 
{

	private T[] elementosHeap;

	private int tamanoMaximo;

	private int tamano;
	
	private Comparator<T> comparador;


	public MaxHeapCP(Comparator<T> pComparador)
	{

		tamanoMaximo = 10;

		elementosHeap = (T[]) new Comparable[tamanoMaximo+1];

		tamano = 0 ;

		comparador = pComparador;

	}


	public int darNumElementos()
	{
		return tamano;
	}

	public void agregar(T el)
	{
		if(tamano == tamanoMaximo)
		{
			T[] temp = (T[]) new Comparable[2*tamanoMaximo+1];
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


	public T[] darElementos()
	{
		return elementosHeap;
	}
	

	public T darMax()
	{
		return elementosHeap[1];
	}

	public T sacarMax()
	{
		T buscado = elementosHeap[1];
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
		T t = elementosHeap[i];
		elementosHeap[i] = elementosHeap[j];
		elementosHeap[j] = t;
	}




}

