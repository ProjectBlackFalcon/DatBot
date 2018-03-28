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
public class GameRolePlayArenaRegistrationStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 6284;

	private boolean registered;
	private int step;
	private int battleMode;

	public boolean isRegistered() { return this.registered; }
	public void setRegistered(boolean registered) { this.registered = registered; };
	public int getStep() { return this.step; }
	public void setStep(int step) { this.step = step; };
	public int getBattleMode() { return this.battleMode; }
	public void setBattleMode(int battleMode) { this.battleMode = battleMode; };

	public GameRolePlayArenaRegistrationStatusMessage(){
	}

	public GameRolePlayArenaRegistrationStatusMessage(boolean registered, int step, int battleMode){
		this.registered = registered;
		this.step = step;
		this.battleMode = battleMode;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.registered);
			writer.writeByte(this.step);
			writer.writeInt(this.battleMode);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.registered = reader.readBoolean();
			this.step = reader.readByte();
			this.battleMode = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
