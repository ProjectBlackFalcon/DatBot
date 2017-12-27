package protocol.network.types.game.look;

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
import protocol.network.types.game.look.EntityLook;

@SuppressWarnings("unused")
public class SubEntity extends NetworkMessage {
	public static final int ProtocolId = 54;

	private int bindingPointCategory;
	private int bindingPointIndex;
	private EntityLook subEntityLook;

	public int getBindingPointCategory() { return this.bindingPointCategory; };
	public void setBindingPointCategory(int bindingPointCategory) { this.bindingPointCategory = bindingPointCategory; };
	public int getBindingPointIndex() { return this.bindingPointIndex; };
	public void setBindingPointIndex(int bindingPointIndex) { this.bindingPointIndex = bindingPointIndex; };
	public EntityLook getSubEntityLook() { return this.subEntityLook; };
	public void setSubEntityLook(EntityLook subEntityLook) { this.subEntityLook = subEntityLook; };

	public SubEntity(){
	}

	public SubEntity(int bindingPointCategory, int bindingPointIndex, EntityLook subEntityLook){
		this.bindingPointCategory = bindingPointCategory;
		this.bindingPointIndex = bindingPointIndex;
		this.subEntityLook = subEntityLook;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.bindingPointCategory);
			writer.writeByte(this.bindingPointIndex);
			subEntityLook.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.bindingPointCategory = reader.readByte();
			this.bindingPointIndex = reader.readByte();
			this.subEntityLook = new EntityLook();
			this.subEntityLook.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
