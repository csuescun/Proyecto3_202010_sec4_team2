package model.data_structures;

import java.util.Iterator;

public interface IHash <Key extends Comparable<Key>, Value extends Comparable<Value>>
{

	void putInSet(Key key, Value value);
	
	Queue<Value> getSet(Key key);
	
	Iterator<Value> deleteSet(Key key);
	
	Iterator<Key> keys();
}
