package utils.d2o.modules;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class PointOfInterest {
	
	public static final String MODULE ="PointOfInterest";
    
	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    public int id;
    public int nameId;  
    public int categoryId;  
    private String _name;    
        
    public static PointOfInterest getPointOfInterestById(int id)
    {
       return (PointOfInterest) GameData.getObject(MODULE,id);
    }
    
    public static Object[] getPointOfInterests()
    {
       return GameData.getObjects(MODULE);
    }
    
	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}
}
