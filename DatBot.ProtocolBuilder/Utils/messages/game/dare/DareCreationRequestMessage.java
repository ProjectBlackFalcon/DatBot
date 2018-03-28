package protocol.network.messages.game.dare;

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
import protocol.network.types.game.dare.DareCriteria;

@SuppressWarnings("unused")
public class DareCreationRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6665;

	private long subscriptionFee;
	private long jackpot;
	private int maxCountWinners;
	private int delayBeforeStart;
	private int duration;
	private boolean isPrivate;
	private boolean isForGuild;
	private boolean isForAlliance;
	private boolean needNotifications;
	private List<DareCriteria> criterions;

	public long getSubscriptionFee() { return this.subscriptionFee; }
	public void setSubscriptionFee(long subscriptionFee) { this.subscriptionFee = subscriptionFee; };
	public long getJackpot() { return this.jackpot; }
	public void setJackpot(long jackpot) { this.jackpot = jackpot; };
	public int getMaxCountWinners() { return this.maxCountWinners; }
	public void setMaxCountWinners(int maxCountWinners) { this.maxCountWinners = maxCountWinners; };
	public int getDelayBeforeStart() { return this.delayBeforeStart; }
	public void setDelayBeforeStart(int delayBeforeStart) { this.delayBeforeStart = delayBeforeStart; };
	public int getDuration() { return this.duration; }
	public void setDuration(int duration) { this.duration = duration; };
	public boolean isIsPrivate() { return this.isPrivate; }
	public void setIsPrivate(boolean isPrivate) { this.isPrivate = isPrivate; };
	public boolean isIsForGuild() { return this.isForGuild; }
	public void setIsForGuild(boolean isForGuild) { this.isForGuild = isForGuild; };
	public boolean isIsForAlliance() { return this.isForAlliance; }
	public void setIsForAlliance(boolean isForAlliance) { this.isForAlliance = isForAlliance; };
	public boolean isNeedNotifications() { return this.needNotifications; }
	public void setNeedNotifications(boolean needNotifications) { this.needNotifications = needNotifications; };
	public List<DareCriteria> getCriterions() { return this.criterions; }
	public void setCriterions(List<DareCriteria> criterions) { this.criterions = criterions; };

	public DareCreationRequestMessage(){
	}

	public DareCreationRequestMessage(long subscriptionFee, long jackpot, int maxCountWinners, int delayBeforeStart, int duration, boolean isPrivate, boolean isForGuild, boolean isForAlliance, boolean needNotifications, List<DareCriteria> criterions){
		this.subscriptionFee = subscriptionFee;
		this.jackpot = jackpot;
		this.maxCountWinners = maxCountWinners;
		this.delayBeforeStart = delayBeforeStart;
		this.duration = duration;
		this.isPrivate = isPrivate;
		this.isForGuild = isForGuild;
		this.isForAlliance = isForAlliance;
		this.needNotifications = needNotifications;
		this.criterions = criterions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, isPrivate);
			flag = BooleanByteWrapper.SetFlag(1, flag, isForGuild);
			flag = BooleanByteWrapper.SetFlag(2, flag, isForAlliance);
			flag = BooleanByteWrapper.SetFlag(3, flag, needNotifications);
			writer.writeByte(flag);
			writer.writeVarLong(this.subscriptionFee);
			writer.writeVarLong(this.jackpot);
			writer.writeShort(this.maxCountWinners);
			writer.writeUnsignedInt(this.delayBeforeStart);
			writer.writeUnsignedInt(this.duration);
			writer.writeShort(this.criterions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.criterions.size()){
				this.criterions.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.isPrivate = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.isForGuild = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.isForAlliance = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.needNotifications = BooleanByteWrapper.GetFlag(flag, (byte) 3);
			this.subscriptionFee = reader.readVarLong();
			this.jackpot = reader.readVarLong();
			this.maxCountWinners = reader.readShort();
			this.delayBeforeStart = reader.readUnsignedByte();
			this.duration = reader.readUnsignedByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.criterions = new ArrayList<DareCriteria>();
			while( _loc3_ <  _loc2_){
				DareCriteria _loc15_ = new DareCriteria();
				_loc15_.Deserialize(reader);
				this.criterions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
