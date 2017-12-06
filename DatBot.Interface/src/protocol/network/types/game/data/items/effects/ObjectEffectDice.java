package protocol.network.types.game.data.items.effects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.data.items.effects.ObjectEffect;

@SuppressWarnings("unused")
public class ObjectEffectDice extends ObjectEffect {
	public static final int ProtocolId = 73;

	public int diceNum;
	public int diceSide;
	public int diceConst;

	public ObjectEffectDice(){
	}

	public ObjectEffectDice(int diceNum, int diceSide, int diceConst){
		this.diceNum = diceNum;
		this.diceSide = diceSide;
		this.diceConst = diceConst;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.diceNum);
			writer.writeVarShort(this.diceSide);
			writer.writeVarShort(this.diceConst);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.diceNum = reader.readVarShort();
			this.diceSide = reader.readVarShort();
			this.diceConst = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("diceNum : " + this.diceNum);
		//Network.appendDebug("diceSide : " + this.diceSide);
		//Network.appendDebug("diceConst : " + this.diceConst);
	//}
}
