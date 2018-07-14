package gamedata.d2o;

import java.util.Hashtable;

public class GameData {
    private static Hashtable<String, Hashtable<Integer, Integer>> _overrides = new Hashtable<String, Hashtable<Integer, Integer>>();
    
    public static void addOverride(String str, int i1, int i2) {
    	if(!_overrides.containsKey(str))
    		_overrides.put(str, new Hashtable<Integer, Integer>());
    	_overrides.get(str).put(i1, i2);
    }
    
    public static Object getObject(String str, int i) {
    	Object o = null;
    	if(_overrides.containsKey(str) && _overrides.get(str).containsKey(i))
    		i = _overrides.get(str).get(i);
    	o = GameDataFileAccessor.getInstance().getObject(str, i);
    	return o;
    }
    
    public static Object[] getObjects(String str) {
    	Object[] array;
    	array = GameDataFileAccessor.getInstance().getObjects(str);
    	return array;
    }
}