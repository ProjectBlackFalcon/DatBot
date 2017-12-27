package protocol.network.types.common.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.common.basic.StatisticData;

@SuppressWarnings("unused")
public class StatisticDataBoolean extends StatisticData {
	public static final int ProtocolId = 482;

	private boolean value;

	public boolean isValue() { return this.value; };
	public void setValue(boolean value) { this.value = value; };

	public StatisticDataBoolean(){
	}

	public StatisticDataBoolean(boolean value){
		this.value = value;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.value);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.value = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
