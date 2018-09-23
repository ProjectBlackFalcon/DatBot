package protocol.network.types.game.presets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class SimpleCharacterCharacteristicForPreset extends NetworkMessage {
	public static final int ProtocolId = 541;

	private String keyword;
	private int base;
	private int additionnal;

	public String getKeyword() { return this.keyword; }
	public void setKeyword(String keyword) { this.keyword = keyword; };
	public int getBase() { return this.base; }
	public void setBase(int base) { this.base = base; };
	public int getAdditionnal() { return this.additionnal; }
	public void setAdditionnal(int additionnal) { this.additionnal = additionnal; };

	public SimpleCharacterCharacteristicForPreset(){
	}

	public SimpleCharacterCharacteristicForPreset(String keyword, int base, int additionnal){
		this.keyword = keyword;
		this.base = base;
		this.additionnal = additionnal;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.keyword);
			writer.writeVarShort(this.base);
			writer.writeVarShort(this.additionnal);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.keyword = reader.readUTF();
			this.base = reader.readVarShort();
			this.additionnal = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
