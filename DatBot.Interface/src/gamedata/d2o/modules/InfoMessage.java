package gamedata.d2o.modules;

import java.util.Arrays;

import gamedata.d2o.GameData;
import gamedata.d2o.GameDataFileAccessor;
import utils.d2i.d2iManager;

public class InfoMessage {
	public static final String MODULE = "InfoMessages";
	
	static {
		try {
			GameDataFileAccessor.getInstance().init(MODULE);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int typeId;
	public int messageId;
	public int textId;
	private String _text;

	public static InfoMessage getInfoMessageById(int id) {
		return (InfoMessage) GameData.getObject(MODULE, id);
	}
	public static InfoMessage[] getInfoMessages() {
		Object[] objArray = GameData.getObjects(MODULE);
        return Arrays.copyOf(objArray, objArray.length, InfoMessage[].class);
	}

	public String getText() {
		if(this._text == null)
			this._text = d2iManager.getText(this.textId);
		return this._text;
	}
}