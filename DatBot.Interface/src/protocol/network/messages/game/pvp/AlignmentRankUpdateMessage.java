package protocol.network.messages.game.pvp;

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

@SuppressWarnings("unused")
public class AlignmentRankUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6058;

	private int alignmentRank;
	private boolean verbose;

	public int getAlignmentRank() { return this.alignmentRank; }
	public void setAlignmentRank(int alignmentRank) { this.alignmentRank = alignmentRank; };
	public boolean isVerbose() { return this.verbose; }
	public void setVerbose(boolean verbose) { this.verbose = verbose; };

	public AlignmentRankUpdateMessage(){
	}

	public AlignmentRankUpdateMessage(int alignmentRank, boolean verbose){
		this.alignmentRank = alignmentRank;
		this.verbose = verbose;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.alignmentRank);
			writer.writeBoolean(this.verbose);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.alignmentRank = reader.readByte();
			this.verbose = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
