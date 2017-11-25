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
public class GameFightFighterLightInformations extends NetworkMessage {
	public static final int ProtocolId = 413;

	public double id;
	public int wave;
	public int level;
	public int breed;
	public boolean sex;
	public boolean alive;

	public GameFightFighterLightInformations(){
	}

	public GameFightFighterLightInformations(double id, int wave, int level, int breed, boolean sex, boolean alive){
		this.id = id;
		this.wave = wave;
		this.level = level;
		this.breed = breed;
		this.sex = sex;
		this.alive = alive;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, sex);
			flag = BooleanByteWrapper.SetFlag(1, flag, alive);
			writer.writeByte(flag);
			writer.writeDouble(this.id);
			writer.writeByte(this.wave);
			writer.writeVarShort(this.level);
			writer.writeByte(this.breed);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.sex = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.alive = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.id = reader.readDouble();
			this.wave = reader.readByte();
			this.level = reader.readVarShort();
			this.breed = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("wave : " + this.wave);
		//Network.appendDebug("level : " + this.level);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("alive : " + this.alive);
	//}
}