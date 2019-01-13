package protocol.network.messages.game.context.roleplay.breach.branch;

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
import protocol.network.types.game.context.roleplay.breach.ExtendedBreachBranch;

@SuppressWarnings("unused")
public class BreachBranchesMessage extends NetworkMessage {
	public static final int ProtocolId = 6812;

	private List<ExtendedBreachBranch> branches;

	public List<ExtendedBreachBranch> getBranches() { return this.branches; }
	public void setBranches(List<ExtendedBreachBranch> branches) { this.branches = branches; };

	public BreachBranchesMessage(){
	}

	public BreachBranchesMessage(List<ExtendedBreachBranch> branches){
		this.branches = branches;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.branches.size());
			int _loc2_ = 0;
			while( _loc2_ < this.branches.size()){
				this.branches.get(_loc2_).Serialize(writer);
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
			this.branches = new ArrayList<ExtendedBreachBranch>();
			while( _loc3_ <  _loc2_){
				ExtendedBreachBranch _loc15_ = new ExtendedBreachBranch();
				_loc15_.Deserialize(reader);
				this.branches.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
