package protocol.network.messages.game.context.roleplay.job;

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
import protocol.network.types.game.context.roleplay.job.JobBookSubscription;

@SuppressWarnings("unused")
public class JobBookSubscriptionMessage extends NetworkMessage {
	public static final int ProtocolId = 6593;

	private List<JobBookSubscription> subscriptions;

	public List<JobBookSubscription> getSubscriptions() { return this.subscriptions; }
	public void setSubscriptions(List<JobBookSubscription> subscriptions) { this.subscriptions = subscriptions; };

	public JobBookSubscriptionMessage(){
	}

	public JobBookSubscriptionMessage(List<JobBookSubscription> subscriptions){
		this.subscriptions = subscriptions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.subscriptions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.subscriptions.size()){
				this.subscriptions.get(_loc2_).Serialize(writer);
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
			this.subscriptions = new ArrayList<JobBookSubscription>();
			while( _loc3_ <  _loc2_){
				JobBookSubscription _loc15_ = new JobBookSubscription();
				_loc15_.Deserialize(reader);
				this.subscriptions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
