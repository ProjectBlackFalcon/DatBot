package protocol.network.messages.game.context.roleplay.houses;

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
import protocol.network.types.game.house.HouseInformationsForSell;

@SuppressWarnings("unused")
public class HouseToSellListMessage extends NetworkMessage {
	public static final int ProtocolId = 6140;

	private int pageIndex;
	private int totalPage;
	private List<HouseInformationsForSell> houseList;

	public int getPageIndex() { return this.pageIndex; }
	public void setPageIndex(int pageIndex) { this.pageIndex = pageIndex; };
	public int getTotalPage() { return this.totalPage; }
	public void setTotalPage(int totalPage) { this.totalPage = totalPage; };
	public List<HouseInformationsForSell> getHouseList() { return this.houseList; }
	public void setHouseList(List<HouseInformationsForSell> houseList) { this.houseList = houseList; };

	public HouseToSellListMessage(){
	}

	public HouseToSellListMessage(int pageIndex, int totalPage, List<HouseInformationsForSell> houseList){
		this.pageIndex = pageIndex;
		this.totalPage = totalPage;
		this.houseList = houseList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.pageIndex);
			writer.writeVarShort(this.totalPage);
			writer.writeShort(this.houseList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.houseList.size()){
				this.houseList.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.pageIndex = reader.readVarShort();
			this.totalPage = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.houseList = new ArrayList<HouseInformationsForSell>();
			while( _loc3_ <  _loc2_){
				HouseInformationsForSell _loc15_ = new HouseInformationsForSell();
				_loc15_.Deserialize(reader);
				this.houseList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
