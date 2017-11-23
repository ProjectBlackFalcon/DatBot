package protocol.network.messages.game.context.fight;

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
public class GameFightJoinMessage extends NetworkMessage {
	public static final int ProtocolId = 702;

	public boolean isTeamPhase;
	public boolean canBeCancelled;
	public boolean canSayReady;
	public boolean isFightStarted;
	public int timeMaxBeforeFightStart;
	public int fightType;

	public GameFightJoinMessage(){
	}

	public GameFightJoinMessage(boolean isTeamPhase, boolean canBeCancelled, boolean canSayReady, boolean isFightStarted, int timeMaxBeforeFightStart, int fightType){
		this.isTeamPhase = isTeamPhase;
		this.canBeCancelled = canBeCancelled;
		this.canSayReady = canSayReady;
		this.isFightStarted = isFightStarted;
		this.timeMaxBeforeFightStart = timeMaxBeforeFightStart;
		this.fightType = fightType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, isTeamPhase);
			flag = BooleanByteWrapper.SetFlag(1, flag, canBeCancelled);
			flag = BooleanByteWrapper.SetFlag(2, flag, canSayReady);
			flag = BooleanByteWrapper.SetFlag(3, flag, isFightStarted);
			writer.writeByte(flag);
			writer.writeShort(this.timeMaxBeforeFightStart);
			writer.writeByte(this.fightType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.isTeamPhase = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.canBeCancelled = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.canSayReady = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.isFightStarted = BooleanByteWrapper.GetFlag(flag, (byte) 3);
			this.timeMaxBeforeFightStart = reader.readShort();
			this.fightType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("isTeamPhase : " + this.isTeamPhase);
		//Network.appendDebug("canBeCancelled : " + this.canBeCancelled);
		//Network.appendDebug("canSayReady : " + this.canSayReady);
		//Network.appendDebug("isFightStarted : " + this.isFightStarted);
		//Network.appendDebug("timeMaxBeforeFightStart : " + this.timeMaxBeforeFightStart);
		//Network.appendDebug("fightType : " + this.fightType);
	//}
}
