package model.data_structures;

public interface IQueue<T> extends Iterable<T> 
{

	/**
	 * Retorna true si la cola esta vacia
	 * @return true si la cola esta vacia, false si no lo está. 
	 */

	public boolean isEmpty(); 

	/**
	 * Retorna el tamano de la cola. 
	 * @return tamano cola (numero de elementos contenidos)
	 */
	public int darTamano();


	/**
	 * Retorna el primer elemento de la cola.
	 * @return primer elemento de la cola
	 */
	public T darPrimerElemento(); 

	/**
	 * Retorna el ultimo elemento de la cola
	 * @return ultimo elemento de la cola
	 */
	public T darUltimoElemento(); 

	/**
	 * Agrega un nuevo elemento a la cola
	 * @param item elemento que se desea agregar
	 */
	public void enqueue(T item);

	/**
	 * Elimina y retorna el elemento que se agregó más antiguamente
	 * @return elemento agregado más antiguamente
	 */

	public T dequeue();


}
