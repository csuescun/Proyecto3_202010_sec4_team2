package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.MaxColaCP;

public class TestMaxColaCP 
{

	private MaxColaCP<String> cola;

	@Before
	public void setUp()
	{
		cola = new MaxColaCP<String>();
		
		cola.agregar("H");
		cola.agregar("O");
		cola.agregar("L");
		cola.agregar("A");
	}
	
	@Test
	public void testEsVacia()
	{
		assertTrue(!cola.esVacia());
	}
	
	@Test
	public void testDarTamano()
	{
		assertEquals("El tamano no es correcto", 4, cola.darTamano());
	}
	
	@Test
	public void testDarMax()
	{
		assertEquals("El maximo no es el esperado", "O", cola.darMax());
	}
	
	@Test
	public void testAgregar()
	{
		cola.agregar("Z");
		assertEquals("El tamano no es el esperado",5, cola.darTamano() );
		assertEquals("El elemento no es el esperado", "Z", cola.darMax());
		
	}
	
	@Test
	public void testSacarMax()
	{
		cola.sacarMax();
		assertEquals("El tamano no es el esperado",3, cola.darTamano() );
		assertEquals("El maximo no es el esperado", "L", cola.darMax());
	}
	
}
