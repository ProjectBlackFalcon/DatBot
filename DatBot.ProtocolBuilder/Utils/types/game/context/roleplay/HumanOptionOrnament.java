package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.HumanOption;

@SuppressWarnings("unused")
public class HumanOptionOrnament extends HumanOption {
	public static final int ProtocolId = 411;

	private int ornamentId;
	private int level;

	public int getOrnamentId() { return this.ornamentId; };
	public void setOrnamentId(int ornamentId) { this.ornamentId = ornamentId; };
	public int getLevel() { return this.level; };
	public void setLevel(int level) { this.level = level; };

	public HumanOptionOrnament(){
	}

	public HumanOptionOrnament(int ornamentId, int level){
		this.ornamentId = ornamentId;
		this.level = level;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.ornamentId);
			writer.writeVarShort(this.level);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.ornamentId = reader.readVarShort();
			this.level = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
