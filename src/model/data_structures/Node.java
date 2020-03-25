package model.data_structures;

public class Node<E> {
	
	private Node<E> siguiente;
	private E elemento;
	
	public Node(E item)
	{
		siguiente = null;
		elemento = item;
	}

	public Node<E> darSiguiente()
	{
		return siguiente;
	}
	
	public void cambiarSiguiente(Node<E> pSiguiente)
	{
		siguiente = pSiguiente;
	}
	
	public E darElemento()
	{
		return elemento;
	}

}
