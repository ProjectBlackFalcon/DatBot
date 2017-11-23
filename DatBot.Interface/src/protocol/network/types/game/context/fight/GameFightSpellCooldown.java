package protocol.network.types.game.context.fight;

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
public class GameFightSpellCooldown extends NetworkMessage {
	public static final int ProtocolId = 205;

	public int spellId;
	public int cooldown;

	public GameFightSpellCooldown(){
	}

	public GameFightSpellCooldown(int spellId, int cooldown){
		this.spellId = spellId;
		this.cooldown = cooldown;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.spellId);
			writer.writeByte(this.cooldown);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellId = reader.readInt();
			this.cooldown = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("spellId : " + this.spellId);
		//Network.appendDebug("cooldown : " + this.cooldown);
	//}
}
