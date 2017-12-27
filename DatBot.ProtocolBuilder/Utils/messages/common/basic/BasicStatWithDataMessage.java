package protocol.network.messages.common.basic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.common.basic.BasicStatMessage;
import protocol.network.types.common.basic.StatisticData;

@SuppressWarnings("unused")
public class BasicStatWithDataMessage extends BasicStatMessage {
	public static final int ProtocolId = 6573;

	private List<StatisticData> datas;

	public List<StatisticData> getDatas() { return this.datas; };
	public void setDatas(List<StatisticData> datas) { this.datas = datas; };

	public BasicStatWithDataMessage(){
	}

	public BasicStatWithDataMessage(List<StatisticData> datas){
		this.datas = datas;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.datas.size());
			int _loc2_ = 0;
			while( _loc2_ < this.datas.size()){
				writer.writeShort(StatisticData.ProtocolId);
				this.datas.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.datas = new ArrayList<StatisticData>();
			while( _loc3_ <  _loc2_){
				StatisticData _loc15_ = (StatisticData) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.datas.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
