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
import protocol.network.NetworkMessage;
import protocol.network.types.game.character.restriction.ActorRestrictionsInformations;
import protocol.network.types.game.context.roleplay.HumanOption;

@SuppressWarnings("unused")
public class HumanInformations extends NetworkMessage {
	public static final int ProtocolId = 157;

	private ActorRestrictionsInformations restrictions;
	private boolean sex;
	private List<HumanOption> options;

	public ActorRestrictionsInformations getRestrictions() { return this.restrictions; }
	public void setRestrictions(ActorRestrictionsInformations restrictions) { this.restrictions = restrictions; };
	public boolean isSex() { return this.sex; }
	public void setSex(boolean sex) { this.sex = sex; };
	public List<HumanOption> getOptions() { return this.options; }
	public void setOptions(List<HumanOption> options) { this.options = options; };

	public HumanInformations(){
	}

	public HumanInformations(ActorRestrictionsInformations restrictions, boolean sex, List<HumanOption> options){
		this.restrictions = restrictions;
		this.sex = sex;
		this.options = options;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			restrictions.Serialize(writer);
			writer.writeBoolean(this.sex);
			writer.writeShort(this.options.size());
			int _loc2_ = 0;
			while( _loc2_ < this.options.size()){
				writer.writeShort(HumanOption.ProtocolId);
				this.options.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.restrictions = new ActorRestrictionsInformations();
			this.restrictions.Deserialize(reader);
			this.sex = reader.readBoolean();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.options = new ArrayList<HumanOption>();
			while( _loc3_ <  _loc2_){
				HumanOption _loc15_ = (HumanOption) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.options.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
