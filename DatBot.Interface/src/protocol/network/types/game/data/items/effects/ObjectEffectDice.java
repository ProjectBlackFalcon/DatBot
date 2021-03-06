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

	private int diceNum;
	private int diceSide;
	private int diceConst;

	public int getDiceNum() { return this.diceNum; }
	public void setDiceNum(int diceNum) { this.diceNum = diceNum; };
	public int getDiceSide() { return this.diceSide; }
	public void setDiceSide(int diceSide) { this.diceSide = diceSide; };
	public int getDiceConst() { return this.diceConst; }
	public void setDiceConst(int diceConst) { this.diceConst = diceConst; };

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
			writer.writeVarInt(this.diceNum);
			writer.writeVarInt(this.diceSide);
			writer.writeVarInt(this.diceConst);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.diceNum = reader.readVarInt();
			this.diceSide = reader.readVarInt();
			this.diceConst = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
