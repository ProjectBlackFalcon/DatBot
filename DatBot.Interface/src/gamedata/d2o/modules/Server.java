package gamedata.d2o.modules;

import java.util.Vector;

import gamedata.d2o.GameData;
import gamedata.d2o.GameDataFileAccessor;
import utils.d2i.d2iManager;

public class Server {
	
	public static final String MODULE = "Servers";   
	
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
    public int commentId;    
    public Integer openingDate;    
    public String language;    
    public int populationId;    
    public int gameTypeId;    
    public int communityId;    
    public Vector<String> restrictedToLanguages;    
    public boolean monoAccount;    
    private String _name;    
    private String _comment;    
    private ServerGameType _gameType;    
    private ServerCommunity _community;    
    private ServerPopulation _population;
    
    
    public static Server getServerById(int id)
    {
       return (Server) GameData.getObject(MODULE,id);
    }
    
    public static Object[] getServers()
    {
       return GameData.getObjects(MODULE);
    }
    
	public String getName() {
		if(this._name == null)
			this._name = d2iManager.getText(this.nameId);
		return this._name;
	}
	
	public String getComment() {
		if(this._comment == null)
			this._comment = d2iManager.getText(this.commentId);
		return this._comment;
	}

	@Override
	public String toString() {
		return "Server [id=" + id + ", nameId=" + nameId + ", commentId=" + commentId + ", openingDate=" + openingDate + ", language=" + language + ", populationId=" + populationId + ", gameTypeId=" + gameTypeId + ", communityId=" + communityId + ", restrictedToLanguage=" + restrictedToLanguages + ", monoAccount=" + monoAccount + ", _name=" + _name
			+ ", _comment=" + _comment + ", _gameType=" + _gameType + ", _community=" + _community + ", _population=" + _population + "]";
	}

}
