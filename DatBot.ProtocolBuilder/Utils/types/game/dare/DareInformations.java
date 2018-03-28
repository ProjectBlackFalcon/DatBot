package protocol.network.types.game.dare;

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
import protocol.network.types.game.character.CharacterBasicMinimalInformations;
import protocol.network.types.game.dare.DareCriteria;

@SuppressWarnings("unused")
public class DareInformations extends NetworkMessage {
	public static final int ProtocolId = 502;

	private double dareId;
	private CharacterBasicMinimalInformations creator;
	private long subscriptionFee;
	private long jackpot;
	private int maxCountWinners;
	private double endDate;
	private boolean isPrivate;
	private int guildId;
	private int allianceId;
	private List<DareCriteria> criterions;
	private double startDate;

	public double getDareId() { return this.dareId; }
	public void setDareId(double dareId) { this.dareId = dareId; };
	public CharacterBasicMinimalInformations getCreator() { return this.creator; }
	public void setCreator(CharacterBasicMinimalInformations creator) { this.creator = creator; };
	public long getSubscriptionFee() { return this.subscriptionFee; }
	public void setSubscriptionFee(long subscriptionFee) { this.subscriptionFee = subscriptionFee; };
	public long getJackpot() { return this.jackpot; }
	public void setJackpot(long jackpot) { this.jackpot = jackpot; };
	public int getMaxCountWinners() { return this.maxCountWinners; }
	public void setMaxCountWinners(int maxCountWinners) { this.maxCountWinners = maxCountWinners; };
	public double getEndDate() { return this.endDate; }
	public void setEndDate(double endDate) { this.endDate = endDate; };
	public boolean isIsPrivate() { return this.isPrivate; }
	public void setIsPrivate(boolean isPrivate) { this.isPrivate = isPrivate; };
	public int getGuildId() { return this.guildId; }
	public void setGuildId(int guildId) { this.guildId = guildId; };
	public int getAllianceId() { return this.allianceId; }
	public void setAllianceId(int allianceId) { this.allianceId = allianceId; };
	public List<DareCriteria> getCriterions() { return this.criterions; }
	public void setCriterions(List<DareCriteria> criterions) { this.criterions = criterions; };
	public double getStartDate() { return this.startDate; }
	public void setStartDate(double startDate) { this.startDate = startDate; };

	public DareInformations(){
	}

	public DareInformations(double dareId, CharacterBasicMinimalInformations creator, long subscriptionFee, long jackpot, int maxCountWinners, double endDate, boolean isPrivate, int guildId, int allianceId, List<DareCriteria> criterions, double startDate){
		this.dareId = dareId;
		this.creator = creator;
		this.subscriptionFee = subscriptionFee;
		this.jackpot = jackpot;
		this.maxCountWinners = maxCountWinners;
		this.endDate = endDate;
		this.isPrivate = isPrivate;
		this.guildId = guildId;
		this.allianceId = allianceId;
		this.criterions = criterions;
		this.startDate = startDate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.dareId);
			creator.Serialize(writer);
			writer.writeVarLong(this.subscriptionFee);
			writer.writeVarLong(this.jackpot);
			writer.writeShort(this.maxCountWinners);
			writer.writeDouble(this.endDate);
			writer.writeBoolean(this.isPrivate);
			writer.writeVarInt(this.guildId);
			writer.writeVarInt(this.allianceId);
			writer.writeShort(this.criterions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.criterions.size()){
				this.criterions.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeDouble(this.startDate);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dareId = reader.readDouble();
			this.creator = new CharacterBasicMinimalInformations();
			this.creator.Deserialize(reader);
			this.subscriptionFee = reader.readVarLong();
			this.jackpot = reader.readVarLong();
			this.maxCountWinners = reader.readShort();
			this.endDate = reader.readDouble();
			this.isPrivate = reader.readBoolean();
			this.guildId = reader.readVarInt();
			this.allianceId = reader.readVarInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.criterions = new ArrayList<DareCriteria>();
			while( _loc3_ <  _loc2_){
				DareCriteria _loc15_ = new DareCriteria();
				_loc15_.Deserialize(reader);
				this.criterions.add(_loc15_);
				_loc3_++;
			}
			this.startDate = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
