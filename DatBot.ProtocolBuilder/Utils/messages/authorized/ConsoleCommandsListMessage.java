package protocol.network.messages.authorized;

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
public class ConsoleCommandsListMessage extends NetworkMessage {
	public static final int ProtocolId = 6127;

	private List<String> aliases;
	private List<String> args;
	private List<String> descriptions;

	public List<String> getAliases() { return this.aliases; };
	public void setAliases(List<String> aliases) { this.aliases = aliases; };
	public List<String> getArgs() { return this.args; };
	public void setArgs(List<String> args) { this.args = args; };
	public List<String> getDescriptions() { return this.descriptions; };
	public void setDescriptions(List<String> descriptions) { this.descriptions = descriptions; };

	public ConsoleCommandsListMessage(){
	}

	public ConsoleCommandsListMessage(List<String> aliases, List<String> args, List<String> descriptions){
		this.aliases = aliases;
		this.args = args;
		this.descriptions = descriptions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.aliases.size());
			int _loc2_ = 0;
			while( _loc2_ < this.aliases.size()){
				writer.writeUTF(this.aliases.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.args.size());
			int _loc3_ = 0;
			while( _loc3_ < this.args.size()){
				writer.writeUTF(this.args.get(_loc3_));
				_loc3_++;
			}
			writer.writeShort(this.descriptions.size());
			int _loc4_ = 0;
			while( _loc4_ < this.descriptions.size()){
				writer.writeUTF(this.descriptions.get(_loc4_));
				_loc4_++;
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
			this.aliases = new ArrayList<String>();
			while( _loc3_ <  _loc2_){
				String _loc15_ = reader.readUTF();
				this.aliases.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.args = new ArrayList<String>();
			while( _loc5_ <  _loc4_){
				String _loc16_ = reader.readUTF();
				this.args.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.descriptions = new ArrayList<String>();
			while( _loc7_ <  _loc6_){
				String _loc17_ = reader.readUTF();
				this.descriptions.add(_loc17_);
				_loc7_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
