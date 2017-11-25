package protocol.network.messages.game.basic;

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
import protocol.network.types.game.social.AbstractSocialGroupInfos;

@SuppressWarnings("unused")
public class BasicWhoIsMessage extends NetworkMessage {
	public static final int ProtocolId = 180;

	public boolean self;
	public int position;
	public String accountNickname;
	public int accountId;
	public String playerName;
	public long playerId;
	public int areaId;
	public int serverId;
	public int originServerId;
	public List<AbstractSocialGroupInfos> socialGroups;
	public boolean verbose;
	public int playerState;

	public BasicWhoIsMessage(){
	}

	public BasicWhoIsMessage(boolean self, int position, String accountNickname, int accountId, String playerName, long playerId, int areaId, int serverId, int originServerId, List<AbstractSocialGroupInfos> socialGroups, boolean verbose, int playerState){
		this.self = self;
		this.position = position;
		this.accountNickname = accountNickname;
		this.accountId = accountId;
		this.playerName = playerName;
		this.playerId = playerId;
		this.areaId = areaId;
		this.serverId = serverId;
		this.originServerId = originServerId;
		this.socialGroups = socialGroups;
		this.verbose = verbose;
		this.playerState = playerState;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, self);
			flag = BooleanByteWrapper.SetFlag(1, flag, verbose);
			writer.writeByte(flag);
			writer.writeByte(this.position);
			writer.writeUTF(this.accountNickname);
			writer.writeInt(this.accountId);
			writer.writeUTF(this.playerName);
			writer.writeVarLong(this.playerId);
			writer.writeShort(this.areaId);
			writer.writeShort(this.serverId);
			writer.writeShort(this.originServerId);
			writer.writeShort(this.socialGroups.size());
			int _loc2_ = 0;
			while( _loc2_ < this.socialGroups.size()){
				writer.writeShort(AbstractSocialGroupInfos.ProtocolId);
				this.socialGroups.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeByte(this.playerState);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.self = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.verbose = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.position = reader.readByte();
			this.accountNickname = reader.readUTF();
			this.accountId = reader.readInt();
			this.playerName = reader.readUTF();
			this.playerId = reader.readVarLong();
			this.areaId = reader.readShort();
			this.serverId = reader.readShort();
			this.originServerId = reader.readShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.socialGroups = new ArrayList<AbstractSocialGroupInfos>();
			while( _loc3_ <  _loc2_){
				AbstractSocialGroupInfos _loc15_ = (AbstractSocialGroupInfos) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.socialGroups.add(_loc15_);
				_loc3_++;
			}
			this.playerState = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("self : " + this.self);
		//Network.appendDebug("position : " + this.position);
		//Network.appendDebug("accountNickname : " + this.accountNickname);
		//Network.appendDebug("accountId : " + this.accountId);
		//Network.appendDebug("playerName : " + this.playerName);
		//Network.appendDebug("playerId : " + this.playerId);
		//Network.appendDebug("areaId : " + this.areaId);
		//Network.appendDebug("serverId : " + this.serverId);
		//Network.appendDebug("originServerId : " + this.originServerId);
		//for(AbstractSocialGroupInfos a : socialGroups) {
			//Network.appendDebug("socialGroups : " + a);
		//}
		//Network.appendDebug("verbose : " + this.verbose);
		//Network.appendDebug("playerState : " + this.playerState);
	//}
}