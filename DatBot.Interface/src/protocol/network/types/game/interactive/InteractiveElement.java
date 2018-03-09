package protocol.network.types.game.interactive;

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
import protocol.network.types.game.interactive.InteractiveElementSkill;

@SuppressWarnings("unused")
public class InteractiveElement extends NetworkMessage {
	public static final int ProtocolId = 80;

	private int elementId;
	private int elementTypeId;
	private List<InteractiveElementSkill> enabledSkills;
	private List<InteractiveElementSkill> disabledSkills;
	private boolean onCurrentMap;

	public int getElementId() { return this.elementId; };
	public void setElementId(int elementId) { this.elementId = elementId; };
	public int getElementTypeId() { return this.elementTypeId; };
	public void setElementTypeId(int elementTypeId) { this.elementTypeId = elementTypeId; };
	public List<InteractiveElementSkill> getEnabledSkills() { return this.enabledSkills; };
	public void setEnabledSkills(List<InteractiveElementSkill> enabledSkills) { this.enabledSkills = enabledSkills; };
	public List<InteractiveElementSkill> getDisabledSkills() { return this.disabledSkills; };
	public void setDisabledSkills(List<InteractiveElementSkill> disabledSkills) { this.disabledSkills = disabledSkills; };
	public boolean isOnCurrentMap() { return this.onCurrentMap; };
	public void setOnCurrentMap(boolean onCurrentMap) { this.onCurrentMap = onCurrentMap; };

	public InteractiveElement(){
	}

	public InteractiveElement(int elementId, int elementTypeId, List<InteractiveElementSkill> enabledSkills, List<InteractiveElementSkill> disabledSkills, boolean onCurrentMap){
		this.elementId = elementId;
		this.elementTypeId = elementTypeId;
		this.enabledSkills = enabledSkills;
		this.disabledSkills = disabledSkills;
		this.onCurrentMap = onCurrentMap;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.elementId);
			writer.writeInt(this.elementTypeId);
			writer.writeShort(this.enabledSkills.size());
			int _loc2_ = 0;
			while( _loc2_ < this.enabledSkills.size()){
				writer.writeShort(InteractiveElementSkill.ProtocolId);
				this.enabledSkills.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.disabledSkills.size());
			int _loc3_ = 0;
			while( _loc3_ < this.disabledSkills.size()){
				writer.writeShort(InteractiveElementSkill.ProtocolId);
				this.disabledSkills.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
			writer.writeBoolean(this.onCurrentMap);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.elementId = reader.readInt();
			this.elementTypeId = reader.readInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.enabledSkills = new ArrayList<InteractiveElementSkill>();
			while( _loc3_ <  _loc2_){
				InteractiveElementSkill _loc15_ = (InteractiveElementSkill) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.enabledSkills.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.disabledSkills = new ArrayList<InteractiveElementSkill>();
			while( _loc5_ <  _loc4_){
				InteractiveElementSkill _loc16_ = (InteractiveElementSkill) ProtocolTypeManager.getInstance(reader.readShort());
				_loc16_.Deserialize(reader);
				this.disabledSkills.add(_loc16_);
				_loc5_++;
			}
			this.onCurrentMap = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return "InteractiveElement [elementId=" + elementId + ", elementTypeId=" + elementTypeId + ", enabledSkills=" + enabledSkills + ", disabledSkills=" + disabledSkills + ", onCurrentMap=" + onCurrentMap + "]";
	}

}
