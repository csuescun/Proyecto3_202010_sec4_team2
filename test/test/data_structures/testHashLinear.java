package test.data_structures;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;

import model.data_structures.LinearProbingHash;
import model.data_structures.SeparateChainingHash;

public class testHashLinear {
	
	LinearProbingHash<String, String> hashLP;
	
	@Before
	public void setUp()
	{
		hashLP = new LinearProbingHash<String, String>(7);
		hashLP.putInSet("1", "H");
		hashLP.putInSet("2", "O");
		hashLP.putInSet("3", "L");
		hashLP.putInSet("4", "A");
		
	}
	
	
	@Test
	public void testDarTamano()
	{
		assertEquals("El tamano no es el esperado", 7, hashLP.darTamano() );
	}
	
	@Test
	public void testDarTotalDuplas()
	{
		assertEquals("El numero de duplas no es el esperado", 4, hashLP.darTotalDuplas());
	}
	
	@Test 
	public void testDarTotalRehashes()
	{
		assertEquals("El numero de rehashes no es el esperado", 0, hashLP.darTotalRehashes());
	}
	
	@Test
	public void testContains()
	{
		assertTrue(hashLP.contains("1"));
		assertTrue(!hashLP.contains("5"));
	}
	
	@Test
	public void testPutInSet()
	{

		hashLP.putInSet("1", "!");
		assertEquals("El total de valores no es el esperado", hashLP.getSet("1").darTamano(), 2);
		
		hashLP.putInSet("5", "C");
		assertTrue(hashLP.contains("5"));
		assertEquals("El total de valores no es el esperado", hashLP.getSet("5").darTamano(), 1);
	}
	
	@Test
	public void testGetSet()
	{
		assertEquals("El total de valores no es el esperado", hashLP.getSet("1").darTamano(), 1);
		assertNull("El total de valores no es el esperado", hashLP.getSet("8"));
	}
	
	@Test
	public void testDeleteSet()
	{
		Iterator<String> bla = hashLP.deleteSet("1");
		assertTrue(!hashLP.contains("1"));
	}
	
	@Test 
	public void testResize()
	{
		hashLP.resize(16);
		assertEquals("El tamano no es el esperado", 16, hashLP.darTamano() );
		assertEquals("El numero de duplas no es el esperado", 4, hashLP.darTotalDuplas());
		assertEquals("El numero de rehashes no es el esperado", 1, hashLP.darTotalRehashes());
	}
	
	

}
