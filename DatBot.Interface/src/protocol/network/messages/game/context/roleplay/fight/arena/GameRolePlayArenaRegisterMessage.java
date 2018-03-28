package protocol.network.messages.game.context.roleplay.fight.arena;

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
public class GameRolePlayArenaRegisterMessage extends NetworkMessage {
	public static final int ProtocolId = 6280;

	private int battleMode;

	public int getBattleMode() { return this.battleMode; }
	public void setBattleMode(int battleMode) { this.battleMode = battleMode; };

	public GameRolePlayArenaRegisterMessage(){
	}

	public GameRolePlayArenaRegisterMessage(int battleMode){
		this.battleMode = battleMode;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.battleMode);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.battleMode = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
