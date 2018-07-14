package gamedata.d2o.modules;

import gamedata.d2o.GameData;
import gamedata.d2o.GameDataFileAccessor;
import utils.d2i.d2iManager;

public class MountBehavior {
	
	public static final String MODULE = "MountBehaviors";
    
    
    public int id;    
    public int nameId;    
    public int descriptionId;    
    private String _name;    
    private String _description;
    
	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    public static MountBehavior getMountBehaviorById(int id)  
    {
       return (MountBehavior) GameData.getObject(MODULE,id);
    }
    
    public static Object[] getMountBehaviors() 
    {
       return GameData.getObjects(MODULE);
    }
    
	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}

	public String getDescription() {
		if(this._description == null)
			this._description = d2iManager.getText(this.descriptionId);
		return this._description;
	}

	@Override
	public String toString() {
		return "MountBehavior [id=" + id + ", nameId=" + nameId + ", descriptionId=" + descriptionId + ", _name=" + _name + ", _description=" + _description + "]";
	}
}
