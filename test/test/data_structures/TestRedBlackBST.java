package test.data_structures;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.Queue;
import model.data_structures.RedBlackBST;

public class TestRedBlackBST 
{

	private RedBlackBST <Integer,String> bst;

	@Before
	public void setUpEscenario1()
	{
		bst = new RedBlackBST<Integer, String>();
		bst.put(1, "A");
		bst.put(2, "B");
		bst.put(3, "C");
		bst.put(4, "D");
		bst.put(5, "E");
		bst.put(6, "F");
		bst.put(7, "G");
		bst.put(8, "H");
		bst.put(9, "I");
		bst.put(10, "J");
	}

	public void setUpEscenario2()
	{
		bst = new RedBlackBST<Integer, String>();
	}

	public void setUpEscenario3()
	{
		bst = new RedBlackBST<Integer, String>();
		bst.put(1, "A");
		bst.put(2, "B");
		bst.put(3, "C");
	}

	@Test
	public void testSize()
	{
		assertEquals("El tamaño no es el esperado", 10, bst.size());
	}

	@Test
	public void testIsEmpty()
	{
		assertFalse("El arbol no deberia estar vacio", bst.isEmpty());

		setUpEscenario2();
		assertTrue("El arbol deberia estar vacio", bst.isEmpty());
	}

	@Test
	public void testGet()
	{
		assertNull("La llave no deberia existir",bst.get(19));
		assertEquals("La llave deberia existir", "A", bst.get(1));
	}

	@Test
	public void testGetHeight()
	{
		assertEquals("La altura no es la esperada",4, bst.getHeight(9));
		assertEquals("La altura no es la esperada", 2, bst.getHeight(2));
	}

	@Test
	public void testContains()
	{
		assertTrue("La llave deberia existir", bst.contains(5));
		assertFalse("La llave no deberia existir", bst.contains(22));
	}

	@Test
	public void testPut()
	{
		bst.put(3, "M");
		bst.put(20, "Q");
		assertEquals("Deberia cambiar el valor original a M", "M", bst.get(3));
		assertEquals("Debio añadirse como nuevo elemento", "Q",bst.get(20));

	}

	@Test
	public void testHeight()
	{
		assertEquals("La altura del arbol no es la esperada", 4, bst.height());
	}

	@Test
	public void testMin()
	{
		assertEquals("La llave minima no es la esperada",(Integer)1,bst.min());	
	}

	@Test
	public void testMax()
	{
		assertEquals("La llave maxima no es la esperada",(Integer)10,bst.max());
	}

	@Test
	public void testCheck()
	{
		assertTrue("El arbol deberia estar checked", bst.check());
	}

	@Test
	public void testKeys()
	{
		setUpEscenario3();

		Iterator<Integer> keys = bst.keys().iterator();	

		assertEquals("La llave no es la esperada", (Integer)1,keys.next());
		assertEquals("La llave no es la esperada", (Integer)2,keys.next());
		assertEquals("La llave no es la esperada", (Integer)3,keys.next());

		assertFalse("El numero de llaves no es el esperado", keys.hasNext());
	}

	@Test
	public void testValuesInRange()
	{
		Iterator<String> valores = bst.valuesInRange(1, 4);

		assertEquals("El elemento no es el esperado", "A",(String)valores.next());
		assertEquals("El elemento no es el esperado", "B",(String)valores.next());
		assertEquals("El elemento no es el esperado", "C",(String)valores.next());
		assertEquals("El elemento no es el esperado", "D",(String)valores.next());

		assertFalse("El numero de valores no es el esperado", valores.hasNext());
	}

	@Test
	public void testKeysInRange()
	{
		Iterator<Integer> llaves = bst.keysInRange(6, 10);
		
		assertEquals("La llave no es la esperada", (Integer)6, llaves.next());
		assertEquals("La llave no es la esperada", (Integer)7, llaves.next());
		assertEquals("La llave no es la esperada", (Integer)8, llaves.next());
		assertEquals("La llave no es la esperada", (Integer)9, llaves.next());
		assertEquals("La llave no es la esperada", (Integer)10, llaves.next());
		
		assertFalse("El numero de llaves no es el esperado", llaves.hasNext());
		
	}

}

