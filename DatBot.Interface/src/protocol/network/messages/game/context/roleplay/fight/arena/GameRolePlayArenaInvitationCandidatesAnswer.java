package protocol.network.messages.game.context.roleplay.fight.arena;

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
import protocol.network.types.game.context.roleplay.fight.arena.LeagueFriendInformations;

@SuppressWarnings("unused")
public class GameRolePlayArenaInvitationCandidatesAnswer extends NetworkMessage {
	public static final int ProtocolId = 6783;

	private List<LeagueFriendInformations> candidates;

	public List<LeagueFriendInformations> getCandidates() { return this.candidates; }
	public void setCandidates(List<LeagueFriendInformations> candidates) { this.candidates = candidates; };

	public GameRolePlayArenaInvitationCandidatesAnswer(){
	}

	public GameRolePlayArenaInvitationCandidatesAnswer(List<LeagueFriendInformations> candidates){
		this.candidates = candidates;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.candidates.size());
			int _loc2_ = 0;
			while( _loc2_ < this.candidates.size()){
				this.candidates.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.candidates = new ArrayList<LeagueFriendInformations>();
			while( _loc3_ <  _loc2_){
				LeagueFriendInformations _loc15_ = new LeagueFriendInformations();
				_loc15_.Deserialize(reader);
				this.candidates.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
