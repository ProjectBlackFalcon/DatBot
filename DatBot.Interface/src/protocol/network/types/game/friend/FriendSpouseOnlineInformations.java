package protocol.network.types.game.friend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.friend.FriendSpouseInformations;

@SuppressWarnings("unused")
public class FriendSpouseOnlineInformations extends FriendSpouseInformations {
	public static final int ProtocolId = 93;

	private double mapId;
	private int subAreaId;
	private boolean inFight;
	private boolean followSpouse;

	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public boolean isInFight() { return this.inFight; }
	public void setInFight(boolean inFight) { this.inFight = inFight; };
	public boolean isFollowSpouse() { return this.followSpouse; }
	public void setFollowSpouse(boolean followSpouse) { this.followSpouse = followSpouse; };

	public FriendSpouseOnlineInformations(){
	}

	public FriendSpouseOnlineInformations(double mapId, int subAreaId, boolean inFight, boolean followSpouse){
		this.mapId = mapId;
		this.subAreaId = subAreaId;
		this.inFight = inFight;
		this.followSpouse = followSpouse;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, inFight);
			flag = BooleanByteWrapper.SetFlag(1, flag, followSpouse);
			writer.writeByte(flag);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.inFight = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.followSpouse = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
