package protocol.network.messages.game.context.roleplay.breach;

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
import protocol.network.types.game.character.CharacterMinimalInformations;
import protocol.network.types.game.data.items.effects.ObjectEffectInteger;

@SuppressWarnings("unused")
public class BreachStateMessage extends NetworkMessage {
	public static final int ProtocolId = 6799;

	private CharacterMinimalInformations owner;
	private List<ObjectEffectInteger> bonuses;
	private int erosion;
	private int bugdet;
	private boolean saved;

	public CharacterMinimalInformations getOwner() { return this.owner; }
	public void setOwner(CharacterMinimalInformations owner) { this.owner = owner; };
	public List<ObjectEffectInteger> getBonuses() { return this.bonuses; }
	public void setBonuses(List<ObjectEffectInteger> bonuses) { this.bonuses = bonuses; };
	public int getErosion() { return this.erosion; }
	public void setErosion(int erosion) { this.erosion = erosion; };
	public int getBugdet() { return this.bugdet; }
	public void setBugdet(int bugdet) { this.bugdet = bugdet; };
	public boolean isSaved() { return this.saved; }
	public void setSaved(boolean saved) { this.saved = saved; };

	public BreachStateMessage(){
	}

	public BreachStateMessage(CharacterMinimalInformations owner, List<ObjectEffectInteger> bonuses, int erosion, int bugdet, boolean saved){
		this.owner = owner;
		this.bonuses = bonuses;
		this.erosion = erosion;
		this.bugdet = bugdet;
		this.saved = saved;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			owner.Serialize(writer);
			writer.writeShort(this.bonuses.size());
			int _loc2_ = 0;
			while( _loc2_ < this.bonuses.size()){
				this.bonuses.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeVarInt(this.erosion);
			writer.writeVarInt(this.bugdet);
			writer.writeBoolean(this.saved);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.owner = new CharacterMinimalInformations();
			this.owner.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.bonuses = new ArrayList<ObjectEffectInteger>();
			while( _loc3_ <  _loc2_){
				ObjectEffectInteger _loc15_ = new ObjectEffectInteger();
				_loc15_.Deserialize(reader);
				this.bonuses.add(_loc15_);
				_loc3_++;
			}
			this.erosion = reader.readVarInt();
			this.bugdet = reader.readVarInt();
			this.saved = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
