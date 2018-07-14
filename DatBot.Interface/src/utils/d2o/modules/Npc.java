package utils.d2o.modules;

import java.util.Vector;

import utils.d2i.d2iManager;
import utils.d2o.GameData;
import utils.d2o.GameDataFileAccessor;

public class Npc {
	
	public static final String MODULE = "Npcs";
	
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
    public Vector<Vector<Integer>> dialogMessages;    
    public Vector<Vector<Integer>> dialogReplies;    
    public Vector<Integer> actions;
    public int gender;    
    public String look;    
    public int tokenShop;    
    public Vector<AnimFunNpcData> animFunList;    
    public boolean fastAnimsFun;    
    private String _name;
      
    public static Npc getNpcById(int id)
    {
       return (Npc) GameData.getObject(MODULE,id);
    }
    
    public static Object[] getNpcs()
    {
       return GameData.getObjects(MODULE);
    }
    
	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}

	@Override
	public String toString() {
		return "Npc [id=" + id + ", nameId=" + nameId + ", dialogMessages=" + dialogMessages + ", dialogReplies=" + dialogReplies + ", actions=" + actions + ", gender=" + gender + ", look=" + look + ", tokenShop=" + tokenShop + ", animFunList=" + animFunList + ", fastAnimsFun=" + fastAnimsFun + ", _name=" + _name + "]";
	}

}
